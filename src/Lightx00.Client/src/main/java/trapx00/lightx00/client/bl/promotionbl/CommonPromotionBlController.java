package trapx00.lightx00.client.bl.promotionbl;

import trapx00.lightx00.client.bl.draftbl.DraftService;
import trapx00.lightx00.client.bl.draftbl.factory.DraftServiceFactory;
import trapx00.lightx00.client.bl.logbl.LogService;
import trapx00.lightx00.client.bl.logbl.factory.LogServiceFactory;
import trapx00.lightx00.client.vo.manager.promotion.PromotionVoBase;
import trapx00.lightx00.shared.dataservice.promotiondataservice.CommonPromotionDataService;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.IdNotExistsException;
import trapx00.lightx00.shared.exception.database.NoMoreBillException;
import trapx00.lightx00.shared.exception.database.PromotionInvalidStateException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.po.manager.promotion.PromotionPoBase;
import trapx00.lightx00.shared.queryvo.BaseQueryVo;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommonPromotionBlController<PromotionVo extends PromotionVoBase, PromotionPo extends PromotionPoBase, QueryVo extends BaseQueryVo<PromotionPo,String,QueryVo>> {
    private LogService logService = LogServiceFactory.getLogService();
    private CommonPromotionDataService<PromotionPo, QueryVo> dataService;
    private DraftService draftService = DraftServiceFactory.getDraftService();
    private String promotionName = "";
    private PromotionPoVoConverter<PromotionPo, PromotionVo> converter;

    /**
     * Constructor.
     * @param dataService 对应的促销策略DataService
     * @param promotionName 促销策略中文名
     * @param converter vo/po转换器
     */
    public CommonPromotionBlController(CommonPromotionDataService<PromotionPo, QueryVo> dataService, String promotionName, PromotionPoVoConverter<PromotionPo, PromotionVo> converter) {
        this.dataService = dataService;
        this.promotionName = promotionName;
        this.converter = converter;
    }

    public ResultMessage submit(PromotionVo promotion) {
        try {
            ResultMessage opResult = dataService.submit(converter.fromVoToPo(promotion));
            if (opResult.isSuccess()) {
                logService.log(LogSeverity.Success, String.format("创建一份%s，内容是%s。", promotionName, promotion.toString()));
            } else {
                logService.log(LogSeverity.Failure, String.format("创建一份%s失败，原因不明。内容是%s。",promotionName, promotion.toString()));
            }
            return opResult;
        } catch (RemoteException e) { //RemoteException是网络原因的错误。这里记下日志，然后用UncheckedRemoteException包一下继续抛出。
            logService.log(LogSeverity.Failure, String.format("创建一份%s失败，原因网络原因，具体信息是%s，促销策略内容是%s", promotionName, e.getMessage(), promotion.toString()));
            throw new UncheckedRemoteException(e);
        } catch (IdExistsException e) { //参考服务器端写的代码，直接抓对应的异常即可，记得如果不在这里处理，需要继续往外抛出。比如这里就需要UI层继续处理这个异常，所以需要继续抛出。
            logService.log(LogSeverity.Failure, String.format("创建一份%s失败，原因是单据ID（%s）已经存在。促销策略内容是%s", promotionName, promotion.getId(), promotion.toString()));
            throw e;
        }

    }

    /**
     * Saves a half-completed Promotion as draft.
     *
     * @param promotion PromotionVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    public ResultMessage saveAsDraft(PromotionVo promotion) {
        submit(promotion); //再次强调草稿的逻辑是先提交，再找DraftService记录一下草稿信息。
        ResultMessage opResult = draftService.saveAsDraft(promotion);
        if (opResult.isSuccess()) {
            logService.log(LogSeverity.Success, "提交草稿成功，促销策略ID是" + promotion.getId());
        } else {
            logService.log(LogSeverity.Failure, "提交草稿失败，原因不明。");
        }
        return opResult;
    }

    /**
     * Gets the id for the next bill.
     *
     * @return id for the next bill
     */
    public String getId() {
        try {
            String id = dataService.getId();
            logService.log(LogSeverity.Info, String.format("获得了一个新的%s促销策略ID：%s", promotionName, id));
            return id;
        } catch (RemoteException e) { //RemoteException是网络原因的错误。这里记下日志，然后用UncheckedRemoteException包一下继续抛出。
            logService.log(LogSeverity.Failure, String.format("获得新%sID失败，原因是网络原因，具体是%s", promotionName, e.getMessage()));
            throw new UncheckedRemoteException(e);
        } catch (NoMoreBillException e) { //参考服务器端写的代码，直接抓对应的异常即可，记得如果不在这里处理，需要继续往外抛出。比如这里就需要UI层继续处理这个异常，所以需要继续抛出。
            logService.log(LogSeverity.Failure, String.format("获得新%sID失败，原因是当日ID已满。", promotionName));
            throw e;
        }
    }

    /**
     * Delete a draft.
     *
     * @param id id of the draft to be deleted
     * @return whether the operation is done successfully
     */
    public ResultMessage deleteDraft(String id) {
        String logLeading = String.format("删除%s草稿（id: %s）", promotionName, id);
        try {
            ResultMessage opResult = dataService.delete(id);
            if (opResult.isSuccess()) {
                logService.log(LogSeverity.Success, logLeading + "成功。");
            } else {
                logService.log(LogSeverity.Failure, logLeading + "失败。原因不明。");
            }
            return opResult;
        } catch (RemoteException e) {
            logService.log(LogSeverity.Failure, String.format(logLeading + "失败，原因网络原因，具体信息是%s。", e.getMessage()));
            throw new UncheckedRemoteException(e);
        } catch (IdNotExistsException e) {
            logService.log(LogSeverity.Failure, logLeading + "失败，原因是ID不存在。");
            throw e;
        }
    }

    /**
     * Delete a promotion.
     *
     * @param id id for the bill
     * @return whether the operation is done successfully
     */
    public ResultMessage delete(String id) {
        String logLeadingText = String.format("丢弃促销策略%s(id: %s)", promotionName,id);
        try {
            ResultMessage resultMessage = dataService.delete(id);
            if (resultMessage.isSuccess()) {
                logService.log(LogSeverity.Success, logLeadingText + "成功。");
            } else {
                logService.log(LogSeverity.Failure, logLeadingText + "失败，原因不明。");
            }
            return resultMessage;
        } catch (RemoteException e) {
            logService.log(LogSeverity.Failure, String.format(logLeadingText + "失败，原因是网络原因，具体信息是%s。", e.getMessage()));
            throw new UncheckedRemoteException(e);
        } catch (IdNotExistsException e) {
            logService.log(LogSeverity.Failure, logLeadingText + "失败，原因是ID不存在。");
            throw e;
        } catch (PromotionInvalidStateException e) {
            logService.log(LogSeverity.Failure, logLeadingText + "失败，原因是促销策略正在生效。");
            throw e;
        }
    }

    /**
     * Queries bills.
     *
     * @param query query
     * @return bills that match the condition
     */
    public List<PromotionVo> queryPromotion(QueryVo query) {
        String logLeadingText = String.format("查找%s", promotionName);
        try {
            PromotionPo[] queryResult = dataService.queryPromotion(query);
            logService.log(LogSeverity.Success, String.format(logLeadingText + "成功，查找到%d条记录。", queryResult.length));
            return Arrays.stream(queryResult).map(x -> converter.fromPoToVo(x)).collect(Collectors.toList());
        } catch (RemoteException e) {
            logService.log(LogSeverity.Failure, String.format(logLeadingText + "失败，原因是网络原因，具体信息是%s。", e.getMessage()));
            throw new UncheckedRemoteException(e);
        }
    }


}

package trapx00.lightx00.client.bl.util;

import trapx00.lightx00.client.bl.approvalbl.ApprovalRequest;
import trapx00.lightx00.client.bl.approvalbl.factory.ApprovalRequestFactory;
import trapx00.lightx00.client.bl.draftbl.DraftService;
import trapx00.lightx00.client.bl.draftbl.factory.DraftServiceFactory;
import trapx00.lightx00.client.bl.logbl.LogService;
import trapx00.lightx00.client.bl.logbl.factory.LogServiceFactory;
import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.shared.dataservice.CommonBillDataService;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.exception.database.BillInvalidStateException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.IdNotExistsException;
import trapx00.lightx00.shared.exception.database.NoMoreBillException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillPo;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.queryvo.BaseQueryVo;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommonBillBlController<BillVoType extends BillVo, BillPoType extends BillPo, QueryType extends BaseQueryVo<BillPoType, String, QueryType>> {
    private LogService logService = LogServiceFactory.getLogService();
    private CommonBillDataService<BillPoType, QueryType> dataService;
    private DraftService draftService = DraftServiceFactory.getDraftService();
    private String billName;
    private BillPoVoConverter<BillPoType, BillVoType> converter;
    private ApprovalRequest approvalRequest = ApprovalRequestFactory.getService();

    /**
     * Constructor.
     * @param dataService 对应的单据DataService
     * @param billName 单据中文名
     * @param converter vo/po转换器
     */
    public CommonBillBlController(CommonBillDataService<BillPoType, QueryType> dataService, String billName, BillPoVoConverter<BillPoType, BillVoType> converter) {
        this.dataService = dataService;
        this.billName = billName;
        this.converter = converter;
    }

    public ResultMessage add(BillVoType bill) {
        try {
            ResultMessage opResult = dataService.submit(converter.fromVoToPo(bill));
            if (opResult.isSuccess()) {
                logService.log(LogSeverity.Success, String.format("创建了一张%s，内容是%s。", billName, bill.toString()));

            } else {
                logService.log(LogSeverity.Failure, String.format("创建一张%s失败，原因不明。内容是%s。",billName, bill.toString()));
            }
            return opResult;
        } catch (RemoteException e) { //RemoteException是网络原因的错误。这里记下日志，然后用UncheckedRemoteException包一下继续抛出。
            logService.log(LogSeverity.Failure, String.format("创建一张%s失败，原因网络原因，具体信息是%s，单子内容是%s", billName, e.getMessage(), bill.toString()));
            throw new UncheckedRemoteException(e);
        } catch (IdExistsException e) { //参考服务器端写的代码，直接抓对应的异常即可，记得如果不在这里处理，需要继续往外抛出。比如这里就需要UI层继续处理这个异常，所以需要继续抛出。
            logService.log(LogSeverity.Failure, String.format("创建一张%s失败，原因是单据ID（%s）已经存在。单据内容是%s", billName, bill.getId(), bill.toString()));
            throw e;
        }
    }

    public ResultMessage submit(BillVoType bill) {
        bill.setState(BillState.WaitingForApproval);
        approvalRequest.requestApproval(bill);
        return add(bill);
    }

    /**
     * Saves a half-completed CashBill as draft.
     *
     * @param bill CashBillVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    public ResultMessage saveAsDraft(BillVoType bill) {
        bill.setState(BillState.Draft);
        add(bill); //再次强调草稿的逻辑是先提交，再找DraftService记录一下草稿信息。
        ResultMessage opResult = draftService.saveAsDraft(bill);
        if (opResult.isSuccess()) {
            logService.log(LogSeverity.Success, "提交草稿成功，单据ID是" + bill.getId());
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
            logService.log(LogSeverity.Info, String.format("获得了一个新的%s单子ID：%s", billName, id));
            return id;
        } catch (RemoteException e) { //RemoteException是网络原因的错误。这里记下日志，然后用UncheckedRemoteException包一下继续抛出。
            logService.log(LogSeverity.Failure, String.format("获得新%sID失败，原因是网络原因，具体是%s", billName, e.getMessage()));
            throw new UncheckedRemoteException(e);
        } catch (NoMoreBillException e) { //参考服务器端写的代码，直接抓对应的异常即可，记得如果不在这里处理，需要继续往外抛出。比如这里就需要UI层继续处理这个异常，所以需要继续抛出。
            logService.log(LogSeverity.Failure, String.format("获得新%sID失败，原因是当日ID已满。", billName));
            throw e;
        }
    }

    /**
     * Deletes a draft.
     *
     * @param id id of the draft to be deleted
     * @return whether the operation is done successfully
     */
    public ResultMessage deleteDraft(String id) {
        String logLeading = String.format("删除%s草稿（id: %s）", billName, id);
        try {
            ResultMessage opResult = dataService.abandon(id);
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
     * Abandons a bill.
     *
     * @param id id for the bill
     * @return whether the operation is done successfully
     */
    public ResultMessage abandon(String id) {
        String logLeadingText = String.format("丢弃一张%s单据(id: %s)", billName, id);
        try {
            ResultMessage resultMessage = dataService.abandon(id);
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
        }
    }

    /**
     * Activates a bill that has been approved of.
     *
     * @param id id for the bill
     * @return whether the operation is done successfully
     */
    public ResultMessage activate(String id) {
        String logLeadingText = String.format("使一张%s单据生效(id: %s)", billName, id);
        try {
            ResultMessage resultMessage = dataService.activate(id);
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
        }
    }

    /**
     * Queries bills.
     *
     * @param query query
     * @return bills that match the condition
     */
    public List<BillVoType> query(QueryType query) {
        String logLeadingText = String.format("查找%s单据", billName);
        try {
            BillPoType[] queryResult = dataService.query(query);
            logService.log(LogSeverity.Success, String.format(logLeadingText + "成功，查找到%d条记录。", queryResult.length));
            return Arrays.stream(queryResult).map(x -> converter.fromPoToVo(x)).collect(Collectors.toList());
        } catch (RemoteException e) {
            logService.log(LogSeverity.Failure, String.format(logLeadingText + "失败，原因是网络原因，具体信息是%s。", e.getMessage()));
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * When bill is approved, this method is called to modify the state of the bill.
     *
     * @param billId id for the bill
     * @param state  newState. Only BillState.Approved and BillState.Rejected are allowed.
     * @return whether the operation is done successfully
     */
    public ResultMessage approvalComplete(String billId, BillState state) {
        String logLeadingText = String.format("修改%s单据(id: %s)审批结果到%s", billName, billId, state.toString());
        try {
            ResultMessage resultMessage = dataService.approvalComplete(billId, state);
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
            logService.log(LogSeverity.Failure, logLeadingText + "失败，原因是单据ID不存在。");
            throw e;
        } catch (BillInvalidStateException e) {
            if (e.getExpecteds()[0].equals(BillState.WaitingForApproval)) { //原单据状态无效。
                logService.log(LogSeverity.Failure, String.format(logLeadingText + "失败，原因是原单据状态无效。期望是：%s，实际是：%s。", BillState.WaitingForApproval.toString(), e.getActual().toString()));
            } else { //新状态无效。
                logService.log(LogSeverity.Failure, String.format("失败，原因是传入状态无效。期望是：%s和%s, 实际是：%s", e.getExpecteds()[0].toString(), e.getExpecteds()[1], e.getActual()));
            }
            throw e;
        }
    }

}

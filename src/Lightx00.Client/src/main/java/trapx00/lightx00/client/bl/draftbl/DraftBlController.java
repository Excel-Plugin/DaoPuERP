package trapx00.lightx00.client.bl.draftbl;

import trapx00.lightx00.client.bl.adminbl.EmployeeInfo;
import trapx00.lightx00.client.bl.adminbl.factory.EmployeeInfoFactory;
import trapx00.lightx00.client.bl.logbl.LogService;
import trapx00.lightx00.client.bl.logbl.factory.LogServiceFactory;
import trapx00.lightx00.client.bl.loginbl.CurrentUserService;
import trapx00.lightx00.client.bl.loginbl.factory.CurrentUserServiceFactory;
import trapx00.lightx00.client.blservice.draftblservice.DraftBlService;
import trapx00.lightx00.client.datafactory.draftdataservicefactory.DraftDataServiceFactory;
import trapx00.lightx00.client.vo.Draftable;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.draft.DraftVo;
import trapx00.lightx00.client.vo.draft.DraftableQueryServiceRegistry;
import trapx00.lightx00.shared.dataservice.draftdataservice.DraftDataService;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.draft.DraftPo;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.queryvo.DraftQueryVo;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DraftBlController implements DraftBlService, DraftService {
    private DraftDataService draftDataService = DraftDataServiceFactory.getService();
    private LogService logService = LogServiceFactory.getLogService();
    private CurrentUserService currentUserService = CurrentUserServiceFactory.getCurrentUserService();
    private EmployeeInfo employeeInfo = EmployeeInfoFactory.getEmployeeInfo();
    /**
     * Saves a draftable as a draft.
     *
     * @param draft draft
     * @return whether operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(Draftable draft) {
        DraftPo draftPo = new DraftPo(new Date(),
            currentUserService.getCurrentUser().getId(),
            draft.getId(),
            DraftTypeRegistry.getTypeFromClass(draft.getClass())
        );
        try {
            ResultMessage rm = draftDataService.add(draftPo);
            if (rm.isSuccess()) {
                logService.log(LogSeverity.Success, "保存草稿成功。草稿ID：" + draft.getId());
            } else {
                logService.log(LogSeverity.Failure, "保存草稿失败，原因不明。");
            }
            return rm;
        } catch (RemoteException e) {
            logService.log(LogSeverity.Failure, "保存草稿失败，是网络错误，信息：" + e.getMessage());
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Updates current user's draft.
     *
     * @return Current User's draft
     */
    @Override
    public DraftVo[] update() {
        try {
            EmployeeVo currentUser = currentUserService.getCurrentUser();
            DraftPo[] queryResults = draftDataService.query(new DraftQueryVo().eq("saverId", currentUser.getId()));

            List<DraftVo> convertedVos = Arrays.stream(queryResults)
                .map(x -> new DraftVo(
                    x.getId(),
                    x.getSaveTime(),
                    employeeInfo.queryById(x.getSaverId()),
                    DraftableQueryServiceRegistry.queryDraftable(x.getDraftType(), x.getDraftableId()),
                    x.getDraftType()))
                .collect(Collectors.toList());
            logService.log(LogSeverity.Success, String.format("查找了用户%s(id: %s)的草稿。查到%d条记录。",currentUser.getName(), currentUser.getId(), convertedVos.size()));
            return convertedVos.toArray(new DraftVo[convertedVos.size()]);
        } catch (RemoteException e) {
            logService.log(LogSeverity.Failure, "查找草稿失败，原因是网络原因，信息是：" + e.getMessage());
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Deletes a draft.
     *
     * @param draft draft to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(DraftVo draft) {
        try {
            draft.getDraft().deleteService().deleteDraft(draft.getDraft().getId());
            ResultMessage rm = draftDataService.delete(draft.getId());
            if (rm.isSuccess()) {
                logService.log(LogSeverity.Success, String.format("删除草稿(id: %d)成功。", draft.getId()));
            } else {
                logService.log(LogSeverity.Failure, String.format("删除草稿(id: %d)失败，原因不明。", draft.getId()));
            }
            return rm;
        } catch (RemoteException e) {
            logService.log(LogSeverity.Failure, "删除草稿失败，是网络原因，信息是：" + e.getMessage());
            throw new UncheckedRemoteException(e);
        }
    }
}

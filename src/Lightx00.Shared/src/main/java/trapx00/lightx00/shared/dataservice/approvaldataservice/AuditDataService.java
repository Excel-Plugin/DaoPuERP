package trapx00.lightx00.shared.dataservice.approvaldataservice;

import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.AuditIdPo;
import trapx00.lightx00.shared.queryvo.AuditIdQueryVo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuditDataService extends Remote {
    /**
     * Filter some AuditIdPo.
     * @param query the filter conditions
     * @return array of AuditIdPo which match the conditions
     */
    AuditIdPo[] query(AuditIdQueryVo query) throws RemoteException;

    /**
     * Delete AuditIdPo after approving.
     * @param billInfo the corresponding AuditIdPo to the BillPo
     * @return whether the operation is done successfully
     */
    ResultMessage pass(AuditIdPo billInfo) throws RemoteException;

    /**
     * Delete AuditIdPo after rejecting the approval.
     * @param billInfo the corresponding AuditIdPo to the BillPo
     * @return whether the operation is done successfully
     */
    ResultMessage reject(AuditIdPo billInfo) throws RemoteException;

    /**
     * Save the submitted bill.
     * @param id id of the bill has been submitted
     * @return whether the operation is done successfully
     */
    ResultMessage requestApproval(String id) throws RemoteException;
}

package trapx00.lightx00.server.data.approvaldata.mock;

import trapx00.lightx00.server.data.approvaldata.AuditDataController;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.AuditIdPo;
import trapx00.lightx00.shared.queryvo.AuditIdQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.util.Date;

public class AuditDataControllerMock extends AuditDataController {
    /**
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     *
     * <p>The object is exported with a server socket
     * created using the {@link RMISocketFactory} class.
     *
     * @throws RemoteException if failed to export object
     * @since JDK1.1
     */
    public AuditDataControllerMock() throws RemoteException {
    }

    /**
     * Filter some AuditIdPo.
     * @param query the filter conditions
     * @return array of AuditIdPo which match the conditions
     */
    public AuditIdPo[] query(AuditIdQueryVo query) {
        return new AuditIdPo[]{new AuditIdPo("XJFYD-20171112-00001",new Date())};
    }

    /**
     * Delete AuditIdPo after rejecting the approval.
     * @param billInfo the corresponding AuditIdPo to the BillPo
     * @return whether the operation is done successfully
     */
    public ResultMessage reject(AuditIdPo billInfo) {
        return ResultMessage.Success;
    }

    /**
     * Delete AuditIdPo after approving the bill.
     * @param billInfo the corresponding AuditIdPo to the BillPo
     * @return whether the operation is done successfully
     */
    public ResultMessage pass(AuditIdPo billInfo) {
        return ResultMessage.Success;
    }

    /**
     * Save the submitted bill.
     * @param id id of the bill has been submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage requestApproval(String id) {
        return ResultMessage.Success;
    }
}

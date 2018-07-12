package trapx00.lightx00.server.data.financedata;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.financedata.factory.FinanceDataDaoFactory;
import trapx00.lightx00.server.data.util.CommonBillDataController;
import trapx00.lightx00.shared.dataservice.financedataservice.InitialEstablishmentDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.financestaff.SystemSnapshotPo;
import trapx00.lightx00.shared.queryvo.SystemSnapshotQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class InitialEstablishmentDataController extends UnicastRemoteObject implements InitialEstablishmentDataService {
    /**
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     * <p>
     * <p>The object is exported with a server socket
     * created using the {@link RMISocketFactory} class.
     *
     * @throws RemoteException if failed to export object
     * @since JDK1.1
     */
    public InitialEstablishmentDataController() throws RemoteException {
    }

    private Dao<SystemSnapshotPo, String> dao = FinanceDataDaoFactory.getSystemSnapshotDao();
    private CommonBillDataController<SystemSnapshotPo> commonBillDataController = new CommonBillDataController<>(dao, this);

    /**
     * Submits a SystemSnapshotPo or save it as a draft.
     * If there is a SystemSnapshotPo with the same id as passed-in parameter do,
     *    if the existing SystemSnapshotPo is in BillState.Draft state, it will be updated/replaced by parameter.
     *    otherwise a IdExistsException would be thrown.
     *
     * @param snapshot snapshot
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(SystemSnapshotPo snapshot) {
        return commonBillDataController.submit(snapshot);
    }

    /**
     * Activates a SystemSnapshotPo.
     * The bill must be in BillState.WaitingForApproval state.
     * Otherwise a BillInvalidStateException will be thrown.
     *
     * @param id id for the bill that have been approved of
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage activate(String id) {
        return commonBillDataController.activate(id);
    }

    /**
     * Abandons a SystemSnapshotPo.
     * If a snapshot is in BillState.Draft, it will be deleted.
     * If a snapshot is in BillState.Rejected/Approved/WaitingForApproval, it will be changed as Abandoned.
     * If a snapshot is in other state, a BillInvalidStateException will be thrown.
     * @param id id for the CashBill to be abandoned
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage abandon(String id) {
        return commonBillDataController.abandon(id);
    }

    /**
     * Changes the state of a bill if approval is completed.
     *
     * @param billId    the id of the bill.
     * @param billState new bill state. Only Approved and Rejected is allowed.
     * @return whether the operation is done successfully.
     */
    @Override
    public ResultMessage approvalComplete(String billId, BillState billState) throws RemoteException {
        return commonBillDataController.approvalComplete(billId, billState);
    }

    /**
     * Gets the id for the next snapshot.
     * If there are already 99999 snapshot for this day, a NoMoreBillException will be thrown.
     *
     * @return id for the next snapshot
     */
    @Override
    public String getId() {
        return commonBillDataController.getId("QCJZ");
    }

    /**
     * Queries SystemSnapshotPos.
     *
     * @param query query
     * @return SystemSnapshotPos that match query condition
     */
    @Override
    public SystemSnapshotPo[] query(SystemSnapshotQueryVo query) {
        List<SystemSnapshotPo> results = commonBillDataController.query(query);
        return results.toArray(new SystemSnapshotPo[results.size()]);
    }
}

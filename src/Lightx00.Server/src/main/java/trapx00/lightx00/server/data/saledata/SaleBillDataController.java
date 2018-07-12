package trapx00.lightx00.server.data.saledata;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.saledata.factory.SaleBillDataDaoFactory;
import trapx00.lightx00.server.data.util.CommonBillDataController;
import trapx00.lightx00.shared.dataservice.saledataservice.SaleBillDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.salestaff.SaleBillPo;
import trapx00.lightx00.shared.queryvo.SaleBillQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class SaleBillDataController extends UnicastRemoteObject implements SaleBillDataService {
    private Dao<SaleBillPo, String> saleBillDao = SaleBillDataDaoFactory.getSaleBillDao();
    private CommonBillDataController<SaleBillPo> commonBillDataController = new CommonBillDataController<SaleBillPo>(saleBillDao, this);

    /**
     * @throws RemoteException
     */
    public SaleBillDataController() throws RemoteException {
        super();
    }

    /**
     * Activates a SaleBill.
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
     * Abandons a SaleBill.
     * If a Bill is in BillState.Draft, it will be deleted.
     * If a Bill is in BillState.Rejected/Approved/WaitingForApproval, it will be changed as Abandoned.
     * If a bill is in other state, a BillInvalidStateException will be thrown.
     *
     * @param id id for the bill to be abandoned
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage abandon(String id) {
        return commonBillDataController.abandon(id);
    }

    /**
     * Queries SaleBill.
     *
     * @param query query condition
     * @return SaleBillPos that match the query condition
     */
    @Override
    public SaleBillPo[] query(SaleBillQueryVo query) {
        List<SaleBillPo> result = commonBillDataController.query(query);
        return result.toArray(new SaleBillPo[result.size()]);
    }

    /**
     * submit a saleBill
     *
     * @param saleBill to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(SaleBillPo saleBill) throws RemoteException {
        return commonBillDataController.submit(saleBill);
    }

    /**
     * get the id of the next sale bill
     *
     * @return the id of the next sale bill
     */
    @Override
    public String getId() {
        return commonBillDataController.getId("XSD");
    }

    /**
     * Changes the state of a bill if approval is completed.
     *
     * @param billId    the id of the bill.
     * @param billState new bill state. Only Approved and Rejected is allowed.
     * @return whether the operation is done successfully.
     */
    @Override
    public ResultMessage approvalComplete(String billId, BillState billState) {
        return commonBillDataController.approvalComplete(billId, billState);
    }
}

package trapx00.lightx00.server.data.saledata;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.saledata.factory.SaleRefundBillDataDaoFactory;
import trapx00.lightx00.server.data.util.CommonBillDataController;
import trapx00.lightx00.shared.dataservice.saledataservice.SaleRefundBillDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.salestaff.SaleRefundBillPo;
import trapx00.lightx00.shared.queryvo.SaleRefundBillQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class SaleRefundBillDataController extends UnicastRemoteObject implements SaleRefundBillDataService {
    private Dao<SaleRefundBillPo, String> saleRefundBillDao = SaleRefundBillDataDaoFactory.getSaleRefundBillDao();
    private CommonBillDataController<SaleRefundBillPo> commonBillDataController = new CommonBillDataController<SaleRefundBillPo>(saleRefundBillDao, this);

    /**
     * @throws RemoteException
     */
    public SaleRefundBillDataController() throws RemoteException {
        super();
    }

    /**
     * Activates a SaleRefundBill.
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
     * Abandons a SaleRefundBill.
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
     * Queries SaleRefundBill.
     *
     * @param query query condition
     * @return SaleRefundBillPos that match the query condition
     */
    @Override
    public SaleRefundBillPo[] query(SaleRefundBillQueryVo query) {
        List<SaleRefundBillPo> result=commonBillDataController.query(query);
        return result.toArray(new SaleRefundBillPo[result.size()]);
    }

    /**
     * submit a saleRefundBill
     *
     * @param saleRefundBill to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(SaleRefundBillPo saleRefundBill) throws RemoteException {
        return commonBillDataController.submit(saleRefundBill);
    }

    /**
     * get the id of the next sale refund bill
     *
     * @return the id of the next sale refund bill
     */
    @Override
    public String getId() {
        return commonBillDataController.getId("XSTHD");
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

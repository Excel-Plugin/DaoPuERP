package trapx00.lightx00.server.data.financedata.mock;

import trapx00.lightx00.server.data.financedata.ReceivalBillDataController;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.financestaff.ReceivalBillPo;
import trapx00.lightx00.shared.po.financestaff.Transcation;
import trapx00.lightx00.shared.queryvo.ReceivalBillQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.util.Date;

public class ReceivalBillDataControllerMock extends ReceivalBillDataController {
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
    public ReceivalBillDataControllerMock() throws RemoteException {
    }

    /**
     * Submits a ReceivalBill or save it as a draft.
     *
     * @param bill ReceivalBill
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(ReceivalBillPo bill) {
        return ResultMessage.Success;
    }

    /**
     * Activates a ReceivalBill.
     *
     * @param id id for the ReceivalBill that have been approved of
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage activate(String id) {
        return ResultMessage.Success;
    }

    /**
     * Abandons a ReceivalBill.
     *
     * @param id id for the ReceivalBill to be abandoned
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage abandon(String id) {
        return ResultMessage.Success;
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
        return ResultMessage.Success;
    }

    /**
     * Queries bills
     *
     * @param query query
     * @return ReceivalBillVos that match query condition
     */
    @Override
    public ReceivalBillPo[] query(ReceivalBillQueryVo query) {
        return new ReceivalBillPo[]{
                new ReceivalBillPo("SKD-20171111-00001",new Date(), BillState.Approved, "123", "123", new Transcation[]{ new Transcation(1,0.0,"123")}, 0.0)
        };
    }

    /**
     * Gets the id for the next bill.
     *
     * @return id for the next bill
     */
    @Override
    public String getId() {
        return "SKD-20171111-00002";
    }
}

package trapx00.lightx00.server.data.inventorydata.mock;

import trapx00.lightx00.server.data.inventorydata.PurchaseRefundBillDataController;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.salestaff.PurchaseRefundBillPo;

import java.rmi.RemoteException;

public class PurchaseRefundBillDataControllerMock extends PurchaseRefundBillDataController {
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
    public PurchaseRefundBillDataControllerMock() throws RemoteException {
    }

    @Override
    public ResultMessage submit(PurchaseRefundBillPo purchaseBill) {
        return ResultMessage.Success;
    }
}

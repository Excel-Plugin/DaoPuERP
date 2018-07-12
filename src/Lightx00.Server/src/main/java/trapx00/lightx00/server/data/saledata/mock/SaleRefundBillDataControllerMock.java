package trapx00.lightx00.server.data.saledata.mock;

import trapx00.lightx00.server.data.saledata.SaleRefundBillDataController;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.salestaff.SaleRefundBillPo;

import java.rmi.RemoteException;

public class SaleRefundBillDataControllerMock extends SaleRefundBillDataController {
    /**
     * @throws RemoteException
     */
    public SaleRefundBillDataControllerMock() throws RemoteException {
    }

    @Override
    public ResultMessage submit(SaleRefundBillPo saleRefundBill) {
        return ResultMessage.Success;
    }
}

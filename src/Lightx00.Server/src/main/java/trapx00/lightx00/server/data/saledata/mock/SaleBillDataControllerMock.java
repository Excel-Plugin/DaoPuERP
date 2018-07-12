package trapx00.lightx00.server.data.saledata.mock;

import trapx00.lightx00.server.data.saledata.SaleBillDataController;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.salestaff.SaleBillPo;

import java.rmi.RemoteException;

public class SaleBillDataControllerMock extends SaleBillDataController {
    /**
     * @throws RemoteException
     */
    public SaleBillDataControllerMock() throws RemoteException {
    }

    @Override
    public ResultMessage submit(SaleBillPo saleBill) {
        return ResultMessage.Success;
    }
}

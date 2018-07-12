package trapx00.lightx00.server.data.clientdata.mock;

import trapx00.lightx00.server.data.clientdata.ClientDataController;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.client.ClientPo;
import trapx00.lightx00.shared.po.client.ClientState;
import trapx00.lightx00.shared.po.client.ClientType;

import java.rmi.RemoteException;

public class ClientDataControllerMock extends ClientDataController {
    /**
     * @throws RemoteException
     */
    public ClientDataControllerMock() throws RemoteException {
    }

    @Override
    public ClientPo[] query(String query) {
        ClientPo[] clientPos = new ClientPo[1];
        clientPos[0] = new ClientPo("0",
                ClientType.Retailer,
                1,
                "xiaoming",
                "12345678",
                "12345678",
                "210000",
                "12345679@qq.com",
                123,
                456,
                "0",
             ClientState.Real);
        return clientPos;
    }

    @Override
    public ResultMessage modify(ClientPo client) {
        return ResultMessage.Success;
    }

    /**
     * Gets the id for the next client
     *
     * @return id for the next client
     */
    @Override
    public String getId() {
        return "0";
    }

    @Override
    public ResultMessage add(ClientPo client) {
        return ResultMessage.Success;
    }

    /**
     * delete some clients
     *
     * @param id of the client to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(String[] id) {
        return ResultMessage.Success;
    }
}

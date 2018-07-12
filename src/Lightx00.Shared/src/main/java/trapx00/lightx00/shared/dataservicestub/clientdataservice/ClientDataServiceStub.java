package trapx00.lightx00.shared.dataservicestub.clientdataservice;

import trapx00.lightx00.shared.dataservice.clientdataservice.ClientDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.client.ClientPo;
import trapx00.lightx00.shared.po.client.ClientState;
import trapx00.lightx00.shared.po.client.ClientType;

import java.rmi.RemoteException;

public class ClientDataServiceStub implements ClientDataService {
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

    /**
     * Query clients who has the id
     *
     * @param id id
     * @return the client with its id
     */
    @Override
    public ClientPo queryById(String id) throws RemoteException {
        return null;
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
    public String getId() throws RemoteException {
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
    public ResultMessage delete(String[] id) throws RemoteException {
        return ResultMessage.Success;
    }

}

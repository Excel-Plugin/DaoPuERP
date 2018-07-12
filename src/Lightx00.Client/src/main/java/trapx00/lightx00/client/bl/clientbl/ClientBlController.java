package trapx00.lightx00.client.bl.clientbl;

import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.draftbl.DraftService;
import trapx00.lightx00.client.bl.draftbl.DraftableQueryService;
import trapx00.lightx00.client.bl.draftbl.factory.DraftServiceFactory;
import trapx00.lightx00.client.bl.util.ClientPoVoConverter;
import trapx00.lightx00.client.blservice.clientblservice.ClientBlService;
import trapx00.lightx00.client.datafactory.clientdataservicefactory.ClientDataServiceFactory;
import trapx00.lightx00.client.vo.Draftable;
import trapx00.lightx00.client.vo.salestaff.ClientVo;
import trapx00.lightx00.shared.dataservice.clientdataservice.ClientDataService;
import trapx00.lightx00.shared.po.ClientModificationFlag;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.client.ClientPo;
import trapx00.lightx00.shared.po.client.ClientState;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientBlController implements ClientBlService, DraftDeleteService, ClientModificationService, DraftableQueryService, ClientQueryService, ClientPoVoConverter {

    ClientDataService clientDataService = ClientDataServiceFactory.getInstance();
    DraftService draftService = DraftServiceFactory.getDraftService();

    /**
     * Deletes a draft.
     *
     * @param id id of the draft to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage deleteDraft(String id) {
        try {
            String[] ids = new String[1];
            ids[0] = id;
            return clientDataService.delete(ids);
        } catch (RemoteException e) {
            e.printStackTrace();
            return ResultMessage.Failure;
        }
    }

    /**
     * Query clients which includes the words
     *
     * @param query condition
     * @return the clients which corresponds to the query
     */
    @Override
    public ClientVo[] query(String query) {
        try {
            ClientPo[] clientPos = clientDataService.query(query);
            ArrayList<ClientVo> clientVos = new ArrayList<ClientVo>();
            for (int i = 0; i < clientPos.length; i++) {
                if (clientPos[i].getClientState() == ClientState.Real) {
                    clientVos.add(fromPoToVo(clientPos[i]));
                }
            }
            return clientVos.toArray(new ClientVo[clientVos.size()]);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Query clients who has the id
     *
     * @param id id
     * @return the client with its id
     */
    @Override
    public ClientVo queryById(String id) {
        try {
            ClientPo clientPo = clientDataService.queryById(id);
            return fromPoToVo(clientPo);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Saves a half-completed client as draft.
     *
     * @param client ClientVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(ClientVo client) {
        try {
            ResultMessage resultMessage;
            ResultMessage resultMessage1 = draftService.saveAsDraft(client);
            ResultMessage resultMessage2 = clientDataService.add(fromVoToPo(client, ClientState.Draft));
            if (resultMessage1 == resultMessage2) {
                if (resultMessage1 == ResultMessage.Success) {
                    resultMessage = ResultMessage.Success;
                } else {
                    resultMessage = ResultMessage.Failure;
                }
            } else {
                resultMessage = ResultMessage.Failure;
            }
            return resultMessage;
        } catch (RemoteException e) {
            e.printStackTrace();
            return ResultMessage.Failure;
        }
    }

    /**
     * Gets the id for the next client
     *
     * @return id for the next client
     */
    @Override
    public String getId() {
        String result;
        try {
            result = clientDataService.getId();
        } catch (RemoteException e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }

    /**
     * modify a client
     *
     * @param client to be modified
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage modify(ClientVo client) {
        try {
            ClientPo clientPo = fromVoToPo(client, ClientState.Real);
            return clientDataService.modify(clientPo);
        } catch (RemoteException e) {
            e.printStackTrace();
            return ResultMessage.Failure;
        }
    }

    /**
     * add a client
     *
     * @param client to be added
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(ClientVo client) {
        try {
            ClientPo clientPo = fromVoToPo(client, ClientState.Real);
            return clientDataService.add(clientPo);
        } catch (RemoteException e) {
            e.printStackTrace();
            return ResultMessage.Failure;
        }
    }

    /**
     * delete some clients
     *
     * @param ids of the client to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(String[] ids) {
        try {
            return clientDataService.delete(ids);
        } catch (RemoteException e) {
            e.printStackTrace();
            return ResultMessage.Failure;
        }
    }

    /**
     * modify the pay or receive of client
     *
     * @param clientId the id of the client
     * @param flag     to pay or to receive
     * @param delta    to alter number
     * @return Result:SECCESS or FAILED
     */
    @Override
    public ResultMessage modifyClient(String clientId, ClientModificationFlag flag, double delta) {
        ClientPo clientPo = new ClientPo();
        try {
            ClientPo[] clientPos = clientDataService.query(clientId);
            for (ClientPo client : clientPos) {
                if (client.getId().equals(clientId)) {
                    clientPo = client;
                    break;
                }
            }
            if (clientPo.getId() == null || clientPo.getId().length() == 0) {
                return ResultMessage.Failure;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return ResultMessage.Failure;
        }
        try {
            if (flag == ClientModificationFlag.PAYABLE) {
                clientPo.setPayable(clientPo.getPayable() + delta);
            } else {
                clientPo.setReceivable(clientPo.getReceivable() + delta);
            }
            clientDataService.modify(clientPo);
            return ResultMessage.Success;
        } catch (RemoteException e) {
            e.printStackTrace();
            return ResultMessage.Failure;
        }
    }

    /**
     * Queries draft with id.
     *
     * @param id i  d
     * @return draft
     */
    @Override
    public Draftable queryDraft(String id) {
        try {
            ClientPo[] clientPos = clientDataService.query(id);
            ClientPo clientPo = new ClientPo();
            for (ClientPo client : clientPos) {
                if (client.getId().equals(id)) {
                    clientPo = client;
                    break;
                }
            }
            return fromPoToVo(clientPo);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Convert vo to po.
     *
     * @param clientVo    vo
     * @param clientState
     * @return po
     */
    @Override
    public ClientPo fromVoToPo(ClientVo clientVo, ClientState clientState) {
        ClientPo clientPo = new ClientPo(clientVo.getId(), clientVo.getClientType(), clientVo.getClientLevel(), clientVo.getName(), clientVo.getPhone(), clientVo.getAddress(), clientVo.getZipCode(), clientVo.getEmail(), clientVo.getReceivableQuota(), clientVo.getReceivable(), clientVo.getPayable(), clientVo.getDefaultOperatorId(), clientState);
        return clientPo;
    }

    /**
     * Convert vo to po, with default clientState=Real.
     *
     * @param clientVo vo
     * @return po
     */
    @Override
    public ClientPo fromVoToPo(ClientVo clientVo) {
        ClientPo clientPo = new ClientPo(clientVo.getId(), clientVo.getClientType(), clientVo.getClientLevel(), clientVo.getName(), clientVo.getPhone(), clientVo.getAddress(), clientVo.getZipCode(), clientVo.getEmail(), clientVo.getReceivableQuota(), clientVo.getReceivable(), clientVo.getPayable(), clientVo.getDefaultOperatorId(), ClientState.Real);
        return clientPo;
    }

    /**
     * Convert po to vo.
     *
     * @param clientPo po
     * @return vo
     */
    @Override
    public ClientVo fromPoToVo(ClientPo clientPo) {
        ClientVo clientVo = new ClientVo(clientPo.getId(), clientPo.getClientType(), clientPo.getClientLevel(), clientPo.getName(), clientPo.getPhone(), clientPo.getAddress(), clientPo.getZipCode(), clientPo.getEmail(), clientPo.getReceivableQuota(), clientPo.getReceivable(), clientPo.getPayable(), clientPo.getDefaultOperatorId());
        return clientVo;
    }
}

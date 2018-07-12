package trapx00.lightx00.client.blservice.clientblservice;

import trapx00.lightx00.client.vo.salestaff.ClientVo;
import trapx00.lightx00.shared.po.ResultMessage;

public interface ClientBlService {

    /**
     * Query clients which includes the words
     * @param query condition
     * @return the clients which corresponds to the query
     */
    ClientVo[] query(String query);

    /**
     * Query clients who has the id
     *
     * @param id id
     * @return the client with its id
     */
    ClientVo queryById(String id);

    /**
     * Saves a half-completed client as draft.
     *
     * @param client ClientVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    ResultMessage saveAsDraft(ClientVo client);//保存客户信息为草稿

    /**
     * Gets the id for the next client
     *
     * @return id for the next client
     */
    String getId();//获得新的ID

    /**
     * modify a client
     * @param client to be modified
     * @return whether the operation is done successfully
     */
    ResultMessage modify(ClientVo client);//修改客户信息

    /**
     * add a client
     * @param client to be added
     * @return whether the operation is done successfully
     */
    ResultMessage add(ClientVo client);//新增客户

    /**
     * delete some clients
     * @param id of the client to be deleted
     * @return whether the operation is done successfully
     */
    ResultMessage delete(String[] id);//删除客户
}
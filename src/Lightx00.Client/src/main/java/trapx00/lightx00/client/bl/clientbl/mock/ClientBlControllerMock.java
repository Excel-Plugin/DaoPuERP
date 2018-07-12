package trapx00.lightx00.client.bl.clientbl.mock;

import trapx00.lightx00.client.bl.clientbl.ClientBlController;
import trapx00.lightx00.client.vo.salestaff.ClientVo;
import trapx00.lightx00.client.vo.salestaff.SaleStaffVo;
import trapx00.lightx00.shared.po.ClientModificationFlag;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.client.ClientType;
import trapx00.lightx00.shared.po.employee.EmployeeState;
import trapx00.lightx00.shared.po.salestaff.SaleStaffType;

import java.util.Date;

public class ClientBlControllerMock extends ClientBlController {

    /**
     * Deletes a draft.
     * @param id id of the draft to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage deleteDraft(String id) {
        return ResultMessage.Success;
    }

    /**
     * Query clients which includes the words
     * @param query condition
     * @return the clients which corresponds to the query
     */
    @Override
    public ClientVo[] query(String query) {
        ClientVo[] clientVos=new ClientVo[1];
        clientVos[0]=new ClientVo("0",
                ClientType.Retailer,
                1,
                "xiaoming",
                "12345678",
                "12345678",
                "210000",
                "12345679@qq.com",
                123,
                456,
                new SaleStaffVo("10001","销售经理",new Date(),"123456", EmployeeState.Active,true, SaleStaffType.Manager));
        return clientVos;
    }

    /**
     * Saves a half-completed client as draft.
     *
     * @param client ClientVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(ClientVo client) {
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

    /**
     * modify a client
     * @param client to be modified
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage modify(ClientVo client) {
        return ResultMessage.Success;
    }

    /**
     * add a client
     * @param client to be added
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(ClientVo client) {
        return ResultMessage.Success;
    }

    /**
     * delete some clients
     * @param id of the client to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(String[] id) {
        return ResultMessage.Success;
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
        return ResultMessage.Success;
    }
}

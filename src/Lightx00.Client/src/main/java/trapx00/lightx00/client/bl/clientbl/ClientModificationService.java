package trapx00.lightx00.client.bl.clientbl;

import trapx00.lightx00.shared.po.ClientModificationFlag;
import trapx00.lightx00.shared.po.ResultMessage;

public interface ClientModificationService {

    /**
     * modify the pay or receive of client
     * @param clientId the id of the client
     * @param flag to pay or to receive
     * @param delta to alter number
     * @return Result:SECCESS or FAILED
     */
    ResultMessage modifyClient(String clientId, ClientModificationFlag flag, double delta);

}

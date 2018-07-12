package trapx00.lightx00.server.data.promotiondata.mock;

import trapx00.lightx00.server.data.promotiondata.ClientPromotionDataController;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.ClientPromotionPo;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;
import trapx00.lightx00.shared.queryvo.promotion.TotalPricePromotionQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.util.Date;

public class ClientPromotionDataControllerMock extends ClientPromotionDataController {
    /**
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     *
     * <p>The object is exported with a server socket
     * created using the {@link RMISocketFactory} class.
     *
     * @throws RemoteException if failed to export object
     * @since JDK1.1
     */
    public ClientPromotionDataControllerMock() throws RemoteException {
    }

    /**
     * Submit a completed ClientPromotionPo.
     * @param promotion the ClientPromotionPo to be submitted
     * @return whether the operation is done successfully
     */
    public ResultMessage submit(ClientPromotionPo promotion) {
        return ResultMessage.Success;
    }

    /**
     * Filter ClientPromotionPo.
     * @param query the filter conditions
     * @return array of ClientPromotionPo which match the conditions
     */
    public ClientPromotionPo[] queryPromotion(TotalPricePromotionQueryVo query) {
        return new ClientPromotionPo[]{new ClientPromotionPo("0001",new Date(),new Date(), PromotionState.Waiting,1,200,null,0)};
    }

    /**
     * Delete a useless ClientPromotionPo.
     * @param promotion the ClientPromotionPo to be deleted
     * @return whether the operation is done successfully
     */
    public ResultMessage delete(ClientPromotionPo promotion) {
        return ResultMessage.Success;
    }

    /**
     * Get id for the next promotion.
     *
     * @return id for the next promotion
     */
    public String getId() {
        return "0001";
    }
}

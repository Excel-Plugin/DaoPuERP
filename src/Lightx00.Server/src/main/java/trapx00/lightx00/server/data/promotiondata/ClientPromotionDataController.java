package trapx00.lightx00.server.data.promotiondata;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.promotiondata.factory.PromotionDataDaoFactory;
import trapx00.lightx00.shared.dataservice.promotiondataservice.ClientPromotionDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.ClientPromotionPo;
import trapx00.lightx00.shared.queryvo.promotion.ClientPromotionQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ClientPromotionDataController extends UnicastRemoteObject implements ClientPromotionDataService {
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
    public ClientPromotionDataController() throws RemoteException {
    }

    private Dao<ClientPromotionPo,String> clientPromotionDao = PromotionDataDaoFactory.getClientPromotionDao();
    private PromotionDataController<ClientPromotionPo> promotionDataController = new PromotionDataController<>(clientPromotionDao, this);

    /**
     * Submit a completed ClientPromotionPo.
     * If there is a ClientPromotionPo with the same id as passed-in parameter do,
     *    if the existing ClientPromotionPo is in PromotionState.Draft state, it will be updated/replaced by parameter.
     *    otherwise a IdExistsException would be thrown.
     *
     * @param promotion the ClientPromotionPo to be submitted
     * @return whether the operation is done successfully
     */
    public ResultMessage submit(ClientPromotionPo promotion) {
        return promotionDataController.submit(promotion);
    }

    /**
     * Filter ClientPromotionPo.
     * @param query the filter conditions
     * @return array of ClientPromotionPo which match the conditions
     */
    public ClientPromotionPo[] queryPromotion(ClientPromotionQueryVo query) {
        List<ClientPromotionPo> result = promotionDataController.query(query);
        return result.toArray(new ClientPromotionPo[result.size()]);
    }

    /**
     * Delete a useless promotion.
     * @param id id of the promotion to be deleted
     * @return whether the operation is done successfully
     */
    public ResultMessage delete(String id) {
        return promotionDataController.delete(id);
    }

    /**
     * Get id for the next promotion.
     *
     * @return id for the next promotion
     */
    public String getId() {
        return promotionDataController.getId("KHCXCL");
    }
}

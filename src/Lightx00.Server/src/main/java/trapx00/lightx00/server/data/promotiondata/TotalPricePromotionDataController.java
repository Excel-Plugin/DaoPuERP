package trapx00.lightx00.server.data.promotiondata;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.promotiondata.factory.PromotionDataDaoFactory;
import trapx00.lightx00.shared.dataservice.promotiondataservice.TotalPricePromotionDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.TotalPricePromotionPo;
import trapx00.lightx00.shared.queryvo.promotion.TotalPricePromotionQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class TotalPricePromotionDataController extends UnicastRemoteObject implements TotalPricePromotionDataService {

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
    public TotalPricePromotionDataController() throws RemoteException {
    }

    private Dao<TotalPricePromotionPo,String> totalPricePromotionDao = PromotionDataDaoFactory.getTotalPricePromotionDao();
    private PromotionDataController<TotalPricePromotionPo> promotionDataController = new PromotionDataController<>(totalPricePromotionDao, this);

    /**
     * Submit a completed TotalPricePromotionPo.
     * @param promotion the TotalPricePromotionPo to be submitted
     * @return whether the operation is done successfully
     */
    public ResultMessage submit(TotalPricePromotionPo promotion) {
        return promotionDataController.submit(promotion);
    }

    /**
     * Filter TotalPricePromotionPo.
     * If there is a TotalPricePromotionPo with the same id as passed-in parameter do,
     *    if the existing TotalPricePromotionPo is in PromotionState.Draft state, it will be updated/replaced by parameter.
     *    otherwise a IdExistsException would be thrown.
     *
     * @param query the filter conditions
     * @return array of TotalPricePromotionPo which match the conditions
     */
    public TotalPricePromotionPo[] queryPromotion(TotalPricePromotionQueryVo query) {
        List<TotalPricePromotionPo> result = promotionDataController.query(query);
        return result.toArray(new TotalPricePromotionPo[result.size()]);
    }

    /**
     * Delete a useless TotalPricePromotionPo.
     * @param id id of the TotalPricePromotionPo to be deleted
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
        return promotionDataController.getId("MECXCL");
    }
}

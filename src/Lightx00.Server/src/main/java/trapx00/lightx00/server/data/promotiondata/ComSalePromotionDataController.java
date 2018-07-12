package trapx00.lightx00.server.data.promotiondata;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.promotiondata.factory.PromotionDataDaoFactory;
import trapx00.lightx00.shared.dataservice.promotiondataservice.ComSalePromotionDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.ComSalePromotionPo;
import trapx00.lightx00.shared.queryvo.promotion.ComSalePromotionQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ComSalePromotionDataController extends UnicastRemoteObject implements ComSalePromotionDataService {
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
    public ComSalePromotionDataController() throws RemoteException {
    }

    private Dao<ComSalePromotionPo,String> comSalePromotionDao = PromotionDataDaoFactory.getComSalePromotionDao();
    private PromotionDataController<ComSalePromotionPo> promotionDataController = new PromotionDataController<>(comSalePromotionDao, this);

    /**
     * Submit a completed ComSalePromotionPo.
     * If there is a ComSalePromotionPo with the same id as passed-in parameter do,
     *    if the existing ComSalePromotionPo is in PromotionState.Draft state, it will be updated/replaced by parameter.
     *    otherwise a IdExistsException would be thrown.
     * @param promotion the ComSalePromotionPo to be submitted
     * @return whether the operation is done successfully
     */
    public ResultMessage submit(ComSalePromotionPo promotion) {
        return promotionDataController.submit(promotion);
    }

    /**
     * Filter ComSalePromotionPo.
     * @param query the filter conditions
     * @return array of ComSalePromotionPo which match the conditions
     */
    public ComSalePromotionPo[] queryPromotion(ComSalePromotionQueryVo query) {
        List<ComSalePromotionPo> result = promotionDataController.query(query);
        return result.toArray(new ComSalePromotionPo[result.size()]);
    }

    /**
     * Delete a useless ComSalePromotionPo.
     * @param id id of the ComSalePromotionPo to be deleted
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
        return promotionDataController.getId("SPJJCXCL");
    }
}

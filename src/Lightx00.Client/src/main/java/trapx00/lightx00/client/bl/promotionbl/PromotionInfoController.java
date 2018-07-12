package trapx00.lightx00.client.bl.promotionbl;

import trapx00.lightx00.client.bl.draftbl.DraftableQueryService;
import trapx00.lightx00.client.datafactory.promotiondataservicefactory.ClientPromotionDataServiceFactory;
import trapx00.lightx00.client.datafactory.promotiondataservicefactory.ComSalePromotionDataServiceFactory;
import trapx00.lightx00.client.datafactory.promotiondataservicefactory.TotalPricePromotionDataServiceFactory;
import trapx00.lightx00.client.vo.Draftable;
import trapx00.lightx00.client.vo.manager.promotion.ClientPromotionVo;
import trapx00.lightx00.client.vo.manager.promotion.ComSalePromotionVo;
import trapx00.lightx00.client.vo.manager.promotion.PromotionVoBase;
import trapx00.lightx00.client.vo.manager.promotion.TotalPricePromotionVo;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;
import trapx00.lightx00.shared.dataservice.promotiondataservice.ClientPromotionDataService;
import trapx00.lightx00.shared.dataservice.promotiondataservice.ComSalePromotionDataService;
import trapx00.lightx00.shared.dataservice.promotiondataservice.TotalPricePromotionDataService;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.po.manager.promotion.ClientPromotionPo;
import trapx00.lightx00.shared.po.manager.promotion.ComSalePromotionPo;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;
import trapx00.lightx00.shared.po.manager.promotion.TotalPricePromotionPo;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;
import trapx00.lightx00.shared.queryvo.promotion.ClientPromotionQueryVo;
import trapx00.lightx00.shared.queryvo.promotion.ComSalePromotionQueryVo;
import trapx00.lightx00.shared.queryvo.promotion.TotalPricePromotionQueryVo;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PromotionInfoController implements PromotionInfo,DraftableQueryService {
    private ClientPromotionDataService clientPromotionDataService = ClientPromotionDataServiceFactory.getService();
    private ComSalePromotionDataService comSalePromotionDataService = ComSalePromotionDataServiceFactory.getService();
    private TotalPricePromotionDataService totalPricePromotionDataService = TotalPricePromotionDataServiceFactory.getService();

    private ClientPromotionVo fromPotoVo(ClientPromotionPo po) {
        return new ClientPromotionVo(po.getId(),po.getStartDate(),po.getEndDate(),po.getPromotionState(),po.getClientLevel(),po.getCouponPrice(),po.getPromotionCommodities(),po.getSalePrice());
    }
    private ComSalePromotionVo fromPotoVo(ComSalePromotionPo po) {
        return new ComSalePromotionVo(po.getId(),po.getStartDate(),po.getEndDate(),po.getPromotionState(),po.getPromotionCommodities(),po.getOnSalePrice());
    }
    private TotalPricePromotionVo fromPotoVo(TotalPricePromotionPo po) {
        return new TotalPricePromotionVo(po.getId(),po.getStartDate(),po.getEndDate(),po.getPromotionState(),po.getCouponPrice(),po.getTotalPrice(),po.getPromotionCommodities());
    }
    /**
     * Give usable promotions to the sale.
     * @param saleBill the saleBill which represents current sale activity
     * @return usable promotions to the sale
     */
    @Override
    public PromotionVoBase[] queryPromotion(SaleBillVo saleBill) {
        try {
            Date percent = new Date();
            List<PromotionVoBase> result = new ArrayList<PromotionVoBase>();
            ClientPromotionPo[] clientPromotions = clientPromotionDataService.queryPromotion(new ClientPromotionQueryVo());
            ComSalePromotionPo[] comSalePromotions = comSalePromotionDataService.queryPromotion(new ComSalePromotionQueryVo());
            TotalPricePromotionPo[] totalPricePromotions = totalPricePromotionDataService.queryPromotion(new TotalPricePromotionQueryVo());
            CommodityItem[] saleCommodities = saleBill.getCommodityList();
            double totalPrice = saleBill.getOriginTotal();
            for(TotalPricePromotionPo po : totalPricePromotions) {
                if(compare(percent,po.getStartDate(),po.getEndDate())){
                    if(po.getTotalPrice() <= totalPrice)
                        result.add(fromPotoVo(po));
                }
            }
            for(ClientPromotionPo po:clientPromotions) {
                if(compare(percent,po.getStartDate(),po.getEndDate())){
                    if(po.getClientLevel()<=saleBill.getClientLevel())
                        result.add(fromPotoVo(po));
                }
            }
            for(ComSalePromotionPo po:comSalePromotions) {
                //改促销策略
                if(compare(percent,po.getStartDate(),po.getEndDate())){
                    PromotionCommodity[] promotionCommodities = po.getPromotionCommodities();
                    int num = 0;
                    for(CommodityItem commodity:saleBill.getCommodityList()) {
                        for(PromotionCommodity promotionCommodity:promotionCommodities){
                            if(commodity.getCommodityId().equals(promotionCommodity.getCommodityId())&&commodity.getNumber()>=promotionCommodity.getAmount())
                                num++;
                        }
                    }
                    if(num == promotionCommodities.length)
                        result.add(fromPotoVo(po));
                }

            }
            return result.toArray(new PromotionVoBase[result.size()]);
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }


    /**
     * Queries promotion with id.
     * @param id id
     * @return promotionVo
     */
    public PromotionVoBase queryPromotion(String id) {
        try {
            ClientPromotionPo[] clientPromotions = clientPromotionDataService.queryPromotion(new ClientPromotionQueryVo());
            ComSalePromotionPo[] comSalePromotions = comSalePromotionDataService.queryPromotion(new ComSalePromotionQueryVo());
            TotalPricePromotionPo[] totalPricePromotions = totalPricePromotionDataService.queryPromotion(new TotalPricePromotionQueryVo());
            for(TotalPricePromotionPo po : totalPricePromotions) {
                if(po.getId().equals(id))
                    return fromPotoVo(po);
            }
            for(ClientPromotionPo po:clientPromotions) {
                if(po.getId().equals(id))
                    return fromPotoVo(po);
            }
            for(ComSalePromotionPo po:comSalePromotions) {
                if(po.getId().equals(id))
                    return fromPotoVo(po);
            }
            return null;
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Queries draft with id.
     *
     * @param id id
     * @return draft
     */
    @Override
    public Draftable queryDraft(String id) {
        return queryPromotion(id);
    }

    private boolean compare(Date now,Date start,Date end){
        return now.getTime() >= start.getTime() && now.getTime() <= end.getTime();
    }
}

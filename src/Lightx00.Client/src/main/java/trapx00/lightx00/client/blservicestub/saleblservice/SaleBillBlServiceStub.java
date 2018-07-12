package trapx00.lightx00.client.blservicestub.saleblservice;

import trapx00.lightx00.client.blservice.saleblservice.SaleBillBlService;
import trapx00.lightx00.client.datafactory.saledataservicefactory.SaleBillDataServiceFactory;
import trapx00.lightx00.client.vo.manager.promotion.PromotionVoBase;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;
import trapx00.lightx00.shared.dataservice.saledataservice.SaleBillDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.salestaff.SaleBillPo;
import trapx00.lightx00.shared.queryvo.SaleBillQueryVo;

import java.rmi.RemoteException;

public class SaleBillBlServiceStub implements SaleBillBlService {

    SaleBillDataService saleBillDataService=new SaleBillDataServiceFactory().getInstance();
    /**
     * submit a saleBill
     *
     * @param saleBill to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(SaleBillVo saleBill) {
        SaleBillPo saleBillPo=null;
        try {
            return saleBillDataService.submit(saleBillPo);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return ResultMessage.Failure;
    }

    /**
     * saves a half-completed saleBill as draft.
     *
     * @param saleBill SaleBillVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(SaleBillVo saleBill) {
        return ResultMessage.Success;
    }

    /**
     * Gets the id for the next saleBill
     *
     * @return id for the next saleBill
     */
    @Override
    public String getId() {
        return "XSD-20171107-00000";
    }

    /**
     * query for SaleBills
     *
     * @param query function
     * @return array of SaleBillVo
     */
    @Override
    public SaleBillVo[] querySaleBill(SaleBillQueryVo query) {
        return new SaleBillVo[0];
    }

    /**
     * query for sale promotion
     *
     * @param saleBillVo current sale bill
     * @return promotions
     */
    @Override
    public PromotionVoBase[] queryPromotion(SaleBillVo saleBillVo) {
        return new PromotionVoBase[0];
    }
}

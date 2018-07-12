package trapx00.lightx00.client.test.bl.inventorybl;

import org.junit.Test;
import trapx00.lightx00.client.bl.inventorybl.PurchaseBillBlController;
import trapx00.lightx00.client.bl.inventorybl.PurchaseBillBlInfo;
import trapx00.lightx00.client.bl.inventorybl.PurchaseRefundBillBlController;
import trapx00.lightx00.client.bl.inventorybl.factory.PurchaseBillBlFactory;
import trapx00.lightx00.client.bl.inventorybl.factory.PurchaseBillBlInfoFactory;
import trapx00.lightx00.client.bl.inventorybl.factory.PurchaseRefundBillBlFactory;
import trapx00.lightx00.client.vo.salestaff.PurchaseBillVo;
import trapx00.lightx00.client.vo.salestaff.PurchaseRefundBillVo;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;
import trapx00.lightx00.shared.queryvo.PurchaseBillQueryVo;
import trapx00.lightx00.shared.queryvo.PurchaseRefundBillQueryVo;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class PurchaseBillBlInfoControllerTest {

    private PurchaseBillBlInfo purchaseBillBlInfo = PurchaseBillBlInfoFactory.getPurchaseBillBlInfo();
    private PurchaseBillBlController purchaseBillBlController = PurchaseBillBlFactory.getPurchaseBillBlController();
    private PurchaseRefundBillBlController purchaseRefundBillBlController = PurchaseRefundBillBlFactory.getPurchaseRefundBillBlController();
    private PurchaseBillVo purchaseBillVo=new PurchaseBillVo("0",new Date(), BillState.Draft, "123",1,"123","123",10,new CommodityItem[] {new CommodityItem("123","!23","!@#",1,1,1,"")});
    private PurchaseRefundBillVo purchaseRefundBillVo=new PurchaseRefundBillVo("0",null,null,null,1,null,null,100,null);

    @Test
    public void queryPurchaseBillVo() throws Exception {
        purchaseBillBlController.submit(purchaseBillVo);
        assertEquals("0", purchaseBillBlInfo.queryPurchaseBillVo(new PurchaseBillQueryVo().idEq("0"))[0].getId());
        purchaseBillBlController.abandon("0");
    }

    @Test
    public void queryPurchaseRefundBillVo() throws Exception {
        purchaseRefundBillBlController.submit(purchaseRefundBillVo);
        assertEquals("0", purchaseBillBlInfo.queryPurchaseRefundBillVo(new PurchaseRefundBillQueryVo().idEq("0"))[0].getId());
        purchaseRefundBillBlController.abandon("0");
    }

}
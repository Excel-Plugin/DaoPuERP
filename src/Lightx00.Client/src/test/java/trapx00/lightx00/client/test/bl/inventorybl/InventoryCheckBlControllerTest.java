package trapx00.lightx00.client.test.bl.inventorybl;

import org.junit.Test;
import trapx00.lightx00.client.bl.inventorybl.InventoryCheckBlController;
import trapx00.lightx00.client.bl.inventorybl.PurchaseBillBlInfo;
import trapx00.lightx00.client.bl.inventorybl.factory.InventoryCheckServiceFactory;
import trapx00.lightx00.client.bl.inventorybl.factory.PurchaseBillBlInfoFactory;
import trapx00.lightx00.client.bl.salebl.SaleBillBlInfo;
import trapx00.lightx00.client.bl.salebl.factory.SaleBillBlInfoFactory;
import trapx00.lightx00.client.bl.util.FormatDateTime;
import trapx00.lightx00.client.vo.inventorystaff.InventoryViewItem;
import trapx00.lightx00.client.vo.inventorystaff.InventoryViewVo;
import trapx00.lightx00.client.vo.salestaff.PurchaseBillVo;
import trapx00.lightx00.client.vo.salestaff.PurchaseRefundBillVo;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;
import trapx00.lightx00.client.vo.salestaff.SaleRefundBillVo;
import trapx00.lightx00.shared.queryvo.PurchaseBillQueryVo;
import trapx00.lightx00.shared.queryvo.PurchaseRefundBillQueryVo;
import trapx00.lightx00.shared.queryvo.SaleBillQueryVo;
import trapx00.lightx00.shared.queryvo.SaleRefundBillQueryVo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InventoryCheckBlControllerTest {

    private InventoryCheckBlController service= InventoryCheckServiceFactory.getInventoryCheckBlController();

    File file=new File("");
    Date date=new Date();

    private SaleBillBlInfo saleBillBlInfo= SaleBillBlInfoFactory.getSaleBillBlInfo();
    private PurchaseBillBlInfo purchaseBillBlInfo= PurchaseBillBlInfoFactory.getPurchaseBillBlInfo();

    @Test
    public void test()throws Exception{
        String str = "2018-01-02";
        Date beginTime=new SimpleDateFormat("yyyy-MM-dd").parse(str);
        String str1 = "2018-01-03";
        Date endTime=new SimpleDateFormat("yyyy-MM-dd").parse(str1);
        PurchaseBillVo[] purchaseBillVos=purchaseBillBlInfo.queryPurchaseBillVo(new PurchaseBillQueryVo()
                .between("date",beginTime,endTime));
        PurchaseRefundBillVo[] purchaseRefundBillVos=purchaseBillBlInfo.queryPurchaseRefundBillVo(new PurchaseRefundBillQueryVo()
                .between("date",beginTime,endTime));
        SaleBillVo[]saleBillVos=saleBillBlInfo.querySaleBill(new SaleBillQueryVo()
                .between("date",beginTime,endTime));
        SaleRefundBillVo[] saleRefundBillVos=saleBillBlInfo.querySaleRefundBill(new SaleRefundBillQueryVo()
                .between("date",beginTime,endTime).and());
        InventoryViewVo inventoryViewVo=new InventoryViewVo("View"+FormatDateTime.toShortDateString(new Date()),new Date(),null);
        InventoryViewItem inventoryViewItem=new InventoryViewItem(new Date(),0,
                0,0,0,0,0,
                0);
        System.out.println(saleBillVos.length);
        System.out.println(saleBillVos[0].getCommodityList()[0].getNumber());
        for(int i=0;i<purchaseBillVos.length;i++){
            for(int j=0;j<purchaseBillVos[i].getCommodityList().length;j++) {
                inventoryViewItem.setInventoryAmount(inventoryViewItem.getInventoryAmount() + purchaseBillVos[i].getCommodityList()[j].getNumber());
                inventoryViewItem.setInventoryMoney(inventoryViewItem.getOutOfInVentoryMoney() + purchaseBillVos[i].getCommodityList()[j].getPrice()
                        *purchaseBillVos[i].getCommodityList()[j].getNumber());
            }
        }
        for(int i=0;i<purchaseRefundBillVos.length;i++){
            for(int j=0;j<purchaseRefundBillVos[i].getCommodityList().length;j++) {
                inventoryViewItem.setOutOfInventoryAmount(inventoryViewItem.getOutOfInVentoryMoney() + purchaseRefundBillVos[i].getCommodityList()[j].getNumber());
                inventoryViewItem.setOutOfInVentoryMoney(inventoryViewItem.getOutOfInVentoryMoney() + purchaseRefundBillVos[i].getCommodityList()[j].getPrice()
                        *purchaseRefundBillVos[i].getCommodityList()[j].getNumber());
            }
        }

        for(int i=0;i<saleBillVos.length;i++){
            for(int j=0;j<saleBillVos[i].getCommodityList().length;j++) {
                inventoryViewItem.setOutOfInventoryAmount(inventoryViewItem.getOutOfInVentoryMoney() + saleBillVos[i].getCommodityList()[j].getNumber());
                inventoryViewItem.setOutOfInVentoryMoney(inventoryViewItem.getOutOfInVentoryMoney() + saleBillVos[i].getCommodityList()[j].getPrice()
                        *saleBillVos[i].getCommodityList()[j].getNumber());
            }
        }

        for(int i=0;i<saleRefundBillVos.length;i++){
            for(int j=0;j<saleRefundBillVos[i].getCommodityList().length;j++) {
                inventoryViewItem.setInventoryAmount(inventoryViewItem.getInventoryAmount() + saleRefundBillVos[i].getCommodityList()[j].getNumber());
                inventoryViewItem.setInventoryMoney(inventoryViewItem.getOutOfInVentoryMoney() + saleRefundBillVos[i].getCommodityList()[j].getPrice()
                        *saleRefundBillVos[i].getCommodityList()[j].getNumber());
            }
        }

        inventoryViewItem.setSum(inventoryViewItem.getInventoryAmount()-inventoryViewItem.getOutOfInventoryAmount());
        inventoryViewItem.setInSoldPrice(inventoryViewItem.getInventoryMoney()/inventoryViewItem.getInventoryAmount());
        inventoryViewItem.setOutSoldPrice(inventoryViewItem.getOutOfInVentoryMoney()/inventoryViewItem.getOutOfInventoryAmount());
        inventoryViewVo.setItems(inventoryViewItem);
        System.out.println(inventoryViewVo.getItems().getInventoryAmount());
        System.out.println(inventoryViewVo.getItems().getSum());
    }
//    @Test
//    public void check() throws Exception {
//        Date endtime=new Date();
//        String result=String.valueOf(service.check(date,endtime).getSum());
//        assertEquals("208.0",result);
//    }
//
//    @Test
//    public void picture() throws Exception {
//
//        assertEquals("C0001",service.picture().getItems()[0].getId());
//    }

    @Test
    public void export() throws Exception {
       // assertEquals(ResultMessage.Success,service.export(file));
    }

}
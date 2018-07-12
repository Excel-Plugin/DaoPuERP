package trapx00.lightx00.client.bl.inventorybl;

import trapx00.lightx00.client.bl.commoditybl.CommodityInfo;
import trapx00.lightx00.client.bl.commoditybl.factory.CommodityInfoFactory;
import trapx00.lightx00.client.bl.inventorybl.factory.PurchaseBillBlInfoFactory;
import trapx00.lightx00.client.bl.logbl.LogService;
import trapx00.lightx00.client.bl.logbl.factory.LogServiceFactory;
import trapx00.lightx00.client.bl.salebl.SaleBillBlInfo;
import trapx00.lightx00.client.bl.salebl.factory.SaleBillBlInfoFactory;
import trapx00.lightx00.client.bl.util.ExcelOutput;
import trapx00.lightx00.client.bl.util.FormatDateTime;
import trapx00.lightx00.client.bl.util.PoVoConverter;
import trapx00.lightx00.client.blservice.inventoryblservice.InventoryCheckBlService;
import trapx00.lightx00.client.vo.inventorystaff.*;
import trapx00.lightx00.client.vo.salestaff.PurchaseBillVo;
import trapx00.lightx00.client.vo.salestaff.PurchaseRefundBillVo;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;
import trapx00.lightx00.client.vo.salestaff.SaleRefundBillVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.inventorystaff.CommodityPo;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.queryvo.*;
import trapx00.lightx00.shared.util.DateHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class InventoryCheckBlController implements InventoryCheckBlService {

    private LogService logService = LogServiceFactory.getLogService();
    private CommodityInfo commodityInfo= CommodityInfoFactory.getCommodityInfo();
    private SaleBillBlInfo saleBillBlInfo= SaleBillBlInfoFactory.getSaleBillBlInfo();
    private PurchaseBillBlInfo purchaseBillBlInfo= PurchaseBillBlInfoFactory.getPurchaseBillBlInfo();
    private PoVoConverter<CommodityPo,CommodityVo> commodityConver=CommodityInfoFactory.getPoVoConverter();

    /**
     * Checks the invenntory change between the begintime and endtime
     * @param beginTime
     * @param endTime
     * @return The inventoryView during specified time rangeget
     */
    @Override
    public InventoryViewVo getInventoryView(Date beginTime, Date endTime) {
        PurchaseBillVo [] purchaseBillVos=purchaseBillBlInfo.queryPurchaseBillVo(new PurchaseBillQueryVo()
                .between("date",beginTime,endTime).and().eq("state",BillState.Activated));
        PurchaseRefundBillVo[] purchaseRefundBillVos=purchaseBillBlInfo.queryPurchaseRefundBillVo(new PurchaseRefundBillQueryVo()
                .between("date",beginTime,endTime).and().eq("state",BillState.Activated));
        SaleBillVo[]saleBillVos=saleBillBlInfo.querySaleBill(new SaleBillQueryVo()
                .between("date",beginTime,endTime).and().eq("state",BillState.Activated));
        SaleRefundBillVo[] saleRefundBillVos=saleBillBlInfo.querySaleRefundBill(new SaleRefundBillQueryVo()
                .between("date",beginTime,endTime).and().eq("state",BillState.Activated));

        InventoryViewVo inventoryViewVo=new InventoryViewVo("View"+FormatDateTime.toShortDateString(new Date()),new Date(),null);
        InventoryViewItem inventoryViewItem=new InventoryViewItem(new Date(),0,
                0,0,0,0,0,
                0);
        for(int i=0;i<purchaseBillVos.length;i++){
            for(int j=0;j<purchaseBillVos[i].getCommodityList().length;j++) {
                inventoryViewItem.setInventoryAmount(inventoryViewItem.getInventoryAmount() + purchaseBillVos[i].getCommodityList()[j].getNumber());
                System.out.println(inventoryViewItem.getInventoryAmount());
                inventoryViewItem.setInventoryMoney(inventoryViewItem.getInventoryMoney() + purchaseBillVos[i].getCommodityList()[j].getPrice()
                        *purchaseBillVos[i].getCommodityList()[j].getNumber());
                inventoryViewItem.setInsellnum(inventoryViewItem.getInsellnum()+purchaseBillVos[i].getCommodityList()[j].getNumber());
                inventoryViewItem.setOutSoldPrice(inventoryViewItem.getOutSoldPrice()+purchaseBillVos[i].getCommodityList()[j].getNumber()*
                purchaseBillVos[i].getCommodityList()[j].getPrice());
                System.out.println(inventoryViewItem.getInventoryMoney());
            }
        }
        for(int i=0;i<purchaseRefundBillVos.length;i++){
            for(int j=0;j<purchaseRefundBillVos[i].getCommodityList().length;j++) {
                inventoryViewItem.setOutOfInventoryAmount(inventoryViewItem.getOutOfInventoryAmount() + purchaseRefundBillVos[i].getCommodityList()[j].getNumber());
                inventoryViewItem.setOutOfInVentoryMoney(inventoryViewItem.getOutOfInVentoryMoney() + purchaseRefundBillVos[i].getCommodityList()[j].getPrice()
                        *purchaseRefundBillVos[i].getCommodityList()[j].getNumber());
            }
        }

        for(int i=0;i<saleBillVos.length;i++){
            for(int j=0;j<saleBillVos[i].getCommodityList().length;j++) {
                inventoryViewItem.setOutOfInventoryAmount(inventoryViewItem.getOutOfInventoryAmount() + saleBillVos[i].getCommodityList()[j].getNumber());
                inventoryViewItem.setOutOfInVentoryMoney(inventoryViewItem.getOutOfInVentoryMoney() + saleBillVos[i].getCommodityList()[j].getPrice()
                        *saleBillVos[i].getCommodityList()[j].getNumber());
                inventoryViewItem.setInSoldPrice(inventoryViewItem.getInSoldPrice()+saleBillVos[i].getCommodityList()[j].getNumber()*
                saleBillVos[i].getCommodityList()[j].getPrice());
                inventoryViewItem.setSolerNum(inventoryViewItem.getSolerNum()+saleBillVos[i].getCommodityList()[j].getNumber());
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
        inventoryViewVo.setItems(inventoryViewItem);
        System.out.println(inventoryViewVo.getItems().getInventoryAmount());
        System.out.println(inventoryViewVo.getItems().getSum());
        return inventoryViewVo;



    }

    /**
     * Gets the inventory snapshot
     * @return The inventory snapshot during that day
     */
    @Override
    public InventoryPictureVo getInventoryPicture() {
        LocalDate start= LocalDate.now();
        LocalDate end=start.plusDays(1);

        Date beginTime= DateHelper.fromLocalDate(start);
        Date endTime=DateHelper.fromLocalDate(end);
        PurchaseBillVo [] purchaseBillVos=purchaseBillBlInfo.queryPurchaseBillVo(new PurchaseBillQueryVo()
                .between("date",beginTime,endTime).and().eq("state",BillState.Activated));
        PurchaseRefundBillVo[] purchaseRefundBillVos=purchaseBillBlInfo.queryPurchaseRefundBillVo(new PurchaseRefundBillQueryVo()
                .between("date",beginTime,endTime).and().eq("state",BillState.Activated));
        SaleBillVo[]saleBillVos=saleBillBlInfo.querySaleBill(new SaleBillQueryVo()
                .between("date",beginTime,endTime).and().eq("state",BillState.Activated));
        SaleRefundBillVo[] saleRefundBillVos=saleBillBlInfo.querySaleRefundBill(new SaleRefundBillQueryVo()
                .between("date",beginTime,endTime).and().eq("state",BillState.Activated));

        ArrayList<String> commoditys=new ArrayList<>();
        for(int i=0;i<purchaseBillVos.length;i++){
            for(int j=0;j<purchaseBillVos[i].getCommodityList().length;j++) {
                if(commoditys.contains(purchaseBillVos[i].getCommodityList()[j].getCommodityId())){

                }else{
                    commoditys.add(purchaseBillVos[i].getCommodityList()[j].getCommodityId());
                }
            }
        }
        for(int i=0;i<purchaseRefundBillVos.length;i++){
            for(int j=0;j<purchaseRefundBillVos[i].getCommodityList().length;j++) {
                if(commoditys.contains(purchaseRefundBillVos[i].getCommodityList()[j].getCommodityId())){

                }else{
                    commoditys.add(purchaseRefundBillVos[i].getCommodityList()[j].getCommodityId());
                }
            }
        }

        for(int i=0;i<saleBillVos.length;i++){
            for(int j=0;j<saleBillVos[i].getCommodityList().length;j++) {
               if(commoditys.contains(saleBillVos[i].getCommodityList()[j].getCommodityId())){
               }else{
                   commoditys.add(saleBillVos[i].getCommodityList()[j].getCommodityId());
               }
            }
        }

        for(int i=0;i<saleRefundBillVos.length;i++){
            for(int j=0;j<saleRefundBillVos[i].getCommodityList().length;j++) {
               if(commoditys.contains(saleRefundBillVos[i].getCommodityList()[j].getCommodityId())){

               }else{
                   commoditys.add(saleRefundBillVos[i].getCommodityList()[j].getCommodityId());
               }
            }
        }
        InventoryPictureItem[] inventoryPictureItems=new InventoryPictureItem[commoditys.size()];

        for(int i=0;i<inventoryPictureItems.length;i++){
            inventoryPictureItems[i]=new InventoryPictureItem(commodityConver.fromVoToPo(
                    commodityInfo.queryCommodity(new CommodityQueryVo().idEq(commoditys.get(i)))[0]
            ));
        }
        InventoryPictureVo inventoryPictureVo=new InventoryPictureVo(new Date(),inventoryPictureItems);
        logService.log(LogSeverity.Success,FormatDateTime.toShortDateString(new Date())+"库存盘点完毕");
        return inventoryPictureVo;
    }

    /**
     * Exports the inventory snapshot to the required file by excel
     * @param path
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage export(String path,String name) {
        InventoryPictureVo operPicture=this.getInventoryPicture();
        String[]output=new String[operPicture.getItems().length+1];
        output[0]="名称-型号-库存数量-库存均价-批次-批号-出厂日期";
        for(int i=0;i<operPicture.getItems().length;i++){
            output[i+1]=operPicture.getItems()[i].getName()+"-"+operPicture.getItems()[i].getType()+"-"+
                    operPicture.getItems()[i].getAmount()+"-"+operPicture.getItems()[i].getPrice()+"-"+
                    operPicture.getItems()[i].getBatch()+"-"+operPicture.getItems()[i].getBatchNo()+"-"+
                    FormatDateTime.toShortDateString(operPicture.getItems()[i].getDate());
        }
        try{
            ResultMessage opResult= ExcelOutput.createExcel(path,output,name);
            if (opResult.isSuccess()) {
                logService.log(LogSeverity.Success, String.format("导出成功"));
            } else {
                logService.log(LogSeverity.Failure, String.format("导出失败"));
            }
        }catch(Exception e){
            logService.log(LogSeverity.Failure, String.format( "导出excel失败，原因位置错误，具体信息是%s。", e.getMessage()));
            throw e;
        }

        return ResultMessage.Success;
    }



}

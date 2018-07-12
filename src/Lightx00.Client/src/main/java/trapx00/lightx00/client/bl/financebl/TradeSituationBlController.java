package trapx00.lightx00.client.bl.financebl;

import trapx00.lightx00.client.bl.inventorybl.InventoryDetailBillInfo;
import trapx00.lightx00.client.bl.inventorybl.InventoryGiftInfo;
import trapx00.lightx00.client.bl.inventorybl.PurchaseBillBlInfo;
import trapx00.lightx00.client.bl.inventorybl.factory.InventoryBillInfoFactory;
import trapx00.lightx00.client.bl.inventorybl.factory.PurchaseBillBlInfoFactory;
import trapx00.lightx00.client.bl.logbl.LogService;
import trapx00.lightx00.client.bl.logbl.factory.LogServiceFactory;
import trapx00.lightx00.client.bl.promotionbl.couponbl.CouponInfo;
import trapx00.lightx00.client.bl.promotionbl.couponbl.factory.CouponFactory;
import trapx00.lightx00.client.bl.salebl.SaleBillBlInfo;
import trapx00.lightx00.client.bl.salebl.factory.SaleBillBlInfoFactory;
import trapx00.lightx00.client.blservice.financeblservice.TradeSituationBlService;
import trapx00.lightx00.client.vo.financestaff.TradeSituationVo;
import trapx00.lightx00.client.vo.inventorystaff.InventoryDetailBillVo;
import trapx00.lightx00.client.vo.inventorystaff.InventoryGiftVo;
import trapx00.lightx00.client.vo.salestaff.PurchaseBillVo;
import trapx00.lightx00.client.vo.salestaff.PurchaseRefundBillVo;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;
import trapx00.lightx00.client.vo.salestaff.SaleRefundBillVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.inventorystaff.InventoryBillType;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;
import trapx00.lightx00.shared.queryvo.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TradeSituationBlController implements TradeSituationBlService {
    private SaleBillBlInfo saleBillBlInfo = SaleBillBlInfoFactory.getSaleBillBlInfo();
    private InventoryDetailBillInfo inventoryDetailBillInfo = InventoryBillInfoFactory.getInventoryDetailBillInfo();
    private InventoryGiftInfo inventoryGiftInfo = InventoryBillInfoFactory.getInventoryGiftInfo();
    private PurchaseBillBlInfo purchaseBillBlInfo = PurchaseBillBlInfoFactory.getPurchaseBillBlInfo();
    private CouponInfo couponInfo = CouponFactory.getCouponInfo();
    private LogService logService = LogServiceFactory.getLogService();

    /**
     * Queries TradeSituation during specified time range
     *
     * @param start start time
     * @param end   end time
     * @return TradeSituation during specified time range
     */
    @Override
    public TradeSituationVo query(Date start, Date end) {
        List<SaleBillVo> saleBillVos = new ArrayList<>(Arrays.asList(saleBillBlInfo.querySaleBill(
            new SaleBillQueryVo()
                .eq("state", BillState.Activated)
                .and()
                .between("date", start, end))));
        List<SaleRefundBillVo> saleRefundBillVos = new ArrayList<>(Arrays.asList(saleBillBlInfo.querySaleRefundBill(
            new SaleRefundBillQueryVo()
                .eq("state", BillState.Activated)
                .and()
                .between("date", start,end))));
        List<InventoryDetailBillVo> overflowBills = new ArrayList<>(Arrays.asList(inventoryDetailBillInfo.queryInventoryWarningBill(
            new InventoryBillQueryVo()
                .eq("state", BillState.Activated)
                .and()
                .eq("inventoryBillType", InventoryBillType.Overflow)
                .and()
                .between("date", start,end)
        )));
        List<InventoryDetailBillVo> lossBills = new ArrayList<>(Arrays.asList(inventoryDetailBillInfo.queryInventoryWarningBill(
            new InventoryBillQueryVo()
                .eq("state", BillState.Activated)
                .and()
                .eq("inventoryBillType", InventoryBillType.Loss)
                .and()
                .between("date", start, end)
        )));
        List<InventoryGiftVo> giftVos = new ArrayList<>(Arrays.asList(inventoryGiftInfo.queryInventoryGiftBill(
            new InventoryGiftQueryVo()
                .eq("state", BillState.Activated)
                .and()
                .between("date", start, end))));

        List<PurchaseBillVo> purchaseBillVos = new ArrayList<>(Arrays.asList(purchaseBillBlInfo.queryPurchaseBillVo(
            new PurchaseBillQueryVo()
                .eq("state", BillState.Activated)
                .and()
                .between("date", start, end))));
        List<PurchaseRefundBillVo> purchaseRefundBillVoList = new ArrayList<>(Arrays.asList(purchaseBillBlInfo.queryPurchaseRefundBillVo(new PurchaseRefundBillQueryVo().between("date", start, end))));

        logService.log(LogSeverity.Info, "查询了经营情况表。");

        return new TradeSituationVo(
            saleIncome(saleBillVos, saleRefundBillVos),
            overflowIncome(overflowBills),
            0,
            differenceOfPurchaseAndRefund(purchaseBillVos, purchaseRefundBillVoList),
            couponInfo.queryUnusedCouponValue(start, end),
            totalPromotion(saleBillVos, saleRefundBillVos),
            saleCost(purchaseBillVos, purchaseRefundBillVoList),
            lossCost(lossBills),
            giveawayCost(giftVos)
        );

    }

    private double totalPromotion(List<SaleBillVo> saleBillVos, List<SaleRefundBillVo> saleRefundBillVos) {
        return saleBillVos.stream().mapToDouble(SaleBillVo::getMinusProfits).sum()
            - saleRefundBillVos.stream().mapToDouble(SaleRefundBillVo::getMinusProfits).sum();
    }

    private double saleIncome(List<SaleBillVo> saleBillVos, List<SaleRefundBillVo> saleRefundBillVos) {
        double saleIncome = saleBillVos.stream()
            .flatMapToDouble(x -> Arrays.stream(x.getCommodityList()).mapToDouble(c -> c.getPrice() * c.getNumber()))
            .sum();

        double refund = saleRefundBillVos.stream()
            .flatMapToDouble(x -> Arrays.stream(x.getCommodityList()).mapToDouble(c -> c.getPrice() * c.getNumber()))
            .sum();

        return saleIncome - refund;
    }

    private double overflowIncome(List<InventoryDetailBillVo> overflowBills) {
        return overflowBills.stream()
            .flatMapToDouble(x -> Arrays.stream(x.getCommodities()).mapToDouble(c -> c.getDelta() * c.getUnitPrice()))
            .sum();
    }

    private double differenceOfPurchaseAndRefund(List<PurchaseBillVo> purchaseBillVos, List<PurchaseRefundBillVo> purchaseRefundBillVos) {
        List<String> refundedPurchaseCommodityId = purchaseRefundBillVos.stream()
            .flatMap(x -> Arrays.stream(x.getCommodityList()).map(CommodityItem::getCommodityId))
            .collect(Collectors.toList());

        double refunded = purchaseRefundBillVos.stream()
            .flatMapToDouble(x -> Arrays.stream(x.getCommodityList()).mapToDouble(c -> c.getPrice() * c.getNumber()))
            .sum();

        double cost = purchaseBillVos.stream()
            .flatMap(x -> Arrays.stream(x.getCommodityList()))
            .filter(x -> refundedPurchaseCommodityId.contains(x.getCommodityId()))
            .mapToDouble(x -> x.getPrice() * x.getNumber())
            .sum();

        return refunded - cost;
    }

    private double lossCost(List<InventoryDetailBillVo> lossBills) {
        double lossCost = lossBills.stream()
            .flatMapToDouble(x -> Arrays.stream(x.getCommodities()).mapToDouble(c -> c.getDelta() * c.getUnitPrice()))
            .sum();
        return lossCost;
    }

    private double saleCost(List<PurchaseBillVo> purchaseBillVos, List<PurchaseRefundBillVo> purchaseRefundBillVos) {
        double purchaseCost = purchaseBillVos.stream()
            .flatMapToDouble(x -> Arrays.stream(x.getCommodityList()).mapToDouble(c -> c.getPrice() * c.getNumber()))
            .sum();

        double purchaseRefundIncome = purchaseRefundBillVos.stream()
            .flatMapToDouble(x -> Arrays.stream(x.getCommodityList()).mapToDouble(c -> c.getPrice() * c.getNumber()))
            .sum();

        return purchaseCost - purchaseRefundIncome;
    }


    private double giveawayCost(List<InventoryGiftVo> inventoryGiftVos) {
        double giveawayCost = inventoryGiftVos.stream()
            .flatMapToDouble(x -> Arrays.stream(x.getGifts()).mapToDouble(c -> c.getPrice() * c.getAmount()))
            .sum();

        return giveawayCost;
    }


    /**
     * Exports a TradeSituation
     *
     * @param situation TradeSituation to be exported
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage export(TradeSituationVo situation) {
        logService.log(LogSeverity.Info, "导出了经营情况表。");
        return ResultMessage.Success;
    }
}

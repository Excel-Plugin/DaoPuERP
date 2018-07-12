package trapx00.lightx00.client.bl.financebl;

import trapx00.lightx00.client.bl.draftbl.DraftableQueryService;
import trapx00.lightx00.client.bl.financebl.factory.SpecificFinanceBillQueryFactory;
import trapx00.lightx00.client.bl.inventorybl.InventoryDetailBillInfo;
import trapx00.lightx00.client.bl.inventorybl.InventoryGiftInfo;
import trapx00.lightx00.client.bl.inventorybl.PurchaseBillBlInfo;
import trapx00.lightx00.client.bl.inventorybl.factory.InventoryBillInfoFactory;
import trapx00.lightx00.client.bl.inventorybl.factory.PurchaseBillBlInfoFactory;
import trapx00.lightx00.client.bl.logbl.LogService;
import trapx00.lightx00.client.bl.logbl.factory.LogServiceFactory;
import trapx00.lightx00.client.bl.salebl.SaleBillBlInfo;
import trapx00.lightx00.client.bl.salebl.factory.SaleBillBlInfoFactory;
import trapx00.lightx00.client.blservice.financeblservice.TradeHistoryBlService;
import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.Draftable;
import trapx00.lightx00.client.vo.financestaff.FinanceBillVo;
import trapx00.lightx00.client.vo.financestaff.TradeHistoryQueryVo;
import trapx00.lightx00.client.vo.financestaff.TradeHistoryVo;
import trapx00.lightx00.shared.exception.database.IdNotExistsException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.bill.BillType;
import trapx00.lightx00.shared.po.financestaff.FinanceBillType;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.queryvo.*;

import java.util.*;

@SuppressWarnings({"unchecked","varargs"})
public class TradeHistoryBlController implements TradeHistoryBlService, FinanceBillInfo, BillInfo, DraftableQueryService {
    private CashBillInfo cashBillInfo = SpecificFinanceBillQueryFactory.getCashbillInfo();
    private PaymentBillInfo paymentBillInfo = SpecificFinanceBillQueryFactory.getPaymentBillInfo();
    private ReceivalBillInfo receivalBillInfo = SpecificFinanceBillQueryFactory.getReceivalBillInfo();
    private SystemSnapshotInfo systemSnapshotInfo = SpecificFinanceBillQueryFactory.getSystemSnapshotInfo();
    private PurchaseBillBlInfo purchaseBillBlInfo = PurchaseBillBlInfoFactory.getPurchaseBillBlInfo();
    private SaleBillBlInfo saleBillBlInfo = SaleBillBlInfoFactory.getSaleBillBlInfo();
    private InventoryDetailBillInfo inventoryDetailBillInfo = InventoryBillInfoFactory.getInventoryDetailBillInfo();
    private InventoryGiftInfo inventoryGiftInfo = InventoryBillInfoFactory.getInventoryGiftInfo();
    private LogService logService = LogServiceFactory.getLogService();

    private HashMap<FinanceBillType, BaseFinanceBillInfo> financeBillTypeBaseFinanceBillInfoHashMap = new HashMap<>();

    public TradeHistoryBlController() {
        financeBillTypeBaseFinanceBillInfoHashMap.put(FinanceBillType.CashBill, cashBillInfo);
        financeBillTypeBaseFinanceBillInfoHashMap.put(FinanceBillType.PaymentBill, paymentBillInfo);
        financeBillTypeBaseFinanceBillInfoHashMap.put(FinanceBillType.ReceivalBill, receivalBillInfo);
        financeBillTypeBaseFinanceBillInfoHashMap.put(FinanceBillType.SystemSnapshot, systemSnapshotInfo);
    }

    /**
     * Queries FinanceBill
     *
     * @param query FinanceBill query condition
     * @return Finance bills that match query condition
     */
    @Override
    public FinanceBillVo[] query(FinanceBillQueryVo query) {
        ArrayList<FinanceBillVo> queryResults = new ArrayList<>();
        for (Map.Entry<FinanceBillType, SpecificFinanceBillQueryVo> entry : query.getHashMap().entrySet()) {
            if (query.getSpecificFinanceBillQueryVo(entry.getKey()) != null) {
                queryResults.addAll(Arrays.asList(financeBillTypeBaseFinanceBillInfoHashMap.get(entry.getKey()).query(entry.getValue())));
            }
        }
        return queryResults.toArray(new FinanceBillVo[queryResults.size()]);
    }

    /**
     * Queries a TradeHistory
     *
     * @param query TradeHistory query condition
     * @return TradeHistory that matches query condition
     */
    @Override
    public TradeHistoryVo query(TradeHistoryQueryVo query) {
        BaseQueryVo baseQueryVo = new BaseQueryVo().ne("state", BillState.Draft);

        if (query.getBillTypes() == null) {
            query.setBillTypes(BillType.values());
        }

        if (query.getStart() != null && query.getEnd() != null) {
            baseQueryVo.and();
            baseQueryVo.between("date",
                query.getStart(),
                query.getEnd());
        }
        if (query.getOperatorIds() != null) {
            baseQueryVo.and();
            baseQueryVo.in("operatorId", query.getOperatorIds());
        }

        if (query.getClientIds() != null && query.getClientIds().length > 0) {
            baseQueryVo.and();
            baseQueryVo.in("clientId", query.getClientIds());
        }

        List<BillVo> queryResult = new ArrayList<>();

        if (query.getBillTypes().length != 0) {
            List<BillType> billTypes = new ArrayList<>(Arrays.asList(query.getBillTypes()));
            if (billTypes.contains(BillType.InventoryBill)) {
                queryResult.addAll(Arrays.asList(inventoryGiftInfo.queryInventoryGiftBill(new InventoryGiftQueryVo(baseQueryVo))));
            }

            if (billTypes.contains(BillType.FinanceBill)) {
                queryResult.addAll(Arrays.asList(cashBillInfo.query(new CashBillQueryVo(baseQueryVo))));
                queryResult.addAll(Arrays.asList(paymentBillInfo.query(new PaymentBillQueryVo(baseQueryVo))));
                queryResult.addAll(Arrays.asList(receivalBillInfo.query(new ReceivalBillQueryVo(baseQueryVo))));
                queryResult.addAll(Arrays.asList(systemSnapshotInfo.query(new SystemSnapshotQueryVo(baseQueryVo))));
            }

            if (billTypes.contains(BillType.SaleBill)) {
                queryResult.addAll(Arrays.asList(saleBillBlInfo.querySaleBill(new SaleBillQueryVo(baseQueryVo))));
                queryResult.addAll(Arrays.asList(saleBillBlInfo.querySaleRefundBill(new SaleRefundBillQueryVo(baseQueryVo))));
            }

            if (billTypes.contains(BillType.StockBill)) {
                queryResult.addAll(Arrays.asList(purchaseBillBlInfo.queryPurchaseBillVo(new PurchaseBillQueryVo(baseQueryVo))));
                queryResult.addAll(Arrays.asList(purchaseBillBlInfo.queryPurchaseRefundBillVo(new PurchaseRefundBillQueryVo(baseQueryVo))));
            }
        }
        return new TradeHistoryVo(queryResult.toArray(new BillVo[queryResult.size()]));

    }

    /**
     * Export a TradeHistory
     *
     * @param tradeHistory TradeHistory to be exported
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage export(TradeHistoryVo tradeHistory) {
        logService.log(LogSeverity.Info, "导出了经营情况表。");
        return ResultMessage.Success;
    }


    /**
     * Queries Bill with id.
     *
     * @param id bill id
     * @return BillVos that has the
     */
    @Override
    public BillVo queryBill(String id) {
        BaseQueryVo queryVo = new BaseQueryVo().idEq(id);
        BillVo[] queryResult = null;
        if (id.startsWith("GIFT")) {
            queryResult = inventoryGiftInfo.queryInventoryGiftBill(new InventoryGiftQueryVo(queryVo));
        } else if (id.startsWith("JKD")) {
            queryResult = inventoryDetailBillInfo.queryInventoryWarningBill(new InventoryBillQueryVo(queryVo));
        } else if (id.startsWith("JHD")) {
            queryResult = purchaseBillBlInfo.queryPurchaseBillVo(new PurchaseBillQueryVo(queryVo));
        } else if (id.startsWith("JHTHD")) {
            queryResult = purchaseBillBlInfo.queryPurchaseRefundBillVo(new PurchaseRefundBillQueryVo(queryVo));
        } else if (id.startsWith("XSD")) {
            queryResult = saleBillBlInfo.querySaleBill(new SaleBillQueryVo(queryVo));
        } else if (id.startsWith("XSTHD")) {
            queryResult = saleBillBlInfo.querySaleRefundBill(new SaleRefundBillQueryVo(queryVo));
        } else if (id.startsWith("SKD")) {
            queryResult = receivalBillInfo.query(new ReceivalBillQueryVo(queryVo));
        } else if (id.startsWith("FKD")) {
            queryResult = paymentBillInfo.query(new PaymentBillQueryVo(queryVo));
        } else if (id.startsWith("XJFYD")) {
            queryResult = cashBillInfo.query(new CashBillQueryVo(queryVo));
        } else if (id.startsWith("QCJZ")) {
            queryResult = systemSnapshotInfo.query(new SystemSnapshotQueryVo(queryVo));
        }
        if (queryResult == null || queryResult.length == 0) {
            throw new IdNotExistsException(id);
        } else {
            return queryResult[0];
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
        return queryBill(id);
    }
}

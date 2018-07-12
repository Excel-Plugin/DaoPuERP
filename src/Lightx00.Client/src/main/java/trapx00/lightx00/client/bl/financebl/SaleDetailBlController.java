package trapx00.lightx00.client.bl.financebl;

import trapx00.lightx00.client.bl.commoditybl.CommodityInfo;
import trapx00.lightx00.client.bl.commoditybl.factory.CommodityInfoFactory;
import trapx00.lightx00.client.bl.logbl.LogService;
import trapx00.lightx00.client.bl.logbl.factory.LogServiceFactory;
import trapx00.lightx00.client.bl.salebl.SaleBillBlInfo;
import trapx00.lightx00.client.bl.salebl.factory.SaleBillBlInfoFactory;
import trapx00.lightx00.client.blservice.financeblservice.SaleDetailBlService;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.financestaff.SaleDetailQueryVo;
import trapx00.lightx00.client.vo.financestaff.SaleDetailVo;
import trapx00.lightx00.client.vo.financestaff.SaleRecordVo;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.client.vo.salestaff.ClientVo;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;
import trapx00.lightx00.shared.queryvo.CommodityQueryVo;
import trapx00.lightx00.shared.queryvo.SaleBillQueryVo;

import java.util.*;
import java.util.stream.Collectors;

public class SaleDetailBlController implements SaleDetailBlService {
    private SaleBillBlInfo saleBillBlInfo = SaleBillBlInfoFactory.getSaleBillBlInfo();
    private CommodityInfo commodityInfo = CommodityInfoFactory.getCommodityInfo();
    private LogService logService = LogServiceFactory.getLogService();

    /**
     * Queries SaleDetail.
     *
     * @param query SaleDetail query conditions
     * @return SaleDetail that matches query conditions
     */
    @Override
    public SaleDetailVo query(SaleDetailQueryVo query) {
        SaleBillQueryVo queryVo = new SaleBillQueryVo();
        queryVo.eq("state", BillState.Activated);

        if (query.getStart() != null && query.getEnd() != null) {
            queryVo.and();
            queryVo.between("date", query.getStart(), query.getEnd());
        }
        if (query.getClients() != null && query.getClients().length > 0) {
            queryVo.and();
            ArrayList<String> clientIds = Arrays.stream(query.getClients()).map(ClientVo::getId).collect(Collectors.toCollection(ArrayList::new));
            queryVo.in("clientId", clientIds);
        }
        if (query.getOperators() != null && query.getOperators().length > 0) {
            queryVo.and();
            ArrayList<String> operatorIds = Arrays.stream(query.getOperators()).map(EmployeeVo::getId).collect(Collectors.toCollection(ArrayList::new));
            queryVo.in("salesmanId", operatorIds);
        }

        SaleBillVo[] queryResult = saleBillBlInfo.querySaleBill(queryVo);

        List<SaleBillVo> filtered = new ArrayList<>();

        List<CommodityVo> concernedCommodities = new ArrayList<>();

        if (query.getCommodityName() != null){
            concernedCommodities.addAll(Arrays.asList(commodityInfo.queryCommodity(new CommodityQueryVo().eq("name", query.getCommodityName()))));
            List<String> commodityIdConditions = concernedCommodities.stream()
                .map(CommodityVo::getId).collect(Collectors.toList());
            for (SaleBillVo saleBillVo : queryResult) {
                if (Arrays.stream(saleBillVo.getCommodityList()).map(CommodityItem::getCommodityId).anyMatch(commodityIdConditions::contains)) {
                    filtered.add(saleBillVo);
                }
            }
        } else {
            filtered.addAll(Arrays.asList(queryResult));
            concernedCommodities.addAll(new ArrayList<>(
                filtered.stream().flatMap(x -> Arrays.stream(x.getCommodityList()))
                .map(CommodityItem::getCommodityId)
                .map(x -> Arrays.stream(commodityInfo.queryCommodity(new CommodityQueryVo().idEq(x))).findFirst().orElse(null))
                    .filter(Objects::nonNull).collect(Collectors.toList())));
        }

        return calculateDetailWithFilteredSaleBillsAndCommodities(filtered, concernedCommodities);
    }

    private SaleDetailVo calculateDetailWithFilteredSaleBillsAndCommodities(List<SaleBillVo> filtered, List<CommodityVo> concernedCommodities) {

        return new SaleDetailVo(filtered.stream()
            .map(x -> {
                List<SaleRecordVo> recordsForDate = new ArrayList<>();
                for (CommodityItem item : x.getCommodityList()) {
                    CommodityVo commodity = concernedCommodities.stream().filter(ec -> ec.getId().equals(item.getCommodityId())).findFirst().orElse(null);
                    assert commodity != null;
                    recordsForDate.add(new SaleRecordVo(x.getDate(), commodity, item.getNumber(), item.getPrice(), item.getNumber() * item.getPrice()));
                }
                return recordsForDate;
            }).flatMap(Collection::stream).toArray(SaleRecordVo[]::new));
    }

    /**
     * Exports a SaleDetail.
     *
     * @param detail SaleDetail to be exported
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage export(SaleDetailVo detail) {
        logService.log(LogSeverity.Info, "导出了销售明细表。");
        return ResultMessage.Success;
    }
}

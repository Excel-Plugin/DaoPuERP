package trapx00.lightx00.client.vo.financestaff;

import trapx00.lightx00.shared.util.DateHelper;

import java.util.ArrayList;
import java.util.List;

public class SaleDetailVo { //销售明细表
    private SaleRecordVo[] records;

    public SaleDetailVo(SaleRecordVo[] records) {
        this.records = records;
    }

    public SaleRecordVo[] getRecords() {
        return records;
    }

    public String[] toExcel() {
        List<String> content = new ArrayList<>();
        content.add("日期-商品名-商品型号-售出数量-单价-总价");
        for(SaleRecordVo record : records) {
            content.add(String.format("%s-%s-%s-%.2f-%.2f-%.2f\n",
                DateHelper.fromDate(record.getDate()),
                record.getCommodityName().getName(),
                record.getCommodityName().getType(),
                record.getAmount(),
                record.getUnitPrice(),
                record.getTotal()
                ));
        }
        return content.toArray(new String[content.size()]);
    }
}

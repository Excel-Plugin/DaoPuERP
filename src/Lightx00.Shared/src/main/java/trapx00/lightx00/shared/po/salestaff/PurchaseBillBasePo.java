package trapx00.lightx00.shared.po.salestaff;

import com.j256.ormlite.field.DatabaseField;
import trapx00.lightx00.shared.po.bill.BillPo;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.bill.BillType;

import java.util.Date;

public class PurchaseBillBasePo extends BillPo {
    @DatabaseField
    private PurchaseBillType purchaseBillType;

    public PurchaseBillBasePo(String id, Date date, BillState state, PurchaseBillType purchaseBillType, String operatorId) {
        super(BillType.InventoryBill, id, date, state, operatorId);
        this.purchaseBillType = purchaseBillType;
    }

    public PurchaseBillBasePo() {
    }

    public PurchaseBillType getPurchaseBillType() {
        return purchaseBillType;
    }
}

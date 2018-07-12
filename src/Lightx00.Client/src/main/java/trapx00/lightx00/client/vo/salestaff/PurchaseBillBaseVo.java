package trapx00.lightx00.client.vo.salestaff;

import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.Reversible;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.bill.BillType;
import trapx00.lightx00.shared.po.salestaff.PurchaseBillType;

import java.util.Date;

public abstract class PurchaseBillBaseVo extends BillVo implements Reversible {
    private PurchaseBillType purchaseBillType;

    public PurchaseBillBaseVo(String id, Date date, BillState state, PurchaseBillType purchaseBillType, String operatorId) {
        super(BillType.InventoryBill, id, date, state, operatorId);
        this.purchaseBillType = purchaseBillType;
    }

    public PurchaseBillType getPurchaseBillType() {
        return purchaseBillType;
    }
}

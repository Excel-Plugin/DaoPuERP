package trapx00.lightx00.client.vo.inventorystaff;

import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.Reversible;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.bill.BillType;
import trapx00.lightx00.shared.po.inventorystaff.InventoryBillType;

import java.util.Date;

public abstract class InventoryBillBaseVo extends BillVo implements Reversible {

    private InventoryBillType inventoryBillType;

    public InventoryBillType getInventoryBillType() {

        return inventoryBillType;

    }
    public void setInventoryBillType(InventoryBillType inventoryBillType) {

        this.inventoryBillType=inventoryBillType;

    }



    public InventoryBillBaseVo(String id, Date date, BillState state, InventoryBillType inventoryBillType,String operatorId) {

        super(BillType.InventoryBill, id, date, state,operatorId);

        this.inventoryBillType = inventoryBillType;

    }

}

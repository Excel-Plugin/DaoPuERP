package trapx00.lightx00.shared.po.inventorystaff;

import com.j256.ormlite.field.DatabaseField;
import trapx00.lightx00.shared.po.bill.BillPo;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.bill.BillType;

import java.util.Date;



public class InventoryBillPo extends BillPo {
    @DatabaseField
     private  InventoryBillType inventoryBillType;

    public InventoryBillPo(String id, Date date, BillState state,
                           InventoryBillType inventoryBillType,String operatorId) {
        super(BillType.InventoryBill, id, date, state,operatorId);
        this.inventoryBillType = inventoryBillType;
    }

    public InventoryBillPo(){

    }

    public InventoryBillType getInventoryBillType() {
        return inventoryBillType;
    }

    public void setInventoryBillType(InventoryBillType inventoryBillType) {
        this.inventoryBillType = inventoryBillType;
    }
}

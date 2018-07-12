package trapx00.lightx00.shared.po.inventorystaff;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.bill.BillType;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;

import java.util.Date;


@DatabaseTable(tableName = "InventoryGiftBill")
public class InventoryGiftPo extends InventoryBillPo {
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private  PromotionCommodity[] gifts;

    public InventoryGiftPo(BillType billType, String id, Date date, BillState state, InventoryBillType inventoryBillType
            , PromotionCommodity[] gifts,String operatorId) {
        super(id,date,state,inventoryBillType,operatorId);
        this.gifts = gifts;
    }

    public InventoryGiftPo(){

    }

    public PromotionCommodity[] getGifts() {
        return gifts;
    }


    public void setGifts(PromotionCommodity[] gifts) {
        this.gifts = gifts;
    }
}

package trapx00.lightx00.client.vo.inventorystaff;

import java.util.Date;

public class InventoryViewVo {
    private Date time;
    private String id;
    private InventoryViewItem items;

    public InventoryViewVo(String id,Date time,InventoryViewItem items) {
        this.id=id;
        this.time=time;
        this.items=items;
    }

    public InventoryViewItem getItems() {
        return items;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setItems(InventoryViewItem items) {
        this.items = items;
    }

    public Date getTime() {
        return time;
    }
}

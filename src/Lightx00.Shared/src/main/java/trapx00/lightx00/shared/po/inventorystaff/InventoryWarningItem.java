package trapx00.lightx00.shared.po.inventorystaff;

import java.io.Serializable;

public class InventoryWarningItem implements Serializable {
    private String id;
    private double delta;
    private double unitPrice;

    public InventoryWarningItem(String id, double delta, double unitPrice) {
        this.id = id;
        this.delta = delta;
        this.unitPrice = unitPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}

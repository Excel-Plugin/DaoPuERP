package suwu.daopuerp.dto.stock;

public class ProductionBillStockItem {
    private String stockId;
    private double stockAmount;
    private String stockProcess;

    public ProductionBillStockItem() {
    }

    public ProductionBillStockItem(String stockId, double stockAmount, String stockProcess) {
        this.stockId = stockId;
        this.stockAmount = stockAmount;
        this.stockProcess = stockProcess;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public double getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(double stockAmount) {
        this.stockAmount = stockAmount;
    }

    public String getStockProcess() {
        return stockProcess;
    }

    public void setStockProcess(String stockProcess) {
        this.stockProcess = stockProcess;
    }
}

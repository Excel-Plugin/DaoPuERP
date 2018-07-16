package suwu.daopuerp.dto.formula;

public class StockItem {
    private String stockId;
    private String stockName;
    private double stockPercent;
    private double stockPrice;

    public StockItem() {
    }

    public StockItem(String stockId, String stockName, double stockPercent, double stockPrice) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.stockPercent = stockPercent;
        this.stockPrice = stockPrice;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public double getStockPercent() {
        return stockPercent;
    }

    public void setStockPercent(double stockPercent) {
        this.stockPercent = stockPercent;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }
}

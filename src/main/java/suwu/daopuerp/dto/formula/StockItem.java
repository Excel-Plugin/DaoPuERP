package suwu.daopuerp.dto.formula;

public class StockItem {
    private String stockId;
    private String stockName;
    private double stockPercent;
    private double stockPrice;
    private String stockProcess;

    public StockItem() {
    }

    public StockItem(String stockId, String stockName, double stockPercent, double stockPrice, String stockProcess) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.stockPercent = stockPercent;
        this.stockPrice = stockPrice;
        this.stockProcess = stockProcess;
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

    public String getStockProcess() {
        return stockProcess;
    }

    public void setStockProcess(String stockProcess) {
        this.stockProcess = stockProcess;
    }
}

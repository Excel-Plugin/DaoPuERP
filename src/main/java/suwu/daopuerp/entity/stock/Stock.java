package suwu.daopuerp.entity.stock;


import suwu.daopuerp.entity.annotation.Column;
import suwu.daopuerp.entity.annotation.Id;
import suwu.daopuerp.entity.annotation.Table;

@Table(name = "Stock")
public class Stock {
    @Id
    @Column(name = "stockId")
    private String stockId;
    @Column(name = "stockName")
    private String stockName;
    @Column(name = "stockPercent")
    private double stockPercent;
    @Column(name = "stockPrice")
    private double stockPrice;
    @Column(name = "stockProcess")
    private String stockProcess;

    public Stock() {
    }

    public Stock(String stockId, String stockName, double stockPercent, double stockPrice, String stockProcess) {
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

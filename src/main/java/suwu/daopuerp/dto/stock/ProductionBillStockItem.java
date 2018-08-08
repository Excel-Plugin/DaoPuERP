package suwu.daopuerp.dto.stock;

import java.io.Serializable;

public class ProductionBillStockItem implements Serializable {
    private String stockCode;
    private double stockAmount;
    private String stockProcess;

    public ProductionBillStockItem() {
    }

    public ProductionBillStockItem(String stockCode, double stockAmount, String stockProcess) {
        this.stockCode = stockCode;
        this.stockAmount = stockAmount;
        this.stockProcess = stockProcess;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
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

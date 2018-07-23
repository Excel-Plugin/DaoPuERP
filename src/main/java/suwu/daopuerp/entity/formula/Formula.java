package suwu.daopuerp.entity.formula;

import suwu.daopuerp.dto.stock.StockItem;
import suwu.daopuerp.entity.Entity;
import suwu.daopuerp.entity.annotation.Column;
import suwu.daopuerp.entity.annotation.EnumTranslate;
import suwu.daopuerp.entity.annotation.Id;
import suwu.daopuerp.entity.annotation.JsonSerialize;
import suwu.daopuerp.publicdata.BillType;

import java.util.List;

public class Formula extends Entity {
    @Id
    @Column(name = "formulaId")
    private String formulaId;
    @EnumTranslate(targetClass = BillType.class)
    @Column(name = "billType")
    private BillType billType;
    @Column(name = "formulaCode")
    private String formulaCode;
    @Column(name = "formulaName")
    private String formulaName;
    @Column(name = "formulaType")
    private String formulaType;
    @JsonSerialize
    @Column(name = "stockItems")
    private List<StockItem> stockItems;
    @Column(name = "stableAttr1")
    private String stableAttr1;
    @Column(name = "stableAttr2")
    private String stableAttr2;

    public Formula() {
    }

    public Formula(String formulaId, BillType billType, String formulaCode, String formulaName, String formulaType, List<StockItem> stockItems, String stableAttr1, String stableAttr2) {
        this.formulaId = formulaId;
        this.billType = billType;
        this.formulaCode = formulaCode;
        this.formulaName = formulaName;
        this.formulaType = formulaType;
        this.stockItems = stockItems;
        this.stableAttr1 = stableAttr1;
        this.stableAttr2 = stableAttr2;
    }

    public String getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(String formulaId) {
        this.formulaId = formulaId;
    }

    public BillType getBillType() {
        return billType;
    }

    public void setBillType(BillType billType) {
        this.billType = billType;
    }

    public String getFormulaCode() {
        return formulaCode;
    }

    public void setFormulaCode(String formulaCode) {
        this.formulaCode = formulaCode;
    }

    public String getFormulaName() {
        return formulaName;
    }

    public void setFormulaName(String formulaName) {
        this.formulaName = formulaName;
    }

    public String getFormulaType() {
        return formulaType;
    }

    public void setFormulaType(String formulaType) {
        this.formulaType = formulaType;
    }

    public List<StockItem> getStockItems() {
        return stockItems;
    }

    public void setStockItems(List<StockItem> stockItems) {
        this.stockItems = stockItems;
    }

    public String getStableAttr1() {
        return stableAttr1;
    }

    public void setStableAttr1(String stableAttr1) {
        this.stableAttr1 = stableAttr1;
    }

    public String getStableAttr2() {
        return stableAttr2;
    }

    public void setStableAttr2(String stableAttr2) {
        this.stableAttr2 = stableAttr2;
    }
}

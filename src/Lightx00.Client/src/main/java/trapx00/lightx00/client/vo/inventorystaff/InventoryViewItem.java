package trapx00.lightx00.client.vo.inventorystaff;

import java.util.Date;

public class InventoryViewItem  {

    private Date operaDate;
    private  double inventoryAmount;//入库数量
    private  double inventoryMoney;//入库金额
    private  double outOfInventoryAmount;//出库数量
    private  double outOfInVentoryMoney;//
    private  double sum;//合计


    public double getSolerNum() {
        return solerNum;
    }

    public void setSolerNum(double solerNum) {
        this.solerNum = solerNum;
    }

    public double getInsellnum() {
        return insellnum;
    }

    public void setInsellnum(double insellnum) {
        this.insellnum = insellnum;
    }

    private  double inSoldPrice;//销售金额
    private double solerNum;//销售数量
    private double insellnum;//进货数量
    private  double outSoldPrice;//进货金额

    public InventoryViewItem (Date time, double inventoryAmount, double inventoryMoney,
                           double outOfInventoryAmount, double outOfInVentoryMoney, double sum,
                           double inSoldPrice, double outSoldPrice) {
        this.operaDate=time;
        this.inventoryAmount = inventoryAmount;
        this.inventoryMoney = inventoryMoney;
        this.outOfInventoryAmount = outOfInventoryAmount;
        this.outOfInVentoryMoney = outOfInVentoryMoney;
        this.sum = sum;
        this.inSoldPrice = inSoldPrice;
        this.outSoldPrice = outSoldPrice;
        this.solerNum=0;
        this.insellnum =0;
    }

    public double getInventoryAmount() {
        return inventoryAmount;
    }

    public void setInventoryAmount(double inventoryAmount) {
        this.inventoryAmount = inventoryAmount;
    }

    public double getInventoryMoney() {
        return inventoryMoney;
    }

    public void setInventoryMoney(double inventoryMoney) {
        this.inventoryMoney = inventoryMoney;
    }

    public double getOutOfInventoryAmount() {
        return outOfInventoryAmount;
    }

    public void setOutOfInventoryAmount(double outOfInventoryAmount) {
        this.outOfInventoryAmount = outOfInventoryAmount;
    }

    public double getOutOfInVentoryMoney() {
        return outOfInVentoryMoney;
    }

    public void setOutOfInVentoryMoney(double outOfInVentoryMoney) {
        this.outOfInVentoryMoney = outOfInVentoryMoney;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getInSoldPrice() {
        return inSoldPrice;
    }

    public void setInSoldPrice(double inSoldPrice) {
        this.inSoldPrice = inSoldPrice;
    }

    public double getOutSoldPrice() {
        return outSoldPrice;
    }

    public void setOutSoldPrice(double outSoldPrice) {
        this.outSoldPrice = outSoldPrice;
    }


}

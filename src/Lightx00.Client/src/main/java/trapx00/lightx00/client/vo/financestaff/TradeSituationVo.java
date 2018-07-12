package trapx00.lightx00.client.vo.financestaff;

public class TradeSituationVo { //经营历程表
    private double saleIncome; //销售收入

    private double overflowIncome; //报溢收
    private double incomeAdjustIncome; //成本调价
    private double differenceOfSaleAndRefundIncome; //进货退货差价
    private double unusedCouponValue; //未使用的代金卷


    private double commodityIncome; //商品类收入 = 报溢收 + 成本调价 + 进货退货差价
    private double promotion; //折让


    private double saleCost; //销售成本

    private double lossCost; //商品报损
    private double giveawayCost; //商品赠出
    private double commodityCost; //商品支出 = 报损 + 赠出
    private double totalCost;


    private double profit;

    public TradeSituationVo(double saleIncome, double overflowIncome, double incomeAdjustIncome, double differenceOfSaleAndRefundIncome, double unusedCouponValue, double promotion, double saleCost, double lossCost, double giveawayCost) {
        this.saleIncome = saleIncome;
        this.overflowIncome = overflowIncome;
        this.incomeAdjustIncome = incomeAdjustIncome;
        this.differenceOfSaleAndRefundIncome = differenceOfSaleAndRefundIncome;
        this.unusedCouponValue = unusedCouponValue;

        this.commodityIncome = overflowIncome + incomeAdjustIncome + differenceOfSaleAndRefundIncome + unusedCouponValue;


        this.promotion = promotion;
        this.saleCost = saleCost;
        this.lossCost = lossCost;
        this.giveawayCost = giveawayCost;



        this.commodityCost = lossCost + giveawayCost;
        this.totalCost = commodityCost + saleCost;



        this.profit = saleIncome + commodityIncome - totalCost;

    }

    public TradeSituationVo() {
    }

    public double getUnusedCouponValue() {
        return unusedCouponValue;
    }

    public void setUnusedCouponValue(double unusedCouponValue) {
        this.unusedCouponValue = unusedCouponValue;
    }

    public double getSaleIncome() {
        return saleIncome;
    }

    public void setSaleIncome(double saleIncome) {
        this.saleIncome = saleIncome;
    }

    public double getOverflowIncome() {
        return overflowIncome;
    }

    public void setOverflowIncome(double overflowIncome) {
        this.overflowIncome = overflowIncome;
    }

    public double getIncomeAdjustIncome() {
        return incomeAdjustIncome;
    }

    public void setIncomeAdjustIncome(double incomeAdjustIncome) {
        this.incomeAdjustIncome = incomeAdjustIncome;
    }

    public double getDifferenceOfSaleAndRefundIncome() {
        return differenceOfSaleAndRefundIncome;
    }

    public void setDifferenceOfSaleAndRefundIncome(double differenceOfSaleAndRefundIncome) {
        this.differenceOfSaleAndRefundIncome = differenceOfSaleAndRefundIncome;
    }

    public double getCommodityIncome() {
        return commodityIncome;
    }

    public void setCommodityIncome(double commodityIncome) {
        this.commodityIncome = commodityIncome;
    }


    public double getPromotion() {
        return promotion;
    }

    public void setPromotion(double promotion) {
        this.promotion = promotion;
    }

    public double getSaleCost() {
        return saleCost;
    }

    public void setSaleCost(double saleCost) {
        this.saleCost = saleCost;
    }

    public double getLossCost() {
        return lossCost;
    }

    public void setLossCost(double lossCost) {
        this.lossCost = lossCost;
    }

    public double getGiveawayCost() {
        return giveawayCost;
    }

    public void setGiveawayCost(double giveawayCost) {
        this.giveawayCost = giveawayCost;
    }

    public double getCommodityCost() {
        return commodityCost;
    }

    public void setCommodityCost(double commodityCost) {
        this.commodityCost = commodityCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}

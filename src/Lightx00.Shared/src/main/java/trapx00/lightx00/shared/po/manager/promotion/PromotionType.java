package trapx00.lightx00.shared.po.manager.promotion;

public enum PromotionType {
    ComSalePromotion("商品降价促销策略"),//组合商品降价促销策略
    TotalPricePromotion("满额类促销策略"),//满额促销策略（赠送礼品或代金券）
    ClientPromotion("客户类促销策略");//客户促销策略（赠送礼品或代金券或价格折让）

    PromotionType(String chinese) {
        this.chinese = chinese;
    }

    private String chinese;

    @Override
    public String toString() {
        return chinese;
    }
}

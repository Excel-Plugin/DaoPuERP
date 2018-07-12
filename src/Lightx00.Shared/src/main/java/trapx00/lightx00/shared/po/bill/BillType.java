package trapx00.lightx00.shared.po.bill;

public enum BillType {
    InventoryBill("库存类单据"),
    StockBill("进货类单据"),
    SaleBill("销售类单据"),
    FinanceBill("财务类单据");

    private String chineseName;

    BillType(String chineseName) {
        this.chineseName = chineseName;
    }


    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    @Override
    public String toString() {
        return chineseName;
    }
}

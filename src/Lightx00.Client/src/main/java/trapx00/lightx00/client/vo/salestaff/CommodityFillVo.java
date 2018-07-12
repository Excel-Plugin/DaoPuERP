package trapx00.lightx00.client.vo.salestaff;

public class CommodityFillVo {

    private double number;

    private String comment;

    public CommodityFillVo(double number, String comment) {
        this.number = number;
        this.comment = comment;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

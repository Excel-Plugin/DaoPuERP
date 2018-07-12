package trapx00.lightx00.client.vo.salestaff;

public class SaleCommodityFillVo {
    private double price;
    private double number;
    private String comment;

    public SaleCommodityFillVo(double price, double number, String comment) {
        this.price = price;
        this.number = number;
        this.comment = comment;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

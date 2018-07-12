package trapx00.lightx00.shared.po.financestaff;

import java.io.Serializable;

public class  Transcation implements Serializable {
    private int accountId;
    private double total;
    private String comment;

    public Transcation(int accountId, double total, String comment) {
        this.accountId = accountId;
        this.total = total;
        this.comment = comment;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Transcation{" +
            "accountId='" + accountId + '\'' +
            ", total=" + total +
            ", comment='" + comment + '\'' +
            '}';
    }
}

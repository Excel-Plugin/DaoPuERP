package trapx00.lightx00.shared.po.financestaff;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

@DatabaseTable(tableName = "BankAccount")
public class BankAccountPo implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private double amount;
    @DatabaseField
    private Date createTime;

    public BankAccountPo(String name, double amount, Date createTime) {
        this.name = name;
        this.amount = amount;
        this.createTime = createTime;
    }

    public BankAccountPo(int id, String name, double amount, Date createTime) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.createTime = createTime;
    }

    public BankAccountPo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BankAccountPo{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", amount=" + amount +
            ", createTime=" + createTime +
            '}';
    }
}

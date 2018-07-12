package trapx00.lightx00.client.vo.financestaff;

import trapx00.lightx00.shared.po.financestaff.BankAccountPo;

import java.util.Date;

public class BankAccountVo {
    private int id;
    private String name;
    private double amount;
    private Date createTime;

    public BankAccountVo(String name, double amount, Date createTime) {
        this.name = name;
        this.amount = amount;
        this.createTime = createTime;
    }

    public BankAccountVo(int id, String name, double amount, Date createTime) {
        this(name, amount, createTime);
        this.id = id;
    }

    public static BankAccountVo fromPo(BankAccountPo bankAccountPo) {
        return new BankAccountVo(bankAccountPo.getId(), bankAccountPo.getName(), bankAccountPo.getAmount(), bankAccountPo.getCreateTime());
    }

    public BankAccountPo toPo() {
        BankAccountPo bankAccountPo = new BankAccountPo(name, amount, createTime);
        bankAccountPo.setId(id);
        return bankAccountPo;
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
}

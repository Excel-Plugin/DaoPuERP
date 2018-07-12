package trapx00.lightx00.client.presentation.bankaccountui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;
import trapx00.lightx00.client.vo.financestaff.BankAccountVo;

import java.util.Date;

public class BankAccountModel extends RecursiveTreeObject<BankAccountModel> {
    private IntegerProperty id;
    private StringProperty name;
    private DoubleProperty balance;
    private ObjectProperty<Date> createTime;

    public BankAccountModel(int id, String name, double balance, Date createTime) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.balance = new SimpleDoubleProperty(balance);
        this.createTime = new SimpleObjectProperty<>(createTime);
    }

    public BankAccountVo toBankAccountVo() {
        return new BankAccountVo(id.getValue(), name.getValue(), balance.getValue(), createTime.getValue());
    }

    public BankAccountModel(BankAccountVo bankAccountVo) {
        this(bankAccountVo.getId(), bankAccountVo.getName(), bankAccountVo.getAmount(), bankAccountVo.getCreateTime());
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getBalance() {
        return balance.get();
    }

    public DoubleProperty balanceProperty() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance.set(balance);
    }

    public Date getCreateTime() {
        return createTime.get();
    }

    public ObjectProperty<Date> createTimeProperty() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime.set(createTime);
    }
}

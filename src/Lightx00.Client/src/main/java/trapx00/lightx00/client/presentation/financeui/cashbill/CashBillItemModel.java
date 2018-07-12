package trapx00.lightx00.client.presentation.financeui.cashbill;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import trapx00.lightx00.shared.po.financestaff.CashBillItem;

public class CashBillItemModel extends RecursiveTreeObject<CashBillItemModel> {
    private StringProperty name;
    private DoubleProperty amount;
    private StringProperty comment;

    public CashBillItemModel(String name, double amount, String comment) {
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleDoubleProperty(amount);
        this.comment = new SimpleStringProperty(comment);
    }

    public CashBillItemModel(CashBillItem cashBillItem) {
        this(cashBillItem.getName(), cashBillItem.getAmount(), cashBillItem.getComment());
    }


    public CashBillItem toCashBillItem() {
        return new CashBillItem(name.getValue(), amount.getValue(), comment.getValue());
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

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }



}

package trapx00.lightx00.client.presentation.financeui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;
import trapx00.lightx00.client.presentation.bankaccountui.BankAccountSelection;
import trapx00.lightx00.client.presentation.bankaccountui.factory.BankAccountUiFactory;
import trapx00.lightx00.client.vo.financestaff.BankAccountVo;
import trapx00.lightx00.shared.po.financestaff.Transcation;

public class TranscationModel extends RecursiveTreeObject<TranscationModel> {
    private ObjectProperty<BankAccountVo> bankAccount;
    private DoubleProperty price;
    private StringProperty comment;


    public TranscationModel(BankAccountVo bankAccount, double price, String comment) {
        this.bankAccount = new SimpleObjectProperty<>(bankAccount);
        this.price = new SimpleDoubleProperty(price);
        this.comment = new SimpleStringProperty(comment);
    }

    public static TranscationModel fromTranscation(Transcation transcation) {
        BankAccountSelection selection = BankAccountUiFactory.getBankAccountSelectionUi();
        return new TranscationModel(selection.queryId(transcation.getAccountId()), transcation.getTotal(), transcation.getComment());
    }

    public Transcation toTranscation() {
        return new Transcation(
            bankAccount.getValue().getId(),
            price.get(),
            comment.get()
        );
    }


    public BankAccountVo getBankAccount() {
        return bankAccount.get();
    }

    public ObjectProperty<BankAccountVo> bankAccountProperty() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountVo bankAccount) {
        this.bankAccount.set(bankAccount);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
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

package trapx00.lightx00.client.presentation.bankaccountui;

import trapx00.lightx00.client.vo.financestaff.BankAccountVo;

import java.util.function.Consumer;

public interface BankAccountSelection {
    void showBankAccountSelectDialog(Consumer<BankAccountVo> callback);

    BankAccountVo queryId(int id);
}
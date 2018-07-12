package trapx00.lightx00.client.bl.bankaccountbl;

import trapx00.lightx00.shared.po.ResultMessage;

public interface BankAccountModificationService {
    /**
     * Modifies a bank account's balance.
     * @param id bank account's id
     * @param delta delta for the balance
     * @return whether the operation is done successfully
     */
    ResultMessage modifyBankAccount(int id, double delta);
}

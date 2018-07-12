package trapx00.lightx00.client.bl.bankaccountbl;

import trapx00.lightx00.client.vo.financestaff.BankAccountVo;
import trapx00.lightx00.shared.queryvo.BankAccountQueryVo;

public interface BankAccountInfo {
    /**
     * Queries bank accounts.
     * @param queryVo bank account.
     * @return bankaccount vos that match condition
     */
    BankAccountVo[] queryBankAccount(BankAccountQueryVo queryVo);
}

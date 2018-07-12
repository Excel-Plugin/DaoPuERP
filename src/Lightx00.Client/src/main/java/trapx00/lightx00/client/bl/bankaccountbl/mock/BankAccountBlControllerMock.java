package trapx00.lightx00.client.bl.bankaccountbl.mock;

import trapx00.lightx00.client.bl.bankaccountbl.BankAccountBlController;
import trapx00.lightx00.client.vo.financestaff.BankAccountVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.BankAccountQueryVo;

import java.util.Date;

public class BankAccountBlControllerMock extends BankAccountBlController {
    /**
     * Modifies a bank account's balance.
     *
     * @param id    bank account's id
     * @param delta delta for the balance
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage modifyBankAccount(int id, double delta) {
        return ResultMessage.Success;
    }

    /**
     * Adds a bank account.
     *
     * @param newAccount New account Vo
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(BankAccountVo newAccount) {
        return ResultMessage.Success;
    }

    /**
     * Modifies a account according to the id.
     *
     * @param newAccount new account vo
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage modify(BankAccountVo newAccount) {
        return ResultMessage.Success;
    }

    /**
     * Queries bank account.
     *
     * @param query BankAccountQueryVo
     * @return whether the operation is done successfully
     */
    @Override
    public BankAccountVo[] query(BankAccountQueryVo query) {
        return new BankAccountVo[] {
                new BankAccountVo(1, "现金1",10.0,new Date())
        };
    }

    /**
     * Deletes a bank account.
     *
     * @param id BankAccountQueryVo
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(int id) {
        return ResultMessage.Success;
    }
}

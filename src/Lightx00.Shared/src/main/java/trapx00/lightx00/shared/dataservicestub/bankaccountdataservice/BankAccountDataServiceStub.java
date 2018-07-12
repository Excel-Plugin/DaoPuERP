package trapx00.lightx00.shared.dataservicestub.bankaccountdataservice;

import trapx00.lightx00.shared.dataservice.bankaccountdataservice.BankAccountDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.financestaff.BankAccountPo;
import trapx00.lightx00.shared.queryvo.BankAccountQueryVo;

import java.util.Date;

public class BankAccountDataServiceStub implements BankAccountDataService {
    /**
     * Queries bank account.
     *
     * @param query query
     * @return Bank accounts that match query
     */
    @Override
    public BankAccountPo[] query(BankAccountQueryVo query) {
        return new BankAccountPo[] {
                new BankAccountPo("123",10,new Date())
        };
    }

    /**
     * Adds a account.
     *
     * @param account new account
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(BankAccountPo account) {
        return ResultMessage.Success;
    }

    /**
     * Modifies an account with the id.
     *
     * @param account new account info
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage modify(BankAccountPo account) {
        return ResultMessage.Success;
    }

    /**
     * Deletes an account.
     *
     * @param id id for the account to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(int id) {
        return ResultMessage.Success;
    }
}

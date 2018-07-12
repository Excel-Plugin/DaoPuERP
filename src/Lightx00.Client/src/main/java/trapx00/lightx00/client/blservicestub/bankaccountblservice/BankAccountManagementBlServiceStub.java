package trapx00.lightx00.client.blservicestub.bankaccountblservice;

import trapx00.lightx00.client.blservice.bankaccountblservice.BankAccountManagementBlService;
import trapx00.lightx00.client.vo.financestaff.BankAccountVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.BankAccountQueryVo;

import java.util.Date;

public class BankAccountManagementBlServiceStub implements BankAccountManagementBlService {
    @Override
    public ResultMessage add(BankAccountVo newAccount) {
        return ResultMessage.Success;
    }

    @Override
    public ResultMessage modify(BankAccountVo newAccount) {
        return ResultMessage.Success;
    }

    @Override
    public BankAccountVo[] query(BankAccountQueryVo query) {
        return new BankAccountVo[] {
               new BankAccountVo(1,"123",123,new Date())
        };
    }

    /**
     * Deletes a bank account.
     *
     * @param id id for the account to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(int id) {
        return ResultMessage.Success;
    }

}

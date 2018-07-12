package trapx00.lightx00.client.bl.bankaccountbl;

import trapx00.lightx00.client.bl.util.PoVoConverter;
import trapx00.lightx00.client.blservice.bankaccountblservice.BankAccountManagementBlService;
import trapx00.lightx00.client.datafactory.bankaccountdataservicefactory.BankAccountDataServiceFactory;
import trapx00.lightx00.client.vo.financestaff.BankAccountVo;
import trapx00.lightx00.shared.dataservice.bankaccountdataservice.BankAccountDataService;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.exception.bl.bankaccountbl.NotEnoughBalanceException;
import trapx00.lightx00.shared.exception.database.IdNotExistsException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.financestaff.BankAccountPo;
import trapx00.lightx00.shared.queryvo.BankAccountQueryVo;

import java.rmi.RemoteException;
import java.util.Arrays;

public class BankAccountBlController implements BankAccountManagementBlService, BankAccountModificationService, BankAccountInfo, PoVoConverter<BankAccountPo, BankAccountVo> {
    private BankAccountDataService dataService = BankAccountDataServiceFactory.getDataService();

    /**
     * Modifies a bank account's balance.
     *
     * @param id    bank account's id
     * @param delta delta for the balance
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage modifyBankAccount(int id, double delta) {
        BankAccountVo[] queryResult = query(new BankAccountQueryVo().idEq(id));
        if (queryResult == null || queryResult.length == 0) {
            throw new IdNotExistsException(String.valueOf(id));
        }
        BankAccountVo newAccount = queryResult[0];
        double previousAmount = newAccount.getAmount();
        if (previousAmount < (-delta)) {
            throw new NotEnoughBalanceException(previousAmount, delta);
        }
        newAccount.setAmount(previousAmount + delta);
        return modify(newAccount);
    }

    /**
     * Adds a bank account.
     *
     * @param newAccount New account Vo
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(BankAccountVo newAccount) {
        BankAccountPo bankAccountPo = newAccount.toPo();
        try {
            return dataService.add(bankAccountPo);
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }

    }

    /**
     * Modifies a account according to the id.
     *
     * @param newAccount new account vo
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage modify(BankAccountVo newAccount) {
        BankAccountPo bankAccountPo = newAccount.toPo();
        try {
            return dataService.modify(bankAccountPo);
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }

    }

    /**
     * Queries bank account.
     *
     * @param query BankAccountQueryVo
     * @return whether the operation is done successfully
     */
    @Override
    public BankAccountVo[] query(BankAccountQueryVo query) {
        try {
            BankAccountPo[] queryResult = dataService.query(query);
            return Arrays.stream(queryResult)
                .map(BankAccountVo::fromPo)
                .toArray(BankAccountVo[]::new);
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Deletes a bank account.
     *
     * @param id BankAccountQueryVo
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(int id) {
        try {
            return dataService.delete(id);
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Queries bank accounts.
     *
     * @param queryVo bank account.
     * @return bankaccount vos that match condition
     */
    @Override
    public BankAccountVo[] queryBankAccount(BankAccountQueryVo queryVo) {
        return query(queryVo);
    }

    /**
     * Convert vo to po.
     *
     * @param vo vo
     * @return po
     */
    @Override
    public BankAccountPo fromVoToPo(BankAccountVo vo) {
        return new BankAccountPo(vo.getId(), vo.getName(), vo.getAmount(), vo.getCreateTime());
    }

    /**
     * Convert po to vo.
     *
     * @param po po
     * @return vo
     */
    @Override
    public BankAccountVo fromPoToVo(BankAccountPo po) {
        return new BankAccountVo(po.getId(), po.getName(), po.getAmount(), po.getCreateTime());
    }
}

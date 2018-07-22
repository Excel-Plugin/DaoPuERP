package suwu.daopuerp.dataservice.factory;

import suwu.daopuerp.data.account.AccountDataServiceImpl;
import suwu.daopuerp.dataservice.account.AccountDataService;

public class AccountDataServiceFactory {
    private static AccountDataService accountDataService = new AccountDataServiceImpl();

    public static AccountDataService getAccountDataService() {
        return accountDataService;
    }
}

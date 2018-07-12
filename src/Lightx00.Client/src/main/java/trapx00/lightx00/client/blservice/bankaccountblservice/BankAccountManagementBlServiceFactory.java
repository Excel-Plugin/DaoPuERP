package trapx00.lightx00.client.blservice.bankaccountblservice;

import trapx00.lightx00.client.bl.bankaccountbl.factory.BankAccountFactory;

public class BankAccountManagementBlServiceFactory {

    public static BankAccountManagementBlService getInstance() {
        return BankAccountFactory.getController();
    }
}

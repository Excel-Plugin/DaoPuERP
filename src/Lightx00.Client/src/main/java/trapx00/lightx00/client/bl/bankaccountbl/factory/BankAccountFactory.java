package trapx00.lightx00.client.bl.bankaccountbl.factory;

import trapx00.lightx00.client.bl.bankaccountbl.BankAccountBlController;
import trapx00.lightx00.client.bl.bankaccountbl.BankAccountInfo;
import trapx00.lightx00.client.bl.bankaccountbl.BankAccountModificationService;
import trapx00.lightx00.client.bl.util.PoVoConverter;
import trapx00.lightx00.client.vo.financestaff.BankAccountVo;
import trapx00.lightx00.shared.po.financestaff.BankAccountPo;

public class BankAccountFactory {
    private static BankAccountBlController controller = new BankAccountBlController();
    private static BankAccountModificationService modificationService = controller;

    public static BankAccountBlController getController() {
        return controller;
    }

    public static BankAccountModificationService getModificationService() {
        return modificationService;
    }

    public static BankAccountInfo getBankAccountInfo() {
        return controller;
    }

    public static PoVoConverter<BankAccountPo, BankAccountVo> getPoVoConverter() {
        return controller;
    }

}

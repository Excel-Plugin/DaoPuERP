package trapx00.lightx00.client.bl.salebl.factory;

import trapx00.lightx00.client.bl.salebl.SaleBillBlInfo;
import trapx00.lightx00.client.bl.salebl.SaleBillBlInfoController;

public class SaleBillBlInfoFactory {
    private static SaleBillBlInfo saleBillBlInfo = new SaleBillBlInfoController();

    public static SaleBillBlInfo getSaleBillBlInfo() {
        return saleBillBlInfo;
    }
}

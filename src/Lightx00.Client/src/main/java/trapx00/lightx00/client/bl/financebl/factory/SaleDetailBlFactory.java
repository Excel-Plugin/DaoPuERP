package trapx00.lightx00.client.bl.financebl.factory;

import trapx00.lightx00.client.bl.financebl.SaleDetailBlController;

public class SaleDetailBlFactory {
    private static SaleDetailBlController controller = new SaleDetailBlController();

    public static SaleDetailBlController getController() {
        return controller;
    }

}

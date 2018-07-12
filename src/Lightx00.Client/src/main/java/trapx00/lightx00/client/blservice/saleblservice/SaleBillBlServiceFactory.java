package trapx00.lightx00.client.blservice.saleblservice;

import trapx00.lightx00.client.bl.salebl.factory.SaleBillBlFactory;

public class SaleBillBlServiceFactory {
    private static SaleBillBlService saleBillBlService = SaleBillBlFactory.getSaleBillBlController();

    public static SaleBillBlService getInstance() {
        return saleBillBlService;
    }
}

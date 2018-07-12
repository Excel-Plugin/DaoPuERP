package trapx00.lightx00.client.bl.commoditybl.factory;

import trapx00.lightx00.client.bl.commoditybl.CommodityBlController;
import trapx00.lightx00.client.bl.commoditybl.CommodityInfo;
import trapx00.lightx00.client.bl.util.PoVoConverter;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.shared.po.inventorystaff.CommodityPo;

public class CommodityInfoFactory {
    private static CommodityInfo commodityInfo =new CommodityBlController();

    private static PoVoConverter<CommodityPo,CommodityVo> commodityPoVoConverter=new CommodityBlController();

    public static CommodityInfo getCommodityInfo(){
        return commodityInfo;
    }

    public  static PoVoConverter<CommodityPo,CommodityVo> getPoVoConverter(){
        return commodityPoVoConverter;
    }

}

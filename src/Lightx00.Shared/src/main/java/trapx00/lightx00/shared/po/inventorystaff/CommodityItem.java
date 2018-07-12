package trapx00.lightx00.shared.po.inventorystaff;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class CommodityItem implements Serializable{
    @DatabaseField
    String id;
    @DatabaseField
    String name;

    public CommodityItem(String id,String name){
        this.id=id;
        this.name=name;
    }

}

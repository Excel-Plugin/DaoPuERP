package trapx00.lightx00.shared.po.inventorystaff;

import java.io.Serializable;

public class CommoditySortItem implements Serializable {
    String id;
    String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public CommoditySortItem(String id,String name){
        this.id=id;
        this.name=name;
    }
}

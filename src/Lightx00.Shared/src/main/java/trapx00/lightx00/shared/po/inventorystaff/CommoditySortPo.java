package trapx00.lightx00.shared.po.inventorystaff;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "CommoditySort")
public class CommoditySortPo implements Serializable{
    //商品分类
    @DatabaseField(id=true)
    private String id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String preId;//父类
    @DatabaseField
    private int leaf;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private CommoditySortItem[] commoditySortItems;


    public CommoditySortPo(){

    }

    public CommoditySortPo(String id, String name, int leaf,
                           String preId, CommoditySortItem[] commoditySortItems) {
        this.leaf=leaf;
        this.id = id;
        this.name = name;
        this.preId = preId;
        this.commoditySortItems=commoditySortItems;
    }


    public CommoditySortItem[] getCommoditySortItems() {
        return commoditySortItems;
    }

    public int getLeaf() {
        return leaf;
    }

    public void setLeaf(int leaf) {
        this.leaf = leaf;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getPreId() {
        return preId;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }



}

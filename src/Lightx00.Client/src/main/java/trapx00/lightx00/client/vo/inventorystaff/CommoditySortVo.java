package trapx00.lightx00.client.vo.inventorystaff;

import trapx00.lightx00.shared.po.inventorystaff.CommoditySortItem;

public class CommoditySortVo {
    //商品分类
    private String id;
    private String name;



    private int leaf;
    private String preId;//爸爸
    private CommoditySortItem[] commoditySortItems;//儿子们

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    private boolean isLeaf;

    //public static CommoditySortPo commoditySortVotoPo(CommoditySortVo  commoditySortVo){
   //     return new CommoditySortPo(commoditySortVo.getId(),commoditySortVo.getName(),commoditySortVo.get)
   // }

    public CommoditySortVo(String id, String name, int leaf,
                           String preId,CommoditySortItem[] commoditySortItems) {
        this.id = id;
        this.name = name;
        this.leaf=leaf;
        this.preId = preId;
        this.commoditySortItems=commoditySortItems;
    }
    public CommoditySortItem[] getCommoditySortItems() {
        return commoditySortItems;
    }

    public void setCommoditySortItems(CommoditySortItem[] commoditySortItems) {
        this.commoditySortItems = commoditySortItems;
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

    public int getLeaf() {
        return leaf;
    }

    public void setLeaf(int leaf) {
        this.leaf = leaf;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setPreId(String preId) {
        this.preId = preId;
    }

}

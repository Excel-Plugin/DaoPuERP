package trapx00.lightx00.shared.po.client;

public enum ClientType {
    Supplier(0),
    Retailer(1);

    private int id;

    ClientType(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        if(id==0){
            return "进货商";
        }
        else{
            return "销售商";
        }
    }
}

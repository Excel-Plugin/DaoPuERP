package trapx00.lightx00.shared.po.financestaff;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.client.ClientPo;
import trapx00.lightx00.shared.po.inventorystaff.CommodityPo;

import java.util.Arrays;
import java.util.Date;

@DatabaseTable(tableName = "SystemSnapShot")
public class  SystemSnapshotPo extends FinanceBillPo { //账
    //存储一次不修改
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private CommodityPo[] commodities;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private ClientPo[] clients;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private BankAccountPo[] bankAccounts;

    public SystemSnapshotPo(String id, Date date, BillState state, CommodityPo[] commodities,
                            ClientPo[] clients, BankAccountPo[] bankAccounts, String operatorId) {
        super(id, date, state, FinanceBillType.SystemSnapshot, operatorId);
        this.commodities = commodities;
        this.clients = clients;
        this.bankAccounts = bankAccounts;
    }

    public SystemSnapshotPo() {
    }

    public CommodityPo[] getCommodities() {
        return commodities;
    }

    public void setCommodities(CommodityPo[] commodities) {
        this.commodities = commodities;
    }

    public ClientPo[] getClients() {
        return clients;
    }

    public void setClients(ClientPo[] clients) {
        this.clients = clients;
    }

    public BankAccountPo[] getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(BankAccountPo[] bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    @Override
    public String toString() {
        return "SystemSnapshotPo{" +
            "commodities=" + Arrays.toString(commodities) +
            ", clients=" + Arrays.toString(clients) +
            ", bankAccounts=" + Arrays.toString(bankAccounts) +
            "} " + super.toString();
    }
}
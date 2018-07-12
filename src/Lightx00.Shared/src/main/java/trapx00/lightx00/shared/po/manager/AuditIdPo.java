package trapx00.lightx00.shared.po.manager;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

@DatabaseTable(tableName = "AuditID")
public class AuditIdPo implements Serializable {
    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private Date approvalTime;

    public AuditIdPo(String id, Date approvalTime) {
        this.id = id;
        this.approvalTime = approvalTime;
    }

    public AuditIdPo() {}

    public String getId() {
        return id;
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

}

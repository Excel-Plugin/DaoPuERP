package trapx00.lightx00.client.vo.manager;

import java.util.Date;

public class AuditIdVo {
    private String id;
    private Date approvalTime;

    public AuditIdVo(String id, Date approvalTime) {
        this.id = id;
        this.approvalTime = approvalTime;
    }

    public String getId() {
        return id;
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

}

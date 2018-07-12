package trapx00.lightx00.shared.po.employee;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "EmployeeFaceIdInfo")
public class EmployeeFaceIdInfo {
    private static final long updateSpanSec = 12 * 60 * 60;

    @DatabaseField(id = true)
    private String employeeId;

    @DatabaseField
    private long lastUpdate;

    @DatabaseField
    private String faceId = "";

    @DatabaseField
    private String imgUri;

    public EmployeeFaceIdInfo(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public EmployeeFaceIdInfo() {
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }


    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
        this.lastUpdate = System.currentTimeMillis();
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public boolean needUpdate() {
        return System.currentTimeMillis() - lastUpdate >= updateSpanSec;
    }

}

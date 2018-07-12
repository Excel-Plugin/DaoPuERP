package trapx00.lightx00.shared.po.draft;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

@DatabaseTable(tableName = "Draft")
public class DraftPo implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private Date saveTime;
    @DatabaseField
    private String saverId;
    @DatabaseField
    private String draftableId;
    @DatabaseField
    private DraftType draftType;

    public DraftPo(Date saveTime, String saverId, String draftableId, DraftType draftType) {
        this.saveTime = saveTime;
        this.saverId = saverId;
        this.draftableId = draftableId;
        this.draftType = draftType;
    }

    public DraftPo() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public DraftType getDraftType() {
        return draftType;
    }

    public void setDraftType(DraftType draftType) {
        this.draftType = draftType;
    }

    public int getId() {
        return id;
    }

    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }

    public String getSaverId() {
        return saverId;
    }

    public void setSaverId(String saverId) {
        this.saverId = saverId;
    }

    public String getDraftableId() {
        return draftableId;
    }

    public void setDraftableId(String draftableId) {
        this.draftableId = draftableId;
    }

    @Override
    public String toString() {
        return "DraftPo{" +
            "id=" + id +
            ", saveTime=" + saveTime +
            ", saverId='" + saverId + '\'' +
            ", draftableId='" + draftableId + '\'' +
            ", draftType=" + draftType +
            '}';
    }
}

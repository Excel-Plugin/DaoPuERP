package trapx00.lightx00.shared.po.notification;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

@DatabaseTable(tableName = "Notification")
public class NotificationPo implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private Date date;
    @DatabaseField
    private String senderId;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private String[] receiverIds;
    @DatabaseField
    private NotificationType type;
    @DatabaseField
    private String content;


    public NotificationPo(Date date, String senderId, String[] receiverIds, NotificationType type, String content) {
        this.date = date;
        this.senderId = senderId;
        this.receiverIds = receiverIds;
        this.type = type;
        this.content = content;
    }

    public NotificationPo(int id, Date date, String senderId, String[] receiverIds, NotificationType type, String content) {
        this.id = id;
        this.date = date;
        this.senderId = senderId;
        this.receiverIds = receiverIds;
        this.type = type;
        this.content = content;
    }

    public NotificationPo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String[] getReceiverIds() {
        return receiverIds;
    }

    public void setReceiverIds(String[] receiverIds) {
        this.receiverIds = receiverIds;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NotificationPo{" +
            "id=" + id +
            ", date=" + date +
            ", senderId='" + senderId + '\'' +
            ", receiverIds=" + Arrays.toString(receiverIds) +
            ", type=" + type +
            ", content='" + content + '\'' +
            '}';
    }
}

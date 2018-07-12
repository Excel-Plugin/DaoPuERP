package trapx00.lightx00.client.presentation.notificationui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import trapx00.lightx00.client.vo.notification.NotificationVo;

public class NotificationModel extends RecursiveTreeObject<NotificationModel> {
    private ObjectProperty<NotificationVo> voObjectProperty;

    public NotificationModel(ObjectProperty<NotificationVo> voObjectProperty) {
        this.voObjectProperty = voObjectProperty;
    }

    public NotificationModel(NotificationVo vo) {
        this.voObjectProperty = new SimpleObjectProperty<>(vo);
    }

    public NotificationVo getVoObjectProperty() {
        return voObjectProperty.get();
    }

    public ObjectProperty<NotificationVo> voObjectPropertyProperty() {
        return voObjectProperty;
    }

    public void setVoObjectProperty(NotificationVo voObjectProperty) {
        this.voObjectProperty.set(voObjectProperty);
    }
}

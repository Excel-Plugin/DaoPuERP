package trapx00.lightx00.client.presentation.helpui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import trapx00.lightx00.client.vo.log.LogVo;

public class LogSelectionItemModel extends RecursiveTreeObject<LogSelectionItemModel> {
    public ObjectProperty<LogVo> logVoObjectProperty;

    public LogSelectionItemModel(LogVo logVoObjectProperty) {
        this.logVoObjectProperty = new SimpleObjectProperty<>(logVoObjectProperty);
    }

    public LogVo getLogVoObjectProperty() {
        return logVoObjectProperty.get();
    }

    public ObjectProperty<LogVo> logVoObjectPropertyProperty() {
        return logVoObjectProperty;
    }

    public void setLogVoObjectProperty(LogVo logVoObjectProperty) {
        this.logVoObjectProperty.set(logVoObjectProperty);
    }
}

package trapx00.lightx00.client.presentation.logui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;
import trapx00.lightx00.client.vo.log.LogVo;
import trapx00.lightx00.shared.po.log.LogSeverity;

import java.util.Date;

public class LogTableItemModel extends RecursiveTreeObject<LogTableItemModel> {
    private IntegerProperty id;
    private ObjectProperty<Date> date;
    private ObjectProperty<LogSeverity> severity;
    private StringProperty content;

    public LogTableItemModel(LogVo logVo) {
        this.date = new SimpleObjectProperty<>(logVo.getDate());
        this.severity = new SimpleObjectProperty<>(logVo.getSeverity());
        this.content = new SimpleStringProperty(logVo.getContent());
        this.id = new SimpleIntegerProperty(logVo.getId());
    }

    public LogVo toLogVo() {
        return new LogVo(id.get(), date.get(), severity.get(), content.get());
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public Date getDate() {
        return date.get();
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    public LogSeverity getSeverity() {
        return severity.get();
    }

    public ObjectProperty<LogSeverity> severityProperty() {
        return severity;
    }

    public void setSeverity(LogSeverity severity) {
        this.severity.set(severity);
    }

    public String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }
}

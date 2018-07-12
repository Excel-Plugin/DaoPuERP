package trapx00.lightx00.client.vo.log;

import trapx00.lightx00.client.presentation.helpui.ContentDisplayUi;
import trapx00.lightx00.client.presentation.logui.LogDetailUi;
import trapx00.lightx00.shared.po.log.LogSeverity;

import java.util.Date;

public class LogVo {
    private int id;
    private Date date;
    private LogSeverity severity;
    private String content;

    public LogVo(Date date, LogSeverity severity, String content) {
        this.date = date;
        this.severity = severity;
        this.content = content;
    }

    public LogVo(int id, Date date, LogSeverity severity, String content) {
        this.id = id;
        this.date = date;
        this.severity = severity;
        this.content = content;
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

    public LogSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(LogSeverity severity) {
        this.severity = severity;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ContentDisplayUi<LogVo> contentDisplayUi() {
        return new LogDetailUi();
    }
}


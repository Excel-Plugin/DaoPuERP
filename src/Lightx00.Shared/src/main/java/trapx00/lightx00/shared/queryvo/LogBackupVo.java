package trapx00.lightx00.shared.queryvo;

import java.io.Serializable;

public class LogBackupVo implements Serializable {
    String date;
    String url;

    public LogBackupVo(String date, String url) {
        this.date = date;
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

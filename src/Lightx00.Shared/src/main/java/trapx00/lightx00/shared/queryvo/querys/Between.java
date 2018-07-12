package trapx00.lightx00.shared.queryvo.querys;

import com.j256.ormlite.stmt.Where;

import java.io.Serializable;
import java.sql.SQLException;

public class Between<Po, PK, Obj extends Serializable> extends BaseQuery<Po, PK> {

    private String columnName;
    private Obj low;
    private Obj high;

    public Between(String columnName, Obj low, Obj high) {
        this.columnName = columnName;
        this.low = low;
        this.high = high;
    }

    @Override
    public void execute(Where<Po, PK> previous) throws SQLException {
        previous.between(columnName, low, high);
    }
}

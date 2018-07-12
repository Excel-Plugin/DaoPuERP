package trapx00.lightx00.shared.queryvo.querys;

import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;

public class IsNull<Po, PK> extends BaseQuery<Po, PK> {
    private String columnName;

    public String getColumnName() {
        return columnName;
    }

    public IsNull(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public void execute(Where<Po, PK> previous) throws SQLException {
        previous.isNull(columnName);
    }
}

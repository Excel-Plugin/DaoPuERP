package trapx00.lightx00.shared.queryvo.querys;

import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;

public class IsNotNull<Po, PK> extends IsNull<Po, PK> {

    public IsNotNull(String columnName) {
        super(columnName);
    }

    @Override
    public void execute(Where<Po, PK> previous) throws SQLException {
        previous.isNotNull(getColumnName());
    }
}

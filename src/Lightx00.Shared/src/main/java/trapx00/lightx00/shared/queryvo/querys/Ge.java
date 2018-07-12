package trapx00.lightx00.shared.queryvo.querys;

import com.j256.ormlite.stmt.Where;

import java.io.Serializable;
import java.sql.SQLException;

public class Ge<Po, PK, Obj extends Serializable> extends OneArgQuery<Po, PK, Obj> {

    public Ge(String columnName, Obj value) {
        super(columnName, value);
    }

    @Override
    public void execute(Where<Po, PK> previous) throws SQLException {
        previous.ge(columnName, value);
    }
}

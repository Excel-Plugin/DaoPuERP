package trapx00.lightx00.shared.queryvo.querys;

import com.j256.ormlite.stmt.Where;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotIn<Po, PK, Obj extends Serializable> extends In<Po, PK, Obj> {


    public NotIn(String columnName, ArrayList<Obj> objects) {
        super(columnName, objects);
    }

    @Override
    public void execute(Where<Po, PK> previous) throws SQLException {
        previous.notIn(getColumnName(), getObjects());
    }
}

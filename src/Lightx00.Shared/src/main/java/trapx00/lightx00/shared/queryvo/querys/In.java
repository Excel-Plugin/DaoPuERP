package trapx00.lightx00.shared.queryvo.querys;

import com.j256.ormlite.stmt.Where;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public class In<Po, PK, Obj extends Serializable> extends BaseQuery<Po,PK> {
    private String columnName;
    private ArrayList<Obj> objects;

    public In(String columnName, ArrayList<Obj> objects) {
        this.columnName = columnName;
        this.objects = objects;
    }

    public String getColumnName() {
        return columnName;
    }

    public ArrayList<Obj> getObjects() {
        return objects;
    }

    @Override
    public void execute(Where<Po, PK> previous) throws SQLException {
        previous.in(columnName, objects);
    }
}

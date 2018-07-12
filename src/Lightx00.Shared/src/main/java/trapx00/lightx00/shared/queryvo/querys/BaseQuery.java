package trapx00.lightx00.shared.queryvo.querys;

import com.j256.ormlite.stmt.Where;

import java.io.Serializable;
import java.sql.SQLException;

public abstract class BaseQuery<Po, PK> implements Serializable {
    public abstract void execute(Where<Po, PK> previous) throws SQLException;
}

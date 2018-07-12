package trapx00.lightx00.shared.queryvo.querys;

import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;

public class Or<Po,PK> extends BaseQuery<Po, PK> {
    @Override
    public void execute(Where<Po, PK> previous) throws SQLException {
        previous.or();
    }
}

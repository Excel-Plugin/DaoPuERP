package trapx00.lightx00.shared.queryvo.querys;

import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;

public class IdEq<Po, PK> extends BaseQuery<Po, PK> {
    private PK id;

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }

    public IdEq(PK id) {
        this.id = id;
    }

    @Override
    public void execute(Where<Po, PK> previous) throws SQLException {
        previous.idEq(id);
    }
}

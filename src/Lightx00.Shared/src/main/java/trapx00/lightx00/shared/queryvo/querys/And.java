package trapx00.lightx00.shared.queryvo.querys;

import com.j256.ormlite.stmt.Where;

public class And<Po, PK> extends BaseQuery<Po, PK> {

    @Override
    public void execute(Where<Po, PK> previous) {
        previous.and();
    }
}

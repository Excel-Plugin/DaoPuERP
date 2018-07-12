package trapx00.lightx00.shared.queryvo.querys;

import java.io.Serializable;

public abstract class OneArgQuery<Po, PK, Obj extends Serializable> extends BaseQuery<Po, PK> {
    protected String columnName;
    protected Obj value;

    public OneArgQuery(String columnName, Obj value) {
        this.columnName = columnName;
        this.value = value;
    }
}

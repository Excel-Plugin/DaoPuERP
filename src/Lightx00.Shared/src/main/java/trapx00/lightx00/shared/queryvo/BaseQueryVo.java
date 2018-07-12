package trapx00.lightx00.shared.queryvo;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.Where;
import trapx00.lightx00.shared.queryvo.querys.*;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class BaseQueryVo<Po,PK, T extends BaseQueryVo> implements Serializable {
    private ArrayList<BaseQuery<Po,PK>> queries = new ArrayList<>();

    public T idEq(PK id) {
        queries.add(new IdEq<>(id));
        return (T) this;
    }

    public T and() {
        queries.add(new And<>());
        return (T) this;
    }

    public <Obj extends Serializable> T between(String columnName, Obj low, Obj high) {
        queries.add(new Between<>(columnName, low, high));
        return (T) this;
    }

    public <Obj extends Serializable> T eq(String columnName, Obj value) {
        queries.add(new Eq<>(columnName, value));
        return (T) this;
    }

    public <Obj extends Serializable> T ge(String columnName, Obj value) {
        queries.add(new Ge<>(columnName, value));
        return (T) this;
    }

    public <Obj extends Serializable> T gt(String columnName, Obj value) {
        queries.add(new Gt<>(columnName, value));
        return (T) this;
    }

    public <Obj extends Serializable>  T in(String columnName, ArrayList<Obj> objects) {
        queries.add(new In<>(columnName, objects));
        return (T) this;
    }

    public <Obj extends Serializable>  T in(String columnName, Obj[] objects) {
        ArrayList<Obj> list = Arrays.stream(objects).collect(Collectors.toCollection(ArrayList::new));
        queries.add(new In<>(columnName, list));
        return (T) this;
    }

    public T isNotNull(String columnName) {
        queries.add(new IsNotNull<>(columnName));
        return (T) this;
    }

    public T isNull(String columnName) {
        queries.add(new IsNull<>(columnName));
        return (T) this;
    }

    public <Obj extends Serializable> T le(String columnName, Obj value) {
        queries.add(new Le<>(columnName, value));
        return (T) this;
    }

    public <Obj extends Serializable> T like(String columnName, Obj value) {
        queries.add(new Like<>(columnName, value));
        return (T) this;
    }

    public <Obj extends Serializable> T lt(String columnName, Obj value) {
        queries.add(new Lt<>(columnName, value));
        return (T) this;
    }

    public <Obj extends Serializable> T ne(String columnName, Obj value) {
        queries.add(new Ne<>(columnName, value));
        return (T) this;
    }

    public T not() {
        queries.add(new Not<>());
        return (T) this;
    }

    public <Obj extends Serializable> T notIn(String columnName, ArrayList<Obj> objects) {
        queries.add(new NotIn<>(columnName,objects));
        return (T) this;
    }

    public T or() {
        queries.add(new Or<>());
        return (T) this;
    }

    public int conditionCount() {
        return queries.size();
    }

    public BaseQueryVo() {

    }

    public BaseQueryVo(BaseQueryVo another) {
        this.queries = another.queries;
    }

    protected void setQueries(ArrayList<BaseQuery<Po, PK>> queries) {
        this.queries = queries;
    }

    protected ArrayList<BaseQuery<Po, PK>> getQueries() {
        return queries;
    }

    public PreparedQuery<Po> prepareQuery(Dao<Po, PK> dao) throws SQLException {
        if (queries.size() == 0) {
            return dao.queryBuilder().prepare();
        }
        Where<Po, PK> whereClause = dao.queryBuilder().where();
        for (BaseQuery<Po, PK> query : queries) {
            query.execute(whereClause);
        }
        return whereClause.prepare();
    }
}

package trapx00.lightx00.shared.exception.database;

import java.sql.SQLException;

public class DbSqlException extends DatabaseException {
    private SQLException actualException;

    public DbSqlException(SQLException actualException) {
        this.actualException = actualException;
    }

    public SQLException getActualException() {
        return actualException;
    }
}

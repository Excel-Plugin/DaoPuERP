package trapx00.lightx00.shared.exception.database;

public class IdExistsException extends DatabaseException {
    private String id;

    public String getId() {
        return id;
    }

    public IdExistsException(String id) {
        this.id = id;
    }
}

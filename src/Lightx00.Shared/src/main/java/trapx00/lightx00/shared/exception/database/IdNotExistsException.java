package trapx00.lightx00.shared.exception.database;

public class IdNotExistsException extends DatabaseException {
    private String id;

    public IdNotExistsException(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getMessage() {
        return "unknown id: " + id;
    }
}

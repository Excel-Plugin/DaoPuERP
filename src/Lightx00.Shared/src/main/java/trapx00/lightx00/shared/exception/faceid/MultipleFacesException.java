package trapx00.lightx00.shared.exception.faceid;

public class MultipleFacesException extends RuntimeException {
    private int numOfFaces;

    public int getNumOfFaces() {
        return numOfFaces;
    }

    public MultipleFacesException(int numOfFaces) {
        this.numOfFaces = numOfFaces;
    }

    @Override
    public String toString() {
        return "MultipleFacesException{" +
            "numOfFaces=" + numOfFaces +
            "} " + super.toString();
    }
}

package parser;

public class SyntaxException extends RuntimeException {
    private String expected;
    private int position;

    public SyntaxException(String expected, int atPosition) {
        super(expected + " expected at position " + atPosition);
        this.expected = expected;
        this.position = atPosition;
    }

    public String getExpected() {
        return expected;
    }

    public int getPosition() {
        return position;
    }
}

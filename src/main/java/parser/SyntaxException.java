/*!! Parser */

/*!
SyntaxException
===============
*/

/*!- Header */
package parser;

/*! A `SyntaxException` is raised by the function in the `Parser` if an expected was not found at the current
position. */
public class SyntaxException extends RuntimeException {
    public final String expected;
    public final int position;

    public SyntaxException(String expected, int atPosition) {
        super(expected + " expected at position " + atPosition);
        this.expected = expected;
        this.position = atPosition;
    }
}

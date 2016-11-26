/*!! Interpreter */

/*!
Visitor
=======

The interpreter (and the evaluator) are performing structural recursion on the inductive data structure `Program` and
`Expression`, respectively. We want to define one interpreter function that behaves differently depending on the
argument. In functional languages this is done with
[Pattern Matching](https://de.wikipedia.org/wiki/Pattern_Matching#Programmierung)
and in Java this is typically implemented using the
[Visitor Pattern](https://en.wikipedia.org/wiki/Visitor_pattern).
*/

/*- Header */
package interpreter;


/*! This `Visitor` is implemented using
[Reflection](https://en.wikipedia.org/wiki/Reflection_(computer_programming)). That is kind of cheating, but simplifies
the classical Visitor pattern a lot. Of course the performance is bad, but performance is not an issue in this little
demonstration and actually there are a lot of other performance issues as well. */
public abstract class Visitor<T> {
    @SuppressWarnings("unchecked")
    public T visit(Object object) {
        try {
            /*! Get the name of the class of the given object, search for a method with this name in self and call it. */
            return (T) this.getClass().getMethod("visit" + object.getClass().getSimpleName(), object.getClass()).invoke(this, object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
package interpreter;

public abstract class Visitor<T> {
    @SuppressWarnings("unchecked")
    public T visit(Object object) {
        try {
            return (T) this.getClass().getMethod("visit" + object.getClass().getSimpleName(), object.getClass()).invoke(this, object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
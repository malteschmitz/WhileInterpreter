package interpreter;

import expression.*;
import java.lang.reflect.Method;

public abstract class ExpressionVisitor<T> {
    public T visit(Expression expression) {
        String methodName = "visit" + expression.getClass().getSimpleName();
        Method method;
        try {
            method = this.getClass().getMethod(methodName, expression.getClass());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        Object result;
        try {
            result = method.invoke(this, expression);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return (T) result;
    }

    public abstract T visitAddition(Addition addition);
    public abstract T visitSubtraction(Subtraction subtraction);
    public abstract T visitInt(Int integer);
    public abstract T visitIdentifier(Identifier identifier);
}
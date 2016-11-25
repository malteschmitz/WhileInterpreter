package interpreter;

import program.*;

public abstract class ProgramVisitor {
    public void visit(Program program) {
        try {
            this.getClass().getMethod("visit" + program.getClass().getSimpleName(), program.getClass()).invoke(this, program);
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public abstract void visitAssignment(Assignment assignment);
    public abstract void visitComposition(Composition composition);
    public abstract void visitConditional(Conditional conditional);
    public abstract void visitLoop(Loop loop);
}
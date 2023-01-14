package domain.Statements;

import domain.ADTs.IDictionary;
import domain.ADTs.IStack;
import domain.Exceptions.ADTException;
import domain.Exceptions.MyException;
import domain.Expressions.Expression;
import domain.ProgramState.ProgramState;
import domain.Types.BoolType;
import domain.Types.Type;
import domain.Values.BoolValue;
import domain.Values.Value;

public class IfStatement implements IStatement {
    private Expression exp;
    private IStatement thenS;
    private IStatement elseS;

    public IfStatement(Expression exp, IStatement th, IStatement el) {
        this.exp = exp;
        this.thenS = th;
        this.elseS = el;
    }

    @Override
    public String toString() {
        return "(IF (" + this.exp.toString() + ") THEN(" + this.thenS.toString() + ") ELSE (" + this.elseS.toString() + ")";
    }

    public ProgramState execute(ProgramState state) throws MyException, ADTException {
        Value result = this.exp.evaluate(state.getSymbolTable(), state.getHeap());
        IStack<IStatement> stack = state.getExecutionStack();
        if (result.getType().equals(new BoolType())) {
            BoolValue res = (BoolValue) result;
            if (res.getValue()) {
                stack.push(this.thenS);
            } else {
                stack.push(this.elseS);
            }
            return null;
        } else throw new MyException("Conditional expression is not Boolean.");
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typExp = exp.typecheck(typeEnv);
        if (typExp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.clone());
            elseS.typecheck(typeEnv.clone());
            return typeEnv;
        } else {
            throw new MyException("The condition of IF does not have the type bool.");
        }
    }

    @Override
    public IStatement clone() {
        return new IfStatement(this.exp.clone(), this.thenS.clone(), this.elseS.clone());
    }
}

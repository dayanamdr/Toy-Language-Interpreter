package domain.Statements;

import domain.ADTs.IDictionary;
import domain.ADTs.IList;
import domain.ADTs.MyList;
import domain.Exceptions.ADTException;
import domain.Exceptions.MyException;
import domain.Expressions.Expression;
import domain.ProgramState.ProgramState;
import domain.Types.Type;
import domain.Values.Value;

public class PrintStatement implements IStatement {
    Expression exp;

    public PrintStatement(Expression exp) {
        this.exp = exp;
    }

    public String toString() {
        return "print(" + this.exp.toString() + ")";
    }

    public ProgramState execute(ProgramState state) throws MyException, ADTException {
        IList<Value> out = state.getOutput();
        Value result = this.exp.evaluate(state.getSymbolTable(), state.getHeap());
        out.add(result);
        state.setOutput(out);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStatement clone() {
        return new PrintStatement(this.exp.clone());
    }
}

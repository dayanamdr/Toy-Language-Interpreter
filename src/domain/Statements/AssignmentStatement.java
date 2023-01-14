package domain.Statements;

import domain.ADTs.IDictionary;
import domain.Exceptions.ADTException;
import domain.Exceptions.MyException;
//import Model.Expressions.Expression;
import domain.Expressions.Expression;
import domain.ProgramState.ProgramState;
import domain.Types.Type;
import domain.Values.Value;

public class AssignmentStatement implements IStatement {
    private String key; // the id
    private Expression exp;

    public AssignmentStatement(String id, Expression exp) {
        this.key = id;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return this.key + "=" + this.exp.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, ADTException {
        IDictionary<String, Value> table = state.getSymbolTable();
        if (table.isDefined(this.key)) {
            Value result = this.exp.evaluate(table, state.getHeap());
            if (result.getType().equals(table.lookup(this.key).getType()))
                table.update(this.key, result);
            else throw new MyException("Type of expression and type of variable do not match.");
        } else throw new MyException("Variable id is not declared.");
        state.setSymbolTable(table);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(key);
        Type typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(typeExp)) {
            return typeEnv;
        } else throw new MyException("Assignment Statement: right hand side and left hand side have different types.");
    }

    @Override
    public IStatement clone() {
        return new AssignmentStatement(this.key, this.exp.clone());
    }
}

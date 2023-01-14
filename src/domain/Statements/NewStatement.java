package domain.Statements;

import domain.ADTs.IDictionary;
import domain.ADTs.IHeap;
import domain.Exceptions.ADTException;
import domain.Exceptions.MyException;
import domain.Expressions.Expression;
import domain.ProgramState.ProgramState;
import domain.Types.ReferenceType;
import domain.Types.Type;
import domain.Values.ReferenceValue;
import domain.Values.Value;

public class NewStatement implements IStatement {
    private final String varName;
    private final Expression expression;

    public NewStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ADTException, MyException {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap heap = state.getHeap();
        if (!symbolTable.isDefined(varName)) {
            throw new MyException(String.format("%s not in symTable", varName));
        }
        Value varValue = symbolTable.lookup(varName);
        if (!(varValue.getType() instanceof ReferenceType)) {
            throw new MyException(String.format("%s in not of RefType", varName));
        }
        Value evaluated = expression.evaluate(symbolTable, heap);
        Type locationType = ((ReferenceValue)varValue).getLocationType();
        if (!locationType.equals(evaluated.getType())) {
            throw new MyException((String.format("%s not of %s", varName, evaluated.getType())));
        }
        int newPosition = heap.add(evaluated);
        symbolTable.put(varName, new ReferenceValue(newPosition, locationType));
        state.setSymbolTable(symbolTable);
        state.setHeap(heap);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typVar = typeEnv.lookup(varName);
        Type typExp = expression.typecheck(typeEnv);
        if (typVar.equals(new ReferenceType(typExp))) {
            return typeEnv;
        } else {
            throw new MyException("New statement: right hand side and left and side have different types.");
        }
    }

    @Override
    public IStatement clone() {
        return new NewStatement(varName, expression.clone());
    }

    @Override
    public String toString() {
        return String.format("New(%s, %s)", varName, expression);
    }
}

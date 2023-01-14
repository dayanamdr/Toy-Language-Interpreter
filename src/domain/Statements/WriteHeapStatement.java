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

public class WriteHeapStatement implements IStatement {
    private final String varName;
    private final Expression expression;

    public WriteHeapStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, ADTException {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap heap = state.getHeap();
        if (!symbolTable.isDefined(varName)) {
            throw new MyException(String.format("%s not present in the symTable", varName));
        }
        Value value = symbolTable.lookup(varName);
        if (!(value instanceof ReferenceValue)) {
            throw new MyException(String.format("%s not of RefType", value));
        }
        ReferenceValue refValue = (ReferenceValue) value;
        Value evaluated = expression.evaluate(symbolTable, heap);
        if (!evaluated.getType().equals(refValue.getLocationType())) {
            throw new MyException(String.format("%s not of %s", evaluated, refValue.getLocationType()));
        }
        heap.update(refValue.getAddress(), evaluated);
        state.setHeap(heap);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(varName).equals(new ReferenceType(expression.typecheck(typeEnv)))) {
            return typeEnv;
        } else {
            throw new MyException("WriteHeap: right hand side and left hand side have different types.");
        }
    }

    @Override
    public IStatement clone() {
        return new WriteHeapStatement(varName, expression.clone());
    }

    @Override
    public String toString() {
        return String.format("WriteHeap(%s, %s)", varName, expression);
    }
}

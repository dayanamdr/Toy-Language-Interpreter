package domain.ADTs;

import domain.Exceptions.MyException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements IStack {
    private Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<T>();
    }

    @Override
    public T pop() throws MyException {
        if (this.stack.isEmpty()) {
            throw new MyException("Empty stack");
        }
        return stack.pop();
    }

    @Override
    public String toString() {
        String result = "{";
        for (T el : this.stack) {
            result += el.toString() + "|";
        }
        result += "}";
        return result.toString();
    }

    @Override
    public void push(Object value) {
        stack.push((T) value);
    }

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

    @Override
    public List<T> getReverse() {
        List<T> l = (List<T>) Arrays.asList(this.stack.toArray());
        Collections.reverse(l);
        return l;
    }

    @Override
    public List getValues() {
        return stack.subList(0, stack.size());
    }

    @Override
    public int size() {
        return stack.size();
    }
}

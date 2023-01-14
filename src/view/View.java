package view;

import controller.Controller;
import domain.ADTs.*;
import domain.Exceptions.ADTException;
import domain.Exceptions.InterpreterException;
import domain.Exceptions.MyException;
import domain.Expressions.ArithmeticExpression;
import domain.Expressions.ValueExpression;
import domain.Expressions.VariableExpression;
import domain.ProgramState.ProgramState;
import domain.Statements.*;
import domain.Types.BoolType;
import domain.Types.IntType;
import domain.Values.BoolValue;
import domain.Values.IntValue;
import repository.IRepository;
import repository.Repository;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class View {
    public View() {
    }

    public void executeProgram(Controller controller) throws IOException, ADTException, InterruptedException, InterpreterException, InterpreterException {
        controller.allSteps();
    }

    public void printMenu() {
        System.out.println("Choose a statement:");
        System.out.println("0 - exit");
        System.out.println("1- int v; v=2; Print(v)");
        System.out.println("2- int a; int b; a=2+3*5;b=a+1; Print(b)");
        System.out.println("3- bool a; int v; a=true;(If a Then v=2 Else v=3); Print(v)");
    }

    public void startView() {
        boolean done = false;
        while (!done) {
            try {
                this.printMenu();
                Scanner readOption = new Scanner(System.in);
                int option = readOption.nextInt();
                if (option == 0) {
                    done = true;
                } else if (option == 1) {
                    runProgram1();
                } else if (option == 2) {
                    runProgram2();
                } else if (option == 3) {
                    runProgram3();
                } else {
                    System.out.println("Invalid input!");
                }
            } catch (Exception message) {
                System.err.println(message);
            }
        }
    }

    public void runProgram1() {
        IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("var", new IntType()),
                new CompoundStatement(new AssignmentStatement("var", new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("var"))));
        this.runStatement(ex1);
    }

    public void runProgram2() {
        IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a",
                                new ArithmeticExpression('+', new ValueExpression(new IntValue(2)),
                                        new ArithmeticExpression('*', new ValueExpression(new IntValue(3)),
                                                new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b",
                                        new ArithmeticExpression('+', new VariableExpression("a"),
                                                new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
        this.runStatement(ex2);
    }

    public void runProgram3() {
        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));
        this.runStatement(ex3);
    }

    public void runStatement(IStatement statementToBeRun) {
      try {

          IDictionary map = new MyDictionary();
          IStack stack = new MyStack();
          IList output = new MyList();
          IDictionary fileTable = new MyDictionary();
          IHeap heap = new MyHeap();


          ProgramState prgState = new ProgramState(stack, map, output, statementToBeRun, fileTable, heap);
          IRepository repo = new Repository(prgState,"text.in");
          repo.addProgram(prgState);

          Controller controller = new Controller(repo);

          System.out.println("Do you want to display the steps?[Y/n]");
          Scanner readOption = new Scanner(System.in);
          String option = readOption.next();
          controller.setDisplayFlag(Objects.equals(option, "Y"));

          this.executeProgram(controller);
          if (Objects.equals(option, "n")) {
              System.out.println(prgState);
          }
      } catch (Exception message) {
          System.err.println(message);
        }
    }
}

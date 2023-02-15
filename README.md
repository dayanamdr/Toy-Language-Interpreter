# Toy-Language-Interpreter
Custom toy language interpreter built on Java

# Used concepts
* Layered architecture
* Encapsulation, streams, interfaces
* MVC pattern
* JavaFX for GUI

# Variables Types
* Bool
* Int
* String
* Reference

# Instructions
* arithmetical expressions
* logical expressions
* relational expressions
* variable declaration
* variable assignment
* if
* while
* printing
* file opening, reading and closing
* fork (multi-threading)
* heap allocation, reading and writing

# Functionalities
* Storing local variables in Symbol Tables
* Storing instructions in Execution Stacks
* Storing BufferedReader objects into a File Table which is used for file operations
* Storing printing output in an Output Table
* While forking, a new program state is being created with a unique ID (each state has a unique ID, a symbol table and an execution stack)
* Shared heap across all the states created by a program - allocation, reading, writing and garbage collector

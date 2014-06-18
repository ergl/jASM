# jARM: Java-built ARM executer, debugger and IDE #

### How? ###
This started as a project for the Programming Theory course at Complutense University of Madrid, 2014.

The original idea was to do a virtual machine for executing a pseudo-asm language, but I quickly thought of implementing all of the ARM instruction set and making a quick and easy to use development environment for my Hardware Funtamentals course. 

[//]: # (This is a comment ✓)

### Ideas ###
- Print CPU status to log file
- Add support for Registers
    - Add new Instructions that execute on the registers
- Add displacement jumps
- Add CPU reset support (stack.flush, memory.flush program.rest, executionManager.reset)
- Breakpoint support
    - Specify in cpu:runProgram that breakpoints exist
    - Add clause to executionManager:onNextInstruction to detect breakpoint pc
- Undo support (one undo step - for now)

### TODO ###
- Reimplementing the internal structure of the virtual machine following the current ARM standard
- Implementing the full ARM instruction set
- Recognizing different comments and arm delimiters
- Redesign the GUI and make it more intuitive and easy to use
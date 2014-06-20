# jARM: Java-built ARM executer, debugger and IDE #

### How? ###
This started as a project for the Programming Theory course at Complutense University of Madrid, 2014.

The original idea was to do a virtual machine for executing a pseudo-asm language, but I quickly thought of implementing all of the ARM instruction set and making a quick and easy to use development environment for my Hardware Funtamentals course. 

[//]: # (This is a comment ✓)

### Ideas ###
- ✓ Print CPU status to log file. Use [-l, --log] to enable.
- ✓ Add CPU reset support. Use the 'reset' command on interactive mode or press the 'Reset' button while on windowed mode.
- ✓ Breakpoint support. Double click on an instruction to add a breakpoint. Double click again to remove it.
- Add support for Registers
    - Add new Instructions that execute on the registers
- Add displacement jumps
- Undo support (one undo step - for now)

### TODO ###
- Reimplementing the internal structure of the virtual machine following the current ARM standard
- Implementing the full ARM instruction set
- Recognizing different comments and arm delimiters
- Redesign the GUI and make it more intuitive and easy to use

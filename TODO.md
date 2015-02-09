## TODO

- Rethink breakpoint flow

    * Expected: Step instruction doesn't trigger the breakpoint, in either mode (batch, interactive or visual)
    * Happens: Step instruction triggers the breakpoint
    * Expected: When running the program, reaching a breakpoint pauses the CPU, in visual and in interactive
    * Happens: In interactive, the CPU continues to execute the same instruction over and over. In visual, I have no idea

- Breakpoint support for CLI interface
- Reset buttons to correct state after a breakpoint is reached
- Add displacement jumps


### Major
- Test current build and find any possible bugs
- Recognizing comments and delimiters  

### Minor
- Add Undo instruction
- Redesign UI

## Bugs (use the issue tracker)
- The log file is not written on error, why?

    * Enable log, interactive
    * Enable a breakpoint on any instruction: `b X`
    * Run the program: `run`
    * Infinite loop happens, prints a lot of status info to the console
    * Force quit
    * Check log file and realize it is empty

- CPU doesn't care if breakpoint is reached on CLI mode
- On visual mode, after a breakpoint is reached, one has to Pause, and the Run again the program. Solution: Reset buttons to correct state after a breakpoint is reached


## Instructions to support
- ### Arithmetics
    Structure: `<op>{<cond>}{S}Rd,Rn,Operand2`
    
    Operations:

    * `ADC op1 + op2 + carry`
    * `SBC op1 - op2 + carry - 1`
    * `RSB op2 - op1`  
    * `RSC op2 - op1 + carry - 1`  
    
    Usage:

    * `ADD r0,r1,r2`  
    * `SUBGT r3,r3,#1` 

- ### Comparisons  
    Structure: `<op>{<cond>}Rn,Operand2`  
    
    Operations:

    * `CMP op1 - op2`  
    * `CMN op1 + op2`  
    * `TST op1 && op2`  
    * `TEQ op1 EOR op2` 
     
    Usage:

    - `CMP r0,r1`  
    - `TSTEQ r2,#5`

 
- ### Logical Ops  
    Structure:  `<op>{<cond>}{S}Rd,Rn,Operand2`  
    
    Operations:

    * `AND op1 && op2`  
    * `EOR op1 EOR op2`  
    * `ORR op1 || op2`  
    * `BIC op1 && !op2`  

    Usage:

    - `AND r0,r1,r2`  
    - `BICEQ r2,r3,#7`  
    - `EORS r1,r3,r0`  


- ### Data Movement  
     Structure: `<op>{<cond>}{S}Rd,Operand2`  
     
     Operations:

     * `MOV op2`  
     * `MVN !op2`  
     
     Usage:

     - `MOV r0,r1`  
     - `MOVS r2,#10`  
     - `MVNEQ r1,#0`  

- ### Barrel Shift

    (See documentation)  
    
    * Logical Shift Left: `LSL #X`
    * Logical Shift Right: `LSR #X`
    * Arithmetic Shift Right: `ASR #X`
    * Rotate Right: `ROR #X`
    
- ### ...

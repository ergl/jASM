## To do
### Major
- Test current build and find any possible bugs
- Recognizing comments and delimiters  

### Minor
- Add Undo instruction
- Redesign UI

## Bugs
No bugs found as of yet. For future bugs refer to the issue tracker

## Instructions to support
- ### Arithmetics
    Structure: `<op>{<cond>}{S}Rd,Rn,Operand2`  
    * `ADD op1 + op2`
    * `ADC op1 + op2 + carry`
    * `SUB op1 - op2`
    * `SBC op1 - op2 + carry - 1`
    * `RSB op2 - op1`
    * `RSC op2 - op1 + carry - 1`  
    
    Usage:
        - ADD r0,r1,r2
        - SUBGT r3,r3,#1
    
- ### Comparisons 
    Structure: `<op>{<cond>}Rn,Operand2`  
    * `CMP op1 - op2 [nw]` (no write)
    * `CMN op1 + op2 [nw]`
    * `TST op1 && op2 [nw]`
    * `TEQ op1 EOR op2 [nw]`
    
    Usage:
        - CMP r0,r1
        - TSTEQ r2,#5
- ### Logical Ops
    Structure:  `<op>{<cond>}{S}Rd,Rn,Operand2`  
    * `AND op1 && op2`
    * `EOR op1 EOR op2`
    * `ORR op1 || op2`
    * `BIC op1 && !op2`
    
    Usage:
        - AND r0,r1,r2
        - BICEQ r2,r3,#7 
        - EORS r1,r3,r0

- ### Data Movement
     Structure: `<op>{<cond>}{S}Rd,Operand2`  
     * `MOV op2`
     * `MVN !op2`
  
     Usage:
         - MOV r0,r1
         - MOVS r2,#10
         - MVNEQ r1,#0
                  
- ### Multiplication
    * `MUL{<cond>}{S}Rd,Rm,Rs : Rd = Rm * Rs`
    * `MLA{<cond>}{S}Rd,Rm,Rs : Rd = (Rm*Rs) + Rn [Multiply Accumulate]`
    
    Restrictions: 
        - Rd and Rm cannot be the same register (can be avoided by swapping Rm and Rs around)
        - Cannot use PC as operand
- ### Load / Store
    (See documentation)  
    Obs: Must move data into register before using them.
    
- ### Bargit rel Shift
    (See documentation)  
    * Logical Shift Left: `LSL #X`
    * Logical Shift Right: `LSR #X`
    * Arithmetic Shift Right: `ASR #X`
    * Rotate Right: `ROR #X`
    
- ### ...

## Futures

Please put all desired changes or new features below:

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

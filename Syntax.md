## Syntax

The syntax looks like most ASM instructions and arguments.

Please note that functions, sections or global variables are not supported as of yet; and:

- Comments are marked with the `;` character.
- When using the REPL, all programs must end with the `END` instruction, although it is not needed when loading code from a file.
- ???

Example:

```asm
	push 7
	push 9
	add
    store 0
	halt

```

Would store `16` in the `0` memory address.
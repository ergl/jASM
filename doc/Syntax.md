## Syntax

The syntax looks like most ASM instructions and arguments.

Please note that functions, sections or global variables are not supported as of yet; and:

- Comments are marked with the `;` character.
- When using the REPL, all programs must end with the `END` instruction, although it is not needed when loading code from a file.
- ???

Example:

```asm
    push 10
    move R0
    push 43
    out
    loopdec r0 2
    halt
```

Would print out 10 `+` characters (ascii code 43).

To find out more, please check out the [examples](../examples/)

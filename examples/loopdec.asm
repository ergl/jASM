; This program will print out 10 `+` characters (ascii code 43)
push 10
move R0
push 43
out
loopdec r0 2 ; goes to 2 (push 43) while the value in R0 is greater than zero
halt

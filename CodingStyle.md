# jARM coding style

Most of this guide is taken from the [Linux Kernel coding style guide](https://git.kernel.org/cgit/linux/kernel/git/torvalds/linux.git/tree/Documentation/CodingStyle?id=refs/tags/v3.19-rc6) and
the [Google Java style guide](http://google-styleguide.googlecode.com/svn/trunk/javaguide.html).

In case of any doubt, refer to any of the above, as they are a lot more complete than this one. For java-specific issues, refer to
the Google style guide.

## Source file basics

### File name

The source file name consists of the case-sensitive name of the top-level class it contains, plus the `.java` extension.

### File encoding and line endings

File encoding must be UTF-8. Unix-style line endings must be used.  
If you are on Windows, remember to set up Git to strip Windows-style endings
when pulling and pushing.

### Whitespace and indentation

Indentation should be 4 spaces. No tabs.
No horizontal alignment in assignations or declarations.

_Bad_:
```java
    int x    = something;          // Please don't
    String y = "A curious string"; // do this
```

If you need to indent a method or function call, it should be double indented.

Don't put multiple statements and / or asignments on a single line.
Strip all trailing whitespace at the end of lines.

### Breaking long lines and strings

Line length will be a maximum of 100 characters, with an usual length of 80.
Statements longer than 80-100 columns will be broken into sensible chunks, unless
exceeding the above limit significanlty decreases readability and does not hide 
information. However, never break user-visible strings such as `println`.

### Braces

All non-emtpy code blocks will have the opening brace trailing the block statement:

```java
    if (x is true) {
        we do y
        and z
    }

    switch (foo) {
        case bar:
            return baz;
        case zzz:
            something;
            // comment fall through
        default:
            return xyz;
    }
```

Note that the closing brace is empty on a line of its own, except in the cases where it is followed by a continueation of the same statement, ie a `while` or
`else`, like this:

```java
    do {
        body
    } while (cond);
```

and

```java
    if (x) {
        ...
    } else if (y) {
        ...
    } else {
        ...
    }
```

Do not unnecessarily use braces where a single statement will do:

```java
    if (cond)
        action();
```

This does not apply if only one branch is a single statement; in the latter case
use braces in both branches:

```java
    if (cond) {
        foo
        bar
    } else {
        baz
    }
```

However, be concise on empty blocks

```java
    void doNothing() {}
```

### Spaces

Use a space after:

`if, switch, case, for, do, while`

but not after funtion names.

Don't add spaces around (inside) parenthesized expressions.
Use spaces around most binary and ternary operatos, but not after unary
operators.

No space before or after the postfix and prefix increment & decrement unary operators.

Use spaces around the lambda `->` arrow.

### Naming

#### Class names
Class names should follow the `UpperCamelCase` notation.

#### Method and variable names
Every variable and function should be all lowercase if it consists of a single
word or the `lowerCamelCase` notation if it consists of multiple words.

#### Constant names
Constant names should use `CONSTANT_CASE`: all uppercase, with words separated
by underscores. 

Every constant is a static final field, but not all final fields are constants.
Consider this before following the above convention.

### Annotations

Don't use IDE-specific annotations like `@serial`. If you use them, remember to
strip them before pushing your changes.

## Programming Practices

### Function length

We could debate for ages here. Be sure that it doesn't exceed 30-50 lines.
Apply exceptions with sound judgement.

### @Override: always used

### Caught exceptions: not ignored

When it truly is appropiate to take no actions whatsoever in a catch block, the
reason is justified and explained in a comment:

```java
    try {
        int i = Integer.parseInt(response);
        return handleNumericResponse(i);
    } catch (NumberFormatException ok) {
        // it's not numeric; that's fine, just continue
    }
    return handleTextResponse(response);
```

__Exception:__ In tests, a caught exception may be ignored without comment _if_
it is named `expected`. The following is a very common idiom for ensuring that
the method under test _does_ throw an exception of the expected type:

```java
    try {
        emptyStack.pop();
        fail();
    } catch (NoSuchElementException expected) {
    }
```

### Static memebers: qualified using class

```java
    Foo aFoo = ...;
    Foo.aStaticMethod(); // good
    aFoo.aStaticMethod(); // bad
    somethingThatYieldsAFoo().aStaticMethod(); // very bad
```

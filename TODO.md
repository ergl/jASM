## TODO

- Check cli arguments conflicts
- Add displacement jumps
- Look into NullPointerException errors when parsing some instructions
- Why use custom class vs Observer / Observable? -> To extend functionality -> TODO: Extend watcher functionality
- Tests

- Breakpoint support for CLI interface
- Reset buttons to correct state after a breakpoint is reached
- Rethink breakpoint flow:

    * Expected: Step instruction doesn't trigger the breakpoint, in either mode (batch, interactive or visual)
    * Happens: Step instruction triggers the breakpoint
    * Expected: When running the program, reaching a breakpoint pauses the CPU, in visual and in interactive
    * Happens: In interactive, the CPU continues to execute the same instruction over and over. In visual, exception is raised

- Set up Ant build


### Major

- Refactor TwoParamInst, OneParamInst, etc
- Test current build and find any possible bugs
- Add instruction to switch to a function
- Figure out a way to return variables from a function (shared memory? stack? register?)

### Minor
- Add Undo instruction
- Redesign UI
- Support more instructions (see Future.md)

## Bugs

Please note: for future bug reports, please use the issue tracker.

- CPU doesn't care if breakpoint is reached on CLI mode
- On visual mode, after a breakpoint is reached, one has to Pause, and the Run again the program. Solution:
Reset buttons to correct state after a breakpoint is reached
- The log file is not written on error, why? -> why: when a breakpoint exception is raised, the cpu skips the log() line

    ```java
        if (...) {
            throw new BreakpointException(...);
        }
        // ...
        log();
        // ...
        catch (BreakpointException e) {
            // ...
        }
    ```

    * Enable log, interactive
    * Enable a breakpoint on any instruction: `b X`
    * Run the program: `run`
    * Infinite loop happens, prints a lot of status info to the console
    * Force quit
    * Check log file and realize it is empty

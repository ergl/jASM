package mv.ins.instList;

import commons.Commons;
import commons.exceptions.RecoverableException;
import commons.exceptions.UnrecoverableException;
import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.cpu.RegisterBank;
import mv.ins.Instruction;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

/**
 * Describes all instructions that get a parameter (store, load...)
 *
 * @author Borja
 */
// TODO: Refactor into separate classes -> OneParam to NoParam, TwoParam to OneParam, and come up with new classes for instructions with more than one parameter
public abstract class TwoParamInst implements Instruction {

    protected String orden;
    protected String register = null;
    protected Integer param = null;
    protected Integer auxParam = null;

    public TwoParamInst(String orden) {
        this.orden = orden;
    }

    public TwoParamInst(String orden, int param) {
        this.orden = orden;
        this.param = param;
    }

    public TwoParamInst(String orden, int param, int param1) {
        this.orden = orden;
        this.param = param;
        this.auxParam = param1;
    }

    public TwoParamInst(String orden, String register, int param) {
        this.orden = orden;
        this.register = register;
        this.param = param;
    }

    public TwoParamInst(String orden, String register, int param, int param1) {
        this.orden = orden;
        this.register = register;
        this.param = param;
        this.auxParam = param1;
    }

    public abstract void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers)
            throws UnrecoverableException, RecoverableException;

    protected abstract Instruction getInst(int param);

    public Instruction parse(String input) {
        input = input.trim();
        String[] tokens = input.split("\\ ");

        if (tokens.length != 2) {
            return null;
        }

        if (Commons.isInteger(tokens[1])) {
            int param = Integer.parseInt(tokens[1]);
            if (tokens[0].equalsIgnoreCase(orden)) {
                return getInst(param);
            }
        }

        return null;
    }

    @Override
    public String toString() {
        if (this.register == null) {
            return this.orden.toUpperCase() + " " + this.param.toString();
        }
        if (this.auxParam == null) {
            return this.orden.toUpperCase() + " " + this.register + this.param.toString();
        }
        return this.orden.toUpperCase() + " " + this.register + this.param + " " + this.auxParam;
    }
}

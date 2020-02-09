package com.passaro.mypallene.visitor;

import com.passaro.mypallene.error.ErrorHandler;
import com.passaro.mypallene.nodekind.NodeKind;
import com.passaro.mypallene.nodetype.FunctionNodeType;
import com.passaro.mypallene.semantic.SymbolTable;
import com.passaro.mypallene.semantic.SymbolTableRecord;
import com.passaro.mypallene.syntax.*;
import com.passaro.mypallene.syntax.expression.*;
import com.passaro.mypallene.syntax.expression.binaryexpr.arithop.DivOp;
import com.passaro.mypallene.syntax.expression.binaryexpr.arithop.MinusOp;
import com.passaro.mypallene.syntax.expression.binaryexpr.arithop.PlusOp;
import com.passaro.mypallene.syntax.expression.binaryexpr.arithop.TimesOp;
import com.passaro.mypallene.syntax.expression.binaryexpr.relop.*;
import com.passaro.mypallene.syntax.expression.unaryexpr.NotExpression;
import com.passaro.mypallene.syntax.expression.unaryexpr.SharpExpression;
import com.passaro.mypallene.syntax.expression.unaryexpr.UMinusExpression;
import com.passaro.mypallene.syntax.function.ComplexDefFun;
import com.passaro.mypallene.syntax.function.SimpleDefFun;
import com.passaro.mypallene.syntax.statement.*;
import com.passaro.mypallene.syntax.typedenoter.ArrayTypeDenoter;
import com.passaro.mypallene.syntax.typedenoter.FunctionTypeDenoter;
import com.passaro.mypallene.syntax.typedenoter.PrimitiveTypeDenoter;

import java.util.List;

public class PreScopeCheckerVisitor implements Visitor<Boolean, SymbolTable> {

    private ErrorHandler errorHandler;
    private int mainCounter = 0;

    public PreScopeCheckerVisitor(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    private boolean checkContext(List<? extends AstNode> nodes, SymbolTable arg) {
        if (nodes.isEmpty()) {
            return true;
        } else {
            return nodes.stream().allMatch(node -> node.accept(this, arg));
        }
    }

    @Override
    public Boolean visit(Program program, SymbolTable arg) {
        arg.enterScope();
        boolean isProgramSafe = this.checkContext(program.getFunctions(), arg);
        arg.exitScope();
        if (!errorHandler.haveErrors()) {
            if (this.mainCounter >= 2) {
                this.errorHandler.reportError("Too many main method, only one can be present", program);
                return false;
            } else if (this.mainCounter == 0) {
                this.errorHandler.reportError("You must specify one main method", program);
                return false;
            }
        }
        return isProgramSafe;

    }

    @Override
    public Boolean visit(Global global, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(SimpleDefFun simpleDefFun, SymbolTable arg) {
        if (simpleDefFun.getVariable().getName().equalsIgnoreCase("main")) {
            this.mainCounter++;
        }
        boolean isSimpleFunctionSafe = simpleDefFun.getVariable().accept(this, arg);
        if (!isSimpleFunctionSafe) {
            this.errorHandler.reportYetDefined(simpleDefFun);
        } else {
            arg.addEntry(simpleDefFun.getVariable().getName(), new SymbolTableRecord(simpleDefFun.getTypeDenoter().typeFactory(), NodeKind.FUNCTION));
        }
        return isSimpleFunctionSafe;

    }

    @Override
    public Boolean visit(ComplexDefFun complexDefFun, SymbolTable arg) {
        if (complexDefFun.getVariable().getName().equalsIgnoreCase("main")) {
            this.mainCounter++;
        }
        boolean isComplexFunctionSafe = complexDefFun.getVariable().accept(this, arg);
        if (!isComplexFunctionSafe) {
            this.errorHandler.reportYetDefined(complexDefFun);
        } else {
            arg.addEntry(complexDefFun.getVariable().getName(),
                    new SymbolTableRecord(new FunctionNodeType(complexDefFun.domain(), complexDefFun.codomain()), NodeKind.FUNCTION));
        }
        return isComplexFunctionSafe;
    }

    @Override
    public Boolean visit(ParDecl parDecl, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(VarDecl varDecl, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(VarInitValue varInitValue, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(PrimitiveTypeDenoter primitiveTypeDenoter, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(ArrayTypeDenoter arrayTypeDenoter, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(FunctionTypeDenoter functionTypeDenoter, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(WhileStatement whileStatement, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(IfThenStatement ifThenStatement, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(IfThenElseStatement ifThenElseStatements, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(ForStatement forStatement, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(LocalStatement localStatement, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(AssignStatement assignStatement, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(ArrayElementStatement arrayElementStatement, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(FunctionCallStatement functionCallStatement, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(ReadStatement readStatement, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(WriteStatement writeStatement, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(ReturnStatement returnStatement, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(FloatConst floatConst, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(StringConst stringConst, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(IntegerConst integerConst, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(ArrayConst emptyArrayExpression, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(ArrayRead readArrayExpression, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(FunctionCall functionCallExpression, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(PlusOp plusOp, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(MinusOp minusOp, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(TimesOp timesOp, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(DivOp divOp, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(AndOp andOp, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(OrOp orOp, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(GtOp gtOp, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(GeOp geOp, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(LtOp ltOp, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(LeOp leOp, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(EqOp eqOp, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(NeOp neOp, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(UMinusExpression uMinusExpression, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(NilConst nilConst, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(Id id, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(BooleanConst booleanConst, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(NotExpression notExpression, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(SharpExpression sharpExpression, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(NopStatement nopStatement, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(Variable variable, SymbolTable arg) {
        return !arg.probe(variable.getName());
    }
}

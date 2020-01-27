package visitor;

import error.ErrorHandler;
import nodetype.NodeType;
import nodetype.PrimitiveNodeType;
import semantic.SymbolTable;
import syntax.*;
import syntax.expression.*;
import syntax.expression.binaryexpr.arithop.DivOp;
import syntax.expression.binaryexpr.arithop.MinusOp;
import syntax.expression.binaryexpr.arithop.PlusOp;
import syntax.expression.binaryexpr.arithop.TimesOp;
import syntax.expression.binaryexpr.relop.*;
import syntax.expression.unaryexpr.NotExpression;
import syntax.expression.unaryexpr.SharpExpression;
import syntax.expression.unaryexpr.UMinusExpression;
import syntax.function.ComplexDefFun;
import syntax.function.SimpleDefFun;
import syntax.statement.*;
import syntax.typedenoter.ArrayTypeDenoter;
import syntax.typedenoter.FunctionTypeDenoter;
import syntax.typedenoter.PrimitiveTypeDenoter;

import java.util.function.Consumer;

public class TypeCheckerVisitor implements Visitor<NodeType, SymbolTable> {
    private ErrorHandler errorHandler;

    public TypeCheckerVisitor(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    private Consumer<? super AstNode> typeCheck(SymbolTable arg) {
        return (AstNode node) -> node.accept(this, arg);
    }


    @Override
    public NodeType visit(Program program, SymbolTable arg) {
        arg.enterScope();
        program.getGlobal().getVarDecls().forEach(this.typeCheck(arg));
        program.getFunctions().forEach(this.typeCheck(arg));
        return PrimitiveNodeType.NULL;
    }

    @Override
    public NodeType visit(Global global, SymbolTable arg) {
        global.getVarDecls().forEach(this.typeCheck(arg));
        return PrimitiveNodeType.NULL;
    }

    @Override
    public NodeType visit(SimpleDefFun simpleDefFun, SymbolTable arg) {
        NodeType fType = simpleDefFun.getTypeDenoter().accept(this, arg);
        arg.enterScope();
        simpleDefFun.getStatements().forEach(this.typeCheck(arg));
        arg.exitScope();
        return fType;
    }

    @Override
    public NodeType visit(ComplexDefFun complexDefFun, SymbolTable arg) {
        NodeType fType = complexDefFun.getTypeDenoter().accept(this, arg);
        arg.exitScope();
        complexDefFun.getParDecls().forEach(this.typeCheck(arg));
        complexDefFun.getStatements().forEach(this.typeCheck(arg));
        arg.exitScope();
        return fType;
    }

    @Override
    public NodeType visit(ParDecl parDecl, SymbolTable arg) {
        NodeType pType = parDecl.getTypeDenoter().accept(this, arg);
        parDecl.getVariable().accept(this, arg);
        return pType;
    }

    @Override
    public NodeType visit(VarDecl varDecl, SymbolTable arg) {
        NodeType vType = varDecl.getTypeDenoter().accept(this, arg);
        varDecl.getVariable().accept(this, arg);
        return vType;
    }

    @Override
    public NodeType visit(VarInitValue varInitValue, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(PrimitiveTypeDenoter primitiveTypeDenoter, SymbolTable arg) {
        return primitiveTypeDenoter.typeFactory();
    }

    @Override
    public NodeType visit(ArrayTypeDenoter arrayTypeDenoter, SymbolTable arg) {
        return arrayTypeDenoter.typeFactory();
    }

    @Override
    public NodeType visit(FunctionTypeDenoter functionTypeDenoter, SymbolTable arg) {
        return functionTypeDenoter.typeFactory();
    }

    @Override
    public NodeType visit(WhileStatement whileStatement, SymbolTable arg) {
        NodeType wType = whileStatement.getExpr().accept(this, arg);
        if (wType.equals(PrimitiveNodeType.BOOL)) {

        }
        return null;
    }

    @Override
    public NodeType visit(IfThenStatement ifThenStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(IfThenElseStatement ifThenElseStatements, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(ForStatement forStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(LocalStatement localStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(AssignStatement assignStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(ArrayElementStatement arrayElementStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(FunctionCallStatement functionCallStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(ReadStatement readStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(WriteStatement writeStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(ReturnStatement returnStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(FloatConst floatConst, SymbolTable arg) {
        floatConst.setType(PrimitiveNodeType.FLOAT);
        return floatConst.getType();
    }

    @Override
    public NodeType visit(StringConst stringConst, SymbolTable arg) {
        stringConst.setType(PrimitiveNodeType.STRING);
        return stringConst.getType();
    }

    @Override
    public NodeType visit(IntegerConst integerConst, SymbolTable arg) {
        integerConst.setType(PrimitiveNodeType.INT);
        return integerConst.getType();
    }

    @Override
    public NodeType visit(ArrayConst emptyArrayExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(ArrayRead readArrayExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(FunctionCall functionCallExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(PlusOp plusOp, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(MinusOp minusOp, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(TimesOp timesOp, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(DivOp divOp, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(AndOp andOp, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(OrOp orOp, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(GtOp gtOp, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(GeOp geOp, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(LtOp ltOp, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(LeOp leOp, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(EqOp eqOp, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(NeOp neOp, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(UMinusExpression uMinusExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(NilConst nilConst, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(Id id, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(BooleanConst booleanConst, SymbolTable arg) {
        booleanConst.setType(PrimitiveNodeType.BOOL);
        return booleanConst.getType();
    }

    @Override
    public NodeType visit(NotExpression notExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(SharpExpression sharpExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(NopStatement nopStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public NodeType visit(Variable variable, SymbolTable arg) {
        return arg.lookup(variable.getValue()).get().getTypeDenoter();
    }
}

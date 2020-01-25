package visitor;

import error.ErrorHandler;
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
        return null;
    }

    @Override
    public Boolean visit(Global global, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(SimpleDefFun simpleDefFun, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(ComplexDefFun complexDefFun, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(ParDecl parDecl, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(VarDecl varDecl, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(VarInitValue varInitValue, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(PrimitiveTypeDenoter primitiveTypeDenoter, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(ArrayTypeDenoter arrayTypeDenoter, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(FunctionTypeDenoter functionTypeDenoter, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(WhileStatement whileStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(IfThenStatement ifThenStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(IfThenElseStatement ifThenElseStatements, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(ForStatement forStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(LocalStatement localStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(AssignStatement assignStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(ArrayElementStatement arrayElementStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(FunctionCallStatement functionCallStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(ReadStatement readStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(WriteStatement writeStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(ReturnStatement returnStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(FloatConst floatConst, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(StringConst stringConst, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(IntegerConst integerConst, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(ArrayConst emptyArrayExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(ArrayRead readArrayExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(FunctionCall functionCallExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(PlusOp plusOp, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(MinusOp minusOp, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(TimesOp timesOp, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(DivOp divOp, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(AndOp andOp, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(OrOp orOp, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(GtOp gtOp, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(GeOp geOp, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(LtOp ltOp, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(LeOp leOp, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(EqOp eqOp, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(NeOp neOp, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(UMinusExpression uMinusExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(NilConst nilConst, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(Id id, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(BooleanConst booleanConst, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(NotExpression notExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(SharpExpression sharpExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(NopStatement nopStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(Variable variable, SymbolTable arg) {
        return null;
    }
}

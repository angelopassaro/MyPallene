package visitor;

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

public class CLangVisitor implements Visitor<String, SymbolTable> {

    private final String root;

    public CLangVisitor(String root) {
        this.root = root;
    }

    @Override
    public String visit(Program program, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(Global global, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(SimpleDefFun simpleDefFun, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(ComplexDefFun complexDefFun, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(ParDecl parDecl, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(VarDecl varDecl, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(VarInitValue varInitValue, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(PrimitiveTypeDenoter primitiveTypeDenoter, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(ArrayTypeDenoter arrayTypeDenoter, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(FunctionTypeDenoter functionTypeDenoter, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(WhileStatement whileStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(IfThenStatement ifThenStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(IfThenElseStatement ifThenElseStatements, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(ForStatement forStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(LocalStatement localStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(AssignStatement assignStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(ArrayElementStatement arrayElementStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(FunctionCallStatement functionCallStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(ReadStatement readStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(WriteStatement writeStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(ReturnStatement returnStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(FloatConst floatConst, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(StringConst stringConst, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(IntegerConst integerConst, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(ArrayConst emptyArrayExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(ArrayRead readArrayExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(FunctionCall functionCallExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(PlusOp plusOp, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(MinusOp minusOp, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(TimesOp timesOp, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(DivOp divOp, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(AndOp andOp, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(OrOp orOp, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(GtOp gtOp, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(GeOp geOp, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(LtOp ltOp, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(LeOp leOp, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(EqOp eqOp, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(NeOp neOp, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(UMinusExpression uMinusExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(NilConst nilConst, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(Id id, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(BooleanConst booleanConst, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(NotExpression notExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(SharpExpression sharpExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(NopStatement nopStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(Variable variable, SymbolTable arg) {
        return null;
    }
}

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

import java.util.List;
import java.util.StringJoiner;

public class PreCLangVisitor implements Visitor<String, SymbolTable> {

    private final String root;

    public PreCLangVisitor(String root) {
        this.root = root;
    }

    private String beautify(List<? extends AstNode> nodes, StringJoiner joiner, SymbolTable table) {
        nodes.forEach(node -> joiner.add(node.accept(this, table)));
        return joiner.toString();
    }

    @Override
    public String visit(Program program, SymbolTable arg) {
        arg.enterScope();
        String global = this.beautify(program.getGlobal().getVarDecls(), new StringJoiner("\n"), arg);
        String functions = this.beautify(program.getFunctions(), new StringJoiner("\n"), arg);
        arg.exitScope();
        return this.root.replace("$declarations$", global).replace("$statements$", functions);
    }

    @Override
    public String visit(Variable variable, SymbolTable arg) {
        return variable.getValue();
    }


    @Override
    public String visit(VarDecl varDecl, SymbolTable arg) {
        String type = varDecl.getTypeDenoter().accept(this, arg);
        String name = varDecl.getVariable().accept(this, arg);
        String value = varDecl.getVarInitValue().accept(this, arg);
        String result;
        if (value != null) {
            result = String.format("%s %s = %s;", type, name, value);
        } else {
            result = String.format("%s %s;", type, name);
        }
        return result;
    }

    @Override
    public String visit(Global global, SymbolTable arg) {
        return this.beautify(global.getVarDecls(), new StringJoiner("\n"), arg);
    }

    @Override
    public String visit(SimpleDefFun simpleDefFun, SymbolTable arg) {
        String name = simpleDefFun.getVariable().accept(this, arg);
        String type = simpleDefFun.getTypeDenoter().accept(this, arg);
        return String.format("%s %s();", type, name);
    }

    @Override
    public String visit(ComplexDefFun complexDefFun, SymbolTable arg) {
        String name = complexDefFun.getVariable().accept(this, arg);
        String type = complexDefFun.getTypeDenoter().accept(this, arg);
        String parDecls = this.beautify(complexDefFun.getParDecls(), new StringJoiner(" , "), arg);
        return String.format("%s %s (%s);", type, name, parDecls);
    }

    @Override
    public String visit(ParDecl parDecl, SymbolTable arg) {
        return String.format("%s %s", parDecl.getTypeDenoter().typeFactory(), parDecl.getVariable().getValue());
    }


    @Override
    public String visit(VarInitValue varInitValue, SymbolTable arg) {
        return varInitValue.getExpr().accept(this, arg);
    }

    @Override
    public String visit(PrimitiveTypeDenoter primitiveTypeDenoter, SymbolTable arg) {
        String type = primitiveTypeDenoter.typeFactory().toString();
        return type.equals("undefined") ? "void" : type;
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
        return Float.toString(floatConst.getValue());
    }

    @Override
    public String visit(StringConst stringConst, SymbolTable arg) {
        return stringConst.getValue();
    }

    @Override
    public String visit(IntegerConst integerConst, SymbolTable arg) {
        return Integer.toString(integerConst.getValue());
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
        return String.format("%s + %s", plusOp.getElement1(), plusOp.getElement2());

    }

    @Override
    public String visit(MinusOp minusOp, SymbolTable arg) {
        return String.format("%s - %s", minusOp.getElement1(), minusOp.getElement2());

    }

    @Override
    public String visit(TimesOp timesOp, SymbolTable arg) {
        return String.format("%s * %s", timesOp.getElement1(), timesOp.getElement2());

    }

    @Override
    public String visit(DivOp divOp, SymbolTable arg) {
        return String.format("%s / %s", divOp.getElement1(), divOp.getElement2());

    }

    @Override
    public String visit(AndOp andOp, SymbolTable arg) {
        return String.format("%s && %s", andOp.getElement1(), andOp.getElement2());

    }

    @Override
    public String visit(OrOp orOp, SymbolTable arg) {
        return String.format("%s || %s", orOp.getElement1(), orOp.getElement2());
    }

    @Override
    public String visit(GtOp gtOp, SymbolTable arg) {
        return String.format("%s > %s", gtOp.getElement1(), gtOp.getElement2());
    }

    @Override
    public String visit(GeOp geOp, SymbolTable arg) {
        return String.format("%s >= %s", geOp.getElement1(), geOp.getElement2());
    }

    @Override
    public String visit(LtOp ltOp, SymbolTable arg) {
        return String.format("%s < %s", ltOp.getElement1(), ltOp.getElement2());
    }

    @Override
    public String visit(LeOp leOp, SymbolTable arg) {
        return String.format("%s <= %s", leOp.getElement1(), leOp.getElement2());
    }

    @Override
    public String visit(EqOp eqOp, SymbolTable arg) {
        return String.format("%s == %s", eqOp.getElement1(), eqOp.getElement2());

    }

    @Override
    public String visit(NeOp neOp, SymbolTable arg) {
        return String.format("%s != %s", neOp.getElement1(), neOp.getElement2());
    }

    @Override
    public String visit(UMinusExpression uMinusExpression, SymbolTable arg) {
        String minus = uMinusExpression.getMinus().accept(this, arg);
        return String.format("- %s", minus);
    }

    @Override
    public String visit(NilConst nilConst, SymbolTable arg) {
        return nilConst.getValue().toString();
    }

    @Override
    public String visit(Id id, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(BooleanConst booleanConst, SymbolTable arg) {
        return booleanConst.getValue().toString();
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


}

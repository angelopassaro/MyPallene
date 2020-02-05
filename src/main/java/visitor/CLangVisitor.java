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

public class CLangVisitor implements Visitor<String, SymbolTable> {

    private final String root;

    public CLangVisitor(String root) {
        this.root = root;
    }

    private String beautify(List<? extends AstNode> nodes, StringJoiner joiner, SymbolTable table) {
        nodes.forEach(node -> joiner.add(node.accept(this, table)));
        return joiner.toString();
    }


    @Override
    public String visit(Program program, SymbolTable arg) {
        arg.enterScope();
        String functions = this.beautify(program.getFunctions(), new StringJoiner("\n"), arg);
        arg.exitScope();
        return this.root.replace("$functions$", functions);
    }

    @Override
    public String visit(Global global, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(SimpleDefFun simpleDefFun, SymbolTable arg) {
        arg.enterScope();
        String name = simpleDefFun.getVariable().accept(this, arg);
        String type = simpleDefFun.getTypeDenoter().accept(this, arg);
        String statements = this.beautify(simpleDefFun.getStatements(), new StringJoiner(";\n"), arg);
        arg.exitScope();
        return String.format("%s %s(){\n %s; \n}", type, name, statements);
    }

    @Override
    public String visit(ComplexDefFun complexDefFun, SymbolTable arg) {
        arg.enterScope();
        String name = complexDefFun.getVariable().accept(this, arg);
        String type = complexDefFun.getTypeDenoter().accept(this, arg);
        String parDecls = this.beautify(complexDefFun.getParDecls(), new StringJoiner(" , "), arg);
        String statements = this.beautify(complexDefFun.getStatements(), new StringJoiner(";\n"), arg);
        arg.exitScope();
        return String.format("%s %s (%s){\n %s; \n}", type, name, parDecls, statements);
    }

    @Override
    public String visit(ParDecl parDecl, SymbolTable arg) {
        return String.format("%s %s", parDecl.getTypeDenoter().typeFactory(), parDecl.getVariable().getValue());
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
        String expr = whileStatement.getExpr().accept(this, arg);
        String statements = this.beautify(whileStatement.getStatements(), new StringJoiner("\n"), arg);
        return String.format("while(%s){\n %s;}", expr, statements);
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
        String id = assignStatement.getId().accept(this, arg);
        String expr = assignStatement.getExpr().accept(this, arg);
        return String.format("%s = %s", id, expr);
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
        return floatConst.getValue().toString();
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
        return String.format("%s + %s", plusOp.getElement1().accept(this, arg), plusOp.getElement2().accept(this, arg));

    }

    @Override
    public String visit(MinusOp minusOp, SymbolTable arg) {
        return String.format("%s - %s", minusOp.getElement1().accept(this, arg), minusOp.getElement2().accept(this, arg));

    }

    @Override
    public String visit(TimesOp timesOp, SymbolTable arg) {
        return String.format("%s * %s", timesOp.getElement1().accept(this, arg), timesOp.getElement2().accept(this, arg));

    }

    @Override
    public String visit(DivOp divOp, SymbolTable arg) {
        return String.format("%s / %s", divOp.getElement1().accept(this, arg), divOp.getElement2().accept(this, arg));

    }

    @Override
    public String visit(AndOp andOp, SymbolTable arg) {
        return String.format("%s && %s", andOp.getElement1().accept(this, arg), andOp.getElement2().accept(this, arg));

    }

    @Override
    public String visit(OrOp orOp, SymbolTable arg) {
        return String.format("%s || %s", orOp.getElement1().accept(this, arg), orOp.getElement2().accept(this, arg));
    }

    @Override
    public String visit(GtOp gtOp, SymbolTable arg) {
        return String.format("%s > %s", gtOp.getElement1().accept(this, arg), gtOp.getElement2().accept(this, arg));
    }

    @Override
    public String visit(GeOp geOp, SymbolTable arg) {
        return String.format("%s >= %s", geOp.getElement1().accept(this, arg), geOp.getElement2().accept(this, arg));
    }

    @Override
    public String visit(LtOp ltOp, SymbolTable arg) {
        return String.format("%s < %s", ltOp.getElement1().accept(this, arg), ltOp.getElement2().accept(this, arg));
    }

    @Override
    public String visit(LeOp leOp, SymbolTable arg) {
        return String.format("%s <= %s", leOp.getElement1().accept(this, arg), leOp.getElement2().accept(this, arg));
    }

    @Override
    public String visit(EqOp eqOp, SymbolTable arg) {
        return String.format("%s == %s", eqOp.getElement1().accept(this, arg), eqOp.getElement2().accept(this, arg));

    }

    @Override
    public String visit(NeOp neOp, SymbolTable arg) {
        return String.format("%s != %s", neOp.getElement1().accept(this, arg), neOp.getElement2().accept(this, arg));
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
        return id.getValue();
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

    @Override
    public String visit(Variable variable, SymbolTable arg) {
        return variable.getValue();
    }
}

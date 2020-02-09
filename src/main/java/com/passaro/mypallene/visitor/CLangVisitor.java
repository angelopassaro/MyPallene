package com.passaro.mypallene.visitor;

import com.passaro.mypallene.nodetype.NodeType;
import com.passaro.mypallene.nodetype.PrimitiveNodeType;
import com.passaro.mypallene.semantic.SymbolTable;
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

    private String formatType(NodeType type) {
        PrimitiveNodeType pType = PrimitiveNodeType.class.cast(type);
        switch (pType) {
            case FLOAT:
                return "%f";
            case STRING:
                return "%s";
            default:
                return "%d";
        }
    }

    public String functionType(String name, String type) {
        if (name.equals("main") && type.equals("void")) {
            return "int";
        } else if ((!name.equals("main")) && type.equals("void")) {
            return "void";
        } else if (type.equals("string")) {
            return "char *";
        } else {
            return type;
        }
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
        String statements = this.beautify(simpleDefFun.getStatements(), new StringJoiner("\n"), arg);
        arg.exitScope();
        type = functionType(name, type);
        return String.format("\n%s %s(){\n%s\n}\n", type, name, statements);
    }

    @Override
    public String visit(ComplexDefFun complexDefFun, SymbolTable arg) {
        arg.enterScope();
        String name = complexDefFun.getVariable().accept(this, arg);
        String type = complexDefFun.getTypeDenoter().accept(this, arg);
        String parDecls = this.beautify(complexDefFun.getParDecls(), new StringJoiner(" , "), arg);
        String statements = this.beautify(complexDefFun.getStatements(), new StringJoiner("\n"), arg);
        arg.exitScope();
        type = functionType(name, type);
        return String.format("\n%s %s (%s){\n%s\n}\n", type, name, parDecls, statements);
    }

    @Override
    public String visit(ParDecl parDecl, SymbolTable arg) {
        return String.format("%s %s", parDecl.getTypeDenoter().typeFactory(), parDecl.getVariable().getName());
    }


    @Override
    public String visit(VarDecl varDecl, SymbolTable arg) {
        String type = varDecl.getTypeDenoter().accept(this, arg);
        String name = varDecl.getVariable().accept(this, arg);
        if (type.equals("string")) {
            type = "char *";
        }
        if (varDecl.getTypeDenoter() instanceof ArrayTypeDenoter) name = name + "[50]";

        if (varDecl.getVarInitValue() != null) {
            return String.format("%s %s = %s;", type, name, varDecl.getVarInitValue().accept(this, arg));
        } else {
            return String.format("%s %s;", type, name);
        }
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
    public String visit(WhileStatement whileStatement, SymbolTable arg) {
        String expr = whileStatement.getExpr().accept(this, arg);
        String statements = this.beautify(whileStatement.getStatements(), new StringJoiner("\n"), arg);
        return String.format("while(%s){\n%s}", expr, statements);
    }

    @Override
    public String visit(IfThenStatement ifThenStatement, SymbolTable arg) {
        String condition = ifThenStatement.getExpr().accept(this, arg);
        String statements = this.beautify(ifThenStatement.getStatements(), new StringJoiner("\n"), arg);
        return String.format("if (%s) {\n%s\n}", condition, statements);
    }

    @Override
    public String visit(IfThenElseStatement ifThenElseStatements, SymbolTable arg) {
        String condition = ifThenElseStatements.getExpr().accept(this, arg);
        String statements = this.beautify(ifThenElseStatements.getThenStatement(), new StringJoiner("\n"), arg);
        String elseStatements = this.beautify(ifThenElseStatements.getElseStatement(), new StringJoiner("\n"), arg);
        return String.format("if (%s) {\n%s\n} else {\n%s\n}", condition, statements, elseStatements);
    }

    //TODO da rivedere
    @Override
    public String visit(ForStatement forStatement, SymbolTable arg) {
        arg.enterScope();
        String var = forStatement.getVariable().accept(this, arg);
        String varValue = forStatement.getAssignExpr().accept(this, arg);
        String expr = forStatement.getCommaExpr().accept(this, arg);
        String statements = this.beautify(forStatement.getStatements(), new StringJoiner("\n"), arg);
        arg.exitScope();
        return String.format("for (int %s = %s; %s; %s++){\n %s\n}", var, varValue, expr, var, statements);
    }

    @Override
    public String visit(LocalStatement localStatement, SymbolTable arg) {
        arg.enterScope();
        String varDecl = this.beautify(localStatement.getVarDecls(), new StringJoiner("\n"), arg);
        String statements = this.beautify(localStatement.getStatements(), new StringJoiner("\n"), arg);
        arg.exitScope();
        return String.format("{\n%s\n%s\n}", varDecl, statements);
    }

    @Override
    public String visit(AssignStatement assignStatement, SymbolTable arg) {
        String id = assignStatement.getId().accept(this, arg);
        String expr = assignStatement.getExpr().accept(this, arg);
        return String.format("%s = %s;", id, expr);
    }

    @Override
    public String visit(ArrayElementStatement arrayElementStatement, SymbolTable arg) {
        String array = arrayElementStatement.getArrayExpr().accept(this, arg);
        String index = arrayElementStatement.getArrayPoint().accept(this, arg);
        String assignee = arrayElementStatement.getArrayAssign().accept(this, arg);
        return String.format("%s[%s] = %s;", array, index, assignee);
    }

    @Override
    public String visit(FunctionCallStatement functionCallStatement, SymbolTable arg) {
        String func = functionCallStatement.getId().accept(this, arg);
        String params = this.beautify(functionCallStatement.getExprs(), new StringJoiner(" , "), arg);
        return String.format("%s(%s)", func, params);
    }

    @Override
    public String visit(ReadStatement readStatement, SymbolTable arg) {
        StringJoiner scanfs = new StringJoiner("\n");
        readStatement.getIds().forEach(var -> {
            String type = this.formatType(arg.lookup(var.getName()).get().getTypeDenoter());
            String varName = (type == "%s" ? "&" + var.getName() : var.getName());
            scanfs.add(String.format("scanf(\"%s\", &%s);", type, varName));
        });
        return scanfs.toString();
    }

    @Override
    public String visit(WriteStatement writeStatement, SymbolTable arg) {
        StringJoiner scanfs = new StringJoiner("\n");
        writeStatement.getExprs().forEach(expr -> {
            String type = this.formatType(expr.getType());
            String toPrint = expr.accept(this, arg);
            scanfs.add(String.format("printf(\"%s\", %s);", type, toPrint));
        });
        return scanfs.toString();
    }

    @Override
    public String visit(ReturnStatement returnStatement, SymbolTable arg) {
        String type = returnStatement.getExpr().accept(this, arg);
        if (type.equals("NULL")) {
            type = "0";
        }
        return String.format("return %s;", type);
    }

    @Override
    public String visit(FloatConst floatConst, SymbolTable arg) {
        return floatConst.getName().toString();
    }

    @Override
    public String visit(StringConst stringConst, SymbolTable arg) {
        return String.format("\"%s\"", stringConst.getName());
    }

    @Override
    public String visit(IntegerConst integerConst, SymbolTable arg) {
        return Integer.toString(integerConst.getName());
    }

    @Override
    public String visit(ArrayConst emptyArrayExpression, SymbolTable arg) {
        return "{ }";
    }

    @Override
    public String visit(ArrayRead readArrayExpression, SymbolTable arg) {
        String array = readArrayExpression.getArrayName().accept(this, arg);
        String index = readArrayExpression.getArrayElement().accept(this, arg);
        return String.format("%s[%s]", array, index);
    }

    @Override
    public String visit(FunctionCall functionCallExpression, SymbolTable arg) {
        String func = functionCallExpression.getId().accept(this, arg);
        String params = this.beautify(functionCallExpression.getExprs(), new StringJoiner(" , "), arg);
        return String.format("%s(%s)", func, params);
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
        if (gtOp.getElement1().getType().toString().equals("string") && gtOp.getElement2().getType().toString().equals("string"))
            return String.format("strcmp(%s, %s) > 0", gtOp.getElement1().accept(this, arg), gtOp.getElement2().accept(this, arg));
        return String.format("%s > %s", gtOp.getElement1().accept(this, arg), gtOp.getElement2().accept(this, arg));
    }

    @Override
    public String visit(GeOp geOp, SymbolTable arg) {
        return String.format("%s >= %s", geOp.getElement1().accept(this, arg), geOp.getElement2().accept(this, arg));
    }

    @Override
    public String visit(LtOp ltOp, SymbolTable arg) {
        if (ltOp.getElement1().getType().toString().equals("string") && ltOp.getElement2().getType().toString().equals("string"))
            return String.format("strcmp(%s, %s) < 0", ltOp.getElement1().accept(this, arg), ltOp.getElement2().accept(this, arg));
        return String.format("%s < %s", ltOp.getElement1().accept(this, arg), ltOp.getElement2().accept(this, arg));
    }

    @Override
    public String visit(LeOp leOp, SymbolTable arg) {
        return String.format("%s <= %s", leOp.getElement1().accept(this, arg), leOp.getElement2().accept(this, arg));
    }

    @Override
    public String visit(EqOp eqOp, SymbolTable arg) {
        if (eqOp.getElement1().getType().toString().equals("string") && eqOp.getElement2().getType().toString().equals("string"))
            return String.format("strcmp(%s, %s) == 0", eqOp.getElement1().accept(this, arg), eqOp.getElement2().accept(this, arg));
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
        return "NULL";
    }

    @Override
    public String visit(Id id, SymbolTable arg) {
        return id.getName();
    }

    @Override
    public String visit(BooleanConst booleanConst, SymbolTable arg) {
        return booleanConst.getName().toString();
    }

    @Override
    public String visit(NotExpression notExpression, SymbolTable arg) {
        return String.format("!(%s)", notExpression.getNot().accept(this, arg));
    }

    @Override
    public String visit(SharpExpression sharpExpression, SymbolTable arg) {
        String a = sharpExpression.getExpr().accept(this, arg);
        sharpExpression.setType(PrimitiveNodeType.INT);
        if (sharpExpression.getExpr().getType().toString().equalsIgnoreCase("string")) {
            return String.format("strlen(%s)", a);
        } else {
            return String.format("count(%s)", a);
        }
    }

    @Override
    public String visit(NopStatement nopStatement, SymbolTable arg) {
        return "nop()";
    }

    @Override
    public String visit(ArrayTypeDenoter arrayTypeDenoter, SymbolTable arg) {
        return arrayTypeDenoter.cType();
    }

    @Override
    public String visit(FunctionTypeDenoter functionTypeDenoter, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(Variable variable, SymbolTable arg) {
        return variable.getName();
    }
}
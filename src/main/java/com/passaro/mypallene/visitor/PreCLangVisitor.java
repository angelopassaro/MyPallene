package com.passaro.mypallene.visitor;

import com.passaro.mypallene.semantic.GlobalArray;
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
import java.util.function.Consumer;

public class PreCLangVisitor implements Visitor<String, SymbolTable> {

    private final String root;
    private GlobalArray globalArray;
    private final String array = "typedef struct {\n" +
            "  int *array;\n" +
            "  int used;\n" +
            "  int size;\n" +
            "} ArrayInt;\n" +
            "\n" +
            "void initArrayInt(ArrayInt *a) {\n" +
            "  a->array = (int *)malloc(100 * sizeof(int));\n" +
            "  a->used = 0;\n" +
            "  a->size = 100;\n" +
            "}\n" +
            "\n" +
            "void insertArrayInt(ArrayInt *a, int element, int position) {\n" +
            "  if (position >= a->size) {\n" +
            "    a->size *= position;\n" +
            "    a->array = (int *)realloc(a->array, a->size * sizeof(int));\n" +
            "  }\n" +
            "  a->array[position] = element;\n" +
            "  a->used++;\n" +
            "}\n" +
            "\n" +
            "typedef struct {\n" +
            "  char **array;\n" +
            "  int used;\n" +
            "  int size;\n" +
            "} ArrayChar;\n" +
            "\n" +
            "void initArrayChar(ArrayChar *a) {\n" +
            "  a->array = malloc(100 * sizeof(char *) + 1);\n" +
            "  a->used = 0;\n" +
            "  a->size = 100;\n" +
            "}\n" +
            "\n" +
            "void insertArrayChar(ArrayChar *a, char *element, int position) {\n" +
            "  if (position >= a->size) {\n" +
            "    a->size *= position;\n" +
            "    a->array = realloc(a->array, a->size * sizeof(char) + 1);\n" +
            "  }\n" +
            "  a->array[position] = element;\n" +
            "  a->used++;\n" +
            "}\n" +
            "typedef struct {\n" +
            "  float *array;\n" +
            "  int used;\n" +
            "  int size;\n" +
            "} ArrayFloat;\n" +
            "\n" +
            "void initArrayFloat(ArrayFloat *a) {\n" +
            "  a->array = (float *)malloc(100 * sizeof(float));\n" +
            "  a->used = 0;\n" +
            "  a->size = 100;\n" +
            "}\n" +
            "\n" +
            "void insertArrayFloat(ArrayFloat *a, float element, int position) {\n" +
            "  if (position >= a->size) {\n" +
            "    a->size *= position;\n" +
            "    a->array = (float *)realloc(a->array, a->size * sizeof(float));\n" +
            "  }\n" +
            "  a->array[position] = element;\n" +
            "  a->used++;\n" +
            "}" +
            "typedef struct {\n" +
            "  bool *array;\n" +
            "  int used;\n" +
            "  int size;\n" +
            "} ArrayBool;\n" +
            "\n" +
            "void initArrayBool(ArrayBool *a) {\n" +
            "  a->array = (bool *)malloc(100 * sizeof(bool));\n" +
            "  a->used = 0;\n" +
            "  a->size = 100;\n" +
            "}\n" +
            "\n" +
            "void insertArrayBool(ArrayBool *a, bool element, int position) {\n" +
            "  if (position >= a->size) {\n" +
            "    a->size *= position;\n" +
            "    a->array = (bool *)realloc(a->array, a->size * sizeof(bool));\n" +
            "  }\n" +
            "  a->array[position] = element;\n" +
            "  a->used++;\n" +
            "}";

    public PreCLangVisitor(String root, GlobalArray globalArray) {
        this.root = root;
        this.globalArray = globalArray;
    }

    private String beautify(List<? extends AstNode> nodes, StringJoiner joiner, SymbolTable table) {
        nodes.forEach(node -> joiner.add(node.accept(this, table)));
        return joiner.toString();
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

    private Consumer<ParDecl> formatArg(StringJoiner joiner, SymbolTable table) {
        return t -> {
            if (t.getTypeDenoter() instanceof ArrayTypeDenoter) {
                joiner.add(String.format("%s %s", (t.getTypeDenoter()).cType(), t.getVariable().getName()));
            } else if (t.getTypeDenoter() instanceof FunctionTypeDenoter) {
                joiner.add(String.format(t.accept(PreCLangVisitor.this, table), "%s"));
            } else {
                joiner.add(String.format("%s", t.accept(PreCLangVisitor.this, table)));
            }
        };
    }

    @Override
    public String visit(Program program, SymbolTable arg) {
        arg.enterScope();
        String global = this.beautify(program.getGlobal().getVarDecls(), new StringJoiner("\n"), arg);
        String functions = this.beautify(program.getFunctions(), new StringJoiner("\n"), arg);
        arg.exitScope();
        String result = this.root.replace("$declarations$", global).replace("$statements$", functions);
        return result.contains("ArrayInt") || result.contains("ArrayFloat") || result.contains("ArrayChar") || result.contains("ArrayBool") ? result.replace("$array$", array) : result.replace("$array$", "// no array use");
    }

    @Override
    public String visit(Variable variable, SymbolTable arg) {
        return variable.getName();
    }

    @Override
    public String visit(Execute execute, SymbolTable arg) {
        return null;
    }


    @Override
    public String visit(VarDecl varDecl, SymbolTable arg) {
        String type = varDecl.getTypeDenoter().accept(this, arg);
        String name = varDecl.getVariable().accept(this, arg);
        String result;
        if (varDecl.getTypeDenoter() instanceof ArrayTypeDenoter) {
            type = (varDecl.getTypeDenoter()).cType();
            this.globalArray.addGlobal(String.format("init%s(&%s);", type, name));
            if (!varDecl.getVarInitValue().accept(this, arg).equals("null")) {
                this.globalArray.addGlobal(String.format("insert%s(&%s,%s,0);", type, name, varDecl.getVarInitValue().accept(this, arg)));
            }
        }
        if (varDecl.getVarInitValue() != null && !(varDecl.getTypeDenoter() instanceof ArrayTypeDenoter)) {
            String value = varDecl.getVarInitValue().accept(this, arg);
            result = String.format("%s %s = %s;", type, name, value);
        } else
            result = String.format("%s %s;", type, name);
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
        type = functionType(name, type);
        return name.equals("main") ? "" : String.format("%s %s();", type, name);
    }

    @Override
    public String visit(ComplexDefFun complexDefFun, SymbolTable arg) {
        String name = complexDefFun.getVariable().accept(this, arg);
        String type = complexDefFun.getTypeDenoter().accept(this, arg);
        StringJoiner parDecls = new StringJoiner(", ");
        complexDefFun.getParDecls().forEach(this.formatArg(parDecls, arg));
        //  String parDecls = this.beautify(complexDefFun.getParDecls(), new StringJoiner(" , "), arg);
        type = functionType(name, type);
        return name.equals("main") ? "" : String.format("%s %s (%s);", type, name, parDecls);
    }

    @Override
    public String visit(ParDecl parDecl, SymbolTable arg) {
        String type = parDecl.getTypeDenoter().accept(this, arg);
        String name = parDecl.getVariable().accept(this, arg);
        if (parDecl.getTypeDenoter() instanceof FunctionTypeDenoter) {
            return String.format(type, name);
        }
        return String.format("%s %s", type, name);
    }


    @Override
    public String visit(VarInitValue varInitValue, SymbolTable arg) {
        return varInitValue.getExpr().accept(this, arg);
    }

    @Override
    public String visit(PrimitiveTypeDenoter primitiveTypeDenoter, SymbolTable arg) {
        String type = primitiveTypeDenoter.typeFactory().toString();
        return type.equals("undefined") ? "int" : type;
    }

    @Override
    public String visit(ArrayTypeDenoter arrayTypeDenoter, SymbolTable arg) {
        return arrayTypeDenoter.cType();
    }

    @Override
    public String visit(FunctionTypeDenoter functionTypeDenoter, SymbolTable arg) {
        return functionTypeDenoter.cType();
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
        return Float.toString(floatConst.getName());
    }

    @Override
    public String visit(StringConst stringConst, SymbolTable arg) {
        return stringConst.getName();
    }

    @Override
    public String visit(IntegerConst integerConst, SymbolTable arg) {
        return Integer.toString(integerConst.getName());
    }

    @Override
    public String visit(ArrayConst emptyArrayExpression, SymbolTable arg) {
        return "null";
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
        return nilConst.getName().toString();
    }

    @Override
    public String visit(Id id, SymbolTable arg) {
        return null;
    }

    @Override
    public String visit(BooleanConst booleanConst, SymbolTable arg) {
        return booleanConst.getName().toString();
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

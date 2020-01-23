package visitor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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

public class ConcreteXMLVisitor implements Visitor<Element, Document> {

    private Consumer<? super AstNode> addParent(Element parent, Document arg) {
        return (AstNode node) -> parent.appendChild(node.accept(this, arg));
    }

    @Override
    public Element visit(Program program, Document arg) {
        Element element = arg.createElement("Program");
        element.appendChild(program.getGlobal().accept(this, arg));
        program.getFunctions().forEach(addParent(element, arg));
        arg.appendChild(element);
        return element;
    }

    @Override
    public Element visit(Global global, Document arg) {
        Element element = arg.createElement("Global");
        global.getVarDecls().forEach(addParent(element, arg));
        return element;
    }

    @Override
    public Element visit(SimpleDefFun simpleDefFun, Document arg) {
        Element element = arg.createElement("Function");
        element.appendChild(simpleDefFun.getVariable().accept(this, arg));
        element.appendChild(simpleDefFun.getTypeDenoterDenoter().accept(this, arg));
        simpleDefFun.getStatements().forEach(addParent(element, arg));
        return element;
    }

    @Override
    public Element visit(ComplexDefFun complexDefFun, Document arg) {
        Element element = arg.createElement("Function");
        element.appendChild(complexDefFun.getVariable().accept(this, arg));
        complexDefFun.getParDecls().forEach(addParent(element, arg));
        element.appendChild(complexDefFun.getTypeDenoterDenoter().accept(this, arg));
        complexDefFun.getStatements().forEach(addParent(element, arg));
        return element;
    }

    @Override
    public Element visit(ParDecl parDecl, Document arg) {
        Element element = arg.createElement("ParDecl");
        element.appendChild(parDecl.getVariable().accept(this, arg));
        element.appendChild(parDecl.getTypeDenoterDenoter().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(VarDecl varDecl, Document arg) {
        Element element = arg.createElement("VarDecl");
        element.appendChild(varDecl.getVariable().accept(this, arg));
        element.appendChild(varDecl.getTypeDenoterDenoter().accept(this, arg));
        element.appendChild(varDecl.getVarInitValue().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(VarInitValue varInitValue, Document arg) {
        Element element = arg.createElement("VarInitValue");
        element.appendChild(varInitValue.getExpr().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(PrimitiveTypeDenoter primitiveTypeDenoter, Document arg) {
        Element element = arg.createElement("PrimitiveTypeDenoter.java");
        element.setAttribute("kind", primitiveTypeDenoter.getKind());
        return element;
    }


    @Override
    public Element visit(ArrayTypeDenoter arrayTypeDenoter, Document arg) {
        Element element = arg.createElement("ArrayType");
        element.appendChild(arrayTypeDenoter.getTypeDenoterDenoter().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(FunctionTypeDenoter functionTypeDenoter, Document arg) {
        Element element = arg.createElement("FunctionType");
        functionTypeDenoter.getTypeDenoters().forEach(addParent(element, arg));
        element.appendChild(functionTypeDenoter.getReturnType().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(WhileStatement whileStatement, Document arg) {
        Element element = arg.createElement("WhileStatement");
        element.appendChild(whileStatement.getExpr().accept(this, arg));
        whileStatement.getStatements().forEach(addParent(element, arg));
        return element;
    }

    @Override
    public Element visit(IfThenStatement ifThenStatement, Document arg) {
        Element element = arg.createElement("IfThenStatement");
        element.appendChild(ifThenStatement.getExpr().accept(this, arg));
        ifThenStatement.getStatements().forEach(addParent(element, arg));
        return element;
    }

    @Override
    public Element visit(IfThenElseStatement ifThenElseStatements, Document arg) {
        Element element = arg.createElement("IfThenElseStatement");
        element.appendChild(ifThenElseStatements.getExpr().accept(this, arg));
        ifThenElseStatements.getThenStatement().forEach(addParent(element, arg));
        ifThenElseStatements.getElseStatement().forEach(addParent(element, arg));
        return element;
    }

    @Override
    public Element visit(ForStatement forStatement, Document arg) {
        Element element = arg.createElement("ForStatement");
        element.appendChild(forStatement.getVariable().accept(this, arg));
        element.appendChild(forStatement.getAssignExpr().accept(this, arg));
        element.appendChild(forStatement.getCommaExpr().accept(this, arg));
        forStatement.getStatements().forEach(addParent(element, arg));
        return element;
    }

    @Override
    public Element visit(LocalStatement localStatement, Document arg) {
        Element element = arg.createElement("LocalStatement");
        localStatement.getVarDecls().forEach(addParent(element, arg));
        localStatement.getStatements().forEach(addParent(element, arg));
        return element;
    }

    @Override
    public Element visit(AssignStatement assignStatement, Document arg) {
        Element element = arg.createElement("AssignStatement");
        element.appendChild(assignStatement.getId().accept(this, arg));
        element.appendChild(assignStatement.getExpr().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(ArrayElementStatement arrayElementStatement, Document arg) {
        Element element = arg.createElement("ArrayElement");
        element.appendChild(arrayElementStatement.getArrayExpr().accept(this, arg));
        element.appendChild(arrayElementStatement.getArrayPoint().accept(this, arg));
        element.appendChild(arrayElementStatement.getArrayAssign().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(FunctionCallStatement functionCallStatement, Document arg) {
        Element element = arg.createElement("FunctionCallStatement");
        element.appendChild(functionCallStatement.getId().accept(this, arg));
        functionCallStatement.getExprs().forEach(addParent(element, arg));
        return element;
    }

    @Override
    public Element visit(ReadStatement readStatement, Document arg) {
        Element element = arg.createElement("ReadStatement");
        readStatement.getIds().forEach(addParent(element, arg));
        return element;
    }

    @Override
    public Element visit(WriteStatement writeStatement, Document arg) {
        Element element = arg.createElement("WriteStatement");
        writeStatement.getExprs().forEach(addParent(element, arg));
        return element;
    }

    @Override
    public Element visit(ReturnStatement returnStatement, Document arg) {
        Element element = arg.createElement("ReturnStatement");
        element.appendChild(returnStatement.getExpr().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(FloatConst floatConst, Document arg) {
        Element element = arg.createElement("FloatConst");
        element.setAttribute("value", floatConst.getValue().toString());
        return element;
    }

    @Override
    public Element visit(StringConst stringConst, Document arg) {
        Element element = arg.createElement("StringConst");
        element.setAttribute("text", stringConst.getValue());
        return element;
    }

    @Override
    public Element visit(IntegerConst integerConst, Document arg) {
        Element element = arg.createElement("IntegerConst");
        element.setAttribute("value", integerConst.getValue().toString());
        return element;
    }

    @Override
    public Element visit(ArrayConst emptyArrayExpression, Document arg) {
        Element element = arg.createElement("EmptyArrayExpression");
        element.setAttribute("type", emptyArrayExpression.getTypeDenoterDenoter().toString());
        return element;
    }

    @Override
    public Element visit(ArrayRead readArrayExpression, Document arg) {
        Element element = arg.createElement("ReadArrayExpression");
        element.appendChild(readArrayExpression.getArrayName().accept(this, arg));
        element.appendChild(readArrayExpression.getArrayElement().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(FunctionCall functionCallExpression, Document arg) {
        Element element = arg.createElement("FunctionCallExpression");
        element.appendChild(functionCallExpression.getId().accept(this, arg));
        functionCallExpression.getExprs().forEach(addParent(element, arg));
        return element;
    }

    @Override
    public Element visit(PlusOp plusOp, Document arg) {
        Element element = arg.createElement("PlusOp");
        element.appendChild(plusOp.getElement1().accept(this, arg));
        element.appendChild(plusOp.getElement2().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(MinusOp minusOp, Document arg) {
        Element element = arg.createElement("MinusOp");
        element.appendChild(minusOp.getElement1().accept(this, arg));
        element.appendChild(minusOp.getElement2().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(TimesOp timesOp, Document arg) {
        Element element = arg.createElement("TimesOp");
        element.appendChild(timesOp.getElement1().accept(this, arg));
        element.appendChild(timesOp.getElement2().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(DivOp divOp, Document arg) {
        Element element = arg.createElement("DivOp");
        element.appendChild(divOp.getElement1().accept(this, arg));
        element.appendChild(divOp.getElement2().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(AndOp andOp, Document arg) {
        Element element = arg.createElement("AndOp");
        element.appendChild(andOp.getElement1().accept(this, arg));
        element.appendChild(andOp.getElement2().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(OrOp orOp, Document arg) {
        Element element = arg.createElement("OrOp");
        element.appendChild(orOp.getElement1().accept(this, arg));
        element.appendChild(orOp.getElement2().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(GtOp gtOp, Document arg) {
        Element element = arg.createElement("GtOp");
        element.appendChild(gtOp.getElement1().accept(this, arg));
        element.appendChild(gtOp.getElement2().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(GeOp geOp, Document arg) {
        Element element = arg.createElement("GeOp");
        element.appendChild(geOp.getElement1().accept(this, arg));
        element.appendChild(geOp.getElement2().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(LtOp ltOp, Document arg) {
        Element element = arg.createElement("LtOp");
        element.appendChild(ltOp.getElement1().accept(this, arg));
        element.appendChild(ltOp.getElement2().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(LeOp leOp, Document arg) {
        Element element = arg.createElement("LeOp");
        element.appendChild(leOp.getElement1().accept(this, arg));
        element.appendChild(leOp.getElement2().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(EqOp eqOp, Document arg) {
        Element element = arg.createElement("EqOp");
        element.appendChild(eqOp.getElement1().accept(this, arg));
        element.appendChild(eqOp.getElement2().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(NeOp neOp, Document arg) {
        Element element = arg.createElement("neOp");
        element.appendChild(neOp.getElement1().accept(this, arg));
        element.appendChild(neOp.getElement2().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(UMinusExpression uMinusExpression, Document arg) {
        Element element = arg.createElement("UMinusExpression");
        element.appendChild(uMinusExpression.getMinus().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(NilConst nilConst, Document arg) {
        Element element = arg.createElement("NilConst");
        element.setAttribute("Value", nilConst.getValue().toString());
        return element;
    }

    @Override
    public Element visit(Id id, Document arg) {
        Element element = arg.createElement("Id");
        element.setAttribute("lexeme", id.getValue());
        return element;
    }

    @Override
    public Element visit(BooleanConst booleanConst, Document arg) {
        Element element = arg.createElement("BooleanConst");
        element.setAttribute("value", booleanConst.getValue().toString());
        return element;
    }

    @Override
    public Element visit(NotExpression notExpression, Document arg) {
        Element element = arg.createElement("NotExpression");
        return element;
    }

    @Override
    public Element visit(SharpExpression sharpExpression, Document arg) {
        Element element = arg.createElement("SharpElement");
        element.appendChild(sharpExpression.getExpr().accept(this, arg));
        return element;
    }

    @Override
    public Element visit(NopStatement nopStatement, Document arg) {
        Element element = arg.createElement("NopStatement");
        return element;
    }

    @Override
    public Element visit(Variable variable, Document arg) {
        Element element = arg.createElement("Variable");
        element.setAttribute("lexeme", variable.getValue());
        return element;
    }


}

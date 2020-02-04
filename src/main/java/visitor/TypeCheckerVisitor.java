package visitor;

import error.ErrorHandler;
import nodetype.CompositeNodeType;
import nodetype.FunctionNodeType;
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

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * TODO Controllo dato di ritorno error message
 */
public class TypeCheckerVisitor implements Visitor<NodeType, SymbolTable> {
    private ErrorHandler errorHandler;
    private ArrayList<NodeType> returnType = new ArrayList<>();

    public TypeCheckerVisitor(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    private Consumer<? super AstNode> typeCheck(SymbolTable arg) {

        return (AstNode node) -> node.accept(this, arg);
    }


    @Override
    public NodeType visit(Program program, SymbolTable arg) {
        arg.enterScope();
        if (program.getGlobal() != null) {
            program.getGlobal().getVarDecls().forEach(this.typeCheck(arg));
        }
        program.getFunctions().forEach(this.typeCheck(arg));
        arg.exitScope();
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
        if (!this.returnType.isEmpty() && !this.returnType.contains(fType)) {
            this.errorHandler.reportTypeMismatch(fType, returnType.get(0), simpleDefFun);
        }
        this.returnType.clear();
        arg.exitScope();
        return fType;
    }

    @Override
    public NodeType visit(ComplexDefFun complexDefFun, SymbolTable arg) {
        NodeType fType = complexDefFun.getTypeDenoter().accept(this, arg);
        arg.enterScope();
        complexDefFun.getParDecls().forEach(this.typeCheck(arg));
        complexDefFun.getStatements().forEach(this.typeCheck(arg));
        if (!this.returnType.isEmpty() && !this.returnType.contains(fType)) {
            this.errorHandler.reportTypeMismatch(fType, returnType.get(0), complexDefFun);
        }
        arg.exitScope();
        this.returnType.clear();
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
        NodeType tType = varDecl.getTypeDenoter().accept(this, arg);
        if (varDecl.getVarInitValue() != null) {
            varDecl.getVarInitValue().getExpr().accept(this, arg);
            //vType varDecl.getVarInitValue().getExpr().accept(this,arg);
            //if (!tType.equals(vType)) {
            //    this.errorHandler.reportTypeMismatch(tType, vType, varDecl);
            //} else {
            //    varDecl.getVariable().accept(this, arg);
            //}
        }
        return tType;
    }

    @Override
    public NodeType visit(VarInitValue varInitValue, SymbolTable arg) {
        varInitValue.getExpr().accept(this, arg);
        return PrimitiveNodeType.NULL;
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
        if (!wType.equals(PrimitiveNodeType.BOOL)) {
            this.errorHandler.reportTypeMismatch(PrimitiveNodeType.BOOL, wType, whileStatement);
        }
        whileStatement.getStatements().forEach(this.typeCheck(arg));
        return PrimitiveNodeType.NULL;
    }

    @Override
    public NodeType visit(IfThenStatement ifThenStatement, SymbolTable arg) {
        NodeType ifType = ifThenStatement.getExpr().accept(this, arg);
        if (!ifType.equals(PrimitiveNodeType.BOOL)) {
            this.errorHandler.reportTypeMismatch(PrimitiveNodeType.BOOL, ifType, ifThenStatement);
        }
        ifThenStatement.getStatements().forEach(this.typeCheck(arg));
        return PrimitiveNodeType.NULL;
    }

    @Override
    public NodeType visit(IfThenElseStatement ifThenElseStatements, SymbolTable arg) {
        NodeType ifType = ifThenElseStatements.getExpr().accept(this, arg);
        if (!ifType.equals(PrimitiveNodeType.BOOL)) {
            this.errorHandler.reportTypeMismatch(PrimitiveNodeType.BOOL, ifType, ifThenElseStatements);
        }
        ifThenElseStatements.getElseStatement().forEach(this.typeCheck(arg));
        ifThenElseStatements.getThenStatement().forEach(this.typeCheck(arg));
        return PrimitiveNodeType.NULL;
    }

    @Override
    public NodeType visit(ForStatement forStatement, SymbolTable arg) {
        arg.enterScope();
        NodeType assignType = forStatement.getAssignExpr().accept(this, arg);
        if (!assignType.equals(PrimitiveNodeType.INT)) {
            this.errorHandler.reportTypeMismatch(PrimitiveNodeType.INT, assignType, forStatement);
        }
        NodeType commaExpr = forStatement.getCommaExpr().accept(this, arg);
        if (!commaExpr.equals(PrimitiveNodeType.BOOL)) {
            this.errorHandler.reportTypeMismatch(PrimitiveNodeType.BOOL, commaExpr, forStatement);
        }
        forStatement.getStatements().forEach(this.typeCheck(arg));
        arg.exitScope();
        return PrimitiveNodeType.NULL;
    }

    @Override
    public NodeType visit(LocalStatement localStatement, SymbolTable arg) {
        arg.enterScope();
        localStatement.getVarDecls().forEach(this.typeCheck(arg));
        localStatement.getStatements().forEach(this.typeCheck(arg));
        arg.exitScope();
        return PrimitiveNodeType.NULL;
    }

    @Override
    public NodeType visit(AssignStatement assignStatement, SymbolTable arg) {
        NodeType leftType = assignStatement.getId().accept(this, arg);
        NodeType rightType = assignStatement.getExpr().accept(this, arg);
        if (!leftType.equals(rightType)) {
            this.errorHandler.reportTypeMismatch(leftType, rightType, assignStatement);
        }
        return rightType;
    }

    @Override
    public NodeType visit(ArrayElementStatement arrayElementStatement, SymbolTable arg) {
        arrayElementStatement.getArrayExpr().accept(this, arg);
        NodeType aType = arrayElementStatement.getArrayPoint().getType();
        if (!aType.equals(PrimitiveNodeType.INT)) {
            this.errorHandler.reportTypeMismatch(PrimitiveNodeType.INT, aType, arrayElementStatement);
        }
        arrayElementStatement.getArrayAssign().accept(this, arg);
        return PrimitiveNodeType.NULL;
    }

    @Override
    public NodeType visit(FunctionCallStatement functionCallStatement, SymbolTable arg) {
        CompositeNodeType input = new CompositeNodeType(new ArrayList<>());
        functionCallStatement.getExprs().forEach(e -> input.addType(e.accept(this, arg)));
        functionCallStatement.getId().accept(this, arg);
        FunctionNodeType cNodeType = (FunctionNodeType) functionCallStatement.getId().accept(this, arg);
        if (!cNodeType.getInput().equals(input)) {
            this.errorHandler.reportTypeMismatch(cNodeType.getInput(), input, functionCallStatement);
        }
        return cNodeType.getOutput();
    }

    @Override
    public NodeType visit(FunctionCall functionCallExpression, SymbolTable arg) {
        CompositeNodeType input = new CompositeNodeType(new ArrayList<>());
        functionCallExpression.getExprs().forEach(e -> input.addType(e.accept(this, arg)));
        functionCallExpression.getId().accept(this, arg);
        FunctionNodeType cNodeType = (FunctionNodeType) functionCallExpression.getId().accept(this, arg);
        if (!cNodeType.getInput().equals(input)) {
            this.errorHandler.reportTypeMismatch(cNodeType.getInput(), input, functionCallExpression);
        }
        return cNodeType.getOutput();
    }

    @Override
    public NodeType visit(ReadStatement readStatement, SymbolTable arg) {
        readStatement.getIds().forEach(this.typeCheck(arg));
        return PrimitiveNodeType.NULL;
    }

    @Override
    public NodeType visit(WriteStatement writeStatement, SymbolTable arg) {
        writeStatement.getExprs().forEach(this.typeCheck(arg));
        return PrimitiveNodeType.NULL;
    }

    @Override
    public NodeType visit(ReturnStatement returnStatement, SymbolTable arg) {
        NodeType rType = returnStatement.getExpr().accept(this, arg);
        this.returnType.add(rType);
        // if (!rType.equals(this.functionType)) {
        //     this.errorHandler.reportTypeMismatch(this.functionType, rType, returnStatement);
        // }

        // System.out.println("Return " + rType);
        return rType;
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
        emptyArrayExpression.accept(this, arg);
        return PrimitiveNodeType.NULL;
    }

    @Override
    public NodeType visit(ArrayRead readArrayExpression, SymbolTable arg) {
        readArrayExpression.accept(this, arg);
        return PrimitiveNodeType.NULL;
    }


    @Override
    public NodeType visit(PlusOp plusOp, SymbolTable arg) {
        NodeType lType = plusOp.getElement1().accept(this, arg);
        NodeType rType = plusOp.getElement2().accept(this, arg);
        if (!(rType.equals(PrimitiveNodeType.INT) || rType.equals(PrimitiveNodeType.FLOAT))) {
            this.errorHandler.reportTypeMismatch(lType, rType, plusOp);
        }
        return lType.checkAdd((PrimitiveNodeType) rType);
    }

    @Override
    public NodeType visit(MinusOp minusOp, SymbolTable arg) {
        NodeType lType = minusOp.getElement1().accept(this, arg);
        NodeType rType = minusOp.getElement2().accept(this, arg);
        if (!(rType.equals(PrimitiveNodeType.INT) || rType.equals(PrimitiveNodeType.FLOAT))) {
            this.errorHandler.reportTypeMismatch(lType, rType, minusOp);
        }
        return lType.checkSub((PrimitiveNodeType) rType);
    }

    @Override
    public NodeType visit(TimesOp timesOp, SymbolTable arg) {
        NodeType lType = timesOp.getElement1().accept(this, arg);
        NodeType rType = timesOp.getElement2().accept(this, arg);
        if (!(rType.equals(PrimitiveNodeType.INT) || rType.equals(PrimitiveNodeType.FLOAT))) {
            this.errorHandler.reportTypeMismatch(lType, rType, timesOp);
        }
        return lType.checkMul((PrimitiveNodeType) rType);
    }

    @Override
    public NodeType visit(DivOp divOp, SymbolTable arg) {
        NodeType lType = divOp.getElement1().accept(this, arg);
        NodeType rType = divOp.getElement2().accept(this, arg);
        if (!(rType.equals(PrimitiveNodeType.INT) || rType.equals(PrimitiveNodeType.FLOAT))) {
            this.errorHandler.reportTypeMismatch(lType, rType, divOp);
        }
        return lType.checkDiv((PrimitiveNodeType) rType);
    }

    @Override
    public NodeType visit(AndOp andOp, SymbolTable arg) {
        NodeType lType = andOp.getElement1().accept(this, arg);
        NodeType rType = andOp.getElement2().accept(this, arg);
        if (rType.equals(PrimitiveNodeType.BOOL)) {
            this.errorHandler.reportTypeMismatch(PrimitiveNodeType.BOOL, rType, andOp);
        }
        return lType.checkRel((PrimitiveNodeType) rType);
    }

    @Override
    public NodeType visit(OrOp orOp, SymbolTable arg) {
        NodeType lType = orOp.getElement1().accept(this, arg);
        NodeType rType = orOp.getElement2().accept(this, arg);
        if (rType.equals(PrimitiveNodeType.BOOL)) {
            this.errorHandler.reportTypeMismatch(PrimitiveNodeType.BOOL, rType, orOp);
        }
        return lType.checkRel((PrimitiveNodeType) rType);
    }

    @Override
    public NodeType visit(GtOp gtOp, SymbolTable arg) {
        NodeType lType = gtOp.getElement1().accept(this, arg);
        NodeType rType = gtOp.getElement2().accept(this, arg);
        if (!(rType.equals(PrimitiveNodeType.INT) || rType.equals(PrimitiveNodeType.FLOAT)) && !(lType.equals(PrimitiveNodeType.INT) || lType.equals(PrimitiveNodeType.FLOAT))) {
            this.errorHandler.reportTypeMismatch(PrimitiveNodeType.INT, rType, gtOp);
        }
        return lType.checkRel((PrimitiveNodeType) rType);
    }

    @Override
    public NodeType visit(GeOp geOp, SymbolTable arg) {
        NodeType lType = geOp.getElement1().accept(this, arg);
        NodeType rType = geOp.getElement2().accept(this, arg);
        if (!(rType.equals(PrimitiveNodeType.INT) || rType.equals(PrimitiveNodeType.FLOAT)) && !(lType.equals(PrimitiveNodeType.INT) || lType.equals(PrimitiveNodeType.FLOAT))) {
            this.errorHandler.reportTypeMismatch(PrimitiveNodeType.INT, rType, geOp);
        }
        return lType.checkRel((PrimitiveNodeType) rType);
    }


    @Override
    public NodeType visit(LtOp ltOp, SymbolTable arg) {
        NodeType lType = ltOp.getElement1().accept(this, arg);
        NodeType rType = ltOp.getElement2().accept(this, arg);
        if (!(rType.equals(PrimitiveNodeType.INT) || rType.equals(PrimitiveNodeType.FLOAT)) && !(lType.equals(PrimitiveNodeType.INT) || lType.equals(PrimitiveNodeType.FLOAT))) {
            this.errorHandler.reportTypeMismatch(PrimitiveNodeType.INT, rType, ltOp);
        }
        return lType.checkRel((PrimitiveNodeType) rType);
    }


    @Override
    public NodeType visit(LeOp leOp, SymbolTable arg) {
        NodeType lType = leOp.getElement1().accept(this, arg);
        NodeType rType = leOp.getElement2().accept(this, arg);
        if (!(rType.equals(PrimitiveNodeType.INT) || rType.equals(PrimitiveNodeType.FLOAT)) && !(lType.equals(PrimitiveNodeType.INT) || lType.equals(PrimitiveNodeType.FLOAT))) {
            this.errorHandler.reportTypeMismatch(PrimitiveNodeType.INT, rType, leOp);
        }
        return lType.checkRel((PrimitiveNodeType) rType);
    }


    @Override
    public NodeType visit(EqOp eqOp, SymbolTable arg) {
        NodeType lType = eqOp.getElement1().accept(this, arg);
        NodeType rType = eqOp.getElement2().accept(this, arg);
        if (!(rType.equals(PrimitiveNodeType.INT) || rType.equals(PrimitiveNodeType.FLOAT)) && !(lType.equals(PrimitiveNodeType.INT) || lType.equals(PrimitiveNodeType.FLOAT))) {
            this.errorHandler.reportTypeMismatch(PrimitiveNodeType.INT, rType, eqOp);
        }
        return lType.checkRel((PrimitiveNodeType) rType);
    }


    @Override
    public NodeType visit(NeOp neOp, SymbolTable arg) {
        NodeType lType = neOp.getElement1().accept(this, arg);
        NodeType rType = neOp.getElement2().accept(this, arg);
        if (rType.equals(PrimitiveNodeType.BOOL)) {
            this.errorHandler.reportTypeMismatch(PrimitiveNodeType.BOOL, rType, neOp);
        }
        return lType.checkRel((PrimitiveNodeType) rType);
    }

    @Override
    public NodeType visit(UMinusExpression uMinusExpression, SymbolTable arg) {
        NodeType uType = uMinusExpression.getMinus().accept(this, arg);
        if (!uType.equals(PrimitiveNodeType.INT)) {
            this.errorHandler.reportTypeMismatch(PrimitiveNodeType.INT, uMinusExpression.getType(), uMinusExpression);
        }
        return uType;
    }

    @Override
    public NodeType visit(NilConst nilConst, SymbolTable arg) {
        nilConst.setType(PrimitiveNodeType.NULL);
        return nilConst.getType();
    }

    @Override
    public NodeType visit(Id id, SymbolTable arg) {
        NodeType iType = arg.lookup(id.getValue()).get().getTypeDenoter();
        id.setType(iType);
        return iType;
    }

    @Override
    public NodeType visit(BooleanConst booleanConst, SymbolTable arg) {
        booleanConst.setType(PrimitiveNodeType.BOOL);
        return booleanConst.getType();
    }

    @Override
    public NodeType visit(NotExpression notExpression, SymbolTable arg) {
        NodeType nType = notExpression.getNot().accept(this, arg);
        if (!nType.equals(PrimitiveNodeType.BOOL)) {
            this.errorHandler.reportTypeMismatch(PrimitiveNodeType.BOOL, nType, notExpression);
        }
        return nType;
    }

    @Override
    public NodeType visit(SharpExpression sharpExpression, SymbolTable arg) {
        sharpExpression.getExpr().accept(this, arg);
        return PrimitiveNodeType.NULL;
    }

    @Override
    public NodeType visit(NopStatement nopStatement, SymbolTable arg) {
        nopStatement.accept(this, arg);
        return PrimitiveNodeType.NULL;
    }

    @Override
    public NodeType visit(Variable variable, SymbolTable arg) {
        return arg.lookup(variable.getValue()).get().getTypeDenoter();
    }
}

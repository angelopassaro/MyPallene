package visitor;

import error.ErrorHandler;
import nodekind.NodeKind;
import nodetype.PrimitiveNodeType;
import semantic.SymbolTable;
import semantic.SymbolTableRecord;
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
import syntax.typedenoter.TypeDenoter;

import java.util.List;

public class ScopeCheckerVisitor implements Visitor<Boolean, SymbolTable> {

    private ErrorHandler errorHandler;

    public ScopeCheckerVisitor(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    private boolean checkContext(List<? extends AstNode> nodes, SymbolTable arg) {
        if (nodes.isEmpty())
            return true;
        else
            return nodes.stream().allMatch(node -> node.accept(this, arg));
    }

    @Override
    public Boolean visit(Program program, SymbolTable arg) {
        arg.enterScope();
        boolean isGlobalSafe = program.getGlobal().accept(this, arg);
        boolean areFunctionsSafe = this.checkContext(program.getFunctions(), arg);
        boolean isProgramSafe = isGlobalSafe && areFunctionsSafe;
        if (!isProgramSafe) {
            this.errorHandler.reportError("Program Error", program);
        }
        arg.exitScope();
        return isProgramSafe;
    }

    @Override
    public Boolean visit(Global global, SymbolTable arg) {
        arg.enterScope();
        boolean areVarDeclsSafe = this.checkContext(global.getVarDecls(), arg);
        boolean isGlobalSafe = areVarDeclsSafe;
        if (!isGlobalSafe) {
            this.errorHandler.reportError("Global Error", global);
        }
        arg.exitScope();
        return isGlobalSafe;
    }

    @Override
    public Boolean visit(SimpleDefFun simpleDefFun, SymbolTable arg) {
        boolean isSimpleFunctionSafe = simpleDefFun.getId().accept(this, arg);
        if (!isSimpleFunctionSafe) {
            this.errorHandler.reportYetDefined(simpleDefFun);
        } else {
            arg.enterScope();
            boolean isStatementsSafe = this.checkContext(simpleDefFun.getStatements(), arg);
            boolean isTypeSafe = simpleDefFun.getType().accept(this, arg);
            isSimpleFunctionSafe = isStatementsSafe && isTypeSafe;
            if (!isSimpleFunctionSafe) {
                this.errorHandler.reportError("Simple Function Error", simpleDefFun);
            }
            arg.exitScope();
            arg.addEntry(simpleDefFun.getId().toString(), new SymbolTableRecord(simpleDefFun.getType().typeFactory().toString(), NodeKind.FUNCTION));
        }
        return isSimpleFunctionSafe;
    }

    @Override
    public Boolean visit(ComplexDefFun complexDefFun, SymbolTable arg) {
        boolean isComplexFunctionSafe = complexDefFun.getId().accept(this, arg);
        if (!isComplexFunctionSafe) {
            this.errorHandler.reportYetDefined(complexDefFun);
        } else {
            arg.enterScope();
            boolean isParDeclSafe = this.checkContext(complexDefFun.getParDecls(), arg);
            boolean isStatementsSafe = this.checkContext(complexDefFun.getStatements(), arg);
            boolean isTypeSafe = complexDefFun.getType().accept(this, arg);
            isComplexFunctionSafe = isStatementsSafe && isTypeSafe && isParDeclSafe;
            if (!isComplexFunctionSafe) {
                this.errorHandler.reportError("Simple Function Error", complexDefFun);
            }
            arg.exitScope();
            arg.addEntry(complexDefFun.getId().toString(), new SymbolTableRecord(complexDefFun.getType().typeFactory().toString(), NodeKind.FUNCTION));
        }
        return isComplexFunctionSafe;
    }

    @Override
    public Boolean visit(ParDecl parDecl, SymbolTable arg) {
        boolean isParDeclSafe = parDecl.getId().accept(this, arg);
        if (!isParDeclSafe) {
            this.errorHandler.reportError("ParDecl Error", parDecl);
        } else {
            arg.addEntry(parDecl.getId().toString(), new SymbolTableRecord(parDecl.getType().typeFactory().toString(), NodeKind.VARIABLE));
        }
        return isParDeclSafe;
    }

    @Override
    public Boolean visit(VarDecl varDecl, SymbolTable arg) {
        boolean isVarDeclSafe = varDecl.getId().accept(this, arg);
        if (!isVarDeclSafe) {
            this.errorHandler.reportError("VarDecl Error", varDecl);
        } else {
            arg.addEntry(varDecl.getId().toString(), new SymbolTableRecord(varDecl.getType().typeFactory().toString(), NodeKind.VARIABLE));
        }
        return isVarDeclSafe;
    }

    @Override
    public Boolean visit(VarInitValue varInitValue, SymbolTable arg) {
        return varInitValue.getExpr().accept(this, arg);
    }


    @Override
    public Boolean visit(WhileStatement whileStatement, SymbolTable arg) {
        boolean isExprSafe = whileStatement.getExpr().accept(this, arg);
        arg.enterScope();
        boolean areStatementsSafe = checkContext(whileStatement.getStatements(), arg);
        boolean isWhileSafe = isExprSafe && areStatementsSafe;
        if (!isWhileSafe) {
            this.errorHandler.reportError("While Statement Error", whileStatement);
        }
        arg.exitScope();
        return isWhileSafe;
    }

    @Override
    public Boolean visit(IfThenStatement ifThenStatement, SymbolTable arg) {
        boolean isExprSafe = ifThenStatement.getExpr().accept(this, arg);
        boolean areStatementsSafe = checkContext(ifThenStatement.getStatements(), arg);
        boolean isIfThenStatementSafe = isExprSafe && areStatementsSafe;
        if (!isIfThenStatementSafe) {
            this.errorHandler.reportError("If Else Statement Error", ifThenStatement);
        }
        return isIfThenStatementSafe;
    }

    @Override
    public Boolean visit(IfThenElseStatement ifThenElseStatements, SymbolTable arg) {
        boolean isExprSafe = ifThenElseStatements.getExpr().accept(this, arg);
        boolean areThenStatementSafe = checkContext(ifThenElseStatements.getThenStatement(), arg);
        boolean areElseStatementSafe = checkContext(ifThenElseStatements.getElseStatement(), arg);
        boolean ifThenElseStatement = isExprSafe && areThenStatementSafe && areElseStatementSafe;
        if (!ifThenElseStatement) {
            this.errorHandler.reportError("If Then Else Statements Error", ifThenElseStatements);
        }
        return ifThenElseStatement;
    }

    //????
    @Override
    public Boolean visit(ForStatement forStatement, SymbolTable arg) {
        arg.enterScope();
        arg.addEntry(forStatement.getId().toString(), new SymbolTableRecord(forStatement.getId().getValue(), NodeKind.VARIABLE));
        boolean areStatemetSafe = checkContext(forStatement.getStatements(), arg);
        boolean isAssignExprSafe = forStatement.getAssignExpr().accept(this, arg);
        boolean isAssignCommaSafe = forStatement.getCommaExpr().accept(this, arg);
        boolean isForSafe = areStatemetSafe && isAssignCommaSafe && isAssignExprSafe;
        arg.exitScope();
        return isForSafe;
    }

    //????
    @Override
    public Boolean visit(LocalStatement localStatement, SymbolTable arg) {
        arg.enterScope();
        boolean areStatementsSafe = checkContext(localStatement.getStatements(), arg);
        boolean areVarDeclSafe = checkContext(localStatement.getVarDecls(), arg);
        boolean isLocalSafe = areStatementsSafe && areVarDeclSafe;
        if (!isLocalSafe) {
            this.errorHandler.reportError("Local Statement Error", localStatement);
        }
        arg.exitScope();
        return isLocalSafe;
    }

    @Override
    public Boolean visit(AssignStatement assignStatement, SymbolTable arg) {
        boolean isLeftSafe = assignStatement.getId().accept(this, arg);
        boolean isRightSafe = assignStatement.getExpr().accept(this, arg);
        boolean isAssignSafe = isLeftSafe && isRightSafe;
        if (!isAssignSafe) {
            this.errorHandler.reportError("Assign Sstatement Error", assignStatement);
        }
        return isAssignSafe;
    }


    @Override
    public Boolean visit(ReadStatement readStatement, SymbolTable arg) {
        boolean isReadStatementSafe = checkContext(readStatement.getIds(), arg);
        if (!isReadStatementSafe) {
            this.errorHandler.reportNotDefined(readStatement);
        }
        return isReadStatementSafe;
    }

    @Override
    public Boolean visit(WriteStatement writeStatement, SymbolTable arg) {
        boolean isWriteStatementSafe = checkContext(writeStatement.getExprs(), arg);
        if (!isWriteStatementSafe) {
            this.errorHandler.reportError("Write Statement Error", writeStatement);
        }
        return isWriteStatementSafe;
    }


    @Override
    public Boolean visit(FunctionCallStatement functionCallStatement, SymbolTable arg) {
        boolean isFunctionCallSafe = functionCallStatement.getId().accept(this, arg);
        if (!isFunctionCallSafe) {
            this.errorHandler.reportNotDefined(functionCallStatement);
        } else {
            boolean areStatementsSafe = checkContext(functionCallStatement.getExprs(), arg);
            if (!areStatementsSafe) {
                this.errorHandler.reportError("Function Call Error", functionCallStatement);
            }
        }
        return isFunctionCallSafe;
    }

    @Override
    public Boolean visit(FunctionCall functionCallExpression, SymbolTable arg) {
        boolean isFunctionCallSafe = functionCallExpression.getId().accept(this, arg);
        if (!isFunctionCallSafe) {
            this.errorHandler.reportYetDefined(functionCallExpression);
        } else {
            arg.enterScope();
            boolean areStatementsSafe = checkContext(functionCallExpression.getExprs(), arg);
            if (!areStatementsSafe) {
                this.errorHandler.reportError("Function Call Error", functionCallExpression);
            }
            arg.exitScope();
            arg.addEntry(functionCallExpression.getId().toString(), new SymbolTableRecord("da modificare il type", NodeKind.FUNCTION));
        }
        return isFunctionCallSafe;
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
    public Boolean visit(PlusOp plusOp, SymbolTable arg) {
        boolean isLeftSafe = plusOp.getElement1().accept(this, arg);
        boolean isRightSafe = plusOp.getElement2().accept(this, arg);
        boolean isPlusSafe = isLeftSafe && isRightSafe;
        if (!isPlusSafe) {
            this.errorHandler.reportError("Plus Op Error", plusOp);
        }
        return isPlusSafe;
    }

    @Override
    public Boolean visit(MinusOp minusOp, SymbolTable arg) {
        boolean isLeftSafe = minusOp.getElement1().accept(this, arg);
        boolean isRightSafe = minusOp.getElement2().accept(this, arg);
        boolean isMinusSafe = isLeftSafe && isRightSafe;
        if (!isMinusSafe) {
            this.errorHandler.reportError("Minus Op Error", minusOp);
        }
        return isMinusSafe;
    }

    @Override
    public Boolean visit(TimesOp timesOp, SymbolTable arg) {
        boolean isLeftSafe = timesOp.getElement1().accept(this, arg);
        boolean isRightSafe = timesOp.getElement2().accept(this, arg);
        boolean isTimesSafe = isLeftSafe && isRightSafe;
        if (!isTimesSafe) {
            this.errorHandler.reportError("Time Op Error", timesOp);
        }
        return isTimesSafe;
    }

    @Override
    public Boolean visit(DivOp divOp, SymbolTable arg) {
        boolean isLeftSafe = divOp.getElement1().accept(this, arg);
        boolean isRightSafe = divOp.getElement2().accept(this, arg);
        boolean isDivSafe = isLeftSafe && isRightSafe;
        if (!isDivSafe) {
            this.errorHandler.reportError("Div Op Error", divOp);
        }
        return isDivSafe;
    }

    @Override
    public Boolean visit(AndOp andOp, SymbolTable arg) {
        boolean isLeftSafe = andOp.getElement1().accept(this, arg);
        boolean isRightSafe = andOp.getElement2().accept(this, arg);
        boolean isAndSafe = isLeftSafe && isRightSafe;
        if (!isAndSafe) {
            this.errorHandler.reportError("And Op Error", andOp);
        }
        return isAndSafe;
    }

    @Override
    public Boolean visit(OrOp orOp, SymbolTable arg) {
        boolean isLeftSafe = orOp.getElement1().accept(this, arg);
        boolean isRightSafe = orOp.getElement2().accept(this, arg);
        boolean isOrSafe = isLeftSafe && isRightSafe;
        if (!isOrSafe) {
            this.errorHandler.reportError("Or Op Error", orOp);
        }
        return isOrSafe;
    }

    @Override
    public Boolean visit(GtOp gtOp, SymbolTable arg) {
        boolean isLeftSafe = gtOp.getElement1().accept(this, arg);
        boolean isRightSafe = gtOp.getElement2().accept(this, arg);
        boolean isGtSafe = isLeftSafe && isRightSafe;
        if (!isGtSafe) {
            this.errorHandler.reportError("Gt Op Error", gtOp);
        }
        return isGtSafe;
    }

    @Override
    public Boolean visit(GeOp geOp, SymbolTable arg) {
        boolean isLeftSafe = geOp.getElement1().accept(this, arg);
        boolean isRightSafe = geOp.getElement2().accept(this, arg);
        boolean isGeSafe = isLeftSafe && isRightSafe;
        if (!isGeSafe) {
            this.errorHandler.reportError("Ge Op Error", geOp);
        }
        return isGeSafe;
    }

    @Override
    public Boolean visit(LtOp ltOp, SymbolTable arg) {
        boolean isLeftSafe = ltOp.getElement1().accept(this, arg);
        boolean isRightSafe = ltOp.getElement2().accept(this, arg);
        boolean isLtSafe = isLeftSafe && isRightSafe;
        if (!isLtSafe) {
            this.errorHandler.reportError("Lt Op Error", ltOp);
        }
        return isLtSafe;
    }

    @Override
    public Boolean visit(LeOp leOp, SymbolTable arg) {
        boolean isLeftSafe = leOp.getElement1().accept(this, arg);
        boolean isRightSafe = leOp.getElement2().accept(this, arg);
        boolean isLeSafe = isLeftSafe && isRightSafe;
        if (!isLeSafe) {
            this.errorHandler.reportError("Le Op Error", leOp);
        }
        return isLeSafe;
    }

    @Override
    public Boolean visit(EqOp eqOp, SymbolTable arg) {
        boolean isLeftSafe = eqOp.getElement1().accept(this, arg);
        boolean isRightSafe = eqOp.getElement2().accept(this, arg);
        boolean isEqSafe = isLeftSafe && isRightSafe;
        if (!isEqSafe) {
            this.errorHandler.reportError("Eq Op Error", eqOp);
        }
        return isEqSafe;
    }

    @Override
    public Boolean visit(NeOp neOp, SymbolTable arg) {
        boolean isLeftSafe = neOp.getElement1().accept(this, arg);
        boolean isRightSafe = neOp.getElement2().accept(this, arg);
        boolean isNeSafe = isLeftSafe && isRightSafe;
        if (!isNeSafe) {
            this.errorHandler.reportError("Ne Op Error", neOp);
        }
        return isNeSafe;
    }

    @Override
    public Boolean visit(UMinusExpression uMinusExpression, SymbolTable arg) {
        boolean isUMinusSafe = uMinusExpression.getMinus().accept(this, arg);
        if (!isUMinusSafe) {
            this.errorHandler.reportError("UMinus Expression Error", uMinusExpression);
        }
        return isUMinusSafe;
    }

    @Override
    public Boolean visit(Id id, SymbolTable arg) {
        return arg.lookup(id.getValue()).isPresent();
    }

    @Override
    public Boolean visit(NilConst nilConst, SymbolTable arg) {
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
        boolean isSharpSafe = sharpExpression.getExpr().accept(this, arg);
        if (!isSharpSafe) {
            this.errorHandler.reportError("Sharp Error", sharpExpression);
        }
        return isSharpSafe;
    }

    @Override
    public Boolean visit(NopStatement nopStatement, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(TypeDenoter typeDenoter, SymbolTable arg) {
        return true;
    }

    @Override
    public Boolean visit(PrimitiveNodeType primitiveNodeType, SymbolTable arg) {
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
    public Boolean visit(ArrayConst emptyArrayExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(ArrayRead readArrayExpression, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(ReturnStatement returnStatement, SymbolTable arg) {
        return null;
    }

    @Override
    public Boolean visit(ArrayElementStatement arrayElementStatement, SymbolTable arg) {
        return null;
    }
}

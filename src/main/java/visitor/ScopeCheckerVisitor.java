package visitor;

import error.ErrorHandler;
import nodekind.NodeKind;
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
import syntax.typedenoter.PrimitiveTypeDenoter;

import java.util.List;

/*
TODO
Local inestato: (ora non funziona)
function add(x: float, y: float):float
        local
            i: float = 2.0;
            i = i + 1
            local
                i: float = 2.0;
                i = i + 1;
                return x + y + i
            end
        end
end

Variabile globale non visibile

global
    result: float = 0.0;
    buffer: float = 0.0
end

function add(x: float, y: float):float
        local
            i: float = 2.0;
            i = i + 1;
            result ==>;
            return x + y + i
    end
end
 */

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

    /**
     * Program visit
     *
     * @param program The program node
     * @param arg     Additional parameter
     * @return
     */
    @Override
    public Boolean visit(Program program, SymbolTable arg) {
        boolean isGlobalSafe = program.getGlobal().accept(this, arg);
        boolean areFunctionsSafe = this.checkContext(program.getFunctions(), arg);
        boolean isProgramSafe = isGlobalSafe && areFunctionsSafe;
        if (!isProgramSafe) {
            this.errorHandler.reportError("Program Error", program);
        }
        arg.exitScope();
        return isProgramSafe;
    }

    /**
     * Global visit
     *
     * @param global The program node
     * @param arg    Additional parameter
     * @return
     */
    @Override
    public Boolean visit(Global global, SymbolTable arg) {
        boolean areVarDeclsSafe = this.checkContext(global.getVarDecls(), arg);
        boolean isGlobalSafe = areVarDeclsSafe;
        if (!isGlobalSafe) {
            this.errorHandler.reportError("Global Error", global);
        }
        return isGlobalSafe;
    }

    /**
     * SimpleDef visit
     *
     * @param simpleDefFun The simple function node
     * @param arg          Additional parameter
     * @return
     */
    @Override
    public Boolean visit(SimpleDefFun simpleDefFun, SymbolTable arg) {
        boolean isSimpleFunctionSafe = simpleDefFun.getVariable().accept(this, arg);
        if (!isSimpleFunctionSafe) {
            this.errorHandler.reportYetDefined(simpleDefFun);
        } else {
            arg.enterScope();
            boolean isStatementsSafe = this.checkContext(simpleDefFun.getStatements(), arg);
            String name = simpleDefFun.getVariable().getValue();
            isSimpleFunctionSafe = isStatementsSafe;
            if (!isSimpleFunctionSafe) {
                this.errorHandler.reportError("Simple Function Error", simpleDefFun);
            }
            arg.exitScope();
            //isSimpleFunctionSafe = isSimpleFunctionSafe && !arg.probe(name);
            //if (isSimpleFunctionSafe) {
            //    arg.addEntry(name, new SymbolTableRecord(simpleDefFun.getTypeDenoter().typeFactory(), NodeKind.FUNCTION));
            //}
        }
        return isSimpleFunctionSafe;
    }

    /**
     * ComplexDefFun visit
     *
     * @param complexDefFun The complex function node
     * @param arg           Additional parameter
     * @return
     */
    @Override
    public Boolean visit(ComplexDefFun complexDefFun, SymbolTable arg) {
        boolean isComplexFunctionSafe = complexDefFun.getVariable().accept(this, arg);
        if (!isComplexFunctionSafe) {
            this.errorHandler.reportYetDefined(complexDefFun);
        } else {
            arg.enterScope();
            boolean isParDeclSafe = this.checkContext(complexDefFun.getParDecls(), arg);
            boolean isStatementsSafe = this.checkContext(complexDefFun.getStatements(), arg);
            String name = complexDefFun.getVariable().getValue();
            isComplexFunctionSafe = isStatementsSafe && isParDeclSafe;
            if (!isComplexFunctionSafe) {
                this.errorHandler.reportError("Simple Function Error", complexDefFun);
            }
            arg.exitScope();
            //isComplexFunctionSafe = isComplexFunctionSafe && !arg.probe(name);
            //if (isComplexFunctionSafe) {
            //    arg.addEntry(name, new SymbolTableRecord(complexDefFun.getTypeDenoter().typeFactory(), NodeKind.FUNCTION));
            //}
        }
        return isComplexFunctionSafe;
    }

    /**
     * ParDecl visit
     *
     * @param parDecl The param
     * @param arg     Additional parameter
     * @return
     */
    @Override
    public Boolean visit(ParDecl parDecl, SymbolTable arg) {
        boolean isParDeclSafe = parDecl.getVariable().accept(this, arg);
        if (!isParDeclSafe) {
            this.errorHandler.reportError("ParDecl Error", parDecl);
        } else {
            arg.addEntry(parDecl.getVariable().getValue(), new SymbolTableRecord(parDecl.getTypeDenoter().typeFactory(), NodeKind.VARIABLE));
        }
        return isParDeclSafe;
    }

    /**
     * VarDecl visit
     *
     * @param varDecl The variable
     * @param arg     Additional parameter
     * @return
     */
    @Override
    public Boolean visit(VarDecl varDecl, SymbolTable arg) {
        boolean isVarDeclSafe = varDecl.getVariable().accept(this, arg);
        if (!isVarDeclSafe) {
            this.errorHandler.reportError("VarDecl Error", varDecl);
        } else {
            isVarDeclSafe = !arg.probe(varDecl.getVariable().getValue());
            if (isVarDeclSafe) {
                arg.addEntry(varDecl.getVariable().getValue(), new SymbolTableRecord(varDecl.getTypeDenoter().typeFactory(), NodeKind.VARIABLE));
            }
        }
        return isVarDeclSafe;
    }

    /**
     * @param varInitValue The initial value of variable
     * @param arg          Additional parameter
     * @return
     */
    @Override
    public Boolean visit(VarInitValue varInitValue, SymbolTable arg) {
        boolean isVarInitValueSafe = varInitValue.getExpr().accept(this, arg);
        if (!isVarInitValueSafe) {
            this.errorHandler.reportError("VarInitValue Error", varInitValue);
        }
        return isVarInitValueSafe;
    }


    /**
     * WhileStatement visit
     *
     * @param whileStatement The while statement
     * @param arg            Additional parameter
     * @return
     */
    @Override
    public Boolean visit(WhileStatement whileStatement, SymbolTable arg) {
        boolean isExprSafe = whileStatement.getExpr().accept(this, arg);
        boolean areStatementsSafe = checkContext(whileStatement.getStatements(), arg);
        boolean isWhileSafe = isExprSafe && areStatementsSafe;
        if (!isWhileSafe) {
            this.errorHandler.reportError("While Statement Error", whileStatement);
        }
        return isWhileSafe;
    }

    /**
     * IfThenStatement visit
     *
     * @param ifThenStatement The If Then Statement
     * @param arg             Additional parameter
     * @return
     */
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

    /**
     * ifThenElseStatement visit
     *
     * @param ifThenElseStatements The If Then Statement
     * @param arg                  Additional parameter
     * @return
     */
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

    /**
     * forStatement visit
     *
     * @param forStatement The for statement
     * @param arg          Additional parameter
     * @return
     */
    @Override
    public Boolean visit(ForStatement forStatement, SymbolTable arg) {
        arg.enterScope();
        boolean areStatemetSafe = checkContext(forStatement.getStatements(), arg);
        boolean isAssignExprSafe = forStatement.getAssignExpr().accept(this, arg);
        boolean isAssignCommaSafe = forStatement.getCommaExpr().accept(this, arg);
        boolean isForSafe = areStatemetSafe && isAssignCommaSafe && isAssignExprSafe;
        arg.exitScope();
        return isForSafe;
    }

    /**
     * Local statement visit
     *
     * @param localStatement The local statement
     * @param arg            Additional parameter
     * @return
     */
    @Override
    public Boolean visit(LocalStatement localStatement, SymbolTable arg) {
        arg.enterScope();
        boolean areVarDeclSafe = checkContext(localStatement.getVarDecls(), arg);
        boolean areStatementsSafe = checkContext(localStatement.getStatements(), arg);
        boolean isLocalSafe = areStatementsSafe && areVarDeclSafe;
        if (!isLocalSafe) {
            this.errorHandler.reportError("Local Statement Error", localStatement);
        }
        arg.exitScope();
        return isLocalSafe;
    }

    /**
     * AssignStatement visit
     *
     * @param assignStatement The assign Statement
     * @param arg             Additional parameter
     * @return
     */
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


    /**
     * ReadStatement visit
     *
     * @param readStatement The readStatement
     * @param arg           Additional parameter
     * @return
     */
    @Override
    public Boolean visit(ReadStatement readStatement, SymbolTable arg) {
        boolean isReadStatementSafe = checkContext(readStatement.getIds(), arg);
        if (!isReadStatementSafe) {
            this.errorHandler.reportNotDefined(readStatement);
        }
        return isReadStatementSafe;
    }

    /**
     * WriteStatement visit
     *
     * @param writeStatement The writeStatement
     * @param arg            Additional parameter
     * @return
     */
    @Override
    public Boolean visit(WriteStatement writeStatement, SymbolTable arg) {
        boolean isWriteStatementSafe = checkContext(writeStatement.getExprs(), arg);
        if (!isWriteStatementSafe) {
            this.errorHandler.reportError("Write Statement Error", writeStatement);
        }
        return isWriteStatementSafe;
    }


    /**
     * FunctionCallstatement visit
     *
     * @param functionCallStatement The FunctionCallStatement
     * @param arg                   Additional parameter
     * @return
     */
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

    /**
     * FunctionCall visit
     *
     * @param functionCallExpression The FunctionCallStatement
     * @param arg                    Additional parameter
     * @return
     */
    @Override
    public Boolean visit(FunctionCall functionCallExpression, SymbolTable arg) {
        boolean isFunctionCallSafe = functionCallExpression.getId().accept(this, arg);
        if (!isFunctionCallSafe) {
            this.errorHandler.reportYetDefined(functionCallExpression);
        } else {
            boolean areStatementsSafe = checkContext(functionCallExpression.getExprs(), arg);
            if (!areStatementsSafe) {
                this.errorHandler.reportError("Function Call Error", functionCallExpression);
            }
        }
        return isFunctionCallSafe;
    }

    /**
     * Float value const
     *
     * @param floatConst The value
     * @param arg        Additional parameter
     * @return
     */
    @Override
    public Boolean visit(FloatConst floatConst, SymbolTable arg) {
        return true;
    }


    /**
     * @param stringConst The value
     * @param arg         Additional parameter
     * @return
     */
    @Override
    public Boolean visit(StringConst stringConst, SymbolTable arg) {
        return true;
    }

    /**
     * Integer value const
     *
     * @param integerConst The value
     * @param arg          Additional parameter
     * @return
     */
    @Override
    public Boolean visit(IntegerConst integerConst, SymbolTable arg) {
        return true;
    }


    /**
     * PlusOp visit
     *
     * @param plusOp The plusExpression
     * @param arg    Additional paramaeter
     * @return
     */
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

    /**
     * MinusOp visit
     *
     * @param minusOp The minusExpression
     * @param arg     Additional parameter
     * @return
     */
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

    /**
     * TimesOp visit
     *
     * @param timesOp The TimesExpression
     * @param arg     Additional parameter
     * @return
     */
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

    /**
     * DivOp visit
     *
     * @param divOp The DivExpression
     * @param arg   Additional parameter
     * @return
     */
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

    /**
     * AndOp visit
     *
     * @param andOp The AndExpression
     * @param arg   Additional parameter
     * @return
     */
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

    /**
     * OrOp visit
     *
     * @param orOp The AndExpression
     * @param arg  Additional parameter
     * @return
     */
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

    /**
     * GtOp visit
     *
     * @param gtOp The AndExpression
     * @param arg  Additional parameter
     * @return
     */
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

    /**
     * GeOp visit
     *
     * @param geOp The AndExpression
     * @param arg  Additional parameter
     * @return
     */
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

    /**
     * LtOp visit
     *
     * @param ltOp The AndExpression
     * @param arg  Additional parameter
     * @return
     */
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

    /**
     * LeOp visit
     *
     * @param leOp The AndExpression
     * @param arg  Additional parameter
     * @return
     */
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

    /**
     * EqOp visit
     *
     * @param eqOp The AndExpression
     * @param arg  Additional parameter
     * @return
     */
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

    /**
     * NeOp visit
     *
     * @param neOp The AndExpression
     * @param arg  Additional parameter
     * @return
     */
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

    /**
     * IMinusExpression
     *
     * @param uMinusExpression The AndExpression
     * @param arg              Additional parameter
     * @return
     */
    @Override
    public Boolean visit(UMinusExpression uMinusExpression, SymbolTable arg) {
        boolean isUMinusSafe = uMinusExpression.getMinus().accept(this, arg);
        if (!isUMinusSafe) {
            this.errorHandler.reportError("UMinus Expression Error", uMinusExpression);
        }
        return isUMinusSafe;
    }

    /**
     * Id
     *
     * @param id  The id
     * @param arg Additional parameter
     * @return
     */
    @Override
    public Boolean visit(Id id, SymbolTable arg) {
        return arg.lookup(id.getValue()).isPresent();
    }


    /**
     * Variable
     *
     * @param variable The Variable
     * @param arg      The Additional parameter
     * @return
     */
    @Override
    public Boolean visit(Variable variable, SymbolTable arg) {
        return arg.lookup(variable.getValue()).isEmpty();
    }

    /**
     * Nil value const
     *
     * @param nilConst The NilConst
     * @param arg      Additional parameter
     * @return
     */
    @Override
    public Boolean visit(NilConst nilConst, SymbolTable arg) {
        return true;
    }


    /**
     * Boolean value const
     *
     * @param booleanConst The boolenConst
     * @param arg          Additional parameter
     * @return
     */
    @Override
    public Boolean visit(BooleanConst booleanConst, SymbolTable arg) {
        return true;
    }

    /**
     * NotExpression
     *
     * @param notExpression The notExpression
     * @param arg           Additional parameter
     * @return
     */
    @Override
    public Boolean visit(NotExpression notExpression, SymbolTable arg) {
        return true;
    }

    /**
     * SharpExpression
     *
     * @param sharpExpression The sharpExpression
     * @param arg             The Additional parameter
     * @return
     */
    @Override
    public Boolean visit(SharpExpression sharpExpression, SymbolTable arg) {
        boolean isSharpSafe = sharpExpression.getExpr().accept(this, arg);
        if (!isSharpSafe) {
            this.errorHandler.reportError("Sharp Error", sharpExpression);
        }
        return isSharpSafe;
    }

    /**
     * NopStatement
     *
     * @param nopStatement The NopStatement
     * @param arg          The Additional parameter
     * @return
     */
    @Override
    public Boolean visit(NopStatement nopStatement, SymbolTable arg) {
        return true;
    }


    /**
     * PrimitiveTypeDenoter
     *
     * @param primitiveTypeDenoter Primitive type
     * @param arg                  Additional parameter
     * @return
     */
    @Override
    public Boolean visit(PrimitiveTypeDenoter primitiveTypeDenoter, SymbolTable arg) {
        return true;
    }

    /**
     * ArrayTypeDenoter
     *
     * @param arrayTypeDenoter The type of array
     * @param arg              Additional parameter
     * @return
     */
    @Override
    public Boolean visit(ArrayTypeDenoter arrayTypeDenoter, SymbolTable arg) {
        return true;
    }

    /**
     * FunctionTypeDenoter
     *
     * @param functionTypeDenoter The type
     * @param arg                 Additional parameter
     * @return
     */
    @Override
    public Boolean visit(FunctionTypeDenoter functionTypeDenoter, SymbolTable arg) {
        return true;
    }

    /**
     * @param emptyArrayExpression Type
     * @param arg                  Additional parameter
     * @return
     */
    @Override
    public Boolean visit(ArrayConst emptyArrayExpression, SymbolTable arg) {
        return true;
    }

    /**
     * ArrayRead visit
     *
     * @param readArrayExpression The readArray
     * @param arg                 Additional parameter
     * @return
     */
    @Override
    public Boolean visit(ArrayRead readArrayExpression, SymbolTable arg) {
        boolean isArrayElementSafe = readArrayExpression.getArrayElement().accept(this, arg);
        boolean isArrayExpressionSafe = readArrayExpression.getArrayName().accept(this, arg);
        boolean isArrayReadSafe = isArrayElementSafe && isArrayExpressionSafe;
        if (!isArrayReadSafe) {
            this.errorHandler.reportError("Array Statement Error", readArrayExpression);
        }
        return isArrayReadSafe;
    }

    /**
     * ReturnStatement visit
     *
     * @param returnStatement The returnStatement
     * @param arg             Additional parameter
     * @return
     */
    @Override
    public Boolean visit(ReturnStatement returnStatement, SymbolTable arg) {
        boolean isRetunStatementSafe = returnStatement.getExpr().accept(this, arg);
        if (!isRetunStatementSafe) {
            this.errorHandler.reportError("Return Statement Error", returnStatement);
        }
        return isRetunStatementSafe;
    }

    /**
     * ArrayElement visit
     *
     * @param arrayElementStatement The arrayElement statement
     * @param arg                   Additional parameter
     * @return
     */
    @Override
    public Boolean visit(ArrayElementStatement arrayElementStatement, SymbolTable arg) {
        boolean isArrayAssignSafe = arrayElementStatement.getArrayAssign().accept(this, arg);
        boolean isArrayExprSafe = arrayElementStatement.getArrayExpr().accept(this, arg);
        boolean isArrayPointSafe = arrayElementStatement.getArrayPoint().accept(this, arg);
        boolean isarrayElementStatementSafe = isArrayAssignSafe && isArrayExprSafe && isArrayPointSafe;
        if (!isarrayElementStatementSafe) {
            this.errorHandler.reportError("ArrayElement Statement Error", arrayElementStatement);
        }
        return isarrayElementStatementSafe;
    }
}

package com.passaro.mypallene.visitor;

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

public interface Visitor<T, P> {

    /**
     * Visit for the program node
     *
     * @param program The program node
     * @param arg     Additional parameter
     */
    T visit(Program program, P arg);

    /**
     * Visit for the global node
     *
     * @param global The program node
     * @param arg    Additional parameter
     */
    T visit(Global global, P arg);

    /**
     * Visit for the simple function node
     *
     * @param simpleDefFun The simple function node
     * @param arg          Additional parameter
     */
    T visit(SimpleDefFun simpleDefFun, P arg);

    /**
     * Visit for the complex node
     *
     * @param complexDefFun The complex function node
     * @param arg           Additional parameter
     */
    T visit(ComplexDefFun complexDefFun, P arg);

    /**
     * Visit for parDecl node
     *
     * @param parDecl The param
     * @param arg     Additional parameter
     */
    T visit(ParDecl parDecl, P arg);

    /**
     * Visit for the VarDecl node
     *
     * @param varDecl The variable
     * @param arg     Additional parameter
     */
    T visit(VarDecl varDecl, P arg);

    /**
     * Visit for the  varInitValue node
     *
     * @param varInitValue The initial value of variable
     * @param arg          Additional parameter
     */
    T visit(VarInitValue varInitValue, P arg);


    /**
     * @param primitiveTypeDenoter Primitive type
     * @param arg                  Additional parameter
     */
    T visit(PrimitiveTypeDenoter primitiveTypeDenoter, P arg);


    /**
     * Visit For arrayType node
     *
     * @param arrayTypeDenoter The type of array
     * @param arg              Additional parameter
     */
    T visit(ArrayTypeDenoter arrayTypeDenoter, P arg);

    /**
     * Visit for functionType node
     *
     * @param functionTypeDenoter The type
     * @param arg                 Additional parameter
     */
    T visit(FunctionTypeDenoter functionTypeDenoter, P arg);

    /**
     * Visit for WhileStatement node
     *
     * @param whileStatement The while statement
     * @param arg            Additional parameter
     */
    T visit(WhileStatement whileStatement, P arg);

    /**
     * Visit for IfThenStatement
     *
     * @param ifThenStatement The If Then Statement
     * @param arg             Additional parameter
     */
    T visit(IfThenStatement ifThenStatement, P arg);

    /**
     * Visit for IfThenElseStatement
     *
     * @param ifThenElseStatements The If Then Statement
     * @param arg                  Additional parameter
     */
    T visit(IfThenElseStatement ifThenElseStatements, P arg);

    /**
     * Visit for forStatement node
     *
     * @param forStatement The for statement
     * @param arg          Additional parameter
     */
    T visit(ForStatement forStatement, P arg);

    /**
     * Visit for localStatement node
     *
     * @param localStatement The local statement
     * @param arg            Additional parameter
     */
    T visit(LocalStatement localStatement, P arg);

    /**
     * Visit for AssignStatement node
     *
     * @param assignStatement The assign Statement
     * @param arg             Additional parameter
     */
    T visit(AssignStatement assignStatement, P arg);


    /**
     * Visit for arrayElementStatement node
     *
     * @param arrayElementStatement The arrayElement statement
     * @param arg                   Additional parameter
     */
    T visit(ArrayElementStatement arrayElementStatement, P arg);

    /**
     * Visit for functionCallStatement node
     *
     * @param functionCallStatement The FunctionCallStatement
     * @param arg                   Additional parameter
     */
    T visit(FunctionCallStatement functionCallStatement, P arg);

    /**
     * Visit for ReadStatement node
     *
     * @param readStatement The readStatement
     * @param arg           Additional parameter
     */
    T visit(ReadStatement readStatement, P arg);


    /**
     * Visit for writeStatement node
     *
     * @param writeStatement The writeStatement
     * @param arg            Additional parameter
     */
    T visit(WriteStatement writeStatement, P arg);

    /**
     * Visit for returnStatement node
     *
     * @param returnStatement The returnStatement
     * @param arg             Additional parameter
     */
    T visit(ReturnStatement returnStatement, P arg);


    /**
     * @param floatConst The value
     * @param arg        Additional parameter
     */
    T visit(FloatConst floatConst, P arg);

    /**
     * @param stringConst The value
     * @param arg         Additional parameter
     */
    T visit(StringConst stringConst, P arg);

    /**
     * @param integerConst The value
     * @param arg          Additional parameter
     */
    T visit(IntegerConst integerConst, P arg);

    /**
     * @param emptyArrayExpression Type
     * @param arg                  Additional parameter
     */
    T visit(ArrayConst emptyArrayExpression, P arg);

    /**
     * @param readArrayExpression The readArray
     * @param arg                 Additional parameter
     */
    T visit(ArrayRead readArrayExpression, P arg);

    /**
     * @param functionCallExpression The FunctionCallStatement
     * @param arg                    Additional parameter
     */
    T visit(FunctionCall functionCallExpression, P arg);


    /**
     * @param plusOp The plusExpression
     * @param arg    Additional paramaeter
     */
    T visit(PlusOp plusOp, P arg);

    /**
     * @param minusOp The minusExpression
     * @param arg     Additional parameter
     */
    T visit(MinusOp minusOp, P arg);

    /**
     * @param timesOp The TimesExpression
     * @param arg     Additional parameter
     */
    T visit(TimesOp timesOp, P arg);

    /**
     * @param divOp The DivExpression
     * @param arg   Additional parameter
     */
    T visit(DivOp divOp, P arg);

    /**
     * @param andOp The AndExpression
     * @param arg   Additional parameter
     */
    T visit(AndOp andOp, P arg);

    /**
     * @param orOp The AndExpression
     * @param arg  Additional parameter
     */
    T visit(OrOp orOp, P arg);

    /**
     * @param gtOp The AndExpression
     * @param arg  Additional parameter
     */
    T visit(GtOp gtOp, P arg);

    /**
     * @param geOp The AndExpression
     * @param arg  Additional parameter
     */
    T visit(GeOp geOp, P arg);

    /**
     * @param ltOp The AndExpression
     * @param arg  Additional parameter
     */
    T visit(LtOp ltOp, P arg);

    /**
     * @param leOp The AndExpression
     * @param arg  Additional parameter
     */
    T visit(LeOp leOp, P arg);

    /**
     * @param eqOp The AndExpression
     * @param arg  Additional parameter
     */
    T visit(EqOp eqOp, P arg);

    /**
     * @param neOp The AndExpression
     * @param arg  Additional parameter
     */
    T visit(NeOp neOp, P arg);

    /**
     * @param uMinusExpression The AndExpression
     * @param arg              Additional parameter
     */
    T visit(UMinusExpression uMinusExpression, P arg);

    /**
     * @param nilConst The NilConst
     * @param arg      Additional parameter
     */
    T visit(NilConst nilConst, P arg);

    /**
     * @param id  The id
     * @param arg Additional parameter
     */
    T visit(Id id, P arg);

    /**
     * @param booleanConst The boolenConst
     * @param arg          Additional parameter
     */
    T visit(BooleanConst booleanConst, P arg);

    /**
     * @param notExpression The notExpression
     * @param arg           Additional parameter
     */
    T visit(NotExpression notExpression, P arg);

    /**
     * @param sharpExpression The sharpExpression
     * @param arg             The Additional parameter
     */
    T visit(SharpExpression sharpExpression, P arg);


    /**
     * @param nopStatement The NopStatement
     * @param arg          The Additional parameter
     */
    T visit(NopStatement nopStatement, P arg);


    /**
     * @param variable The Variable
     * @param arg      The Additional parameter
     */
    T visit(Variable variable, P arg);
}

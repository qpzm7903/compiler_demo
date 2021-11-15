package com.qpzm7903.compiler.visitor;

import com.qpzm7903.compiler.antlr4.PlayScriptBaseVisitor;
import com.qpzm7903.compiler.antlr4.PlayScriptParser;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-12-21:32
 */
public class SimpleVisitor extends PlayScriptBaseVisitor<Object> {
    Map<String, Object> variableMap = new HashMap<>();

    @Override
    public Object visitStatement(PlayScriptParser.StatementContext ctx) {
        if (!Objects.isNull(ctx.statementExpression)) {
            return visitExpression(ctx.expression());
        } else if (!Objects.isNull(ctx.IF())) {
            Boolean condition = (Boolean) visitParExpression(ctx.parExpression());
            if (condition) {
                return visitStatement(ctx.statement(0));
            } else if (!Objects.isNull(ctx.statement(0))) {
                return visitStatement(ctx.statement(1));
            }
        } else if (!Objects.isNull(ctx.FOR())) {
            PlayScriptParser.ForControlContext forControlContext = ctx.forControl();
            PlayScriptParser.ForInitContext forInitContext = forControlContext.forInit();
            if (forInitContext != null) {
                Object o = visitForInit(forInitContext);
            }
            Object result = null;
            while (true) {
                if (forControlContext.expression() != null) {
                    Boolean condition = (Boolean) visitExpression(forControlContext.expression());
                    if (condition) {
                        result = visitStatement(ctx.statement(0));
                        if (forControlContext.forUpdate != null) {
                            visitExpressionList(forControlContext.forUpdate);
                        }
                    } else {
                        break;
                    }

                }
            }
            return result;
        } else if (ctx.WHILE() != null) {
            if (ctx.parExpression().expression() != null && ctx.statement(0) != null) {
                Object result = null;
                while (true) {
                    Boolean condition = (Boolean) visitExpression(ctx.parExpression().expression());
                    if (condition) {
                        result = visitStatement(ctx.statement(0));
                    } else {
                        break;

                    }
                }
                return result;
            }
        }
        if (!Objects.isNull(ctx.block())) {
            return visitBlock(ctx.block());
        }
        return visitExpression(ctx.expression());
    }

    @Override
    public Object visitBlock(PlayScriptParser.BlockContext ctx) {
        return visitBlockStatements(ctx.blockStatements());
    }

    @Override
    public Object visitParExpression(PlayScriptParser.ParExpressionContext ctx) {
        return visitExpression(ctx.expression());
    }

    @Override
    public Object visitBlockStatement(PlayScriptParser.BlockStatementContext ctx) {
        Object result = null;
        if (!Objects.isNull(ctx.variableDeclarators())) {
            result = visitVariableDeclarators(ctx.variableDeclarators());
            return result;
        }
        System.out.println();
        return super.visitBlockStatement(ctx);
    }

    @Override
    public Object visitTypeType(PlayScriptParser.TypeTypeContext ctx) {
        System.out.println();
        return super.visitTypeType(ctx);
    }

    @Override
    public Object visitIntegerLiteral(PlayScriptParser.IntegerLiteralContext ctx) {

        Object rtn = null;
        if (ctx.DECIMAL_LITERAL() != null) {
            rtn = Integer.valueOf(ctx.DECIMAL_LITERAL().getText());
        }
        return rtn;

    }

    @Override
    public Object visitVariableDeclarator(PlayScriptParser.VariableDeclaratorContext ctx) {
        Object result = null;
        String variableName = (String) visitVariableDeclaratorId(ctx.variableDeclaratorId());

        if (!Objects.isNull(ctx.variableInitializer())) {
            result = visitVariableInitializer(ctx.variableInitializer());
        }
        variableMap.put(variableName, result);
        System.out.println(ctx);
        return result;
    }

    @Override
    public Object visitVariableDeclaratorId(PlayScriptParser.VariableDeclaratorIdContext ctx) {
        TerminalNode identifier = ctx.IDENTIFIER();

        return identifier.getText();
    }

    @Override
    public Object visitVariableInitializer(PlayScriptParser.VariableInitializerContext ctx) {

        return visitExpression(ctx.expression());
    }

    @Override
    public Object visitVariableDeclarators(PlayScriptParser.VariableDeclaratorsContext ctx) {
        Object result = null;
        for (PlayScriptParser.VariableDeclaratorContext child : ctx.variableDeclarator()) {
            result = visitVariableDeclarator(child);
            return result;

        }
        System.out.println(ctx);
        return super.visitVariableDeclarators(ctx);
    }

    @Override
    public Object visitPrimary(PlayScriptParser.PrimaryContext context) {
        System.out.println(context);
        if (!Objects.isNull(context.IDENTIFIER())) {
            return variableMap.get(context.IDENTIFIER().getText());
        }
        if (context.children.size() >= 3) {
            return visitExpression(context.expression());
        }
        return super.visitPrimary(context);
    }


    @Override
    public Object visitExpression(PlayScriptParser.ExpressionContext ctx) {
        if (ctx.bop != null && ctx.expression().size() >= 2) {
            switch (ctx.bop.getType()) {
                case PlayScriptParser.ADD:
                    Object left = this.visitExpression(ctx.expression(0));
                    Object right = this.visitExpression(ctx.expression(1));
                    return ((int) (left)) + (int) (right);
                case PlayScriptParser.MUL:
                    left = this.visitExpression(ctx.expression(0));
                    right = this.visitExpression(ctx.expression(1));
                    return ((int) (left)) * (int) (right);
                case PlayScriptParser.DIV:
                    left = this.visitExpression(ctx.expression(0));
                    right = this.visitExpression(ctx.expression(1));
                    return ((int) (left)) / (int) (right);
                case PlayScriptParser.SUB:
                    left = this.visitExpression(ctx.expression(0));
                    right = this.visitExpression(ctx.expression(1));
                    return ((int) (left)) - (int) (right);
                case PlayScriptParser.GT:
                    left = this.visitExpression(ctx.expression(0));
                    right = this.visitExpression(ctx.expression(1));
                    return ((int) (left)) > (int) (right);
                case PlayScriptParser.LT:
                    left = this.visitExpression(ctx.expression(0));
                    right = this.visitExpression(ctx.expression(1));
                    return ((int) (left)) < (int) (right);
                case PlayScriptParser.LE:
                    left = this.visitExpression(ctx.expression(0));
                    right = this.visitExpression(ctx.expression(1));
                    return ((int) (left)) <= (int) (right);
                case PlayScriptParser.GE:
                    left = this.visitExpression(ctx.expression(0));
                    right = this.visitExpression(ctx.expression(1));
                    return ((int) (left)) >= (int) (right);
                case PlayScriptParser.EQUAL:
                    left = this.visitExpression(ctx.expression(0));
                    right = this.visitExpression(ctx.expression(1));
                    return ((int) (left)) == (int) (right);
                case PlayScriptParser.NOTEQUAL:
                    left = this.visitExpression(ctx.expression(0));
                    right = this.visitExpression(ctx.expression(1));
                    return ((int) (left)) != (int) (right);
                case PlayScriptParser.ASSIGN:
                    String variableName = ctx.expression(0).primary().IDENTIFIER().getText();
                    right = this.visitExpression(ctx.expression(1));
                    variableMap.put(variableName, right);
                    return right;
                case PlayScriptParser.ADD_ASSIGN:
                    variableName = ctx.expression(0).primary().IDENTIFIER().getText();
                    Integer value = (Integer) variableMap.get(variableName);
                    right = this.visitExpression(ctx.expression(1));
                    variableMap.put(variableName, value + (Integer) right);

                    return variableMap.get(variableName);

                case PlayScriptParser.INC:
                    variableName = ctx.expression(0).primary().IDENTIFIER().getText();
                    value = (Integer) variableMap.get(variableName);
                    variableMap.put(variableName, value + 1);
                    return variableMap.get(variableName);
                default:
                    throw new RuntimeException("not support for " + ctx.bop.getType());
            }
        }
        if (!Objects.isNull(ctx.postfix)) {
            switch (ctx.postfix.getType()) {
                case PlayScriptParser.INC:
                    String variableName = ctx.expression(0).primary().IDENTIFIER().getText();
                    Integer value = (Integer) variableMap.get(variableName);
                    variableMap.put(variableName, value + 1);
                    return variableMap.get(variableName);
                case PlayScriptParser.DEC:
                    variableName = ctx.expression(0).primary().IDENTIFIER().getText();
                    value = (Integer) variableMap.get(variableName);
                    variableMap.put(variableName, value - 1);
                    return variableMap.get(variableName);
            }

        }
        return super.visitExpression(ctx);
    }

    @Override
    public Object visitProg(PlayScriptParser.ProgContext ctx) {
        System.out.println(ctx);
        return visitBlockStatements(ctx.blockStatements());
    }

    @Override
    public Object visitBlockStatements(PlayScriptParser.BlockStatementsContext ctx) {
        Object result = null;
        for (PlayScriptParser.BlockStatementContext child : ctx.blockStatement()) {
            result = visitBlockStatement(child);
        }
        return result;
    }
}

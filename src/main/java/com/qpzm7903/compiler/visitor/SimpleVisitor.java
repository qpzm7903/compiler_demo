package com.qpzm7903.compiler.visitor;

import com.qpzm7903.compiler.antlr4.PlayScriptBaseVisitor;
import com.qpzm7903.compiler.antlr4.PlayScriptParser;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-12-21:32
 */
public class SimpleVisitor extends PlayScriptBaseVisitor<Object> {

    @Override
    public Object visitStatement(PlayScriptParser.StatementContext ctx) {
        System.out.println(ctx);
        return super.visitStatement(ctx);
    }

    @Override
    public Object visitBlockStatement(PlayScriptParser.BlockStatementContext ctx) {
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
        System.out.println(ctx);
        return super.visitVariableDeclarator(ctx);
    }

    @Override
    public Object visitVariableDeclarators(PlayScriptParser.VariableDeclaratorsContext ctx) {
        System.out.println(ctx);
        return super.visitVariableDeclarators(ctx);
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
                default:
                    throw new RuntimeException("not support for " + ctx.bop.getType());
            }
        }
        return super.visitExpression(ctx);
    }

    @Override
    public Object visitProg(PlayScriptParser.ProgContext ctx) {
        System.out.println(ctx);
        return super.visitProg(ctx);
    }

    @Override
    public Object visitExpressionList(PlayScriptParser.ExpressionListContext ctx) {
        System.out.println(ctx);
        return super.visitExpressionList(ctx);
    }


}

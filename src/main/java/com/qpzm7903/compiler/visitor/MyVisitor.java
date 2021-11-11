package com.qpzm7903.compiler.visitor;

import com.qpzm7903.compiler.expr.DemoBaseVisitor;
import com.qpzm7903.compiler.expr.DemoParser;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-12-7:14
 */

public class MyVisitor extends DemoBaseVisitor<Object> {
    @Override
    public Object visitPlus(final DemoParser.PlusContext ctx) {

        Object left = visit(ctx.left);
        Object right = visit(ctx.right);
        if (left instanceof Number && right instanceof Number) {
            return ((Number) left).intValue() + ((Number) right).intValue();
        } else if (left instanceof String && right instanceof String) {
            return Integer.parseInt((String) left) + Integer.parseInt((String) right);
        }
        throw new RuntimeException("add left right must be number");
    }

    @Override
    public String visitLiteralNumber(final DemoParser.LiteralNumberContext ctx) {
        return ctx.getText();
    }
}

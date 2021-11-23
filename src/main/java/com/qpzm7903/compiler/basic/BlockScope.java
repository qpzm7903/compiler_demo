package com.qpzm7903.compiler.basic;

import org.antlr.v4.runtime.ParserRuleContext;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-16-7:52
 */
public class BlockScope extends Scope {
    //给block编号的数字
    private static int index = 1;

    protected BlockScope() {
        this.name = "block" + index++;
    }

    public BlockScope(Scope scope, ParserRuleContext context) {
        this.name = "block" + index++;
        this.scope = scope;
        this.ctx = context;
    }

    @Override
    public String toString() {
        return "Block " + name;
    }
}

package com.qpzm7903.compiler.basic;

import org.antlr.v4.runtime.ParserRuleContext;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-16-20:53
 */
public class Variable extends Symbol {
    protected Type type;
    protected Object defaultValue;

    public Variable(Type type, Object defaultValue, ParserRuleContext context) {
        this.context = context;
        this.type = type;
        this.defaultValue = defaultValue;
    }
}
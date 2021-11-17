package com.qpzm7903.compiler.basic;

import org.antlr.v4.runtime.ParserRuleContext;

/**
 * 编译过程产生的变量、函数、类、块，都被称为符号
 *
 * @author qpzm7903
 * @since 2021-11-16-7:50
 */
public class Symbol {

    /**
     * 符号的名称
     */
    protected String name;

    protected Scope scope;

    protected int visibility = 0;

    protected ParserRuleContext context;

    public String getName() {
        return name;
    }

    public Scope getScope() {
        return scope;
    }

}
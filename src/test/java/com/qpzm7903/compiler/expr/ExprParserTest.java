package com.qpzm7903.compiler.expr;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-11-23:04
 */
class ExprParserTest {
    @Test
    void test() {
        extracted("h");
        extracted("1");
    }

    private void extracted(String h) {
        ANTLRInputStream input = new ANTLRInputStream(h);
        ExprLexer exprLexer = new ExprLexer(input);
        CommonTokenStream commonTokenStream = new CommonTokenStream(exprLexer);
        ExprParser exprParser = new ExprParser(commonTokenStream);
        exprParser.prog();
    }

}
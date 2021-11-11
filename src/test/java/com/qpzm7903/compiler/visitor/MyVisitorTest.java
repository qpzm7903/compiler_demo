package com.qpzm7903.compiler.visitor;

import com.qpzm7903.compiler.expr.DemoLexer;
import com.qpzm7903.compiler.expr.DemoParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-12-7:17
 */
class MyVisitorTest {
    private final MyVisitor myVisitor = new MyVisitor();

    @Test
    public void visitLiteralNumber_returnsTextValueOfNumber() throws Exception {
        // setup
        final DemoParser.LiteralNumberContext literalNumberNode = mock(DemoParser.LiteralNumberContext.class);
        when(literalNumberNode.getText()).thenReturn("42");

        // execution
        final String actual = myVisitor.visitLiteralNumber(literalNumberNode);

        // evaluation
        assertEquals(actual, "42");
    }

    @Test
    void test_() {
        ANTLRInputStream input = new ANTLRInputStream("2+2");
        Object compile = compile(input);
        Assertions.assertThat(compile).isEqualTo(4);
        System.out.println(compile);
    }
    public static Object compile(ANTLRInputStream input) {
        DemoLexer lexer = new DemoLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DemoParser parser = new DemoParser(tokens);
        ParseTree tree = parser.expression();
        return new MyVisitor().visit(tree);
    }

}
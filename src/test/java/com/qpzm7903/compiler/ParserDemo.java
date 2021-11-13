package com.qpzm7903.compiler;

import com.qpzm7903.compiler.antlr4.PlayScriptBaseListener;
import com.qpzm7903.compiler.antlr4.PlayScriptLexer;
import com.qpzm7903.compiler.antlr4.PlayScriptParser;
import com.qpzm7903.compiler.visitor.SimpleVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-13-18:17
 */
public class ParserDemo {
    /**
     * 执行脚本，并打印输出AST和求值过程。
     *
     * @param script
     */
    int evaluate(String script) {
        ANTLRInputStream input = new ANTLRInputStream(script);
        Object compile = compile(input);
        return (int) compile;
//        ASTNode rootNode = parse(script);
//        dumpAST(rootNode, "");
//        return evaluate(rootNode, "");
    }

    public static Object compile(ANTLRInputStream input) {
        PlayScriptLexer lexer = new PlayScriptLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PlayScriptParser parser = new PlayScriptParser(tokens);
        ParseTree tree = parser.expression();
        return new SimpleVisitor().visit(tree);
    }

    @Test
    void test_visitor() {
        evaluate("int a = 10;");
    }

    @Test
    void test() {
        String script = "int a = 10;";
        ANTLRInputStream input = new ANTLRInputStream(script);
        PlayScriptLexer lexer = new PlayScriptLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PlayScriptParser parser = new PlayScriptParser(tokens);
        ParseTree tree = parser.prog();
        Object visit = new SimpleVisitor().visit(tree);
        System.out.println(visit);


    }

    @Test
    void test_2() {
        String script = "1+2";
        ANTLRInputStream input = new ANTLRInputStream(script);
        PlayScriptLexer lexer = new PlayScriptLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PlayScriptParser parser = new PlayScriptParser(tokens);
        ParseTree tree = parser.expression();


    }
}

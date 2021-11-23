package com.qpzm7903.compiler.semantic;

import com.qpzm7903.compiler.antlr4.PlayScriptLexer;
import com.qpzm7903.compiler.antlr4.PlayScriptParser;
import com.qpzm7903.compiler.basic.AnnotatedTree;
import com.qpzm7903.compiler.basic.TypeAndScopeScanner;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-22-22:35
 */
class TypeAndScopeScannerTest {
    @Test
    void test() {
        String script = "int a = 10;";
        PlayScriptLexer playScriptLexer = new PlayScriptLexer(CharStreams.fromString(script));
        CommonTokenStream tokens = new CommonTokenStream(playScriptLexer);
        PlayScriptParser playScriptParser = new PlayScriptParser(tokens);
        PlayScriptParser.ProgContext prog = playScriptParser.prog();
        ParseTreeWalker walker = new ParseTreeWalker();
        AnnotatedTree at = new AnnotatedTree();
        walker.walk(new TypeAndScopeScanner(at), prog);
    }

}
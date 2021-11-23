package com.qpzm7903.compiler.basic;

import com.qpzm7903.compiler.antlr4.PlayScriptBaseListener;
import com.qpzm7903.compiler.antlr4.PlayScriptParser;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Stack;

/**
 * 解析出类型（包括函数）和作用域
 *
 * @author qpzm7903
 * @since 2021-11-22-22:30
 */
public class TypeAndScopeScanner extends PlayScriptBaseListener {
    private Stack<Scope> scopeStack = new Stack<>();
    private AnnotatedTree at = null;

    public TypeAndScopeScanner(AnnotatedTree at) {
        this.at = at;
    }

    private Scope pushScope(Scope scope, ParserRuleContext ctx) {
        at.node2Scope.put(ctx, scope);
        scope.ctx = ctx;

        scopeStack.push(scope);
        return scope;
    }

    //从栈中弹出scope
    private void popScope() {
        scopeStack.pop();
    }

    //在遍历树的过程中，当前的Scope
    private Scope currentScope() {
        if (scopeStack.size() > 0) {
            return scopeStack.peek();
        } else {
            return null;
        }
    }

    @Override
    public void enterProg(PlayScriptParser.ProgContext ctx) {
        System.out.println(ctx);
        Namespace namespace = new Namespace(currentScope(), ctx, "");

        pushScope(namespace, ctx);
    }

    @Override
    public void exitProg(PlayScriptParser.ProgContext ctx) {
        popScope();
    }

    @Override
    public void enterBlock(PlayScriptParser.BlockContext ctx) {
        if (ctx.parent instanceof PlayScriptParser.FunctionBodyContext) {
            //对于函数，不需要再额外建一个scope
            return;
        }
        BlockScope scope = new BlockScope(currentScope(), ctx);
        currentScope().addSymbol(scope);
        pushScope(scope, ctx);
    }

    @Override
    public void exitBlock(PlayScriptParser.BlockContext ctx) {
        if (ctx.parent instanceof PlayScriptParser.FunctionBodyContext) {
            // 函数不用
            return;
        }
        popScope();
    }

    @Override
    public void enterStatement(PlayScriptParser.StatementContext ctx) {
        //为for建立额外的Scope
        if (ctx.FOR() != null) {
            BlockScope scope = new BlockScope(currentScope(), ctx);
            currentScope().addSymbol(scope);
            pushScope(scope, ctx);
        }
    }

    @Override
    public void exitStatement(PlayScriptParser.StatementContext ctx) {
        //释放for语句的外层scope
        if (ctx.FOR() != null) {
            popScope();
        }
    }

    @Override
    public void enterFunctionDeclaration(PlayScriptParser.FunctionDeclarationContext ctx) {
        String idName = ctx.IDENTIFIER().getText();

        //注意：目前funtion的信息并不完整，参数要等到TypeResolver.java中去确定。
        Function function = new Function(idName, currentScope(), ctx);

        at.types.add(function);

        currentScope().addSymbol(function);

        // 创建一个新的scope
        pushScope(function, ctx);
    }

    @Override
    public void exitFunctionDeclaration(PlayScriptParser.FunctionDeclarationContext ctx) {
        popScope();
    }

}

// Generated from com\qpzm7903\compiler\expr\Demo.g4 by ANTLR 4.5
package com.qpzm7903.compiler.expr;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DemoParser}.
 */
public interface DemoListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code Plus}
	 * labeled alternative in {@link DemoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPlus(DemoParser.PlusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Plus}
	 * labeled alternative in {@link DemoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPlus(DemoParser.PlusContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralNumber}
	 * labeled alternative in {@link DemoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLiteralNumber(DemoParser.LiteralNumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralNumber}
	 * labeled alternative in {@link DemoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLiteralNumber(DemoParser.LiteralNumberContext ctx);
}
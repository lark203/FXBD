// Generated from F:/project/java/FXEditor/src/main/java/com/xx/Rows/lexer/Rows.g4 by ANTLR 4.13.2
package com.xx.antlr.Rows.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link RowsParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface RowsVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link RowsParser#props}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProps(RowsParser.PropsContext ctx);
	/**
	 * Visit a parse tree produced by {@link RowsParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(RowsParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link RowsParser#row}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRow(RowsParser.RowContext ctx);
}
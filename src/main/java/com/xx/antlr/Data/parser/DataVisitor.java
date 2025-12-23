// Generated from F:/project/java/FXEditor/src/main/java/com/xx/Data/lexer/Data.g4 by ANTLR 4.13.2
package com.xx.antlr.Data.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link DataParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface DataVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link DataParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(DataParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link DataParser#group}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroup(DataParser.GroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link DataParser#sequence}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSequence(DataParser.SequenceContext ctx);
}
// Generated from F:/project/java/FXEditor/src/main/java/com/xx/cymbol/lexer/cymbol.g4 by ANTLR 4.13.2
package com.xx.antlr.cymbol.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link cymbolParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface cymbolVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link cymbolParser#props}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProps(cymbolParser.PropsContext ctx);
	/**
	 * Visit a parse tree produced by {@link cymbolParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(cymbolParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link cymbolParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(cymbolParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link cymbolParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(cymbolParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link cymbolParser#functionDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDecl(cymbolParser.FunctionDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link cymbolParser#formalParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameters(cymbolParser.FormalParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link cymbolParser#formalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameter(cymbolParser.FormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link cymbolParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(cymbolParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link cymbolParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat(cymbolParser.StatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Call}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(cymbolParser.CallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Not}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNot(cymbolParser.NotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Mult}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMult(cymbolParser.MultContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSub(cymbolParser.AddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Equal}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqual(cymbolParser.EqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Var}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(cymbolParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(cymbolParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Index}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndex(cymbolParser.IndexContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Negate}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegate(cymbolParser.NegateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Int}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(cymbolParser.IntContext ctx);
	/**
	 * Visit a parse tree produced by {@link cymbolParser#exprList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprList(cymbolParser.ExprListContext ctx);
}
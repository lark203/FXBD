// Generated from F:/project/java/FXBD/src/main/java/com/xx/cymbol/lexer/cymbol.g4 by ANTLR 4.13.2
package com.xx.antlr.cymbol.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link cymbolParser}.
 */
public interface cymbolListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link cymbolParser#props}.
	 * @param ctx the parse tree
	 */
	void enterProps(cymbolParser.PropsContext ctx);
	/**
	 * Exit a parse tree produced by {@link cymbolParser#props}.
	 * @param ctx the parse tree
	 */
	void exitProps(cymbolParser.PropsContext ctx);
	/**
	 * Enter a parse tree produced by {@link cymbolParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(cymbolParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link cymbolParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(cymbolParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link cymbolParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void enterVarDecl(cymbolParser.VarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link cymbolParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void exitVarDecl(cymbolParser.VarDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link cymbolParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(cymbolParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link cymbolParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(cymbolParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link cymbolParser#functionDecl}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDecl(cymbolParser.FunctionDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link cymbolParser#functionDecl}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDecl(cymbolParser.FunctionDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link cymbolParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameters(cymbolParser.FormalParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link cymbolParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameters(cymbolParser.FormalParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link cymbolParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameter(cymbolParser.FormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link cymbolParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameter(cymbolParser.FormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link cymbolParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(cymbolParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link cymbolParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(cymbolParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link cymbolParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(cymbolParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link cymbolParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(cymbolParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Call}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCall(cymbolParser.CallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Call}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCall(cymbolParser.CallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Not}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNot(cymbolParser.NotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Not}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNot(cymbolParser.NotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Mult}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMult(cymbolParser.MultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Mult}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMult(cymbolParser.MultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddSub(cymbolParser.AddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddSub(cymbolParser.AddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Equal}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEqual(cymbolParser.EqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Equal}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEqual(cymbolParser.EqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Var}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterVar(cymbolParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Var}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitVar(cymbolParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParens(cymbolParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParens(cymbolParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Index}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIndex(cymbolParser.IndexContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Index}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIndex(cymbolParser.IndexContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Negate}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNegate(cymbolParser.NegateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Negate}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNegate(cymbolParser.NegateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Int}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterInt(cymbolParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Int}
	 * labeled alternative in {@link cymbolParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitInt(cymbolParser.IntContext ctx);
	/**
	 * Enter a parse tree produced by {@link cymbolParser#exprList}.
	 * @param ctx the parse tree
	 */
	void enterExprList(cymbolParser.ExprListContext ctx);
	/**
	 * Exit a parse tree produced by {@link cymbolParser#exprList}.
	 * @param ctx the parse tree
	 */
	void exitExprList(cymbolParser.ExprListContext ctx);
}
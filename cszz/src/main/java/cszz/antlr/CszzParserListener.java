// Generated from CszzParser.g4 by ANTLR 4.5.1
package cszz.antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CszzParser}.
 */
public interface CszzParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CszzParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void enterCompilationUnit(CszzParser.CompilationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void exitCompilationUnit(CszzParser.CompilationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#scriptDef}.
	 * @param ctx the parse tree
	 */
	void enterScriptDef(CszzParser.ScriptDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#scriptDef}.
	 * @param ctx the parse tree
	 */
	void exitScriptDef(CszzParser.ScriptDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#classDef}.
	 * @param ctx the parse tree
	 */
	void enterClassDef(CszzParser.ClassDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#classDef}.
	 * @param ctx the parse tree
	 */
	void exitClassDef(CszzParser.ClassDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#importDecl}.
	 * @param ctx the parse tree
	 */
	void enterImportDecl(CszzParser.ImportDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#importDecl}.
	 * @param ctx the parse tree
	 */
	void exitImportDecl(CszzParser.ImportDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(CszzParser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(CszzParser.QualifiedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(CszzParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(CszzParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#fieldDecl}.
	 * @param ctx the parse tree
	 */
	void enterFieldDecl(CszzParser.FieldDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#fieldDecl}.
	 * @param ctx the parse tree
	 */
	void exitFieldDecl(CszzParser.FieldDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#methodDecl}.
	 * @param ctx the parse tree
	 */
	void enterMethodDecl(CszzParser.MethodDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#methodDecl}.
	 * @param ctx the parse tree
	 */
	void exitMethodDecl(CszzParser.MethodDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#annotation}.
	 * @param ctx the parse tree
	 */
	void enterAnnotation(CszzParser.AnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#annotation}.
	 * @param ctx the parse tree
	 */
	void exitAnnotation(CszzParser.AnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(CszzParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(CszzParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#classType}.
	 * @param ctx the parse tree
	 */
	void enterClassType(CszzParser.ClassTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#classType}.
	 * @param ctx the parse tree
	 */
	void exitClassType(CszzParser.ClassTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#parameterizedElementType}.
	 * @param ctx the parse tree
	 */
	void enterParameterizedElementType(CszzParser.ParameterizedElementTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#parameterizedElementType}.
	 * @param ctx the parse tree
	 */
	void exitParameterizedElementType(CszzParser.ParameterizedElementTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#wildcardType}.
	 * @param ctx the parse tree
	 */
	void enterWildcardType(CszzParser.WildcardTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#wildcardType}.
	 * @param ctx the parse tree
	 */
	void exitWildcardType(CszzParser.WildcardTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(CszzParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(CszzParser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#localVarDecl}.
	 * @param ctx the parse tree
	 */
	void enterLocalVarDecl(CszzParser.LocalVarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#localVarDecl}.
	 * @param ctx the parse tree
	 */
	void exitLocalVarDecl(CszzParser.LocalVarDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#ifStat}.
	 * @param ctx the parse tree
	 */
	void enterIfStat(CszzParser.IfStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#ifStat}.
	 * @param ctx the parse tree
	 */
	void exitIfStat(CszzParser.IfStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(CszzParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(CszzParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#emptyStat}.
	 * @param ctx the parse tree
	 */
	void enterEmptyStat(CszzParser.EmptyStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#emptyStat}.
	 * @param ctx the parse tree
	 */
	void exitEmptyStat(CszzParser.EmptyStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#errorousStat}.
	 * @param ctx the parse tree
	 */
	void enterErrorousStat(CszzParser.ErrorousStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#errorousStat}.
	 * @param ctx the parse tree
	 */
	void exitErrorousStat(CszzParser.ErrorousStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#throwStat}.
	 * @param ctx the parse tree
	 */
	void enterThrowStat(CszzParser.ThrowStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#throwStat}.
	 * @param ctx the parse tree
	 */
	void exitThrowStat(CszzParser.ThrowStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#blockStmt}.
	 * @param ctx the parse tree
	 */
	void enterBlockStmt(CszzParser.BlockStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#blockStmt}.
	 * @param ctx the parse tree
	 */
	void exitBlockStmt(CszzParser.BlockStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#tryStat}.
	 * @param ctx the parse tree
	 */
	void enterTryStat(CszzParser.TryStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#tryStat}.
	 * @param ctx the parse tree
	 */
	void exitTryStat(CszzParser.TryStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#returnStat}.
	 * @param ctx the parse tree
	 */
	void enterReturnStat(CszzParser.ReturnStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#returnStat}.
	 * @param ctx the parse tree
	 */
	void exitReturnStat(CszzParser.ReturnStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#postIfStmt}.
	 * @param ctx the parse tree
	 */
	void enterPostIfStmt(CszzParser.PostIfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#postIfStmt}.
	 * @param ctx the parse tree
	 */
	void exitPostIfStmt(CszzParser.PostIfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#varDeclStat}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclStat(CszzParser.VarDeclStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#varDeclStat}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclStat(CszzParser.VarDeclStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void enterVarDecl(CszzParser.VarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void exitVarDecl(CszzParser.VarDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#breakStat}.
	 * @param ctx the parse tree
	 */
	void enterBreakStat(CszzParser.BreakStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#breakStat}.
	 * @param ctx the parse tree
	 */
	void exitBreakStat(CszzParser.BreakStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#continueStat}.
	 * @param ctx the parse tree
	 */
	void enterContinueStat(CszzParser.ContinueStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#continueStat}.
	 * @param ctx the parse tree
	 */
	void exitContinueStat(CszzParser.ContinueStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#whileStat}.
	 * @param ctx the parse tree
	 */
	void enterWhileStat(CszzParser.WhileStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#whileStat}.
	 * @param ctx the parse tree
	 */
	void exitWhileStat(CszzParser.WhileStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#doWhileStat}.
	 * @param ctx the parse tree
	 */
	void enterDoWhileStat(CszzParser.DoWhileStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#doWhileStat}.
	 * @param ctx the parse tree
	 */
	void exitDoWhileStat(CszzParser.DoWhileStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#forStat}.
	 * @param ctx the parse tree
	 */
	void enterForStat(CszzParser.ForStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#forStat}.
	 * @param ctx the parse tree
	 */
	void exitForStat(CszzParser.ForStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#forEachStat}.
	 * @param ctx the parse tree
	 */
	void enterForEachStat(CszzParser.ForEachStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#forEachStat}.
	 * @param ctx the parse tree
	 */
	void exitForEachStat(CszzParser.ForEachStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#expressions}.
	 * @param ctx the parse tree
	 */
	void enterExpressions(CszzParser.ExpressionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#expressions}.
	 * @param ctx the parse tree
	 */
	void exitExpressions(CszzParser.ExpressionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#exprStat}.
	 * @param ctx the parse tree
	 */
	void enterExprStat(CszzParser.ExprStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#exprStat}.
	 * @param ctx the parse tree
	 */
	void exitExprStat(CszzParser.ExprStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewExpr(CszzParser.NewExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewExpr(CszzParser.NewExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code invokeExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterInvokeExpr(CszzParser.InvokeExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code invokeExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitInvokeExpr(CszzParser.InvokeExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code instanceofExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterInstanceofExpr(CszzParser.InstanceofExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code instanceofExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitInstanceofExpr(CszzParser.InstanceofExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code interpolationExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterInterpolationExpr(CszzParser.InterpolationExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code interpolationExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitInterpolationExpr(CszzParser.InterpolationExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrayExpr(CszzParser.ArrayExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrayExpr(CszzParser.ArrayExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code incExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIncExpr(CszzParser.IncExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code incExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIncExpr(CszzParser.IncExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code castExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCastExpr(CszzParser.CastExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code castExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCastExpr(CszzParser.CastExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mapExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMapExpr(CszzParser.MapExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mapExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMapExpr(CszzParser.MapExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpr(CszzParser.BinaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpr(CszzParser.BinaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code getFieldExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGetFieldExpr(CszzParser.GetFieldExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code getFieldExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGetFieldExpr(CszzParser.GetFieldExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bitShiftExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBitShiftExpr(CszzParser.BitShiftExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bitShiftExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBitShiftExpr(CszzParser.BitShiftExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code questionExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterQuestionExpr(CszzParser.QuestionExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code questionExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitQuestionExpr(CszzParser.QuestionExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(CszzParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(CszzParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code getArrayElementExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGetArrayElementExpr(CszzParser.GetArrayElementExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code getArrayElementExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGetArrayElementExpr(CszzParser.GetArrayElementExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code preIncExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPreIncExpr(CszzParser.PreIncExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code preIncExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPreIncExpr(CszzParser.PreIncExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code errorousMemberExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterErrorousMemberExpr(CszzParser.ErrorousMemberExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code errorousMemberExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitErrorousMemberExpr(CszzParser.ErrorousMemberExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(CszzParser.UnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpr(CszzParser.UnaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code literalExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLiteralExpr(CszzParser.LiteralExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code literalExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLiteralExpr(CszzParser.LiteralExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberInvocationExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMemberInvocationExpr(CszzParser.MemberInvocationExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberInvocationExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMemberInvocationExpr(CszzParser.MemberInvocationExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpr(CszzParser.AssignExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpr(CszzParser.AssignExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newArrayExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewArrayExpr(CszzParser.NewArrayExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newArrayExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewArrayExpr(CszzParser.NewArrayExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code selfRefExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSelfRefExpr(CszzParser.SelfRefExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code selfRefExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSelfRefExpr(CszzParser.SelfRefExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifierExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpr(CszzParser.IdentifierExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpr(CszzParser.IdentifierExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(CszzParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(CszzParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link CszzParser#varModifier}.
	 * @param ctx the parse tree
	 */
	void enterVarModifier(CszzParser.VarModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CszzParser#varModifier}.
	 * @param ctx the parse tree
	 */
	void exitVarModifier(CszzParser.VarModifierContext ctx);
}

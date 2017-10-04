// Generated from CszzParser.g4 by ANTLR 4.5.1
package cszz.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CszzParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CszzParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CszzParser#compilationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompilationUnit(CszzParser.CompilationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#scriptDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScriptDef(CszzParser.ScriptDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#classDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDef(CszzParser.ClassDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#importDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDecl(CszzParser.ImportDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#qualifiedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedName(CszzParser.QualifiedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(CszzParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#fieldDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDecl(CszzParser.FieldDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#methodDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDecl(CszzParser.MethodDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#annotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotation(CszzParser.AnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(CszzParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#classType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassType(CszzParser.ClassTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#parameterizedElementType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterizedElementType(CszzParser.ParameterizedElementTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#wildcardType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWildcardType(CszzParser.WildcardTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(CszzParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#localVarDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVarDecl(CszzParser.LocalVarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#ifStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStat(CszzParser.IfStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat(CszzParser.StatContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#emptyStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyStat(CszzParser.EmptyStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#errorousStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitErrorousStat(CszzParser.ErrorousStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#throwStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThrowStat(CszzParser.ThrowStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#blockStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStmt(CszzParser.BlockStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#tryStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTryStat(CszzParser.TryStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#returnStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStat(CszzParser.ReturnStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#postIfStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostIfStmt(CszzParser.PostIfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#varDeclStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclStat(CszzParser.VarDeclStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(CszzParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#breakStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStat(CszzParser.BreakStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#continueStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStat(CszzParser.ContinueStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#whileStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStat(CszzParser.WhileStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#doWhileStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhileStat(CszzParser.DoWhileStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#forStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStat(CszzParser.ForStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#forEachStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForEachStat(CszzParser.ForEachStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#expressions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressions(CszzParser.ExpressionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#exprStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStat(CszzParser.ExprStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewExpr(CszzParser.NewExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code invokeExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInvokeExpr(CszzParser.InvokeExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code instanceofExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceofExpr(CszzParser.InstanceofExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code interpolationExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterpolationExpr(CszzParser.InterpolationExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayExpr(CszzParser.ArrayExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code incExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncExpr(CszzParser.IncExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code castExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpr(CszzParser.CastExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mapExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapExpr(CszzParser.MapExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpr(CszzParser.BinaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code getFieldExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGetFieldExpr(CszzParser.GetFieldExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bitShiftExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitShiftExpr(CszzParser.BitShiftExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code questionExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestionExpr(CszzParser.QuestionExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(CszzParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code getArrayElementExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGetArrayElementExpr(CszzParser.GetArrayElementExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code preIncExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPreIncExpr(CszzParser.PreIncExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code errorousMemberExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitErrorousMemberExpr(CszzParser.ErrorousMemberExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpr(CszzParser.UnaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code literalExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExpr(CszzParser.LiteralExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code memberInvocationExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberInvocationExpr(CszzParser.MemberInvocationExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpr(CszzParser.AssignExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newArrayExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewArrayExpr(CszzParser.NewArrayExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selfRefExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelfRefExpr(CszzParser.SelfRefExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identifierExpr}
	 * labeled alternative in {@link CszzParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierExpr(CszzParser.IdentifierExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(CszzParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link CszzParser#varModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarModifier(CszzParser.VarModifierContext ctx);
}

package cszz.test;

import java.util.List;
import cszz.antlr.CszzLexer;
import cszz.compiler.CompilationUnit;
import cszz.compiler.CszzCompiler;
import cszz.util.ParseTreeNavigator;
import cszz.util.TokenNavigator;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 *  
 */
public class CszzCompilerTest {
    
    public CszzCompilerTest() {
    }
    
    @Test
    public void test(){
        CszzCompiler kc = new CszzCompiler();
        kc.addSource("Test", "class{  }","Test.kl");
        kc.compile();
        CompilationUnit unit = kc.getCompilationUnit("Test");
        assert unit != null;
        CommonTokenStream ts = unit.getTokenStream();
        //the tokens contains tokens in all channels
        List<Token> tokens = ts.getTokens();
        assertEquals(5, ts.size());
        testTokenNavigator(tokens.toArray(new Token[0]),unit.getAstBuilder().getParseTree());
    }
    
    private void testTokenNavigator(Token[] tokens,ParseTree tree){
        TokenNavigator tokenNav = new TokenNavigator(tokens);
        ParseTreeNavigator treeNav = new ParseTreeNavigator(tree);
        assertEquals(0,tokenNav.getCurrentTokenIndex());
        assertEquals("class", treeNav.getParseTree(tokenNav.getCurrentToken()).getText());
        
        
        
        
        
        tokenNav.move(5);
        assertEquals("{", tokenNav.getCurrentToken().getText());
        assertEquals(1,tokenNav.getCurrentTokenIndex());
        tokenNav.next(CszzLexer.DEFAULT_TOKEN_CHANNEL);
        assertEquals("}", tokenNav.getCurrentToken().getText());
        
        tokenNav.move(8);
        assertEquals("}",tokenNav.getCurrentToken().getText());
        
        assertEquals("}", treeNav.getParseTree(tokenNav.getCurrentToken()).getText());
        assertEquals(3, tokenNav.getCurrentTokenIndex());
        
    }
}

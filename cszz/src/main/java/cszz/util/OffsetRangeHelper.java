package cszz.util;

import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import cszz.compiler.OffsetRange;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 *  
 */
public class OffsetRangeHelper {

    public static OffsetRange getOffsetRange(ParserRuleContext tree) {
        Token start = tree.getStart();
        Token stop = tree.getStop();
        if(start==null || stop==null) return OffsetRange.NONE;
        return getOffsetRange(start, stop);
    }

    public static OffsetRange getOffsetRange(Token token) {
        return getOffsetRange(token, token);
    }

    public static OffsetRange getOffsetRange(Token start, Token stop) {
        OffsetRange offset = new OffsetRange();
        offset.startOffset = start.getStartIndex();
        offset.stopOffset = stop.getStopIndex();
        offset.startLine = start.getLine();
        offset.startLineColumn = start.getCharPositionInLine();
        offset.stopLine = stop.getLine();
        offset.stopLineColumn = stop.getCharPositionInLine();
        return offset;
    }

}

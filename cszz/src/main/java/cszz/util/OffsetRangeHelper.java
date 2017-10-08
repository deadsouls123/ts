package cszz.util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import cszz.compiler.OffsetRange;

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

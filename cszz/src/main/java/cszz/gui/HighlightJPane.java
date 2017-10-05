package cszz.gui;

import java.util.StringTokenizer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.rtf.RTFEditorKit;

import cszz.antlr.CszzLexer;

public class HighlightJPane extends JTextPane {

	private static final long serialVersionUID = -66377652770879651L;
	protected StyleContext m_context;
	protected DefaultStyledDocument m_doc;
	private MutableAttributeSet keyAttr, normalAttr;
	private MutableAttributeSet bracketAttr;
	private MutableAttributeSet inputAttributes = new RTFEditorKit().getInputAttributes();

	private final static String[] _keys = CszzLexer.getLexerStringList();

	private final static char[] _character = new char[] { '(', ')', ',', ';', ':', '\t', '\n', '+', '-', '*', '/' };

	public HighlightJPane() {
		super();
		setBackground(Color.BLACK);
		setMargin(new Insets(5, 5, 5, 5));
		setCaretColor(Color.WHITE);
		setCursor(new Cursor(Cursor.TEXT_CURSOR));
		
		m_context = new StyleContext();
		m_doc = new DefaultStyledDocument(m_context);
		this.setDocument(m_doc);
		this.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				dealSingleRow();
			}
		});

		keyAttr = new SimpleAttributeSet();
		StyleConstants.setForeground(keyAttr, Color.ORANGE);
		StyleConstants.setBold(keyAttr, true);

		normalAttr = new SimpleAttributeSet();
		StyleConstants.setBold(normalAttr, false);
		StyleConstants.setForeground(normalAttr, Color.WHITE);
		bracketAttr = new SimpleAttributeSet();
		StyleConstants.setForeground(bracketAttr, Color.ORANGE);
		StyleConstants.setBold(bracketAttr, true);
	}

	private void setBracketColor(String _text) {
		int len = _text.length();
		for (int i = 0; i < len; i++) {
			char ch = _text.charAt(i);
			if (ch == '{' || ch == '}') {
				m_doc.setCharacterAttributes(i, 1, bracketAttr, false);
			}
		}
	}

	private boolean isCharacter(char _ch) {
		for (int i = 0; i < _character.length; i++) {
			if (_ch == _character[i]) {
				return true;
			}
		}
		return false;
	}

	private int setKeyColor(String _key, int _start, int _length) {
		for (int i = 0; i < _keys.length; i++) {
			int li_index = _key.indexOf(_keys[i]);
			if (li_index < 0) {
				continue;
			}
			int li_legnth = li_index + _keys[i].length();
			if (li_legnth == _key.length()) {
				if (li_index == 0) {
					m_doc.setCharacterAttributes(_start, _keys[i].length(), keyAttr, false);
				} else {
					char ch_temp = _key.charAt(li_index - 1);
					if (isCharacter(ch_temp)) {
						m_doc.setCharacterAttributes(_start + li_index, _keys[i].length(), keyAttr, false);
					}
				}
			} else {
				if (li_index == 0) {
					char ch_temp = _key.charAt(_keys[i].length());
					if (isCharacter(ch_temp)) {
						m_doc.setCharacterAttributes(_start, _keys[i].length(), keyAttr, false);
					}
				} else {
					char ch_temp = _key.charAt(li_index - 1);
					char ch_temp_2 = _key.charAt(li_legnth);
					if (isCharacter(ch_temp) && isCharacter(ch_temp_2)) {
						m_doc.setCharacterAttributes(_start + li_index, _keys[i].length(), keyAttr, false);
					}
				}
			}
		}
		return _length + 1;
	}

	private void dealText(int _start, int _end) {
		String text = "";
		try {
			text = m_doc.getText(_start, _end - _start).toUpperCase();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		if (text == null || text.equals("")) {
			return;
		}
		int xStart = 0;
		m_doc.setCharacterAttributes(_start, text.length(), normalAttr, false);
		MyStringTokenizer st = new MyStringTokenizer(text);
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			if (s == null)
				return;
			xStart = st.getCurrPosition();
			setKeyColor(s.toUpperCase(), _start + xStart, s.length());
		}
		setBracketColor(text);
		inputAttributes.addAttributes(normalAttr);
	}

	private void dealSingleRow() {
		Element root = m_doc.getDefaultRootElement();

		int cursorPos = this.getCaretPosition();
		int line = root.getElementIndex(cursorPos);
		Element para = root.getElement(line);
		int start = para.getStartOffset();
		int end = para.getEndOffset() - 1;
		dealText(start, end);
	}

	public void syntaxParse() {
		Element root = m_doc.getDefaultRootElement();
		int li_count = root.getElementCount();
		for (int i = 0; i < li_count; i++) {
			Element para = root.getElement(i);
			int start = para.getStartOffset();
			int end = para.getEndOffset() - 1;
			dealText(start, end);
		}
	}
}

class MyStringTokenizer extends StringTokenizer {
	String sval = " ";
	String oldStr, str;
	int m_currPosition = 0, m_beginPosition = 0;

	MyStringTokenizer(String str) {
		super(str, " ");
		this.oldStr = str;
		this.str = str;
	}

	public String nextToken() {
		try {
			String s = super.nextToken();
			int pos = -1;
			if (oldStr.equals(s)) {
				return s;
			}
			pos = str.indexOf(s + sval);
			if (pos == -1) {
				pos = str.indexOf(sval + s);
				if (pos == -1)
					return null;
				else
					pos += 1;
			}
			int xBegin = pos + s.length();
			str = str.substring(xBegin);
			m_currPosition = m_beginPosition + pos;
			m_beginPosition = m_beginPosition + xBegin;
			return s;
		} catch (java.util.NoSuchElementException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public int getCurrPosition() {
		return m_currPosition;
	}
}
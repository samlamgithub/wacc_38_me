// Generated from ./BasicLexer.g4 by ANTLR 4.4
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BasicLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SEMICOLON=1, COMMA=2, WS=3, COMMENT=4, OPEN_PARENTHESES=5, CLOSE_PARENTHESES=6, 
		ASSIGN=7, BEGIN=8, END=9, IS=10, SKIP=11, RETURN=12, READ=13, FREE=14, 
		EXIT=15, PRINT=16, PRINTLN=17, IF=18, THEN=19, ELSE=20, FI=21, WHILE=22, 
		DO=23, DONE=24, NEWPAIR=25, CALL=26, FST=27, SND=28, INT=29, BOOL=30, 
		CHAR=31, STRING=32, PAIR=33, LSBRACKET=34, RSBRACKET=35, CHARLITERAL=36, 
		MUL=37, DIV=38, MOD=39, PLUS=40, MINUS=41, GREATER=42, GREATEREQU=43, 
		LESS=44, LESSEQU=45, EQUAL=46, NOTEQUAL=47, AND=48, OR=49, STRLITERAL=50, 
		POSITIVEINTLITERAL=51, JUSTINTLITERAL=52, NOTNEGUNARYOPER=53, TRUE=54, 
		FALSE=55, NULL=56, IDENT=57, ESCAPECHARACTER=58;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'", "'\\u0018'", 
		"'\\u0019'", "'\\u001A'", "'\\u001B'", "'\\u001C'", "'\\u001D'", "'\\u001E'", 
		"'\\u001F'", "' '", "'!'", "'\"'", "'#'", "'$'", "'%'", "'&'", "'''", 
		"'('", "')'", "'*'", "'+'", "','", "'-'", "'.'", "'/'", "'0'", "'1'", 
		"'2'", "'3'", "'4'", "'5'", "'6'", "'7'", "'8'", "'9'", "':'"
	};
	public static final String[] ruleNames = {
		"SEMICOLON", "COMMA", "WS", "COMMENT", "HASH", "OPEN_PARENTHESES", "CLOSE_PARENTHESES", 
		"DIGIT", "ASSIGN", "BEGIN", "END", "IS", "SKIP", "RETURN", "READ", "FREE", 
		"EXIT", "PRINT", "PRINTLN", "IF", "THEN", "ELSE", "FI", "WHILE", "DO", 
		"DONE", "NEWPAIR", "CALL", "FST", "SND", "INT", "BOOL", "CHAR", "STRING", 
		"PAIR", "LSBRACKET", "RSBRACKET", "UNDERSCORE", "UPPERCASE", "LOWERCASE", 
		"CHARLITERAL", "MUL", "DIV", "MOD", "PLUS", "MINUS", "GREATER", "GREATEREQU", 
		"LESS", "LESSEQU", "EQUAL", "NOTEQUAL", "AND", "OR", "CHARACTER", "STRLITERAL", 
		"HexDigit", "EscapeSequence", "OctalEscape", "UnicodeEscape", "POSITIVEINTLITERAL", 
		"JUSTINTLITERAL", "NOT", "NOTNEGUNARYOPER", "LENGTH", "ORDER", "TOCHAR", 
		"TRUE", "FALSE", "NULL", "IDENT", "ESCAPECHARACTER"
	};


	public BasicLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "BasicLexer.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2<\u020d\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\7\5\u009e\n\5\f\5\16\5\u00a1"+
		"\13\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21"+
		"\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26\3\26"+
		"\3\26\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\37\3\37"+
		"\3\37\3\37\3 \3 \3 \3 \3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#"+
		"\3#\3#\3#\3$\3$\3$\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3*\3*\3"+
		"+\3+\3+\5+\u0143\n+\3,\3,\3,\5,\u0148\n,\3-\3-\3-\5-\u014d\n-\3.\3.\3"+
		".\5.\u0152\n.\3/\3/\3/\5/\u0157\n/\3\60\3\60\3\60\5\60\u015c\n\60\3\61"+
		"\3\61\3\61\3\61\3\61\5\61\u0163\n\61\3\62\3\62\3\62\5\62\u0168\n\62\3"+
		"\63\3\63\3\63\3\63\3\63\5\63\u016f\n\63\3\64\3\64\3\64\3\64\3\64\5\64"+
		"\u0176\n\64\3\65\3\65\3\65\3\65\3\65\5\65\u017d\n\65\3\66\3\66\3\66\3"+
		"\66\3\66\5\66\u0184\n\66\3\67\3\67\3\67\3\67\3\67\5\67\u018b\n\67\38\7"+
		"8\u018e\n8\f8\168\u0191\138\38\38\58\u0195\n8\39\39\39\79\u019a\n9\f9"+
		"\169\u019d\139\39\39\3:\3:\3;\3;\3;\3;\5;\u01a7\n;\3<\3<\3<\3<\3<\3<\3"+
		"<\3<\3<\5<\u01b2\n<\3=\3=\3=\3=\3=\3=\3=\3>\5>\u01bc\n>\3>\6>\u01bf\n"+
		">\r>\16>\u01c0\3?\6?\u01c4\n?\r?\16?\u01c5\3@\3@\3@\5@\u01cb\n@\3A\3A"+
		"\3A\3A\5A\u01d1\nA\3B\3B\3B\3B\3B\3B\3B\5B\u01da\nB\3C\3C\3C\3C\3C\3C"+
		"\3C\5C\u01e3\nC\3D\3D\3D\3D\3D\3D\3D\5D\u01ec\nD\3E\3E\3E\3E\3E\3F\3F"+
		"\3F\3F\3F\3F\3G\3G\3G\3G\3G\3H\3H\3H\5H\u0201\nH\3H\3H\3H\3H\7H\u0207"+
		"\nH\fH\16H\u020a\13H\3I\3I\2\2J\3\3\5\4\7\5\t\6\13\2\r\7\17\b\21\2\23"+
		"\t\25\n\27\13\31\f\33\r\35\16\37\17!\20#\21%\22\'\23)\24+\25-\26/\27\61"+
		"\30\63\31\65\32\67\339\34;\35=\36?\37A C!E\"G#I$K%M\2O\2Q\2S&U\'W(Y)["+
		"*]+_,a-c.e/g\60i\61k\62m\63o\2q\64s\2u\2w\2y\2{\65}\66\177\2\u0081\67"+
		"\u0083\2\u0085\2\u0087\2\u00898\u008b9\u008d:\u008f;\u0091<\3\2\13\5\2"+
		"\13\f\17\17\"\"\4\2\f\f\17\17\5\2$$))^^\4\2$$^^\5\2\62;CHch\n\2$$))^^"+
		"ddhhppttvv\3\2\62\65\3\2\629\13\2$$))\62\62^^ddhhppttvv\u0224\2\3\3\2"+
		"\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\23"+
		"\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2"+
		"\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2"+
		"\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3"+
		"\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2"+
		"\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2S\3\2\2\2"+
		"\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a"+
		"\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2"+
		"\2\2\2q\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\u0081\3\2\2\2\2\u0089\3\2\2\2"+
		"\2\u008b\3\2\2\2\2\u008d\3\2\2\2\2\u008f\3\2\2\2\2\u0091\3\2\2\2\3\u0093"+
		"\3\2\2\2\5\u0095\3\2\2\2\7\u0097\3\2\2\2\t\u009b\3\2\2\2\13\u00a4\3\2"+
		"\2\2\r\u00a6\3\2\2\2\17\u00a8\3\2\2\2\21\u00aa\3\2\2\2\23\u00ac\3\2\2"+
		"\2\25\u00ae\3\2\2\2\27\u00b4\3\2\2\2\31\u00b8\3\2\2\2\33\u00bb\3\2\2\2"+
		"\35\u00c0\3\2\2\2\37\u00c7\3\2\2\2!\u00cc\3\2\2\2#\u00d1\3\2\2\2%\u00d6"+
		"\3\2\2\2\'\u00dc\3\2\2\2)\u00e4\3\2\2\2+\u00e7\3\2\2\2-\u00ec\3\2\2\2"+
		"/\u00f1\3\2\2\2\61\u00f4\3\2\2\2\63\u00fa\3\2\2\2\65\u00fd\3\2\2\2\67"+
		"\u0102\3\2\2\29\u010a\3\2\2\2;\u010f\3\2\2\2=\u0113\3\2\2\2?\u0117\3\2"+
		"\2\2A\u011b\3\2\2\2C\u0120\3\2\2\2E\u0125\3\2\2\2G\u012c\3\2\2\2I\u0131"+
		"\3\2\2\2K\u0133\3\2\2\2M\u0135\3\2\2\2O\u0137\3\2\2\2Q\u0139\3\2\2\2S"+
		"\u013b\3\2\2\2U\u0142\3\2\2\2W\u0147\3\2\2\2Y\u014c\3\2\2\2[\u0151\3\2"+
		"\2\2]\u0156\3\2\2\2_\u015b\3\2\2\2a\u0162\3\2\2\2c\u0167\3\2\2\2e\u016e"+
		"\3\2\2\2g\u0175\3\2\2\2i\u017c\3\2\2\2k\u0183\3\2\2\2m\u018a\3\2\2\2o"+
		"\u0194\3\2\2\2q\u0196\3\2\2\2s\u01a0\3\2\2\2u\u01a6\3\2\2\2w\u01b1\3\2"+
		"\2\2y\u01b3\3\2\2\2{\u01bb\3\2\2\2}\u01c3\3\2\2\2\177\u01ca\3\2\2\2\u0081"+
		"\u01d0\3\2\2\2\u0083\u01d9\3\2\2\2\u0085\u01e2\3\2\2\2\u0087\u01eb\3\2"+
		"\2\2\u0089\u01ed\3\2\2\2\u008b\u01f2\3\2\2\2\u008d\u01f8\3\2\2\2\u008f"+
		"\u0200\3\2\2\2\u0091\u020b\3\2\2\2\u0093\u0094\7=\2\2\u0094\4\3\2\2\2"+
		"\u0095\u0096\7.\2\2\u0096\6\3\2\2\2\u0097\u0098\t\2\2\2\u0098\u0099\3"+
		"\2\2\2\u0099\u009a\b\4\2\2\u009a\b\3\2\2\2\u009b\u009f\5\13\6\2\u009c"+
		"\u009e\n\3\2\2\u009d\u009c\3\2\2\2\u009e\u00a1\3\2\2\2\u009f\u009d\3\2"+
		"\2\2\u009f\u00a0\3\2\2\2\u00a0\u00a2\3\2\2\2\u00a1\u009f\3\2\2\2\u00a2"+
		"\u00a3\b\5\2\2\u00a3\n\3\2\2\2\u00a4\u00a5\7%\2\2\u00a5\f\3\2\2\2\u00a6"+
		"\u00a7\7*\2\2\u00a7\16\3\2\2\2\u00a8\u00a9\7+\2\2\u00a9\20\3\2\2\2\u00aa"+
		"\u00ab\4\62;\2\u00ab\22\3\2\2\2\u00ac\u00ad\7?\2\2\u00ad\24\3\2\2\2\u00ae"+
		"\u00af\7d\2\2\u00af\u00b0\7g\2\2\u00b0\u00b1\7i\2\2\u00b1\u00b2\7k\2\2"+
		"\u00b2\u00b3\7p\2\2\u00b3\26\3\2\2\2\u00b4\u00b5\7g\2\2\u00b5\u00b6\7"+
		"p\2\2\u00b6\u00b7\7f\2\2\u00b7\30\3\2\2\2\u00b8\u00b9\7k\2\2\u00b9\u00ba"+
		"\7u\2\2\u00ba\32\3\2\2\2\u00bb\u00bc\7u\2\2\u00bc\u00bd\7m\2\2\u00bd\u00be"+
		"\7k\2\2\u00be\u00bf\7r\2\2\u00bf\34\3\2\2\2\u00c0\u00c1\7t\2\2\u00c1\u00c2"+
		"\7g\2\2\u00c2\u00c3\7v\2\2\u00c3\u00c4\7w\2\2\u00c4\u00c5\7t\2\2\u00c5"+
		"\u00c6\7p\2\2\u00c6\36\3\2\2\2\u00c7\u00c8\7t\2\2\u00c8\u00c9\7g\2\2\u00c9"+
		"\u00ca\7c\2\2\u00ca\u00cb\7f\2\2\u00cb \3\2\2\2\u00cc\u00cd\7h\2\2\u00cd"+
		"\u00ce\7t\2\2\u00ce\u00cf\7g\2\2\u00cf\u00d0\7g\2\2\u00d0\"\3\2\2\2\u00d1"+
		"\u00d2\7g\2\2\u00d2\u00d3\7z\2\2\u00d3\u00d4\7k\2\2\u00d4\u00d5\7v\2\2"+
		"\u00d5$\3\2\2\2\u00d6\u00d7\7r\2\2\u00d7\u00d8\7t\2\2\u00d8\u00d9\7k\2"+
		"\2\u00d9\u00da\7p\2\2\u00da\u00db\7v\2\2\u00db&\3\2\2\2\u00dc\u00dd\7"+
		"r\2\2\u00dd\u00de\7t\2\2\u00de\u00df\7k\2\2\u00df\u00e0\7p\2\2\u00e0\u00e1"+
		"\7v\2\2\u00e1\u00e2\7n\2\2\u00e2\u00e3\7p\2\2\u00e3(\3\2\2\2\u00e4\u00e5"+
		"\7k\2\2\u00e5\u00e6\7h\2\2\u00e6*\3\2\2\2\u00e7\u00e8\7v\2\2\u00e8\u00e9"+
		"\7j\2\2\u00e9\u00ea\7g\2\2\u00ea\u00eb\7p\2\2\u00eb,\3\2\2\2\u00ec\u00ed"+
		"\7g\2\2\u00ed\u00ee\7n\2\2\u00ee\u00ef\7u\2\2\u00ef\u00f0\7g\2\2\u00f0"+
		".\3\2\2\2\u00f1\u00f2\7h\2\2\u00f2\u00f3\7k\2\2\u00f3\60\3\2\2\2\u00f4"+
		"\u00f5\7y\2\2\u00f5\u00f6\7j\2\2\u00f6\u00f7\7k\2\2\u00f7\u00f8\7n\2\2"+
		"\u00f8\u00f9\7g\2\2\u00f9\62\3\2\2\2\u00fa\u00fb\7f\2\2\u00fb\u00fc\7"+
		"q\2\2\u00fc\64\3\2\2\2\u00fd\u00fe\7f\2\2\u00fe\u00ff\7q\2\2\u00ff\u0100"+
		"\7p\2\2\u0100\u0101\7g\2\2\u0101\66\3\2\2\2\u0102\u0103\7p\2\2\u0103\u0104"+
		"\7g\2\2\u0104\u0105\7y\2\2\u0105\u0106\7r\2\2\u0106\u0107\7c\2\2\u0107"+
		"\u0108\7k\2\2\u0108\u0109\7t\2\2\u01098\3\2\2\2\u010a\u010b\7e\2\2\u010b"+
		"\u010c\7c\2\2\u010c\u010d\7n\2\2\u010d\u010e\7n\2\2\u010e:\3\2\2\2\u010f"+
		"\u0110\7h\2\2\u0110\u0111\7u\2\2\u0111\u0112\7v\2\2\u0112<\3\2\2\2\u0113"+
		"\u0114\7u\2\2\u0114\u0115\7p\2\2\u0115\u0116\7f\2\2\u0116>\3\2\2\2\u0117"+
		"\u0118\7k\2\2\u0118\u0119\7p\2\2\u0119\u011a\7v\2\2\u011a@\3\2\2\2\u011b"+
		"\u011c\7d\2\2\u011c\u011d\7q\2\2\u011d\u011e\7q\2\2\u011e\u011f\7n\2\2"+
		"\u011fB\3\2\2\2\u0120\u0121\7e\2\2\u0121\u0122\7j\2\2\u0122\u0123\7c\2"+
		"\2\u0123\u0124\7t\2\2\u0124D\3\2\2\2\u0125\u0126\7u\2\2\u0126\u0127\7"+
		"v\2\2\u0127\u0128\7t\2\2\u0128\u0129\7k\2\2\u0129\u012a\7p\2\2\u012a\u012b"+
		"\7i\2\2\u012bF\3\2\2\2\u012c\u012d\7r\2\2\u012d\u012e\7c\2\2\u012e\u012f"+
		"\7k\2\2\u012f\u0130\7t\2\2\u0130H\3\2\2\2\u0131\u0132\7]\2\2\u0132J\3"+
		"\2\2\2\u0133\u0134\7_\2\2\u0134L\3\2\2\2\u0135\u0136\7a\2\2\u0136N\3\2"+
		"\2\2\u0137\u0138\4C\\\2\u0138P\3\2\2\2\u0139\u013a\4c|\2\u013aR\3\2\2"+
		"\2\u013b\u013c\7)\2\2\u013c\u013d\5o8\2\u013d\u013e\7)\2\2\u013eT\3\2"+
		"\2\2\u013f\u0143\7,\2\2\u0140\u0141\7,\2\2\u0141\u0143\7\"\2\2\u0142\u013f"+
		"\3\2\2\2\u0142\u0140\3\2\2\2\u0143V\3\2\2\2\u0144\u0148\7\61\2\2\u0145"+
		"\u0146\7\61\2\2\u0146\u0148\7\"\2\2\u0147\u0144\3\2\2\2\u0147\u0145\3"+
		"\2\2\2\u0148X\3\2\2\2\u0149\u014d\7\'\2\2\u014a\u014b\7\'\2\2\u014b\u014d"+
		"\7\"\2\2\u014c\u0149\3\2\2\2\u014c\u014a\3\2\2\2\u014dZ\3\2\2\2\u014e"+
		"\u0152\7-\2\2\u014f\u0150\7-\2\2\u0150\u0152\7\"\2\2\u0151\u014e\3\2\2"+
		"\2\u0151\u014f\3\2\2\2\u0152\\\3\2\2\2\u0153\u0157\7/\2\2\u0154\u0155"+
		"\7/\2\2\u0155\u0157\7\"\2\2\u0156\u0153\3\2\2\2\u0156\u0154\3\2\2\2\u0157"+
		"^\3\2\2\2\u0158\u015c\7@\2\2\u0159\u015a\7@\2\2\u015a\u015c\7\"\2\2\u015b"+
		"\u0158\3\2\2\2\u015b\u0159\3\2\2\2\u015c`\3\2\2\2\u015d\u015e\7@\2\2\u015e"+
		"\u0163\7?\2\2\u015f\u0160\7@\2\2\u0160\u0161\7?\2\2\u0161\u0163\7\"\2"+
		"\2\u0162\u015d\3\2\2\2\u0162\u015f\3\2\2\2\u0163b\3\2\2\2\u0164\u0168"+
		"\7>\2\2\u0165\u0166\7>\2\2\u0166\u0168\7\"\2\2\u0167\u0164\3\2\2\2\u0167"+
		"\u0165\3\2\2\2\u0168d\3\2\2\2\u0169\u016a\7>\2\2\u016a\u016f\7?\2\2\u016b"+
		"\u016c\7>\2\2\u016c\u016d\7?\2\2\u016d\u016f\7\"\2\2\u016e\u0169\3\2\2"+
		"\2\u016e\u016b\3\2\2\2\u016ff\3\2\2\2\u0170\u0171\7?\2\2\u0171\u0176\7"+
		"?\2\2\u0172\u0173\7?\2\2\u0173\u0174\7?\2\2\u0174\u0176\7\"\2\2\u0175"+
		"\u0170\3\2\2\2\u0175\u0172\3\2\2\2\u0176h\3\2\2\2\u0177\u0178\7#\2\2\u0178"+
		"\u017d\7?\2\2\u0179\u017a\7#\2\2\u017a\u017b\7?\2\2\u017b\u017d\7\"\2"+
		"\2\u017c\u0177\3\2\2\2\u017c\u0179\3\2\2\2\u017dj\3\2\2\2\u017e\u017f"+
		"\7(\2\2\u017f\u0184\7(\2\2\u0180\u0181\7(\2\2\u0181\u0182\7(\2\2\u0182"+
		"\u0184\7\"\2\2\u0183\u017e\3\2\2\2\u0183\u0180\3\2\2\2\u0184l\3\2\2\2"+
		"\u0185\u0186\7~\2\2\u0186\u018b\7~\2\2\u0187\u0188\7~\2\2\u0188\u0189"+
		"\7~\2\2\u0189\u018b\7\"\2\2\u018a\u0185\3\2\2\2\u018a\u0187\3\2\2\2\u018b"+
		"n\3\2\2\2\u018c\u018e\n\4\2\2\u018d\u018c\3\2\2\2\u018e\u0191\3\2\2\2"+
		"\u018f\u018d\3\2\2\2\u018f\u0190\3\2\2\2\u0190\u0195\3\2\2\2\u0191\u018f"+
		"\3\2\2\2\u0192\u0193\7^\2\2\u0193\u0195\5\u0091I\2\u0194\u018f\3\2\2\2"+
		"\u0194\u0192\3\2\2\2\u0195p\3\2\2\2\u0196\u019b\7$\2\2\u0197\u019a\5u"+
		";\2\u0198\u019a\n\5\2\2\u0199\u0197\3\2\2\2\u0199\u0198\3\2\2\2\u019a"+
		"\u019d\3\2\2\2\u019b\u0199\3\2\2\2\u019b\u019c\3\2\2\2\u019c\u019e\3\2"+
		"\2\2\u019d\u019b\3\2\2\2\u019e\u019f\7$\2\2\u019fr\3\2\2\2\u01a0\u01a1"+
		"\t\6\2\2\u01a1t\3\2\2\2\u01a2\u01a3\7^\2\2\u01a3\u01a7\t\7\2\2\u01a4\u01a7"+
		"\5y=\2\u01a5\u01a7\5w<\2\u01a6\u01a2\3\2\2\2\u01a6\u01a4\3\2\2\2\u01a6"+
		"\u01a5\3\2\2\2\u01a7v\3\2\2\2\u01a8\u01a9\7^\2\2\u01a9\u01aa\t\b\2\2\u01aa"+
		"\u01ab\t\t\2\2\u01ab\u01b2\t\t\2\2\u01ac\u01ad\7^\2\2\u01ad\u01ae\t\t"+
		"\2\2\u01ae\u01b2\t\t\2\2\u01af\u01b0\7^\2\2\u01b0\u01b2\t\t\2\2\u01b1"+
		"\u01a8\3\2\2\2\u01b1\u01ac\3\2\2\2\u01b1\u01af\3\2\2\2\u01b2x\3\2\2\2"+
		"\u01b3\u01b4\7^\2\2\u01b4\u01b5\7w\2\2\u01b5\u01b6\5s:\2\u01b6\u01b7\5"+
		"s:\2\u01b7\u01b8\5s:\2\u01b8\u01b9\5s:\2\u01b9z\3\2\2\2\u01ba\u01bc\7"+
		"-\2\2\u01bb\u01ba\3\2\2\2\u01bb\u01bc\3\2\2\2\u01bc\u01be\3\2\2\2\u01bd"+
		"\u01bf\5\21\t\2\u01be\u01bd\3\2\2\2\u01bf\u01c0\3\2\2\2\u01c0\u01be\3"+
		"\2\2\2\u01c0\u01c1\3\2\2\2\u01c1|\3\2\2\2\u01c2\u01c4\5\21\t\2\u01c3\u01c2"+
		"\3\2\2\2\u01c4\u01c5\3\2\2\2\u01c5\u01c3\3\2\2\2\u01c5\u01c6\3\2\2\2\u01c6"+
		"~\3\2\2\2\u01c7\u01cb\7#\2\2\u01c8\u01c9\7#\2\2\u01c9\u01cb\7\"\2\2\u01ca"+
		"\u01c7\3\2\2\2\u01ca\u01c8\3\2\2\2\u01cb\u0080\3\2\2\2\u01cc\u01d1\5\177"+
		"@\2\u01cd\u01d1\5\u0083B\2\u01ce\u01d1\5\u0085C\2\u01cf\u01d1\5\u0087"+
		"D\2\u01d0\u01cc\3\2\2\2\u01d0\u01cd\3\2\2\2\u01d0\u01ce\3\2\2\2\u01d0"+
		"\u01cf\3\2\2\2\u01d1\u0082\3\2\2\2\u01d2\u01d3\7n\2\2\u01d3\u01d4\7g\2"+
		"\2\u01d4\u01da\7p\2\2\u01d5\u01d6\7n\2\2\u01d6\u01d7\7g\2\2\u01d7\u01d8"+
		"\7p\2\2\u01d8\u01da\7\"\2\2\u01d9\u01d2\3\2\2\2\u01d9\u01d5\3\2\2\2\u01da"+
		"\u0084\3\2\2\2\u01db\u01dc\7q\2\2\u01dc\u01dd\7t\2\2\u01dd\u01e3\7f\2"+
		"\2\u01de\u01df\7q\2\2\u01df\u01e0\7t\2\2\u01e0\u01e1\7f\2\2\u01e1\u01e3"+
		"\7\"\2\2\u01e2\u01db\3\2\2\2\u01e2\u01de\3\2\2\2\u01e3\u0086\3\2\2\2\u01e4"+
		"\u01e5\7e\2\2\u01e5\u01e6\7j\2\2\u01e6\u01ec\7t\2\2\u01e7\u01e8\7e\2\2"+
		"\u01e8\u01e9\7j\2\2\u01e9\u01ea\7t\2\2\u01ea\u01ec\7\"\2\2\u01eb\u01e4"+
		"\3\2\2\2\u01eb\u01e7\3\2\2\2\u01ec\u0088\3\2\2\2\u01ed\u01ee\7v\2\2\u01ee"+
		"\u01ef\7t\2\2\u01ef\u01f0\7w\2\2\u01f0\u01f1\7g\2\2\u01f1\u008a\3\2\2"+
		"\2\u01f2\u01f3\7h\2\2\u01f3\u01f4\7c\2\2\u01f4\u01f5\7n\2\2\u01f5\u01f6"+
		"\7u\2\2\u01f6\u01f7\7g\2\2\u01f7\u008c\3\2\2\2\u01f8\u01f9\7p\2\2\u01f9"+
		"\u01fa\7w\2\2\u01fa\u01fb\7n\2\2\u01fb\u01fc\7n\2\2\u01fc\u008e\3\2\2"+
		"\2\u01fd\u0201\5M\'\2\u01fe\u0201\5O(\2\u01ff\u0201\5Q)\2\u0200\u01fd"+
		"\3\2\2\2\u0200\u01fe\3\2\2\2\u0200\u01ff\3\2\2\2\u0201\u0208\3\2\2\2\u0202"+
		"\u0207\5M\'\2\u0203\u0207\5O(\2\u0204\u0207\5Q)\2\u0205\u0207\5\21\t\2"+
		"\u0206\u0202\3\2\2\2\u0206\u0203\3\2\2\2\u0206\u0204\3\2\2\2\u0206\u0205"+
		"\3\2\2\2\u0207\u020a\3\2\2\2\u0208\u0206\3\2\2\2\u0208\u0209\3\2\2\2\u0209"+
		"\u0090\3\2\2\2\u020a\u0208\3\2\2\2\u020b\u020c\t\n\2\2\u020c\u0092\3\2"+
		"\2\2\"\2\u009f\u0142\u0147\u014c\u0151\u0156\u015b\u0162\u0167\u016e\u0175"+
		"\u017c\u0183\u018a\u018f\u0194\u0199\u019b\u01a6\u01b1\u01bb\u01c0\u01c5"+
		"\u01ca\u01d0\u01d9\u01e2\u01eb\u0200\u0206\u0208\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
// Generated from F:/project/java/FXEditor/src/main/java/com/xx/XMLLexer/lexer/XMLLexer.g4 by ANTLR 4.13.2
package com.xx.XMLLexer.lexer;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class XMLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		OPEN=1, COMMENT=2, EntityRef=3, TEXT=4, CLOSE=5, SLASH_CLOSE=6, EQUIALS=7, 
		STARING=8, Name=9, S=10;
	public static final int
		INSIDE=1;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "INSIDE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"OPEN", "COMMENT", "EntityRef", "TEXT", "CLOSE", "SLASH_CLOSE", "EQUIALS", 
			"STARING", "Name", "S", "ALPHA", "DIGIT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'<'", null, null, null, "'>'", "'/>'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "OPEN", "COMMENT", "EntityRef", "TEXT", "CLOSE", "SLASH_CLOSE", 
			"EQUIALS", "STARING", "Name", "S"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public XMLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "XMLLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\na\u0006\uffff\uffff\u0006\uffff\uffff\u0002\u0000\u0007"+
		"\u0000\u0002\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007"+
		"\u0003\u0002\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007"+
		"\u0006\u0002\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n"+
		"\u0007\n\u0002\u000b\u0007\u000b\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0005\u0001%\b\u0001\n\u0001\f\u0001(\t\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001"+
		"\u0002\u0004\u00022\b\u0002\u000b\u0002\f\u00023\u0001\u0002\u0001\u0002"+
		"\u0001\u0003\u0004\u00039\b\u0003\u000b\u0003\f\u0003:\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0005\u0006H\b\u0006\n\u0006"+
		"\f\u0006K\t\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0001\b\u0005\bU\b\b\n\b\f\bX\t\b\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0002&I\u0000"+
		"\f\u0002\u0001\u0004\u0002\u0006\u0003\b\u0004\n\u0005\f\u0006\u000e\u0007"+
		"\u0010\b\u0012\t\u0014\n\u0016\u0000\u0018\u0000\u0002\u0000\u0001\u0005"+
		"\u0001\u0000az\u0002\u0000&&<<\u0003\u0000\t\n\r\r  \u0002\u0000AZaz\u0001"+
		"\u000009c\u0000\u0002\u0001\u0000\u0000\u0000\u0000\u0004\u0001\u0000"+
		"\u0000\u0000\u0000\u0006\u0001\u0000\u0000\u0000\u0000\b\u0001\u0000\u0000"+
		"\u0000\u0001\n\u0001\u0000\u0000\u0000\u0001\f\u0001\u0000\u0000\u0000"+
		"\u0001\u000e\u0001\u0000\u0000\u0000\u0001\u0010\u0001\u0000\u0000\u0000"+
		"\u0001\u0012\u0001\u0000\u0000\u0000\u0001\u0014\u0001\u0000\u0000\u0000"+
		"\u0002\u001a\u0001\u0000\u0000\u0000\u0004\u001e\u0001\u0000\u0000\u0000"+
		"\u0006/\u0001\u0000\u0000\u0000\b8\u0001\u0000\u0000\u0000\n<\u0001\u0000"+
		"\u0000\u0000\f@\u0001\u0000\u0000\u0000\u000eE\u0001\u0000\u0000\u0000"+
		"\u0010N\u0001\u0000\u0000\u0000\u0012Q\u0001\u0000\u0000\u0000\u0014Y"+
		"\u0001\u0000\u0000\u0000\u0016]\u0001\u0000\u0000\u0000\u0018_\u0001\u0000"+
		"\u0000\u0000\u001a\u001b\u0005<\u0000\u0000\u001b\u001c\u0001\u0000\u0000"+
		"\u0000\u001c\u001d\u0006\u0000\u0000\u0000\u001d\u0003\u0001\u0000\u0000"+
		"\u0000\u001e\u001f\u0005<\u0000\u0000\u001f \u0005!\u0000\u0000 !\u0005"+
		"-\u0000\u0000!\"\u0005-\u0000\u0000\"&\u0001\u0000\u0000\u0000#%\t\u0000"+
		"\u0000\u0000$#\u0001\u0000\u0000\u0000%(\u0001\u0000\u0000\u0000&\'\u0001"+
		"\u0000\u0000\u0000&$\u0001\u0000\u0000\u0000\')\u0001\u0000\u0000\u0000"+
		"(&\u0001\u0000\u0000\u0000)*\u0005-\u0000\u0000*+\u0005-\u0000\u0000+"+
		",\u0005>\u0000\u0000,-\u0001\u0000\u0000\u0000-.\u0006\u0001\u0001\u0000"+
		".\u0005\u0001\u0000\u0000\u0000/1\u0005&\u0000\u000002\u0007\u0000\u0000"+
		"\u000010\u0001\u0000\u0000\u000023\u0001\u0000\u0000\u000031\u0001\u0000"+
		"\u0000\u000034\u0001\u0000\u0000\u000045\u0001\u0000\u0000\u000056\u0005"+
		";\u0000\u00006\u0007\u0001\u0000\u0000\u000079\b\u0001\u0000\u000087\u0001"+
		"\u0000\u0000\u00009:\u0001\u0000\u0000\u0000:8\u0001\u0000\u0000\u0000"+
		":;\u0001\u0000\u0000\u0000;\t\u0001\u0000\u0000\u0000<=\u0005>\u0000\u0000"+
		"=>\u0001\u0000\u0000\u0000>?\u0006\u0004\u0002\u0000?\u000b\u0001\u0000"+
		"\u0000\u0000@A\u0005/\u0000\u0000AB\u0005>\u0000\u0000BC\u0001\u0000\u0000"+
		"\u0000CD\u0006\u0005\u0002\u0000D\r\u0001\u0000\u0000\u0000EI\u0005=\u0000"+
		"\u0000FH\t\u0000\u0000\u0000GF\u0001\u0000\u0000\u0000HK\u0001\u0000\u0000"+
		"\u0000IJ\u0001\u0000\u0000\u0000IG\u0001\u0000\u0000\u0000JL\u0001\u0000"+
		"\u0000\u0000KI\u0001\u0000\u0000\u0000LM\u0005\"\u0000\u0000M\u000f\u0001"+
		"\u0000\u0000\u0000NO\u0005\"\u0000\u0000OP\u0003\u0012\b\u0000P\u0011"+
		"\u0001\u0000\u0000\u0000QV\u0003\u0016\n\u0000RU\u0003\u0016\n\u0000S"+
		"U\u0003\u0018\u000b\u0000TR\u0001\u0000\u0000\u0000TS\u0001\u0000\u0000"+
		"\u0000UX\u0001\u0000\u0000\u0000VT\u0001\u0000\u0000\u0000VW\u0001\u0000"+
		"\u0000\u0000W\u0013\u0001\u0000\u0000\u0000XV\u0001\u0000\u0000\u0000"+
		"YZ\u0007\u0002\u0000\u0000Z[\u0001\u0000\u0000\u0000[\\\u0006\t\u0001"+
		"\u0000\\\u0015\u0001\u0000\u0000\u0000]^\u0007\u0003\u0000\u0000^\u0017"+
		"\u0001\u0000\u0000\u0000_`\u0007\u0004\u0000\u0000`\u0019\u0001\u0000"+
		"\u0000\u0000\b\u0000\u0001&3:ITV\u0003\u0005\u0001\u0000\u0006\u0000\u0000"+
		"\u0004\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
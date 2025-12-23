// Generated from F:/project/java/FXEditor/src/main/java/com/xx/starter/array/ArrayInit.g4 by ANTLR 4.13.2
package com.xx.antlr.arrayInit.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class ArrayInitParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, INT=4, WS=5;
	public static final int
		RULE_prog = 0, RULE_init = 1, RULE_value = 2;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "init", "value"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "','", "'}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, "INT", "WS"
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

	@Override
	public String getGrammarFileName() { return "ArrayInit.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ArrayInitParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgContext extends ParserRuleContext {
		public InitContext init() {
			return getRuleContext(InitContext.class,0);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ArrayInitListener ) ((ArrayInitListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ArrayInitListener ) ((ArrayInitListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ArrayInitVisitor ) return ((ArrayInitVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(6);
			init();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InitContext extends ParserRuleContext {
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public InitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_init; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ArrayInitListener ) ((ArrayInitListener)listener).enterInit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ArrayInitListener ) ((ArrayInitListener)listener).exitInit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ArrayInitVisitor ) return ((ArrayInitVisitor<? extends T>)visitor).visitInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InitContext init() throws RecognitionException {
		InitContext _localctx = new InitContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_init);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(8);
			match(T__0);
			setState(9);
			value();
			setState(14);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(10);
				match(T__1);
				setState(11);
				value();
				}
				}
				setState(16);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(17);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueContext extends ParserRuleContext {
		public InitContext init() {
			return getRuleContext(InitContext.class,0);
		}
		public TerminalNode INT() { return getToken(ArrayInitParser.INT, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ArrayInitListener ) ((ArrayInitListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ArrayInitListener ) ((ArrayInitListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ArrayInitVisitor ) return ((ArrayInitVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_value);
		try {
			setState(21);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(19);
				init();
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 2);
				{
				setState(20);
				match(INT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0005\u0018\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0005\u0001\r\b\u0001\n\u0001\f\u0001\u0010\t"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0003\u0002\u0016"+
		"\b\u0002\u0001\u0002\u0000\u0000\u0003\u0000\u0002\u0004\u0000\u0000\u0016"+
		"\u0000\u0006\u0001\u0000\u0000\u0000\u0002\b\u0001\u0000\u0000\u0000\u0004"+
		"\u0015\u0001\u0000\u0000\u0000\u0006\u0007\u0003\u0002\u0001\u0000\u0007"+
		"\u0001\u0001\u0000\u0000\u0000\b\t\u0005\u0001\u0000\u0000\t\u000e\u0003"+
		"\u0004\u0002\u0000\n\u000b\u0005\u0002\u0000\u0000\u000b\r\u0003\u0004"+
		"\u0002\u0000\f\n\u0001\u0000\u0000\u0000\r\u0010\u0001\u0000\u0000\u0000"+
		"\u000e\f\u0001\u0000\u0000\u0000\u000e\u000f\u0001\u0000\u0000\u0000\u000f"+
		"\u0011\u0001\u0000\u0000\u0000\u0010\u000e\u0001\u0000\u0000\u0000\u0011"+
		"\u0012\u0005\u0003\u0000\u0000\u0012\u0003\u0001\u0000\u0000\u0000\u0013"+
		"\u0016\u0003\u0002\u0001\u0000\u0014\u0016\u0005\u0004\u0000\u0000\u0015"+
		"\u0013\u0001\u0000\u0000\u0000\u0015\u0014\u0001\u0000\u0000\u0000\u0016"+
		"\u0005\u0001\u0000\u0000\u0000\u0002\u000e\u0015";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
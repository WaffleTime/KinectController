/* ControllerFSMFactory.java */
/* Generated By:JavaCC: Do not edit this line. ControllerFSMFactory.java */
package com.lcsc.hackathon.kinectcontroller.config;

import java.io.*;
import java.util.*;
import com.lcsc.hackathon.kinectcontroller.controller.*;

public class ControllerFSMFactory implements ControllerFSMFactoryConstants {
    private ControllerStateMachine _csm;
    private String _startingStateId = null;

    private void addStartingState(String stateId) {
        //If we can't change the state, then we'll just store the id until the state is added later.
        if (!_csm.changeState(stateId)) {
            _startingStateId = stateId;
        }
    }

    private void addState(ControllerState state) {
        _csm.addState(state);

        //If the starting state hasn't been set and this state is the starting state.
        if (_startingStateId != null && _startingStateId.equals(state.stateId)) {
            if (_csm.changeState(_startingStateId)) {
                _startingStateId = null;
            }
        }
    }

  final public ControllerStateMachine create() throws ParseException {Token t;
    int version;
_csm = new ControllerStateMachine();
    jj_consume_token(VERSION);
    t = jj_consume_token(NUMBER);
version = Integer.parseInt(t.image);
switch(version) {
            case 1:
                parseVersion1();
                break;
        }
    jj_consume_token(0);
{if ("" != null) return _csm;}
    throw new Error("Missing return statement in function");
  }

  final public void parseVersion1() throws ParseException {String stateId;
    ControllerState state;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case STARTING_STATE:
      case STATE:{
        ;
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case STARTING_STATE:{
        jj_consume_token(STARTING_STATE);
        jj_consume_token(IS);
        stateId = jj_consume_token(IDENTIFIER).image;
addStartingState(stateId);
        break;
        }
      case STATE:{
        jj_consume_token(STATE);
        stateId = jj_consume_token(IDENTIFIER).image;
        state = parseState(stateId);
addState(state);
        break;
        }
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  final public ControllerState parseState(String stateId) throws ParseException {ControllerState state;
    String gestureId;
    String armId;
state = new ControllerState(stateId);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case GESTURE:
      case MOUSE_GESTURE:{
        ;
        break;
        }
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case GESTURE:{
        jj_consume_token(GESTURE);
        gestureId = jj_consume_token(IDENTIFIER).image;
        break;
        }
      case MOUSE_GESTURE:{
        jj_consume_token(MOUSE_GESTURE);
        armId = jj_consume_token(IDENTIFIER).image;
        break;
        }
      default:
        jj_la1[3] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
{if ("" != null) return state;}
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public ControllerFSMFactoryTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[4];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x12,0x12,0x60,0x60,};
   }

  /** Constructor with InputStream. */
  public ControllerFSMFactory(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public ControllerFSMFactory(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ControllerFSMFactoryTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public ControllerFSMFactory(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ControllerFSMFactoryTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public ControllerFSMFactory(ControllerFSMFactoryTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ControllerFSMFactoryTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk_f() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[16];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 4; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 16; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}

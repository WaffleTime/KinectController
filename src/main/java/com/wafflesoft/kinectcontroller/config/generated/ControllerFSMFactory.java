/* Generated By:JavaCC: Do not edit this line. ControllerFSMFactory.java */
package com.wafflesoft.kinectcontroller.config.generated;

import java.io.*;
import java.util.*;
import com.wafflesoft.kinectcontroller.controller.*;
import com.wafflesoft.kinectcontroller.posturerules.*;

import com.wafflesoft.kinectcontroller.Conversions;

import com.wafflesoft.kinectcontroller.config.factories.PremadeGestureType;
import com.wafflesoft.kinectcontroller.config.factories.GestureFactory;

import com.wafflesoft.kinectcontroller.config.installers.*;

//These are the Reaction objects.

import com.wafflesoft.kinectcontroller.emulation.*;
import com.wafflesoft.kinectcontroller.emulation.reactions.persistent.*;
import com.wafflesoft.kinectcontroller.emulation.reactions.hapaxlegomenon.*;
import com.wafflesoft.kinectcontroller.emulation.reactions.config.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.InstantiationException;

public class ControllerFSMFactory implements ControllerFSMFactoryConstants {
    private static final Logger _logger = LoggerFactory.getLogger(ControllerFSMFactory.class);
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

  final public ControllerStateMachine create() throws ParseException {
    Token t;
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
     {if (true) return _csm;}
    throw new Error("Missing return statement in function");
  }

  final public void parseVersion1() throws ParseException {
    String stateId;
    ControllerState state;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case STARTING_STATE:
      case STATE:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case STARTING_STATE:
        jj_consume_token(STARTING_STATE);
        jj_consume_token(IS);
        stateId = jj_consume_token(IDENTIFIER).image;
             addStartingState(stateId);
        break;
      case STATE:
        jj_consume_token(STATE);
        stateId = jj_consume_token(IDENTIFIER).image;
        state = parseState(stateId);
             addState(state);
        break;
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  final public ControllerState parseState(String stateId) throws ParseException {
    ControllerState state;
    String gestureId;
    String armId;
     state = new ControllerState(stateId, _csm);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case GESTURE:
      case PREMADE_GESTURE:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PREMADE_GESTURE:
        jj_consume_token(PREMADE_GESTURE);
        gestureId = jj_consume_token(IDENTIFIER).image;
        parsePremadeGesture(gestureId, state);
        break;
      case GESTURE:
        jj_consume_token(GESTURE);
        gestureId = jj_consume_token(IDENTIFIER).image;
        parseGesture(gestureId, state);
        break;
      default:
        jj_la1[3] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
     {if (true) return state;}
    throw new Error("Missing return statement in function");
  }

  final public void parsePremadeGesture(String gestureId, ControllerState state) throws ParseException {
    Token           t;
    Map<String,String> config = new HashMap<String,String>();

    String tmpKey;
    String tmpValue;
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IDENTIFIER:
        ;
        break;
      default:
        jj_la1[4] = jj_gen;
        break label_3;
      }
      tmpKey = jj_consume_token(IDENTIFIER).image;
      jj_consume_token(IS);
      tmpValue = jj_consume_token(IDENTIFIER).image;
         config.put(tmpKey, tmpValue);
    }
        //Using the a gesture id that was given in the config, we'll determine the class name for the factory that will
        //create and install the premade gesture.
        PremadeGestureType type = PremadeGestureType.fromString(gestureId);
        String factoryFullName  = "com.wafflesoft.kinectcontroller.config.factories." + type.factoryClassName;

        //This instantiates a GestureFactory for some premade gesture. We will be interacting with that implementation
        //through the GestureFactory interface
        try {
            GestureFactory factory = (GestureFactory)Class.forName(factoryFullName).newInstance();

            //This kicks off whatever specific implementation of the factory that was defined.
            boolean isValid = factory.loadConfig(config);

            //We will only install this gesture if the configuration was valid.
            if (isValid) {
                factory.installGesture(state);
            }
            else {
                _logger.warn("Invalid Configuration for Premade Gesture: " + gestureId);
            }
        } catch (ClassNotFoundException e) {
            _logger.warn(String.format("Factory Class Not Found: %s", factoryFullName), e);
        } catch (InstantiationException e) {
            _logger.warn(String.format("Factory Class Instantiation Failed: %s", factoryFullName), e);
        } catch (IllegalAccessException e) {
            _logger.warn(String.format("Factory Class Illegal Access: %s", factoryFullName), e);
        }
  }

  final public void parseGesture(String gestureId, ControllerState state) throws ParseException {
    String          ruleType;
    String          reactionType;
    Token           t;
    Gesture         gesture = new Gesture(gestureId, state);
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case RULE:
      case REACTION:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_4;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case RULE:
        jj_consume_token(RULE);
        ruleType = jj_consume_token(IDENTIFIER).image;
                switch(RuleType.fromString(ruleType)) {
                    case DISTANCE_FROM_POINT:
                        parseDistanceFromPointRule(state, gesture);
                        break;
                    case POSITIONX:
                    case POSITIONY:
                    case POSITIONZ:
                        parsePositionRule(state, gesture, RuleType.fromString(ruleType));
                        break;
                    case ANGLE:
                        parseAngleRule(state, gesture);
                        break;
                    case DISTANCE:
                    case DISTANCEX:
                    case DISTANCEY:
                    case DISTANCEZ:
                        parseDistanceRule(state, gesture, RuleType.fromString(ruleType));
                        break;
                }
        break;
      case REACTION:
        jj_consume_token(REACTION);
        reactionType = jj_consume_token(IDENTIFIER).image;
                switch(ReactionType.fromString(reactionType)) {
                    case KEY_DOWN_UP:
                    case KEY_DOWN:
                    case KEY_UP:
                        parseKeyReaction(gesture, ReactionType.fromString(reactionType));
                        break;
                    case SHUTDOWN:
                        gesture.addReaction(new ShutdownReaction());
                        break;
                }
        break;
      default:
        jj_la1[6] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
     state.addGesture(gestureId, gesture);
  }

  final public void parseKeyReaction(Gesture gesture, ReactionType reactionType) throws ParseException {
    String keyId;
    jj_consume_token(KEY);
    jj_consume_token(IS);
    keyId = jj_consume_token(IDENTIFIER).image;
                ButtonReactionConfig config = new ButtonReactionConfig(keyId, "keyboard");
                gesture.addReaction(new ButtonReaction(config));
  }

  final public void parseAngleRule(ControllerState state, Gesture gesture) throws ParseException {
    Token   t;
    String  end1        = null;
    String  vertex      = null;
    String  end2        = null;
    Integer minAngle    = null;
    Integer maxAngle    = null;
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case END1:
      case END2:
      case VERTEX:
      case MIN_ANGLE:
      case MAX_ANGLE:
        ;
        break;
      default:
        jj_la1[7] = jj_gen;
        break label_5;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case END1:
        jj_consume_token(END1);
        jj_consume_token(IS);
        end1 = jj_consume_token(IDENTIFIER).image;
        break;
      case END2:
        jj_consume_token(END2);
        jj_consume_token(IS);
        end2 = jj_consume_token(IDENTIFIER).image;
        break;
      case VERTEX:
        jj_consume_token(VERTEX);
        jj_consume_token(IS);
        vertex = jj_consume_token(IDENTIFIER).image;
        break;
      case MIN_ANGLE:
        jj_consume_token(MIN_ANGLE);
        jj_consume_token(IS);
        t = jj_consume_token(NUMBER);
             minAngle = Integer.parseInt(t.image);
        break;
      case MAX_ANGLE:
        jj_consume_token(MAX_ANGLE);
        jj_consume_token(IS);
        t = jj_consume_token(NUMBER);
             maxAngle = Integer.parseInt(t.image);
        break;
      default:
        jj_la1[8] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
        String className = RuleType.ANGLE.className;

         //Error checking for a valid Angle rule
        boolean valid = true;

        if (end1 == null) {
            _logger.error(String.format("Angle Rule within %s gesture requires an end1 attribute!", gesture.gestureId));
            _logger.error("Usage:\u005cngesture <GestureId>\u005cn\u005ctrule Angle\u005cn\u005ct\u005ctend1 is <JointID>");
            valid = false;
        }
        if (vertex == null) {
            _logger.error(String.format("%s Rule within %s gesture requires an vertex attribute!", className, gesture.gestureId));
            _logger.error(String.format("Usage:\u005cngesture <GestureId>\u005cn\u005ctrule %s\u005cn\u005ct\u005ctvertex is <JointID>", className));
            valid = false;
        }
        if (end2 == null) {
            _logger.error(String.format("%s Rule within %s gesture requires an end2 attribute!", className, gesture.gestureId));
            _logger.error(String.format("Usage:\u005cngesture <GestureId>\u005cn\u005ctrule %s\u005cn\u005ct\u005ctend2 is <JointID>", className));
            valid = false;
        }
        if (minAngle == null) {
            _logger.error(String.format("%s Rule within %s gesture requires an min_angle attribute!", className, gesture.gestureId));
            _logger.error(String.format("Usage:\u005cngesture <GestureId>\u005cn\u005ctrule %s\u005cn\u005ct\u005ctmin_angle is <JointID>", className));
            valid = false;
        }
        if (maxAngle == null) {
            _logger.error(String.format("%s Rule within %s gesture requires an max_angle attribute!", className, gesture.gestureId));
            _logger.error(String.format("Usage:\u005cngesture <GestureId>\u005cn\u005ctrule %s\u005cn\u005ct\u005ctmax_angle is <JointID>", className));
            valid = false;
        }
        if (!valid) {
            System.exit(1);
        }

        AngleRuleInstaller.install(gesture, end1, vertex, end2, minAngle, maxAngle);
  }

  final public void parseDistanceRule(ControllerState state, Gesture gesture, RuleType ruleType) throws ParseException {
    Token   t;
    String  joint1      = null;
    String  joint2      = null;
    Double  minDistance = null;
    Double  maxDistance = null;
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case JOINT1:
      case JOINT2:
      case MIN_DISTANCE:
      case MAX_DISTANCE:
        ;
        break;
      default:
        jj_la1[9] = jj_gen;
        break label_6;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case JOINT1:
        jj_consume_token(JOINT1);
        jj_consume_token(IS);
        joint1 = jj_consume_token(IDENTIFIER).image;
        break;
      case JOINT2:
        jj_consume_token(JOINT2);
        jj_consume_token(IS);
        joint2 = jj_consume_token(IDENTIFIER).image;
        break;
      case MIN_DISTANCE:
        jj_consume_token(MIN_DISTANCE);
        jj_consume_token(IS);
        t = jj_consume_token(NUMBER);
             minDistance = Double.parseDouble(t.image);
        break;
      case MAX_DISTANCE:
        jj_consume_token(MAX_DISTANCE);
        jj_consume_token(IS);
        t = jj_consume_token(NUMBER);
             maxDistance = Double.parseDouble(t.image);
        break;
      default:
        jj_la1[10] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
        String className = ruleType.className;

        //Error checking for a valid Angle rule
        boolean valid = true;

        if (joint1 == null) {
            _logger.error(String.format("%s Rule within %s gesture requires an joint1 attribute!", className, gesture.gestureId));
            _logger.error(String.format("Usage:\u005cngesture <GestureId>\u005cn\u005ctrule %s\u005cn\u005ct\u005ctjoint1 is <JointID>", className));
            valid = false;
        }
        if (joint2 == null) {
            _logger.error(String.format("%s Rule within %s gesture requires an joint2 attribute!", className, gesture.gestureId));
            _logger.error(String.format("Usage:\u005cngesture <GestureId>\u005cn\u005ctrule %s\u005cn\u005ct\u005ctjoint2 is <JointID>", className));
            valid = false;
        }
        if (minDistance == null) {
            _logger.error(String.format("%s Rule within %s gesture requires an min_distance attribute!", className, gesture.gestureId));
            _logger.error(String.format("Usage:\u005cngesture <GestureId>\u005cn\u005ctrule %s\u005cn\u005ct\u005ctmin_distance is <Number>", className));
            valid = false;
        }
        if (maxDistance == null) {
            _logger.error(String.format("%s Rule within %s gesture requires an max_distance attribute!", className, gesture.gestureId));
            _logger.error(String.format("Usage:\u005cngesture <GestureId>\u005cn\u005ctrule %s\u005cn\u005ct\u005ctmax_distance is <Number>", className));
            valid = false;
        }
        if (!valid) {
            System.exit(1);
        }

        switch (ruleType) {
            case DISTANCE:
                DistanceRuleInstaller.install(gesture, joint1, joint2, minDistance, maxDistance);
                break;
            case DISTANCEX:
                DistanceRuleInstaller.installX(gesture, joint1, joint2, minDistance, maxDistance);
                break;
            case DISTANCEY:
                DistanceRuleInstaller.installY(gesture, joint1, joint2, minDistance, maxDistance);
                break;
            case DISTANCEZ:
                DistanceRuleInstaller.installZ(gesture, joint1, joint2, minDistance, maxDistance);
                break;
            default:
                _logger.error("Invalid RuleType given to parseDistanceRule(): " + ruleType.alias);
                System.exit(1);
                break;
        }
  }

  final public void parsePositionRule(ControllerState state, Gesture gesture, RuleType ruleType) throws ParseException {
    Token   t;
    String  joint   = null;
    Double  minPos  = null;
    Double  maxPos  = null;
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case JOINT:
      case MIN_POSITION:
      case MAX_POSITION:
        ;
        break;
      default:
        jj_la1[11] = jj_gen;
        break label_7;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case JOINT:
        jj_consume_token(JOINT);
        jj_consume_token(IS);
        joint = jj_consume_token(IDENTIFIER).image;
        break;
      case MIN_POSITION:
        jj_consume_token(MIN_POSITION);
        jj_consume_token(IS);
        t = jj_consume_token(NUMBER);
             minPos = Double.parseDouble(t.image);
        break;
      case MAX_POSITION:
        jj_consume_token(MAX_POSITION);
        jj_consume_token(IS);
        t = jj_consume_token(NUMBER);
             maxPos = Double.parseDouble(t.image);
        break;
      default:
        jj_la1[12] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
        String className = ruleType.className;

        //Error checking for a valid Angle rule
        boolean valid = true;

        if (joint == null) {
            _logger.error(String.format("%s Rule within %s gesture requires an joint attribute!", className, gesture.gestureId));
            _logger.error(String.format("Usage:\u005cngesture <GestureId>\u005cn\u005ctrule %s\u005cn\u005ct\u005ctjoint is <JointID>", className));
            valid = false;
        }
        if (minPos == null) {
            _logger.error(String.format("%s Rule within %s gesture requires an min_position attribute!", className, gesture.gestureId));
            _logger.error(String.format("Usage:\u005cngesture <GestureId>\u005cn\u005ctrule %s\u005cn\u005ct\u005ctmin_position is <Number>", className));
            valid = false;
        }
        if (maxPos == null) {
            _logger.error(String.format("%s Rule within %s gesture requires an max_position attribute!", className, gesture.gestureId));
            _logger.error(String.format("Usage:\u005cngesture <GestureId>\u005cn\u005ctrule %s\u005cn\u005ct\u005ctmax_position is <Number>", className));
            valid = false;
        }
        if (!valid) {
            System.exit(1);
        }

        switch(ruleType) {
            case POSITIONX:
                PositionRuleInstaller.installX(gesture, joint, minPos, maxPos);
                break;
            case POSITIONY:
                PositionRuleInstaller.installY(gesture, joint, minPos, maxPos);
                break;
            case POSITIONZ:
                PositionRuleInstaller.installZ(gesture, joint, minPos, maxPos);
                break;
            default:
                _logger.error("Invalid RuleType given to parsePositionRule(): " + ruleType.alias);
                System.exit(1);
                break;
        }
  }

  final public void parseDistanceFromPointRule(ControllerState state, Gesture gesture) throws ParseException {
    Token   t;
    String  joint       = null;
    Double  minDistance = null;
    Double  maxDistance = null;
    Double  pointX      = new Double(0);
    Double  pointY      = new Double(0);
    Double  pointZ      = new Double(1500);
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case JOINT:
      case MIN_DISTANCE:
      case MAX_DISTANCE:
      case POINT:
        ;
        break;
      default:
        jj_la1[13] = jj_gen;
        break label_8;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case JOINT:
        jj_consume_token(JOINT);
        jj_consume_token(IS);
        joint = jj_consume_token(IDENTIFIER).image;
        break;
      case POINT:
        jj_consume_token(POINT);
        jj_consume_token(IS);
        t = jj_consume_token(NUMBER);
             pointX = Double.parseDouble(t.image);
        jj_consume_token(COMMA);
        t = jj_consume_token(NUMBER);
             pointY = Double.parseDouble(t.image);
        jj_consume_token(COMMA);
        t = jj_consume_token(NUMBER);
             pointZ = Double.parseDouble(t.image);
        break;
      case MIN_DISTANCE:
        jj_consume_token(MIN_DISTANCE);
        jj_consume_token(IS);
        t = jj_consume_token(NUMBER);
             minDistance = Double.parseDouble(t.image);
        break;
      case MAX_DISTANCE:
        jj_consume_token(MAX_DISTANCE);
        jj_consume_token(IS);
        t = jj_consume_token(NUMBER);
             maxDistance = Double.parseDouble(t.image);
        break;
      default:
        jj_la1[14] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
        String className = RuleType.DISTANCE_FROM_POINT.className;

        //Error checking for a valid Angle rule
        boolean valid = true;

        if (joint == null) {
            _logger.error(String.format("%s Rule within %s gesture requires an joint attribute!", className, gesture.gestureId));
            _logger.error(String.format("Usage:\u005cngesture <GestureId>\u005cn\u005ctrule %s\u005cn\u005ct\u005ctjoint is <JointID>", className));
            valid = false;
        }
        if (minDistance == null) {
            _logger.error(String.format("%s Rule within %s gesture requires an min_distance attribute!", className, gesture.gestureId));
            _logger.error(String.format("Usage:\u005cngesture <GestureId>\u005cn\u005ctrule %s\u005cn\u005ct\u005ctmin_distance is <Number>", className));
            valid = false;
        }
        if (maxDistance == null) {
            _logger.error(String.format("%s Rule within %s gesture requires an max_distance attribute!", className, gesture.gestureId));
            _logger.error(String.format("Usage:\u005cngesture <GestureId>\u005cn\u005ctrule %s\u005cn\u005ct\u005ctmax_distance is <Number>", className));
            valid = false;
        }
        if (!valid) {
            System.exit(1);
        }

        double[] point = new double[] {pointX.doubleValue(), pointY.doubleValue(), pointZ.doubleValue()};

        DistanceFromPointRuleInstaller.install(gesture, joint, point, minDistance, maxDistance);
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
  final private int[] jj_la1 = new int[15];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x12,0x12,0x60,0x60,0x4000000,0x400100,0x400100,0x1c600,0x1c600,0x61800,0x61800,0x182000,0x182000,0x262000,0x262000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,};
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
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
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
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public ControllerFSMFactory(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ControllerFSMFactoryTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public ControllerFSMFactory(ControllerFSMFactoryTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ControllerFSMFactoryTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
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

  private int jj_ntk() {
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
    boolean[] la1tokens = new boolean[33];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 15; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 33; i++) {
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

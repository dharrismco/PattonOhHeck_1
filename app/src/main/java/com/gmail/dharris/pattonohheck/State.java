package com.gmail.dharris.pattonohheck;

import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.gmail.dharris.pattonohheck.client.POHResponse;

public class State {

    public static NetworkHandler nh;
    public static boolean settingsLock=false;
    public static boolean ALPHA = true;
    public static Object HandSync = new Object();
    public static Hand hand = null;
    public static int playerNumber=-1;
    public static Card trump;
    public static boolean showWon=false;
    public static Handler updateUIHandler = null;
    public static Card curSelectedCard=null;
    public static int state =0;
    public static int wonTrick=0;
    public static boolean lock=false;
    public static boolean showNumCard=false;

    public static POHResponse score = null;
    public static POHResponse lastBid = null;
    public static RelativeLayout RL;
    public static int maxRounds=10;

    public static java.util.ArrayList<View> playedViews = new java.util.ArrayList<View>();
    public static java.util.ArrayList<View> namedViews = new java.util.ArrayList<View>();

}

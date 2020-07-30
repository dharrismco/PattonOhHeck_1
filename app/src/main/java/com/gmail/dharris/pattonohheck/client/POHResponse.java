package com.gmail.dharris.pattonohheck.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gmail.dharris.pattonohheck.Card;
import com.gmail.dharris.pattonohheck.Hand;
import com.gmail.dharris.pattonohheck.ScoreBoardManagementSystem;
import com.gmail.dharris.pattonohheck.SettingObject;

public class POHResponse {
    public static class POHResponseScore
    {
        public int playerNumber;
        public int bid;
        public int taken;
        public int score;
        public String playerName;

    }
    public String ResponseType;
    public Hand responseHand;
    public boolean gameAvailable;
    public boolean gameCreated;
    public String[] playerNames;
    public String cardPlayed;
    public String playerRequest;
    public int playerNumber;
    public int currentPlayerNumber;
    public boolean lastCard;
    public String currentSuitLed;
    public Card trump;
    public String won;
    public boolean wonTrick;
    public int bidder;
    public boolean biddingDone;
    public int round;
    public int cantBid;
    public POHResponseScore[] scores;
    public int dealer;
    public String winningCard;
    public int maxRounds;
    public ScoreBoardManagementSystem.SBMSGame scoreBoard;
}

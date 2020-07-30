package com.gmail.dharris.pattonohheck.client;

import com.gmail.dharris.pattonohheck.Card;
import com.gmail.dharris.pattonohheck.SettingObject;

public class POHRequest {
        public String requestType;
        public String playerName;
        public Card cardPlayed;
        public int rounds;
        public int bid;
        public String playerId = SettingObject.getPlayerId();
}

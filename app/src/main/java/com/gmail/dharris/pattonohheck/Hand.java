package com.gmail.dharris.pattonohheck;

import java.util.Arrays;
import java.util.Comparator;

public class Hand {
    private Card[] cards;

    public Hand()
    {

    }
    public Hand(java.util.ArrayList<Card> hand)
    {
        cards=hand.toArray(new Card[hand.size()]);


    }

    public Card[] getCards()
    {
        return cards;
    }
}

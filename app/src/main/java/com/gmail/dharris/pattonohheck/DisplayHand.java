package com.gmail.dharris.pattonohheck;

import android.graphics.Matrix;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class DisplayHand {

    private Card trump;
    boolean firstUse=true;
    private java.util.ArrayList<Card> cards = new java.util.ArrayList<Card>();

    public void setTrump(Card t)
    {
        trump = t;
    }
    public Card getTrump()
    {
        return trump;
    }

    public DisplayHand(Card[] cards)
    {

        for (int i = 0; i < cards.length; i++) {
            this.cards.add(cards[i]);
        }
    }

    public java.util.ArrayList<Card> getCards()
    {
        if (firstUse)
        {
            firstUse=false;
            sort();
        }
        return cards;
    }

    public void sort() {
        Collections.sort(cards, new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return o1.getSortValue(trump) - o2.getSortValue(trump);
            }
        });
        int totalX= (int) (cards.size()*Constants.getDealtCardsSeperation());
        int startRotation = cards.size()*-1;
        int startY = (int)(Constants.getDealtCardPivotY()+((cards.size()/2)*(7*Constants.scaleY)));
        for (int i = 0; i < cards.size(); i++) {

            if (cards.get(i).imgView != null) {
                cards.get(i).imgView.setY(startY);
                //cards.get(i).imgView.setX(Constants.dealtCardsStartingX + (i * (Constants.dealtCardsSeperation*Constants.scaleX)));
                cards.get(i).imgView.setX((int)(Constants.getDealtCardPivotX()-(.5*totalX))+(i*Constants.getDealtCardsSeperation()));
                cards.get(i).imgView.bringToFront();
                cards.get(i).imgView.setRotation(startRotation+(i*2));
                if (i < (cards.size()/2))
                {
                    startY-=(int)(7*Constants.scaleY);
                }
                else
                {
                    startY+=(int)(7*Constants.scaleY);
                }

            }
        }
    }
}

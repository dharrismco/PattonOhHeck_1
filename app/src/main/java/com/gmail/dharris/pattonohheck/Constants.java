package com.gmail.dharris.pattonohheck;

public class Constants {

    public static final int dealtCardsStartingX=20;
    public static final int dealtCardsStartingY=750;
    public static final int dealtCardsYRise=20;
    public static final int playedCardsStartingX=30;
    public static final int playedCardsStartingY=20;
    public static final int playedCardsNameSeperation=20;
    public static final int playedCardsNameTextSize=12;
    public static final int dealtCardsSeperation=70;
    public static final int playedCardsSeperation=190;
    public static int windowSizeX;
    public static int windowSizeY;

    public static int getX(int x)
    {
        return (int)(x*scaleX);
    }

    public static int getY(int y)
    {
        return (int)(y*scaleY);
    }
    public static final int trumpCardX=1000;
    public static final int trumpCardY=380;

    public static float scaleX=1;
    public static float scaleY=1;

    public static final int scoreStartX=1600;
    public static final int scoreStartY=20;
    public static final int scoreSpacingY=60;

    public static final int maxX=2392;
    private static final int dealtCardPivotX=400;
    private static final int dealtCardPivotY=700;

    public static int getDealtCardPivotX()
    {
        return (int)(dealtCardPivotX*scaleX);
    }
    public static int getDealtCardPivotY()
    {
        return (int)(dealtCardPivotY*scaleY);
    }
    public static int getDealtCardsSeperation()
    {
        return (int)(dealtCardsSeperation*scaleX);
    }



}

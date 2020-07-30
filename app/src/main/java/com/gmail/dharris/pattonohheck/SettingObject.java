package com.gmail.dharris.pattonohheck;

import android.content.SharedPreferences;
import android.graphics.Color;

import java.util.UUID;

public class SettingObject {
    private static String playerName=null;
    private static boolean HighToLow = false;

    public static boolean isTrumpRight() {
        return TrumpRight;
    }

    public static void setTrumpRight(boolean trumpRight) {
        TrumpRight = trumpRight;
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("trumpRight", trumpRight);
        editor.commit();

    }

    public static boolean isDebug() {
        return settings.getBoolean("debugEnabled", false);
    }

    public static void setDebug(boolean debug) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("debugEnabled", debug);
        editor.commit();

    }


    public static void resetColors()
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("backgroundColorInt", Color.WHITE);
        editor.putInt("buttonColorInt", Color.LTGRAY);
        editor.putInt("handLeaderColorInt", Color.parseColor("#228B22"));
        editor.putInt("textColorInt", Color.BLACK);
        editor.putInt("buttonTextColorInt", Color.BLACK);
        editor.putInt("bidderHighlightColorInt", Color.GREEN);
        editor.putInt("playerHighlightColorInt", Color.parseColor("#99CCFF"));
        editor.commit();
    }
    private static boolean TrumpRight = false;
    private static SharedPreferences settings;
    private static String backgroundColor;

    public static String getPlayerName() {
        return playerName;
    }

    public static void setPlayerName(String playerName) {
        SettingObject.playerName = playerName;
    }

    public static boolean isHighToLow() {
        return HighToLow;
    }

    public static void setHighToLow(boolean highToLow) {
        SettingObject.HighToLow = highToLow;
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("highToLow", highToLow);
        editor.commit();

    }

    public static int getBackgroundColorInt()
    {
        return settings.getInt("backgroundColorInt", Color.WHITE);
    }

    public static void setBackgroundColorInt(int bgc)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("backgroundColorInt", bgc);
        editor.commit();
    }

    public static String getPlayerId()
    {
        return settings.getString("playerId", null);
    }

    public static void setPlayerId(String pid)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("playerId", pid);
        editor.commit();
    }

    public static int getDealerIndicatorInt()
    {
        return settings.getInt("dealerIndicator", Color.BLUE);
    }

    public static void setBDealerIndicatorInt(int bgc)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("dealerIndicator", bgc);
        editor.commit();
    }
    public static int getButtonColorInt()
    {
        return settings.getInt("buttonColorInt", Color.LTGRAY);
    }

    public static void setButtonColorInt(int bgc)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("buttonColorInt", bgc);
        editor.commit();
    }

    public static int getHandLeaderColorInt()
    {
        return settings.getInt("handLeaderColorInt", Color.parseColor("#228B22"));
    }

    public static void setHandLeaderColorInt(int bgc)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("handLeaderColorInt", bgc);
        editor.commit();
    }
    public static int getTextColorInt()
    {
        return settings.getInt("textColorInt", Color.BLACK);
    }

    public static void setTextColorInt(int bgc)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("textColorInt", bgc);
        editor.commit();
    }

    public static int getButtonTextColorInt()
    {
        return settings.getInt("buttonTextColorInt", Color.BLACK);
    }

    public static void setButtonTextColorInt(int bgc)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("buttonTextColorInt", bgc);
        editor.commit();
    }

    public static int getBidderHighlightColorInt()
    {
        return settings.getInt("bidderHighlightColorInt", Color.GREEN);
    }

    public static void setBidderHighlightColorInt(int bgc)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("bidderHighlightColorInt", bgc);
        editor.commit();
    }

    public static int getPlayerHighlightColorInt()
    {
        return settings.getInt("playerHighlightColorInt", Color.parseColor("#99CCFF"));
    }

    public static void setPlayerHightlightColorInt(int bgc)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("playerHighlightColorInt", bgc);
        editor.commit();
    }



    public static SharedPreferences getSettings() {
        return settings;
    }

    public static void setSettings(SharedPreferences settings) {
        SettingObject.settings = settings;
    }
}

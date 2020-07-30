package com.gmail.dharris.pattonohheck;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.gmail.dharris.pattonohheck.client.POHResponse;

import java.util.Timer;
import java.util.TimerTask;

public class ScoreDisplay {

    private TextView name = null;
    private TextView bid = null;
    private TextView taken = null;
    private TextView scoreView = null;
    private TextView bidP = null;
    private TextView takenP = null;
    private TextView scoreViewP = null;

    public ImageView emoji=null;

    private static TextView overBid = null;
    private static TextView underBid = null;
    private static TextView nameHeader = null;
    private static TextView bidHeader = null;
    private static TextView takenHeader = null;
    private static TextView scoreViewHeader = null;
    private static TextView DealtHeader = null;
    private static TextView roundView = null;
    private static ImageView highlighterBid=null;
    private static ImageView highlighterPlay=null;
    private static ImageView lineSeperator=null;
    private static RelativeLayout relativeLayout=null;
    private static ImageView triangleUp=null;
    private static ImageView triangleDown=null;
    private static ImageView dealerCircle=null;
    private static TextView differentialView=null;
    private static TextView totalBidHeader=null;
    private static TextView totalBidNumberHeader=null;
    private static TextView totalBidText=null;
    private static Button previousButton=null;
    public static MainActivity _aca;

    private Object emojiLock=new Object();

    private static boolean showScoringHeader=false;

    private static ScrollView scoreBoardArea;
    private static RelativeLayout scoreDisplayRL;

    public static void setupScoreDisplay(int totalPlayers, RelativeLayout rl, AppCompatActivity context, boolean reset)
    {
        boolean resetting=false;
        if (scoreBoardArea != null && reset) {
            scoreBoardArea.removeAllViews();
            scoreDisplayRL.removeAllViews();
            rl.removeView(scoreBoardArea);
            resetting=true;
        }
        else if (scoreBoardArea != null)
        {
            //RelativeLayout.LayoutParams SLP2 = new RelativeLayout.LayoutParams(Constants.windowSizeX-((int)((Constants.scoreStartX)*Constants.scaleX)), (int)(Constants.scoreSpacingY*(totalPlayers+1)*Constants.scaleY) );
            //scoreDisplayRL.setLayoutParams(SLP2);
            scoreDisplayRL.getLayoutParams().height=(int)((Constants.scoreSpacingY+10)*(totalPlayers+1)*Constants.scaleY);
            scoreDisplayRL.requestLayout();
            return;
        }
        scoreBoardArea = new ScrollView(context);
        scoreBoardArea.setBackgroundColor(Color.TRANSPARENT);


        RelativeLayout.LayoutParams SLP = new RelativeLayout.LayoutParams(Constants.windowSizeX-((int)((Constants.scoreStartX)*Constants.scaleX)), (int)(900*Constants.scaleY) );
        RelativeLayout.LayoutParams SLP2 = new RelativeLayout.LayoutParams(Constants.windowSizeX-((int)((Constants.scoreStartX)*Constants.scaleX)), (int)((Constants.scoreSpacingY+10)*(totalPlayers+1)*Constants.scaleY) );

        scoreBoardArea.setLayoutParams(SLP);
        scoreBoardArea.setX(Constants.scoreStartX*Constants.scaleX);
        scoreBoardArea.setY((Constants.scoreStartY+100)*Constants.scaleY);

        scoreDisplayRL = new RelativeLayout(context);
        scoreDisplayRL.setGravity(Gravity.LEFT);
        scoreDisplayRL.setLayoutParams(SLP2);
        scoreDisplayRL.setBackgroundColor(Color.TRANSPARENT);
        scoreBoardArea.addView(scoreDisplayRL);
        rl.addView(scoreBoardArea);
        scoreDisplayRL.setPadding(0,0,0,0);

        if (resetting)
        {
            scoreDisplayRL.addView(nameHeader);
            scoreDisplayRL.addView(bidHeader);
            scoreDisplayRL.addView(takenHeader);
            scoreDisplayRL.addView(scoreViewHeader);
            if (nameHeader != null)
            {
                ((ViewGroup)nameHeader.getParent()).removeView(nameHeader);
            }
            nameHeader=null;
            differentialView=null;
            showScoringHeader=false;
            for (int z = 0; z < 20; z++)
            {
                if (scoreDisplay[z] != null)
                {
                    scoreDisplay[z].name=null;
                }
            }
        }
    }

    public static ScoreDisplay[] scoreDisplay = new ScoreDisplay[20];
    private static int currentRound=-1;

    public static void display(POHResponse.POHResponseScore[] scores, AppCompatActivity context, RelativeLayout relativelayout, int round, boolean gameStarted, int dealer)
    {
        setupScoreDisplay(ScoreDisplay.getPlayerCount(), relativelayout, context, false);

        RelativeLayout.LayoutParams textLP = new RelativeLayout.LayoutParams((int)(Constants.scaleX*310),(int)(Constants.scaleY*50));
        RelativeLayout.LayoutParams numberLP = new RelativeLayout.LayoutParams((int)(Constants.scaleX*130),(int)(Constants.scaleY*50));
        RelativeLayout.LayoutParams largeLP = new RelativeLayout.LayoutParams((int)(Constants.scaleX*230),(int)(Constants.scaleY*100));
        RelativeLayout.LayoutParams otherLP = new RelativeLayout.LayoutParams((int)(Constants.scaleX*130),(int)(Constants.scaleY*100));

        if (nameHeader == null)
        {
            nameHeader = new TextView(context);
            nameHeader.setTextColor(SettingObject.getTextColorInt());
            nameHeader.setX(Constants.scaleX * (10+102));
            nameHeader.setY(Constants.scaleY * (Constants.scoreStartY));
            nameHeader.setTextSize(12);
            nameHeader.setLayoutParams(textLP);
            nameHeader.setGravity(Gravity.LEFT | Gravity.CENTER);
            nameHeader.setSingleLine(true);
            scoreDisplayRL.addView(nameHeader);


            bidHeader = new TextView(context);
            bidHeader.setTextColor(SettingObject.getTextColorInt());
            bidHeader.setX(Constants.scaleX * (10 + 360));
            bidHeader.setY(Constants.scaleY * (Constants.scoreStartY));
            bidHeader.setTextSize(12);
            bidHeader.setLayoutParams(numberLP);
            bidHeader.setGravity(Gravity.LEFT | Gravity.CENTER);
            bidHeader.setSingleLine(true);
            scoreDisplayRL.addView(bidHeader);


            takenHeader = new TextView(context);
            takenHeader.setTextColor(SettingObject.getTextColorInt());
            takenHeader.setX(Constants.scaleX * (10 + 500));
            takenHeader.setY(Constants.scaleY * (Constants.scoreStartY));
            takenHeader.setTextSize(12);
            takenHeader.setLayoutParams(numberLP);
            takenHeader.setGravity(Gravity.LEFT | Gravity.CENTER);
            takenHeader.setSingleLine(true);
            scoreDisplayRL.addView(takenHeader);


            scoreViewHeader = new TextView(context);
            scoreViewHeader.setTextColor(SettingObject.getTextColorInt());
            scoreViewHeader.setX(Constants.scaleX * (10 + 640));
            scoreViewHeader.setY(Constants.scaleY * (Constants.scoreStartY));
            scoreViewHeader.setTextSize(12);
            scoreViewHeader.setLayoutParams(numberLP);
            scoreViewHeader.setGravity(Gravity.LEFT | Gravity.CENTER);
            scoreViewHeader.setSingleLine(true);
            scoreDisplayRL.addView(scoreViewHeader);

        }
        if (differentialView == null) {
            previousButton=new Button(context);
            previousButton.setBackgroundColor(SettingObject.getButtonColorInt());
            previousButton.setTextColor(SettingObject.getButtonTextColorInt());
            previousButton.setPadding(5,5,5,5);
            previousButton.setText("Previous");
            RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams((int)(Constants.scaleY*600),((int)(Constants.scaleX*120)));
            previousButton.setLayoutParams(lp2);
            previousButton.setX(Constants.scoreStartX*Constants.scaleX);
            previousButton.setY(Constants.scoreStartY*Constants.scaleY);
            relativelayout.addView(previousButton);

            previousButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch(event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            for (int i=0; i < scoreDisplay.length;i++)
                            {
                                if (scoreDisplay[i] != null) {
                                    scoreDisplay[i].scoreDisplayRL.removeView(scoreDisplay[i].bid);
                                    scoreDisplay[i].scoreDisplayRL.removeView(scoreDisplay[i].taken);
                                    scoreDisplay[i].scoreDisplayRL.removeView(scoreDisplay[i].scoreView);
                                    scoreDisplay[i].scoreDisplayRL.addView(scoreDisplay[i].bidP);
                                    scoreDisplay[i].scoreDisplayRL.addView(scoreDisplay[i].takenP);
                                    scoreDisplay[i].scoreDisplayRL.addView(scoreDisplay[i].scoreViewP);

                                }
                            }
                            return true;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            for (int i=0; i < scoreDisplay.length;i++)
                            {
                                if (scoreDisplay[i] != null) {
                                    scoreDisplay[i].scoreDisplayRL.removeView(scoreDisplay[i].bidP);
                                    scoreDisplay[i].scoreDisplayRL.removeView(scoreDisplay[i].takenP);
                                    scoreDisplay[i].scoreDisplayRL.removeView(scoreDisplay[i].scoreViewP);
                                    scoreDisplay[i].scoreDisplayRL.addView(scoreDisplay[i].bid);
                                    scoreDisplay[i].scoreDisplayRL.addView(scoreDisplay[i].taken);
                                    scoreDisplay[i].scoreDisplayRL.addView(scoreDisplay[i].scoreView);

                                }
                            }
                            return true;
                    }
                    return false;
                }
            });
            differentialView = new TextView(context);
            differentialView.setTextColor(SettingObject.getTextColorInt());
            differentialView.setX(Constants.scaleX * (Constants.scoreStartX+105));
            differentialView.setY(Constants.scaleY * (975));
            differentialView.setTextSize(20);
            differentialView.setText("");
            differentialView.setLayoutParams(otherLP);
            differentialView.setGravity(Gravity.LEFT | Gravity.BOTTOM);
            differentialView.setSingleLine(true);
            relativelayout.addView(differentialView);

            roundView = new TextView(context);
            roundView.setTextColor(SettingObject.getTextColorInt());
            roundView.setX(Constants.scaleX * (Constants.scoreStartX + 100));
            roundView.setY(Constants.scaleY * 1000);
            roundView.setTextSize(14);
            roundView.setLayoutParams(largeLP);
            roundView.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
            relativelayout.addView(roundView);

            DealtHeader = new TextView(context);
            DealtHeader.setTextColor(SettingObject.getTextColorInt());
            DealtHeader.setX(Constants.scaleX * (Constants.scoreStartX + 100));
            DealtHeader.setY(Constants.scaleY * 900);
            DealtHeader.setTextSize(14);
            DealtHeader.setText("Dealt");
            DealtHeader.setLayoutParams(largeLP);
            DealtHeader.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
            DealtHeader.setSingleLine(true);

            totalBidHeader = new TextView(context);
            totalBidHeader.setTextColor(SettingObject.getTextColorInt());
            totalBidHeader.setX(Constants.scaleX * (Constants.scoreStartX + 370));
            totalBidHeader.setY(Constants.scaleY * 900);
            totalBidHeader.setTextSize(14);
            totalBidHeader.setText("Total Bid");
            totalBidHeader.setLayoutParams(largeLP);
            totalBidHeader.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
            totalBidHeader.setSingleLine(true);

            totalBidNumberHeader = new TextView(context);
            totalBidNumberHeader.setTextColor(SettingObject.getTextColorInt());
            totalBidNumberHeader.setX(Constants.scaleX * (Constants.scoreStartX + 370));
            totalBidNumberHeader.setY(Constants.scaleY * 1000);
            totalBidNumberHeader.setTextSize(14);
            totalBidNumberHeader.setText("0");
            totalBidNumberHeader.setLayoutParams(largeLP);
            totalBidNumberHeader.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
            totalBidNumberHeader.setSingleLine(true);

            nameHeader.setText("Player");
            bidHeader.setText("Bid");
            takenHeader.setText("Taken");
            scoreViewHeader.setText("Score");
            {
                Paint strokePaint = new Paint();
                strokePaint.setStyle(Paint.Style.FILL);
                strokePaint.setColor(SettingObject.getBidderHighlightColorInt());

                Bitmap bg = Bitmap.createBitmap((int) ((Constants.maxX - Constants.scoreStartX - 2) * Constants.scaleX), (int) (Constants.scoreSpacingY * Constants.scaleY), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bg);

                canvas.drawRect(0, 0, (Constants.maxX - Constants.scoreStartX - 2) * Constants.scaleX, (int) (Constants.scoreSpacingY * Constants.scaleY), strokePaint);

                highlighterBid = new ImageView(context);

                highlighterBid.setImageBitmap(bg);
                highlighterBid.setAdjustViewBounds(true);
                highlighterBid.setScaleType(ImageView.ScaleType.FIT_XY);
            }


                Paint strokePaint = new Paint();
                strokePaint.setStyle(Paint.Style.FILL);
                strokePaint.setColor(SettingObject.getPlayerHighlightColorInt());

                Bitmap bg = Bitmap.createBitmap((int) ((Constants.maxX - Constants.scoreStartX - 2) * Constants.scaleX), (int) (Constants.scoreSpacingY * Constants.scaleY), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bg);

                canvas.drawRect(0, 0, (Constants.maxX - Constants.scoreStartX - 2) * Constants.scaleX, (int) (Constants.scoreSpacingY * Constants.scaleY), strokePaint);

                highlighterPlay = new ImageView(context);

                highlighterPlay.setImageBitmap(bg);
                highlighterPlay.setAdjustViewBounds(true);
                highlighterPlay.setScaleType(ImageView.ScaleType.FIT_XY);


            ScoreDisplay.relativeLayout = relativelayout;

            Paint upTrianglePaint = new Paint();
            upTrianglePaint.setStyle(Paint.Style.FILL);
            upTrianglePaint.setColor(Color.RED);

            Paint downTrianglePaint = new Paint();
            downTrianglePaint.setStyle(Paint.Style.FILL);
            downTrianglePaint.setColor(Color.GREEN);

            overBid = new TextView(context);
            overBid.setTextColor(SettingObject.getTextColorInt());
            overBid.setX(Constants.scaleX * (Constants.scoreStartX+50));
            overBid.setY(Constants.scaleY * 925);
            overBid.setTextSize(12);
            overBid.setText("Over");
            overBid.setLayoutParams(otherLP);
            overBid.setGravity(Gravity.CENTER | Gravity.CENTER);
            overBid.setSingleLine(true);

            underBid = new TextView(context);
            underBid.setTextColor(SettingObject.getTextColorInt());
            underBid.setX(Constants.scaleX * (Constants.scoreStartX+50));
            underBid.setY(Constants.scaleY * 1065);
            underBid.setTextSize(12);
            underBid.setText("Under");

            underBid.setLayoutParams(otherLP);
            underBid.setGravity(Gravity.CENTER | Gravity.CENTER);
            underBid.setSingleLine(true);

            bg = Bitmap.createBitmap((int) ((Constants.maxX-Constants.scoreStartX-2)*Constants.scaleX), (int) (Constants.scoreSpacingY * Constants.scaleY), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bg);
            drawTriangle(0, (int)(Constants.scaleY *60), (int)(Constants.scaleX * 50), (int)(Constants.scaleY * 50), false, upTrianglePaint, canvas);
            triangleUp = new ImageView(context);

            triangleUp.setImageBitmap(bg);
            triangleUp.setAdjustViewBounds(true);
            triangleUp.setScaleType(ImageView.ScaleType.FIT_XY);
            triangleUp.setX(Constants.scaleX * (Constants.scoreStartX+50));
            triangleUp.setY(Constants.scaleY * 1000);

            Paint dealerCirclePaint = new Paint();
            dealerCirclePaint.setStyle(Paint.Style.FILL);
            dealerCirclePaint.setColor(SettingObject.getDealerIndicatorInt());

            bg = Bitmap.createBitmap((int)(Constants.scaleX * 30), (int)(Constants.scaleY *30), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bg);
            drawTriangle(0, (int)(Constants.scaleY *30), (int)(Constants.scaleX * 30), (int)(Constants.scaleY * 30), false, dealerCirclePaint, canvas);
            dealerCircle = new ImageView(context);

            dealerCircle.setImageBitmap(bg);
            dealerCircle.setRotation(90);
            dealerCircle.setAdjustViewBounds(true);
            dealerCircle.setScaleType(ImageView.ScaleType.FIT_XY);
            dealerCircle.setX((Constants.scaleX*(10+67)));
            int dcs = (((int)(Constants.scaleY*Constants.scoreSpacingY)) - (int)(Constants.scaleY * 30)+1)/2;
            dealerCircle.setY(dcs+Constants.scaleY*(Constants.scoreStartY+((1)*Constants.scoreSpacingY)));
            scoreDisplayRL.addView(dealerCircle);
            dealerCircle.bringToFront();

            bg = Bitmap.createBitmap((int) ((Constants.maxX-Constants.scoreStartX-2)*Constants.scaleX), (int) (Constants.scoreSpacingY * Constants.scaleY), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bg);
            drawTriangle(0, 0, (int)(Constants.scaleX * 50), (int)(Constants.scaleY * 50), true, downTrianglePaint, canvas);
            triangleDown = new ImageView(context);

            triangleDown.setImageBitmap(bg);
            triangleDown.setAdjustViewBounds(true);
            triangleDown.setScaleType(ImageView.ScaleType.FIT_XY);
            triangleDown.setX(Constants.scaleX * (Constants.scoreStartX+50));
            triangleDown.setY(Constants.scaleY * 1020);

        }
        int totalBid=0;

        for (int i=0; i < scores.length; i++)
        {

            System.out.println(scores);
            if (scoreDisplay[scores[i].playerNumber-1] == null)
            {
                scoreDisplay[scores[i].playerNumber-1]=new ScoreDisplay();
            }
            if (currentRound != round)
            {
                if (scoreDisplay[scores[i].playerNumber-1].bidP != null) {
                    scoreDisplay[scores[i].playerNumber - 1].bidP.setText(scoreDisplay[scores[i].playerNumber - 1].bid.getText());
                    scoreDisplay[scores[i].playerNumber - 1].scoreViewP.setText(scoreDisplay[scores[i].playerNumber - 1].scoreView.getText());
                    scoreDisplay[scores[i].playerNumber - 1].takenP.setText(scoreDisplay[scores[i].playerNumber - 1].taken.getText());
                }
            }

            scoreDisplay[scores[i].playerNumber-1].display(scores[i], context, relativelayout);
            if (scores[i].bid >= 0)
            {
                totalBid+=scores[i].bid;
            }
        }
        currentRound=round;
        roundView.setText(String.valueOf(round));
        if (showScoringHeader==false) {
            relativelayout.addView(DealtHeader);
            relativelayout.addView(totalBidHeader);
            relativelayout.addView(totalBidNumberHeader);
            showScoringHeader = true;
        }
        relativelayout.removeView(triangleUp);
        relativelayout.removeView(triangleDown);
        relativelayout.removeView(underBid);
        relativelayout.removeView(overBid);

        if (gameStarted) {
            totalBidNumberHeader.setText(String.valueOf(totalBid));
            if (totalBid < round) {
                relativelayout.addView(triangleDown);
                relativelayout.addView(underBid);

            } else if (totalBid > round) {
                relativelayout.addView(triangleUp);
                relativelayout.addView(overBid);

            }
            differentialView.setText(String.valueOf(Math.abs(round - totalBid)));
        }
        if (dealer > 0)
        {
            ScoreDisplay.scoreDisplayRL.removeView(dealerCircle);
            ScoreDisplay.scoreDisplayRL.addView(dealerCircle);

            int dcs = (((int)(Constants.scaleY*Constants.scoreSpacingY)) - (int)(Constants.scaleY * 30)+1)/2;
            dealerCircle.setY(dcs+Constants.scaleY*(Constants.scoreStartY+((dealer)*Constants.scoreSpacingY)));
            dealerCircle.bringToFront();
        }
    }

    private Timer timer;
    private Canvas emojiCanvas;
    private Bitmap emojiBitmap;
    public static void cleanAllEmojis()
    {
        for (int i=0; i < scoreDisplay.length;i++)
        {
            if (scoreDisplay[i] != null) {
                scoreDisplay[i].cleanEmoji();
            }
        }
    }
    public void cleanEmoji()
    {
        synchronized (emojiLock) {
            if (emojiBitmap!= null) {
                scoreDisplayRL.removeView(emoji);
                emojiBitmap.recycle();
                emojiBitmap = null;
            }
        }
    }
    public class ClearCanvas extends TimerTask {
        public ScoreDisplay display;
        public void run() {
            System.out.println("Time's up! "+emojiBitmap+ " - "+emojiCanvas);
            Message message = new Message();
            // Set message type
            message.what = _aca.MESSAGE_CLEAN_EMOJI;
            message.obj = display;
            // Send message to main thread Handler.
            State.updateUIHandler.sendMessage(message);

            timer.cancel(); //Terminate the timer thread
        }
    }
    public ClearCanvas getClearCanvas()
    {
        ClearCanvas CC = new ClearCanvas();
        CC.display=this;
        return CC;
    }

    public void display(POHResponse.POHResponseScore score, AppCompatActivity context, RelativeLayout relativelayout)
    {
        setupScoreDisplay(ScoreDisplay.getPlayerCount(), relativelayout, context, false);

        if (name == null)
        {
            RelativeLayout.LayoutParams textLP = new RelativeLayout.LayoutParams((int)(Constants.scaleX*140),(int)(Constants.scaleY*Constants.scoreSpacingY));
            RelativeLayout.LayoutParams numberLP = new RelativeLayout.LayoutParams((int)(Constants.scaleX*120),(int)(Constants.scaleY*Constants.scoreSpacingY));
            if (score.playerNumber % 2 == 0)
            {
                Paint strokePaint = new Paint();
                strokePaint.setStyle(Paint.Style.FILL);
                strokePaint.setColor(Color.parseColor("#60EEEEEE"));

                Bitmap bg = Bitmap.createBitmap((int) ((Constants.maxX-Constants.scoreStartX-2)*Constants.scaleX), (int) (Constants.scoreSpacingY * Constants.scaleY), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bg);

                canvas.drawRect(0, 0, (Constants.maxX-Constants.scoreStartX-2)*Constants.scaleX, (int) (Constants.scoreSpacingY * Constants.scaleY), strokePaint);

                lineSeperator = new ImageView(context);

                lineSeperator.setImageBitmap(bg);
                lineSeperator.setAdjustViewBounds(true);
                lineSeperator.setScaleType(ImageView.ScaleType.FIT_XY);

                lineSeperator.setX((Constants.scaleX*(10))+6);
                lineSeperator.setY(Constants.scaleY*(Constants.scoreStartY+((score.playerNumber)*Constants.scoreSpacingY)));

                scoreDisplayRL.addView(lineSeperator);
            }

            name = new TextView(context);
            name.setTextColor(SettingObject.getTextColorInt());
            name.setX(Constants.scaleX*(10+102));
            name.setY(Constants.scaleY*(Constants.scoreStartY+((score.playerNumber)*Constants.scoreSpacingY)));
            name.setTextSize(12);
            name.setLayoutParams(textLP);
            name.setGravity(Gravity.LEFT | Gravity.CENTER);
            name.setPadding(0,0,0,0);
            name.setSingleLine(true);
            scoreDisplayRL.addView(name);


            bid = new TextView(context);
            bid.setTextColor(SettingObject.getTextColorInt());
            bid.setX(Constants.scaleX*(10+360));
            bid.setY(Constants.scaleY*(Constants.scoreStartY+((score.playerNumber)*Constants.scoreSpacingY)));
            bid.setTextSize(12);
            bid.setLayoutParams(numberLP);
            bid.setGravity(Gravity.LEFT | Gravity.CENTER);
            scoreDisplayRL.addView(bid);

            bidP = new TextView(context);
            bidP.setTextColor(SettingObject.getTextColorInt());
            bidP.setX(Constants.scaleX*(10+360));
            bidP.setY(Constants.scaleY*(Constants.scoreStartY+((score.playerNumber)*Constants.scoreSpacingY)));
            bidP.setTextSize(12);
            bidP.setLayoutParams(numberLP);
            bidP.setGravity(Gravity.LEFT | Gravity.CENTER);



            taken = new TextView(context);
            taken.setTextColor(SettingObject.getTextColorInt());
            taken.setX(Constants.scaleX*(10+500));
            taken.setY(Constants.scaleY*(Constants.scoreStartY+((score.playerNumber)*Constants.scoreSpacingY)));
            taken.setTextSize(12);
            taken.setLayoutParams(numberLP);
            taken.setGravity(Gravity.LEFT | Gravity.CENTER);
            scoreDisplayRL.addView(taken);

            takenP = new TextView(context);
            takenP.setTextColor(SettingObject.getTextColorInt());
            takenP.setX(Constants.scaleX*(10+500));
            takenP.setY(Constants.scaleY*(Constants.scoreStartY+((score.playerNumber)*Constants.scoreSpacingY)));
            takenP.setTextSize(12);
            takenP.setLayoutParams(numberLP);
            takenP.setGravity(Gravity.LEFT | Gravity.CENTER);


            scoreView = new TextView(context);
            scoreView.setTextColor(SettingObject.getTextColorInt());
            scoreView.setX(Constants.scaleX*(10+640));
            scoreView.setY(Constants.scaleY*(Constants.scoreStartY+((score.playerNumber)*Constants.scoreSpacingY)));
            scoreView.setTextSize(12);
            scoreView.setLayoutParams(numberLP);
            scoreView.setGravity(Gravity.LEFT | Gravity.CENTER);
            scoreDisplayRL.addView(scoreView);

            scoreViewP = new TextView(context);
            scoreViewP.setTextColor(SettingObject.getTextColorInt());
            scoreViewP.setX(Constants.scaleX*(10+640));
            scoreViewP.setY(Constants.scaleY*(Constants.scoreStartY+((score.playerNumber)*Constants.scoreSpacingY)));
            scoreViewP.setTextSize(12);
            scoreViewP.setLayoutParams(numberLP);
            scoreViewP.setGravity(Gravity.LEFT | Gravity.CENTER);

        }
        name.setText(score.playerName);
        if (score.bid < 0)
        {
            bid.setText("-");
        }
        else {
            bid.setText(String.valueOf(score.bid));
        }
        taken.setText(String.valueOf(score.taken));
        scoreView.setText(String.valueOf(score.score));

        String text="Player: "+score.playerName+" Bid: "+score.bid+" Taken: "+score.taken+" Score: "+score.score;
        System.out.println(text);
    }

    private RelativeLayout relativelayout;
    public static void showEmoji(int player, int resource, AppCompatActivity context, RelativeLayout relativelayout)
    {
        if (scoreDisplay[player-1].timer != null)
        {
            scoreDisplay[player-1].timer.cancel();
        }

        synchronized (scoreDisplay[player-1].emojiLock) {
            if (scoreDisplay[player-1].emojiBitmap != null)
            {
                scoreDisplay[player-1].cleanEmoji();
            }
            scoreDisplay[player - 1].relativelayout = relativelayout;
            scoreDisplay[player - 1].emojiBitmap = Bitmap.createBitmap((int) (50 * Constants.scaleX), (int) (50 * Constants.scaleY), Bitmap.Config.ARGB_8888);
            scoreDisplay[player - 1].emojiCanvas = new Canvas(scoreDisplay[player - 1].emojiBitmap);

            //canvas.drawRect(0, 0, (Constants.maxX - Constants.scoreStartX - 2) * Constants.scaleX, (int) (Constants.scoreSpacingY * Constants.scaleY), strokePaint);

            scoreDisplay[player - 1].emoji = new ImageView(context);

            scoreDisplay[player - 1].emoji.setImageBitmap(scoreDisplay[player - 1].emojiBitmap);
            scoreDisplay[player - 1].emoji.setAdjustViewBounds(true);
            scoreDisplay[player - 1].emoji.setScaleType(ImageView.ScaleType.FIT_XY);
            scoreDisplay[player - 1].emoji.setX(Constants.scaleX * (10) + 7);
            scoreDisplay[player - 1].emoji.setY(Constants.scaleY * (((Constants.scoreSpacingY - 50) / 2) + Constants.scoreStartY + ((player) * Constants.scoreSpacingY)));
            scoreDisplayRL.addView(scoreDisplay[player - 1].emoji);
        }
        Drawable d = ContextCompat.getDrawable(context, resource);
        d.setBounds(0, 0, (int) (50*Constants.scaleX), (int) (50*Constants.scaleY));
        d.draw(scoreDisplay[player-1].emojiCanvas);
        scoreDisplay[player-1].timer = new Timer();
        scoreDisplay[player-1].timer.schedule(scoreDisplay[player-1].getClearCanvas(), 10*1000);

    }
    public static int getPlayerCount()
    {
        int count=0;
        for (int i=0;i < scoreDisplay.length;i++)
        {
            if (scoreDisplay[i] != null) {
               count++;
            }
        }
        return count;
    }
    public static void clearAll()
    {
        for (int i=0;i < scoreDisplay.length;i++)
        {
            if (scoreDisplay[i] != null) {
                scoreDisplay[i].clear();
                scoreDisplay[i]=null;
            }
        }

    }

    public void clear()
    {
        ScoreDisplay.relativeLayout.removeView(highlighterBid);
        ScoreDisplay.relativeLayout.removeView(highlighterPlay);
        ScoreDisplay.relativeLayout.removeView(dealerCircle);
        ScoreDisplay.relativeLayout.removeView( name );
        ScoreDisplay.relativeLayout.removeView( bid );
        ScoreDisplay.relativeLayout.removeView( taken );
        ScoreDisplay.relativeLayout.removeView( scoreView );

    }
    public static int BID=1;
    public static int PLAY=2;
    public static void markAsSelected(int player, int type)
    {
        /*for (int i=0; i < 20;i++)
        {
            if (scoreDisplay[i] != null)
            {
                //scoreDisplay[i].name.setBackgroundColor(Color.WHITE);

            }
        }*/
        ScoreDisplay.scoreDisplayRL.removeView(highlighterBid);
        ScoreDisplay.scoreDisplayRL.removeView(highlighterPlay);

        if (type == BID) {
            if (player > 0) {
                //scoreDisplay[player - 1].name.setBackgroundColor(Color.CYAN);
                highlighterBid.setX((Constants.scaleX * (10)) + 6);
                highlighterBid.setY(Constants.scaleY * (Constants.scoreStartY + ((player) * Constants.scoreSpacingY)));
                ScoreDisplay.scoreDisplayRL.addView(highlighterBid);
                scoreDisplay[player - 1].name.bringToFront();
                scoreDisplay[player - 1].bid.bringToFront();
                scoreDisplay[player - 1].taken.bringToFront();
                scoreDisplay[player - 1].scoreView.bringToFront();
                synchronized (scoreDisplay[player-1].emojiLock) {
                    if (scoreDisplay[player - 1].emojiBitmap != null) {
                        scoreDisplay[player - 1].emoji.bringToFront();
                    }
                }
                if (type == 1) {

                }
            }
        }
        else
        {
            if (player > 0) {
                //scoreDisplay[player - 1].name.setBackgroundColor(Color.CYAN);
                highlighterPlay.setX((Constants.scaleX * (10)) + 6);
                highlighterPlay.setY(Constants.scaleY * (Constants.scoreStartY + ((player) * Constants.scoreSpacingY)));
                ScoreDisplay.scoreDisplayRL.addView(highlighterPlay);
                scoreDisplay[player - 1].name.bringToFront();
                scoreDisplay[player - 1].bid.bringToFront();
                scoreDisplay[player - 1].taken.bringToFront();
                scoreDisplay[player - 1].scoreView.bringToFront();
                if (type == 1) {

                }
            }
        }
    }

    /*public static void markAsWon(int player)
    {
        for (int i=0; i < 20;i++)
        {
            if (scoreDisplay[i] != null)
            {
                scoreDisplay[i].taken.setBackgroundColor(Color.WHITE);
            }
        }
        if (player > 0) {
            scoreDisplay[player - 1].taken.setBackgroundColor(Color.parseColor("#d6f5d6"));
        }
    }*/

    private static void drawTriangle(int x, int y, int width, int height, boolean inverted, Paint paint, Canvas canvas){

        Point p1 = new Point(x,y);
        int pointX = x + width/2;
        int pointY = inverted?  y + height : y - height;

        Point p2 = new Point(pointX,pointY);
        Point p3 = new Point(x+width,y);


        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(p1.x,p1.y);
        path.lineTo(p2.x,p2.y);
        path.lineTo(p3.x,p3.y);
        path.close();

        canvas.drawPath(path, paint);
    }

    public static void setColors()
    {
        if (highlighterBid != null)
        {
            Paint strokePaint = new Paint();
            strokePaint.setStyle(Paint.Style.FILL);
            strokePaint.setColor(SettingObject.getBidderHighlightColorInt());

            Bitmap bg = Bitmap.createBitmap((int) ((Constants.maxX - Constants.scoreStartX - 2) * Constants.scaleX), (int) (Constants.scoreSpacingY * Constants.scaleY), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bg);

            canvas.drawRect(0, 0, (Constants.maxX - Constants.scoreStartX - 2) * Constants.scaleX, (int) (Constants.scoreSpacingY * Constants.scaleY), strokePaint);

            highlighterBid.setImageBitmap(bg);
        }


        if (highlighterPlay != null) {
            Paint strokePaint = new Paint();
            strokePaint.setStyle(Paint.Style.FILL);
            strokePaint.setColor(SettingObject.getPlayerHighlightColorInt());

            Bitmap bg = Bitmap.createBitmap((int) ((Constants.maxX - Constants.scoreStartX - 2) * Constants.scaleX), (int) (Constants.scoreSpacingY * Constants.scaleY), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bg);

            canvas.drawRect(0, 0, (Constants.maxX - Constants.scoreStartX - 2) * Constants.scaleX, (int) (Constants.scoreSpacingY * Constants.scaleY), strokePaint);

            highlighterPlay.setImageBitmap(bg);
        }
        if (previousButton != null)
        {
            previousButton.setBackgroundColor(SettingObject.getButtonColorInt());
            previousButton.setTextColor(SettingObject.getButtonTextColorInt());
        }
        if (dealerCircle != null)
        {
            Paint dealerCirclePaint = new Paint();
            dealerCirclePaint.setStyle(Paint.Style.FILL);
            dealerCirclePaint.setColor(SettingObject.getDealerIndicatorInt());

            Bitmap bg = Bitmap.createBitmap((int) (Constants.scaleX * 30), (int) (Constants.scaleY * 30), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bg);
            drawTriangle(0, (int)(Constants.scaleY *30), (int)(Constants.scaleX * 30), (int)(Constants.scaleY * 30), false, dealerCirclePaint, canvas);
            dealerCircle.setImageBitmap(bg);
        }
    }

}

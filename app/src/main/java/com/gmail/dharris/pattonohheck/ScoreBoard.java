package com.gmail.dharris.pattonohheck;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ScoreBoard extends AppCompatActivity {
    private static ScoreBoardManagementSystem.SBMSGame latestScoreBoard;
    private static ScoreBoard currentSB = null;
    private Button scoreBoard;

    private HorizontalScrollView scoreBoardScrollAreaH;
    private ScrollView scoreBoardScrollArea;
    private RelativeLayout scoreBoardScrollAreaRL;

    private java.util.ArrayList<TextView> headerViews = new java.util.ArrayList<TextView>();

    public static void setLatestScoreBoard(ScoreBoardManagementSystem.SBMSGame latestScoreBoard) {
            ScoreBoard.latestScoreBoard = latestScoreBoard;
            if (currentSB != null) {
                currentSB.updateScoreBoard();
            }

    }


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
        getSupportActionBar(). hide();
        setContentView(R.layout.activity_scoreboard);

        RelativeLayout relativelayout = (RelativeLayout) findViewById(R.id.RL_ScoreBoard2);
        scoreBoard = new Button(this);
        scoreBoard.setText("Return to Game");
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams((int) (600 * Constants.scaleX), (int) (90 * Constants.scaleY));

        scoreBoard.setLayoutParams(lp2);
        scoreBoard.setPadding(5, 5, 5, 5);

        scoreBoard.setX(10 * Constants.scaleX);
        scoreBoard.setY(10 * Constants.scaleY);
        relativelayout.addView(scoreBoard);
        scoreBoard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                finish();
            }
        });
        currentSB=this;
        State.settingsLock=false;

        if (latestScoreBoard != null)
        {
            updateScoreBoard();
        }
    }
    private  java.util.ArrayList<ScoreBoardLine> sbl;
    private boolean initialized=false;
    public void updateScoreBoard()
    {
        if (latestScoreBoard != null && !initialized)
        {
            scoreBoardScrollArea = new ScrollView(this);
            scoreBoardScrollArea.setBackgroundColor(Color.TRANSPARENT);
            RelativeLayout.LayoutParams SLP = new RelativeLayout.LayoutParams((int)(1400*Constants.scaleX), (int)(600*Constants.scaleY) );
            scoreBoardScrollArea.setLayoutParams(SLP);
            scoreBoardScrollArea.setX(110*Constants.scaleX);
            scoreBoardScrollArea.setY(110*Constants.scaleY);
            scoreBoardScrollArea.setPadding(0,0,0,0);


            scoreBoardScrollAreaH = new HorizontalScrollView(this);
            scoreBoardScrollAreaH.setBackgroundColor(Color.TRANSPARENT);
            RelativeLayout.LayoutParams SLP3 = new RelativeLayout.LayoutParams((int)(1400*Constants.scaleX), (int)((latestScoreBoard.getPlayers().size()+1)*60*Constants.scaleY) );
            if (latestScoreBoard.getPlayers().size() < 10)
            {
                SLP3 = new RelativeLayout.LayoutParams((int)(1400*Constants.scaleX), (int)(600*Constants.scaleY) );
            }
            scoreBoardScrollAreaH.setLayoutParams(SLP3);
            scoreBoardScrollAreaH.setX(0);
            scoreBoardScrollAreaH.setY(0);
            scoreBoardScrollAreaH.setPadding(0,0,0,0);
            scoreBoardScrollArea.addView(scoreBoardScrollAreaH);
            RelativeLayout.LayoutParams SLP2 = new RelativeLayout.LayoutParams((int)((200+(latestScoreBoard.getTotalRounds()*2*120))*Constants.scaleX), (int)((latestScoreBoard.getPlayers().size()+1)*60*Constants.scaleY) );
            System.out.println("Width: "+(int)((latestScoreBoard.getRounds().size()*120)*Constants.scaleX));
            scoreBoardScrollAreaRL = new RelativeLayout(this);
            scoreBoardScrollAreaRL.setGravity(Gravity.LEFT);
            scoreBoardScrollAreaRL.setLayoutParams(SLP2);
            scoreBoardScrollAreaH.addView(scoreBoardScrollAreaRL);
            scoreBoardScrollAreaRL.setPadding(0,0,0,0);
            scoreBoardScrollAreaRL.scrollTo(0,0);
            RelativeLayout relativelayout = (RelativeLayout) findViewById(R.id.RL_ScoreBoard2);
            relativelayout.addView(scoreBoardScrollArea);

            RelativeLayout.LayoutParams textLP = new RelativeLayout.LayoutParams((int)(Constants.scaleX*310),(int)(Constants.scaleY*50));

            int posX=0;
            for (int zzz=latestScoreBoard.getTotalRounds(); zzz >= 1; zzz--)
            {
                TextView nv = new TextView(this);
                nv.setTextColor(SettingObject.getTextColorInt());
                nv.setX(Constants.scaleX *  (10 + 190+50+(posX*120)));
                posX++;
                nv.setY(0);
                nv.setTextSize(12);
                nv.setLayoutParams(textLP);
                nv.setGravity(Gravity.LEFT | Gravity.CENTER);
                nv.setPadding(0, 0, 0, 0);
                nv.setSingleLine(true);
                nv.setText(String.valueOf(zzz));
                scoreBoardScrollAreaRL.addView(nv);
                headerViews.add(nv);

            }
            for (int zzz=1; zzz <= latestScoreBoard.getTotalRounds(); zzz++)
            {
                TextView nv = new TextView(this);
                nv.setTextColor(SettingObject.getTextColorInt());
                nv.setX(Constants.scaleX * (10 + 190+50+(posX*120)));
                posX++;
                nv.setY(0);
                nv.setTextSize(12);
                nv.setLayoutParams(textLP);
                nv.setGravity(Gravity.LEFT | Gravity.CENTER);
                nv.setPadding(0, 0, 0, 0);
                nv.setSingleLine(true);
                nv.setText(String.valueOf(zzz));
                scoreBoardScrollAreaRL.addView(nv);
                headerViews.add(nv);

            }

            //if (sbl == null) {
                sbl = new ArrayList<ScoreBoardLine>();
                for (int i = 0; i < latestScoreBoard.getPlayers().size(); i++) {
                    ScoreBoardManagementSystem.SBMSPlayer player = latestScoreBoard.getPlayers().get(i);
                    ScoreBoardLine aScoreBoardLine = new ScoreBoardLine(this, i, player.getPlayerName(), latestScoreBoard.getTotalRounds());
                    sbl.add(aScoreBoardLine);
                }
                initialized=true;
            //}
        }
        if (latestScoreBoard != null)
        {
            for (int i = 0; i < latestScoreBoard.getPlayers().size(); i++) {
                String playerName=latestScoreBoard.getPlayers().get(i).getPlayerName();
                for (int z=0; z < latestScoreBoard.getRounds().size();z++)
                {
                    for (int x=0; x<latestScoreBoard.getRounds().get(z).getPlayerRound().size(); x++)
                    {
                        ScoreBoardManagementSystem.SBMSPlayerRound CPR = latestScoreBoard.getRounds().get(z).getPlayerRound().get(x);
                        if (CPR.getPlayerId().equals(playerName))
                        {
                            sbl.get(i).setScore(CPR.getBid(), CPR.getTaken(), CPR.getScore(), z);
                        }
                        }
                    }
                }
            }
    }

    public class ScoreBoardLine
    {
        public String playerId;
        public TextView nameView;
        public java.util.ArrayList<ScoreBlock> scoreBlocks = new java.util.ArrayList<ScoreBlock>();

        public ScoreBoardLine(Context context, int playerPos, String playerName, int totalRounds)
        {
            RelativeLayout relativelayout = (RelativeLayout) findViewById(R.id.RL_ScoreBoard2);
            RelativeLayout.LayoutParams textLP = new RelativeLayout.LayoutParams((int)(Constants.scaleX*310),(int)(Constants.scaleY*50));

            if (nameView == null)
            {
                System.out.println("Creating Score Board: "+playerName);
                nameView = new TextView(context);
                nameView.setTextColor(SettingObject.getTextColorInt());
                nameView.setX(Constants.scaleX * (2));
                nameView.setY((60 + playerPos * 60)*Constants.scaleY);
                nameView.setTextSize(12);
                nameView.setLayoutParams(textLP);
                nameView.setGravity(Gravity.LEFT | Gravity.CENTER);
                nameView.setPadding(0, 0, 0, 0);
                nameView.setSingleLine(true);
                nameView.setText(playerName);
                scoreBoardScrollAreaRL.addView(nameView);
                for (int i=0; i < totalRounds*2;i++) {
                    scoreBlocks.add(new ScoreBlock(context, playerPos, i));
                }


            }
        }

        public void setScore(int bid, int taken, int score, int round)
        {
            scoreBlocks.get(round).bidView.setText(String.valueOf(bid));
            scoreBlocks.get(round).takenView.setText(String.valueOf(taken));
            scoreBlocks.get(round).scoreView.setText(String.valueOf(score));
        }

    }

    public class ScoreBlock
    {
        public TextView bidView;
        public TextView takenView;
        public TextView scoreView;

        public ScoreBlock(Context context, int playerPos, int round)
        {
            RelativeLayout relativelayout = (RelativeLayout) findViewById(R.id.RL_ScoreBoard2);
            RelativeLayout.LayoutParams textLP = new RelativeLayout.LayoutParams((int)(Constants.scaleX*50),(int)(Constants.scaleY*50));
            bidView = new TextView(context);
            bidView.setTextColor(SettingObject.getTextColorInt());
            bidView.setX(Constants.scaleX * (10 + 190+(round*120)));
            bidView.setY((60 + playerPos * 60)*Constants.scaleY);
            bidView.setTextSize(8);
            bidView.setLayoutParams(textLP);
            bidView.setGravity(Gravity.LEFT | Gravity.TOP);
            bidView.setPadding(0, 0, 0, 0);
            bidView.setSingleLine(true);
            bidView.setText("-");
            scoreBoardScrollAreaRL.addView(bidView);

            Paint strokePaint = new Paint();
            strokePaint.setStyle(Paint.Style.STROKE);
            strokePaint.setColor(Color.BLACK);
            strokePaint.setStrokeWidth(2);

            Bitmap bg = Bitmap.createBitmap((int)(120*Constants.scaleX), (int)(60*Constants.scaleY), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas();
            canvas.setBitmap(bg);
            canvas.drawLine(Constants.getX(30),Constants.getY(0),Constants.getX(0),Constants.getY(60), strokePaint);
            canvas.drawLine(Constants.getX(0),Constants.getY(0),Constants.getX(0),Constants.getY(60), strokePaint);
            canvas.drawLine(Constants.getX(0),Constants.getY(0),Constants.getX(120),Constants.getY(0), strokePaint);
            canvas.drawLine(Constants.getX(120),Constants.getY(60),Constants.getX(0),Constants.getY(60), strokePaint);
            canvas.drawLine(Constants.getX(120),Constants.getY(60),Constants.getX(120),Constants.getY(0), strokePaint);
            ImageView iV = new ImageView(context);
            RelativeLayout.LayoutParams ivlp = new RelativeLayout.LayoutParams((int)(120*Constants.scaleX), (int)(60*Constants.scaleY));
            iV.setLayoutParams(ivlp);
            iV.setAdjustViewBounds(true);
            iV.setScaleType(ImageView.ScaleType.FIT_XY);
            iV.setImageBitmap(bg);

            iV.setX(Constants.scaleX * (10 + 190+(round*120)));
            iV.setY((60 + playerPos * 60)*Constants.scaleY);

            scoreBoardScrollAreaRL.addView(iV);
            takenView = new TextView(context);
            takenView.setTextColor(SettingObject.getTextColorInt());
            takenView.setX(Constants.scaleX * (10 + 210+(round*120)));
            takenView.setY((70 + playerPos * 60)*Constants.scaleY);
            takenView.setTextSize(8);
            takenView.setLayoutParams(textLP);
            takenView.setGravity(Gravity.LEFT | Gravity.CENTER);
            takenView.setPadding(0, 0, 0, 0);
            takenView.setSingleLine(true);
            takenView.setText("-");
            scoreBoardScrollAreaRL.addView(takenView);

            RelativeLayout.LayoutParams textLP2 = new RelativeLayout.LayoutParams((int)(Constants.scaleX*80),(int)(Constants.scaleY*50));
            scoreView = new TextView(context);
            scoreView.setTextColor(SettingObject.getTextColorInt());
            scoreView.setX(Constants.scaleX * (10 + 250+(round*120)));
            scoreView.setY((70 + playerPos * 60)*Constants.scaleY);
            scoreView.setTextSize(10);
            scoreView.setLayoutParams(textLP2);
            scoreView.setGravity(Gravity.LEFT | Gravity.CENTER);
            scoreView.setPadding(0, 0, 0, 0);
            scoreView.setSingleLine(true);
            scoreView.setText("");
            scoreBoardScrollAreaRL.addView(scoreView);

        }

    }
}

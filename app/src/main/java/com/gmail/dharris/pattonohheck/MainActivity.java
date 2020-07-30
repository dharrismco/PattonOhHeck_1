package com.gmail.dharris.pattonohheck;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.Iterator;
import java.util.UUID;

import com.gmail.dharris.pattonohheck.client.POHResponse;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Display WM;
    private AppCompatActivity context = this;
    //image button
    private ImageButton buttonPlay;


    Button smileButton;
    Button sadButton;
    Button tongueButton;
    Button wowButton;
    Button coolButton;
    Button reliefButton;
    Button nervousButton;
    Button ohHellButton;
    Button cryButton;


    Button joinButton;
     //Button dealButton ;
    public static Button startButton ;

    public static Button  playGame;
    public static Button  wonButton;
    Button settingButton;
    Button scoreBoardButton;
    Button bidButton;

    Button numberButtons[];
    int numberButtonSelected=0;
    int numberButtonDisabled = -1;
    TextView suitLed;
    ImageView suitLedImage;

    static boolean turn = false;
    static int cardPlayedCount=0;
    private SettingObject settingObject=new SettingObject();
    boolean gameOver=false;
    public static DisplayHand displayHand=null;


    public Button makeEmojiButton(int resource, int position, final String type)
    {
        RelativeLayout.LayoutParams lpEmoji = new RelativeLayout.LayoutParams((int)(Constants.scaleY*80),((int)(Constants.scaleX*80)));

        Button rtn = new Button(this);
        rtn.setPadding(0,0,0,0);
        rtn.setX(Constants.scaleX*(Constants.scoreStartX-300 - (position * 100)));
        rtn.setY(Constants.scaleY*(1200+15));
        Drawable d = ContextCompat.getDrawable(context, resource);
        rtn.setLayoutParams(lpEmoji);
        rtn.setBackground(d);
        rtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (State.nh != null && State.nh.isOpen())
                {
                    try {
                        (new Thread() {
                            public void run() {
                                try {
                                    com.gmail.dharris.pattonohheck.State.nh.sendEmoji(type);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return rtn;
    }
    public void setBackgroundColor()
    {
        RelativeLayout relativelayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        relativelayout.setBackgroundColor(settingObject.getBackgroundColorInt());
        joinButton.setBackgroundColor(SettingObject.getButtonColorInt());
        playGame.setBackgroundColor(SettingObject.getButtonColorInt());
        bidButton.setBackgroundColor(SettingObject.getButtonColorInt());
        startButton.setBackgroundColor(SettingObject.getButtonColorInt());
        wonButton.setBackgroundColor(SettingObject.getButtonColorInt());
        //scoreBoard.setBackgroundColor(SettingObject.getButtonColorInt());
        wonButton.setBackgroundColor(SettingObject.getButtonColorInt());
        for (int i=0; i <=10; i++) {
            numberButtons[i].setBackgroundColor(SettingObject.getButtonColorInt());
            numberButtons[i].setTextColor(SettingObject.getButtonTextColorInt());
        }
        if (numberButtonDisabled >= 0)
        {
            numberButtons[numberButtonDisabled].setBackgroundColor(darkenColor(SettingObject.getButtonColorInt()));
        }

        wonButton.setTextColor(SettingObject.getButtonTextColorInt());
        //scoreBoard.setTextColor(SettingObject.getButtonTextColorInt());
        startButton.setTextColor(SettingObject.getButtonTextColorInt());
        bidButton.setTextColor(SettingObject.getButtonTextColorInt());
        playGame.setTextColor(SettingObject.getButtonTextColorInt());
        joinButton.setTextColor(SettingObject.getButtonTextColorInt());
        ScoreDisplay.setColors();

        Drawable d = settingButton.getBackground();
        DrawableCompat.setTint(d, SettingObject.getButtonColorInt());
        d = scoreBoardButton.getBackground();
        DrawableCompat.setTint(d, SettingObject.getButtonColorInt());

    }


    public boolean created = false;
    public static boolean playedTrick=false;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Start State: "+State.state);
         joinButton = new Button(this);
        playGame = new Button(this);
        bidButton = new Button(this);
         startButton = new Button(this);
        wonButton =  new Button(this);;
        settingButton =  new Button(this);
        scoreBoardButton =  new Button(this);
        joinButton.setPadding(5,5,5,5);
        playGame.setPadding(5,5,5,5);
        bidButton.setPadding(5,5,5,5);
        startButton.setPadding(5,5,5,5);
        wonButton.setPadding(5,5,5,5);
        super.onCreate(savedInstanceState);
        getSupportActionBar(). hide();

        ScoreDisplay._aca=this;
        ypos= (int) (Constants.dealtCardsStartingY*Constants.scaleY);
        xpos= (int) (Constants.dealtCardsStartingX*Constants.scaleX);
        setContentView(R.layout.activity_main);
        final RelativeLayout relativelayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        State.RL = relativelayout;
        System.out.println("Start RL: "+relativelayout);
        relativelayout.removeAllViews();
        Point windowSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(windowSize);
        System.out.print(windowSize.x);
        System.out.print(windowSize.y);
        Constants.windowSizeX=windowSize.x;
        Constants.windowSizeY=windowSize.y;

        Constants.scaleX = ((float)windowSize.x)/((float)2392);
        Constants.scaleY = ((float)windowSize.y)/((float)1440);
        WM = getWindowManager().getDefaultDisplay();
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SettingObject.setSettings(settings);

        settingObject.setPlayerName(settings.getString("playerName", null));
        settingObject.setHighToLow(settings.getBoolean("highToLow", false));
        settingObject.setTrumpRight(settings.getBoolean("trumpRight", false));

        Score_Board._aca=this;

        if (SettingObject.getPlayerId() == null)
        {
            SettingObject.setPlayerId(UUID.randomUUID().toString());
        }
        if (settingObject.getPlayerName() == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Name?");

// Set up the input
            final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    settingObject.setPlayerName(input.getText().toString());
                    System.out.println("Setting name to: "+settingObject.getPlayerName());

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("playerName", settingObject.getPlayerName());
                    editor.commit();
                }
            });


            builder.show();
        }

        //setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);

        playGame.setText("Your Turn!");
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams((int)(Constants.scaleY*600),((int)(Constants.scaleX*120)));
        playGame.setLayoutParams(lp2);
        playGame.setX(Constants.scaleX*200);
        playGame.setY(Constants.scaleY*500);

        wonButton.setText("");
        wonButton.setLayoutParams(lp2);
        wonButton.setX(Constants.scaleX*200);
        wonButton.setY(Constants.scaleY*500);

        bidButton.setText("Bid");
        bidButton.setLayoutParams(lp2);
        bidButton.setX(Constants.scaleX*200);
        bidButton.setY(Constants.scaleY*500);
        bidButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if (numberButtonSelected >= 0) {
                    try {
                        sendBid();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    relativelayout.removeView(bidButton);
                    hideNumberButtons();
                }
            }
        });

        startButton.setText("Start");
        startButton.setLayoutParams(lp2);
        startButton.setX(Constants.scaleX*200);
        startButton.setY(Constants.scaleY*500);

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if (numberButtonSelected > -1) {

                    startButton.setEnabled(false);
                    try {
                        start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startButton.setEnabled(true);
                }
            }
        });

        RelativeLayout.LayoutParams lp20 = new RelativeLayout.LayoutParams((int)(Constants.scaleY*80),((int)(Constants.scaleX*80)));

        Drawable d = ContextCompat.getDrawable(context, R.drawable.settings);
        DrawableCompat.setTint(d, SettingObject.getButtonColorInt());

        settingButton.setBackground(d);
        settingButton.setLayoutParams(lp20);
        settingButton.setPadding(5,5,5,5);

        settingButton.setX(Constants.scaleX*(Constants.scoreStartX-200));
        //scoreBoard.setX((Constants.scoreStartX-100)*Constants.scaleX);
        settingButton.setY(Constants.scaleY*(1200+15));
        Drawable e = ContextCompat.getDrawable(context, R.drawable.scoreboard);
        DrawableCompat.setTint(e, SettingObject.getButtonColorInt());

        scoreBoardButton.setBackground(e);
        scoreBoardButton.setLayoutParams(lp20);
        scoreBoardButton.setPadding(5,5,5,5);

        scoreBoardButton.setX(Constants.scaleX*(Constants.scoreStartX-300));
        //scoreBoard.setX((Constants.scoreStartX-100)*Constants.scaleX);
        scoreBoardButton.setY(Constants.scaleY*(1200+15));

        Paint strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStrokeWidth(10);

        Bitmap bg = Bitmap.createBitmap(windowSize.x-((int)((Constants.scoreStartX-25)*Constants.scaleX)), ((int)windowSize.y), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(bg);
        canvas.drawLine(5,0,5,windowSize.y, strokePaint);
        canvas.drawLine(1,900*Constants.scaleY,windowSize.x-((int)((Constants.scoreStartX)*Constants.scaleX)), 900*Constants.scaleY, strokePaint);
        ImageView iV = new ImageView(this);
        RelativeLayout.LayoutParams ivlp = new RelativeLayout.LayoutParams(windowSize.x-((int)((Constants.scoreStartX-10)*Constants.scaleX)), windowSize.y);
        iV.setLayoutParams(ivlp);
        iV.setAdjustViewBounds(true);
        iV.setScaleType(ImageView.ScaleType.FIT_XY);
        iV.setImageBitmap(bg);
        iV.setX((Constants.scoreStartX-10)*Constants.scaleX);
        iV.setY(0);
        relativelayout.addView(iV);

            numberButtons = new Button[11];
            RelativeLayout.LayoutParams numButtonLP = new RelativeLayout.LayoutParams((int)(Constants.scaleY*90),((int)(Constants.scaleX*90)));

            for (int i=0; i <=10; i++)
            {
                numberButtons[i] = new Button(this);
                numberButtons[i].setText(Integer.toString(i));
                numberButtons[i].setPadding(5,5,5,5);
                numberButtons[i].setLayoutParams(numButtonLP);
                numberButtons[i].setX(Constants.scaleX*(Constants.playedCardsStartingX + (i*95)));
                numberButtons[i].setY(Constants.scaleY* Constants.playedCardsStartingY);
                numberButtons[i].setBackgroundColor(SettingObject.getButtonColorInt());

                numberButtons[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        numberButtonSelected=Integer.parseInt((String) ((Button)v).getText());
                        for (int i=0;i <= 10;i++)
                        {
                            numberButtons[i].setBackgroundColor(SettingObject.getButtonColorInt());
                        }
                        if (numberButtonDisabled >= 0)
                        {
                            numberButtons[numberButtonDisabled].setBackgroundColor(darkenColor(SettingObject.getButtonColorInt()));
                        }
                        v.setBackgroundColor(Color.CYAN);
                    }
                });
            }


        settingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                synchronized ("settingsLock") {
                    if (!State.settingsLock) {
                        State.settingsLock = true;
                        Intent intent = new Intent(v.getContext(), Score_Board.class);
                        v.getContext().startActivity(intent);
                    }
                }
        }
        });

        relativelayout.addView(settingButton);

        scoreBoardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                synchronized ("settingsLock") {
                    if (!State.settingsLock) {
                        State.settingsLock = true;
                        Intent intent = new Intent(v.getContext(), ScoreBoard.class);
                        v.getContext().startActivity(intent);
                    }
                }
            }
        });

        //TODO
        relativelayout.addView(scoreBoardButton);


        smileButton = makeEmojiButton(R.drawable.smile, 1, "SMILE");
        relativelayout.addView(smileButton);
        sadButton = makeEmojiButton(R.drawable.sad, 2, "SAD");
        relativelayout.addView(sadButton);
        tongueButton = makeEmojiButton(R.drawable.tongue, 3, "TONGUE");
        relativelayout.addView(tongueButton);
        wowButton = makeEmojiButton(R.drawable.wow, 4, "WOW");
        relativelayout.addView(wowButton);

        coolButton = makeEmojiButton(R.drawable.cool, 5, "COOL");
        relativelayout.addView(coolButton);
        reliefButton = makeEmojiButton(R.drawable.relief, 6, "RELIEF");
        relativelayout.addView(reliefButton);
        nervousButton = makeEmojiButton(R.drawable.nervous, 7, "NERVOUS");
        relativelayout.addView(nervousButton);
        ohHellButton = makeEmojiButton(R.drawable.ohhell, 8, "OHHELL");
        relativelayout.addView(ohHellButton);
        cryButton = makeEmojiButton(R.drawable.cry, 9, "CRY");
        relativelayout.addView(cryButton);
        joinButton.setText("Join");
        joinButton.setLayoutParams(lp2);
        joinButton.setX(Constants.scaleX*200);
        joinButton.setY(Constants.scaleY*500);
        relativelayout.addView(joinButton);


        joinButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                    joinButton.setEnabled(false);
                    try {
                        join();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


            }
        });


        suitLed = new TextView(this);
        suitLed.setTextColor(SettingObject.getTextColorInt());
        suitLed.setText("Suit Led:");
        suitLed.setX(Constants.scaleX*(Constants.trumpCardX));
        suitLed.setY(Constants.scaleY*(Constants.trumpCardY+250+((int)(Constants.scaleY*(8*Constants.playedCardsNameTextSize)))));
        suitLed.setTextSize(Constants.playedCardsNameTextSize);
        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams((int)(Constants.scaleX*190),((int)(Constants.scaleY*(8*Constants.playedCardsNameTextSize))));
        suitLed.setLayoutParams(lp3);
        suitLed.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        suitLedImage = new ImageView(this);
        suitLedImage.setImageResource(getResources().getIdentifier("spade" , "drawable", getPackageName()));
        RelativeLayout.LayoutParams lpSuitLedImage = new RelativeLayout.LayoutParams(((int)(Constants.scaleY*(8*Constants.playedCardsNameTextSize))),((int)(Constants.scaleY*(5*Constants.playedCardsNameTextSize))));
        suitLedImage.setLayoutParams(lpSuitLedImage);
        suitLedImage.setX(Constants.scaleX*(Constants.trumpCardX+170));
        suitLedImage.setY(Constants.scaleY*(Constants.trumpCardY+250+((int)(Constants.scaleY*(8*Constants.playedCardsNameTextSize)))));

        setBackgroundColor();



        //getting the button
        //buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);

        //adding a click listener
        //buttonPlay.setOnClickListener(this);
        created=true;

        if (State.state != 0)
        {
            relativelayout.removeView(joinButton);
            ScoreDisplay.setupScoreDisplay(1, relativelayout, this, true);
            ScoreDisplay.display(State.score.scores, context, relativelayout, State.score.round, State.state > 1, State.score.dealer);
        }
        if (State.state == 1)
        {
            relativelayout.addView(startButton);
            startButton.setText("Start C");

            if (State.showNumCard == true)
            {
                showNumberButtons(1, State.maxRounds, -1, -1  );
                State.showNumCard=true;
            }

        }
        if (State.state == 2)
        {
            final ImageView d_jView = new ImageView(context);
            d_jView.setImageResource(getResources().getIdentifier( State.trump.getResourceName() , "drawable", getPackageName()));
            RelativeLayout.LayoutParams lp2_2 = new RelativeLayout.LayoutParams((int)(Constants.scaleY*180),((int)(Constants.scaleX*360)));
            d_jView.setLayoutParams(lp2_2);
            d_jView.setX(Constants.scaleX*Constants.trumpCardX);
            d_jView.setY(Constants.scaleY*Constants.trumpCardY);
            relativelayout.addView(d_jView);

            setupCardPlayArea(relativelayout);

            for (int i=0; i <State.namedViews.size();i++)
            {
                ((ViewGroup)State.namedViews.get(i).getParent()).removeView(State.namedViews.get(i));
                ((ViewGroup)State.playedViews.get(i).getParent()).removeView(State.playedViews.get(i));
                cardPlayRL.addView(State.namedViews.get(i));
                cardPlayRL.addView(State.playedViews.get(i));
            }
            for (int i = 0; i < displayHand.getCards().size(); i++) {
                showCard(displayHand.getCards().get(i));
            }
            displayHand.sort();

            if (cardPlayedCount != 0)
            {
                if (!playedTrick) {
                    tintUnplayableCards(false);
                }
                if ("s".equals(currentSuitLed))
                {
                    suitLedImage.setImageResource(getResources().getIdentifier("spade" , "drawable", getPackageName()));
                }
                else if ("d".equals(currentSuitLed))
                {
                    suitLedImage.setImageResource(getResources().getIdentifier("diamond" , "drawable", getPackageName()));
                }
                else if ("h".equals(currentSuitLed))
                {
                    suitLedImage.setImageResource(getResources().getIdentifier("heart" , "drawable", getPackageName()));
                }
                else
                {
                    suitLedImage.setImageResource(getResources().getIdentifier("club" , "drawable", getPackageName()));
                }
                relativelayout.addView(suitLed);
                relativelayout.addView(suitLedImage);
            }

            if (State.lastBid == null) {
                State.lock=false;
                if (turn) {
                    relativelayout.addView(MainActivity.playGame);
                }
            }
            else
            {
                State.lock=true;
                ScoreDisplay.markAsSelected(State.lastBid.bidder, ScoreDisplay.BID);
                if (State.lastBid.bidder == State.playerNumber) {
                    showNumberButtons(0, State.lastBid.round, -1, State.lastBid.cantBid);
                    int totalBid=0;
                    if (State.lastBid.scores != null) {
                        for (int i = 0; i < State.lastBid.scores.length; i++) {
                            if (State.lastBid.scores[i].bid >= 0)
                            {
                                totalBid+=State.lastBid.scores[i].bid;
                            }
                        }
                    }
                    bidButton.setText("Bid!  "+totalBid+" to you.");
                    relativelayout.addView(bidButton);
                }

            }

        }
        createUpdateUiHandler();
    }


    HorizontalScrollView cardPlayArea;
    RelativeLayout cardPlayRL;
    public void setupCardPlayArea(RelativeLayout relativelayout)
    {
        if (cardPlayArea != null) {
            cardPlayArea.removeAllViews();
            cardPlayRL.removeAllViews();
            relativelayout.removeView(cardPlayArea);
        }
        cardPlayArea = new HorizontalScrollView(context);
        cardPlayArea.setBackgroundColor(Color.TRANSPARENT);

        RelativeLayout.LayoutParams SLP = new RelativeLayout.LayoutParams((int)((Constants.scoreStartX-Constants.playedCardsStartingX-20)*Constants.scaleX), (int)(480*Constants.scaleY) );
        RelativeLayout.LayoutParams SLP2 = new RelativeLayout.LayoutParams((int)((Constants.playedCardsSeperation*ScoreDisplay.getPlayerCount())*Constants.scaleX), (int)(480*Constants.scaleY) );

        cardPlayArea.setLayoutParams(SLP);
        cardPlayArea.setX(Constants.playedCardsStartingX*Constants.scaleX);
        cardPlayArea.setY(Constants.playedCardsStartingY*Constants.scaleY);

        cardPlayRL = new RelativeLayout(context);
        cardPlayRL.setGravity(Gravity.LEFT);
        cardPlayRL.setLayoutParams(SLP2);
        cardPlayArea.addView(cardPlayRL);
        relativelayout.addView(cardPlayArea);
        cardPlayRL.setPadding(0,0,0,0);
        cardPlayRL.scrollTo(0,0);



    }
    public void prepareNextRound()
    {
        numberButtonDisabled=-1;
        Iterator<ImageView> iter = cardViews.iterator();
        RelativeLayout relativelayout = (RelativeLayout)findViewById(R.id.relativeLayout);

        while (iter.hasNext())
        {
            relativelayout.removeView(iter.next());
        }
        cardViews.clear();
        Iterator<View> iter2 = State.playedViews.iterator();

        while (iter2.hasNext())
        {
            cardPlayRL.removeView(iter2.next());
        }
        State.playedViews.clear();

        iter2 = State.namedViews.iterator();

        while (iter2.hasNext())
        {
            cardPlayRL.removeView(iter2.next());
        }
        State.namedViews.clear();

        if (State.trump != null && State.trump.imgView != null) {
            relativelayout.removeView(State.trump.imgView);
        }
        xpos = (int) (Constants.dealtCardsStartingX*Constants.scaleX);
        ypos = (int) (Constants.dealtCardsStartingY*Constants.scaleY);
        cardPlayedCount=0;
    }
    public void deal() throws Exception {

            if (State.nh == null) {
                State.nh = new NetworkHandler(this, settingObject);
                Thread t1 = new Thread(State.nh);
                t1.start();
            }
        (new Thread() {
            public void run() {
                try {
                    com.gmail.dharris.pattonohheck.State.nh.requestDeal();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void sendBid() throws Exception
    {
        if (State.nh == null) {
            State.nh = new NetworkHandler(this, settingObject);

            Thread t1 = new Thread(State.nh);
            t1.start();
        }
        (new Thread() {
            public void run() {
                try {
                    com.gmail.dharris.pattonohheck.State.nh.sendBid(numberButtonSelected);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void start() throws Exception {

        if (State.nh == null) {
            State.nh = new NetworkHandler(this, settingObject);
            Thread t1 = new Thread(State.nh);
            t1.start();
        }
        (new Thread() {
            public void run() {
                try {
                    com.gmail.dharris.pattonohheck.State.nh.requestStart(numberButtonSelected);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public void join() throws Exception {

        if (State.nh != null)
        {
            State.nh.close();

            gameOver=false;
            RelativeLayout relativelayout = (RelativeLayout)findViewById(R.id.relativeLayout);
            relativelayout.removeView(wonButton);
            //ScoreDisplay.cleanAllEmojis();
            ScoreDisplay.clearAll();
            ScoreDisplay.setupScoreDisplay(1, relativelayout, this, true);

            prepareNextRound();
            State.curSelectedCard=null;
            State.state =0;
            State.wonTrick=0;
            State.lock=false;
            State.showNumCard=false;


        }
        if (State.nh == null) {
            State.nh = new NetworkHandler(this, settingObject);

            Thread t1 = new Thread(State.nh);
            t1.start();
        }
        (new Thread() {
            public void run() {
                try {
                    com.gmail.dharris.pattonohheck.State.nh.requestJoin();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void onClick(View v) {

        //starting game activity
        //startActivity(new Intent(this, GameActivity.class));
    }

    int xpos=Constants.dealtCardsStartingX;
    int ypos=Constants.dealtCardsStartingY;

    public java.util.ArrayList<ImageView> cardViews = new java.util.ArrayList<ImageView>();

    public final static int MESSAGE_SHOW_DEAL = 1;
    public final static int MESSAGE_CARD_PLAYED = 2;
    public final static int MESSAGE_GAME_PLAYERS = 3;
    public final static int MESSAGE_GAME_STARTED = 4;
    public final static int MESSAGE_NEXT_PLAY = 5;
    public final static int MESSAGE_BID = 6;
    public final static int MESSAGE_SCORE_UPDATE=7;
    public final static int MESSAGE_GAME_OVER=8;
    public final static int MESSAGE_RESUME=9;
    public final static int MESSAGE_UPDATE_BACKGROUND=10;
    public final static int MESSAGE_EMOJI=11;
    public final static int MESSAGE_CLEAN_EMOJI=12;


    @ColorInt
    int darkenColor(@ColorInt int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }

    private void showNumberButtons(int start, int end, int def, int disabled)
    {
        RelativeLayout relativelayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        int pos=0;
        for (int i=start; i <= end;i++) {
            numberButtons[i].setBackgroundColor(SettingObject.getButtonColorInt());
            relativelayout.addView(numberButtons[i]);
            numberButtons[i].setX(Constants.scaleX*(Constants.playedCardsStartingX + (pos*130)));
            pos++;
        }

        if (disabled >= 0)
        {
            numberButtonDisabled=disabled;
            numberButtons[disabled].setEnabled(false);
            numberButtons[disabled].setBackgroundColor(darkenColor(SettingObject.getButtonColorInt()));
        }
        if (def >= 0) {
            numberButtons[def].setBackgroundColor(Color.CYAN);
        }
        numberButtonSelected=def;
    }

    private void hideNumberButtons()
    {
        RelativeLayout relativelayout = (RelativeLayout)findViewById(R.id.relativeLayout);

        for (int i=0; i <= 10;i++) {
            relativelayout.removeView(numberButtons[i]);
            numberButtons[i].setEnabled(true);

        }
    }
    private void createUpdateUiHandler()
    {
            State.updateUIHandler = new Handler()
            {

                @Override
                public void handleMessage(Message msg) {
                    RelativeLayout relativelayout = State.RL;
                    System.out.println("Handling Message!! "+relativelayout);
                    if (msg.what == MESSAGE_CLEAN_EMOJI)
                    {
                        ((ScoreDisplay) msg.obj).cleanEmoji();
                    }
                    if (msg.what == MESSAGE_EMOJI)
                    {
                        if (((POHResponse)msg.obj).ResponseType.equals("E_SMILE"))
                        {
                            ScoreDisplay.showEmoji(((POHResponse) msg.obj).playerNumber, R.drawable.smile, context, relativelayout);
                        }
                        if (((POHResponse)msg.obj).ResponseType.equals("E_WOW"))
                        {
                            ScoreDisplay.showEmoji(((POHResponse) msg.obj).playerNumber, R.drawable.wow, context, relativelayout);
                        }
                        if (((POHResponse)msg.obj).ResponseType.equals("E_TONGUE"))
                        {
                            ScoreDisplay.showEmoji(((POHResponse) msg.obj).playerNumber, R.drawable.tongue, context, relativelayout);
                        }
                        if (((POHResponse)msg.obj).ResponseType.equals("E_SAD"))
                        {
                            ScoreDisplay.showEmoji(((POHResponse) msg.obj).playerNumber, R.drawable.sad, context, relativelayout);
                        }
                        if (((POHResponse)msg.obj).ResponseType.equals("E_COOL"))
                        {
                            ScoreDisplay.showEmoji(((POHResponse) msg.obj).playerNumber, R.drawable.cool, context, relativelayout);
                        }
                        if (((POHResponse)msg.obj).ResponseType.equals("E_RELIEF"))
                        {
                            ScoreDisplay.showEmoji(((POHResponse) msg.obj).playerNumber, R.drawable.relief, context, relativelayout);
                        }
                        if (((POHResponse)msg.obj).ResponseType.equals("E_NERVOUS"))
                        {
                            ScoreDisplay.showEmoji(((POHResponse) msg.obj).playerNumber, R.drawable.nervous, context, relativelayout);
                        }
                        if (((POHResponse)msg.obj).ResponseType.equals("E_OHHELL"))
                        {
                            ScoreDisplay.showEmoji(((POHResponse) msg.obj).playerNumber, R.drawable.ohhell, context, relativelayout);
                        }
                        if (((POHResponse)msg.obj).ResponseType.equals("E_CRY"))
                        {
                            ScoreDisplay.showEmoji(((POHResponse) msg.obj).playerNumber, R.drawable.cry, context, relativelayout);
                        }

                    }

                    if (gameOver)
                    {
                        return;
                    }

                    if(msg.what == MESSAGE_SCORE_UPDATE) {
                        if ((((POHResponse)msg.obj).currentPlayerNumber > 0) && (State.playerNumber < 1))
                        {
                            State.playerNumber=(((POHResponse)msg.obj).currentPlayerNumber);
                        }
                        State.score=((POHResponse)msg.obj);
                        ScoreDisplay.display(((POHResponse)msg.obj).scores, context, relativelayout, ((POHResponse)msg.obj).round, State.state > 1, ((POHResponse)msg.obj).dealer);
                        if (((POHResponse)msg.obj).scoreBoard != null)
                        {
                            ScoreBoard.setLatestScoreBoard(((POHResponse)msg.obj).scoreBoard);
                        }
                    }
                    else if (State.showWon)
                    {
                        relativelayout.removeView(MainActivity.wonButton);
                        State.showWon=false;
                    }
                        // Means the message is sent from child thread.
                    if (msg.what == MESSAGE_GAME_OVER)
                    {
                        MainActivity.wonButton.setText("Winner: "+(((POHResponse) msg.obj).won));
                        relativelayout.addView(MainActivity.wonButton);
                        State.showWon=true;
                        gameOver=true;
                        relativelayout.addView(joinButton);
                        joinButton.setEnabled(true);
                    }
                    if(msg.what == MESSAGE_SHOW_DEAL)
                    {
                        playedTrick=false;
                        if (State.showNumCard == true)
                        {
                            hideNumberButtons();
                            State.showNumCard=false;
                        }
                        State.wonTrick=0;
                        if (turn)
                        {
                            relativelayout.removeView(MainActivity.playGame);
                            turn=false;
                        }
                        prepareNextRound();
                        // Update ui in main thread.
                        State.hand = (Hand)((POHResponse)msg.obj).responseHand;
                        State.trump = ((POHResponse)msg.obj).trump;
                        final ImageView d_jView = new ImageView(context);
                        d_jView.setImageResource(getResources().getIdentifier( State.trump.getResourceName() , "drawable", getPackageName()));
                        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams((int)(Constants.scaleY*180),((int)(Constants.scaleX*360)));
                        d_jView.setLayoutParams(lp2);
                        d_jView.setX(Constants.scaleX*Constants.trumpCardX);
                        d_jView.setY(Constants.scaleY*Constants.trumpCardY);
                        relativelayout.addView(d_jView);
                        State.trump.imgView=d_jView;
                        ScoreDisplay.markAsSelected(((POHResponse) msg.obj).currentPlayerNumber, ScoreDisplay.BID);
                        if (((POHResponse) msg.obj).currentPlayerNumber == State.playerNumber)
                        {
                            turn=true;
                        }
                        System.out.println();
                        displayHand = new DisplayHand(State.hand.getCards());
                        displayHand.setTrump(State.trump);
                        for (int i = 0; i < displayHand.getCards().size(); i++) {
                            showCard(displayHand.getCards().get(i));
                        }
                        displayHand.sort();
                        currentSuitLed=null;
                        State.lock=false;
                    }
                    if(msg.what == MESSAGE_RESUME)
                    {
                        relativelayout.removeView(MainActivity.playGame);
                        relativelayout.removeView(joinButton);

                        prepareNextRound();
                        // Update ui in main thread.
                        State.hand = (Hand)((POHResponse)msg.obj).responseHand;
                        State.trump = ((POHResponse)msg.obj).trump;
                        final ImageView d_jView = new ImageView(context);
                        d_jView.setImageResource(getResources().getIdentifier( State.trump.getResourceName() , "drawable", getPackageName()));
                        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams((int)(Constants.scaleY*180),((int)(Constants.scaleX*360)));
                        d_jView.setLayoutParams(lp2);
                        d_jView.setX(Constants.scaleX*Constants.trumpCardX);
                        d_jView.setY(Constants.scaleY*Constants.trumpCardY);
                        relativelayout.addView(d_jView);
                        State.trump.imgView=d_jView;
                        ScoreDisplay.markAsSelected(((POHResponse) msg.obj).currentPlayerNumber, ScoreDisplay.PLAY);
                        if (((POHResponse) msg.obj).currentPlayerNumber == State.playerNumber)
                        {
                            turn=true;
                        }
                        System.out.println();
                        displayHand = new DisplayHand(State.hand.getCards());
                        displayHand.setTrump(State.trump);
                        for (int i = 0; i < displayHand.getCards().size(); i++) {
                            showCard(displayHand.getCards().get(i));
                        }
                        displayHand.sort();
                        currentSuitLed=null;
                        State.state=2;
                        State.lock=false;
                    }
                    if(msg.what == MESSAGE_CARD_PLAYED) {


                        System.out.println("Card Played: "+((POHResponse)msg.obj).cardPlayed);
                        int startPos=1;

                        if (((POHResponse) msg.obj).winningCard != null) {
                            if (((POHResponse) msg.obj).cardPlayed.equals(((POHResponse) msg.obj).winningCard)) {
                                startPos=0;
                            }
                        }

                        for (int i=0; i <State.namedViews.size();i++)
                        {
                            if (((int)State.playedViews.get(i).getX()) >= (int)(Constants.scaleX*(startPos*Constants.playedCardsSeperation ))) {
                                State.namedViews.get(i).setX(State.namedViews.get(i).getX() + (Constants.scaleX * Constants.playedCardsSeperation));
                                State.playedViews.get(i).setX(State.playedViews.get(i).getX() + (Constants.scaleX * Constants.playedCardsSeperation));
                            }
                        }
                        final ImageView d_jView = new ImageView(context);
                        d_jView.setImageResource(getResources().getIdentifier( ((POHResponse)msg.obj).cardPlayed , "drawable", getPackageName()));
                        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams((int)(Constants.scaleX*180),((int)(Constants.scaleY*360)));
                        d_jView.setLayoutParams(lp2);
                        d_jView.setX(Constants.scaleX*(startPos*Constants.playedCardsSeperation ));
                        d_jView.setY(20*Constants.scaleY);

                        currentSuitLed = ((POHResponse)msg.obj).currentSuitLed;

                        cardPlayRL.addView(d_jView);
                        State.playedViews.add(d_jView);
                        TextView textView = new TextView(context);
                        textView.setTextColor(SettingObject.getTextColorInt());
                        textView.setText(((POHResponse)msg.obj).playerRequest);
                        //textView.setX(Constants.scaleX*(cardPlayedCount*Constants.playedCardsSeperation+Constants.playedCardsStartingX));
                        //textView.setY(Constants.scaleY*(Constants.playedCardsStartingY-Constants.playedCardsNameSeperation));

                        textView.setX(Constants.scaleX*(startPos*Constants.playedCardsSeperation));
                        textView.setY(Constants.scaleY*(0));
                        textView.setTextSize(Constants.playedCardsNameTextSize);
                        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams((int)(Constants.scaleX*180),((int)(Constants.scaleY*(8*Constants.playedCardsNameTextSize))));
                        textView.setLayoutParams(lp3);
                        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

                        cardPlayRL.addView(textView);

                        State.namedViews.add(textView);
                        if ((((POHResponse) msg.obj).lastCard))
                        {
                            System.out.println("Locking!");
                            State.lock=true;
                            tintUnplayableCards(true);
                            relativelayout.removeView(suitLed);
                            relativelayout.removeView(suitLedImage);


                        }
                        ScoreDisplay.markAsSelected(((POHResponse) msg.obj).currentPlayerNumber, ScoreDisplay.PLAY);

                        if (((POHResponse) msg.obj).currentPlayerNumber == State.playerNumber)
                        {
                            if (!(((POHResponse) msg.obj).lastCard))
                            {
                                relativelayout.removeView(MainActivity.playGame);
                                relativelayout.addView(MainActivity.playGame);
                                State.lock=false;
                            }
                            turn=true;
                        }
                        if ((((POHResponse) msg.obj).won != null))
                        {
                            MainActivity.wonButton.setText("Congrats "+(((POHResponse) msg.obj).won));
                            relativelayout.addView(MainActivity.wonButton);
                            State.showWon=true;
                        }

                        if (cardPlayedCount == 0)
                        {
                            if (!playedTrick) {
                                tintUnplayableCards(false);
                            }
                            if ("s".equals(currentSuitLed))
                            {
                                suitLedImage.setImageResource(getResources().getIdentifier("spade" , "drawable", getPackageName()));
                            }
                            else if ("d".equals(currentSuitLed))
                            {
                                suitLedImage.setImageResource(getResources().getIdentifier("diamond" , "drawable", getPackageName()));
                            }
                            else if ("h".equals(currentSuitLed))
                            {
                                suitLedImage.setImageResource(getResources().getIdentifier("heart" , "drawable", getPackageName()));
                            }
                            else
                            {
                                suitLedImage.setImageResource(getResources().getIdentifier("club" , "drawable", getPackageName()));
                            }
                            relativelayout.addView(suitLed);
                            relativelayout.addView(suitLedImage);
                        }
                        cardPlayedCount++;
                        if (((POHResponse) msg.obj).wonTrick) {
                            State.wonTrick++;
                           // ScoreDisplay.markAsWon(((POHResponse) msg.obj).currentPlayerNumber);

                        }
                        System.out.println("CC: ("+((POHResponse) msg.obj).winningCard+") ("+((POHResponse)msg.obj).cardPlayed+")");
                        if (((POHResponse) msg.obj).winningCard != null)
                        {
                            if (((POHResponse)msg.obj).cardPlayed.equals(((POHResponse) msg.obj).winningCard)) {
                                System.out.println("Winning Card!");
                                //Iterator<View> iter2 = State.playedViews.iterator();

                                /*while (iter2.hasNext()) {
                                    (iter2.next()).setY(Constants.scaleY*Constants.playedCardsStartingY+15);
                                }
                                d_jView.setY(Constants.scaleY*(Constants.playedCardsStartingY+15));*/
                                Iterator<View> iter2 = State.namedViews.iterator();
                                while (iter2.hasNext()) {
                                    TextView iterVal = ((TextView)iter2.next());
                                    iterVal.setTextColor(SettingObject.getTextColorInt());
                                    iterVal.setTypeface(null, Typeface.NORMAL);
                                }
                                textView.setTypeface(null, Typeface.BOLD);
                                textView.setTextColor(SettingObject.getHandLeaderColorInt());


                            }

                        }

                    }
                    if (msg.what == MESSAGE_UPDATE_BACKGROUND) {
                        setBackgroundColor();
                    }
                    if (msg.what == MESSAGE_GAME_PLAYERS)
                    {
                        State.maxRounds=((POHResponse) msg.obj).maxRounds;
                        if (State.showNumCard == false)
                        {
                            showNumberButtons(1, ((POHResponse) msg.obj).maxRounds, -1, -1  );
                            State.showNumCard=true;
                        }
                        else
                        {
                            hideNumberButtons();
                            showNumberButtons(1, ((POHResponse) msg.obj).maxRounds, -1, -1  );
                        }
                        System.out.println("Game Players: "+State.state);
                        if (State.state==0) {
                            relativelayout.removeView(joinButton);
                            //relativelayout.removeView(dealButton);
                            relativelayout.addView(MainActivity.startButton);
                            State.playerNumber=((POHResponse) msg.obj).playerNumber;
                            State.state=1;
                        }
                    }
                    if (msg.what == MESSAGE_NEXT_PLAY)
                    {
                        playedTrick=false;

                        Iterator<View> iter2 = State.playedViews.iterator();

                        while (iter2.hasNext())
                        {
                            cardPlayRL.removeView(iter2.next());
                        }
                        State.playedViews.clear();
                        iter2 = State.namedViews.iterator();

                        while (iter2.hasNext())
                        {
                            cardPlayRL.removeView(iter2.next());
                        }
                        State.namedViews.clear();
                        cardPlayedCount=0;
                        if (turn)
                        {
                            relativelayout.addView(MainActivity.playGame);
                        }
                        currentSuitLed=null;
                        cardPlayRL.scrollTo(0,0);
                        State.lock=false;
                    }
                    if (msg.what == MESSAGE_BID) {
                        POHResponse response = ((POHResponse) msg.obj);
                        State.lastBid=response;

                        System.out.println("BID! "+ response.bidder);
                        if (!response.biddingDone) {
                            State.lock=true;
                            ScoreDisplay.markAsSelected(response.bidder, ScoreDisplay.BID);
                            if (response.bidder == State.playerNumber) {
                                showNumberButtons(0, response.round, -1, response.cantBid);
                                int totalBid=0;
                                if (response.scores != null) {
                                    for (int i = 0; i < response.scores.length; i++) {
                                        if (response.scores[i].bid >= 0)
                                        {
                                            totalBid+=response.scores[i].bid;
                                        }
                                    }
                                }
                                bidButton.setText("Bid!  "+totalBid+" to you.");
                                relativelayout.addView(bidButton);
                            }
                        }
                        else
                        {
                            State.lastBid=null;
                            ScoreDisplay.markAsSelected(((POHResponse) msg.obj).currentPlayerNumber, ScoreDisplay.PLAY);
                            State.lock=false;
                            if (turn)
                            {
                                relativelayout.addView(MainActivity.playGame);
                            }
                        }
                    }
                    if (msg.what == MESSAGE_GAME_STARTED)
                    {
                        System.out.println("Game Started! "+State.state);
                        setupCardPlayArea(relativelayout);
                        ScoreDisplay.markAsSelected(((POHResponse) msg.obj).currentPlayerNumber, ScoreDisplay.BID);

                        if (State.state == 1) {
                            System.out.println("Removing views "+relativelayout);
                            relativelayout.removeView(MainActivity.startButton);
                            relativelayout.removeView(joinButton);
                            //relativelayout.addView(dealButton);

                            if (((POHResponse) msg.obj).currentPlayerNumber == State.playerNumber)
                            {
                                turn=true;
                            }

                            State.state = 2;
                        }
                    }
                }
            };

    }

    private static String currentSuitLed;

    public void tintUnplayableCards(boolean reset)
    {
        boolean haveSuitLed=false;
        if (reset == false) {
            for (int a1 = 0; a1 < displayHand.getCards().size(); a1++) {
                if (currentSuitLed != null && displayHand.getCards().get(a1).getResourceName().startsWith(currentSuitLed)) {
                    haveSuitLed = true;
                }
            }
        }
        for (int a1 = 0; a1 < displayHand.getCards().size(); a1++)
        {
            if (currentSuitLed == null || haveSuitLed == false || displayHand.getCards().get(a1).getResourceName().startsWith(currentSuitLed)) {
                displayHand.getCards().get(a1).imgView.setColorFilter(null);
            }
            else
            {
                displayHand.getCards().get(a1).imgView.setColorFilter(Color.parseColor("#50D3D3D3"));
            }
        }
    }
    public void showCard(final Card card)
    {
        final ImageView d_jView = new ImageView(this);
        d_jView.setImageResource(getResources().getIdentifier(card.getResourceName() , "drawable", getPackageName()));
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams((int)(Constants.scaleY*240),((int)(Constants.scaleX*480)));
        d_jView.setLayoutParams(lp2);
        d_jView.setX(xpos);
        d_jView.setY(ypos);

        RelativeLayout relativelayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        relativelayout.addView(d_jView);
        cardViews.add(d_jView);
        card.imgView=d_jView;
        xpos+=(Constants.dealtCardsSeperation*Constants.scaleX);
        ypos+=0;

        d_jView.setOnTouchListener(new View.OnTouchListener()
        {
            PointF DownPT = new PointF(); // Record Mouse Position When Pressed Down
            PointF StartPT = new PointF(); // Record Start Position of 'img'
            int origPos = 0;
            boolean played = false;
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_MOVE :
                        /*d_jView.bringToFront();

                        int x = (int)(StartPT.x + event.getX() - DownPT.x);
                        int y = (int)(StartPT.y + event.getY() - DownPT.y);
                        x = (x > 620) ? 620:x;
                        //y=d_jView.getY();
                        d_jView.setX(x);
                        d_jView.setY(20);
                        //d_jView.setY(y);
                        StartPT.set( d_jView.getX(), d_jView.getY() );*/
                        System.out.println("Commented out move!");
                        break;
                    case MotionEvent.ACTION_DOWN :
                        System.out.println("Turn: "+turn+"  Lock: "+State.lock);
                        boolean haveSuitLed = false;

                        for (int a1 = 0; a1 < displayHand.getCards().size(); a1++)
                        {
                            if (currentSuitLed != null && displayHand.getCards().get(a1).getResourceName().startsWith(currentSuitLed))
                            {
                                haveSuitLed=true;
                            }
                        }

                        System.out.println("Y: "+d_jView.getY()+ " vs "+(int)((Constants.dealtCardsStartingY-Constants.dealtCardsYRise)*Constants.scaleY));

                        if ((State.curSelectedCard==card) && turn && !State.lock)
                        {
                            if (currentSuitLed == null || haveSuitLed == false || card.getResourceName().startsWith(currentSuitLed)) {

                                System.out.println("Playing " + card.getResourceName());
                                RelativeLayout relativelayout = (RelativeLayout) findViewById(R.id.relativeLayout);
                                displayHand.getCards().remove(card);

                                relativelayout.removeView(card.imgView);
                                displayHand.sort();
                                played = true;
                                turn = false;
                                playedTrick=true;
                                relativelayout.removeView(MainActivity.playGame);

                                (new Thread() {
                                    public void run() {
                                        try {
                                            com.gmail.dharris.pattonohheck.State.nh.playCard(card);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                            else
                            {
                                //d_jView.setY((int)(Constants.dealtCardsStartingY*Constants.scaleY));
                                displayHand.sort();
                            }
                            break;
                        }

                        displayHand.sort();
                        State.curSelectedCard=null;

                        DownPT.set( event.getX(), event.getY() );
                        StartPT.set( d_jView.getX(), d_jView.getY() );
                        if (currentSuitLed == null || haveSuitLed == false || card.getResourceName().startsWith(currentSuitLed)) {
                            d_jView.setY((int)(d_jView.getY()-(Constants.dealtCardsYRise*Constants.scaleY)));
                            State.curSelectedCard=card;
                        }
                        break;
                    case MotionEvent.ACTION_UP :
                        // Nothing have to do
                        if (played)
                        {
                            break;
                        }
                        /*System.out.println("Dropped @ "+d_jView.getX());
                        int zx = (int) d_jView.getX() - 20;
                        int tx = zx % 60;
                        zx = zx - tx;
                        if (tx > 20)
                        {
                            zx = zx + 60;
                        }
                        d_jView.setX(Constants.scaleX*(zx+20));

                        int finalPos = ((int)d_jView.getX()-20)/60;
                        if (finalPos > origPos) {
                            displayHand.getCards().remove(origPos);
                            displayHand.getCards().add(finalPos -1, card);
                        }
                        else
                        {
                            displayHand.getCards().remove(origPos);
                            displayHand.getCards().add(finalPos , card);
                        }
                        for (int a1 = 0; a1 < displayHand.getCards().size(); a1++)
                        {
                            displayHand.getCards().get(a1).imgView.setX(Constants.scaleX*((a1*60)+20));
                            displayHand.getCards().get(a1).imgView.bringToFront();
                        }*/
                        break;
                    default :
                        break;
                }
                return true;
            }
        });

    }
}
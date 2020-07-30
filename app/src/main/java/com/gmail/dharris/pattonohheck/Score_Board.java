package com.gmail.dharris.pattonohheck;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.gmail.dharris.pattonohheck.client.POHResponse;
import com.rarepebble.colorpicker.ColorObserver;
import com.rarepebble.colorpicker.ColorPickerView;
import com.rarepebble.colorpicker.ObservableColor;

import top.defaults.colorpicker.ColorPickerPopup;

public class Score_Board extends AppCompatActivity {

    private Button scoreBoard;
    private Button setBackgroundColor;
    private Button setButtonColor;
    private Button setButtonTextColor;
    private Button setButtonOtherTextColor;
    private Button setButtonHandLeaderColor;
    private Button setButtonPlayerHighlightColor;
    private Button setButtonBidderHightlightColor;
    private Button setDealerArrowColor;
    private Button resetColors;
    private Button changeName;

    public static MainActivity _aca= null;

    private TextView deb1;
    private TextView deb2;

    private TextView tvtl;
    private TextView tvtr;
    private TextView tvlh;
    private TextView tvhl;
    private Switch aSwitch;
    private Switch bSwitch;
    private Switch dSwitch;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
        getSupportActionBar(). hide();


        scoreBoard = new Button(this);
        setBackgroundColor = new Button(this);
        setButtonColor = new Button(this);
        setButtonTextColor = new Button(this);
        setButtonOtherTextColor = new Button(this);
        setButtonHandLeaderColor = new Button(this);
        setButtonPlayerHighlightColor= new Button(this);
        setButtonBidderHightlightColor= new Button(this);
        resetColors= new Button(this);
        setDealerArrowColor = new Button(this);
        changeName=new Button(this);
        setContentView(R.layout.activity_score__board);
        State.settingsLock=false;


        RelativeLayout relativelayout = (RelativeLayout) findViewById(R.id.RL_ScoreBoard);

        tvtl = new TextView(this);
        tvtl.setText("Trump Left");
        tvtl.setX(20 * Constants.scaleX);
        tvtl.setY(50 * Constants.scaleY);
        tvtl.setTextSize(Constants.playedCardsNameTextSize);
        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams((int) (200 * Constants.scaleX), 10 * Constants.playedCardsNameTextSize);
        tvtl.setLayoutParams(lp3);
        tvtl.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        relativelayout.addView(tvtl);
        tvtr = new TextView(this);
        tvtr.setText("Trump Right");
        tvtr.setX(375 * Constants.scaleX);
        tvtr.setY(50 * Constants.scaleY);
        tvtr.setTextSize(Constants.playedCardsNameTextSize);
        tvtr.setLayoutParams(lp3);
        tvtr.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        relativelayout.addView(tvtr);

        bSwitch = new Switch(this);
        bSwitch.setX(225 * Constants.scaleX);
        bSwitch.setY(50 * Constants.scaleY);
        ColorStateList thumbStates = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_checked},
                        new int[]{}
                },
                new int[]{
                        Color.BLUE,
                        Color.BLUE,
                        Color.BLUE
                }
        );
        bSwitch.setThumbTintList(thumbStates);
        ColorStateList trackStates = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_enabled},
                        new int[]{}
                },
                new int[]{
                        Color.GRAY,
                        Color.GRAY
                }
        );
        bSwitch.setTrackTintList(trackStates);
        bSwitch.setTrackTintMode(PorterDuff.Mode.OVERLAY);
        bSwitch.setChecked(SettingObject.isTrumpRight());

        bSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                SettingObject.setTrumpRight(isChecked);
                MainActivity.displayHand.sort();

            }
        });

        relativelayout.addView(bSwitch);



        dSwitch = new Switch(this);
        dSwitch.setX(1300 * Constants.scaleX);
        dSwitch.setY(50 * Constants.scaleY);
        dSwitch.setThumbTintList(thumbStates);
        dSwitch.setTrackTintList(trackStates);
        dSwitch.setTrackTintMode(PorterDuff.Mode.OVERLAY);
        dSwitch.setChecked(SettingObject.isDebug());

        dSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                SettingObject.setDebug(isChecked);
            }
        });
        deb1 = new TextView(this);
        deb1.setText("Normal");
        deb1.setX(1095 * Constants.scaleX);
        deb1.setY(50 * Constants.scaleY);
        deb1.setTextSize(Constants.playedCardsNameTextSize);
        deb1.setLayoutParams(lp3);
        deb1.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        relativelayout.addView(deb1);
        deb2 = new TextView(this);
        deb2.setText("Debug");
        deb2.setX(1450 * Constants.scaleX);
        deb2.setY(50 * Constants.scaleY);
        deb2.setTextSize(Constants.playedCardsNameTextSize);
        deb2.setLayoutParams(lp3);
        deb2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        relativelayout.addView(deb2);


        relativelayout.addView(dSwitch);

        tvlh = new TextView(this);
        tvlh.setText("Low to High");
        tvlh.setX(20 * Constants.scaleX);
        tvlh.setY(150 * Constants.scaleY);
        tvlh.setTextSize(Constants.playedCardsNameTextSize);
        tvlh.setLayoutParams(lp3);
        tvlh.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        relativelayout.addView(tvlh);
        tvhl = new TextView(this);
        tvhl.setText("High to Low");
        tvhl.setX(375 * Constants.scaleX);
        tvhl.setY(150 * Constants.scaleY);
        tvhl.setTextSize(Constants.playedCardsNameTextSize);
        tvhl.setLayoutParams(lp3);
        tvhl.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        relativelayout.addView(tvhl);

        aSwitch = new Switch(this);
        aSwitch.setX(225 * Constants.scaleX);
        aSwitch.setY(150 * Constants.scaleY);
        aSwitch.setThumbTintList(thumbStates);
        aSwitch.setTrackTintList(trackStates);
        aSwitch.setTrackTintMode(PorterDuff.Mode.OVERLAY);
        aSwitch.setChecked(SettingObject.isHighToLow());

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                SettingObject.setHighToLow(isChecked);
                MainActivity.displayHand.sort();


            }
        });

        relativelayout.addView(aSwitch);

        changeName.setText("Change Name");
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams((int) (600 * Constants.scaleX), (int) (90 * Constants.scaleY));

        changeName.setLayoutParams(lp2);
        changeName.setPadding(5, 5, 5, 5);

        changeName.setX(800 * Constants.scaleX);
        changeName.setY(450 * Constants.scaleY);
        relativelayout.addView(changeName);
        final Context aContext = this;
        changeName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                    AlertDialog.Builder builder = new AlertDialog.Builder(aContext);
                    builder.setTitle("Name?");

// Set up the input
                    final EditText input = new EditText(aContext);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    input.setText(SettingObject.getPlayerName());
                    builder.setView(input);

// Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SettingObject.setPlayerName(input.getText().toString());
                            System.out.println("Setting name to: "+SettingObject.getPlayerName());

                            SharedPreferences.Editor editor = SettingObject.getSettings().edit();
                            editor.putString("playerName", SettingObject.getPlayerName());
                            editor.commit();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();

            }
        });

        scoreBoard.setText("Return to Game");
        lp2 = new RelativeLayout.LayoutParams((int) (600 * Constants.scaleX), (int) (90 * Constants.scaleY));

        scoreBoard.setLayoutParams(lp2);
        scoreBoard.setPadding(5, 5, 5, 5);

        scoreBoard.setX(800 * Constants.scaleX);
        scoreBoard.setY(350 * Constants.scaleY);
        relativelayout.addView(scoreBoard);
        scoreBoard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                State.settingsLock=false;
                finish();
            }
        });



        {
            setButtonOtherTextColor.setLayoutParams(lp2);
            setButtonOtherTextColor.setX(20 * Constants.scaleX);
            setButtonOtherTextColor.setY(580 * Constants.scaleY);
            setButtonOtherTextColor.setText("Regular Text");
            setButtonOtherTextColor.setPadding(5, 5, 5, 5);

            relativelayout.addView(setButtonOtherTextColor);
            setButtonOtherTextColor.setOnClickListener(new View.OnClickListener() {
                public void onClick(final View v) {
                    /*new ColorPickerPopup.Builder(Score_Board.this)
                            .initialColor(SettingObject.getTextColorInt()) // Set initial color
                            .enableBrightness(true) // Enable brightness slider or not
                            .enableAlpha(true) // Enable alpha slider or not
                            .okTitle("Choose")
                            .cancelTitle("Cancel")
                            .showIndicator(true)
                            .showValue(true)
                            .build()
                            .show(v, new ColorPickerPopup.ColorPickerObserver() {
                                @Override
                                public void onColorPicked(int color) {
                                    System.out.println("Button Text Color: "+color);

                                    //v.setBackgroundColor(color);
                                    SettingObject.setTextColorInt(color);
                                    Message message = new Message();
                                    // Set message type
                                    message.what = _aca.MESSAGE_UPDATE_BACKGROUND;
                                    // Send message to main thread Handler.
                                    _aca.updateUIHandler.sendMessage(message);

                                    Score_Board.this.setColors();
                                }

                                public void onColor(int color, boolean fromUser) {

                                }
                            });

                     */
                    AlertDialog.Builder builder = new AlertDialog.Builder(Score_Board.this);

                    final ColorPickerView pickerBGC = new ColorPickerView(Score_Board.this);
                    pickerBGC.setColor(SettingObject.getTextColorInt());
                    pickerBGC.addColorObserver(new ColorObserver() {
                        @Override
                        public void updateColor(ObservableColor observableColor) {
                            System.out.println(observableColor.getColor());
                        }
                    });
                    builder.setTitle("Button Text Color")
                            .setView(pickerBGC)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // save result and refresh
                                    SettingObject.setTextColorInt(pickerBGC.getColor());
                                    Message message = new Message();
                                    // Set message type
                                    message.what = _aca.MESSAGE_UPDATE_BACKGROUND;
                                    // Send message to main thread Handler.
                                    State.updateUIHandler.sendMessage(message);

                                    Score_Board.this.setColors();

                                }
                            });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }
            });
        }
        {
            setButtonHandLeaderColor.setLayoutParams(lp2);
            setButtonHandLeaderColor.setX(20 * Constants.scaleX);
            setButtonHandLeaderColor.setY(680 * Constants.scaleY);
            setButtonHandLeaderColor.setText("Hand Leader");
            setButtonHandLeaderColor.setPadding(5, 5, 5, 5);

            relativelayout.addView(setButtonHandLeaderColor);
            setButtonHandLeaderColor.setOnClickListener(new View.OnClickListener() {
                public void onClick(final View v) {
                    /*new ColorPickerPopup.Builder(Score_Board.this)
                            .initialColor(SettingObject.getHandLeaderColorInt()) // Set initial color
                            .enableBrightness(true) // Enable brightness slider or not
                            .enableAlpha(true) // Enable alpha slider or not
                            .okTitle("Choose")
                            .cancelTitle("Cancel")
                            .showIndicator(true)
                            .showValue(true)
                            .build()
                            .show(v, new ColorPickerPopup.ColorPickerObserver() {
                                @Override
                                public void onColorPicked(int color) {

                                    //v.setBackgroundColor(color);
                                    SettingObject.setHandLeaderColorInt(color);
                                    Message message = new Message();
                                    // Set message type
                                    message.what = _aca.MESSAGE_UPDATE_BACKGROUND;
                                    // Send message to main thread Handler.
                                    _aca.updateUIHandler.sendMessage(message);

                                    Score_Board.this.setColors();
                                }

                                public void onColor(int color, boolean fromUser) {

                                }
                            });*/
                    AlertDialog.Builder builder = new AlertDialog.Builder(Score_Board.this);

                    final ColorPickerView pickerBGC = new ColorPickerView(Score_Board.this);
                    pickerBGC.setColor(SettingObject.getHandLeaderColorInt());
                    pickerBGC.addColorObserver(new ColorObserver() {
                        @Override
                        public void updateColor(ObservableColor observableColor) {
                            System.out.println(observableColor.getColor());
                        }
                    });
                    builder.setTitle("Button Text Color")
                            .setView(pickerBGC)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // save result and refresh
                                    SettingObject.setHandLeaderColorInt(pickerBGC.getColor());
                                    Message message = new Message();
                                    // Set message type
                                    message.what = _aca.MESSAGE_UPDATE_BACKGROUND;
                                    // Send message to main thread Handler.
                                    State.updateUIHandler.sendMessage(message);

                                    Score_Board.this.setColors();

                                }
                            });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }
            });
        }
        {
            setButtonPlayerHighlightColor.setLayoutParams(lp2);
            setButtonPlayerHighlightColor.setX(20 * Constants.scaleX);
            setButtonPlayerHighlightColor.setY(780 * Constants.scaleY);
            setButtonPlayerHighlightColor.setText("Player Highlight");
            setButtonPlayerHighlightColor.setPadding(5, 5, 5, 5);

            relativelayout.addView(setButtonPlayerHighlightColor);
            setButtonPlayerHighlightColor.setOnClickListener(new View.OnClickListener() {
                public void onClick(final View v) {
                    /*new ColorPickerPopup.Builder(Score_Board.this)
                            .initialColor(SettingObject.getPlayerHighlightColorInt()) // Set initial color
                            .enableBrightness(true) // Enable brightness slider or not
                            .enableAlpha(true) // Enable alpha slider or not
                            .okTitle("Choose")
                            .cancelTitle("Cancel")
                            .showIndicator(true)
                            .showValue(true)
                            .build()
                            .show(v, new ColorPickerPopup.ColorPickerObserver() {
                                @Override
                                public void onColorPicked(int color) {

                                    //v.setBackgroundColor(color);
                                    SettingObject.setPlayerHightlightColorInt(color);
                                    Message message = new Message();
                                    // Set message type
                                    message.what = _aca.MESSAGE_UPDATE_BACKGROUND;
                                    // Send message to main thread Handler.
                                    _aca.updateUIHandler.sendMessage(message);

                                    Score_Board.this.setColors();
                                }

                                public void onColor(int color, boolean fromUser) {

                                }
                            });

                     */
                    AlertDialog.Builder builder = new AlertDialog.Builder(Score_Board.this);

                    final ColorPickerView pickerBGC = new ColorPickerView(Score_Board.this);
                    pickerBGC.setColor(SettingObject.getPlayerHighlightColorInt());
                    pickerBGC.addColorObserver(new ColorObserver() {
                        @Override
                        public void updateColor(ObservableColor observableColor) {
                            System.out.println(observableColor.getColor());
                        }
                    });
                    builder.setTitle("Button Text Color")
                            .setView(pickerBGC)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // save result and refresh
                                    SettingObject.setPlayerHightlightColorInt(pickerBGC.getColor());
                                    Message message = new Message();
                                    // Set message type
                                    message.what = _aca.MESSAGE_UPDATE_BACKGROUND;
                                    // Send message to main thread Handler.
                                    State.updateUIHandler.sendMessage(message);

                                    Score_Board.this.setColors();

                                }
                            });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }
            });
        }
        {
            setButtonBidderHightlightColor.setLayoutParams(lp2);
            setButtonBidderHightlightColor.setX(20 * Constants.scaleX);
            setButtonBidderHightlightColor.setY(880 * Constants.scaleY);
            setButtonBidderHightlightColor.setText("Bidder Highlight");
            setButtonBidderHightlightColor.setPadding(5, 5, 5, 5);

            relativelayout.addView(setButtonBidderHightlightColor);
            setButtonBidderHightlightColor.setOnClickListener(new View.OnClickListener() {
                public void onClick(final View v) {
                    /*(new ColorPickerPopup.Builder(Score_Board.this)
                            .initialColor(SettingObject.getBidderHighlightColorInt()) // Set initial color
                            .enableBrightness(true) // Enable brightness slider or not
                            .enableAlpha(true) // Enable alpha slider or not
                            .okTitle("Choose")
                            .cancelTitle("Cancel")
                            .showIndicator(true)
                            .showValue(true)
                            .build()
                            .show(v, new ColorPickerPopup.ColorPickerObserver() {
                                @Override
                                public void onColorPicked(int color) {

                                    //v.setBackgroundColor(color);
                                    SettingObject.setBidderHighlightColorInt(color);
                                    Message message = new Message();
                                    // Set message type
                                    message.what = _aca.MESSAGE_UPDATE_BACKGROUND;
                                    // Send message to main thread Handler.
                                    _aca.updateUIHandler.sendMessage(message);

                                    Score_Board.this.setColors();
                                }

                                public void onColor(int color, boolean fromUser) {

                                }
                            });

                     */
                    AlertDialog.Builder builder = new AlertDialog.Builder(Score_Board.this);

                    final ColorPickerView pickerBGC = new ColorPickerView(Score_Board.this);
                    pickerBGC.setColor(SettingObject.getBidderHighlightColorInt());
                    pickerBGC.addColorObserver(new ColorObserver() {
                        @Override
                        public void updateColor(ObservableColor observableColor) {
                            System.out.println(observableColor.getColor());
                        }
                    });
                    builder.setTitle("Button Text Color")
                            .setView(pickerBGC)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // save result and refresh
                                    SettingObject.setBidderHighlightColorInt(pickerBGC.getColor());
                                    Message message = new Message();
                                    // Set message type
                                    message.what = _aca.MESSAGE_UPDATE_BACKGROUND;
                                    // Send message to main thread Handler.
                                    State.updateUIHandler.sendMessage(message);

                                    Score_Board.this.setColors();

                                }
                            });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }
            });
        }
        {
            setDealerArrowColor.setLayoutParams(lp2);
            setDealerArrowColor.setX(20 * Constants.scaleX);
            setDealerArrowColor.setY(980 * Constants.scaleY);
            setDealerArrowColor.setText("Dealer Indicator");
            setDealerArrowColor.setPadding(5, 5, 5, 5);

            relativelayout.addView(setDealerArrowColor);
            setDealerArrowColor.setOnClickListener(new View.OnClickListener() {
                public void onClick(final View v) {
                    /*new ColorPickerPopup.Builder(Score_Board.this)
                            .initialColor(SettingObject.getDealerIndicatorInt()) // Set initial color
                            .enableBrightness(true) // Enable brightness slider or not
                            .enableAlpha(true) // Enable alpha slider or not
                            .okTitle("Choose")
                            .cancelTitle("Cancel")
                            .showIndicator(true)
                            .showValue(true)
                            .build()
                            .show(v, new ColorPickerPopup.ColorPickerObserver() {
                                @Override
                                public void onColorPicked(int color) {

                                    //v.setBackgroundColor(color);
                                    SettingObject.setBDealerIndicatorInt(color);
                                    Message message = new Message();
                                    // Set message type
                                    message.what = _aca.MESSAGE_UPDATE_BACKGROUND;
                                    // Send message to main thread Handler.
                                    _aca.updateUIHandler.sendMessage(message);

                                    Score_Board.this.setColors();
                                }

                                public void onColor(int color, boolean fromUser) {

                                }
                            });

                     */
                    AlertDialog.Builder builder = new AlertDialog.Builder(Score_Board.this);

                    final ColorPickerView pickerBGC = new ColorPickerView(Score_Board.this);
                    pickerBGC.setColor(SettingObject.getDealerIndicatorInt());
                    pickerBGC.addColorObserver(new ColorObserver() {
                        @Override
                        public void updateColor(ObservableColor observableColor) {
                            System.out.println(observableColor.getColor());
                        }
                    });
                    builder.setTitle("Button Text Color")
                            .setView(pickerBGC)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // save result and refresh
                                    SettingObject.setBDealerIndicatorInt(pickerBGC.getColor());
                                    Message message = new Message();
                                    // Set message type
                                    message.what = _aca.MESSAGE_UPDATE_BACKGROUND;
                                    // Send message to main thread Handler.
                                    State.updateUIHandler.sendMessage(message);

                                    Score_Board.this.setColors();

                                }
                            });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }
            });
        }
        {
            resetColors.setLayoutParams(lp2);
            resetColors.setX(20 * Constants.scaleX);
            resetColors.setY(1080 * Constants.scaleY);
            resetColors.setText("Reset Colors");
            resetColors.setPadding(5, 5, 5, 5);

            relativelayout.addView(resetColors);
            resetColors.setOnClickListener(new View.OnClickListener() {
                public void onClick(final View v) {
                    SettingObject.resetColors();
                    Message message = new Message();
                    // Set message type
                    message.what = _aca.MESSAGE_UPDATE_BACKGROUND;
                    // Send message to main thread Handler.
                    State.updateUIHandler.sendMessage(message);

                    Score_Board.this.setColors();
                }
            });
        }
        setBackgroundColor.setLayoutParams(lp2);
        setBackgroundColor.setX(20*Constants.scaleX);
        setBackgroundColor.setY(280*Constants.scaleY);
        setBackgroundColor.setText("Background");
        setBackgroundColor.setPadding(5,5,5,5);

        relativelayout.addView(setBackgroundColor);
        setBackgroundColor.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                /*new ColorPickerPopup.Builder(Score_Board.this)
                        .initialColor(SettingObject.getBackgroundColorInt()) // Set initial color
                        .enableBrightness(true) // Enable brightness slider or not
                        .enableAlpha(true) // Enable alpha slider or not
                        .okTitle("Choose")
                        .cancelTitle("Cancel")
                        .showIndicator(true)
                        .showValue(true)
                        .build()
                        .show(v, new ColorPickerPopup.ColorPickerObserver() {
                            @Override
                            public void onColorPicked(int color) {

                                //v.setBackgroundColor(color);
                                SettingObject.setBackgroundColorInt(color);
                                Message message = new Message();
                                // Set message type
                                message.what = _aca.MESSAGE_UPDATE_BACKGROUND;
                                // Send message to main thread Handler.
                                _aca.updateUIHandler.sendMessage(message);

                                Score_Board.this.setColors();
                            }

                            public void onColor(int color, boolean fromUser) {

                            }
                        });

                 */
                AlertDialog.Builder builder = new AlertDialog.Builder(Score_Board.this);

                final ColorPickerView pickerBGC = new ColorPickerView(Score_Board.this);
                pickerBGC.setColor(SettingObject.getBackgroundColorInt());
                pickerBGC.addColorObserver(new ColorObserver() {
                    @Override
                    public void updateColor(ObservableColor observableColor) {
                        System.out.println(observableColor.getColor());
                    }
                });
                builder.setTitle("Button Text Color")
                        .setView(pickerBGC)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // save result and refresh
                                SettingObject.setBackgroundColorInt(pickerBGC.getColor());
                                Message message = new Message();
                                // Set message type
                                message.what = _aca.MESSAGE_UPDATE_BACKGROUND;
                                // Send message to main thread Handler.
                                State.updateUIHandler.sendMessage(message);

                                Score_Board.this.setColors();

                            }
                        });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });

        setButtonColor.setLayoutParams(lp2);
        setButtonColor.setX(20*Constants.scaleX);
        setButtonColor.setY(380*Constants.scaleY);
        setButtonColor.setText("Button");
        setButtonColor.setPadding(5,5,5,5);

        relativelayout.addView(setButtonColor);
        setButtonColor.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                /*
                new ColorPickerPopup.Builder(Score_Board.this)
                        .initialColor(SettingObject.getButtonColorInt()) // Set initial color
                        .enableBrightness(true) // Enable brightness slider or not
                        .enableAlpha(true) // Enable alpha slider or not
                        .okTitle("Choose")
                        .cancelTitle("Cancel")
                        .showIndicator(true)
                        .showValue(true)
                        .build()
                        .show(v, new ColorPickerPopup.ColorPickerObserver() {
                            @Override
                            public void onColorPicked(int color) {
                                System.out.println("Color Picked: "+color);
                                //v.setBackgroundColor(color);
                                SettingObject.setButtonColorInt(color);
                                Message message = new Message();
                                // Set message type
                                message.what = _aca.MESSAGE_UPDATE_BACKGROUND;
                                // Send message to main thread Handler.
                                _aca.updateUIHandler.sendMessage(message);

                                Score_Board.this.setColors();
                            }

                            public void onColor(int color, boolean fromUser) {
                                System.out.println("Color thought: "+color);

                            }

                        });

                 */
                AlertDialog.Builder builder = new AlertDialog.Builder(Score_Board.this);

                final ColorPickerView pickerBC = new ColorPickerView(Score_Board.this);
                pickerBC.setColor(SettingObject.getButtonColorInt());
                pickerBC.addColorObserver(new ColorObserver() {
                    @Override
                    public void updateColor(ObservableColor observableColor) {
                        System.out.println(observableColor.getColor());
                    }
                });
                builder.setTitle("Button Text Color")
                        .setView(pickerBC)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // save result and refresh
                                SettingObject.setButtonColorInt(pickerBC.getColor());
                                Message message = new Message();
                                // Set message type
                                message.what = _aca.MESSAGE_UPDATE_BACKGROUND;
                                // Send message to main thread Handler.
                                State.updateUIHandler.sendMessage(message);

                                Score_Board.this.setColors();

                            }
                        });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });

        setButtonTextColor.setLayoutParams(lp2);
        setButtonTextColor.setX(20 * Constants.scaleX);
        setButtonTextColor.setY(480 * Constants.scaleY);
        setButtonTextColor.setText("Button Text");
        setButtonTextColor.setPadding(5, 5, 5, 5);

        relativelayout.addView(setButtonTextColor);
        setButtonTextColor.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Score_Board.this);

                final ColorPickerView pickerSBTC = new ColorPickerView(Score_Board.this);
                pickerSBTC.setColor(SettingObject.getButtonTextColorInt());
                pickerSBTC.addColorObserver(new ColorObserver() {
                    @Override
                    public void updateColor(ObservableColor observableColor) {
                        System.out.println(observableColor.getColor());
                    }
                });
                builder.setTitle("Button Text Color")
                        .setView(pickerSBTC)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // save result and refresh
                                System.out.println("H1");
                                SettingObject.setButtonTextColorInt(pickerSBTC.getColor());
                                Message message = new Message();
                                // Set message type
                                message.what = _aca.MESSAGE_UPDATE_BACKGROUND;
                                // Send message to main thread Handler.
                                State.updateUIHandler.sendMessage(message);

                                Score_Board.this.setColors();

                            }
                        });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
                /*
                new ColorPickerPopup.Builder(Score_Board.this)
                        .initialColor(SettingObject.getButtonTextColorInt()) // Set initial color
                        .enableBrightness(true) // Enable brightness slider or not
                        .enableAlpha(true) // Enable alpha slider or not
                        .okTitle("Choose")
                        .cancelTitle("Cancel")
                        .showIndicator(true)
                        .showValue(true)
                        .build()
                        .show(v, new ColorPickerPopup.ColorPickerObserver() {
                            @Override
                            public void onColorPicked(int color) {

                                //v.setBackgroundColor(color);
                                System.out.println("Button Text Color: "+color);
                                SettingObject.setButtonTextColorInt(color);
                                Message message = new Message();
                                // Set message type
                                message.what = _aca.MESSAGE_UPDATE_BACKGROUND;
                                // Send message to main thread Handler.
                                _aca.updateUIHandler.sendMessage(message);

                                Score_Board.this.setColors();
                            }

                            public void onColor(int color, boolean fromUser) {

                            }
                        });

                 */
            }
        });


        setColors();



    }

    public void setColors()
    {
        scoreBoard.setBackgroundColor(SettingObject.getButtonColorInt());
        changeName.setBackgroundColor(SettingObject.getButtonColorInt());
        setBackgroundColor.setBackgroundColor(SettingObject.getButtonColorInt());
        setButtonColor.setBackgroundColor(SettingObject.getButtonColorInt());
        setButtonBidderHightlightColor.setBackgroundColor(SettingObject.getBidderHighlightColorInt());
        setButtonPlayerHighlightColor.setBackgroundColor(SettingObject.getPlayerHighlightColorInt());
        setButtonTextColor.setBackgroundColor(SettingObject.getButtonColorInt());
        setButtonHandLeaderColor.setBackgroundColor(SettingObject.getButtonColorInt());
        setButtonOtherTextColor.setBackgroundColor(SettingObject.getButtonColorInt());
        setDealerArrowColor.setBackgroundColor(SettingObject.getButtonColorInt());
        resetColors.setBackgroundColor(SettingObject.getButtonColorInt());

        tvhl.setTextColor(SettingObject.getTextColorInt());
        tvlh.setTextColor(SettingObject.getTextColorInt());
        tvtr.setTextColor(SettingObject.getTextColorInt());
        tvtl.setTextColor(SettingObject.getTextColorInt());
        deb1.setTextColor(SettingObject.getTextColorInt());
        deb2.setTextColor(SettingObject.getTextColorInt());
        setButtonColor.setTextColor(SettingObject.getButtonTextColorInt());
        setBackgroundColor.setTextColor(SettingObject.getButtonTextColorInt());
        scoreBoard.setTextColor(SettingObject.getButtonTextColorInt());
        changeName.setTextColor(SettingObject.getButtonTextColorInt());
        setButtonBidderHightlightColor.setTextColor(SettingObject.getTextColorInt());
        setButtonPlayerHighlightColor.setTextColor(SettingObject.getTextColorInt());
        setButtonTextColor.setTextColor(SettingObject.getButtonTextColorInt());
        setButtonOtherTextColor.setTextColor(SettingObject.getButtonTextColorInt());
        setButtonHandLeaderColor.setTextColor(SettingObject.getButtonTextColorInt());
        setDealerArrowColor.setTextColor(SettingObject.getButtonTextColorInt());
        resetColors.setTextColor(SettingObject.getButtonTextColorInt());
        try {
            RelativeLayout relativelayout = (RelativeLayout)findViewById(R.id.RL_ScoreBoard);
            relativelayout.setBackgroundColor(SettingObject.getBackgroundColorInt());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}

package com.gmail.dharris.pattonohheck;

import android.os.Message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.dharris.pattonohheck.client.POHRequest;
import com.gmail.dharris.pattonohheck.client.POHResponse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkHandler implements Runnable {

    private MainActivity _aca= null;
    private SettingObject _settingObject;
    private Socket socket            = null;
    private DataInputStream  input   = null;
    private DataOutputStream out     = null;
    private ObjectMapper mapper = new ObjectMapper();
    public void close()
    {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket=null;
    }
    public boolean isOpen()
    {
        if (socket == null)
            return false;
        return !socket.isClosed();
    }
    public void playCard(Card card) throws Exception
    {
        setupConnection();
        POHRequest request = new POHRequest();
        request.requestType="PLAYCARD";
        request.playerName=_settingObject.getPlayerName();
        request.cardPlayed=card;
        try {
            String aString = mapper.writeValueAsString(request);
            out.writeUTF(aString);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void requestDeal() throws Exception
    {
        setupConnection();
        POHRequest request = new POHRequest();
        request.requestType="DEAL";
        request.playerName=_settingObject.getPlayerName();
        try {
            String aString = mapper.writeValueAsString(request);
            out.writeUTF(aString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void requestStart(int count) throws Exception
    {
        setupConnection();
        POHRequest request = new POHRequest();
        request.requestType="START";
        request.rounds=count;
        request.playerName=_settingObject.getPlayerName();
        try {
            String aString = mapper.writeValueAsString(request);
            out.writeUTF(aString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendBid(int count) throws Exception
    {
        setupConnection();
        POHRequest request = new POHRequest();
        request.requestType="BID";
        request.bid=count;
        request.playerName=_settingObject.getPlayerName();
        try {
            String aString = mapper.writeValueAsString(request);
            out.writeUTF(aString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestJoin() throws Exception
    {
        setupConnection();
        POHRequest request = new POHRequest();
        request.requestType="JOINGAME";
        request.playerName=_settingObject.getPlayerName();
        try {
            String aString = mapper.writeValueAsString(request);
            out.writeUTF(aString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEmoji(String type) throws Exception
    {
        setupConnection();
        POHRequest request = new POHRequest();
        request.requestType="E_"+type;
        request.playerName=_settingObject.getPlayerName();
        try {
            String aString = mapper.writeValueAsString(request);
            out.writeUTF(aString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public NetworkHandler(MainActivity aca, SettingObject settingObject)
    {
        _aca=aca;
        _settingObject=settingObject;
        // establish a connection


    }
    private synchronized void setupConnection()
    {
        if (socket == null) {
            try {

                if (SettingObject.isDebug())
                {
                    socket = new Socket("192.168.1.49", 57829);
                }
                else {
                    socket = new Socket("thunderrealms.hopto.org", 57829);
                }

                System.out.println("Connected");

                // takes input from terminal
                input = new DataInputStream(socket.getInputStream());

                // sends output to the socket
                out = new DataOutputStream(socket.getOutputStream());
            } catch (UnknownHostException u) {
                System.out.println(u);
            } catch (IOException i) {
                System.out.println(i);
            }
        }
    }

    @Override
    public void run() {
        setupConnection();


            while(true) {
                try {

                    String line = input.readUTF();
            System.out.println("Network in: "+line);
            try {
                POHResponse response = mapper.readValue(line, POHResponse.class);
                if (response.ResponseType != null && response.ResponseType.startsWith("E_")) {
                    Message message = new Message();
                    // Set message type
                    message.what = _aca.MESSAGE_EMOJI;
                    message.obj = response;
                    // Send message to main thread Handler.
                    State.updateUIHandler.sendMessage(message);
                }

                if ("DEALT".equals(response.ResponseType)) {
                    Message message = new Message();
                    // Set message type
                    message.what = _aca.MESSAGE_SHOW_DEAL;
                    message.obj = response;
                    // Send message to main thread Handler.
                    State.updateUIHandler.sendMessage(message);
                }
                if ("GAMEOVER".equals(response.ResponseType)) {
                    Message message = new Message();
                    // Set message type
                    message.what = _aca.MESSAGE_GAME_OVER;
                    message.obj = response;
                    // Send message to main thread Handler.
                    State.updateUIHandler.sendMessage(message);
                }
                if ("CARDPLAYED".equals(response.ResponseType))
                {
                    Message message = new Message();
                    // Set message type.
                    message.what = _aca.MESSAGE_CARD_PLAYED;
                    message.obj = response;
                    // Send message to main thread Handler.
                    State.updateUIHandler.sendMessage(message);

                }
                if ("GAMEPLAYERS".equals(response.ResponseType))
                {
                    Message message = new Message();
                    // Set message type.
                    message.what = _aca.MESSAGE_GAME_PLAYERS;
                    message.obj = response;
                    // Send message to main thread Handler.
                    State.updateUIHandler.sendMessage(message);

                }
                if ("SCOREUPDATE".equals(response.ResponseType))
                {
                    Message message = new Message();
                    // Set message type.
                    message.what = _aca.MESSAGE_SCORE_UPDATE;
                    message.obj = response;
                    // Send message to main thread Handler.
                    State.updateUIHandler.sendMessage(message);

                }
                if ("STARTGAME".equals(response.ResponseType))
                {
                    Message message = new Message();
                    // Set message type.
                    message.what = _aca.MESSAGE_GAME_STARTED;
                    message.obj = response;
                    // Send message to main thread Handler.
                    State.updateUIHandler.sendMessage(message);

                }
                if ("NEXTPLAY".equals(response.ResponseType))
                {
                    Message message = new Message();
                    // Set message type.
                    message.what = _aca.MESSAGE_NEXT_PLAY;
                    message.obj = response;
                    // Send message to main thread Handler.
                    State.updateUIHandler.sendMessage(message);

                }
                if ("RESUME".equals(response.ResponseType))
                {
                    Message message = new Message();
                    // Set message type.
                    message.what = _aca.MESSAGE_RESUME;
                    message.obj = response;
                    // Send message to main thread Handler.
                    State.updateUIHandler.sendMessage(message);

                }
                if ("BID".equals(response.ResponseType))
                {
                    Message message = new Message();
                    // Set message type.
                    message.what = _aca.MESSAGE_BID;
                    message.obj = response;
                    // Send message to main thread Handler.
                    State.updateUIHandler.sendMessage(message);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
        }

    }
}

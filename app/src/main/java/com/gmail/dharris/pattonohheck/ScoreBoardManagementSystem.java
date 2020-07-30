package com.gmail.dharris.pattonohheck;

public class ScoreBoardManagementSystem {
    public static class SBMSRound {
        private int dealt;
        private String trump;
        private String dealerId;

        public String getDealerId() {
            return dealerId;
        }
        public void setDealerId(String dealerId) {
            this.dealerId = dealerId;
        }
        private java.util.ArrayList<SBMSPlayerRound> playerRound = new java.util.ArrayList<SBMSPlayerRound>();
        private java.util.ArrayList<SBMSTrick> tricks = new java.util.ArrayList<ScoreBoardManagementSystem.SBMSTrick>();

        public java.util.ArrayList<SBMSTrick> getTricks() {
            return tricks;
        }
        public void setTricks(java.util.ArrayList<SBMSTrick> tricks) {
            this.tricks = tricks;
        }
        public int getDealt() {
            return dealt;
        }
        public void setDealt(int dealt) {
            this.dealt = dealt;
        }
        public String getTrump() {
            return trump;
        }
        public void setTrump(String trump) {
            this.trump = trump;
        }
        public java.util.ArrayList<SBMSPlayerRound> getPlayerRound() {
            return playerRound;
        }
        public void setPlayerRound(java.util.ArrayList<SBMSPlayerRound> playerRound) {
            this.playerRound = playerRound;
        }


    }
    public static class SMBSPlayerCard
    {
        private String playerID;
        private String card;
        public String getPlayerID() {
            return playerID;
        }
        public void setPlayerID(String playerID) {
            this.playerID = playerID;
        }
        public String getCard() {
            return card;
        }
        public void setCard(String card) {
            this.card = card;
        }


    }
    public static class SBMSTrick
    {
        private String winningCard;
        private java.util.ArrayList<SMBSPlayerCard> cards = new java.util.ArrayList<ScoreBoardManagementSystem.SMBSPlayerCard>();
        public String getWinningCard() {
            return winningCard;
        }
        public void setWinningCard(String winningCard) {
            this.winningCard = winningCard;
        }
        public java.util.ArrayList<SMBSPlayerCard> getCards() {
            return cards;
        }
        public void setCards(java.util.ArrayList<SMBSPlayerCard> cards) {
            this.cards = cards;
        }

    }
    public static class SBMSPlayerRound {
        String playerId;
        int bid;
        int taken;
        int score;
        java.util.ArrayList<String> dealtCards=new java.util.ArrayList<String>();

        public java.util.ArrayList<String> getDealtCards() {
            return dealtCards;
        }
        public void setCards(java.util.ArrayList<String> cards) {
            this.dealtCards = cards;
        }
        public String getPlayerId() {
            return playerId;
        }
        public void setPlayerId(String playerName) {
            this.playerId = playerName;
        }
        public int getBid() {
            return bid;
        }
        public void setBid(int bid) {
            this.bid = bid;
        }
        public int getTaken() {
            return taken;
        }
        public void setTaken(int taken) {
            this.taken = taken;
        }
        public int getScore() {
            return score;
        }
        public void setScore(int score) {
            this.score = score;
        }

    }

    public static class SBMSPlayer
    {
        private String playerId;
        private String playerName;
        public String getPlayerId() {
            return playerId;
        }
        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }
        public String getPlayerName() {
            return playerName;
        }
        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

    }
    public static class SBMSGame
    {
        private String gameId;
        private java.util.ArrayList<SBMSPlayer> players = new java.util.ArrayList<SBMSPlayer>();
        private int totalRounds;
        private java.util.ArrayList<SBMSRound> rounds = new java.util.ArrayList<SBMSRound>();
        private java.util.Date gameStartTime;
        private java.util.Date gameEndTime;

        public String getGameId() {
            return gameId;
        }
        public void setGameId(String gameId) {
            this.gameId = gameId;
        }
        public java.util.ArrayList<SBMSPlayer> getPlayers() {
            return players;
        }
        public void setPlayers(java.util.ArrayList<SBMSPlayer> players) {
            this.players = players;
        }
        public java.util.ArrayList<SBMSRound> getRounds() {
            return rounds;
        }
        public void setRounds(java.util.ArrayList<SBMSRound> r) {
            this.rounds = r;
        }
        public int getTotalRounds() {
            return totalRounds;
        }
        public void setTotalRounds(int totalRounds) {
            this.totalRounds = totalRounds;
        }
        public java.util.Date getGameStartTime() {
            return gameStartTime;
        }
        public void setGameStartTime(java.util.Date gameStartTime) {
            this.gameStartTime = gameStartTime;
        }
        public java.util.Date getGameEndTime() {
            return gameEndTime;
        }
        public void setGameEndTime(java.util.Date gameEndTime) {
            this.gameEndTime = gameEndTime;
        }
    }
}

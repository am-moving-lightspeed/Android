package com.oaoaoa.battleships.models;


import java.util.Date;



public class Game {

    private String firstUser;
    private int    firstUserResult;
    private String secondUser;
    private int    secondUserResult;
    private Date   gameDate;


    public Game(String firstUser,
                String firstMap,
                String secondUser,
                String secondMap) {

        this.firstUser        = firstUser;
        this.firstUserResult  = 0;
        this.secondUser       = secondUser;
        this.secondUserResult = 0;
    }


    public Game(String firstUser,
                long firstUserScore,
                String secondUser,
                long secondUserScore,
                Date gameDate) {

        this.firstUser        = firstUser;
        this.firstUserResult  = (int) firstUserScore;
        this.secondUser       = secondUser;
        this.secondUserResult = (int) secondUserScore;
        this.gameDate         = gameDate;
    }


    public String getFirstUser() {

        return firstUser;
    }


    public int getFirstUserResult() {

        return firstUserResult;
    }


    public String getSecondUser() {

        return secondUser;
    }


    public int getSecondUserResult() {

        return secondUserResult;
    }


    public Date getGameDate() {

        return gameDate;
    }

}

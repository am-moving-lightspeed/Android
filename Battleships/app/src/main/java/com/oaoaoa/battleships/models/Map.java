package com.oaoaoa.battleships.models;


import com.oaoaoa.battleships.models.states.Cell;

import java.io.Serializable;



public class Map implements Serializable {

    private final Cell[][] mMap;
    private       int      mShipsLeft = 20;


    public Map() {

        mMap = new Cell[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mMap[i][j] = Cell.EMPTY;
            }
        }
    }


    public Map(Map map) {

        mMap = new Cell[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mMap[i][j] = map.getCell(i, j);
            }
        }

        mShipsLeft = map.getShipsLeft();
    }


    public Cell getCell(int i, int j) {

        return mMap[i][j];
    }


    public void setCell(int i, int j, Cell type) {

        this.mMap[i][j] = type;
    }


    public int getShipsLeft() {

        return mShipsLeft;
    }


    public void decreaseShips() {

        if (mShipsLeft > 0) {
            mShipsLeft--;
        }
    }

}

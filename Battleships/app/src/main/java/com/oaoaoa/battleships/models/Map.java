package com.oaoaoa.battleships.models;


public class Map {

    private Cell[][] mMap;


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
    }


    public Cell getCell(int i, int j) {

        return mMap[i][j];
    }


    public void setCell(int i, int j, Cell type) {

        this.mMap[i][j] = type;
    }

}

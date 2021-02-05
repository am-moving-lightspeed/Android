package com.oaoaoa.battleships.models;


public enum Cell {

    EMPTY,
    FILLED,

    // In-game only
    MISS,
    HIT,

    // Editor only
    LOCKED

}

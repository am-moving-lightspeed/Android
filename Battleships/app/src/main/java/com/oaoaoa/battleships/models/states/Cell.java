package com.oaoaoa.battleships.models.states;


import java.io.Serializable;



public enum Cell implements Serializable {

    EMPTY,
    FILLED,

    // In-game only
    PENDING,
    MISS,
    HIT,

    // Editor only
    LOCKED,
    OCCUPIED

}

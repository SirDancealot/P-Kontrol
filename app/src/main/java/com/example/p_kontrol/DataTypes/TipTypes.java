package com.example.p_kontrol.DataTypes;

/**
 * TipTypes is an Enumeration of all the categories of tips in the system.
 *
 * */
public enum TipTypes {

    free(1),paid(2), alarm(3);

    final int value;

    TipTypes(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}

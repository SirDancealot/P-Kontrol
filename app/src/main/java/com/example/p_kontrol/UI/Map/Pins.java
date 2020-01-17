package com.example.p_kontrol.UI.Map;


public enum Pins{
    free("map_tip_pin_paid"),
    paid("map_tip_pin_regular"),
    alarm("map_tip_pin_alarm");

    private final String name;

    Pins(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}




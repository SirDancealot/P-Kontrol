package com.example.p_kontrol.UI.Map;


public enum Pins{
    free("map_tip_pin_regular"),
    paid("map_tip_pin_paid"),
    alarm("map_tip_pin_alarm"),
    pVagt("map_pvagt_pin_alarm"),
    pVagtOld("map_pvagt_pin_alarmold"),
    parkingSpot("map_parkingspot");

    private final String name;

    Pins(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}




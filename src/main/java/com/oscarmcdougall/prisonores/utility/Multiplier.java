package com.oscarmcdougall.prisonores.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Multiplier {

    @Getter private double multiplierAmount;
    private long timeExpires;

    public long getTimeExpires() {
        return timeExpires;
    }
}

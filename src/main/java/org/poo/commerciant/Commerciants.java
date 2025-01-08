package org.poo.commerciant;

import lombok.Getter;

import java.util.List;

@Getter
public class Commerciants {
    private List<Commerciant> commerciants;

    public Commerciant getCommerciantByName(String name) {
        for (Commerciant commerciant : commerciants) {
            if (commerciant.getCommerciant().equals(name)) {
                return commerciant;
            }
        }

        return null;
    }

    public String getCommerciantTypeByName(String name) {
        for (Commerciant commerciant : commerciants) {
            if (commerciant.getCommerciant().equals(name)) {
                return commerciant.getType();
            }
        }

        return null;
    }
}

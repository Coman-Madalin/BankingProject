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

    public Commerciant getCommerciantByIBAN(String iban) {
        for (Commerciant commerciant : commerciants) {
            if (commerciant.getAccount().equals(iban)) {
                return commerciant;
            }
        }

        return null;
    }
}

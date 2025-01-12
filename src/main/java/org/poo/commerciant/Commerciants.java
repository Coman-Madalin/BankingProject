package org.poo.commerciant;

import lombok.Getter;

import java.util.List;

/**
 * The type Commerciants.
 */
@Getter
public class Commerciants {
    private List<Commerciant> commerciants;

    /**
     * Gets commerciant by name.
     *
     * @param name the name
     * @return the commerciant by name
     */
    public Commerciant getCommerciantByName(String name) {
        for (Commerciant commerciant : commerciants) {
            if (commerciant.getCommerciant().equals(name)) {
                return commerciant;
            }
        }

        return null;
    }

    /**
     * Gets commerciant by iban.
     *
     * @param iban the iban
     * @return the commerciant by iban
     */
    public Commerciant getCommerciantByIBAN(String iban) {
        for (Commerciant commerciant : commerciants) {
            if (commerciant.getAccount().equals(iban)) {
                return commerciant;
            }
        }

        return null;
    }
}

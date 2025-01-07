package org.poo.commerciant;

import lombok.Getter;

/**
 * The type Commerciant.
 */
@Getter
public final class Commerciant {
    private int id;
    private String commerciant;
    private String account;
    private String type;
    /**
     * DON'T use this, this is only for deserialization purposes, use cashback field instead
     */
    private String cashbackStrategy;
    private CashbackPlans cashback;


}

package org.poo.exchange;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Exchange.
 */
@Data
@NoArgsConstructor
public final class Exchange {
    private String from;
    private String to;
    private double rate;
}

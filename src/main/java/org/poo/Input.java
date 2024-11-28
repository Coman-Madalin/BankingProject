package org.poo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.poo.command.BaseCommand;
import org.poo.commerciant.Commerciant;
import org.poo.exchange.Exchange;
import org.poo.user.User;

@Data
@NoArgsConstructor
public final class Input {
    private User[] users;
    private Exchange[] exchangeRates;
    private BaseCommand[] commands;
    private Commerciant[] commerciants;
}

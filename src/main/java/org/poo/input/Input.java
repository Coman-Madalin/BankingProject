package org.poo.input;

import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.poo.command.BaseCommand;
import org.poo.commerciant.Commerciant;
import org.poo.exchange.Exchange;
import org.poo.json.JsonUtils;
import org.poo.user.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public final class Input {
//    private static Input instance;

    private User[] users;
    private Exchanges exchanges;
    private BaseCommand[] commands;
    private Commerciant[] commerciants;

//    public Input(){
//        instance = this;
//    }

    public void executeAllCommands() {
        exchanges.makeCommonCurrencyExchange();
//        Gson gson = new Gson();
        for (final BaseCommand command : commands) {
            command.execute(this);
        }
    }

    public void gamesToJson(final String filePath1) throws IOException {
        try (final FileWriter fileWriter = new FileWriter(filePath1)) {
            final Gson gson = JsonUtils.getGson();
            gson.toJson(commands, fileWriter);
        }
    }



//    // TODO: convert by using an auxiliary currency when there is no direct conversion
//    public double exchangeCurrency(final double amount, final String fromCurrency, final String toCurrency) {
//        for (final Exchange exchangeRate : this.getExchangeRates()) {
//            if (exchangeRate.getFrom().equals(fromCurrency)) {
//                if (exchangeRate.getTo().equals(toCurrency)) {
//                    return exchangeRate.getRate() * amount;
//                }
//            }
//
//            if (exchangeRate.getFrom().equals(toCurrency)) {
//                if (exchangeRate.getTo().equals(fromCurrency)) {
//                    return exchangeRate.getRate() / amount;
//                }
//            }
//        }
//        return -100000;
//    }
}

package org.poo.input;

import org.poo.exchange.Exchange;

import java.util.ArrayList;
import java.util.List;

public class Exchanges {
    private List<Exchange> exchangeRates;

    public void makeCommonCurrencyExchange() {
        final String superiorCurrency = exchangeRates.getFirst().getTo();
        final List<Exchange> updatedExchanges = new ArrayList<>();
        updatedExchanges.add(exchangeRates.getFirst());
        exchangeRates.removeFirst();

        while (!exchangeRates.isEmpty()) {
            for (int i = 0; i < exchangeRates.size(); i++) {
                final Exchange currentExchange = exchangeRates.get(i);

                for (int j = 0; j < updatedExchanges.size(); j++) {
                    final Exchange updatedExchange = updatedExchanges.get(j);

                    if (currentExchange.getTo().equals(superiorCurrency)) {
                        updatedExchanges.add(currentExchange);
                        exchangeRates.remove(i);
                        continue;
                    }

                    if (currentExchange.getFrom().equals(superiorCurrency)) {
                        final Exchange exchangeToAdd = new Exchange();
                        exchangeToAdd.setFrom(currentExchange.getTo());

                        exchangeToAdd.setTo(superiorCurrency);
                        exchangeToAdd.setRate(1 / currentExchange.getRate());

                        updatedExchanges.add(exchangeToAdd);
                        exchangeRates.remove(i);
                        continue;
                    }

                    if (currentExchange.getFrom().equals(updatedExchange.getFrom())) {
                        exchangeRates.remove(i);
                        continue;
                    }

                    if (currentExchange.getTo().equals(updatedExchange.getFrom())) {
                        final double updatedRate = currentExchange.getRate() * updatedExchange.getRate();
                        final Exchange exchangeToAdd = new Exchange();
                        exchangeToAdd.setFrom(currentExchange.getFrom());

                        exchangeToAdd.setTo(superiorCurrency);
                        exchangeToAdd.setRate(updatedRate);

                        updatedExchanges.add(exchangeToAdd);
                        exchangeRates.remove(i);
                        continue;
                    }

                    if (currentExchange.getFrom().equals(updatedExchange.getFrom())) {
                        final double updatedRate =
                                currentExchange.getRate() / updatedExchange.getRate();
                        final Exchange exchangeToAdd = new Exchange();
                        exchangeToAdd.setFrom(currentExchange.getTo());

                        exchangeToAdd.setTo(superiorCurrency);
                        exchangeToAdd.setRate(updatedRate);

                        updatedExchanges.add(exchangeToAdd);
                        exchangeRates.remove(i);
                        continue;

                    }

                }
            }
        }

        exchangeRates = updatedExchanges;
    }
}

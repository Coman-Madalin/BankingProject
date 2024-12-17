package org.poo.input;

import org.poo.exchange.Exchange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Exchanges.
 */
public class Exchanges {
    /**
     * Do NOT use, it gets consumed after calling makeCommonCurrencyExchange
     */
    private List<Exchange> exchangeRates;
    private Map<String, Double> exchangeMap;
    private String superiorCurrency;

    /**
     * Make common currency exchange.
     */
    public void makeCommonCurrencyExchange() {
        if (exchangeRates.isEmpty()) {
            return;
        }

        superiorCurrency = exchangeRates.getFirst().getTo();
        exchangeMap = new HashMap<>();
        exchangeMap.put(exchangeRates.getFirst().getFrom(), exchangeRates.getFirst().getRate());
        exchangeRates.removeFirst();

        while (!exchangeRates.isEmpty()) {
            for (int i = 0; i < exchangeRates.size(); i++) {
                final Exchange currentExchange = exchangeRates.get(i);
                if (currentExchange.getTo().equals(superiorCurrency)) {
                    exchangeMap.put(currentExchange.getFrom(), currentExchange.getRate());
                    exchangeRates.remove(i);
                    continue;
                }

                if (currentExchange.getFrom().equals(superiorCurrency)) {
                    exchangeMap.put(currentExchange.getTo(), 1 / currentExchange.getRate());
                    exchangeRates.remove(i);
                    continue;
                }

                if (exchangeMap.containsKey(currentExchange.getTo())) {
                    exchangeMap.put(currentExchange.getFrom(),
                            currentExchange.getRate() * exchangeMap.get(currentExchange.getTo()));
                    exchangeRates.remove(i);
                    continue;
                }

                if (exchangeMap.containsKey(currentExchange.getFrom())) {
                    exchangeMap.put(currentExchange.getTo(),
                            exchangeMap.get(currentExchange.getFrom()) / currentExchange.getRate());
                    exchangeRates.remove(i);
                }
            }
        }
    }

    /**
     * Convert currency double.
     *
     * @param amount         the amount
     * @param actualCurrency the actual currency
     * @param wantedCurrency the wanted currency
     * @return the double
     */
    public double convertCurrency(final double amount, final String actualCurrency,
                                  final String wantedCurrency) {
        if (actualCurrency.equals(wantedCurrency)) {
            return amount;
        }

        if (actualCurrency.equals(superiorCurrency)) {
            final double rate = exchangeMap.get(wantedCurrency);
            return amount / rate;
        }
        if (wantedCurrency.equals(superiorCurrency)) {
            final double rate = exchangeMap.get(actualCurrency);
            return amount * rate;
        }

        final double actualToCommonRate = exchangeMap.get(actualCurrency);
        final double commonToWantedRate = exchangeMap.get(wantedCurrency);
        return amount * actualToCommonRate / commonToWantedRate;
    }
}

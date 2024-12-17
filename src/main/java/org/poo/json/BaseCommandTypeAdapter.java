package org.poo.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.poo.command.BaseCommand;
import org.poo.command.specific.AddAccount;
import org.poo.command.specific.AddFunds;
import org.poo.command.specific.CreateCard;
import org.poo.command.specific.PrintUsers;
import org.poo.command.specific.DeleteAccount;
import org.poo.command.specific.CreateOneTimeCard;
import org.poo.command.specific.DeleteCard;
import org.poo.command.specific.SetMinimumBalance;
import org.poo.command.specific.PayOnline;
import org.poo.command.specific.SendMoney;
import org.poo.command.specific.SetAlias;
import org.poo.command.specific.PrintTransactions;
import org.poo.command.specific.CheckCardStatus;
import org.poo.command.specific.ChangeInterestRate;
import org.poo.command.specific.SplitPayment;
import org.poo.command.specific.Report;
import org.poo.command.specific.SpendingsReport;
import org.poo.command.specific.AddInterest;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;

/**
 * The type Base command type adapter.
 */
public final class BaseCommandTypeAdapter extends TypeAdapter<BaseCommand> {

    private static final Map<String, Class<?>> NAME_TO_COMMAND_CLASS = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("printUsers", PrintUsers.class),
            new AbstractMap.SimpleEntry<>("addAccount", AddAccount.class),
            new AbstractMap.SimpleEntry<>("createCard", CreateCard.class),
            new AbstractMap.SimpleEntry<>("addFunds", AddFunds.class),
            new AbstractMap.SimpleEntry<>("deleteAccount", DeleteAccount.class),
            new AbstractMap.SimpleEntry<>("createOneTimeCard", CreateOneTimeCard.class),
            new AbstractMap.SimpleEntry<>("deleteCard", DeleteCard.class),
            new AbstractMap.SimpleEntry<>("setMinimumBalance", SetMinimumBalance.class),
            new AbstractMap.SimpleEntry<>("payOnline", PayOnline.class),
            new AbstractMap.SimpleEntry<>("sendMoney", SendMoney.class),
            new AbstractMap.SimpleEntry<>("setAlias", SetAlias.class),
            new AbstractMap.SimpleEntry<>("printTransactions", PrintTransactions.class),
            new AbstractMap.SimpleEntry<>("checkCardStatus", CheckCardStatus.class),
            new AbstractMap.SimpleEntry<>("changeInterestRate", ChangeInterestRate.class),
            new AbstractMap.SimpleEntry<>("splitPayment", SplitPayment.class),
            new AbstractMap.SimpleEntry<>("report", Report.class),
            new AbstractMap.SimpleEntry<>("spendingsReport", SpendingsReport.class),
            new AbstractMap.SimpleEntry<>("addInterest", AddInterest.class)
    );

    @Override
    public void write(final JsonWriter out, final BaseCommand value) throws IOException {
        out.beginObject();
        out.name("command");
        out.value(value.getCommand());

        out.name("timestamp");
        out.value(value.getTimestamp());

        final JsonElement outputJsonElement = JsonParser.parseString(value.getOutput());

        out.name("output");
        final Gson gson = JsonUtils.getGSON();
        gson.toJson(outputJsonElement, out);

        out.endObject();
    }

    @Override
    public BaseCommand read(final JsonReader in) {
        final JsonObject jsonObject = JsonParser.parseReader(in).getAsJsonObject();
        final String commandName = jsonObject.getAsJsonPrimitive("command").getAsString();
        final Class<?> clazz = NAME_TO_COMMAND_CLASS.get(commandName);

        if (clazz == null) {
            throw new JsonParseException("Unknown command type: " + commandName);
        }

        return (BaseCommand) new Gson().fromJson(jsonObject, clazz);
    }
}

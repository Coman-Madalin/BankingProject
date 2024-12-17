package org.poo.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;

import org.poo.command.BaseCommand;
import org.poo.command.specific.AddAccount;
import org.poo.command.specific.AddFunds;
import org.poo.command.specific.CreateCard;
import org.poo.command.specific.PrintUsers;
import org.poo.command.specific.DeleteAccount;
import org.poo.command.specific.CreateOneTimeCard;
import org.poo.command.specific.DeleteCard;
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

import org.poo.json.serializers.AccountSerializer;
import org.poo.json.serializers.UserSerializer;
import org.poo.json.serializers.CardSerializer;
import org.poo.json.serializers.CommandArraySerializer;
import org.poo.json.serializers.SplitTransactionSerializer;

import org.poo.json.deserializers.InputDeserializer;

import org.poo.input.Input;
import org.poo.transactions.specific.SplitTransaction;
import org.poo.user.Account;
import org.poo.user.Card;
import org.poo.user.User;

/**
 * The type Json utils.
 */
public class JsonUtils {
    @Getter
    private static final Gson GSON;

    private static final Class<?>[] COMMAND_SUBCLASSES = {
            BaseCommand.class,
            AddAccount.class,
            AddFunds.class,
            CreateCard.class,
            PrintUsers.class,
            DeleteAccount.class,
            CreateOneTimeCard.class,
            DeleteCard.class,
            PayOnline.class,
            SendMoney.class,
            SetAlias.class,
            PrintTransactions.class,
            CheckCardStatus.class,
            ChangeInterestRate.class,
            SplitPayment.class,
            Report.class,
            SpendingsReport.class,
            AddInterest.class
    };

    static {
        final GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(BaseCommand[].class, new CommandArraySerializer())
                .registerTypeAdapter(Input.class, new InputDeserializer())
                .registerTypeAdapter(Account.class, new AccountSerializer())
                .registerTypeAdapter(User.class, new UserSerializer())
                .registerTypeAdapter(Card.class, new CardSerializer())
                .registerTypeAdapter(SplitTransaction.class, new SplitTransactionSerializer());

        for (final Class<?> subclass : COMMAND_SUBCLASSES) {
            gsonBuilder.registerTypeAdapter(subclass, new BaseCommandTypeAdapter());
        }

        GSON = gsonBuilder.create();
    }
}

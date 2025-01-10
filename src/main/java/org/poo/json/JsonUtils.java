package org.poo.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.poo.account.BaseAccount;
import org.poo.account.specific.SavingsAccount;
import org.poo.command.BaseCommand;
import org.poo.command.specific.*;
import org.poo.command.specific.splitpayment.AcceptSplitPayment;
import org.poo.command.specific.splitpayment.RejectSplitPayment;
import org.poo.command.specific.splitpayment.SplitPaymentCommand;
import org.poo.input.Input;
import org.poo.json.deserializers.InputDeserializer;
import org.poo.json.serializers.*;
import org.poo.transactions.specific.split.specific.CustomSplitTransaction;
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
            SplitPaymentCommand.class,
            Report.class,
            SpendingsReport.class,
            AddInterest.class,
            WithdrawSavings.class,
            CashWithdrawal.class,
            AcceptSplitPayment.class,
            RejectSplitPayment.class,
            AddNewBusinessAssociate.class,
            ChangeSpendingLimit.class,
            ChangeDepositLimit.class,
            BusinessReport.class
    };

    static {
        final GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(BaseCommand[].class, new CommandArraySerializer())
                .registerTypeAdapter(Input.class, new InputDeserializer())
                .registerTypeAdapter(BaseAccount.class, new BaseAccountSerializer())
                .registerTypeAdapter(SavingsAccount.class, new BaseAccountSerializer())
                .registerTypeAdapter(User.class, new UserSerializer())
                .registerTypeAdapter(Card.class, new CardSerializer())
                .registerTypeAdapter(CustomSplitTransaction.class, new SplitTransactionSerializer());

        for (final Class<?> subclass : COMMAND_SUBCLASSES) {
            gsonBuilder.registerTypeAdapter(subclass, new BaseCommandTypeAdapter());
        }

        GSON = gsonBuilder.create();
    }
}

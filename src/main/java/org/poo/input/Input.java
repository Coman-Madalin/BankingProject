package org.poo.input;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.poo.command.BaseCommand;
import org.poo.commerciant.Commerciant;
import org.poo.json.JsonUtils;
import org.poo.user.User;

import java.io.FileWriter;
import java.io.IOException;


/**
 * The type Input.
 */
@Getter
@Setter
public final class Input {
    @Getter
    private static Input instance = null;
    private Users users;
    private Exchanges exchanges;
    private BaseCommand[] commands;
    private Commerciant[] commerciants;

    public Input() {
        instance = this;
    }

    /**
     * Is alias boolean.
     *
     * @param account the account
     * @return the boolean
     */
    public static boolean isAlias(final String account) {
        return !account.startsWith("RO");
    }

    public void run() {
        calculateAgeOfUsers();

        exchanges.makeCommonCurrencyExchange();
        for (final BaseCommand command : commands) {
            command.execute();
        }
    }

    /**
     * Games to json.
     *
     * @param filePath1 the file path 1
     * @throws IOException the io exception
     */
    public void gamesToJson(final String filePath1) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath1)) {
            final Gson gson = JsonUtils.getGSON();
            gson.toJson(commands, fileWriter);
        }
    }

    public void calculateAgeOfUsers() {
        for (User user : getUsers().getUsers()) {
            user.calculateAge();
        }

        for (User user : getUsers().getUsers()) {
            System.out.println(user.getAge());
        }
    }
}

package org.poo.main;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.poo.checker.Checker;
import org.poo.checker.CheckerConstants;
import org.poo.input.Input;
import org.poo.json.JsonUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

import static org.poo.utils.Utils.resetRandom;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        final File directory = new File(CheckerConstants.TESTS_PATH);
        final Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            final File resultFile = new File(String.valueOf(path));
            for (final File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        final var sortedFiles = Arrays.stream(Objects.requireNonNull(directory.listFiles())).
                sorted(Comparator.comparingInt(Main::fileConsumer))
                .toList();

        for (final File file : sortedFiles) {
            final String filepath = CheckerConstants.OUT_PATH + file.getName();
            final File out = new File(filepath);
            final boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * Action.
     *
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        final Gson gson = JsonUtils.getGSON();
        final JsonReader reader = new JsonReader(
                new FileReader(CheckerConstants.TESTS_PATH + filePath1)
        );

        final Input input = gson.fromJson(reader, Input.class);
        input.run();
        input.gamesToJson(filePath2);
        resetRandom();
    }

    /**
     * Method used for extracting the test number from the file name.
     *
     * @param file the input file
     * @return the extracted numbers
     */
    public static int fileConsumer(final File file) {
        return Integer.parseInt(
                file.getName()
                        .replaceAll(CheckerConstants.DIGIT_REGEX, CheckerConstants.EMPTY_STR)
        );
    }
}

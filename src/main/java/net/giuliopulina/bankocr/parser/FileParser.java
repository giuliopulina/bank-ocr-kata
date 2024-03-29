package net.giuliopulina.bankocr.parser;

import net.giuliopulina.bankocr.AccountNumber;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileParser {

    private static final int DIGIT_NUMBER_OF_COLUMNS_OCCUPIED = 3;
    private static final int DIGIT_NUMBER_OF_ROWS_OCCUPIED = 3;
    private static final int NUMBER_OF_DIGITS = 9;

    public static List<AccountNumber> parse(File file) throws IOException {

        final List<AccountNumber> accountNumbers = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {

            int count = 0;
            final List<String> accountNumberLinearized = new ArrayList<>(9);

            while (fileReader.ready()) {
                String line = fileReader.readLine();

                if (count == DIGIT_NUMBER_OF_ROWS_OCCUPIED) {
                    count = 0;
                    accountNumbers.add(AccountNumberParser.fromString(accountNumberLinearized));
                    accountNumberLinearized.clear();
                } else {

                    if (line.length() < NUMBER_OF_DIGITS * DIGIT_NUMBER_OF_COLUMNS_OCCUPIED) {
                        int lineLength = line.length();
                        for (int i = 0; i < NUMBER_OF_DIGITS * DIGIT_NUMBER_OF_COLUMNS_OCCUPIED - lineLength; i++) {
                            line += " ";
                        }
                    }

                    for (int i = 0; i < NUMBER_OF_DIGITS; i += 1) {
                        final int startPosition = i * DIGIT_NUMBER_OF_COLUMNS_OCCUPIED;
                        final String token = line.substring(startPosition, startPosition + DIGIT_NUMBER_OF_COLUMNS_OCCUPIED);
                        if (count == 0) {
                            accountNumberLinearized.add(token);
                        } else {
                            accountNumberLinearized.set(i, accountNumberLinearized.get(i) + token);
                        }

                    }

                    count++;
                }

            }
        }

        return accountNumbers;
    }
}

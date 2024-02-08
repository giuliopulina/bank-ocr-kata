package link.giuliopulina.bankocr.collector;

import link.giuliopulina.bankocr.AccountNumber;
import link.giuliopulina.bankocr.AccountNumberDigit;
import link.giuliopulina.bankocr.ReadableAccountNumberDigit;
import link.giuliopulina.bankocr.parser.PaperDigit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.stream.Stream.concat;

public class AlternativesCollector<T extends AccountNumberDigit> {

    public List<AccountNumber> findFor(AccountNumber accountNumber) {

        if (!accountNumber.hasAllReadableDigits()) {
            List<AccountNumber> validAccountNumbers = process(accountNumber, digit -> !digit.isReadable());
            if (validAccountNumbers.size() == 1) {
                return validAccountNumbers;
            }
        }

        return process(accountNumber, digit -> true);
    }

    private List<AccountNumber> process(AccountNumber accountNumber, Predicate<AccountNumberDigit> digitsToHandle) {
        List<AccountNumber> combinations = Collections.unmodifiableList(new ArrayList<>());
        for (AccountNumberDigit digit : accountNumber.getDigits()) {
            final List<AccountNumber> newCombinations = new ArrayList<>();

            List<? extends AccountNumberDigit> possibleDigits = singletonList(digit);
            if (digitsToHandle.test(digit)) {
                List<? extends AccountNumberDigit> alternatives = expandDigitsFrom(digit);
                if (!alternatives.isEmpty()) {
                    possibleDigits = expandDigitsFrom(digit);
                }
            }

            if (combinations.isEmpty()) {
                for (AccountNumberDigit alternativeDigit : possibleDigits) {
                    newCombinations.add(new AccountNumber(singletonList(alternativeDigit)));
                }
            } else {
                for (AccountNumberDigit alternativeDigit : possibleDigits) {
                    combinations.forEach(comb -> newCombinations.add(new AccountNumber(concat(comb.getDigits().stream(), Stream.of(alternativeDigit)).toList())));
                }
            }

            combinations = newCombinations;
        }
        return filterResult(combinations);
    }

    private List<AccountNumber> filterResult(List<AccountNumber> combinations) {
        return combinations.stream().filter(AccountNumber::hasValidChecksum).toList();
    }


    protected List<AccountNumberDigit> expandDigitsFrom(AccountNumberDigit accountNumberDigit) {

        List<AccountNumberDigit> alternatives = new ArrayList<>();
        if (accountNumberDigit.isReadable()) {
            alternatives.add(accountNumberDigit);
        }

        final String pattern = accountNumberDigit.getPattern();

        char[] charArray = pattern.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char character = charArray[i];
            if (character == ' ') {
                addIfValidPattern(alternatives, replaceCharInPatternAt(pattern, '|', i));
                addIfValidPattern(alternatives, replaceCharInPatternAt(pattern, '_', i));
            } else if (character == '_' || character == '|') {
                addIfValidPattern(alternatives, replaceCharInPatternAt(pattern, ' ', i));
            }
        }

        return alternatives;

    }

    private static String replaceCharInPatternAt(String pattern, char newChar, int index) {
        final StringBuilder builder = new StringBuilder(pattern);
        builder.setCharAt(index, newChar);
        return builder.toString();
    }

    private static void addIfValidPattern(List<AccountNumberDigit> alternatives, String pattern) {
        Optional<PaperDigit> paperDigit = PaperDigit.fromPattern(pattern);
        paperDigit.ifPresent(digit -> alternatives.add(new ReadableAccountNumberDigit(digit.digitValue(), pattern, ReadableAccountNumberDigit.Status.CORRECTED)));
    }

}

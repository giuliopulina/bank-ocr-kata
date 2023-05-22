package link.giuliopulina.bankocr.collector;

import link.giuliopulina.bankocr.AccountNumber;
import link.giuliopulina.bankocr.AccountNumberDigit;
import link.giuliopulina.bankocr.ReadableAccountNumberDigit;
import link.giuliopulina.bankocr.parser.PaperDigit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Stream.concat;

public abstract class AlternativesCollector<T extends AccountNumberDigit> {

    public List<AccountNumber> collect(AccountNumber accountNumber) {
        if (shouldSearchForAlternatives(accountNumber)) {
            List<AccountNumber> combinations = Collections.unmodifiableList(new ArrayList<>());
            for (AccountNumberDigit digit : accountNumber.getDigits()) {
                final List<AccountNumber> newCombinations = new ArrayList<>();
                List<? extends AccountNumberDigit> possibleDigits = expandDigitsFrom(digit);
                if (possibleDigits.isEmpty()) {
                    possibleDigits = singletonList(digit);
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

        return singletonList(accountNumber);
    }


    protected abstract boolean shouldSearchForAlternatives(AccountNumber accountNumber);

    protected abstract List<AccountNumber> filterResult(List<AccountNumber> combinations);

    protected List<AccountNumberDigit> expandDigitsFrom(AccountNumberDigit accountNumberDigit) {

        if (!shouldConsiderDigit(accountNumberDigit)) {
            return emptyList();
        }

        List<AccountNumberDigit> alternatives = new ArrayList<>();
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

    protected abstract boolean shouldConsiderDigit(AccountNumberDigit digit);

}

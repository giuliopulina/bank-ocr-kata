package net.giuliopulina.bankocr;

import net.giuliopulina.bankocr.parser.PaperDigit;

import java.util.*;

public class OcrErrorCorrector {
    private final AccountNumber accountNumber;

    // not thread-safe, but the current implementation guarantees that there is no concurrent access on this map
    private static final Map<AccountNumberDigit, List<AccountNumberDigit>> cache = new HashMap<>();

    public OcrErrorCorrector(AccountNumber accountNumber) {
        this.accountNumber = accountNumber;
    }

    public List<AccountNumber> accountNumbers() {

        List<AccountNumber> possibleAlternatives = new ArrayList<>();
        for (int i = 0; i < accountNumber.getDigits().size(); i++) {

            AccountNumberDigit digit = accountNumber.getDigits().get(i);

            List<AccountNumberDigit> sourceDigits = findPossibleSourceDigits(digit);

            for (AccountNumberDigit sourceDigit: sourceDigits) {
                List<AccountNumberDigit> alternative = new ArrayList<>(accountNumber.getDigits());
                alternative.set(i, sourceDigit);
                possibleAlternatives.add(new AccountNumber(alternative));
            }
        }

        return possibleAlternatives;
    }

    private static List<AccountNumberDigit> findPossibleSourceDigits(AccountNumberDigit accountNumberDigit) {

        cache.computeIfAbsent(accountNumberDigit, key -> {
            final List<AccountNumberDigit> possibleSourceDigits = new ArrayList<>();
            final String pattern = key.getPattern();

            char[] charArray = pattern.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                char character = charArray[i];
                if (character == ' ') {
                    addIfValidPattern(possibleSourceDigits, replaceCharInPatternAt(pattern, '|', i));
                    addIfValidPattern(possibleSourceDigits, replaceCharInPatternAt(pattern, '_', i));
                } else if (character == '_' || character == '|') {
                    addIfValidPattern(possibleSourceDigits, replaceCharInPatternAt(pattern, ' ', i));
                }
            }

            return possibleSourceDigits;
        });

        return cache.get(accountNumberDigit);

    }

    private static String replaceCharInPatternAt(String pattern, char newChar, int index) {
        final StringBuilder builder = new StringBuilder(pattern);
        builder.setCharAt(index, newChar);
        return builder.toString();
    }

    private static void addIfValidPattern(List<AccountNumberDigit> alternatives, String pattern) {
        Optional<PaperDigit> paperDigit = PaperDigit.fromPattern(pattern);
        paperDigit.ifPresent(digit -> alternatives.add(new ReadableAccountNumberDigit(digit.digitValue(), pattern)));
    }
}

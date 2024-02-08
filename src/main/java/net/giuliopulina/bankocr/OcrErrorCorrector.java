package net.giuliopulina.bankocr;

import net.giuliopulina.bankocr.parser.PaperDigit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OcrErrorCorrector {
    private final AccountNumber accountNumber;

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

        final List<AccountNumberDigit> possibleSourceDigits = new ArrayList<>();
        final String pattern = accountNumberDigit.getPattern();

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

package link.giuliopulina.bankocr.collector;

import link.giuliopulina.bankocr.AccountNumber;
import link.giuliopulina.bankocr.AccountNumberDigit;
import link.giuliopulina.bankocr.UnreadableAccountNumberDigit;
import link.giuliopulina.bankocr.ReadableAccountNumberDigit;
import link.giuliopulina.bankocr.parser.PaperDigit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class ReadableAlternativesCollector extends AlternativesCollector<UnreadableAccountNumberDigit> {

    protected boolean shouldSearchForAlternatives(AccountNumber accountNumber) {
        return !accountNumber.hasAllReadableDigits();
    }

    protected List<AccountNumber> filterResult(List<AccountNumber> combinations) {
        return combinations.stream().filter(AccountNumber::hasAllReadableDigits).toList();
    }

    protected List<AccountNumberDigit> findAlternatives(AccountNumberDigit accountNumberDigit) {

        if (!(accountNumberDigit instanceof UnreadableAccountNumberDigit unreadableAccountNumberDigit)) {
            return emptyList();
        }

        List<AccountNumberDigit> alternatives = new ArrayList<>();
        final String pattern = unreadableAccountNumberDigit.getPattern();

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
        StringBuilder builder = new StringBuilder(pattern);
        builder.setCharAt(index, newChar);
        return builder.toString();
    }

    private static void addIfValidPattern(List<AccountNumberDigit> alternatives, String pattern) {
        Optional<PaperDigit> paperDigit = PaperDigit.fromPattern(pattern);
        paperDigit.ifPresent(digit -> alternatives.add(ReadableAccountNumberDigit.createWithoutAlternatives(digit.digitValue())));
    }
}

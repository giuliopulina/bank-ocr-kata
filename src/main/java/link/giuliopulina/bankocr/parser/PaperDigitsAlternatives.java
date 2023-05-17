package link.giuliopulina.bankocr.parser;

import java.util.*;

import static java.util.Arrays.asList;

public class PaperDigitsAlternatives {

    private static Map<PaperDigit, List<PaperDigit>> ALTERNATIVES_MAP = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(PaperDigit.NINE, Collections.singletonList(PaperDigit.EIGHT)),
            new AbstractMap.SimpleEntry<>(PaperDigit.ZERO, Collections.singletonList(PaperDigit.EIGHT)),
            new AbstractMap.SimpleEntry<>(PaperDigit.ONE, Collections.singletonList(PaperDigit.SEVEN)),
            new AbstractMap.SimpleEntry<>(PaperDigit.FIVE, asList(PaperDigit.NINE, PaperDigit.SIX))
            );

    public static List<PaperDigit> alternativesFor(PaperDigit digit) {

        List<PaperDigit> paperDigits = ALTERNATIVES_MAP.get(digit);
        if (paperDigits == null) {
            paperDigits = Collections.emptyList();
        }

        return paperDigits;

    }
}

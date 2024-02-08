package net.giuliopulina.bankocr.parser;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public enum PaperDigit {

    ZERO(" _ "+
                "| |"+
                "|_|", 0),
    ONE("   " +
               "  |" +
               "  |", 1),
    TWO(" _ " +
               " _|" +
               "|_ ", 2),
    THREE(" _ " +
                 " _|" +
                 " _|", 3),
    FOUR("   " +
                 "|_|" +
                 "  |", 4),
    FIVE(" _ " +
                "|_ " +
                " _|", 5),
    SIX(" _ " +
               "|_ " +
               "|_|", 6),
    SEVEN(" _ " +
                 "  |" +
                 "  |", 7),
    EIGHT(" _ " +
                 "|_|" +
                 "|_|", 8),
    NINE(" _ " +
                "|_|" +
                " _|", 9);

    private final String pattern;
    private final Integer digitValue;

    PaperDigit(String pattern, Integer digitValue) {
        this.pattern = pattern;
        this.digitValue = digitValue;
    }

    public Integer digitValue() {
        return digitValue;
    }


    public static Optional<PaperDigit> fromPattern(String pattern) {
        for (PaperDigit element : PaperDigit.values()) {
            if (pattern.equals(element.pattern)) {
                return of(element);
            }
        }

        return empty();
    }
}

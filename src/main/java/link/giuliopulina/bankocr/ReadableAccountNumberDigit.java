package link.giuliopulina.bankocr;

import java.util.ArrayList;
import java.util.List;

public class ReadableAccountNumberDigit implements AccountNumberDigit {

    private final Integer digit;
    private List<ReadableAccountNumberDigit> alternatives = new ArrayList<>();

    public ReadableAccountNumberDigit(Integer digit) {
        this.digit = digit;
    }

    private ReadableAccountNumberDigit(Integer digit, List<Integer> alternatives) {
        this.digit = digit;
        this.alternatives = alternatives.stream().map(ReadableAccountNumberDigit::new).toList();
    }

    public static ReadableAccountNumberDigit createWithoutAlternatives(Integer digit) {
        return new ReadableAccountNumberDigit(digit);
    }

    public static ReadableAccountNumberDigit createWithAlternatives(Integer digit, List<Integer> alternatives) {
        return new ReadableAccountNumberDigit(digit, alternatives);
    }

    @Override
    public boolean isReadable() {
        return true;
    }

    public Integer getDigit() {
        return digit;
    }

    public List<ReadableAccountNumberDigit> getAlternatives() {
        return alternatives;
    }


}

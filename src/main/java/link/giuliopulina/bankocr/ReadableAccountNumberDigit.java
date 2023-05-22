package link.giuliopulina.bankocr;

import static link.giuliopulina.bankocr.ReadableAccountNumberDigit.Status.CORRECTED;
import static link.giuliopulina.bankocr.ReadableAccountNumberDigit.Status.UNTOUCHED;

public class ReadableAccountNumberDigit extends AccountNumberDigit {

    private final Integer digit;
    private Status status = UNTOUCHED;

    public ReadableAccountNumberDigit(Integer digit, String pattern) {
        super(pattern);
        this.digit = digit;
        this.readable = true;
    }

    public ReadableAccountNumberDigit(Integer digit, String pattern, Status status) {
        this(digit, pattern);
        this.status = status;
    }

    public Integer getDigit() {
        return digit;
    }

    public boolean hasBeenCorrected() {
        return status == CORRECTED;
    }

    @Override
    public String toString() {
        return ""+digit;
    }

    public enum Status {
        UNTOUCHED, CORRECTED
    }
}

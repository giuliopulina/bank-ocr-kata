package net.giuliopulina.bankocr;

public final class ReadableAccountNumberDigit extends AccountNumberDigit {

    private final Integer digit;

    public ReadableAccountNumberDigit(Integer digit, String pattern) {
        super(pattern);
        this.digit = digit;
        this.readable = true;
    }

    public Integer getDigit() {
        return digit;
    }

    @Override
    public String toString() {
        return "" + digit;
    }

}

package net.giuliopulina.bankocr;

public final class UnreadableAccountNumberDigit extends AccountNumberDigit {

    public UnreadableAccountNumberDigit(String pattern) {
        super(pattern);
        readable = false;
    }

    @Override
    public String toString() {
        return getPattern();
    }

}

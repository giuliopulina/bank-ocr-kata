package link.giuliopulina.bankocr;

public class UnreadableAccountNumberDigit extends AccountNumberDigit {

    public UnreadableAccountNumberDigit(String pattern) {
        super(pattern);
        readable = false;
    }

    @Override
    public boolean hasBeenCorrected() {
        return false;
    }

    @Override
    public String toString() {
        return getPattern();
    }

}

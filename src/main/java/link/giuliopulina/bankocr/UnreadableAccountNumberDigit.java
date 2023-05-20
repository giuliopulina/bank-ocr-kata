package link.giuliopulina.bankocr;

public class UnreadableAccountNumberDigit implements AccountNumberDigit {

    final String pattern;

    public UnreadableAccountNumberDigit(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean isReadable() {
        return false;
    }

    public String getPattern() {
        return pattern;
    }
}

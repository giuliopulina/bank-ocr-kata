package link.giuliopulina.bankocr;

public abstract class AccountNumberDigit {

    private final String pattern;

    protected boolean readable;

    AccountNumberDigit(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    public boolean isReadable() {
        return readable;
    }

    public abstract boolean hasBeenCorrected();


}

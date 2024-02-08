package net.giuliopulina.bankocr;

import java.util.Objects;

public sealed abstract class AccountNumberDigit permits ReadableAccountNumberDigit, UnreadableAccountNumberDigit {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountNumberDigit that = (AccountNumberDigit) o;
        return Objects.equals(pattern, that.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern);
    }
}

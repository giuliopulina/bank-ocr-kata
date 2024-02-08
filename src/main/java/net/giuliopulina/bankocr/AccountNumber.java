package net.giuliopulina.bankocr;

import java.util.List;

public class AccountNumber {

    private final List<AccountNumberDigit> digits;
    private final Checksum checksum;

    public AccountNumber(List<AccountNumberDigit> digits) {
        this.digits = digits;

        if (digits.size() != 9) {
            throw new IllegalArgumentException("Account number must be composed of 9 digits, found " + digits.size() + " instead") ;
        }

        if (isReadable()) {
            checksum = calculateChecksum();
        } else {
            checksum = new Checksum(false, -1);
        }
    }

    public boolean isValid() {
        return isReadable() && hasValidChecksum();
    }

    private boolean isReadable() {
        return digits.stream().allMatch(AccountNumberDigit::isReadable);
    }

    private boolean hasValidChecksum() {
        return checksum.valid();
    }

    private Checksum calculateChecksum() {
        long weight = 9;
        long checksum = 0;

        for (AccountNumberDigit digit : digits) {
            checksum += ((ReadableAccountNumberDigit) digit).getDigit() * weight--;
        }
        return new Checksum(checksum % 11 == 0, checksum);
    }

    public List<AccountNumberDigit> getDigits() {
        return digits;
    }

    @Override
    public String toString() {
        return "AccountNumber{" +
                "digits=" + digits +
                '}';
    }

    record Checksum(boolean valid, long value) {

    }
}

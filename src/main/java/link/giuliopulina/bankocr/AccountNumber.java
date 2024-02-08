package link.giuliopulina.bankocr;

import java.util.List;

public class AccountNumber {

    private final List<AccountNumberDigit> digits;
    private final Checksum checksum;

    public AccountNumber(List<AccountNumberDigit> digits) {
        this.digits = digits;
        if (!hasAllReadableDigits()) {
            checksum = new Checksum(false, -1);
        } else {
            checksum = calculateChecksum();
        }
    }

    public boolean hasAllReadableDigits() {
        return digits.stream().allMatch(AccountNumberDigit::isReadable);
    }

    public boolean hasValidChecksum() {
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

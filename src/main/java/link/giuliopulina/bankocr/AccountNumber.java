package link.giuliopulina.bankocr;

import java.util.List;

public class AccountNumber {

    private final List<AccountNumberDigit> digits;

    public AccountNumber(List<AccountNumberDigit> digits) {
        this.digits = digits;
    }

    public boolean hasAllReadableDigits() {
        return digits.stream().allMatch(AccountNumberDigit::isReadable);
    }

    public boolean hasValidChecksum() {
        long weight = 9;
        long checksum = 0;

        if (!hasAllReadableDigits()) {
            return false;
        }

        for (AccountNumberDigit digit : digits) {
            checksum += ((ReadableAccountNumberDigit) digit).getDigit() * weight--;
        }

        return checksum % 11 == 0;
    }

    public List<AccountNumberDigit> getDigits() {
        return digits;
    }
}

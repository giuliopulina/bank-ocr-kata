package link.giuliopulina.bankocr;

import link.giuliopulina.bankocr.writer.Writer;

import java.util.List;

public class AccountNumber {

    private final List<AccountNumberDigit> digits;

    public AccountNumber(List<AccountNumberDigit> digits) {
        this.digits = digits;
    }

    public boolean hasErrors() {
        return !hasAllValidDigits() || ! hasValidChecksum();
    }

    public boolean hasAllValidDigits() {
        return digits.stream().allMatch(AccountNumberDigit::isValid);
    }

    public boolean hasValidChecksum() {
        long weight = 9;
        long checksum = 0;

        if (!hasAllValidDigits()) {
            return false;
        }

        for (AccountNumberDigit digit : digits) {
            checksum += ((ValidAccountNumberDigit) digit).getDigit() * weight--;
        }

        return checksum % 11 == 0;
    }

    public String write(Writer writer) {
        return writer.writeDigits(digits);
    }


}

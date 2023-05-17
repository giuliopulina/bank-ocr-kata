package link.giuliopulina.bankocr.writer;

import link.giuliopulina.bankocr.AccountNumber;
import link.giuliopulina.bankocr.AccountNumberDigit;
import link.giuliopulina.bankocr.ValidAccountNumberDigit;

import java.util.List;

public class Writer {
    private final List<AccountNumber> accountNumbers;

    public Writer(List<AccountNumber> accountNumbers) {
        this.accountNumbers = accountNumbers;
    }

    public List<String> write() {
        return accountNumbers.stream().map(accountNumber -> {

            String result = accountNumber.write(this);
            if (!accountNumber.hasAllValidDigits()) {
                result += " ILL";
            } else if (!accountNumber.hasValidChecksum()) {
                result += " ERR";
            }

            return result;
        }).toList();
    }

    public String writeDigits(List<AccountNumberDigit> digits) {
        String result = "";
        for (AccountNumberDigit accountNumberDigit : digits) {
            if (accountNumberDigit.isValid()) {
                result += ((ValidAccountNumberDigit)accountNumberDigit).getDigit();
            }
            else {
                result += "?";
            }
        }

        return result;
    }
}

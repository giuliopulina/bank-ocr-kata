package link.giuliopulina.bankocr.writer;

import link.giuliopulina.bankocr.*;

import java.util.List;

public class Writer {
    private final AccountNumbers accountNumbers;

    public Writer(AccountNumbers accountNumbers) {
        this.accountNumbers = accountNumbers;
    }

    public List<String> write() {
        return accountNumbers.getEvaluatedAccountNumbers().stream().map(accountNumber -> {

            String result = accountNumber.write(this);
            if (accountNumber.getStatus() == EvaluatedAccountNumber.Status.UNREADABLE) {
                result += " ILL";
            } else if (accountNumber.getStatus() == EvaluatedAccountNumber.Status.INVALID_CHECKSUM) {
                result += " ERR";
            }
            else if (accountNumber.getStatus() == EvaluatedAccountNumber.Status.AMBIGUOUS) {
                result += " AMB";
            }
            return result;
        }).toList();
    }

    public String writeDigits(List<AccountNumberDigit> digits) {
        String result = "";
        for (AccountNumberDigit accountNumberDigit : digits) {
            if (accountNumberDigit.isReadable()) {
                result += ((ReadableAccountNumberDigit)accountNumberDigit).getDigit();
            }
            else {
                result += "?";
            }
        }

        return result;
    }
}

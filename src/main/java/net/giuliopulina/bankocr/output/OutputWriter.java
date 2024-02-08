package net.giuliopulina.bankocr.output;

import net.giuliopulina.bankocr.*;

import java.util.List;

public class OutputWriter {
    private final AccountNumbers accountNumbers;

    public OutputWriter(AccountNumbers accountNumbers) {
        this.accountNumbers = accountNumbers;
    }

    public List<String> write() {
        return accountNumbers.getEvaluatedAccountNumbers().stream().map(accountNumber -> {
            String result = accountNumber.write(this);
            switch (accountNumber.getStatus()) {
                case UNREADABLE -> result += " ILL";
                case INVALID_CHECKSUM ->  result += " ERR";
                case AMBIGUOUS -> result += " AMB";
            }
            return result;
        }).toList();
    }

    public String writeDigits(List<AccountNumberDigit> digits) {
        StringBuilder result = new StringBuilder();
        for (AccountNumberDigit accountNumberDigit : digits) {
            switch (accountNumberDigit) {
                case ReadableAccountNumberDigit readableDigit -> result.append(readableDigit.getDigit());
                case UnreadableAccountNumberDigit unreadableDigit -> result.append("?");
            }
        }

        return result.toString();
    }
}

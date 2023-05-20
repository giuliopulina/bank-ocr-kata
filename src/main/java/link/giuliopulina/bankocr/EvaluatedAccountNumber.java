package link.giuliopulina.bankocr;

import link.giuliopulina.bankocr.writer.Writer;

public class EvaluatedAccountNumber {

    public enum Status {
        UNREADABLE,
        VALID,
        INVALID_CHECKSUM,
        AMBIGUOUS
    }

    private final AccountNumber accountNumber;
    private final Status status;

    public EvaluatedAccountNumber(AccountNumber accountNumber, Status status) {
        this.accountNumber = accountNumber;
        this.status = status;
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

    public Status getStatus() {
        return status;
    }

    public String write(Writer writer) {
        return writer.writeDigits(accountNumber.getDigits());
    }
}

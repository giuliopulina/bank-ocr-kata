package net.giuliopulina.bankocr;

import net.giuliopulina.bankocr.output.OutputWriter;

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

    public Status getStatus() {
        return status;
    }

    public String write(OutputWriter outputWriter) {
        return outputWriter.writeDigits(accountNumber.getDigits());
    }
}

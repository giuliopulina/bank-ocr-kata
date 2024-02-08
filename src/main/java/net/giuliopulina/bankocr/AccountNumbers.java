package net.giuliopulina.bankocr;

import java.util.ArrayList;
import java.util.List;

public class AccountNumbers {

    private final List<EvaluatedAccountNumber> evaluatedAccountNumbers;

    AccountNumbers(List<EvaluatedAccountNumber> evaluatedAccountNumbers) {
        this.evaluatedAccountNumbers = evaluatedAccountNumbers;
    }

    public static AccountNumbers from(List<AccountNumber> accountNumbers) {

        final List<EvaluatedAccountNumber> result = new ArrayList<>();

        for (AccountNumber originalAccountNumber : accountNumbers) {
            if (originalAccountNumber.hasValidChecksum()) {
                result.add(new EvaluatedAccountNumber(originalAccountNumber, EvaluatedAccountNumber.Status.VALID));
                continue;
            }

            if (originalAccountNumber.isNotReadable() || !originalAccountNumber.hasValidChecksum()) {
                List<AccountNumber> alternatives = new OcrErrorCorrector(originalAccountNumber).accountNumbers();
                List<AccountNumber> validChecksumAlternatives = alternatives.stream().filter(AccountNumber::hasValidChecksum).toList();

                switch (validChecksumAlternatives.size()) {
                    case 0 -> result.add(new EvaluatedAccountNumber(originalAccountNumber, EvaluatedAccountNumber.Status.UNREADABLE));
                    case 1 -> result.add(new EvaluatedAccountNumber(validChecksumAlternatives.get(0), EvaluatedAccountNumber.Status.VALID));
                    default -> result.add(new EvaluatedAccountNumber(originalAccountNumber, EvaluatedAccountNumber.Status.AMBIGUOUS));
                }
            }

        }

        return new AccountNumbers(result);
    }

    public List<EvaluatedAccountNumber> getEvaluatedAccountNumbers() {
        return evaluatedAccountNumbers;
    }

}

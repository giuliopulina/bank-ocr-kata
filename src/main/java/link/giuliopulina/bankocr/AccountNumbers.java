package link.giuliopulina.bankocr;

import link.giuliopulina.bankocr.collector.AlternativesCollector;

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

            if (!originalAccountNumber.hasValidChecksum() || !originalAccountNumber.hasAllReadableDigits()) {
                List<AccountNumber> validAlternatives = new AlternativesCollector<>().findFor(originalAccountNumber);

                switch (validAlternatives.size()) {
                    case 0 -> result.add(new EvaluatedAccountNumber(originalAccountNumber, EvaluatedAccountNumber.Status.INVALID_CHECKSUM));
                    case 1 -> result.add(new EvaluatedAccountNumber(validAlternatives.get(0), EvaluatedAccountNumber.Status.VALID));
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

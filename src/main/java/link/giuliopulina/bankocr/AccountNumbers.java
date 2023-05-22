package link.giuliopulina.bankocr;

import link.giuliopulina.bankocr.collector.ReadableAlternativesCollector;
import link.giuliopulina.bankocr.collector.ValidChecksumAlternativesCollector;

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
            List<AccountNumber> readableAccountNumbers = findReadableAlternatives(originalAccountNumber);

            if (readableAccountNumbers.size() == 0) {
                result.add(new EvaluatedAccountNumber(originalAccountNumber, EvaluatedAccountNumber.Status.UNREADABLE));
            }
            else {
                List<AccountNumber> validAccountNumbers = findValidChecksumAlternatives(readableAccountNumbers);
                if (validAccountNumbers.isEmpty()) {
                    result.add(new EvaluatedAccountNumber(originalAccountNumber, EvaluatedAccountNumber.Status.INVALID_CHECKSUM));
                } else if (validAccountNumbers.size() == 1){
                    result.add(new EvaluatedAccountNumber(validAccountNumbers.get(0), EvaluatedAccountNumber.Status.VALID));
                }
                else {
                    result.add(new EvaluatedAccountNumber(originalAccountNumber, EvaluatedAccountNumber.Status.AMBIGUOUS));
                }
            }
        }

        return new AccountNumbers(result);
    }

    private static List<AccountNumber> findReadableAlternatives(AccountNumber originalAccountNumber) {
        return new ReadableAlternativesCollector().collect(originalAccountNumber);
    }

    private static List<AccountNumber> findValidChecksumAlternatives(List<AccountNumber> readableAccountNumbers) {
        final List<AccountNumber> validAlternatives = new ArrayList<>();
        readableAccountNumbers.forEach(accountNumber -> {
            validAlternatives.addAll(new ValidChecksumAlternativesCollector().collect(accountNumber));
        });

        return validAlternatives;
    }

    public List<EvaluatedAccountNumber> getEvaluatedAccountNumbers() {
        return evaluatedAccountNumbers;
    }
}

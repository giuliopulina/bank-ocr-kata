package link.giuliopulina.bankocr.collector;

import link.giuliopulina.bankocr.AccountNumber;
import link.giuliopulina.bankocr.AccountNumberDigit;
import link.giuliopulina.bankocr.UnreadableAccountNumberDigit;

import java.util.List;

public class ReadableAlternativesCollector extends AlternativesCollector<UnreadableAccountNumberDigit> {

    protected boolean shouldSearchForAlternatives(AccountNumber accountNumber) {
        return !accountNumber.hasAllReadableDigits();
    }

    protected List<AccountNumber> filterResult(List<AccountNumber> combinations) {
        return combinations.stream().filter(AccountNumber::hasAllReadableDigits).toList();
    }

    @Override
    protected boolean shouldConsiderDigit(AccountNumberDigit digit) {
        return !digit.isReadable();
    }

}

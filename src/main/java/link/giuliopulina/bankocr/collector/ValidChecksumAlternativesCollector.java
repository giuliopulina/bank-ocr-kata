package link.giuliopulina.bankocr.collector;

import link.giuliopulina.bankocr.AccountNumber;
import link.giuliopulina.bankocr.AccountNumberDigit;
import link.giuliopulina.bankocr.ReadableAccountNumberDigit;

import java.util.List;

import static java.util.stream.Stream.*;

public class ValidChecksumAlternativesCollector extends AlternativesCollector<ReadableAccountNumberDigit> {
    @Override
    protected boolean shouldSearchForAlternatives(AccountNumber accountNumber) {
        return !accountNumber.hasValidChecksum();
    }

    @Override
    protected List<AccountNumberDigit> expandDigitsFrom(AccountNumberDigit accountNumberDigit) {
        return concat(of(accountNumberDigit), super.expandDigitsFrom(accountNumberDigit).stream()).toList();
    }

    @Override
    protected List<AccountNumber> filterResult(List<AccountNumber> combinations) {
        return combinations.stream().filter(AccountNumber::hasValidChecksum).toList();
    }

    @Override
    protected boolean shouldConsiderDigit(AccountNumberDigit digit) {
        return digit.isReadable() && !digit.hasBeenCorrected();
    }
}

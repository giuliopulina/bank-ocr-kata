package link.giuliopulina.bankocr.collector;

import link.giuliopulina.bankocr.AccountNumber;
import link.giuliopulina.bankocr.AccountNumberDigit;
import link.giuliopulina.bankocr.ReadableAccountNumberDigit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Stream.*;

public class ValidChecksumAlternativesCollector extends AlternativesCollector<ReadableAccountNumberDigit> {
    @Override
    protected boolean shouldSearchForAlternatives(AccountNumber accountNumber) {
        return !accountNumber.hasValidChecksum();
    }

    @Override
    protected List<? extends AccountNumberDigit> findAlternatives(AccountNumberDigit accountNumberDigit) {

        if (!(accountNumberDigit instanceof ReadableAccountNumberDigit validAccountNumberDigit)) {
            return Collections.emptyList();
        }

        return concat(of(validAccountNumberDigit), validAccountNumberDigit.getAlternatives().stream()).toList();
    }

    @Override
    protected List<AccountNumber> filterResult(List<AccountNumber> combinations) {
        return combinations.stream().filter(AccountNumber::hasValidChecksum).toList();
    }
}

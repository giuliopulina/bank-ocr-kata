package link.giuliopulina.bankocr.collector;

import link.giuliopulina.bankocr.AccountNumber;
import link.giuliopulina.bankocr.AccountNumberDigit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;

public abstract class AlternativesCollector<T extends AccountNumberDigit> {

    public List<AccountNumber> collect(AccountNumber accountNumber) {
        if (shouldSearchForAlternatives(accountNumber)) {
            List<AccountNumber> combinations = Collections.unmodifiableList(new ArrayList<>());
            for (AccountNumberDigit digit : accountNumber.getDigits()) {
                final List<AccountNumber> newCombinations = new ArrayList<>();
                List<? extends AccountNumberDigit> alternatives = findAlternatives(digit);
                if (alternatives.isEmpty()) {
                    alternatives = singletonList(digit);
                }

                if (combinations.isEmpty()) {
                    for (AccountNumberDigit alternative : alternatives) {
                        newCombinations.add(new AccountNumber(singletonList(alternative)));
                    }
                } else {
                    for (AccountNumberDigit alternative : alternatives) {
                        combinations.forEach(comb -> newCombinations.add(new AccountNumber(concat(comb.getDigits().stream(), Stream.of(alternative)).toList())));
                    }
                }

                combinations = newCombinations;
            }
            return filterResult(combinations);
        }

        return singletonList(accountNumber);
    }
    

    protected abstract boolean shouldSearchForAlternatives(AccountNumber accountNumber);

    protected abstract List<AccountNumber> filterResult(List<AccountNumber> combinations);

    protected abstract List<? extends AccountNumberDigit> findAlternatives(AccountNumberDigit unreadableAccountNumberDigit);

}

package link.giuliopulina.bankocr.parser;

import link.giuliopulina.bankocr.AccountNumber;
import link.giuliopulina.bankocr.AccountNumberDigit;
import link.giuliopulina.bankocr.UnreadableAccountNumberDigit;
import link.giuliopulina.bankocr.ReadableAccountNumberDigit;

import java.util.List;
import java.util.Optional;

public class AccountNumberParser {

    public static AccountNumber fromString(List<String> representation) {
        List<AccountNumberDigit> list = representation.stream().map(pattern -> {
            Optional<PaperDigit> value = PaperDigit.fromPattern(pattern);
            if (value.isPresent()) {
                final PaperDigit paperDigit = value.get();
                final List<PaperDigit> alternatives = ValidPaperDigitsAlternatives.alternativesFor(paperDigit);
                return ReadableAccountNumberDigit.createWithAlternatives(paperDigit.digitValue(), alternatives.stream().map(PaperDigit::digitValue).toList());
            }
            else {
                return new UnreadableAccountNumberDigit(pattern);
            }
        }).toList();
        return new AccountNumber(list);
    }
}

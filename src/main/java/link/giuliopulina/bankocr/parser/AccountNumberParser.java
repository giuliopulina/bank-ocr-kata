package link.giuliopulina.bankocr.parser;

import link.giuliopulina.bankocr.AccountNumber;
import link.giuliopulina.bankocr.AccountNumberDigit;
import link.giuliopulina.bankocr.InvalidAccountNumberDigit;
import link.giuliopulina.bankocr.ValidAccountNumberDigit;

import java.util.List;
import java.util.Optional;

public class AccountNumberParser {

    public static AccountNumber fromString(List<String> representation) {
        List<AccountNumberDigit> list = representation.stream().map(digit -> {
            Optional<PaperDigit> value = PaperDigit.fromPattern(digit);
            if (value.isPresent()) {
                final PaperDigit paperDigit = value.get();
                final List<PaperDigit> alternatives = PaperDigitsAlternatives.alternativesFor(paperDigit);
                return new ValidAccountNumberDigit(paperDigit.digitValue(), alternatives.stream().map(PaperDigit::digitValue).toList());
            }
            else {
                return new InvalidAccountNumberDigit();
            }
        }).toList();
        return new AccountNumber(list);
    }
}

package net.giuliopulina.bankocr.parser;

import net.giuliopulina.bankocr.AccountNumber;
import net.giuliopulina.bankocr.AccountNumberDigit;
import net.giuliopulina.bankocr.UnreadableAccountNumberDigit;
import net.giuliopulina.bankocr.ReadableAccountNumberDigit;

import java.util.List;
import java.util.Optional;

public class AccountNumberParser {

    public static AccountNumber fromString(List<String> representation) {
        List<AccountNumberDigit> list = representation.stream().map(pattern -> {
            Optional<PaperDigit> value = PaperDigit.fromPattern(pattern);
            if (value.isPresent()) {
                final PaperDigit paperDigit = value.get();
                return new ReadableAccountNumberDigit(paperDigit.digitValue(), pattern);
            }
            else {
                return new UnreadableAccountNumberDigit(pattern);
            }
        }).toList();
        return new AccountNumber(list);
    }
}

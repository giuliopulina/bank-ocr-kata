package link.giuliopulina.bankocr;

import java.util.ArrayList;
import java.util.List;

public class ValidAccountNumberDigit implements AccountNumberDigit{

    private final Integer digit;
    private List<Integer> alternatives = new ArrayList<>();

    public ValidAccountNumberDigit(Integer digit) {
        this.digit = digit;
    }

    public ValidAccountNumberDigit(Integer digit, List<Integer> alternatives) {
        this.digit = digit;
        this.alternatives = alternatives;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public Integer getDigit() {
        return digit;
    }


}

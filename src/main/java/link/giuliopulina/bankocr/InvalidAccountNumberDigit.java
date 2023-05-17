package link.giuliopulina.bankocr;

public class InvalidAccountNumberDigit implements AccountNumberDigit {
    @Override
    public boolean isValid() {
        return false;
    }

}

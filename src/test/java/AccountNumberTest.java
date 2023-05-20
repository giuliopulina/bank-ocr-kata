import link.giuliopulina.bankocr.AccountNumbers;
import link.giuliopulina.bankocr.parser.FileParser;
import link.giuliopulina.bankocr.writer.Writer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

public class AccountNumberTest {

    @Test
    public void testValidInput() throws IOException {
        final File file = loadInputFile("simple_valid_input.txt");
        AccountNumbers accountNumbers = AccountNumbers.from(FileParser.parse(file));
        assertEquals(asList("123456789", "183956710"), new Writer(accountNumbers).write());
    }

    @Test
    public void testInvalidChecksum() throws IOException {
        final File file = loadInputFile("simple_invalid_checksum.txt");
        AccountNumbers accountNumbers = AccountNumbers.from(FileParser.parse(file));
        List<String> result = new Writer(accountNumbers).write();
        assertEquals("123456788 ERR", result.get(0));
        assertEquals("183856710 ERR", result.get(1));
    }

    @Test
    public void testUnreadableDigits() throws IOException {
        final File file = loadInputFile("simple_invalid_parsed_digits.txt");
        AccountNumbers accountNumbers = AccountNumbers.from(FileParser.parse(file));
        List<String> result = new Writer(accountNumbers).write();
        assertEquals("1234567?8 ILL", result.get(0));
        assertEquals("??3856710 ILL", result.get(1));
    }

    private File loadInputFile(String path) {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(classLoader.getResource(path).getFile());
        return file;
    }
}

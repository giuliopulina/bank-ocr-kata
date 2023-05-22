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
    public void shouldNotFindAnyReadableAlternative() throws IOException {
        final File file = loadInputFile("file_with_one_illegible_unresolvable_account_number.txt");
        AccountNumbers accountNumbers = AccountNumbers.from(FileParser.parse(file));
        List<String> result = new Writer(accountNumbers).write();
        assertEquals("?222?2222 ILL", result.get(0));
        System.out.println(result);
    }

    @Test
    public void shouldFindOneValidAlternativeFromAnUnreadableInput() throws IOException {
        final File file = loadInputFile("file_with_one_illegible_resolvable_account_number.txt");
        AccountNumbers accountNumbers = AccountNumbers.from(FileParser.parse(file));
        List<String> result = new Writer(accountNumbers).write();
        assertEquals("424492245", result.get(0));
    }

    @Test
    public void shouldNotFindAnyValidAlternativeFromAnInputWithInvalidChecksum() throws IOException {
        final File file = loadInputFile("file_with_one_error_unresolvable_account_number.txt");
        AccountNumbers accountNumbers = AccountNumbers.from(FileParser.parse(file));
        List<String> result = new Writer(accountNumbers).write();
        assertEquals("424402240 ERR", result.get(0));
    }

    @Test
    public void shouldFindOneValidAlternativeFromAnInputWithInvalidChecksum() throws IOException {
        final File file = loadInputFile("file_with_one_error_resolvable_account_number.txt");
        AccountNumbers accountNumbers = AccountNumbers.from(FileParser.parse(file));
        List<String> result = new Writer(accountNumbers).write();
        assertEquals("244224226", result.get(0));
    }

    private File loadInputFile(String path) {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(classLoader.getResource(path).getFile());
        return file;
    }
}

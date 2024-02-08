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
        final File file = loadInputFile("test_data.txt");
        AccountNumbers accountNumbers = AccountNumbers.from(FileParser.parse(file));
        List<String> results = new Writer(accountNumbers).write();
        System.out.println(results);
        assertEquals(asList("123456789", "183956710"), results);
    }

    private File loadInputFile(String path) {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(classLoader.getResource(path).getFile());
        return file;
    }
}

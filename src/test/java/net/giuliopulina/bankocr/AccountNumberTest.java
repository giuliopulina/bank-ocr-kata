package net.giuliopulina.bankocr;

import net.giuliopulina.bankocr.parser.FileParser;
import net.giuliopulina.bankocr.output.OutputWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AccountNumberTest {

    @Test
    public void testValidInput() throws IOException {
        final File file = loadInputFile("full_test_data.txt");
        AccountNumbers accountNumbers = AccountNumbers.from(FileParser.parse(file));
        List<String> results = new OutputWriter(accountNumbers).write();
        Assertions.assertEquals(results, Arrays.asList(
                "000000000",
                "711111111",
                "777777177",
                "200800000",
                "333393333",
                "888888888 AMB",
                "555555555 AMB",
                "666666666 AMB",
                "999999999 AMB",
                "490067715 AMB",
                "123456789",
                "000000051",
                "490867715"));
    }

    private File loadInputFile(String path) {
        final ClassLoader classLoader = getClass().getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
    }
}

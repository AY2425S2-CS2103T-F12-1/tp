package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FileUtilTest {

    @Test
    public void isValidPath() {

        assertTrue(FileUtil.isValidPath("valid/file/path"));


        assertFalse(FileUtil.isValidPath("a\0"));


        assertThrows(NullPointerException.class, () -> FileUtil.isValidPath(null));
    }

}

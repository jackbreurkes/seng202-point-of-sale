package seng202.team1.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodeValidatorTest {

    @Test
    void testCheckCode() {
        // not enough characters
        assertThrows(IllegalArgumentException.class, () -> {
            CodeValidator.checkCode("CC");
        });

        // too many characters
        assertThrows(IllegalArgumentException.class, () -> {
            CodeValidator.checkCode("ELEVENCHARS");
        });

        // not uppercase alphanumeric
        assertThrows(IllegalArgumentException.class, () -> {
            CodeValidator.checkCode("code");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            CodeValidator.checkCode("COD\u2202");
        });

        // invalid number of characters and not uppercase alphanumeric
        assertThrows(IllegalArgumentException.class, () -> {
            CodeValidator.checkCode("this is not a valid code");
        });

        // valid code (min characters)
        String code = "VAL";
        assertEquals(code, CodeValidator.checkCode(code));

        // valid code (max characters)
        code = "VALIDCODE1";
        assertEquals(code, CodeValidator.checkCode(code));
    }
}
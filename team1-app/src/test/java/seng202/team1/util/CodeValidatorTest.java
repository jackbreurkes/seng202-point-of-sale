package seng202.team1.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodeValidatorTest {

    @Test
    void testCheckCode() {
        // not enough characters
        assertThrows(InvalidDataCodeException.class, () -> {
            CodeValidator.checkCode("CC"); // TODO should this be dynamic based on MIN_CHARS?
        });

        // too many characters
        assertThrows(InvalidDataCodeException.class, () -> {
            CodeValidator.checkCode("ELEVENCHARS"); // TODO should this be dynamic based on MAX_CHARS?
        });

        // not uppercase alphanumeric
        assertThrows(InvalidDataCodeException.class, () -> {
            CodeValidator.checkCode("code");
        });
        assertThrows(InvalidDataCodeException.class, () -> {
            CodeValidator.checkCode("COD\u2202");
        });

        // invalid number of characters and not uppercase alphanumeric
        assertThrows(InvalidDataCodeException.class, () -> {
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
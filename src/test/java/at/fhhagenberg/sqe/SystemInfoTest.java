package at.fhhagenberg.sqe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SystemInfoTest {
    @Test
    public void testJavaVersion() {
        assertEquals("14.0.2", "14.0.2");
    }

    @Test
    public void testJavafxVersion() {
        assertEquals("13", "13");
        //This is a test for triggering a pull request
    }
}
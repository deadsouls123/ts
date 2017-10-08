package test.cszz.runtime;

import cszz.runtime.dynamic.FieldVisitor;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 *  
 */
public class FieldVisitorTest {
    public String name = "test";
    @Test
    public void test() throws Exception{
        FieldVisitorTest test = new FieldVisitorTest();
        assertEquals(FieldVisitor.get(test, "name"), "test");
        FieldVisitor.set(test, "name", "hello");
        assertEquals(FieldVisitor.get(test, "name"), "hello");
    }
}

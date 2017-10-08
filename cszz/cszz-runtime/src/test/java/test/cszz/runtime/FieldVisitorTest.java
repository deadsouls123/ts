package test.cszz.runtime;

import org.junit.Assert;
import org.junit.Test;

import cszz.runtime.dynamic.FieldVisitor;

/**
 *
 *  
 */

public class FieldVisitorTest {
    public String name = "test";
    @Test
    public void test() throws Exception{
        FieldVisitorTest test = new FieldVisitorTest();
        Assert.assertEquals(FieldVisitor.get(test, "name"), "test");
        FieldVisitor.set(test, "name", "hello");
        Assert.assertEquals(FieldVisitor.get(test, "name"), "hello");
    }
}

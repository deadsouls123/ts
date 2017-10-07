package test.cszz.runtime;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import cszz.runtime.dynamic.FieldVisitor;
import cszz.runtime.dynamic.MethodAmbiguousException;
import cszz.runtime.dynamic.MethodDispatcher;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 *  
 */
public class MethodDispatcherTest {
    
    public void method(List obj,ArrayList list){}
    
    public void method(ArrayList obj,List list){}
    
    public String test(Object obj){
        return "Object";
    }
    public String test(List list){
        return "List";
    }
    
    public MethodDispatcherTest(){}
    
    @Test
    public void test() throws Exception {
        List list = new ArrayList();
        MethodDispatcher.invokeMethod(list, "add","test");
        assertEquals("[test]", list.toString());
        assertEquals("List", MethodDispatcher.invokeMethod(this,"test",list));
        assertEquals("Object", MethodDispatcher.invokeMethodExactly(this, "test", new Object[]{list}, new String[]{Object.class.getName()}));
        
        MethodDispatcherTest test = new MethodDispatcherTest();
        boolean ambs = false;
        try{
            MethodDispatcher.invokeMethod(test, "method", list,list);
            fail("unexcepted");
        }catch(MethodAmbiguousException ex){
            ambs = true;
        }
        assertTrue(ambs);
    }
    
}

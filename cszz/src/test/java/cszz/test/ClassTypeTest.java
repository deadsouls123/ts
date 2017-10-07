package cszz.test;

import java.util.Arrays;
import cszz.ast.ClassNode;
import cszz.compiler.AstLoader;
import cszz.AstNotFoundException;
import cszz.core.ArrayType;
import cszz.core.ClassType;
import cszz.core.ObjectType;
import cszz.core.Type;
import cszz.core.Types;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 *  
 */
public class ClassTypeTest {
    
    public ClassTypeTest() {
    }
    
    @Test
    public void testEarsedType() throws AstNotFoundException{
        AstLoader astLoader = new AstLoader();
        ClassType listType = Types.getClassType(astLoader.loadAst("java.util.List"),new Type[0]);
        ClassType arrayListType = Types.getClassType(astLoader.loadAst("java.util.ArrayList"),new Type[0]);
        assertTrue(arrayListType.isSubTypeOf(listType));
        
    }
    
    @Test
    public void test() throws AstNotFoundException{
        AstLoader astLoader = new AstLoader();
        ClassNode listClass = astLoader.loadAst("java.util.List");
        ClassNode arrayListClass = astLoader.loadAst("java.util.ArrayList");
        
        Type[] paramTypes = new Type[]{Types.getRootType()};
        ObjectType listType = Types.getClassType(listClass,paramTypes );
        ArrayType listArrayType = Types.getArrayType(listType);
        ObjectType arrayListType = Types.getClassType(arrayListClass,paramTypes );
        ArrayType arrayListArrayType = Types.getArrayType(arrayListType);
        assertTrue(arrayListType.isSubTypeOf(listType));
        assertTrue(listArrayType.isAssignableFrom(arrayListArrayType));
        String mdDescriptors = Arrays.toString(listType.getMethodDescriptors(listClass, false,true));
        assertEquals("[public abstract boolean contains(java.lang.Object arg0), public abstract boolean addAll(int arg0,java.util.Collection<? extends java.lang.Object> arg1), public void sort(java.util.Comparator<? super java.lang.Object> arg0), public abstract java.util.ListIterator<java.lang.Object> listIterator(int arg0), public abstract java.util.ListIterator<java.lang.Object> listIterator(), public abstract boolean retainAll(java.util.Collection<? extends java.lang.Object> arg0), public abstract java.lang.Object[] toArray(), public abstract java.lang.Object remove(int arg0), public abstract boolean addAll(java.util.Collection<? extends java.lang.Object> arg0), public boolean removeIf(java.util.function.Predicate<? super java.lang.Object> arg0), public abstract int hashCode(), public abstract boolean removeAll(java.util.Collection<? extends java.lang.Object> arg0), public java.util.stream.Stream<java.lang.Object> parallelStream(), public abstract int lastIndexOf(java.lang.Object arg0), public void replaceAll(java.util.function.UnaryOperator<java.lang.Object> arg0), public abstract void clear(), public abstract boolean containsAll(java.util.Collection<? extends java.lang.Object> arg0), public abstract int indexOf(java.lang.Object arg0), public abstract boolean add(java.lang.Object arg0), public abstract boolean isEmpty(), public abstract java.util.List<java.lang.Object> subList(int arg0,int arg1), public abstract java.lang.Object get(int arg0), public abstract java.lang.Object set(int arg0,java.lang.Object arg1), public abstract void add(int arg0,java.lang.Object arg1), public abstract java.util.Iterator<java.lang.Object> iterator(), public java.util.Spliterator<java.lang.Object> spliterator(), public void forEach(java.util.function.Consumer<? super java.lang.Object> arg0), public abstract java.lang.Object[] toArray(java.lang.Object[] arg0), public abstract boolean remove(java.lang.Object arg0), public abstract boolean equals(java.lang.Object arg0), public java.util.stream.Stream<java.lang.Object> stream(), public abstract int size()]",mdDescriptors);
        
        //assertTrue(listType.isSubTypeOf(Types.getRootType()));
    }
    
}

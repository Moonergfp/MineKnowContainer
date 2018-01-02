import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by wuhuachuan on 16/8/25.
 */
public class MyStackTest {
    private static final int LENGTH = 3;
    private MyStack<Integer> myStack = new MyStack<>(3);

    @Test
    public void testPush(){
        //1. null
        try {
            myStack.push(null);
            assertFalse(true);
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }

        //2. int 类型
        try {
            myStack.push(1);
            assertTrue(true);
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }

        //3. 合理的push
        Integer element = new Integer(2);
        myStack.push(element);
        assertTrue("push fail.",testDataIsExist(element));

        //4. 出界
        try {
            for(int i = 0; i < LENGTH + 1; ++i){
                myStack.push(new Integer(i));
            }
            assertFalse(true);
        } catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    public void testPop(){
        //1. 数组元素为空的时候 pop
        try {
            myStack.pop();
            assertFalse(true);
        } catch (Exception e){
            assertTrue(true);
        }

        //2. 正常 pop  // PS:需要先保证 push 操作 ok
        Integer element = new Integer(1);
        myStack.push(element);
        assertTrue("pop is wrong",myStack.pop() == element);
    }

    private boolean testDataIsExist(Integer element) {
        Object elementData[] = myStack.getElementData();

        for (int i = 0 ; i < elementData.length; ++i){
            if(element == elementData[i]){
                return true;
            } else {
                ;
            }
        }
        return false;
    }
}
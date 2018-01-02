public class MyStack<T> {

    private static final int DEFAULT_LENGTH = 5;
    private Object elementData[];

    private int size = 0;


    public MyStack(){
        elementData = new Object[DEFAULT_LENGTH];
    }
    public MyStack(int length){
        elementData = new Object[length];
    }

    public void push(T element) throws ArrayIndexOutOfBoundsException,IllegalArgumentException {
        if(element == null){
            throw new IllegalArgumentException("element can not be null");
        } else {
            if(size == elementData.length){
                throw new ArrayIndexOutOfBoundsException();
            } else {
                elementData[size++] = element;
            }
        }
    }

    public T pop() throws ArrayIndexOutOfBoundsException{
        if(size == 0){
            throw new ArrayIndexOutOfBoundsException();
        } else {
            T element = (T) elementData[--size];
            elementData[size] = null; // 防止内存泄露
            return element;
        }
    }

    public Object[] getElementData(){
        return elementData;
    }
}

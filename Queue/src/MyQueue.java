import javax.naming.OperationNotSupportedException;

/**
 * MyQueue :
 *
 * @author wuhuachuan712@163.com
 * @date 16/12/30
 */
public class MyQueue<T> {

    private Object elementData[];
    private int front;
    private int rear;

    public MyQueue(int length){
        elementData = new Object[length];
    }

    public T poll() throws OperationNotSupportedException {
        if(front == rear){
            throw new OperationNotSupportedException("queue is empty");
        }

        T element = (T) elementData[front];
        elementData[front] = null;  //help GC;
        front = (front + 1) % elementData.length;

        return element;
    }

    public void add(T element) throws OperationNotSupportedException {
        if((rear + 1) % elementData.length == front){
            throw new OperationNotSupportedException("queue is full");
        }

        elementData[rear] = element;
        rear = (rear + 1) % elementData.length;
    }
}

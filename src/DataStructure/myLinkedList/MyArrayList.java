package DataStructure.myLinkedList;

/**
 * 数组
 */
public class MyArrayList<AnyType> {
    //设置初始容量
    private static final int DEFAULT_CAPACITY = 10;

    //List大小
    private int theSize;

    //数组,用来装对象
    private AnyType[] theItems;

    /**
     * 在最后新增元素
     * @param item
     * @return
     */
    public boolean add(AnyType item){
        return true;
    }

    /**
     * 在指定下标新增元素
     * @param idx
     * @param item
     */
    private void add(int idx, AnyType item){

    }

    /**
     * 在指定下标设值
     * @param idx
     * @param item
     * @return
     */
    public AnyType set(int idx, AnyType item){
        if (idx < 0 || idx >= size()){
            throw new ArrayIndexOutOfBoundsException();
        }
        AnyType oldItem = theItems[idx];
        theItems[idx] = item;
        return oldItem;
    }

    /**
     * 获取List大小
     * @return
     */
    public int size(){
        return theSize;
    }

}

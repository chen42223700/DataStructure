package DataStructure.myLinkedList;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * LinkedList：双链表
 */
public class MyLinkedList<AnyType>{

    //链表大小
    private int size;
    //计数器
    private int modCount =  0;
    //起始节点
    private Node<AnyType> beginMarker;
    //最终节点
    private Node<AnyType> endMarker;

    /**
     * 构造方法：新建一节点，首尾节点为空
     */
    public MyLinkedList() {
        clear();
    }

    /**
     *
     * @return LinkedList的大小
     */
    public int size() {
        return size;
    }

    /**
     *
     * @return 判断LinkedList是否为空
     */
    public boolean isEmpty(){
        return 0 == size();
    }

    /**
     * LinkedList 加入数据方法
     * @param data 需要加入的数据
     * @return 是否加入成功
     */
    public boolean add( AnyType data){
        return true;
    }

    /**
     * 从指定下标位置加入数据
     * @param idx
     * @param data
     * @return
     */
    public boolean add(int idx, AnyType data){
        addBefore(getNode(idx), data);
        return true;
    }

    /**
     * 获取指定节点的数据
     * @param idx
     * @return
     */
    public AnyType get(int idx){
        return getNode(idx).data;
    }

    /**
     * 设置指定下表的值，并将原值返回出去
     * @param idx
     * @param data
     * @return
     */
    public AnyType set(int idx, AnyType data){
        Node<AnyType> p = getNode(idx);
        AnyType oldData = p.data;
        p.data = data;
        return  oldData;
    }


    /**
     * LinkedList清空方法
     */
    public void clear(){
        //将开始节点的下一个下一个节点设为最终节点，最终节点的上一节点设为开始节点
        beginMarker = new Node<AnyType>(null, null , null);
        endMarker = new Node<AnyType>(null, beginMarker, null);
        beginMarker.next = endMarker;

        //大小和计数器重置为0
        size = 0;
        modCount = 0;
    }

    /**
     * 删除指定下标节点
     * @param idx
     * @return
     */
    public AnyType remove( int idx){
        return remove(getNode(idx)) ;
    }

    /**
     * 删除指定节点
     * @param p
     * @return
     */
    private AnyType remove(Node<AnyType> p){
        //将指定下标节点的前一节点的后一节点设置为当前节点的下一节点
        p.prev.next = p.next;
        //将当前节点后一节点的前一节点设置为当前节点的前一节点
        p.next.prev = p.prev;
        //大小-1
        size--;
        //计数器+1
        modCount++;
        //返回当前节点的数据
        return p.data;
    }

    /**
     * 获取迭代器
     * @return
     */
    public Iterator<AnyType> iterator(){
        return new LinkedListIterator();
    }

    /**
     * LindedList迭代器的实现
     */
    private class LinkedListIterator implements Iterator<AnyType>{

        //当前节点设置为开始节点的下一节点，即第一个元素
        private Node<AnyType> current = beginMarker.next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;

        /**
         * 当前节点不为最终节点时 return true
         * @return
         */
        @Override
        public boolean hasNext() {
            return (current != endMarker);
        }

        @Override
        public AnyType next() {
            //如果计数器不相等直接抛出异常
            if ( modCount != expectedModCount ){
                throw new ConcurrentModificationException();
            }
            //如果没有下一节点直接抛出异常
            if (!hasNext()){
                throw  new NoSuchElementException();
            }

            AnyType nextData = current.data;
            current = current.next;
            //将remove开关设为true
            okToRemove = true;
            //返回当前的data
            return nextData;
        }

        @Override
        public void remove() {
            //如果计数器不相等直接抛出异常
            if ( modCount != expectedModCount ){
                throw new ConcurrentModificationException();
            }
            //删除当前节点的上一节点
            MyLinkedList.this.remove(current.prev);
            expectedModCount ++;
            okToRemove = false;

        }

        @Override
        public void forEachRemaining(Consumer<? super AnyType> action) {

        }
    }

    /**
     * 从指定节点前加入数据
     * @param node
     * @param data
     */
    private void addBefore(Node<AnyType> node, AnyType data){
        //新建一个节点，该节点的前一节点是指定节点的前一节点，后一节点是指定节点
        Node<AnyType> newNode = new Node<AnyType>(data, node.prev, node);
        //更新前一节点指向后一节点的值: 设为当前节点
        newNode.prev.next = newNode;
        //更新后一节点指向前一节点的值: 设为当前节点
        node.prev = newNode;
        //LinkedList大小+1
        size++;
        //操作数量+1
        modCount++;
    }

    /**
     * 获取当前节点
     * @param idx
     * @return
     */
    private Node<AnyType> getNode(int idx){
        //当传入的下标小于0活着大于大小-1时 抛出越界异常
        if (idx < 0 || idx > size() -1 ){
            throw new IndexOutOfBoundsException();
        }
        Node<AnyType> p;
        //2分法找出指定节点
        if (idx < size() / 2){
            p = beginMarker.next;
            for (int i = 0; i < idx; i++ ){
                p = p.next;
            }
        }else {
            p = endMarker.prev;
            for (int i = size() - 1 ; i > idx ;  i--){
                p = p.prev;
            }
        }
        return p;
    }

    /**
     * LinkedList中的节点
     * @param <AnyType>
     */
    private class Node<AnyType>{

        //当前节点的数据
        private AnyType data;
        //前一节点
        private Node<AnyType> prev;
        //后一节点
        private Node<AnyType> next;

        /**
         * 每个节点的构造方法
         * @param data 当前节点的数据
         * @param prev 前一节点
         * @param next 后一节点
         */
        public Node(AnyType data, Node<AnyType> prev, Node<AnyType> next){
            data = data;
            prev = prev;
            next = next;
        }
    }
}



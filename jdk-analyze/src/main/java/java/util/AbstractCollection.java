/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.util;

/**
 * AbstractCollection 是 Java 集合框架中 Collection 接口 的一个直接实现类， Collection 下的大多数子类都继承 AbstractCollection ，比如 List 的实现类, Set的实现类。
 */

public abstract class AbstractCollection<E> implements Collection<E> {
    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     */
    protected AbstractCollection() {
    }

    // Query Operations

    /**
     * Returns an iterator over the elements contained in this collection.
     *
     * @return an iterator over the elements contained in this collection
     */
    public abstract Iterator<E> iterator();

    public abstract int size();

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns <tt>size() == 0</tt>.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 如果集合中包含至少一个指定对象,返回true
     */
    public boolean contains(Object o) {
        //调用iterator方法，获取此集合中包含的元素的迭代器
        Iterator<E> it = iterator();
        if (o==null) {
            //null的判断，所以，可以通过该方法判断集合中是否有null值
            while (it.hasNext())
                if (it.next()==null)
                    return true;
        } else {
            while (it.hasNext())
                //调用equals方法判断对象的相等
                if (o.equals(it.next()))
                    return true;
        }
        return false;
    }

    /**
     * 此实现返回一个包含所有元素的数组
     */
    public Object[] toArray() {
        //估计数组的大小；准备一个集合大小的数组
        Object[] r = new Object[size()];
        //获取集合元素的迭代器
        Iterator<E> it = iterator();
        for (int i = 0; i < r.length; i++) {
            if (! it.hasNext()) {
                // 元素少于预期，可能情况就是遍历过程中发生了集合元素的移除.Arrays.copyOf方法为复制指定的数组，截断或填充为空
                return Arrays.copyOf(r, i);
            }
            r[i] = it.next();
        }
        //如果集合还有元素（元素多于预期结果），也就是在遍历过程中，集合添加了元素，那么重新分配ToArray中使用的数组，调用finishToArray方法
        return it.hasNext() ? finishToArray(r, it) : r;
    }

    /**
     * 返回包含此集合中所有元素的数组;
     * 原理：根据参数数组的类型，构造了一个与向量元素个数相等的空数组。
     */
    public <T> T[] toArray(T[] a) {
        // 获取到集合的大小
        int size = size();
        //如果传进来的数组大小大于集合，则直接使用a。否则通过反射准备一个size大小的，a对象数组类型的数组
        T[] r = a.length >= size ? a :
                  (T[])java.lang.reflect.Array
                  .newInstance(a.getClass().getComponentType(), size);
        Iterator<E> it = iterator();

        for (int i = 0; i < r.length; i++) {
            if (! it.hasNext()) {
                //元素比预期少
                if (a == r) {
                    //使用null进行终止，a数组等于r数组
                    r[i] = null;
                } else if (a.length < i) {
                    //a的长度小于i，则将r数组截断进行返回
                    return Arrays.copyOf(r, i);
                } else {
                    //数组复制
                    System.arraycopy(r, 0, a, 0, i);
                    if (a.length > i) {
                        //终止数组
                        a[i] = null;
                    }
                }
                return a;
            }
            r[i] = (T)it.next();
        }
        // 元素比预期多
        return it.hasNext() ? finishToArray(r, it) : r;
    }

    /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * 当迭代器返回的元素多于预期时，重新分配ToArray中使用的数组，并从迭代器中完成填充。
     * @param r 数组，其中充满了以前存储的元素
     * @param it 此集合上正在进行的迭代器
     * @return 包含给定数组中的元素的数组，再加上迭代器返回的任何其他元素
     */
    @SuppressWarnings("unchecked")
    private static <T> T[] finishToArray(T[] r, Iterator<?> it) {
        int i = r.length;
        while (it.hasNext()) {
            int cap = r.length;
            if (i == cap) {
                //第一次进行循环的时候，对数组进行重新分配内存大小，扩大了原来大小的一半+1
                int newCap = cap + (cap >> 1) + 1;
                // overflow-conscious code
                if (newCap - MAX_ARRAY_SIZE > 0) {
                    //最大分配Integer.MAX_VALUE
                    newCap = hugeCapacity(cap + 1);
                }
                //扩大数组容量
                r = Arrays.copyOf(r, newCap);
            }
            r[i++] = (T)it.next();
        }
        //返回数组，如果数组分配过大，进行剪裁
        return (i == r.length) ? r : Arrays.copyOf(r, i);
    }
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0)
            //溢出啦，超过int范围了或者是负数
            throw new OutOfMemoryError
                ("Required array size too large");
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }

    // Modification Operations

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always throws an
     * <tt>UnsupportedOperationException</tt>.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IllegalStateException         {@inheritDoc}
     */
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    /**
     * 移除元素
     */
    public boolean remove(Object o) {
        //还是迭代器
        Iterator<E> it = iterator();
        if (o==null) {
            //null值的移除
            while (it.hasNext()) {
                if (it.next()==null) {
                    //调用迭代器的remove方法
                    it.remove();
                    return true;
                }
            }
        } else {
            while (it.hasNext()) {
                if (o.equals(it.next())) {
                    //调用迭代器的remove方法
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }


    // Bulk Operations

    /**
     * 如果此 collection 包含指定 collection 中的所有元素，则返回 true。
     */
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            //遍历参数集合
            if (!contains(e)) {
                //只要有一个元素在集合中没有，那么返回false，该方法最坏的情况，时间复杂度n*n，挺高的，使用的时候注意性能吧
                return false;
            }
        }
        return true;
    }

    /**
     * 将指定 collection 中的所有元素都添加到此 collection 中
     */
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            //遍历参数集合
            if (add(e)) {
                modified = true;
            }
        }
        //只要有一个值添加成功，就会返回true
        return modified;
    }

    /**
     * 移除参数集合中的存在的元素
     */
    public boolean removeAll(Collection<?> c) {
        //NULL判断
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<?> it = iterator();
        while (it.hasNext()) {
            //先遍历的是当前集合
            if (c.contains(it.next())) {
                //只要是参数集合中存在的元素，就会被移除
                it.remove();
                modified = true;
            }
        }
        //只要移除了一个元素，则返回true
        return modified;
    }

    /**
     * 仅保留此集合中包含在指定集合中的元素。换句话说，从此集合中删除未包含在指定集合中的所有元素。
     */
    public boolean retainAll(Collection<?> c) {
        //NULL判断
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                //移除c集合中不存在的元素
                it.remove();
                modified = true;
            }
        }
        //只要移除了一个元素，返回true
        return modified;
    }

    /**
     * 清空集合
     */
    public void clear() {
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }


    //  String conversion

    /**
     * toString方法重写
     */
    public String toString() {
        Iterator<E> it = iterator();
        if (! it.hasNext()) {
            //空集合的输出
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        //遍历集合，进行输出元素的值
        for (;;) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (! it.hasNext()) {
                return sb.append(']').toString();
            }
            sb.append(',').append(' ');
        }
    }

}

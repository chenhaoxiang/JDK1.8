/*
 * Copyright (c) 1997, 2014, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.util;

import java.util.function.UnaryOperator;

/**
 *
 *  List接口表示数据结构中的链表，其继承collection接口，又往里面添加了一些链表操作的方法，主要是随机访问、删除、查找、专用的迭代器等
 *
 *  List提供了一种特殊的iterator遍历器，叫做ListIterator。这种遍历器允许遍历时插入，替换，删除，双向访问。 并且还有一个重载方法允许从一个指定位置开始遍历。
 *
 * 然后我们再看下List接口新增的接口，会发现add，get这些都多了index参数，说明在原来Collection的基础上，List是一个可以指定索引，有序的容器。
 *
 * 一个集合在遍历过程中进行插入删除操作很容易造成错误，特别是无序队列，是无法在遍历过程中进行这些操作的。但是List是一个有序集合，所以在这实现了一个ListIteractor，可以在遍历过程中进行元素操作，并且可以双向访问。
 *
 * ArrayList和LinkedList便是实现了List接口
 *
 * @param <E> the type of elements in this list
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see ListIterator
 * @see Collection
 * @see Set
 * @see ArrayList
 * @see LinkedList
 * @see Vector
 * @see Arrays#asList(Object[])
 * @see Collections#nCopies(int, Object)
 * @see Collections#EMPTY_LIST
 * @see AbstractList
 * @see AbstractSequentialList
 * @since 1.2
 */

public interface List<E> extends Collection<E> {
    // 查询操作

    /**
     * 返回此列表中的元素数。如果此列表包含元素个数超过<tt> Integer.MAX_VALUE </ tt>，返回 <tt> Integer.MAX_VALUE </ tt>。
     *
     * @return 此列表中的元素数量
     */
    int size();

    /**
     * 如果此列表不包含任何元素，则返回<tt> true </ tt>。
     *
     * @return <tt>true</tt> if this list contains no elements
     */
    boolean isEmpty();

    /**
     *
     * 如果集合中包含至少一个指定对象,返回true
     * @param o 要测试其在此集合中的存在的元素
     * @return <tt>true</tt>  如果集合中包含该元素
     */
    boolean contains(Object o);

    /**
     * 返回此集合中元素的迭代器。无法保证元素的返回顺序
     *（除非此集合是某个提供保证的类的实例）。
     *
     * @return an <tt>Iterator</tt> 覆盖此集合中的元素
     */
    Iterator<E> iterator();


    /**
     * 返回包含此集合中所有元素的数组。
     * 如果此集合对其迭代器返回的元素的顺序做出任何保证，则此方法必须以相同的顺序返回元素。
     *
     * 返回的数组将是“安全的”，因为此集合不会保留对它的引用。
     * （换句话说，即使此集合由数组支持，此方法也必须分配新数组）。
     *  调用者因此可以自由修改返回的数组。
     *
     * @return 包含此集合中所有元素的数组
     */
    Object[] toArray();


    /**
     * 返回包含此集合中所有元素的数组;
     * 返回数组的运行时类型是指定数组的运行时类型。
     * 如果集合适合指定的数组，则返回元素到指定的数组中。
     * 否则，将使用指定数组的运行时类型和此集合的大小分配新数组。
     *
     * 此集合适合指定的数组，如果有空余空间（即，数组的元素多于此集合），则紧跟集合结尾的数组中的元素将设置为<tt> null </ TT>。
     * （如果调用者知道此集合不包含任何<tt> null </ tt>元素，则此选项仅用于确定此集合的长度<i> </ i>。）
     *
     * 如果此集合对其迭代器返回的元素的顺序做出任何保证，则此方法必须以相同的顺序返回元素。
     *
     * @param <T> 包含集合的数组的运行时类型
     * @throws ArrayStoreException 如果指定数组的运行时类型不是此集合中每个元素的运行时类型的超类型
     * @throws NullPointerException 如果指定的数组为null
     */
    <T> T[] toArray(T[] a);


    // 修改操作

    /**
     * 将元素e添加到集合中
     * 如果集合不允许重复元素，且集合中已经含有该元素，返回false
     * false-添加失败
     */
    boolean add(E e);

    /**
     * 从该 collection 中移除指定元素的单个实例，如果集合中存在指定元素返回true。
     */
    boolean remove(Object o);


    // Bulk Modification Operations

    /**
     * 如果此 collection 包含指定 collection 中的所有元素，则返回 true。
     */
    boolean containsAll(Collection<?> c);

    /**
     * 将指定 collection 中的所有元素都添加到此 collection 中
     */
    boolean addAll(Collection<? extends E> c);

    /**
     *
     * 将指定集合中的所有元素插入到指定位置的当前列表中（可选操作）。
     *
     * 将当前位置的元素（如果有）和任何后续元素向右移动（增加其索引）。
     *
     * 新元素将按照指定集合的​​迭代器返回的顺序出现在此列表中。
     *
     * 如果在操作正在进行修改的指定集合，则此操作的行为是不确定的。 （请注意，如果指定的集合是当前集合并且它是非空的，则会发生这种情况。）
     *
     * @param index 用于指定插入集合中的元素的初始位置
     * @param c 包含要添加到此列表的元素的集合
     * @return <tt>true</tt> 如果此列表因调用而更改
     * @throws UnsupportedOperationException 如果此列表不支持<tt> addAll </ tt>操作
     * @throws ClassCastException 如果指定集合的​​元素的类阻止将其添加到此列表中
     * @throws NullPointerException 如果指定的集合包含一个或多个null元素，并且此列表不允许null元素，或者指定的集合为null
     * @throws IllegalArgumentException 如果指定集合的​​元素的某些属性阻止将其添加到此列表中
     * @throws IndexOutOfBoundsException 如果索引超出范围 (<tt>index &lt; 0 || index &gt; size()</tt>)
     */
    boolean addAll(int index, Collection<? extends E> c);

    /**
     * 移除此 collection 中那些也包含在指定 collection 中的所有元素（可选操作）。
     */
    boolean removeAll(Collection<?> c);

    /**
     *
     * 仅保留此集合中包含在指定集合中的元素。换句话说，从此集合中删除未包含在指定集合中的所有元素。
     */
    boolean retainAll(Collection<?> c);

    /**
     * 将该列表的每个元素替换为将运算符应用于该元素的结果。操作抛出的错误或运行时异常将转发给调用者。
     * 提供了接口默认实现，支持批量删除，主要是方便Lambda表达式
     *
     * 如果列表的list-iterator不支持{@code set}操作，则在替换第一个元素时将抛出{@code UnsupportedOperationException}。
     *
     * 后续Function接口讲解将会涉及
     *
     * @since 1.8 新增接口
     */
    default void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
        final ListIterator<E> li = this.listIterator();
        while (li.hasNext()) {
            li.set(operator.apply(li.next()));
        }
    }

    /**
     * 根据指定的顺序对此列表进行排序
     * 提供了接口默认实现，用于对集合进行排序，主要是方便Lambda表达式
     * @since 1.8
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    default void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        //进行快排，然后遍历数组，使用迭代器将集合重新设置值
        Arrays.sort(a, (Comparator) c);
        ListIterator<E> i = this.listIterator();
        for (Object e : a) {
            i.next();
            i.set((E) e);
        }
    }

    /**
     * 移除此 collection 中的所有元素。
     */
    void clear();


    // 比较和散列

    /**
     *  比较此 collection 与指定对象是否相等。通过覆盖，实现list与list相等，set与set相等
     */
    boolean equals(Object o);

    /**
     * 返回此 collection 的哈希码值。
     */
    int hashCode();


    // 位置访问操作

    /**
     * 返回此列表中指定位置的元素
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    E get(int index);

    /**
     * 使用指定的元素替换此列表中指定位置的元素
     *
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws UnsupportedOperationException if the <tt>set</tt> operation
     *         is not supported by this list
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this list
     * @throws NullPointerException if the specified element is null and
     *         this list does not permit null elements
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    E set(int index, E element);

    /**
     *
     * 将指定元素插入此列表中的指定位置（可选操作）。将当前位置的元素（如果有）和任何后续元素向右移动（将其添加到其索引中）。
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws UnsupportedOperationException if the <tt>add</tt> operation
     *         is not supported by this list
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this list
     * @throws NullPointerException if the specified element is null and
     *         this list does not permit null elements
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt; size()</tt>)
     */
    void add(int index, E element);

    /**
     *
     * 删除此列表中指定位置的元素（可选操作）。将任何后续元素向左移位（从索引中减去一个）。返回从列表中删除的元素。
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *         is not supported by this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    E remove(int index);


    // 搜索操作

    /**
     *
     * 返回此列表中第一次出现的指定元素的索引，如果此列表不包含该元素，则返回-1。更正式地，返回最低指数<tt> i </ tt>，使得<tt>（o == null＆nbsp;？＆nbsp; get（i）== null＆nbsp;：＆nbsp; o.equals（get（i））） </ tt>，如果没有这样的索引，则为-1。
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in
     *         this list, or -1 if this list does not contain the element
     * @throws ClassCastException if the type of the specified element
     *         is incompatible with this list
     *         (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *         list does not permit null elements
     *         (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    int indexOf(Object o);

    /**
     *
     * 返回此列表中指定元素最后一次出现的索引，如果此列表不包含该元素，则返回-1。更正式地，返回最高指数<tt> i </ tt>，使得<tt>（o == null＆nbsp;？＆nbsp; get（i）== null＆nbsp;：＆nbsp; o.equals（get（i））） </ tt>，如果没有这样的索引，则为-1。     *
     *
     * @param o element to search for
     * @return the index of the last occurrence of the specified element in
     *         this list, or -1 if this list does not contain the element
     * @throws ClassCastException if the type of the specified element
     *         is incompatible with this list
     *         (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *         list does not permit null elements
     *         (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    int lastIndexOf(Object o);


    // 集合 迭代器

    /**
     *
     * 返回此列表中元素的列表迭代器（按正确顺序）。
     * @return 列表中的元素列表迭代器（按正确顺序）
     */
    ListIterator<E> listIterator();

    /**
     * 从列表中的指定位置开始，返回列表中元素的列表迭代器（按正确顺序）。
     * 指定的索引表示初始调用{@link ListIterator＃next next}将返回的第一个元素。
     * 对{@link ListIterator＃previous previous}的初始调用将返回指定索引减去1的元素。
     *
     * @param index index of the first element to be returned from the
     *        list iterator (by a call to {@link ListIterator#next next})
     * @return a list iterator over the elements in this list (in proper
     *         sequence), starting at the specified position in the list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index > size()})
     */
    ListIterator<E> listIterator(int index);

    // View

    /**
     * 返回一个以fromIndex为起始索引（包含），以toIndex为终止索引（不包含）的子列表（List）。
     *
     * subList包括fromIndex，但不包括toIndex。
     *
     * 但值得注意的是，返回的这个子列表的幕后其实还是原列表；也就是说，修改这个子列表，将导致原列表也发生改变；反之亦然。
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex high endpoint (exclusive) of the subList
     * @return a view of the specified range within this list
     * @throws IndexOutOfBoundsException for an illegal endpoint index value
     *         (<tt>fromIndex &lt; 0 || toIndex &gt; size ||
     *         fromIndex &gt; toIndex</tt>)
     */
    List<E> subList(int fromIndex, int toIndex);

    /**
     *
     * Spliterator是一个可分割迭代器(splitable iterator)，可以和iterator顺序遍历迭代器一起看。jdk1.8发布后，对于并行处理的能力大大增强，Spliterator就是为了并行遍历元素而设计的一个迭代器，jdk1.8中的集合框架中的数据结构都默认实现了spliterator
     *
     * Creates a {@link Spliterator} over the elements in this list.
     *
     * <p>The {@code Spliterator} reports {@link Spliterator#SIZED} and
     * {@link Spliterator#ORDERED}.  Implementations should document the
     * reporting of additional characteristic values.
     *
     * @implSpec
     * The default implementation creates a
     * <em><a href="Spliterator.html#binding">late-binding</a></em> spliterator
     * from the list's {@code Iterator}.  The spliterator inherits the
     * <em>fail-fast</em> properties of the list's iterator.
     *
     * @implNote
     * The created {@code Spliterator} additionally reports
     * {@link Spliterator#SUBSIZED}.
     *
     * @return a {@code Spliterator} over the elements in this list
     * @since 1.8
     */
    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, Spliterator.ORDERED);
    }
}

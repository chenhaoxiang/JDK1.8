/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
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

/**
 * 列表的迭代器，允许程序员在任一方向遍历列表，
 * 在迭代期间修改列表，
 * 并获取迭代器在列表中的当前位置。
 * {@code ListIterator}没有当前元素;
 * 它的<I>光标位置</ I>总是位于调用{@code previous（）}返回的元素和调用{@code next（）}返回的元素之间。
 * An iterator for a list of length {@code n} has {@code n+1} possible cursor positions, as illustrated by the carets ({@code ^}) below:
 * <PRE>
 *                      Element(0)   Element(1)   Element(2)   ... Element(n-1)
 * cursor positions:  ^            ^            ^            ^                  ^
 * </PRE>
 * Note that the {@link #remove} and {@link #set(Object)} methods are
 * <i>not</i> defined in terms of the cursor position;  they are defined to
 * operate on the last element returned by a call to {@link #next} or
 * {@link #previous()}.
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author  Josh Bloch
 * @see Collection
 * @see List
 * @see Iterator
 * @see Enumeration
 * @see List#listIterator()
 * @since   1.2
 */
public interface ListIterator<E> extends Iterator<E> {
    // Query Operations

    /**
     * 如果此列表迭代器在向前遍历列表时具有更多元素，则返回{@code true}。 （换句话说，如果{@link #next}返回一个元素而不是抛出异常，则返回{@code true}。）
     *
     * @return {@code true} if the list iterator has more elements when
     *         traversing the list in the forward direction
     */
    boolean hasNext();

    /**
     * 返回列表中的下一个元素并前进光标位置。也就是先后遍历
     *  可以重复调用此方法以遍历列表，或者与调用{@link #previous}混合来来回传递。
     *  请注意，对{@code next}和{@code previous}的交替调用将重复返回相同的元素。）
     * @return the next element in the list
     * @throws NoSuchElementException if the iteration has no next element
     */
    E next();

    /**
     * 如果此列表迭代器在反向遍历列表时具有更多元素，则返回{@code true}。 （换句话说，如果{@link #previous}返回一个元素而不是抛出异常，则返回{@code true}。）
     *
     * @return {@code true} if the list iterator has more elements when
     *         traversing the list in the reverse direction
     */
    boolean hasPrevious();

    /**
     *返回列表中的上一个元素并向后移动光标位置。逆序遍历集合
     *可以反复调用此方法以向后遍历列表，或者与{@link #next}的调用混合来回来。
     *（请注意，对{@code next}和{@code previous}的交替调用将重复返回相同的元素。）
     * @return the previous element in the list
     * @throws NoSuchElementException if the iteration has no previous
     *         element
     */
    E previous();

    /**
     * 返回后续调用{@link #next}将返回的元素的索引。 （如果列表迭代器位于列表的末尾，则返回列表大小。）
     *
     * @return the index of the element that would be returned by a
     *         subsequent call to {@code next}, or list size if the list
     *         iterator is at the end of the list
     */
    int nextIndex();

    /**
     * 返回后续调用{@link #previous}将返回的元素的索引。 （如果列表迭代器位于列表的开头，则返回-1。）
     *
     * @return the index of the element that would be returned by a
     *         subsequent call to {@code previous}, or -1 if the list
     *         iterator is at the beginning of the list
     */
    int previousIndex();


    // Modification Operations

    /**
     * 从列表中删除{@link #next}或{@link #previous}（可选操作）返回的最后一个元素。
     * 此方法只能在每次调用{@code next}或{@code previous}后调用一次。
     * 只有在最后一次调用{@code next}或{@code previous}之后未调用{@link #add}时才能进行此操作。
     * @throws UnsupportedOperationException if the {@code remove}
     *         operation is not supported by this list iterator
     * @throws IllegalStateException if neither {@code next} nor
     *         {@code previous} have been called, or {@code remove} or
     *         {@code add} have been called after the last call to
     *         {@code next} or {@code previous}
     */
    void remove();

    /**
     * 用指定的元素替换{@link #next}或{@link #previous}返回的最后一个元素（可选操作）。具体替换看实现
     * 只有在最后一次调用{@code next}或{@code previous}之后才调用{@link #remove}和{@link #add}，才能进行此调用。
     * @param e the element with which to replace the last element returned by
     *          {@code next} or {@code previous}
     * @throws UnsupportedOperationException if the {@code set} operation
     *         is not supported by this list iterator
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this list
     * @throws IllegalArgumentException if some aspect of the specified
     *         element prevents it from being added to this list
     * @throws IllegalStateException if neither {@code next} nor
     *         {@code previous} have been called, or {@code remove} or
     *         {@code add} have been called after the last call to
     *         {@code next} or {@code previous}
     */
    void set(E e);

    /**
     * 将指定的元素插入列表（可选操作）。
     * 元素将紧接在{@link #next}返回的元素之前插入（如果有），并插入{@link #previous}返回的元素之后（如果有）。
     *（如果列表中不包含任何元素，则新元素将成为列表中的唯一元素。）
     * 在隐式游标之前插入新元素：对{@code next}的后续调用不受影响，随后对{@code previous}的调用将返回新元素。
     *（此调用将调用{@code nextIndex}或{@code previousIndex}返回的值增加1。具体应该看实现怎么操作的）
     * @param e the element to insert
     * @throws UnsupportedOperationException if the {@code add} method is
     *         not supported by this list iterator
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this list
     * @throws IllegalArgumentException if some aspect of this element
     *         prevents it from being added to this list
     */
    void add(E e);
}

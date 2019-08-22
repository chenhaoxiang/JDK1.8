/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.function.Consumer;

/**
 *
 * 为了方便的处理集合中的元素,Java中出现了一个对象,该对象提供了一些方法专门处理集合中的元素.例如删除和获取集合中的元素.该对象就叫做迭代器(Iterator).
 *
 * 对 Collection 进行迭代的类，称其为迭代器。还是面向对象的思想，专业对象做专业的事情，迭代器就是专门取出集合元素的对象。但是该对象比较特殊，不能直接创建对象（通过new），该对象是以内部类的形式存在于每个集合类的内部。
 *
 * Iterator 为一个接口，它只提供了迭代了基本规则，在 JDK 中他是这样定义的：对 collection 进行迭代的迭代器。迭代器取代了 Java Collections Framework 中的 Enumeration。
 * Iterator是Java迭代器最简单的实现，为List设计的ListIterator具有更多的功能，它可以从两个方向遍历List，也可以从List中插入和删除元素。
 *
 *
 *
 * 迭代其实我们可以简单地理解为遍历，是一个标准化遍历各类容器里面的所有对象的方法类，它是一个很典型的设计模式。
 * Iterator 模式是用于遍历集合类的标准访问方法。它可以把访问逻辑从不同类型的集合类中抽象出来，从而避免向客户端暴露集合的内部结构。
 *
 * 如何获取迭代器？Collection接口中定义了获取集合类迭代器的方法（iterator（）），所以所有的Collection体系集合都可以获取自身的迭代器。
 *
 * 简单理解：Iterator迭代器接口用于访问集合中的每一个元素
 *
 * 关于该接口的实现，在集合的实现类中还会再讲到，例如，ArrayList中的Itr
 *
 * @param <E> 此迭代器返回的元素类型
 *
 * @author  Josh Bloch
 * @see Collection
 * @see ListIterator
 * @see Iterable
 * @since 1.2
 */
public interface Iterator<E> {
    /**
     * 判断集合中是否有元素，如果有元素可以迭代，就返回true。
     */
    boolean hasNext();

    /**
     * 返回迭代的下一个元素，注意： 如果没有下一个元素时，调用
     * next方法时，会抛出NoSuchElementException
     */
    E next();

    /**
     * 从迭代器指向的集合中移除迭代器返回的最后一个元素（可选操作）。
     */
    default void remove() {
        throw new UnsupportedOperationException("remove");
    }

    /**
     * 为每个剩余元素执行给定的操作,直到所有的元素都已经被处理或行动抛出一个异常。
     * Java Stream的处理在这里不进行详解，后续会有文章专门介绍
     *
     * @param action 要为每个元素执行的操作
     * @throws NullPointerException 如果指定的操作为null
     * @since 1.8
     */
    default void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        while (hasNext())
            action.accept(next());
    }
}

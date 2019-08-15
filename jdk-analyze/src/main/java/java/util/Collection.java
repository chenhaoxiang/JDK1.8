/*
 * 20190815
 * @Translator chenhx
 */
package java.util;

import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 集合层次结构的根接口，一个集合表示一组对象，称为元素
 * JDK不提供任何该接口的直接实现，JDK提供实现特定情况的子接口，Set，List
 *
 * 所有通用Collection实现类通常需要通过一个间接实现Collection接口的子接口来实现，
 * 而且需要提供两个标准的构造函数，没有参数的构造函数（空参构造），创建一个空集合；
 * 以及包含Collection类型的单个参数的构造函数，使用与其参数相同的元素创建一个新集合。
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see     Set
 * @see     List
 * @see     Map
 * @see     SortedSet
 * @see     SortedMap
 * @see     HashSet
 * @see     TreeSet
 * @see     ArrayList
 * @see     LinkedList
 * @see     Vector
 * @see     Collections
 * @see     Arrays
 * @see     AbstractCollection
 * @since 1.2
 */
public interface Collection<E> extends Iterable<E> {
    // 查询操作

    /**
     * 返回此集合中的元素数。如果这个集合 包含多于<tt> Integer.MAX_VALUE </ tt>的元素，返回 <tt> Integer.MAX_VALUE </ tt>。
     *
     * @return 此集合中的元素数量
     */
    int size();

    /**
     * 如果此集合不包含任何元素，则返回<tt> true </ tt>
     * @return <tt>true</tt> 如果此集合不包含任何元素
     */
    boolean isEmpty();

    /**
     *
     * 如果集合中包含至少一个指定对象,返回true
     * @param o 要测试其在此集合中的存在的元素
     * @return <tt>true</tt>  如果集合中包含该元素
     *
     * @throws ClassCastException 如果指定元素的类型 与集合内元素类型不兼容
     * @throws NullPointerException 如果指定的元素为null且此 collection不允许null元素
     *
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


    // 批量操作

    /**
     * 如果此 collection 包含指定 collection 中的所有元素，则返回 true。
     */
    boolean containsAll(Collection<?> c);

    /**
     * 将指定 collection 中的所有元素都添加到此 collection 中
     */
    boolean addAll(Collection<? extends E> c);

    /**
     * 移除此 collection 中那些也包含在指定 collection 中的所有元素（可选操作）。
     */
    boolean removeAll(Collection<?> c);

    /**
     *
     * 删除此集合中满足给定布尔值函数的所有元素。在迭代期间或通过布尔值函数抛出的错误或运行时异常被中继到调用者。
     * 默认使用迭代器进行删除元素
     *
     * @param filter 一个布尔值函数，它返回{@code true}表示要删除的元素
     * @return {@code true} 如果删除了任何元素
     * @since 1.8
     */
    default boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final Iterator<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

    /**
     *
     * 仅保留此集合中包含在指定集合中的元素（可选操作）。换句话说，从此集合中删除未包含在指定集合中的所有元素。
     */
    boolean retainAll(Collection<?> c);

    /**
     * 移除此 collection 中的所有元素（可选操作）。
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

    /**
     *
     * 在此集合中的元素上创建{@link Spliterator}。
     * TODO 后续如果讲到了{@link Spliterator}再进行补充下面的三个方法
     * Creates a {@link Spliterator} over the elements in this collection.
     *
     * Implementations should document characteristic values reported by the
     * spliterator.  Such characteristic values are not required to be reported
     * if the spliterator reports {@link Spliterator#SIZED} and this collection
     * contains no elements.
     *
     * <p>The default implementation should be overridden by subclasses that
     * can return a more efficient spliterator.  In order to
     * preserve expected laziness behavior for the {@link #stream()} and
     * {@link #parallelStream()}} methods, spliterators should either have the
     * characteristic of {@code IMMUTABLE} or {@code CONCURRENT}, or be
     * <em><a href="Spliterator.html#binding">late-binding</a></em>.
     * If none of these is practical, the overriding class should describe the
     * spliterator's documented policy of binding and structural interference,
     * and should override the {@link #stream()} and {@link #parallelStream()}
     * methods to create streams using a {@code Supplier} of the spliterator,
     * as in:
     * <pre>{@code
     *     Stream<E> s = StreamSupport.stream(() -> spliterator(), spliteratorCharacteristics)
     * }</pre>
     * <p>These requirements ensure that streams produced by the
     * {@link #stream()} and {@link #parallelStream()} methods will reflect the
     * contents of the collection as of initiation of the terminal stream
     * operation.
     *
     * @implSpec
     * The default implementation creates a
     * <em><a href="Spliterator.html#binding">late-binding</a></em> spliterator
     * from the collections's {@code Iterator}.  The spliterator inherits the
     * <em>fail-fast</em> properties of the collection's iterator.
     * <p>
     * The created {@code Spliterator} reports {@link Spliterator#SIZED}.
     *
     * @implNote
     * The created {@code Spliterator} additionally reports
     * {@link Spliterator#SUBSIZED}.
     *
     * <p>If a spliterator covers no elements then the reporting of additional
     * characteristic values, beyond that of {@code SIZED} and {@code SUBSIZED},
     * does not aid clients to control, specialize or simplify computation.
     * However, this does enable shared use of an immutable and empty
     * spliterator instance (see {@link Spliterators#emptySpliterator()}) for
     * empty collections, and enables clients to determine if such a spliterator
     * covers no elements.
     *
     * @return a {@code Spliterator} over the elements in this collection
     * @since 1.8
     */
    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, 0);
    }

    /**
     * Returns a sequential {@code Stream} with this collection as its source.
     *
     * <p>This method should be overridden when the {@link #spliterator()}
     * method cannot return a spliterator that is {@code IMMUTABLE},
     * {@code CONCURRENT}, or <em>late-binding</em>. (See {@link #spliterator()}
     * for details.)
     *
     * @implSpec
     * The default implementation creates a sequential {@code Stream} from the
     * collection's {@code Spliterator}.
     *
     * @return a sequential {@code Stream} over the elements in this collection
     * @since 1.8
     */
    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * Returns a possibly parallel {@code Stream} with this collection as its
     * source.  It is allowable for this method to return a sequential stream.
     *
     * <p>This method should be overridden when the {@link #spliterator()}
     * method cannot return a spliterator that is {@code IMMUTABLE},
     * {@code CONCURRENT}, or <em>late-binding</em>. (See {@link #spliterator()}
     * for details.)
     *
     * @implSpec
     * The default implementation creates a parallel {@code Stream} from the
     * collection's {@code Spliterator}.
     *
     * @return a possibly parallel {@code Stream} over the elements in this
     * collection
     * @since 1.8
     */
    default Stream<E> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
}

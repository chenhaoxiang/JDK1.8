[集合]AbstractCollection源码解析(2)

前面文章介绍了Collection接口，现在介绍该接口的子类AbstractCollection类。  

AbstractCollection是Java集合框架中Collection接口 的一个直接实现类，Collection下的大多数子类都继承 AbstractCollection，比如List的实现类, Set的实现类。    

从名字可以看出来，AbstractCollection是一个抽象类，仅仅对于某些方法进行了默认实现,对于一些抽象方法，留个了子类进行实现，所以本篇也仅仅对AbstractCollection接口的默认实现进行讲解。  

# isEmpty()
首先了看isEmpty接口的默认实现  
```java
public boolean isEmpty() {
    return size() == 0;
}
```
一句话理解：  
调用了size()方法进行了大小的判断，不多说，大家都懂。   

# contains(Object o)
这个方法的作用就是判断集合中是否包含指定的对象，只要包含至少一个，就返回true  
```java
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
```
一句话理解：  
获取了集合的元素迭代器，遍历了集合中元素，对元素和对象进行了equals的判断。如果判断对象为null，使用==判断元素中是否有null

# toArray()

```java
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
```
一句话理解：  
准备一个数组，通过迭代器遍历集合，将集合中对象赋值到数组中。若出现元素少于预期的情况，则进行截断数组。若出现元素多于预期的情况，则再重新分配数组，具体的看finishToArray方法。  

# finishToArray(T[] r, Iterator<?> it)  
在toArray方法中，使用了finishToArray方法进行重新分配数组  
```java
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
```
一句话理解：  
在并发情况下，集合元素增多的时候，对于原分配的数组进行扩大，再填充集合中的新增元素  


# toArray(T[] a)
toArray(T[] a)是Java5 引入泛型模板机制后的新调用方法。就是为了明确类型。

```java
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
```
一句话理解：  
根据参数数组的类型，确定构造数组的类型，返回一个确定类型的数组。  

建议使用方式：  
```java
String s[] = collection.toArray(new String[collection.size()]);
```
当然，如果Collection在创建时明确了类型，比如Collection<String>，这里的方法也有了相应的类型。编译器可以检测到类型错误。   

# add(E e)
```java
public boolean add(E e) {
    throw new UnsupportedOperationException();
}
```
不实现add方法，就给你抛异常  


# remove(Object o)
移除集合中的一个元素  
```java
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
```
一句话理解：  
通过迭代器移除集合中的一个与参数相等的元素，注意是一个噢    


# containsAll(Collection<?> c)
按照顺序遍历参数集合，每个元素调用contains进行判断参数集合中的元素在集合中是否存在，只要参数集合中有一个不被包含的元素，则返回false。  


```java
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

```
一句话理解：  
如果此 collection 包含指定 collection 中的所有元素，则返回 true。  

# addAll(Collection<? extends E> c)
将指定 collection 中的所有元素都添加到此 collection 中  
```java
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

```
一句话理解：  
将参数的集合全部添加到现有集合中  


# removeAll(Collection<?> c)
移除当前集合中包含参数集合的所有值  

```java
/**
 * 移除当前集合的元素在参数集合中的存在的元素
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

```
一句话理解：  
移除当前集合的元素在参数集合中的存在的元素，也就是说，只要是参数集合中存在的元素，在当前集合中都要移除  

# retainAll(Collection<?> c)  
和removeAll方法有点相反，仅保留此集合中包含在指定集合中的元素  
```java

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

```
一句话理解：  
从此集合中删除未包含在指定集合中的所有元素  


# clear() 
清空集合，没啥好说的  
```java
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
```
一句话理解：
清空集合中所有元素   

# toString()
集合的字符串输出，从该方法可以看出，直接输出集合是可以输出元素的，但是注意，集合中的元素是否重写了toString方法    

```java
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
```
一句话理解：
集合的字符串输出  

# 扩展点  

## 构造函数 
AbstractCollection 默认的构造函数是 protected。官方推荐子类自己创建一个 无参构造函数。
原话：
```
The programmer should generally provide a void (no argument) and Collection constructor, as per the recommendation in the Collection interface specification. 
```

## 为什么'add'方法在AbstractCollection中不是抽象的？  
AbstractCollection 的 add(E) 方法默认是抛出异常，这样会不会容易导致问题？为什么不定义为抽象方法？  
stackoverflow上有个回答：  
https://stackoverflow.com/questions/23889410/why-is-add-method-not-abstract-in-abstractcollection  

简单的描述：  
- 如果你想修改一个不可变的集合时，抛出 UnsupportedOperationException 是标准的行为，比如 当你用 Collections.unmodifiableXXX() 方法对某个集合进行处理后，再调用这个集合的 修改方法（add,remove,set…），都会报这个错；
因此 AbstractCollection.add(E) 抛出这个错误是遵从标准；

AbstractCollection指出要实现不可修改的集合，程序员只需要扩展此类并提供iterator和size方法的实现。（该iterator方法返回的迭代器必须实现hasNext和next。）    
要实现可修改的集合，程序员必须另外覆盖此类的add方法（否则抛出一个UnsupportedOperationException），并且iterator方法返回的迭代器必须另外实现其remove方法。  

并非所有集合都是可变的。此add方法抛出异常的默认实现可以轻松实现不可变集合。

另外，在 Java 集合中，还有很多方法都提供了有用的默认行为，比如：
- Iterator.remove()
- AbstractList.add(int, E)
- AbstractList.set(int, E)
- AbstractList.remove(int)
- AbstractMap.put(K, V)
- AbstractMap.SimpleImmutableEntry.setValue(V)


而之所以没有定义为 抽象方法，是因为可能有很多地方用不到这个方法，用不到还必须实现，这岂不是让人很困惑么。   
这跟设计模式中的接口隔离原则有些相似。  

接口隔离原则后续会讲到  

# AbstractCollection源码解析地址  
https://github.com/chenhaoxiang/JDK1.8/blob/master/jdk-analyze/src/main/java/java/util/AbstractCollection.java  

小手点点，实时收获源码解析，感谢您的star  

# 下集预告：  
从AbstractCollection抽象类的方法源码来看，非常多的操作都是通过迭代器来进行的。  
可以看出Iterator的重要性，打算下章进行讲解。  

## 插播广告：   
未关注的贝贝可以来波关注啦。后续更多精彩内容等着您。  

![程序编程之旅](https://github.com/chenhaoxiang/JDK1.8/blob/master/doc/images/cxbczl.jpg?raw=true)   
感谢关注  
 
 
吾非大神，与汝俱进


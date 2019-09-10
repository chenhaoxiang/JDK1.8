[集合]Collection源码解析(1)

原文地址：  
[https://copyfuture.com/blogs-details/20190910135225856szh6cv83a7xawks](https://copyfuture.com/blogs-details/20190910135225856szh6cv83a7xawks)

本篇进行介绍Collection接口，不会有很多，防止产生源码恐惧症（别问我哪来的，我瞎编的）。  
    
本篇不会直接上Collection接口的源码，要看源码的，可以移步至github，我专门建立了一个仓库，方便管理后期的一些源码解析和注释。   
# Collection源码解析地址：  
[https://github.com/chenhaoxiang/JDK1.8/blob/master/jdk-analyze/src/main/java/java/util/Collection.java](https://github.com/chenhaoxiang/JDK1.8/blob/master/jdk-analyze/src/main/java/java/util/Collection.java)  

Collection接口是在JDK1.2之后有的，是Java中最基本的集合接口  

一个Collection代表一组对象，即Collection中的元素（Elements）  

Java SDK不会提供直接继承自Collection的类，Java SDK提供的实现类都是继承自Collection的“子接口”如List和Set。    
（Collection接口注释中有说明）  

Collection接口是高度抽象出来的集合，它包含了集合的基本操作：
查询操作、修改操作、批量操作，另外还提供了比较和散列操作（equals和hashCode）；  
涵盖了添加、删除、清空、遍历(读取)、是否为空、获取集合大小、批量处理等等操作。满足通用集合的所有要求了，如果有其他需求，可以自己进行扩展。另外JDK也提供了很多该接口的子接口的实现，基于该接口进行了非常多的扩展，后面文章也会一一道来。      

在JDK1.8，该接口新增了四个有默认实现的方法：  
- default boolean removeIf(Predicate<? super E> filter) 
- default Spliterator<E> spliterator()
- default Stream<E> stream()
- default Stream<E> parallelStream()    
这四个方法能够在接口上进行默认的实现，得益于Java 8使用两个新概念扩展了接口的含义，即默认方法和静态方法。

默认方法使得开发者可以在 不破坏二进制兼容性的前提下，往现存接口中添加新的方法，即不强制那些实现了该接口的类也同时实现这个新加的方法。   

默认方法和抽象方法之间的区别在于抽象方法需要实现，而默认方法不需要。接口提供的默认方法会被接口的实现类继承或者覆写  

由于JVM上的默认方法的实现在字节码层面提供了支持，因此效率非常高。默认方法允许在不打破现有继承体系的基础上改进接口。  

尽管默认方法有这么多好处，但在实际开发中应该谨慎使用：在复杂的继承体系中，默认方法可能引起歧义和编译错误。如果你想了解更多细节，可以参考官方文档的说明：https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html  

这四个接口暂时不会进行讲解，这是JDK8的新特性。后续如果讲到了Spliterator接口再进行补充这几个方法   

其实接口的源码没啥好说的，就是定义了一些接口，无论是JDK自己扩展，还是开发者自行扩展，那都是实现的事了。所以本篇就到这里，要了解Collection接口的方法有哪些，请看Collection源码解析地址：  
[https://github.com/chenhaoxiang/JDK1.8/blob/master/jdk-analyze/src/main/java/java/util/Collection.java](https://github.com/chenhaoxiang/JDK1.8/blob/master/jdk-analyze/src/main/java/java/util/Collection.java)    

不过可以道一下Collection接口的继承关系，比较它的常用子类后面都会通过文章一一讲解。  

继承Collection接口，非常重要的接口有两个，List和Set。    

List是有序的队列，可以有重复的元素；而Set是数学概念中的集合，不能有重复的元素。List和Set都有它们各自的实现类。  

为了方便，JDK又抽象出AbstractCollection类来让其他类来进行继承，该类实现了接口Collection中的绝大部分方法。

AbstractList和AbstractSet都继承与AbstractCollection，具体的List实现类继承与AbstractList，而Set的实现类则继承与AbstractSet。  
图的话，我就不画了，想偷懒~！~ 大家脑海中想象一下结构就好。后面也还会说到这些结构。   

为啥会有Collection这个接口，这个应该就和接口的作用有点关系了。简单的描述下就是：接口将使用接口的人和实现接口的人分开。也就是说，实现接口的人不用管谁使用接口，而使用接口的人也不需要管谁进行实现，使用接口即可。   

和IoC思想某个方面还是有点像的。解耦解耦还是解耦。圈起来，要考的。      

关于集合的源码讲解，开头就是到这里了，后面更加精彩   

记得去看Collection源码解析地址噢：
https://github.com/chenhaoxiang/JDK1.8/blob/master/jdk-analyze/src/main/java/java/util/Collection.java

不介意的话，可以来个star，后续会持续更新  

忘记说了，Collection继承了Iterator接口，Iterator接口是JDK1.5之后才有的，后面再徐徐道来  
（源码解析基于JDK1.8版本）   

## 下集预告：  
不出意外的话，是AbstractCollection类的解析  

## 插播广告：   
未关注的贝贝可以来波关注啦。后续更多精彩内容等着您。  

![程序编程之旅](https://github.com/chenhaoxiang/JDK1.8/blob/master/doc/images/cxbczl.jpg?raw=true)  

吾非大神，与汝俱进



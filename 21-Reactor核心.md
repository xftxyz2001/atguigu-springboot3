[1、响应式编程-前置知识.pptx](https://www.yuque.com/attachments/yuque/0/2023/pptx/1613913/1700145197412-ce0be9e0-cf2f-4ef7-b644-aa706f9a476e.pptx)

# 前置知识
## 1、Lambda
Java8**语法糖**：
```java
package com.atguiggu.lambda;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * @author lfy
 * @Description
 * @create 2023-11-16 20:07
 */

//函数式接口；只要是函数式接口就可以用Lambda表达式简化
//函数式接口： 接口中有且只有一个未实现的方法，这个接口就叫函数式接口


interface MyInterface {
    int sum(int i, int j);
}

interface MyHaha {
    int haha();

    default int heihei() {
        return 2;
    }

    ; //默认实现
}

interface My666 {
    void aaa(int i,int j,int k);
}


@FunctionalInterface //检查注解，帮我们快速检查我们写的接口是否函数式接口
interface MyHehe {
    int hehe(int i);


}

//1、自己写实现类
class MyInterfaceImpl implements MyInterface {
    @Override
    public int sum(int i, int j) {
        return i + j;
    }
}


public class Lambda {

    public static void main(String[] args) {
        //声明一个函数
        BiConsumer<String,String> consumer = (a,b)->{
            System.out.println("哈哈："+a+"；呵呵："+b);
        };
        consumer.accept("1","2");



        //声明一个函数
        Function<String,Integer> function = (String x) -> Integer.parseInt(x);
        System.out.println(function.apply("2"));


        Supplier<String> supplier = ()-> UUID.randomUUID().toString();
        String s = supplier.get();
        System.out.println(s);


        BiFunction<String,Integer,Long> biFunction = (a,b)-> 888L;

        Predicate<Integer> even = (t)-> t%2 ==0;

//        even.test()//正向判断
//        even.negate().test(2) //反向判断
        System.out.println(even.negate().test(2));


    }


    public static void bbbbb(String[] args) {
        var names = new ArrayList<String>();

        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");
        names.add("David");


        //比较器
//        Collections.sort(names, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o2.compareTo(o1);
//            }
//        });


        //直接写函数式接口就方便   (o1,o2)->o1.compareTo(o2)
//        Collections.sort(names,(o1,o2)->o1.compareTo(o2));
        System.out.println(names);


        // 类::方法； 引用类中的实例方法； 忽略lambda的完整写法
        Collections.sort(names,String::compareTo);
        System.out.println(names);




        new  Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("哈哈啊");
                    }
                }
        ).start();

        Runnable runnable = () -> System.out.println("aaa");

        new Thread(runnable).start();



        //最佳实战：
        //1、以后调用某个方法传入参数，这个参数实例是一个接口对象，且只定义了一个方法，就直接用lambda简化写法


    }


    /**
     * lambda简化函数式接口实例创建
     *
     * @param args
     */
    public static void aaaa(String[] args) {

        //1、自己创建实现类对象
        MyInterface myInterface = new MyInterfaceImpl();
        System.out.println(myInterface.sum(1, 2));

        //2、创建匿名实现类
        MyInterface myInterface1 = new MyInterface() {
            @Override
            public int sum(int i, int j) {
                return i * i + j * j;
            }
        };
//        System.out.println(myInterface1.sum(2, 3));
        //冗余写法

        //3、lambda表达式:语法糖  参数列表  + 箭头 + 方法体
        MyInterface myInterface2 = (x, y) -> {
            return x * x + y * y;
        };
        System.out.println(myInterface2.sum(2, 3));


        //参数位置最少情况
        MyHaha myHaha = () -> {
            return 1;
        };

        MyHehe myHehe = y -> {
            return y * y;
        };


        MyHehe hehe2 = y -> y - 1;

        //完整写法如上：
        //简化写法：
        //1)、参数类型可以不写，只写(参数名)，参数变量名随意定义;
        //    参数表最少可以只有一个 ()，或者只有一个参数名；
        //2、方法体如果只有一句话，{} 可以省略


        MyHehe hehe3 = y -> y + 1;
        System.out.println(hehe3.hehe(7));
        //以上Lambda表达式简化了实例的创建。


        //总结：
        // 1、Lambda表达式： (参数表) -> {方法体}
        // 2、分辨出你的接口是否函数式接口。 函数式接口就可以lambda简化


    }


}

```

## 2、Function
函数式接口的出入参定义：
1、有入参，无出参【消费者】：  function.accept
```java
        BiConsumer<String,String> function = (a,b)->{ //能接受两个入参
            System.out.println("哈哈："+a+"；呵呵："+b);
        };
        function.accept("1","2");
```
2、有入参，有出参【**多功能函数**】：   function.apply
```java
        Function<String,Integer> function = (String x) -> Integer.parseInt(x);
        System.out.println(function.apply("2"));
```
3、无入参，无出参【**普通函数**】：
```java
        Runnable runnable = () -> System.out.println("aaa");

        new Thread(runnable).start();
```
4、无入参 ，有出参【**提供者**】：  supplier.get()
```java
        Supplier<String> supplier = ()-> UUID.randomUUID().toString();
        String s = supplier.get();
        System.out.println(s);
```

java.util.function包下的所有function定义：

- Consumer： 消费者
- Supplier： 提供者
- Predicate： 断言

get/test/apply/accept调用的函数方法；


## 3、StreamAPI
最佳实战：以后凡是你写for循环处理数据的统一全部用StreamAPI进行替换；
Stream所有数据和操作被组合成流管道流管道组成：

- 一个数据源（可以是一个数组、集合、生成器函数、I/O管道）
- 零或多个中间操作（将一个流变形成另一个流）
- 一个终止操作（产生最终结果）

### 中间操作：Intermediate Operations

- filter：过滤；  挑出我们用的元素
- map： 映射： 一一映射，a 变成 b
   - mapToInt、mapToLong、mapToDouble
- flatMap：打散、散列、展开、扩维：一对多映射

```java
filter、
map、mapToInt、mapToLong、mapToDouble
flatMap、flatMapToInt、flatMapToLong、flatMapToDouble
mapMulti、mapMultiToInt、mapMultiToLong、mapMultiToDouble、
parallel、unordered、onClose、sequential
distinct、sorted、peek、limit、skip、takeWhile、dropWhile、
```
### 终止操作：Terminal Operation
```java
forEach、forEachOrdered、toArray、reduce、collect、toList、min、
max、count、anyMatch、allMatch、noneMatch、findFirst、findAny、iterator

```




## 4、Reactive-Stream
[2、响应式编程-Reactor核心.pptx](https://www.yuque.com/attachments/yuque/0/2023/pptx/1613913/1700230453410-c21ddb51-bbcf-44c0-862d-d86f1d8f81cd.pptx)
![1.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1700230458143-d5a32c94-40e6-4494-9572-df4fd5c65fd9.png#averageHue=%23f8f6f4&clientId=ud3f0e289-3efc-4&from=paste&height=1313&id=ue1022acc&originHeight=1641&originWidth=831&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=149936&status=done&style=none&taskId=u4180608f-71fc-4246-a708-bd3123b69b3&title=&width=664.8)
# Reactor
## 1、快速上手
### 介绍
Reactor 是一个用于JVM的完全非阻塞的响应式编程框架，具备高效的需求管理（即对 “背压（backpressure）”的控制）能力。它与 Java 8 函数式 API 直接集成，比如 CompletableFuture， Stream， 以及 Duration。它提供了异步序列 API Flux（用于[N]个元素）和 Mono（用于 [0|1]个元素），并完全遵循和实现了“响应式扩展规范”（Reactive Extensions Specification）。
Reactor 的 reactor-ipc 组件还支持非阻塞的进程间通信（inter-process communication, IPC）。 Reactor IPC 为 HTTP（包括 Websockets）、TCP 和 UDP 提供了支持背压的网络引擎，从而适合 应用于微服务架构。并且完整支持响应式编解码（reactive encoding and decoding）。

### 依赖
```xml
<dependencyManagement> 
    <dependencies>
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-bom</artifactId>
            <version>2023.0.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```
```xml
<dependencies>
    <dependency>
        <groupId>io.projectreactor</groupId>
        <artifactId>reactor-core</artifactId> 
        
    </dependency>
    <dependency>
        <groupId>io.projectreactor</groupId>
        <artifactId>reactor-test</artifactId> 
        <scope>test</scope>
    </dependency>
</dependencies>
```


## 2、响应式编程
> 响应式编程是一种关注于**数据流（data streams）**和**变化传递（propagation of change）**的**异步编程**方式。 这意味着它可以用既有的编程语言表达静态（如数组）或动态（如事件源）的数据流。


了解历史：

- 在响应式编程方面，微软跨出了第一步，它在 .NET 生态中创建了响应式扩展库（Reactive Extensions library, Rx）。接着 RxJava 在JVM上实现了响应式编程。后来，在 JVM 平台出现了一套标准的响应式 编程规范，它定义了一系列标准接口和交互规范。并整合到 Java 9 中（使用 Flow 类）。
- 响应式编程通常作为面向对象编程中的“观察者模式”（Observer design pattern）的一种扩展。 响应式流（reactive streams）与“迭代子模式”（Iterator design pattern）也有相通之处， 因为其中也有 Iterable-Iterator 这样的对应关系。主要的区别在于，Iterator 是基于 “拉取”（pull）方式的，而响应式流是基于“推送”（push）方式的。
- 使用 iterator 是一种“命令式”（imperative）编程范式，即使访问元素的方法是 Iterable 的唯一职责。关键在于，什么时候执行 next() 获取元素取决于开发者。在响应式流中，相对应的 角色是 Publisher-Subscriber，但是 _当有新的值到来的时候_ ，却反过来由发布者（Publisher） 通知订阅者（Subscriber），这种“推送”模式是响应式的关键。此外，对推送来的数据的操作 是通过一种声明式（declaratively）而不是命令式（imperatively）的方式表达的：开发者通过 描述“控制流程”来定义对数据流的处理逻辑。
- 除了数据推送，对错误处理（error handling）和完成（completion）信号的定义也很完善。 一个 Publisher 可以推送新的值到它的 Subscriber（调用 onNext 方法）， 同样也可以推送错误（调用 onError 方法）和完成（调用 onComplete 方法）信号。 错误和完成信号都可以终止响应式流。可以用下边的表达式描述：
```r
onNext x 0..N [onError | onComplete]
```

### 2.1. 阻塞是对资源的浪费
现代应用需要**应对大量的并发用户**，而且即使现代硬件的处理能力飞速发展，**软件性能仍然是关键因素**。
广义来说我们有两种思路来提升程序性能：

1. **并行化（parallelize）** ：使用更多的线程和硬件资源。[异步]
2. 基于现有的资源来 **提高执行效率** 。

通常，Java开发者使用阻塞式（blocking）编写代码。这没有问题，在出现性能瓶颈后， 我们可以增加处理线程，线程中同样是阻塞的代码。但是这种使用资源的方式会迅速面临 资源竞争和并发问题。
更糟糕的是，阻塞会浪费资源。具体来说，比如当一个程序面临延迟（通常是I/O方面， 比如数据库读写请求或网络调用），所在线程需要进入 idle 状态等待数据，从而浪费资源。
所以，并行化方式并非银弹。这是挖掘硬件潜力的方式，但是却带来了复杂性，而且容易造成浪费。

### 2.2. 异步可以解决问题吗？
第二种思路——提高执行效率——可以解决资源浪费问题。通过编写 _异步非阻塞_ 的代码， （任务发起异步调用后）执行过程会切换到另一个 **使用同样底层资源** 的活跃任务，然后等 异步调用返回结果再去处理。

但是在 JVM 上如何编写异步代码呢？Java 提供了两种异步编程方式：

- **回调（Callbacks）** ：异步方法没有返回值，而是采用一个 callback 作为参数（lambda 或匿名类），当结果出来后回调这个 callback。常见的例子比如 Swings 的 EventListener。
- **Futures** ：异步方法 **立即** 返回一个 Future<T>，该异步方法要返回结果的是 T 类型，通过 Future封装。这个结果并不是 _立刻_ 可以拿到，而是等实际处理结束才可用。比如， ExecutorService 执行 Callable<T> 任务时会返回 Future 对象。

这些技术够用吗？并非对于每个用例都是如此，两种方式都有局限性。
回调很难组合起来，因为很快就会导致代码难以理解和维护（即所谓的“回调地狱（callback hell）”）。
考虑这样一种情景：

- 在用户界面上显示用户的5个收藏，或者如果没有任何收藏提供5个建议。
- 这需要3个 服务（一个提供收藏的ID列表，第二个服务获取收藏内容，第三个提供建议内容）：

回调地狱（Callback Hell）的例子：
```java
userService.getFavorites(userId, new Callback<List<String>>() { 
  public void onSuccess(List<String> list) { 
    if (list.isEmpty()) { 
      suggestionService.getSuggestions(new Callback<List<Favorite>>() {
        public void onSuccess(List<Favorite> list) { 
          UiUtils.submitOnUiThread(() -> { 
            list.stream()
                .limit(5)
                .forEach(uiList::show); 
            });
        }

        public void onError(Throwable error) { 
          UiUtils.errorPopup(error);
        }
      });
    } else {
      list.stream() 
          .limit(5)
          .forEach(favId -> favoriteService.getDetails(favId, 
            new Callback<Favorite>() {
              public void onSuccess(Favorite details) {
                UiUtils.submitOnUiThread(() -> uiList.show(details));
              }

              public void onError(Throwable error) {
                UiUtils.errorPopup(error);
              }
            }
          ));
    }
  }

  public void onError(Throwable error) {
    UiUtils.errorPopup(error);
  }
});

```

Reactor改造后为：
```java
userService.getFavorites(userId) 
           .flatMap(favoriteService::getDetails) 
           .switchIfEmpty(suggestionService.getSuggestions()) 
           .take(5) 
           .publishOn(UiUtils.uiThreadScheduler()) 
           .subscribe(uiList::show, UiUtils::errorPopup); 
```


如果你想确保“收藏的ID”的数据在800ms内获得（如果超时，从缓存中获取）呢？在基于回调的代码中， 会比较复杂。但 Reactor 中就很简单，在处理链中增加一个 timeout 的操作符即可。
```java
userService.getFavorites(userId)
           .timeout(Duration.ofMillis(800)) 
           .onErrorResume(cacheService.cachedFavoritesFor(userId)) 
           .flatMap(favoriteService::getDetails) 
           .switchIfEmpty(suggestionService.getSuggestions())
           .take(5)
           .publishOn(UiUtils.uiThreadScheduler())
           .subscribe(uiList::show, UiUtils::errorPopup);
```


> 额外扩展：
> Futures 比回调要好一点，但即使在 Java 8 引入了 CompletableFuture，它对于多个处理的组合仍不够好用。 编排多个 Futures 是可行的，但却不易。此外，Future 还有一个问题：当对 Future 对象最终调用 get() 方法时，仍然会导致阻塞，并且缺乏对多个值以及更进一步对错误的处理。
> 考虑另外一个例子，我们首先得到 ID 的列表，然后通过它进一步获取到“对应的 name 和 statistics” 为元素的列表，整个过程用异步方式来实现。
> CompletableFuture 处理组合的例子

```java
CompletableFuture<List<String>> ids = ifhIds(); 

CompletableFuture<List<String>> result = ids.thenComposeAsync(l -> { 
        Stream<CompletableFuture<String>> zip =
                        l.stream().map(i -> { 
                                                 CompletableFuture<String> nameTask = ifhName(i); 
                                                 CompletableFuture<Integer> statTask = ifhStat(i); 

                                                 return nameTask.thenCombineAsync(statTask, (name, stat) -> "Name " + name + " has stats " + stat); 
                                         });
        List<CompletableFuture<String>> combinationList = zip.collect(Collectors.toList()); 
        CompletableFuture<String>[] combinationArray = combinationList.toArray(new CompletableFuture[combinationList.size()]);

        CompletableFuture<Void> allDone = CompletableFuture.allOf(combinationArray); 
        return allDone.thenApply(v -> combinationList.stream()
                                                                                                 .map(CompletableFuture::join) 
                                                                                                 .collect(Collectors.toList()));
});

List<String> results = result.join(); 
assertThat(results).contains(
                                "Name NameJoe has stats 103",
                                "Name NameBart has stats 104",
                                "Name NameHenry has stats 105",
                                "Name NameNicole has stats 106",
                                "Name NameABSLAJNFOAJNFOANFANSF has stats 121");

```
### 2.3. 从命令式编程到响应式编程
类似 Reactor 这样的响应式库的目标就是要弥补上述“经典”的 JVM 异步方式所带来的不足， 此外还会关注一下几个方面：

- **可编排性（Composability）** 以及 **可读性（Readability）**
- 使用丰富的 **操作符** 来处理形如 **流** 的数据
- 在 **订阅（subscribe）** 之前什么都不会发生
- **背压（backpressure）** 具体来说即 _消费者能够反向告知生产者生产内容的速度的能力_
- **高层次** （同时也是有高价值的）的抽象，从而达到 _并发无关_ 的效果

#### 2.3.1. 可编排性与可读性
可编排性，指的是编排多个异步任务的能力。比如我们将前一个任务的结果传递给后一个任务作为输入， 或者将多个任务以分解再汇总（fork-join）的形式执行，或者将异步的任务作为离散的组件在系统中 进行重用。
这种编排任务的能力与代码的可读性和可维护性是紧密相关的。随着异步处理任务数量和复杂度 的提高，编写和阅读代码都变得越来越困难。就像我们刚才看到的，回调模式是简单的，但是缺点 是在复杂的处理逻辑中，回调中会层层嵌入回调，导致 **回调地狱（Callback Hell）** 。你能猜到 （或有过这种痛苦经历），这样的代码是难以阅读和分析的。
Reactor 提供了丰富的编排操作，从而代码直观反映了处理流程，并且所有的操作保持在同一层次 （尽量避免了嵌套）。

#### 2.3.2. 就像装配流水线
你可以想象数据在响应式应用中的处理，就像流过一条装配流水线。Reactor 既是传送带， 又是一个个的装配工或机器人。原材料从源头（最初的 Publisher）流出，最终被加工为成品， 等待被推送到消费者（或者说 Subscriber）。
原材料会经过不同的中间处理过程，或者作为半成品与其他半成品进行组装。如果某处有齿轮卡住， 或者某件产品的包装过程花费了太久时间，相应的工位就可以向上游发出信号来限制或停止发出原材料。


#### 2.3.3. 操作符（Operators）
在 Reactor 中，操作符（operator）就像装配线中的工位（操作员或装配机器人）。**每一个操作符 对 Publisher 进行相应的处理，然后将 Publisher 包装为一个新的 Publisher。**就像一个链条， 数据源自第一个 Publisher，然后顺链条而下，在每个环节进行相应的处理。**最终，一个订阅者 (Subscriber）终结这个过程**。请记住，**在订阅者（Subscriber）订阅（subscribe）到一个 发布者（Publisher）之前，什么都不会发生。**
> **理解了操作符会创建新的 Publisher 实例这一点，能够帮助你避免一个常见的问题， 这种问题会让你觉得处理链上的某个操作符没有起作用。**

虽然响应式流规范（Reactive Streams specification）没有规定任何操作符， 类似 Reactor 这样的响应式库所带来的最大附加价值之一就是提供丰富的操作符。包括基础的转换操作， 到过滤操作，甚至复杂的编排和错误处理操作。


#### 2.3.4. subscribe() 之前什么都不会发生
在 Reactor 中，当你创建了一条 Publisher 处理链，数据还不会开始生成。事实上，你是创建了 一种抽象的对于异步处理流程的描述（从而方便重用和组装）。
当真正“订阅（subscrib）”的时候，你需要将 Publisher 关联到一个 Subscriber 上，然后 才会触发整个链的流动。这时候，Subscriber 会向上游发送一个 request 信号，一直到达源头 的 Publisher。

#### 2.3.5. 背压
向上游传递信号这一点也被用于实现 **背压** ，就像在装配线上，某个工位的处理速度如果慢于流水线 速度，会对上游发送反馈信号一样。
在响应式流规范中实际定义的机制同刚才的类比非常接近：订阅者可以无限接受数据并让它的源头 “满负荷”推送所有的数据，也可以通过使用 request 机制来告知源头它一次最多能够处理 n 个元素。
中间环节的操作也可以影响 request。想象一个能够将每10个元素分批打包的缓存（buffer）操作。 如果订阅者请求一个元素，那么对于源头来说可以生成10个元素。此外预取策略也可以使用了， 比如在订阅前预先生成元素。
**这样能够将“推送”模式转换为“推送+拉取”混合的模式，如果下游准备好了，可以从上游拉取 n 个元素；但是如果上游元素还没有准备好，下游还是要等待上游的推送。**

#### 2.3.6. 热（Hot） vs 冷（Cold）
在 Rx 家族的响应式库中，响应式流分为“热”和“冷”两种类型，区别主要在于响应式流如何 对订阅者进行响应：

- 一个“冷”的序列，指对于每一个 Subscriber，都会收到从头开始所有的数据。如果源头 生成了一个 HTTP 请求，对于每一个订阅都会创建一个新的 HTTP 请求。
- 一个“热”的序列，指对于一个 Subscriber，只能获取从它开始 订阅 _之后_ 发出的数据。不过注意，有些“热”的响应式流可以缓存部分或全部历史数据。 通常意义上来说，一个“热”的响应式流，甚至在即使没有订阅者接收数据的情况下，也可以 发出数据（这一点同 “Subscribe() 之前什么都不会发生”的规则有冲突）。


## 3、核心特性
### 1、Mono和Flux
Mono: 0|1 数据流
Flux: N数据流

响应式流：元素（内容） + 信号（完成/异常）；



### 2、subscribe()
> 自定义流的信号感知回调

```java
flux.subscribe(
        v-> System.out.println("v = " + v), //流元素消费
        throwable -> System.out.println("throwable = " + throwable), //感知异常结束
        ()-> System.out.println("流结束了...") //感知正常结束
);
```

> 自定义消费者

```java
flux.subscribe(new BaseSubscriber<String>() {

            // 生命周期钩子1： 订阅关系绑定的时候触发
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                // 流被订阅的时候触发
                System.out.println("绑定了..."+subscription);

                //找发布者要数据
                request(1); //要1个数据
//                requestUnbounded(); //要无限数据
            }

            @Override
            protected void hookOnNext(String value) {
                System.out.println("数据到达，正在处理："+value);
                request(1); //要1个数据
            }


            //  hookOnComplete、hookOnError 二选一执行
            @Override
            protected void hookOnComplete() {
                System.out.println("流正常结束...");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                System.out.println("流异常..."+throwable);
            }

            @Override
            protected void hookOnCancel() {
                System.out.println("流被取消...");
            }

            @Override
            protected void hookFinally(SignalType type) {
                System.out.println("最终回调...一定会被执行");
            }
        });

```

### 3、流的取消
> 消费者调用 cancle() 取消流的订阅； 
> #### **Disposable**

```java
        Flux<String> flux = Flux.range(1, 10)
                .map(i -> {
                    System.out.println("map..."+i);
                    if(i==9) {
                        i = 10/(9-i); //数学运算异常;  doOnXxx
                    }
                    return "哈哈：" + i;
                }); //流错误的时候，把错误吃掉，转为正常信号


//        flux.subscribe(); //流被订阅; 默认订阅；
//        flux.subscribe(v-> System.out.println("v = " + v));//指定订阅规则： 正常消费者：只消费正常元素


//        flux.subscribe(
//                v-> System.out.println("v = " + v), //流元素消费
//                throwable -> System.out.println("throwable = " + throwable), //感知异常结束
//                ()-> System.out.println("流结束了...") //感知正常结束
//        );


        // 流的生命周期钩子可以传播给订阅者。
        //  a() {
        //      data = b();
        //  }
        flux.subscribe(new BaseSubscriber<String>() {

            // 生命周期钩子1： 订阅关系绑定的时候触发
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                // 流被订阅的时候触发
                System.out.println("绑定了..."+subscription);

                //找发布者要数据
                request(1); //要1个数据
//                requestUnbounded(); //要无限数据
            }

            @Override
            protected void hookOnNext(String value) {
                System.out.println("数据到达，正在处理："+value);
                if(value.equals("哈哈：5")){
                    cancel(); //取消流
                }
                request(1); //要1个数据
            }


            //  hookOnComplete、hookOnError 二选一执行
            @Override
            protected void hookOnComplete() {
                System.out.println("流正常结束...");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                System.out.println("流异常..."+throwable);
            }

            @Override
            protected void hookOnCancel() {
                System.out.println("流被取消...");
            }

            @Override
            protected void hookFinally(SignalType type) {
                System.out.println("最终回调...一定会被执行");
            }
        });
```


### 4、**BaseSubscriber**
自定义消费者，推荐直接编写 **BaseSubscriber 的逻辑；**


### 5、背压（Backpressure ）和请求重塑（Reshape Requests）
#### 1、buffer：缓冲
```java
Flux<List<Integer>> flux = Flux.range(1, 10)  //原始流10个
        .buffer(3)
        .log();//缓冲区：缓冲3个元素: 消费一次最多可以拿到三个元素； 凑满数批量发给消费者
//
//        //一次发一个，一个一个发；
// 10元素，buffer(3)；消费者请求4次，数据消费完成
```

#### 2、limit：限流
```java
Flux.range(1, 1000)
    .log()
    //限流触发，看上游是怎么限流获取数据的
    .limitRate(100) //一次预取30个元素； 第一次 request(100)，以后request(75)
    .subscribe();
```



### 6、以编程方式创建序列-Sink
> Sink.next  
> Sink.complete  

#### 1、同步环境-generate


#### 2、多线程-create


### 7、 handle()
> 自定义流中元素处理规则

```java
   //
        Flux.range(1,10)
                .handle((value,sink)->{
                    System.out.println("拿到的值："+value);
                    sink.next("张三："+value); //可以向下发送数据的通道
                })
                .log() //日志
                .subscribe();
```



### 8、自定义线程调度
> 响应式：响应式编程： 全异步、消息、事件回调
> 默认还是用当前线程，生成整个流、发布流、流操作


```java
public void thread1(){
    Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

    final Flux<String> flux = Flux
            .range(1, 2)
            .map(i -> 10 + i)
            .log()
            .publishOn(s)
            .map(i -> "value " + i)
            ;

    //只要不指定线程池，默认发布者用的线程就是订阅者的线程；
    new Thread(() -> flux.subscribe(System.out::println)).start();
}
```


### 9、错误处理
命令式编程：常见的错误处理方式
#### Catch and return a static default value. 捕获异常返回一个静态默认值
```java
try {
  return doSomethingDangerous(10);
}
catch (Throwable error) {
  return "RECOVERED";
}
```
> onErrorReturn: 实现上面效果，错误的时候返回一个值
> - 1、吃掉异常，消费者无异常感知
> - 2、返回一个兜底默认值
> - 3、流正常完成；


```java
        Flux.just(1, 2, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorReturn(NullPointerException.class,"哈哈-6666")
                .subscribe(v-> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        ()-> System.out.println("流结束")); // error handling example
```



#### Catch and execute an alternative path with a fallback method.
吃掉异常，执行一个兜底方法；
```java
try {
  return doSomethingDangerous(10);
}
catch (Throwable error) {
  return doOtherthing(10);
}
```
> onErrorResume
> - 1、吃掉异常，消费者无异常感知
> - 2、调用一个兜底方法
> - 3、流正常完成

```java
        Flux.just(1, 2, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i)).onErrorResume(err -> Mono.just("哈哈-777"))
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
```

#### Catch and dynamically compute a fallback value. 捕获并动态计算一个返回值
> 根据错误返回一个新值

```java
try {
  Value v = erroringMethod();
  return MyWrapper.fromValue(v);
}
catch (Throwable error) {
  return MyWrapper.fromError(error);
}
```
```java
.onErrorResume(err -> Flux.error(new BusinessException(err.getMessage()+"：炸了")))
```

- 1、吃掉异常，消费者有感知
- 2、调用一个自定义方法
- 3、流异常完成
#### Catch, wrap to a BusinessException, and re-throw.
捕获并包装成一个业务异常，并重新抛出
```java
try {
  return callExternalService(k);
}
catch (Throwable error) {
  throw new BusinessException("oops, SLA exceeded", error);
}
```
> 包装重新抛出异常:  推荐用  .onErrorMap
> - 1、吃掉异常，消费者有感知 
> - 2、抛新异常
> - 3、流异常完成

```java
.onErrorResume(err -> Flux.error(new BusinessException(err.getMessage()+"：炸了")))

        Flux.just(1, 2, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorMap(err-> new BusinessException(err.getMessage()+": 又炸了..."))
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
```



#### Catch, log an error-specific message, and re-throw.
捕获异常，记录特殊的错误日志，重新抛出
```java
try {
  return callExternalService(k);
}
catch (RuntimeException error) {
  //make a record of the error
  log("uh oh, falling back, service failed for key " + k);
  throw error;
}
```
```java
        Flux.just(1, 2, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .doOnError(err -> {
                    System.out.println("err已被记录 = " + err);
                }).subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
```
> - 异常被捕获、做自己的事情
> - 不影响异常继续顺着流水线传播
> - 1、不吃掉异常，只在异常发生的时候做一件事，消费者有感知 



#### Use the finally block to clean up resources or a Java 7 “try-with-resource” construct.
```java
        Flux.just(1, 2, 3, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .doOnError(err -> {
                    System.out.println("err已被记录 = " + err);
                })
                .doFinally(signalType -> {
                    System.out.println("流信号："+signalType);
                })
```

#### 忽略当前异常，仅通知记录，继续推进
```java
Flux.just(1,2,3,0,5)
        .map(i->10/i)
        .onErrorContinue((err,val)->{
            System.out.println("err = " + err);
            System.out.println("val = " + val);
            System.out.println("发现"+val+"有问题了，继续执行其他的，我会记录这个问题");
        }) //发生
        .subscribe(v-> System.out.println("v = " + v),
                err-> System.out.println("err = " + err));
```




### 10：常用操作
filter、flatMap、concatMap、flatMapMany、transform、defaultIfEmpty、switchIfEmpty、concat、concatWith、merge、mergeWith、mergeSequential、zip、zipWith...


今日内容：

- **常用操作**
- **错误处理**
- **超时与重试**
- **Sinks工具类**
   - **单播**
   - **多播**
   - **重放**
   - **背压**
   - **缓存**
- **阻塞式API**
   - **block**
- **Context-API**：响应式中的ThreadLocal
   - ThreadLocal机制失效
```java
        Flux.just(1,2,3)
                .transformDeferredContextual((flux,context)->{
                    System.out.println("flux = " + flux);
                    System.out.println("context = " + context);
                    return flux.map(i->i+"==>"+context.get("prefix"));
                })
                //上游能拿到下游的最近一次数据
                .contextWrite(Context.of("prefix","哈哈"))
                //ThreadLocal共享了数据，上游的所有人能看到; Context由下游传播给上游
                .subscribe(v-> System.out.println("v = " + v));
```

- **ParallelFlux**：
   - 并发流
```java
        Flux.range(1,1000000)
                .buffer(100)
                .parallel(8)
                .runOn(Schedulers.newParallel("yy"))
.log()
.subscribe();
```

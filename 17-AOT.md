# 1. AOT与JIT
**AOT**：Ahead-of-Time（提前**编译**）：**程序执行前**，全部被编译成**机器码**
**JIT**：Just in Time（即时**编译**）: 程序边**编译**，边运行；
**编译： **

- **源代码（.c、.cpp、.go、.java。。。） ===编译===  机器码**



**语言：**

- **编译型语言：编译器**
- **解释型语言：解释器**
## 1. Complier 与 Interpreter
Java：**半编译半解释**
[https://anycodes.cn/editor](https://anycodes.cn/editor)
![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1683766057952-6f218ecf-4d0a-44ee-a930-1fc7f19085db.png#averageHue=%23f9f9f8&clientId=u77a336a4-0fa2-4&from=paste&height=339&id=wIelI&originHeight=424&originWidth=920&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=63164&status=done&style=none&taskId=u2a07c86f-8c80-4ac2-b42b-f442cb8e02b&title=&width=736)

| 对比项 | **编译器** | **解释器** |
| --- | --- | --- |
| **机器执行速度** | **快**，因为源代码只需被转换一次 | **慢**，因为每行代码都需要被解释执行 |
| **开发效率** | **慢**，因为需要耗费大量时间编译 | **快**，无需花费时间生成目标代码，更快的开发和测试 |
| **调试** | **难以调试**编译器生成的目标代码 | **容易调试**源代码，因为解释器一行一行地执行 |
| **可移植性（跨平台）** | 不同平台需要重新编译目标平台代码 | 同一份源码可以跨平台执行，因为每个平台会开发对应的解释器 |
| **学习难度** | 相对较高，需要了解源代码、编译器以及目标机器的知识 | 相对较低，无需了解机器的细节 |
| **错误检查** | 编译器可以在编译代码时检查错误 | 解释器只能在执行代码时检查错误 |
| **运行时增强** | 无 | 可以**动态增强** |

## 2. AOT 与 JIT 对比
|  | JIT | AOT |
| --- | --- | --- |
| 优点 | 1.具备**实时调整**能力
2.生成**最优机器指令**
3.根据代码运行情况**优化内存占用** | 1.速度快，优化了运行时编译时间和内存消耗
2.程序初期就能达最高性能
3.加快程序启动速度 |
| 缺点 | 1.运行期边编译**速度慢**
2.初始编译不能达到**最高性能** | 1.程序第一次编译占用时间长
2.牺牲**高级语言**一些特性 |

在 OpenJDK 的官方 Wiki 上，介绍了HotSpot 虚拟机一个相对比较全面的、**即时编译器（JIT）**中采用的[优化技术列表](https://xie.infoq.cn/link?target=https%3A%2F%2Fwiki.openjdk.java.net%2Fdisplay%2FHotSpot%2FPerformanceTacticIndex)。
![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1683773230399-b3fe0f68-f85a-4efb-bf38-d1783ea63d49.png#averageHue=%23efefef&clientId=uddcc5443-9304-4&from=paste&id=ud92c5d0f&originHeight=1024&originWidth=1920&originalType=url&ratio=1.25&rotation=0&showTitle=false&size=579522&status=done&style=none&taskId=ued916ae5-7db5-42e7-ba2f-f9b38e2d260&title=)
![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1683773247546-787b3fcf-ad8a-42d2-9a7b-2980eccff97d.png#averageHue=%23f0f0f0&clientId=uddcc5443-9304-4&from=paste&id=u75c916d4&originHeight=1084&originWidth=1920&originalType=url&ratio=1.25&rotation=0&showTitle=false&size=630356&status=done&style=none&taskId=u1d4a3074-bc72-4e40-a676-8472463a7fa&title=)
可使用：-XX:+PrintCompilation 打印JIT编译信息
## 3. JVM架构
.java === .class === 机器码
![未命名绘图.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1684124528903-feb1ce8f-302a-4872-a63d-ae5da99501eb.png#averageHue=%23f0e9e3&clientId=uf3247a6c-8d1d-4&from=paste&height=658&id=qc3xI&originHeight=823&originWidth=792&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=76545&status=done&style=none&taskId=u3512814e-dba2-4da6-baad-50f245bebd3&title=&width=633.6)
**JVM**: 既有**解释器**，又有**编辑器（JIT：即时编译）**；

## 4. Java的执行过程
> 建议阅读：
> - 美团技术：[https://tech.meituan.com/2020/10/22/java-jit-practice-in-meituan.html](https://tech.meituan.com/2020/10/22/java-jit-practice-in-meituan.html)
> - openjdk官网：[https://wiki.openjdk.org/display/HotSpot/Compiler](https://wiki.openjdk.org/display/HotSpot/Compiler)

### 流程概要
![](https://cdn.nlark.com/yuque/0/2023/png/1613913/1683774822566-f6860477-868e-4115-8ee9-7fe9d787e7a9.png#averageHue=%23c6dcc2&clientId=uddcc5443-9304-4&from=paste&id=u3e6fe4cc&originHeight=454&originWidth=648&originalType=url&ratio=1.25&rotation=0&showTitle=false&status=done&style=none&taskId=u070ed165-fad1-451f-9b84-56ccc429f45&title=)
解释执行：
编译执行：
### 详细流程
热点代码：调用次数非常多的代码
![未命名绘图2.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1684143084003-df41f505-f8d0-4ab9-a684-5c39037e8e30.png#averageHue=%23f9f6f5&clientId=uf3247a6c-8d1d-4&from=paste&height=509&id=ud12ff73d&originHeight=636&originWidth=687&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=58756&status=done&style=none&taskId=ub7decd67-6f7a-4a6a-93a5-087faf845b4&title=&width=549.6)

## 5. JVM编译器
JVM中集成了两种编译器，Client Compiler 和 Server Compiler；

- Client Compiler注重启动速度和局部的优化
- Server Compiler更加关注全局优化，性能更好，但由于会进行更多的全局分析，所以启动速度会慢。

Client Compiler：

- HotSpot VM带有一个Client Compiler **C1编译器**
- 这种编译器**启动速度快**，但是性能比较Server Compiler来说会差一些。
- 编译后的机器码执行效率没有C2的高

Server Compiler：

- Hotspot虚拟机中使用的Server Compiler有两种：**C2** 和 **Graal**。
- 在Hotspot VM中，默认的Server Compiler是**C2编译器。**

## 6. 分层编译
Java 7开始引入了分层编译(**Tiered Compiler**)的概念，它结合了**C1**和**C2**的优势，追求启动速度和峰值性能的一个平衡。分层编译将JVM的执行状态分为了五个层次。**五个层级**分别是：

- 解释执行。
- 执行不带profiling的C1代码。
- 执行仅带方法调用次数以及循环回边执行次数profiling的C1代码。
- 执行带所有profiling的C1代码。
- 执行C2代码。

**profiling就是收集能够反映程序执行状态的数据**。其中最基本的统计数据就是方法的调用次数，以及循环回边的执行次数。
![](https://cdn.nlark.com/yuque/0/2023/png/1613913/1683775747739-e428b122-ace8-4b33-b860-6a6c7ea11abd.png#averageHue=%23fdfdfc&clientId=uddcc5443-9304-4&from=paste&id=u56f707b8&originHeight=680&originWidth=863&originalType=url&ratio=1.25&rotation=0&showTitle=false&status=done&style=none&taskId=u62111dc6-ae2d-4473-ae9e-16b68cdc21d&title=)

- 图中第①条路径，代表编译的一般情况，**热点方法**从解释执行到被3层的C1编译，最后被4层的C2编译。
- 如果**方法比较小**（比如Java服务中常见的**getter/setter**方法），3层的profiling没有收集到有价值的数据，JVM就会断定该方法对于C1代码和C2代码的执行效率相同，就会执行图中第②条路径。在这种情况下，JVM会在3层编译之后，放弃进入C2编译，**直接选择用1层的C1编译运行**。
- 在**C1忙碌**的情况下，执行图中第③条路径，在解释执行过程中对程序进行**profiling** ，根据信息直接由第4层的**C2编译**。
- 前文提到C1中的执行效率是**1层>2层>3层**，**第3层**一般要比**第2层**慢35%以上，所以在**C2忙碌**的情况下，执行图中第④条路径。这时方法会被2层的C1编译，然后再被3层的C1编译，以减少方法在**3层**的执行时间。
- 如果**编译器**做了一些比较**激进的优化**，比如分支预测，在实际运行时**发现预测出错**，这时就会进行**反优化**，重新进入**解释执行**，图中第⑤条执行路径代表的就是**反优化**。

总的来说，C1的编译速度更快，C2的编译质量更高，分层编译的不同编译路径，也就是JVM根据当前服务的运行情况来寻找当前服务的最佳平衡点的一个过程。从JDK 8开始，JVM默认开启分层编译。

**云原生**：Cloud Native； Java小改版；

最好的效果：
存在的问题：

- java应用如果用jar，解释执行，热点代码才编译成机器码；初始启动速度慢，初始处理请求数量少。
- 大型云平台，要求每一种应用都必须秒级启动。每个应用都要求效率高。

希望的效果：

- java应用也能提前被编译成**机器码**，随时**急速启动**，一启动就急速运行，最高性能
- 编译成机器码的好处：
   - 另外的服务器还需要安装Java环境
   - 编译成**机器码**的，可以在这个平台 Windows X64 **直接运行**。

**原生**镜像：**native**-image（机器码、本地镜像）

- 把应用打包成能适配本机平台 的可执行文件（机器码、本地镜像）
# 2. GraalVM
[https://www.graalvm.org/](https://www.graalvm.org/)
> **GraalVM**是一个高性能的**JDK**，旨在**加速**用Java和其他JVM语言编写的**应用程序**的**执行**，同时还提供JavaScript、Python和许多其他流行语言的运行时。 
> **GraalVM**提供了**两种**运行**Java应用程序**的方式：
> - 1. 在HotSpot JVM上使用**Graal即时（JIT）编译器**
> - 2. 作为**预先编译（AOT）**的本机**可执行文件**运行（**本地镜像**）。
> 
 GraalVM的多语言能力使得在单个应用程序中混合多种编程语言成为可能，同时消除了外部语言调用的成本。


## 1. 架构
![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1683779334574-5040fa9c-d4cc-497a-aa2c-c937ccd2078d.png#averageHue=%23cecc75&clientId=u3bb3c899-b5cd-4&from=paste&height=394&id=u70c65d39&originHeight=493&originWidth=1310&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=129707&status=done&style=none&taskId=u1c7d3c52-a9b3-45fa-b63d-d801b70a923&title=&width=1048)
## 2. 安装
> 跨平台提供原生镜像原理：

![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1684149328177-6e1474c9-bec3-4b9a-afbe-b17b851f3ab1.png#averageHue=%230a8a0a&clientId=uf3247a6c-8d1d-4&from=paste&height=282&id=ud1a05d26&originHeight=353&originWidth=788&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=30144&status=done&style=none&taskId=u5e8e6341-c2f8-46fb-8e1f-f46358825be&title=&width=630.4)
### 1. VisualStudio
[https://visualstudio.microsoft.com/zh-hans/free-developer-offers/](https://visualstudio.microsoft.com/zh-hans/free-developer-offers/)
![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1684143751616-6c4e0a2d-8507-452b-a6fc-8fe16d4c772d.png#averageHue=%2368b067&clientId=uf3247a6c-8d1d-4&from=paste&height=557&id=ud25d23fc&originHeight=696&originWidth=1241&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=207834&status=done&style=none&taskId=u869a88eb-f195-4d7d-9fbf-0f8a7d0a3d5&title=&width=992.8)

![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1684143930294-6e36dd3d-c994-44b2-aa7c-a120b23ab044.png#averageHue=%23e9e9e9&clientId=uf3247a6c-8d1d-4&from=paste&height=686&id=u5c6f372e&originHeight=858&originWidth=1598&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=183936&status=done&style=none&taskId=u67c7f925-5e7b-4890-bf3a-253d5850dd9&title=&width=1278.4)
别选中文
![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1684152594737-ef7b01c4-304e-42d8-bcf7-00727014ec34.png#averageHue=%23f6f6f6&clientId=ub654d32f-db09-4&from=paste&height=454&id=uf6ef9e9a&originHeight=567&originWidth=1123&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=142290&status=done&style=none&taskId=uaf46e8e1-1978-431c-9a77-c79fdb8f305&title=&width=898.4)

![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1684143982484-bac17232-7e72-4bca-9a74-311fa888a8ca.png#averageHue=%23ececec&clientId=uf3247a6c-8d1d-4&from=paste&height=343&id=uf5148a65&originHeight=429&originWidth=988&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=42696&status=done&style=none&taskId=uf56f5174-0673-4b0d-bb7b-9c5203ad871&title=&width=790.4)

记住你安装的地址；

### 2. GraalVM 
#### 1. 安装
下载 GraalVM + native-image
![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1684144114094-7910b4e6-0073-4e01-9e37-7c165426a1d8.png#averageHue=%23124c59&clientId=uf3247a6c-8d1d-4&from=paste&height=421&id=ub6708d16&originHeight=526&originWidth=1449&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=78903&status=done&style=none&taskId=u858fd346-93f6-4f59-9c80-d2b181833c8&title=&width=1159.2)

![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1684144131164-13bd554b-1c97-4471-909e-7b0c7d5ebcd1.png#averageHue=%23bfdee5&clientId=uf3247a6c-8d1d-4&from=paste&height=378&id=u8571ba04&originHeight=472&originWidth=713&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=35722&status=done&style=none&taskId=u7fcd849c-c1e0-4017-b189-4da99ce77f2&title=&width=570.4)

![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1684144159433-2fbcf2fa-c2f3-482f-8fee-3473f5bb0fcf.png#averageHue=%23fdfcfb&clientId=uf3247a6c-8d1d-4&from=paste&height=649&id=u3ffb6621&originHeight=811&originWidth=1236&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=151643&status=done&style=none&taskId=u652a4eba-d855-4ee7-816a-1ba3c8f6d8f&title=&width=988.8)

![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1684144366433-026a2e7c-a5fb-48ec-8b5a-ae390063b67e.png#averageHue=%23fefefe&clientId=uf3247a6c-8d1d-4&from=paste&height=330&id=udca8d01b&originHeight=413&originWidth=1444&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=106331&status=done&style=none&taskId=ucd5dc7bb-387a-4b38-a2bd-f027875ff0c&title=&width=1155.2)

#### 2. 配置
修改 JAVA_HOME 与 Path，指向新bin路径
![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1684144739494-b078d166-5e09-421d-b237-7ee1a2c153f6.png#averageHue=%23efeeee&clientId=uf3247a6c-8d1d-4&from=paste&height=174&id=ud329bb54&originHeight=217&originWidth=839&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=11891&status=done&style=none&taskId=u0f74bc74-49b1-4135-abb9-082cda94bb8&title=&width=671.2)
![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1684144848621-d8577753-5a5b-402a-863b-617f43e35db1.png#averageHue=%23f2f0ed&clientId=uf3247a6c-8d1d-4&from=paste&height=528&id=u6c9ee2e0&originHeight=660&originWidth=670&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=53320&status=done&style=none&taskId=u969d0175-fcb0-4e7f-8fe7-6219223176c&title=&width=536)

验证JDK环境为GraalVM提供的即可：
![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1684144703862-26be3be1-dd2d-491e-8eca-2317495d77cb.png#averageHue=%23393736&clientId=uf3247a6c-8d1d-4&from=paste&height=183&id=u95a8ccbc&originHeight=229&originWidth=992&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=31405&status=done&style=none&taskId=ub4e14bcf-279c-4b4f-adbb-7fc4e36892f&title=&width=793.6)

#### 3. 依赖
安装 native-image 依赖：

1. 网络环境好：参考：[https://www.graalvm.org/latest/reference-manual/native-image/#install-native-image](https://www.graalvm.org/latest/reference-manual/native-image/#install-native-image)
```shell
gu install native-image
```

2. 网络不好，使用我们下载的离线jar;`native-image-xxx.jar`文件
```shell
gu install --file native-image-installable-svm-java17-windows-amd64-22.3.2.jar
```
#### 4. 验证
```shell
native-image
```


## 3. 测试
### 1. 创建项目

- 1. 创建普通java项目。编写HelloWorld类；
   - 使用`mvn clean package`进行打包
   - 确认jar包是否可以执行`java -jar xxx.jar`
   - 可能需要给 `MANIFEST.MF`添加 `Main-Class: 你的主类`

### 2. 编译镜像

- 编译为原生镜像（native-image）：使用`native-tools`终端

![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1684147385110-bd82ed80-a65a-439f-b82d-fec40e40edec.png#averageHue=%23242220&clientId=uf3247a6c-8d1d-4&from=paste&height=397&id=ua0ad4525&originHeight=496&originWidth=784&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=54988&status=done&style=none&taskId=u82b2a429-77e8-4f32-b2b5-c3344ce600d&title=&width=627.2)
```shell
#从入口开始，编译整个jar
native-image -cp boot3-15-aot-common-1.0-SNAPSHOT.jar com.atguigu.MainApplication -o Haha

#编译某个类【必须有main入口方法，否则无法编译】
native-image -cp .\classes org.example.App
```


### 3. Linux平台测试

- 1. 安装gcc等环境
```shell
yum install lrzsz
sudo yum install gcc glibc-devel zlib-devel
```

- 2. 下载安装配置Linux下的GraalVM、native-image
   - 下载：[https://www.graalvm.org/downloads/](https://www.graalvm.org/downloads/)
   - 安装：GraalVM、native-image
   - 配置：JAVA环境变量为GraalVM
```shell
tar -zxvf graalvm-ce-java17-linux-amd64-22.3.2.tar.gz -C /opt/java/

sudo vim /etc/profile
#修改以下内容
export JAVA_HOME=/opt/java/graalvm-ce-java17-22.3.2
export PATH=$PATH:$JAVA_HOME/bin

source /etc/profile
```

- 3. 安装native-image
```shell
gu install --file native-image-installable-svm-java17-linux-amd64-22.3.2.jar
```

- 4. 使用native-image编译jar为原生程序
```shell
native-image -cp xxx.jar org.example.App
```

# 3. SpringBoot整合
## 1. 依赖导入
```xml
 <build>
        <plugins>
            <plugin>
                <groupId>org.graalvm.buildtools</groupId>
                <artifactId>native-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```
## 2. 生成native-image
1、运行aot提前处理命令：`mvn springboot:process-aot`
2、运行native打包：`mvn -Pnative native:build`
```shell
# 推荐加上 -Pnative
mvn -Pnative native:build -f pom.xml
```
![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1684152780642-b82e9976-170c-4118-bcd3-a319bc325774.png#averageHue=%23f7f5f3&clientId=ub654d32f-db09-4&from=paste&height=230&id=u8dc6d458&originHeight=287&originWidth=580&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=30127&status=done&style=none&taskId=uba2aaf5d-6b4a-4e90-9d48-bc72a37814e&title=&width=464)


## 3. 常见问题
可能提示如下各种错误，无法构建原生镜像，需要配置环境变量；

- 出现`cl.exe`找不到错误
- 出现乱码
- 提示`no include path set`
- 提示fatal error LNK1104: cannot open file 'LIBCMT.lib'
- 提示 LINK : fatal error LNK1104: cannot open file 'kernel32.lib'
- 提示各种其他找不到

**需要修改三个环境变量**：`Path`、`INCLUDE`、`lib`

- 1、 Path：添加如下值
   - `C:\Program Files\Microsoft Visual Studio\2022\Community\VC\Tools\MSVC\14.33.31629\bin\Hostx64\x64`
- 2、新建`INCLUDE`环境变量：值为
```
C:\Program Files\Microsoft Visual Studio\2022\Community\VC\Tools\MSVC\14.33.31629\include;C:\Program Files (x86)\Windows Kits\10\Include\10.0.19041.0\shared;C:\Program Files (x86)\Windows Kits\10\Include\10.0.19041.0\ucrt;C:\Program Files (x86)\Windows Kits\10\Include\10.0.19041.0\um;C:\Program Files (x86)\Windows Kits\10\Include\10.0.19041.0\winrt
```
![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1684154634081-c986db1e-7ab8-4fb6-ada7-f6999570310a.png#averageHue=%23f6f4f3&clientId=uf9f22589-8d25-4&from=paste&height=199&id=HtkKA&originHeight=249&originWidth=1015&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=24525&status=done&style=none&taskId=u31497b1f-4ae0-47d5-8d95-3f1490db359&title=&width=812)

- 3、新建`lib`环境变量：值为
```latex
C:\Program Files\Microsoft Visual Studio\2022\Community\VC\Tools\MSVC\14.33.31629\lib\x64;C:\Program Files (x86)\Windows Kits\10\Lib\10.0.19041.0\um\x64;C:\Program Files (x86)\Windows Kits\10\Lib\10.0.19041.0\ucrt\x64
```
![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1684156564048-2dbf1d6f-b0f0-4493-aef9-a2c1b7d22d58.png#averageHue=%23f4f3f1&clientId=udc3471cb-38a7-4&from=paste&height=117&id=uacb60c1d&originHeight=146&originWidth=978&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=14818&status=done&style=none&taskId=u2d19c17e-02ff-480f-b9ce-57fbbd6f59d&title=&width=782.4)




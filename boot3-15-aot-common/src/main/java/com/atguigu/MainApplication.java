package com.atguigu;


/**
 * 打包成本地镜像：
 *
 * 1、打成jar包:  注意修改 jar包内的 MANIFEST.MF 文件，指定Main-Class的全类名
 *        - java -jar xxx.jar 就可以执行。
 *        - 切换机器，安装java环境。默认解释执行，启动速度慢，运行速度慢
 * 2、打成本地镜像（可执行文件）：
 *        - native-image -cp  你的jar包/路径  你的主类  -o 输出的文件名
 *        - native-image -cp boot3-15-aot-common-1.0-SNAPSHOT.jar com.atguigu.MainApplication -o Demo
 *
 * 并不是所有的Java代码都能支持本地打包；
 * SpringBoot保证Spring应用的所有程序都能在AOT的时候提前告知graalvm怎么处理？
 *
 *   - 动态能力损失：反射的代码：（动态获取构造器，反射创建对象，反射调用一些方法）；
 *     解决方案：额外处理（SpringBoot 提供了一些注解）：提前告知 graalvm 反射会用到哪些方法、构造器
 *   - 配置文件损失：
 *     解决方案：额外处理（配置中心）：提前告知 graalvm 配置文件怎么处理
 *   - 【好消息：新版GraalVM可以自动进行预处理，不用我们手动进行补偿性的额外处理。】
 *   二进制里面不能包含的，不能动态的都得提前处理；
 *
 *   不是所有框架都适配了 AOT特性；Spring全系列栈适配OK
 *
 *  application.properties
 *  a(){
 *      //ssjsj  bcde();
 *      //提前处理
 *  }
 */
public class MainApplication {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
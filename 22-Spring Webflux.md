**今日任务**：

- **Reactor核心**：**HttpHandler** 原生API；
- **DispatcherHandler** 原理；
   - DispatcherHandler 组件分析
   - DispatcherHandler 请求处理流程
   - 返回结果处理
   - 异常处理
   - 视图解析
      - 重定向
      - Rendering
- **注解式 - Controller**
   - 兼容老版本方式
   - **新版本变化**
      - **SSE**
      - **文件上传**
- **错误响应**
   - @ExceptionHandler 
      - ErrorResponse： 自定义 错误响应
      - ProblemDetail：自定义PD返回
- **WebFlux配置**
   - @EnableWebFlux
   - WebFluxConfigurer

> WebFlux：底层完全基于netty+reactor+springweb 完成一个全异步非阻塞的web**响应式框架**
> **底层：异步 + 消息队列(内存) + 事件回调机制 = 整套系统**
> **优点：能使用少量资源处理大量请求；**



# 0、组件对比
| **API功能** | **Servlet-阻塞式Web** | **WebFlux-响应式Web** |
| --- | --- | --- |
| 前端控制器 | DispatcherServlet | DispatcherHandler |
| 处理器 | Controller | WebHandler/Controller |
| 请求、响应 | **ServletRequest**、**ServletResponse** | **ServerWebExchange：**
**ServerHttpRequest、ServerHttpResponse** |
| 过滤器 | Filter（HttpFilter） | WebFilter |
| 异常处理器 | HandlerExceptionResolver | DispatchExceptionHandler |
| Web配置 | @EnableWebMvc | @EnableWebFlux |
| 自定义配置 | WebMvcConfigurer | WebFluxConfigurer |
| 返回结果 | 任意 | **Mono、Flux**、任意 |
| 发送REST请求 | RestTemplate | WebClient |

**Mono： 返回0|1 数据流**
**Flux：返回N数据流**

# 1、WebFlux
> 底层基于Netty实现的Web容器与请求/响应处理机制
> 参照：[https://docs.spring.io/spring-framework/reference/6.0/web/webflux.html](https://docs.spring.io/spring-framework/reference/6.0/web/webflux.html)


## 1、引入
```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.1.6</version>
    </parent>


<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
    </dependencies>
```


> **Context 响应式上下文数据传递； 由下游传播给上游；**
> **以前： 浏览器 --> Controller --> Service --> Dao： 阻塞式编程**
> **现在： Dao（数据源查询对象【数据发布者】） --> Service --> Controller --> 浏览器： 响应式**



> **大数据流程： 从一个数据源拿到大量数据进行分析计算；**
> **ProductVistorDao.loadData()**
> **                              .distinct()**
> **    .map()**
> **    .filter()**
> **     .handle()**
> **.subscribe();**
> **;//加载最新的商品浏览数据**

![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1701433751352-df86d2c7-98a5-4773-bef6-89371f9075db.png#averageHue=%23d7e5f0&clientId=ua21bf77b-e870-4&from=paste&height=447&id=u53965264&originHeight=559&originWidth=1001&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=178649&status=done&style=none&taskId=u4ece118a-e5ae-4bef-9885-07d4a26122d&title=&width=800.8)
## 2、Reactor Core
### 1、HttpHandler、HttpServer
```java
    public static void main(String[] args) throws IOException {
        //快速自己编写一个能处理请求的服务器

        //1、创建一个能处理Http请求的处理器。 参数：请求、响应； 返回值：Mono<Void>：代表处理完成的信号
        HttpHandler handler = (ServerHttpRequest request,
                                   ServerHttpResponse response)->{
            URI uri = request.getURI();
            System.out.println(Thread.currentThread()+"请求进来："+uri);
            //编写请求处理的业务,给浏览器写一个内容 URL + "Hello~!"
//            response.getHeaders(); //获取响应头
//            response.getCookies(); //获取Cookie
//            response.getStatusCode(); //获取响应状态码；
//            response.bufferFactory(); //buffer工厂
//            response.writeWith() //把xxx写出去
//            response.setComplete(); //响应结束

            //数据的发布者：Mono<DataBuffer>、Flux<DataBuffer>

            //创建 响应数据的 DataBuffer
            DataBufferFactory factory = response.bufferFactory();

            //数据Buffer
            DataBuffer buffer = factory.wrap(new String(uri.toString() + " ==> Hello!").getBytes());


            // 需要一个 DataBuffer 的发布者
            return response.writeWith(Mono.just(buffer));
        };

        //2、启动一个服务器，监听8080端口，接受数据，拿到数据交给 HttpHandler 进行请求处理
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);


        //3、启动Netty服务器
        HttpServer.create()
                .host("localhost")
                .port(8080)
                .handle(adapter) //用指定的处理器处理请求
                .bindNow(); //现在就绑定

        System.out.println("服务器启动完成....监听8080，接受请求");
        System.in.read();
        System.out.println("服务器停止....");


    }
```

## 3、DispatcherHandler
> SpringMVC： DispatcherServlet；
> SpringWebFlux： DispatcherHandler

### 1、请求处理流程

- HandlerMapping：**请求映射处理器**； 保存每个请求由哪个方法进行处理
- HandlerAdapter：**处理器适配器**；反射执行目标方法
- HandlerResultHandler：**处理器结果**处理器；

SpringMVC： DispatcherServlet 有一个 doDispatch() 方法，来处理所有请求；
WebFlux： DispatcherHandler 有一个 handle() 方法，来处理所有请求；
```java
public Mono<Void> handle(ServerWebExchange exchange) { 
		if (this.handlerMappings == null) {
			return createNotFoundError();
		}
		if (CorsUtils.isPreFlightRequest(exchange.getRequest())) {
			return handlePreFlight(exchange);
		}
		return Flux.fromIterable(this.handlerMappings) //拿到所有的 handlerMappings
				.concatMap(mapping -> mapping.getHandler(exchange)) //找每一个mapping看谁能处理请求
				.next() //直接触发获取元素； 拿到流的第一个元素； 找到第一个能处理这个请求的handlerAdapter
				.switchIfEmpty(createNotFoundError()) //如果没拿到这个元素，则响应404错误；
				.onErrorResume(ex -> handleDispatchError(exchange, ex)) //异常处理，一旦前面发生异常，调用处理异常
				.flatMap(handler -> handleRequestWith(exchange, handler)); //调用方法处理请求，得到响应结果
	}
```

- 1、请求和响应都封装在 ServerWebExchange 对象中，由handle方法进行处理
- 2、如果没有任何的请求映射器； 直接返回一个： 创建一个未找到的错误； 404； 返回Mono.error；终结流
- 3、跨域工具，是否跨域请求，跨域请求检查是否复杂跨域，需要预检请求；
- 4、Flux流式操作，先找到HandlerMapping，再获取handlerAdapter，再用Adapter处理请求，期间的错误由onErrorResume触发回调进行处理；

源码中的核心两个：

- **handleRequestWith**： 编写了handlerAdapter怎么处理请求
- **handleResult**： String、User、ServerSendEvent、Mono、Flux ...

concatMap： 先挨个元素变，然后把变的结果按照之前元素的顺序拼接成一个完整流
```java
private <R> Mono<R> createNotFoundError() {
		Exception ex = new ResponseStatusException(HttpStatus.NOT_FOUND);
		return Mono.error(ex);
	}
Mono.defer(() -> {
			Exception ex = new ResponseStatusException(HttpStatus.NOT_FOUND);
			return Mono.error(ex);
		}); //有订阅者，且流被激活后就动态调用这个方法； 延迟加载；

```


## 4、注解开发
### 1、目标方法传参
[https://docs.spring.io/spring-framework/reference/6.0/web/webflux/controller/ann-methods/arguments.html](https://docs.spring.io/spring-framework/reference/6.0/web/webflux/controller/ann-methods/arguments.html)

| Controller method argument | Description |
| --- | --- |
| ServerWebExchange | 封装了请求和响应对象的对象; 自定义获取数据、自定义响应 |
| ServerHttpRequest, ServerHttpResponse | 请求、响应 |
| WebSession | 访问Session对象 |
| java.security.Principal |  |
| org.springframework.http.HttpMethod | 请求方式 |
| java.util.Locale | 国际化 |
| java.util.TimeZone + java.time.ZoneId | 时区 |
| @PathVariable | 路径变量 |
| @MatrixVariable | 矩阵变量 |
| @RequestParam | 请求参数 |
| @RequestHeader | 请求头； |
| @CookieValue | 获取Cookie |
| @RequestBody | 获取请求体，Post、文件上传 |
| HttpEntity<B> | 封装后的请求对象 |
| @RequestPart | 获取文件上传的数据 multipart/form-data. |
| java.util.Map, org.springframework.ui.Model, and org.springframework.ui.ModelMap. | Map、Model、ModelMap |
| @ModelAttribute |  |
| Errors, BindingResult | 数据校验，封装错误 |
| SessionStatus + class-level @SessionAttributes | 
 |
| UriComponentsBuilder | For preparing a URL relative to the current request’s host, port, scheme, and context path. See [URI Links](https://docs.spring.io/spring-framework/reference/6.0/web/webflux/uri-building.html). |
| @SessionAttribute |  |
| @RequestAttribute | 转发请求的请求域数据 |
| Any other argument | 所有对象都能作为参数：
1、基本类型 ，等于标注@RequestParam 
2、对象类型，等于标注 @ModelAttribute |



### 2、返回值写法
sse和websocket区别：

- SSE：单工；请求过去以后，等待服务端源源不断的数据
- websocket：双工： 连接建立后，可以任何交互；
| Controller method return value | Description |
| --- | --- |
| @ResponseBody | 把响应数据写出去，如果是对象，可以自动转为json |
| HttpEntity<B>, ResponseEntity<B> | ResponseEntity：支持快捷自定义响应内容 |
| HttpHeaders | 没有响应内容，只有响应头 |
| ErrorResponse | 快速构建错误响应 |
| ProblemDetail | SpringBoot3； |
| String | 就是和以前的使用规则一样；
forward: 转发到一个地址
redirect: 重定向到一个地址
配合模板引擎 |
| View | 直接返回视图对象 |
| java.util.Map, org.springframework.ui.Model | 以前一样 |
| @ModelAttribute | 以前一样 |
| Rendering | 新版的页面跳转API； 不能标注 @ResponseBody 注解 |
| void | 仅代表响应完成信号 |
| Flux<ServerSentEvent>, Observable<ServerSentEvent>, or other reactive type | 使用  text/event-stream 完成SSE效果 |
| Other return values | 未在上述列表的其他返回值，都会当成给页面的数据； |



## 5、文件上传
[https://docs.spring.io/spring-framework/reference/6.0/web/webflux/controller/ann-methods/multipart-forms.html](https://docs.spring.io/spring-framework/reference/6.0/web/webflux/controller/ann-methods/multipart-forms.html)
```java
class MyForm {

	private String name;

	private MultipartFile file;

	// ...

}

@Controller
public class FileUploadController {

	@PostMapping("/form")
	public String handleFormUpload(MyForm form, BindingResult errors) {
		// ...
	}

}
```

现在
```java
@PostMapping("/")
public String handle(@RequestPart("meta-data") Part metadata, 
		@RequestPart("file-data") FilePart file) { 
	// ...
}
```


## 6、错误处理
```java
    @ExceptionHandler(ArithmeticException.class)
    public String error(ArithmeticException exception){
        System.out.println("发生了数学运算异常"+exception);

        //返回这些进行错误处理；
//        ProblemDetail：  建造者：声明式编程、链式调用
//        ErrorResponse ： 

        return "炸了，哈哈...";
    }
```

## 7、RequestContext


## 8、自定义Flux配置
### WebFluxConfigurer
> 容器中注入这个类型的组件，重写底层逻辑


```java
@Configuration
public class MyWebConfiguration {

    //配置底层
    @Bean
    public WebFluxConfigurer webFluxConfigurer(){

        return new WebFluxConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .allowedOrigins("localhost");
            }
        };
    }
}
```

## 9、Filter
```java
@Component
public class MyWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        System.out.println("请求处理放行到目标方法之前...");
        Mono<Void> filter = chain.filter(exchange); //放行


        //流一旦经过某个操作就会变成新流

        Mono<Void> voidMono = filter.doOnError(err -> {
                    System.out.println("目标方法异常以后...");
                }) // 目标方法发生异常后做事
                .doFinally(signalType -> {
                    System.out.println("目标方法执行以后...");
                });// 目标方法执行之后

        //上面执行不花时间。
        return voidMono; //看清楚返回的是谁！！！
    }
}
```

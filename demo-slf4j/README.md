

# slf4j:Simple Loging Facade For Java
仅仅是一个为Java程序提供日志输出的统一接口，并不是一个具体的日志实现方案

## 优势：
－　解耦：引用各个类库时如果使用的日志框架不一致的话，就需要维护几套
－　省内存与提供cpu：使用占位符{}来代替字符串相加，产生日志前判断日志级别来避免不必要的字符串拼接

## 原理
LoggerFactory.getLogger()首先获取一个ILoggerFactory接口，然后使用该接口获取具体的Logger。获取ILoggerFactory的时候用到了一个StaticLoggerBinder类，仔细研究我们会发现StaticLoggerBinder这个类并不是slf4j-api这个包中的类，而是slf4j-log4j包中的类，这个类就是一个中间类，它用来将抽象的slf4j变成具体的log4j，也就是说具体要使用什么样的日志实现方案，就得靠这个StaticLoggerBinder类。再看看slf4j-log4j包种的这个StaticLoggerBinder类创建ILoggerFactory长什么样子：

```
private StaticLoggerBinder() {
  loggerFactory = new Log4jLoggerFactory();
  try {
    Level level = Level.TRACE;
  } catch (NoSuchFieldError nsfe) {
    Util
        .report("This version of SLF4J requires log4j version 1.2.12 or later. See also http://www.slf4j.org/codes.html#log4j_version");
  }
}

public ILoggerFactory getLoggerFactory() {
  return loggerFactory;
}
```
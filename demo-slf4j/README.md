

# slf4j:Simple Loging Facade For Java
������һ��ΪJava�����ṩ��־�����ͳһ�ӿڣ�������һ���������־ʵ�ַ���

## ���ƣ�
����������ø������ʱ���ʹ�õ���־��ܲ�һ�µĻ�������Ҫά������
����ʡ�ڴ����ṩcpu��ʹ��ռλ��{}�������ַ�����ӣ�������־ǰ�ж���־���������ⲻ��Ҫ���ַ���ƴ��

## ԭ��
LoggerFactory.getLogger()���Ȼ�ȡһ��ILoggerFactory�ӿڣ�Ȼ��ʹ�øýӿڻ�ȡ�����Logger����ȡILoggerFactory��ʱ���õ���һ��StaticLoggerBinder�࣬��ϸ�о����ǻᷢ��StaticLoggerBinder����ಢ����slf4j-api������е��࣬����slf4j-log4j���е��࣬��������һ���м��࣬�������������slf4j��ɾ����log4j��Ҳ����˵����Ҫʹ��ʲô������־ʵ�ַ������͵ÿ����StaticLoggerBinder�ࡣ�ٿ���slf4j-log4j���ֵ����StaticLoggerBinder�ഴ��ILoggerFactory��ʲô���ӣ�

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
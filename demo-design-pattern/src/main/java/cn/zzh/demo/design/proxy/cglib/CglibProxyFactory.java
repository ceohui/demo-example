package cn.zzh.demo.design.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyFactory implements MethodInterceptor {

	private Object target;

	public CglibProxyFactory(Object target){
		this.target = target;
	}

	//给目标对象创建一个代理对象
	public Object getProxyInstance(){
		//1.工具类
		Enhancer en = new Enhancer();
		//2.设置父类
		en.setSuperclass(target.getClass());
		//3.设置回调函数
		en.setCallback(this);
		//4.创建子类(代理对象)
		return en.create();
	}

	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		System.out.println("before intercept");

		Object retValue = method.invoke(target, objects);

		System.out.println("after intercept");
		return retValue;
	}
}

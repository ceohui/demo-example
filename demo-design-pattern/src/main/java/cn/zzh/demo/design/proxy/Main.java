package cn.zzh.demo.design.proxy;

import cn.zzh.demo.design.proxy.cglib.CglibProxyFactory;
import cn.zzh.demo.design.proxy.dynamic.DynamicProxyFactory;

public class Main {

	public static void main(String[] args){
//		runDynamicProxy();
		runCglibProxy();
	}

	public static void runDynamicProxy(){
		UserDao userDao = new UserDaoImpl();
		System.out.println(userDao.getClass());

		UserDao proxy = (UserDao) new DynamicProxyFactory(userDao).getProxyInstance();
		System.out.println(proxy.getClass());

		String val = proxy.sayHi("victorzheng");
		System.out.println(val);
	}

	public static void runCglibProxy(){
		RoleDao target = new RoleDao();

		RoleDao proxy = (RoleDao)new CglibProxyFactory(target).getProxyInstance();
		proxy.info("victorzheng");
	}

}

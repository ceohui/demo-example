package cn.zzh.demo.design.proxy;

public class UserDaoImpl implements UserDao {

	@Override
	public String sayHi(String name) {
		return "hi," + name;
	}
}

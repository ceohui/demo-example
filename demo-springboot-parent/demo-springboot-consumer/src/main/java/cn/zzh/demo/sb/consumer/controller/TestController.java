package cn.zzh.demo.sb.consumer.controller;

import cn.zzh.demo.sb.consumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Autowired
	UserService userService;

	@RequestMapping("hello/{name}")
	public String sayHello(@PathVariable("name") String name) {
		long t = System.currentTimeMillis();
		String value = userService.sayHello(name);
		long cost = System.currentTimeMillis() - t;
		return String.format("input:%s, ret:%s, cost:%d", name, value, cost);
	}

}

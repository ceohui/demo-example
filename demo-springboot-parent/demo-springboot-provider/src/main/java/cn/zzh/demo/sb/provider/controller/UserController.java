package cn.zzh.demo.sb.provider.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

	@RequestMapping("hello")
	public String sayHello(String name) {
		return "hi, " + name;
	}
}

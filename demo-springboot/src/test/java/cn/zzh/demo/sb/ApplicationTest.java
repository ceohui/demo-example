package cn.zzh.demo.sb;

import cn.zzh.demo.sb.domain.User;
import cn.zzh.demo.sb.domain.UserRepository;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(Application.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationTest.class);

	@Autowired
	UserRepository userRepository;

	@Test
	public void test_jpa_add() {

		int count = 10;
		for (int i = 0; i < count; i++) {
			User user = new User();
			user.setName("zzh" + i);
			user.setSex(1);

			user = userRepository.save(user);
			LOG.info("save|index:{}|id:{}", i, user.getId());
		}
	}

	@Test
	public void test_jpa_selectById(){
		long id = 11;
		User user = userRepository.findById(id);

		LOG.info("ret:{}", JSON.toJSONString(user));
	}

	@Test
	public void test_jpa_selectByName(){
		String name = "zzh1";
		User user = userRepository.findByName(name);

		LOG.info("ret:{}", JSON.toJSONString(user));
	}

	@Test
	public void test_jpa_selectBySex(){
		int sex = 1;
		List<User> user = userRepository.findBySex(sex);

		LOG.info("ret:{}", JSON.toJSONString(user));
	}

	@Test
	public void test_jpa_delete(){
		//int[] ids = {1,2,3};
		List<Long> ids = Arrays.asList(11L,12L,3L);
		int[] ret = userRepository.deleteBy(ids);

		LOG.info("ret:{}",ret);
	}

	@Test
	public void test_repository() {
		userRepository.findAll();
	}

}

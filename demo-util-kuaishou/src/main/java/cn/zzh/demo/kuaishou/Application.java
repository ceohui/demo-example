package cn.zzh.demo.kuaishou;

import cn.zzh.demo.kuaishou.webmagic.UserPageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication

@Controller
public class Application {

	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	private static Spider spider;

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class, args);

		long[] ids = {
				57669808 // 斗笠
		};
		List<String> urls = new ArrayList<>();
		for (long id : ids) {
			urls.add("https://www.gifshow.com/user/" + id);
		}

		spider = Spider.create(new UserPageProcessor())
				//从"https://github.com/code4craft"开始抓
				.addUrl(urls.toArray(new String[urls.size()]))
				// pipeline
				.addPipeline(new ConsolePipeline())
//				.addPipeline(new MyPipline())
				//开启5个线程抓取
				.thread(5);
		spider.setEmptySleepTime(3000);
		spider.setExitWhenComplete(false);
		

		//启动爬虫
		spider.run();

		LOG.info("start spider");
		Thread.sleep(5000);
		spider.addUrl("https://www.gifshow.com/user/137994259");
		LOG.info("start finish");
	}

	@RequestMapping("/add")
	@ResponseBody
	public String addUrl(String id){
		spider.addUrl("https://www.gifshow.com/user/"+id);

		return "OK";
	}
}

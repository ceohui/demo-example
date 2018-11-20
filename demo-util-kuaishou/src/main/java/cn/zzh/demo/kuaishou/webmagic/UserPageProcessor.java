package cn.zzh.demo.kuaishou.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

public class UserPageProcessor implements PageProcessor {

	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	private Site site = Site.me().setRetryTimes(10).setSleepTime(1000);

	@Override
	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
	public void process(Page page) {
		// 部分二：定义如何抽取页面信息，并保存下来
		page.putField("name", page.getHtml().xpath("/html/head/title/text()").toString());
		page.putField("headUrl", page.getHtml().xpath("/html/body/div[1]/div[2]/div[2]/div/img/@src").toString());
		page.putField("sign", page.getHtml().xpath("/html/body/div[1]/div[2]/div[2]/p/text()").toString());
		page.putField("fans", page.getHtml().xpath("/html/body/div[1]/div[2]/div[2]/div/div/div/span[1]/text()").toString());
		page.putField("follow", page.getHtml().xpath("/html/body/div[1]/div[2]/div[2]/div/div/div/span[3]/text()").toString());
		page.putField("works", page.getHtml().xpath("/html/body/div[1]/div[3]/div[1]/text()").toString());

		String url = page.getUrl().get();
		page.putField("userId", url.substring(url.lastIndexOf("/") + 1));

		// /html/body/div[3]/script[5]/text()
		// /html/body/div[3]/script[5]/text()

		// 部分三：从页面发现后续的url地址来抓取
//		page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
	}


	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {

		long[] ids = {
				57669808 // 斗笠
//				, 137994259 // 九哥
//				, 50204684// 散打哥
//				, 6646803 // 高迪
//				, 59241800 // 二驴
//				, 77225978 // FZ 方丈
//				, 95437553 // 可爱的王可可
//				, 96415463 // 牌牌琦
//				, 156587032 // 浪子吴迪

				,};
		List<String> urls = new ArrayList<>();
		for (long id : ids) {
			urls.add("https://www.gifshow.com/user/" + id);
		}

		Spider.create(new UserPageProcessor())
				//从"https://github.com/code4craft"开始抓
				.addUrl(urls.toArray(new String[urls.size()]))
				// pipeline
				.addPipeline(new ConsolePipeline())
//				.addPipeline(new MyPipline())
				//开启5个线程抓取
				.thread(5)
				//启动爬虫
				.run();
	}
}

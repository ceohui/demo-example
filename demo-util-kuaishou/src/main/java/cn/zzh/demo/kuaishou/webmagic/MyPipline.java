package cn.zzh.demo.kuaishou.webmagic;

import cn.zzh.demo.kuaishou.DBUtil;
import cn.zzh.demo.kuaishou.bean.KsHistFans;
import cn.zzh.demo.kuaishou.bean.KsHistWorks;
import cn.zzh.demo.kuaishou.bean.KsUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class MyPipline implements Pipeline {

	private static final Logger LOG = LoggerFactory.getLogger(MyPipline.class);

	@Override
	public void process(ResultItems resultItems, Task task) {
		LOG.info("====================================");
		String liveData = resultItems.get("liveData");
		if (liveData == null || liveData.isEmpty()) {
			LOG.error("fail|empty data|url: {}", resultItems.getRequest().getUrl());
			LOG.info("script:{}", resultItems.get("script"));
			return;
		}
		liveData = liveData.replace("<script>window.__data__ = ", "").replace(";</script>", "");

		System.out.println("提出数据部分：" + liveData);
		try {
			JSONObject dataObj = JSON.parseObject(liveData);
			JSONObject userObj = dataObj.getJSONObject("liveStream").getJSONObject("user");
			JSONObject countObj = dataObj.getJSONObject("counts");
			JSONArray worksArray = dataObj.getJSONArray("photos");

			String userId = userObj.getString("user_id");
			String kwaiId = userObj.getString("kwaiId");

			int numFans = countObj.getIntValue("fanCount");
			int numFollow = countObj.getIntValue("followCount");
			int numWorks = countObj.getIntValue("photoCount");


			// 用户信息
			KsUser ksUser = new KsUser();
			ksUser.setUserId(userId);
			ksUser.setKwaiId(kwaiId);
			ksUser.setName(userObj.getString("user_name"));
			ksUser.setSign(userObj.getString("user_text"));
			ksUser.setSex("M".equals(userObj.getString("user_sex")) ? 1 : 0);
			ksUser.setHeadUrl(userObj.getString("headurl"));

			ksUser.setNumFans(numFans);
			ksUser.setNumFollow(numFollow);
			ksUser.setNumWorks(numWorks);

			int row = 0;//DBUtil.saveOrUpdateKsUser(ksUser);


			// 粉丝记录
			KsHistFans ksHistFans = new KsHistFans();
			ksHistFans.setUserId(userId);
			ksHistFans.setKwaiId(kwaiId);
			ksHistFans.setNumFans(numFans);
			ksHistFans.setNumFollow(numFollow);
			ksHistFans.setNumWorks(numWorks);

			int histFansRow = 0;//DBUtil.saveKsHistFans(ksHistFans);

			// 作品记录
			if (false && worksArray != null && !worksArray.isEmpty()) {
				for (int i = 0; i < worksArray.size(); i++) {
					KsHistWorks works = worksArray.getObject(i, KsHistWorks.class);
					int worksRet = DBUtil.saveKsHistWorks(works);
					LOG.info("saveWorks|index:{}/{}|ret:{}|info:{}", i, worksArray.size(), worksRet, JSON.toJSONString(works));
				}

			}

			LOG.info("ret|row:{}|histFansRow:{}", row, histFansRow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("====================================");
	}


}

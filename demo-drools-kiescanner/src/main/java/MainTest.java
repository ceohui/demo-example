import cn.zzh.demo.drools.redenvelope.*;
import com.alibaba.fastjson.JSON;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainTest {

	private static final Logger LOG = LoggerFactory.getLogger(MainTest.class);

	public static void main(String[] args) throws InterruptedException {

		final KieContainer kieContainer = ruleFromClassPath();
//		final KieContainer kieContainer = ruleFromMaven();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					long now = System.currentTimeMillis();
					System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(now)));
					try {

						RedEnvelopeModel model = genModel();
						RuleResult ruleRet = new RuleResult();

						ruleRet = fireRule(kieContainer, model, ruleRet, "product", Arrays.asList("red2"));
						System.out.println("result: " + JSON.toJSONString(ruleRet));

						ruleRet = fireRule(kieContainer, model, ruleRet, "ksession-rules2", Arrays.asList("agenda_test"));
						System.out.println("result: " + JSON.toJSONString(ruleRet));

						System.out.println("cost:" + (System.currentTimeMillis() - now));

						Thread.sleep(50000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.setDaemon(true);
		thread.start();

		Thread.currentThread().join();
	}

	public static RuleResult fireRule(KieContainer kieContainer, Object modelData, RuleResult ruleRet, String session, List<String> agendaGroupList) {
		long now = System.currentTimeMillis();
		try {
			KieSession kieSession = kieContainer.newKieSession(session);

			if (ruleRet == null) {
				ruleRet = new RuleResult();
			}
			kieSession.setGlobal("ruleResult", ruleRet);
			kieSession.insert(modelData);

			if (agendaGroupList != null && !agendaGroupList.isEmpty()) {
				for (String agendaGroup : agendaGroupList) {
					kieSession.getAgenda().getAgendaGroup(agendaGroup).setFocus();
				}
			}

			int count = kieSession.fireAllRules();
			kieSession.dispose();

			LOG.info("fireRule|session:{}|ruleCount:{}|result:{}|cost:{}", session, count, ruleRet.getScore(), (System.currentTimeMillis() - now));
			return ruleRet;
		} catch (Exception e) {
			LOG.error("fireRule|session:{}", session, e);
		}
		return null;
	}

	public static KieContainer ruleFromClassPath() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kieContainer = ks.getKieClasspathContainer();

		return kieContainer;
	}

	public static KieContainer ruleFromMaven() {
		KieServices ks = KieServices.Factory.get();

		ReleaseId releaseId = ks.newReleaseId("cn.zzh.demo", "demo-drools-rule", "0.0.1-SNAPSHOT");
//		ReleaseId releaseId = ks.newReleaseId("cn.zzh.demo", "demo-drools", "LATEST");

		KieContainer kieContainer = ks.newKieContainer(releaseId);
		KieScanner kScanner = ks.newKieScanner(kieContainer);
		kScanner.start(10000L);// 毫秒

		return kieContainer;
	}

	public static RedEnvelopeModel genModel() {
		RedEnvelopeModel model = new RedEnvelopeModel();
		model.setUserId(123);
		model.setDeviceId("abc");

		// 设备
		FigureDevice figureDevice = new FigureDevice();
		figureDevice.setRiskAppCount(1);
		figureDevice.setVmCount(1);
		figureDevice.setTamperCount(1);
		model.setFigureDevice(figureDevice);

		// 用户
		FigureUser figureUser = new FigureUser();
		figureUser.setLoginGapDay(60);
		figureUser.setRecentActiveDay(0);
		figureUser.setLoginDevices(Arrays.asList("123"));
		model.setFigureUser(figureUser);

		// 内容
		FigureContent figureContent = new FigureContent();
		figureContent.setRecListenDuration(100);
		figureContent.setRecListenCount(3);
		figureContent.setRecCommentCount(1);
		figureContent.setRecPublishCount(0);
		figureContent.setLiveListenDuration(1000);
		figureContent.setLiveListenCount(1);
		figureContent.setLiveCommentCount(1);
		figureContent.setLivePublishCount(1);
		figureContent.setLivePublishAvgDuration(10);
		model.setFigureContent(figureContent);

		// 交易
		FigureTrade figureTrade = new FigureTrade();
		figureTrade.setHistPayCount(3);
		figureTrade.setHistPayMoney(30);
		figureTrade.setAvgPayMoney(10);
		figureTrade.setIntervalPayDay(15);
		figureTrade.setRedEnvelopeIncomeScale(0.3);
		model.setFigureTrade(figureTrade);

		return model;
	}
}

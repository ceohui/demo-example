package cn.zzh.demo.drools;

import cn.zzh.demo.drools.redenvelope.*;
import com.alibaba.fastjson.JSON;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class MainTest {

	public static void main(String[] args) throws InterruptedException {

		RedEnvelopeModel model = genModel();
		RuleResult ruleRet = new RuleResult();

		// ==========================================
		KieSession kieSession = ruleFromJar();
//		KieSession kieSession = ruleFromClassPath();

		kieSession.setGlobal("ruleResult", ruleRet);
		kieSession.insert(model);

		kieSession.getAgenda().getAgendaGroup("red").setFocus();
		int count = kieSession.fireAllRules();


		// ==========================================
		System.out.println("触发规则：" + count);
		System.out.println("show：" + JSON.toJSONString(model, true));
		System.out.println("结果: " + JSON.toJSONString(ruleRet, true));


		kieSession.dispose();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					try {
						Thread.sleep(5000);
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

	public static KieSession ruleFromClassPath(){
		KieServices ks = KieServices.Factory.get();
		KieContainer kieContainer = ks.getKieClasspathContainer();

		KieSession kieSession = kieContainer.newKieSession("product");
		return kieSession;
	}

	public static KieSession ruleFromJar(){
		KieServices ks = KieServices.Factory.get();

		ReleaseId releaseId = ks.newReleaseId("cn.zzh.demo", "demo-drools-rule", "0.0.1-SNAPSHOT");
//		ReleaseId releaseId = ks.newReleaseId("cn.zzh.demo", "demo-drools", "LATEST");

		KieContainer kieContainer = ks.newKieContainer(releaseId);
		KieScanner kScanner = ks.newKieScanner(kieContainer);
		kScanner.start(10000L);// 毫秒

		return kieContainer.newKieSession("product");
	}

	public static RedEnvelopeModel genModel(){
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

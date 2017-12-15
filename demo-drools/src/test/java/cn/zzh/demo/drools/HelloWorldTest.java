package cn.zzh.demo.drools;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class HelloWorldTest {

	@Test
	public void testHelloWorld() {
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kieContainer = kieServices.newKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("helloWorldSession");
		kieSession.fireAllRules();
		kieSession.dispose();
	}

}

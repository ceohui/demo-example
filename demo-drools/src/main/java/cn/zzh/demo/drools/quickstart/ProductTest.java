package cn.zzh.demo.drools.quickstart;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class ProductTest {

//	public static void main(String[] args) {
//		// 收集已编写的规则，并对规则文件进行编译
//		KnowledgeBuilder kbBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
//		kbBuilder.add(ResourceFactory.newClassPathResource("rules/product.drl", ProductTest.class), ResourceType.DRL);
//
//		// 存放编译之后规则的对象
//		Collection<KnowledgePackage> pkgs = kbBuilder.getKnowledgePackages();
//
//		// 收集应用当中知识（knowledge）定义的知识库对象（KnowledgePackage），在一个 KnowledgeBase 当中可以包含普通的规则（rule）、 规则流(rule flow)、函数定义(function)、用户自定义对象（type model）等，并创建session对象（StatefulKnowledgeSession和 StatelessKnowledgeSession）
//		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
//		kbase.addKnowledgePackages(pkgs);
//
//		// 接收外部插入的数据fact对象（POJO），将编译好的规则包和业务数据通过fireAllRules()方法触发所有的规则执行。使用完成需调用dispose()方法以释放相关内存资源。
//		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
//		Product product = new Product();
//		product.setType(Product.GOLD);
//		ksession.insert(product);
//		ksession.fireAllRules();
//		ksession.dispose();
//
//		System.out.println("The discount for the product " + product.getType()
//				+ " is " + product.getDiscount());
//	}

	public static void main(String[] args) {
		KieServices ks = KieServices.Factory.get();
		KieContainer kieContainer = ks.getKieClasspathContainer();

		KieSession kieSession = kieContainer.newKieSession("product");

		Product product = new Product();
		product.setType(Product.DIAMOND);

		kieSession.insert(product);

		int count = kieSession.fireAllRules();

		System.out.println("触发规则：" + count);
		System.out.println("折扣：" + product.getDiscount());

		kieSession.dispose();

	}


}

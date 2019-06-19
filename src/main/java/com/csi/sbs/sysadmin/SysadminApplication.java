package com.csi.sbs.sysadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.service.impl.SandboxServiceImpl;








@SpringBootApplication
@EnableEurekaClient
public class SysadminApplication {
	
	
	@Bean
    @LoadBalanced
    public RestTemplate rest() {
        return new RestTemplate();
    }

	
	
	
	
	
	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(SysadminApplication.class, args);
		@SuppressWarnings("unused")
		RestTemplate r = context.getBean(org.springframework.web.client.RestTemplate.class);
		@SuppressWarnings("unused")
		SandboxServiceImpl s = (SandboxServiceImpl) context.getBean(com.csi.sbs.sysadmin.business.service.impl.SandboxServiceImpl.class);
//		Thread.sleep(10000);
//		//初始化日志相关配置
//		//InitLog.loadLogConfig(context,"sysadmin");
//		OfflineGenerateSandBoxUtil ou = new OfflineGenerateSandBoxUtil();
//		for(int i=0;i<=99;i++){
//			System.out.println("正在生成第"+i+"个sandboxid数据===========");
//			ou.generateSandBox(r,s);
//		}
//		System.out.println("100个sandboxid生成完毕================");
	}

}


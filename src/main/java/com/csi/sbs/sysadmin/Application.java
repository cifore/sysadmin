package com.csi.sbs.sysadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;






@SpringBootApplication
@EnableEurekaClient
public class Application {
	
	
	@Bean
    @LoadBalanced
    public RestTemplate rest() {
        return new RestTemplate();
    }

	
	
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		RestTemplate r = context.getBean(org.springframework.web.client.RestTemplate.class);
//		Thread.sleep(2000);
//		//初始化日志相关配置
//		//InitLog.loadLogConfig(context,"sysadmin");
//		OfflineGenerateSandBoxUtil ou = new OfflineGenerateSandBoxUtil();
//		for(int i=0;i<99;i++){
//			ou.generateSandBox(r);
//		}
//		System.out.println("100个sandboxid生成完毕");
	}

}


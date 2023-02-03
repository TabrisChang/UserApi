package tw.com.firstbank.fcbcore.fir.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"tw.com.firstbank.fcbcore.fir.service"}, exclude = {SecurityAutoConfiguration.class})
@EntityScan(basePackages = {"tw.com.firstbank.fcbcore.fir.service.domain"})
@EnableJpaRepositories(basePackages = {"tw.com.firstbank.fcbcore.fir.service.adapter.out.repository"})
@EnableTransactionManagement
public class ServiceApplication extends SpringBootServletInitializer {

  /**
   * @param application
   * @description Spring Boot 上到 Ap Server 時, 啟動與初始化設定的 method
   * @author Tabris Chang
   */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(ServiceApplication.class);
  }

  /**
   * @param args
   * @description Spring Boot 直接使用 jar 啟動時, 啟動與初始化設定的 method
   * @author Tabris Chang
   */
  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(ServiceApplication.class);
    springApplication.run(args);
  }

}

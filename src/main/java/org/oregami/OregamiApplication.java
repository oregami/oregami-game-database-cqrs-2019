package org.oregami;

import org.oregami.config.OregamiConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.LocaleResolver;

@SpringBootApplication(scanBasePackages = {"org.oregami"})
@Import(OregamiConfiguration.class)
public class OregamiApplication {

	public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(OregamiApplication.class, args);

        System.out.println("LocaleResolver: " + context.getBean(LocaleResolver.class).toString());

	}
}

package br.com.educhain;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
		.select()
		.apis(RequestHandlerSelectors.any())
		.paths(PathSelectors.any())
		.build();
		
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfo("Educhain MemPool)", 
			"A pool of transactions for EduBlockchain", 
			"v0", 
			"",	//Terms of service URL 
			new Contact("Eduardo Falcão", "edufalcao.wordpress.com", "eduardolfalcao@gmail.com"),
			"",	//License of API 
			"",	//URL of license 
			Collections.EMPTY_LIST);
	}

}
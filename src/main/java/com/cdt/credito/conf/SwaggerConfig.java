package com.cdt.credito.conf;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Lists;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableAutoConfiguration
@Import({springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class})
public class SwaggerConfig {

	@Autowired
	private TypeResolver typeResolver;

	@Bean
	public Docket creditoApi() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.cdt.credito"))
				.paths(PathSelectors.any()).build().pathMapping("/").apiInfo(apiInfo())
				.directModelSubstitute(LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(LocalDateTime.class, java.util.Date.class)
				.genericModelSubstitutes(ResponseEntity.class)
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(DeferredResult.class,
								typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
						typeResolver.resolve(WildcardType.class)))
				.useDefaultResponseMessages(false)
				.tags(
						new Tag("auth", "Authentication related Operations"),
						new Tag("user", "User Operations"),
						new Tag("profile", "User Profile Operations"),
						new Tag("conf", "Configuration Operations")
						)
				.globalResponseMessage(RequestMethod.GET,
						Lists.newArrayList(new ResponseMessageBuilder().code(500).message("500 message")
								.responseModel(new ModelRef("Error")).build()))
				.enableUrlTemplating(false)
				// Workaround to ignore parameters annotated with @ApiIgnore, 
				// this necessary because there is a bug in SpringFox 2.5, 
				// whe should test exclude this line in future versions of SpringFox
				.ignoredParameterTypes(ApiIgnore.class);
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Sistema de Análise de Crédito",
				". ",
				"1.0", "", new Contact("", "www.conductor.com.br", ""), "", "");
	}

	@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration("validatorUrl", // url
				"none", // docExpansion => none | list
				"alpha", // apiSorter => alpha
				"schema", // defaultModelRendering => schema
				new String[] {"get", "post", "put", "delete"}, // supportedSubmitMethods
				false, // enableJsonEditor => true | false
				true); // showRequestHeaders => true | false
	}
}

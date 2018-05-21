package com.cdt.credito.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthToken {

	String value() default "x-auth-token";
	String paramType() default "header";
	String description() default "Token de Autenticação obtido ao realizar login";
	boolean required() default true;

}

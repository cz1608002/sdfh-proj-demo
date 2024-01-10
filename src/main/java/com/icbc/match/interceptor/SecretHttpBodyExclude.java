package com.icbc.match.interceptor;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface SecretHttpBodyExclude {

}

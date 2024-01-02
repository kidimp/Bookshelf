package com.chous.bookshelf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) // В задании "кэш без времени жизни". Я указываю RetentionPolicy.RUNTIME,
// т.к. предполагаю существование кэша в течение всего времени выполнения приложения.
public @interface Cacheable {
}

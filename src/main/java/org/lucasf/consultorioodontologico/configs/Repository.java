package org.lucasf.consultorioodontologico.configs;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Stereotype;
import jakarta.inject.Named;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@RequestScoped
@Stereotype
@Retention(RUNTIME)
@Target(ElementType.TYPE)
@Named
public @interface Repository {
}

package io.github.omarmahamid.annotation;

import io.github.omarmahamid.async.ModeAsyncProfiler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Profiling {

    ModeAsyncProfiler mode() default ModeAsyncProfiler.CPU;

}

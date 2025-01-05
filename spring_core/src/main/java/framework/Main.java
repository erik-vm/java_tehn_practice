package framework;

import framework.annotations.ClassAnnotation;
import framework.annotations.MethodAnnotation;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

        var ctx = new AnnotationConfigApplicationContext(FrameworkConfig.class);

        try (ctx) {
            var beans = ctx.getBeansWithAnnotation(ClassAnnotation.class);

            for (Object bean : beans.values()) {
                Method[] methods = bean.getClass().getDeclaredMethods();

                for (Method method : methods) {

                    MethodAnnotation annotation = method.getAnnotation(MethodAnnotation.class);
                    if (annotation != null && annotation.value() > 1) {
                        method.invoke(bean);
                    }
                }
            }

        }

    }
}
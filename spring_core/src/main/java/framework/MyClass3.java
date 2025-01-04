package framework;

import framework.annotations.ClassAnnotation;
import framework.annotations.MethodAnnotation;

@ClassAnnotation
public class MyClass3 {

    @MethodAnnotation(2)
    public void method1() {
        System.out.println(getClass() + " method1");
    }

    @MethodAnnotation(1)
    public void method2() {
        System.out.println(getClass() + " method2");
    }

}

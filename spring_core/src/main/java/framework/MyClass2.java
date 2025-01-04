package framework;

import framework.annotations.MethodAnnotation;

public class MyClass2 {

    @MethodAnnotation(2)
    public void method1() {
        System.out.println(getClass() + " method1");
    }

    @MethodAnnotation(1)
    public void method2() {
        System.out.println(getClass() + " method2");
    }

}

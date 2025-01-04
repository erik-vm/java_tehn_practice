package proxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ListProvider {

    private static Logger logger = LogManager.getLogger(ListProvider.class);

    public MyList getList() {

        logger.debug("ListProvider.getList() called");

        MyList proxy = (MyList) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[] { MyList.class },
                new DynamicInvocationHandler());

        return new MyListImpl();
    }

    private static class DynamicInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {

            // this will be called for every method that is invoked on the proxy

            // delegate to underlying object
            // method.invoke(object, args);

            return null;
        }
    }
}

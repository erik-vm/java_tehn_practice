package proxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListProvider {

    private static Logger logger = LogManager.getLogger(ListProvider.class);

    public MyList getList() {

        logger.debug("ListProvider.getList() called");

        MyList realObject = new MyListImpl();

        MyList proxy = (MyList) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{MyList.class},
                new DynamicInvocationHandler(realObject));

        return proxy;
    }

    private static class DynamicInvocationHandler implements InvocationHandler {

        private final MyList realObject;

        public DynamicInvocationHandler(MyList realObject) {
            this.realObject = realObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {

            var stream = args == null ? Stream.empty() : Arrays.stream(args);

            String collect = stream.map(Object::toString).collect(Collectors.joining(", "));

            logger.debug("%s(%s)".formatted(method.getName(), collect));

            return method.invoke(realObject, args);

        }
    }
}

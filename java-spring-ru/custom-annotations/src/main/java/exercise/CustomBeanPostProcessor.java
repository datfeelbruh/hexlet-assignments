package exercise;

import java.lang.reflect.Proxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// BEGIN
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    public static final Logger MYLOGGER = LoggerFactory.getLogger(CustomBeanPostProcessor.class);
    private final Map<String, Class> annotatedBeans = new HashMap<>();
    private final Map<String, String> logLevels = new HashMap<>();
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (bean.getClass().isAnnotationPresent(Inspect.class)) {
            String logLevel = bean.getClass().getAnnotation(Inspect.class).level();
            annotatedBeans.put(beanName, bean.getClass());
            logLevels.put(beanName, logLevel);
        }

        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class beanClass = annotatedBeans.get(beanName);

        if (beanClass != null) {
            return Proxy.newProxyInstance(
                    beanClass.getClassLoader(),
                    beanClass.getInterfaces(),
                    (proxy, method, args) -> {

                        String message = String.format(
                                "Was called method: %s() with arguments: %s",
                                method.getName(),
                                Arrays.toString(args)
                        );
                        String logLevel = logLevels.get(beanName);
                        if (logLevel.equals("info")) {
                            MYLOGGER.info(message);
                        } else {
                            MYLOGGER.debug(message);
                        }
                        return method.invoke(bean, args);
                    }
            );
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
// END

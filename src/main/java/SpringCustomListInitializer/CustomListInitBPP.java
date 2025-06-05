package SpringCustomListInitializer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomListInitBPP implements BeanPostProcessor {
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        try {
            for (Field field : bean.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(CustomListInit.class)) {
                    field.setAccessible(true);
                    CustomListInit annotation = field.getAnnotation(CustomListInit.class);
                    List<Object> list = Arrays.stream(annotation.value())
                            .map(aClass -> Introspector.decapitalize(aClass.getSimpleName()))
                            .map(name -> applicationContext.getBean(name))
                            .collect(Collectors.toList());
                    field.set(bean, list);                // throws IllegalAccessException
                    return bean;
                }
            }
        } catch (Exception e) {

        }
        return bean;
    }
}

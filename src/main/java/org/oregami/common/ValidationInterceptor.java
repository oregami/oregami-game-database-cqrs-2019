package org.oregami.common;

import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
public class ValidationInterceptor<T extends Message<?>> implements MessageHandlerInterceptor<Message<T>> {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Object handle(UnitOfWork<? extends Message<T>> unitOfWork, InterceptorChain interceptorChain) throws Exception {
        handle(unitOfWork.getMessage());
        return interceptorChain.proceed();
    }

    private void handle(Message<T> message) throws ValidationException {

        Class<?> commandClass = message.getPayloadType();


        if (commandClass.isAnnotationPresent(CommandValidator.class)) {
            CommandValidator annotation = commandClass.getAnnotation(CommandValidator.class);
            if (annotation==null) {
                return;
            }

            Class validatorClass = annotation.value();
            Method[] methods = validatorClass.getDeclaredMethods();
            for (Method m : methods) {
                if (m.getParameterCount()==1 && m.getParameterTypes()[0].equals(commandClass)) {
                    if (m.getReturnType().equals(CommonResult.class)) {
                        System.out.println("Validation method found: " + m.toString());
                        Object validatorInstance = null;
                        try {
                            validatorInstance = applicationContext.getBean(validatorClass);
                            if (validatorInstance==null) {
                                throw new RuntimeException("Could not create spring bean for class " + validatorClass.getName() + ". Is it a spring @Component ?");
                            }
                            CommonResult<Object> result = (CommonResult<Object>) m.invoke(validatorInstance, message.getPayload());
                            if (result.hasErrors()) {
                                throw new ValidationException(result);
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }


        /*
        if (message.getPayload() instanceof CreateTransliteratedStringCommand) {
            CommonResult<Object> result = applicationContext.getBean(TransliteratedStringValidator.class).validate((CreateTransliteratedStringCommand) message.getPayload());
            if (result.hasErrors()) {
                throw new ValidationException(result);
            }
        }
        */
    }

}

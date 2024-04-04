package com.telegram.maxym.telbot.service.factory;

import com.telegram.maxym.telbot.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceFactory {

    private final ApplicationContext context;

    public <T extends MessageService> T getMessageService(Class<T> serviceClass) {
        return context.getBean(serviceClass);
    }
}

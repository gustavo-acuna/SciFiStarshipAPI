package com.gacuna.scifistarship.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Before("execution(* com.gacuna.scifistarship.service.StarShipService.getStarShipById(..)) && args(id)")
    public void logNegativeId(Long id) {
        if (id != null && id < 0) {
            log.warn("Se solicitó una nave con un ID negativo: {}", id);
        }
    }
}

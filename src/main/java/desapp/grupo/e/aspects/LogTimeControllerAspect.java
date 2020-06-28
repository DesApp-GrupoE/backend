package desapp.grupo.e.aspects;

import desapp.grupo.e.service.log.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@Aspect
public class LogTimeControllerAspect {

    @Around("@annotation(postMapping)")
    public Object aroundCallEndpoint(ProceedingJoinPoint pjp, PostMapping postMapping) throws Throwable {
        return executeAndLog(pjp, RequestMethod.POST, getPath(postMapping.value()));
    }

    @Around("@annotation(getMapping)")
    public Object aroundCallEndpoint(ProceedingJoinPoint pjp, GetMapping getMapping) throws Throwable {
        return executeAndLog(pjp, RequestMethod.GET, getPath(getMapping.value()));
    }

    @Around("@annotation(putMapping)")
    public Object aroundCallEndpoint(ProceedingJoinPoint pjp, PutMapping putMapping) throws Throwable {
        return executeAndLog(pjp, RequestMethod.PUT, getPath(putMapping.value()));
    }

    @Around("@annotation(deleteMapping)")
    public Object aroundCallEndpoint(ProceedingJoinPoint pjp, DeleteMapping deleteMapping) throws Throwable {
        return executeAndLog(pjp, RequestMethod.PUT, getPath(deleteMapping.value()));
    }

    private Object executeAndLog(ProceedingJoinPoint pjp, RequestMethod requestMethod, String path) throws Throwable {
        long callTime = System.currentTimeMillis();
        Object proceed;
        try {
            proceed = pjp.proceed();
        } finally {
            long totalTime = System.currentTimeMillis() - callTime;
            Log.info(String.format("%s to endpoint '%s' - Time elapsed: %s ms.", requestMethod.name(), path, totalTime));
        }
        return proceed;
    }

    private String getPath(String[] value) {
        String path = "";
        if (value.length > 0) {
            path = value[0];
        }
        return path;
    }

}

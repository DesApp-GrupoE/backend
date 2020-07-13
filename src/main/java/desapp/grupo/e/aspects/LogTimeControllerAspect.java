package desapp.grupo.e.aspects;

import desapp.grupo.e.service.log.Log;
import desapp.grupo.e.service.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class LogTimeControllerAspect {

    private StringUtils stringUtils = new StringUtils();

    @Around("@annotation(postMapping)")
    public Object aroundCallEndpoint(ProceedingJoinPoint pjp, PostMapping postMapping) throws Throwable {
        return executeAndLog(pjp, RequestMethod.POST);
    }

    @Around("@annotation(getMapping)")
    public Object aroundCallEndpoint(ProceedingJoinPoint pjp, GetMapping getMapping) throws Throwable {
        return executeAndLog(pjp, RequestMethod.GET);
    }

    @Around("@annotation(putMapping)")
    public Object aroundCallEndpoint(ProceedingJoinPoint pjp, PutMapping putMapping) throws Throwable {
        return executeAndLog(pjp, RequestMethod.PUT);
    }

    @Around("@annotation(deleteMapping)")
    public Object aroundCallEndpoint(ProceedingJoinPoint pjp, DeleteMapping deleteMapping) throws Throwable {
        return executeAndLog(pjp, RequestMethod.DELETE);
    }

    private Object executeAndLog(ProceedingJoinPoint pjp, RequestMethod requestMethod) throws Throwable {
        long callTime = System.currentTimeMillis();
        Object proceed;
        try {
            proceed = pjp.proceed();
        } finally {
            long totalTime = System.currentTimeMillis() - callTime;
            String path = getPath();
            Log.info(String.format("%s to endpoint '%s' - Time elapsed: %s ms.", requestMethod.name(), path, totalTime));
        }
        return proceed;
    }

    private String getPath() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String queryString = request.getQueryString();
        String path = request.getContextPath() + request.getServletPath();
        if(!stringUtils.isNullOrEmpty(queryString)) {
            path += "?" + queryString;
        }
        return path;
    }

}

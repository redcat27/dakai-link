package www.dakai.link.common.spring;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import www.dakai.link.common.beans.RequestContext;
import www.dakai.link.common.beans.ResultCodeEnum;
import www.dakai.link.common.exceptions.InternalException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public abstract class LoginHandlerInterceptor<T> implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("request uri:" + request.getRequestURI());
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }
        if (HttpMethod.GET.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            throw new InternalException(ResultCodeEnum.NO_LOGIN);
        }
        T account = getAccountByToken(token);
        if (account == null) {
            throw new InternalException(ResultCodeEnum.TOKEN_INVALID);
        }
        RequestContext context = RequestContext.current();
        BeanUtils.copyProperties(account, context);
        return true;
    }

    protected abstract T getAccountByToken(String token);
}

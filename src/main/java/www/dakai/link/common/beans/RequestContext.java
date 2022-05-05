package www.dakai.link.common.beans;

import lombok.Data;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Set;

/**
 * className:RequestContext
 * package:com.pi2star.airmon.agent.common.beans
 * Description:
 *
 * @date: 2021/11/22 2:29 下午
 * @author: tangchengzao
 */
@Data
public class RequestContext {

    private String id;
    /**
     * 用户编号
     */
    private String number;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户昵称
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 是否管理员
     */
    private Boolean manager;
    /**
     * 权限列表
     */
    private Set<String> permissions;

    private static final String REQUEST_CONTEXT = "request_context";

    public static RequestContext current() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            RequestContext requestContext = (RequestContext) requestAttributes.getAttribute(REQUEST_CONTEXT, RequestAttributes.SCOPE_SESSION);
            if (requestContext == null) {
                requestContext = new RequestContext();
                requestAttributes.setAttribute(REQUEST_CONTEXT, requestContext, RequestAttributes.SCOPE_SESSION);
            }
            return requestContext;
        } catch (IllegalStateException e) {
            return new RequestContext();
        }
    }
}

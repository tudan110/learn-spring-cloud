package indi.tudan.learn.cloud.gateway.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 网关过滤器
 * <p>
 * 加入到 Spring 容器
 */
@Component
public class UserLoginZuulFilter extends ZuulFilter {

    @Override
    public boolean shouldFilter() {

        // 该过滤器需要执行
        return true;
    }

    @Override
    public Object run() {
        // 编写业务逻辑
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse resp = requestContext.getResponse();
        String method = request.getMethod();
        
        resp.setHeader("Access-Control-Allow-Origin","*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");  // 是否允许客户端携带验证信息，例如cookie
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT, OPTIONS");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token");
        resp.setHeader("Access-Control-Expose-Headers", "token"); 
        
		if ("OPTIONS".equals(method)) {
			requestContext.setSendZuulResponse(false);
			requestContext.setResponseStatusCode(HttpServletResponse.SC_OK);
			return null;
		}        
        
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
        	token = request.getParameter("token");
        }        
        if (StringUtils.isEmpty(token)) {

            // 过滤该请求，不对其进行路由
            requestContext.setSendZuulResponse(false);

            // 设置响应状态码
            requestContext.setResponseStatusCode(401);

            // 设置响应状态码
            requestContext.setResponseBody("token is empty!!");

            return null;
        }
        return null;
    }

    @Override
    public String filterType() {
        // 设置过滤器类型为：pre
    	return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {

        // 设置执行顺序为 0
        return 0;
    }

}
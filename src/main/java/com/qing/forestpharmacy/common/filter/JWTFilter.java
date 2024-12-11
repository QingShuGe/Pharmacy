package com.qing.forestpharmacy.common.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.qing.forestpharmacy.shiro.token.JwtToken;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

public class JwtFilter extends BasicHttpAuthenticationFilter {

    /*
     * 过滤器执行流程：
     * isAccessAllowed()->isLoginAttempt()->executeLogin()
     */

    // 是否允许访问
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        if (this.isLoginAttempt(request, response)) {
            // 有认证意愿
            try {
                this.executeLogin(request, response);
            }catch (Exception e) {
                // token错误
                responseError(e.getMessage());
            }
        }
        // 没有认证意愿（可能是登录行为或者为游客访问）,放行
        // 此处放行是因为有些操作不需要权限也可以执行，而对于那些需要权限才能执行的操作自然会因为没有token而在权限鉴定时被拦截
        return true;
    }

    // 是否有认证意愿（即是否携带token）
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Authorization");
        return token != null;
    }

    // 执行认证
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Authorization");

        JwtToken jwt = new JwtToken(token);
        // 使用自定义的JWTToken而不是默认的UsernamePasswordToken
        Subject subject = getSubject(request,response);
        subject.login(jwt);
        // 调用了realm中的认证方法，没有出现异常则证明认证成功
        return true;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req= (HttpServletRequest) request;
        HttpServletResponse res= (HttpServletResponse) response;
        res.setHeader("Access-control-Allow-Origin", req.getHeader("Origin"));
        res.setHeader("Access-control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        res.setHeader("Access-control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
            res.setStatus(HttpStatus.OK.value());
            // 返回true则继续执行拦截链，返回false则中断后续拦截，直接返回，option请求显然无需继续判断，直接返回
            return false;
        }
        return super.preHandle(request, response);
    }

//    /**
//     * shiro利用 JWT Token 登录成功后，进入该方法
//     * @param token
//     * @param subject
//     * @param request
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    @Override
//    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
//        HttpServletResponse httpResponse = WebUtils.toHttp(response);
//        String newToken = null;
//
//        //登录成功后刷新token
//        if(token instanceof JWTToken) {
//            newToken = JWTUtils.refreshTokenExpired(token.getCredentials().toString(),JwtUtils.SECRET);
//        }
//        if(newToken != null){
//            httpResponse.setHeader(JwtUtils.AUTH_HEADER,newToken);
//        }
//        return true;
//    }

//     非法请求跳转
    private void responseError(String msg){
        try {
            System.out.println("我报错了"+msg);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

package indi.xp.coachview.session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import indi.xp.coachview.common.BusinessErrorCodeEnum;
import indi.xp.coachview.common.Constants;
import indi.xp.coachview.model.vo.UserVo;
import indi.xp.common.exception.BusinessException;
import indi.xp.common.restful.ResponseResult;
import indi.xp.common.utils.SpringContextUtil;
import indi.xp.common.utils.StringUtils;
import indi.xp.common.utils.WebUtils;

public class SessionFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(SessionFilter.class);

    private static final boolean sessionFilterSwitchOn = false; // 开启SessionFilter

    // 不进行拦截的URL
    private String[] skipPaths = { "OPTIONS|*", "*|/swagger-ui.html", "*|/webjars", "*|/swagger", "*|/v2/api-docs",
        "*|/hello", "GET|/public/verification-code/", "POST|/user/sign-in", "GET|/user/check/|/by-phone",
        "POST|/file/upload|*", "POST|/match/import|*", "POST|/team/|/import-members" };

    public SessionFilter() {
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void destroy() {
    }

    /**
     * 根据session及session里的user情况进行过滤.
     * 
     * @throws ServletException
     * @throws IOException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        SessionManager sessionManager = SpringContextUtil.getBean("sessionManager");

        String requestMethod = req.getMethod();
        String requestUri = req.getRequestURI();
        String requestUrl = req.getRequestURL().toString();
        logger.info(requestUrl);

        for (String path : skipPaths) {
            String[] url = path.split("\\|");
            String urlMethod = url[0];
            String urlPreFfix = url.length > 1 ? url[1] : "";
            String urlPostfix = url.length > 2 ? url[2] : "";
            if ((urlMethod.equalsIgnoreCase(requestMethod) || "*".equals(urlMethod))
                && (StringUtils.startsWithIgnoreCase(requestUri, urlPreFfix) || "*".equals(urlPreFfix))
                && (StringUtils.endsWithIgnoreCase(requestUri, urlPostfix) || "*".equals(urlPostfix))
                || !sessionFilterSwitchOn) {
                chain.doFilter(req, response);
                return;
            }
        }

        String token = req.getHeader(Constants.Header.TOKEN);
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(Constants.Header.TOKEN);
        }
        Session session = sessionManager.getSession(token);
        if (session != null) {
            UserVo sessionUser = session.getSessionUser();
            if (sessionUser != null) {
                String uid = req.getHeader(Constants.Header.UID);
                // 如果uid不为空，则校验uid与session中的userId是否匹配
                if (StringUtils.isNotBlank(uid) && !uid.equals(sessionUser.getUid())) {
                    goNotMatchUserSessionURL(resp, uid, sessionUser.getUid());
                } else {
                    // 将SessionContext设置到ThreadLocal
                    SessionConext.setThreadLocalSessionContext(session.getSessionConext());
                    // 更新session时间
                    sessionManager.updateSessionTime(token);
                    chain.doFilter(req, response);
                    return;
                }
            } else {
                goNoSessionURL(resp);
            }
        } else {
            goNoSessionURL(resp);
        }

    }

    /**
     * 返回no-session信息
     */
    private void goNoSessionURL(HttpServletResponse response) {
        logger.info("NO_SESSION, 用户未登录 --------------------------------------------------");

        /**
         * 440 (Unofficial codes) : Login Timeout ,The client's session has
         * expired and must login again.
         */
        response.setStatus(440);
        BusinessException be = new BusinessException(BusinessErrorCodeEnum.NO_SESSION);
        String result = JSON.toJSONString(ResponseResult.buildErrorResult(be));
        WebUtils.sendMessage(response, result);
    }

    /**
     * 用户请求UID与session中userId不匹配时的处理
     */
    private synchronized void goNotMatchUserSessionURL(HttpServletResponse response, String uid, String sessionUseId) {
        logger.warn("Forbidden, UID<" + uid + "> Not Macth UserSession<" + sessionUseId + "> !");
        response.setStatus(403); // Forbidden
        BusinessException be = new BusinessException(BusinessErrorCodeEnum.USER_AND_SESSION_NOT_MATCH);
        String result = JSON.toJSONString(ResponseResult.buildErrorResult(be));
        WebUtils.sendMessage(response, result);
    }

    /**
     * 从cookie中获取用户名和密码.
     */
    @SuppressWarnings("unused")
    private UserVo getUserByCookie(HttpServletRequest request, HttpServletResponse resp) {
        String username = null;
        String password = null;
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Constants.Cookie.USER_NAME.equals(cookie.getName())) {
                    username = cookie.getValue();
                }
                if (Constants.Cookie.USER_PASSWORD.equals(cookie.getName())) {
                    password = cookie.getValue();
                }
            }
        }
        return null;
    }

}

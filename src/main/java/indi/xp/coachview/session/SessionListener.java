package indi.xp.coachview.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionListener implements HttpSessionListener {

    private Logger logger = LoggerFactory.getLogger(SessionListener.class);

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        // HttpSession session = httpSessionEvent.getSession();
        logger.info("session destroyed ...");
    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        // PtoneSessionContext.AddSession(httpSessionEvent.getSession());
        logger.info("session created ...");
    }
}

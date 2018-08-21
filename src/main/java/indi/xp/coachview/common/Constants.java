package indi.xp.coachview.common;

public class Constants {

    public static final String TRUE = "是";
    public static final String FALSE = "否";

    public static final String VALIDATE = "1";
    public static final String INVALIDATE = "0";

    /**
     * http请求header变量名
     */
    public static class Header {
        public static final String TOKEN = "Token";
        public static final String UID = "Uid";
        public static final String LANGUAGE = "Accept-Language";
        public static final String TIMEZONE = "Timezone";
        public static final String SPACE_ID = "SpaceId";
        public static final String SOCKET_ID = "SocketId";
        public static final String TRACE_ID = "TraceId";
    }

    public static class Session {
    }

    public static class Cookie {
        public static final String USER_NAME = "userName";
        public static final String USER_PASSWORD = "userPassword";
    }
}

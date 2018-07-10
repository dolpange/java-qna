package codesquad.util;

import codesquad.domain.User;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static final String USER_KEY = "sessionedUser";

    public static void setUser(HttpSession session, User user) {
        session.setAttribute(USER_KEY, user);
    }

    public static User getUser(HttpSession session) {
        return (User) session.getAttribute(USER_KEY);
    }

    public static String getUserId(HttpSession session) {
        return getUser(session).getUserId();
    }

    public static boolean checkLogin(HttpSession session) {
        return getUser(session) != null;
    }

    public static boolean checkLoginUser(HttpSession session, User user) {
        return user.matchUserId(getUser(session).getUserId());
    }
}

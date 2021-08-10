package yll.component.app.sms;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @类描述 session的单例管理
 * @创建人 张晓磊
 * @创建时间 2015-10-23 上午11:00:02
 */
public class MySessionContext {
	private static HashMap mymap = new HashMap();

    public static synchronized void AddSession(HttpSession session) {
        if (session != null) {
            mymap.put(session.getId(), session);
        }
    }

    public static synchronized void DelSession(HttpSession session) {
        if (session != null) {
            mymap.remove(session.getId());
        }
    }

    public static synchronized HttpSession getSession(String session_id) {
        if (session_id == null){
        	return null;
        }
        return (HttpSession) mymap.get(session_id);
    }
}

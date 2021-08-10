package yll.component.log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.plug.security.Securitys;
import com.github.relucent.base.util.web.WebUtil;

import yll.common.annotations.OpLogAx;
import yll.common.identifier.IdHelper;
import yll.entity.OpLog;
import yll.service.OpLogService;


/**
 * 操作日志切面
 */
@Aspect
@Component
public class OpLogAspectComponent {

    // ==============================Fields===========================================
    /** 日志缓存尺寸 */
    private static final int BUFFER_SIZE = 4096;

    /** 批量插入限制 */
    private static final int INSERT_BATCH_LIMIT = 4096;

    /** 日志缓存队列 */
    private static final BlockingQueue<OpLog> queue = new ArrayBlockingQueue<OpLog>(BUFFER_SIZE);

    @Autowired
    private OpLogService opLogService;

    @Autowired
    private HttpServletRequest requestProxy;

    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /** 切面 */
    @Around("@annotation(yll.common.annotations.OpLogAx) and execution(* yll..*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Principal principal = securitys.getPrincipal();
        try {
            return point.proceed();
        } finally {
            if (principal == null || Principal.NONE.equals(principal)) {
                principal = securitys.getPrincipal();
            }
            log(point, principal);
        }
    }

    private void log(ProceedingJoinPoint point, Principal principal) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        if (principal == null || Principal.NONE.equals(principal)) {
            return;
        }
        OpLogAx opLogAx = method.getAnnotation(OpLogAx.class);
        if (opLogAx == null) {
            return;
        }

        String comment = opLogAx.value();
        String url = requestProxy.getMethod() + "|" + WebUtil.getPathWithinApplication(requestProxy);
        String userIp = getRemoteIp(requestProxy);

        OpLog log = new OpLog();
        log.setId(IdHelper.nextId());
        log.setUrl(url);
        log.setComment(comment);
        log.setUserId(principal.getUserId());
        log.setUserName(principal.getName());
        log.setUserIp(userIp);

        queue.offer(log);
    }

    private String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            int i = ip.indexOf(",");
            return i == -1 ? ip : ip.substring(0, i);
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            int i = ip.indexOf(",");
            return i == -1 ? ip : ip.substring(0, i);
        }
        return request.getRemoteAddr();
    }

    @Scheduled(fixedDelay = 1000)
    protected void scheduled() {
        List<OpLog> logs = null;

        for (int i = 0; i < BUFFER_SIZE; i++) {
            OpLog log = queue.poll();

            if (log == null) {
                break;
            }

            if (logs == null) {
                logs = new ArrayList<OpLog>();
            }

            logs.add(log);

            // 数据库一次插入限制
            if (logs.size() >= INSERT_BATCH_LIMIT) {
                opLogService.inserts(logs.toArray(new OpLog[logs.size()]));
                logs.clear();
            }

        }

        if (logs != null && !logs.isEmpty()) {
            opLogService.inserts(logs.toArray(new OpLog[logs.size()]));
        }
    }
}

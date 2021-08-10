package yll.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.util.net.NetworkHelper;

import yll.common.BaseConstants.Ids;
import yll.common.BaseConstants.Symbols;
import yll.common.context.ContextHelper;
import yll.common.identifier.IdHelper;
import yll.common.standard.AuditableUtil;
import yll.entity.ServerInfo;
import yll.mapper.ServerInfoMapper;

@Transactional
@Service
public class ServerInfoService {
    // ==============================Fields===========================================
    @Autowired
    private ServerInfoMapper serverInfoMapper;
    // ==============================Methods==========================================
    // ...

    // ==============================Overrides========================================
    /** 调度3分钟一次 */
    @Scheduled(initialDelay = 180000, fixedDelay = 180000)
    protected void scheduled() {
        ServerInfo hs = serverInfoMapper.getById(Ids.CURRENT_SERVER_ID);
        if (hs != null) {
            AuditableUtil.setUpdated(hs, Principal.NONE);
            serverInfoMapper.update(hs);
        } else {
            ContextHelper.getBean(ServerInfoService.class).initialize();
        }
    }

    @PostConstruct
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    protected void initialize() {
        long expireAtPoint = System.currentTimeMillis() - DateUtils.MILLIS_PER_DAY;
        String currentId = Ids.CURRENT_SERVER_ID;
        String mac = StringUtils.join(NetworkHelper.getMacAddress(), Symbols.COMMA);
        String host = NetworkHelper.getHostAddress();
        int sequence = 0;

        Set<Integer> sequenceSet = new HashSet<>();
        List<String> expireIds = new ArrayList<>();
        ServerInfo current = null;
        for (ServerInfo hs : serverInfoMapper.findAll()) {
            String id = hs.getId();
            if (currentId.equals(id)) {
                current = hs;
                continue;
            }
            Date hsData = ObjectUtils.defaultIfNull(hs.getUpdatedAt(), hs.getCreatedAt());
            if (hsData == null || hsData.getTime() < expireAtPoint) {
                expireIds.add(id);
                continue;
            }
            sequenceSet.add(hs.getSequence());
        }
        for (String id : expireIds) {
            serverInfoMapper.deleteById(id);
        }
        if (current == null) {
            for (sequence = 1; sequence < Short.MAX_VALUE; sequence++) {
                if (!sequenceSet.contains(sequence)) {
                    break;
                }
            }
            current = new ServerInfo();
            current.setId(currentId);
            current.setMac(mac);
            current.setHost(host);
            current.setSequence(sequence);
            AuditableUtil.setCreated(current, Principal.NONE);
            AuditableUtil.setUpdated(current, Principal.NONE);
            serverInfoMapper.insert(current);
        } else {
            sequence = current.getSequence();
            current.setMac(mac);
            current.setHost(host);
            AuditableUtil.setUpdated(current, Principal.NONE);
            serverInfoMapper.update(current);
        }

        // 为序列ID添加一个后缀(避免多节点并发可能存在的ID冲突)
        final String idSuffix = String.valueOf(sequence);
        new IdHelper() {
            {
                IdHelper.setSuffix(idSuffix);
            }
        };
    }
}

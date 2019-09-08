package com.lee.auth.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lee.common.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/**
 * @author haitao.li
 */
public class RedisClientDetailService extends JdbcClientDetailsService {
    private static final String REDIS_KEY = "oauth_client_details";
    private static final Logger logger = LoggerFactory.getLogger(RedisClientDetailService.class);
    @Resource
    private SysClientService sysClientService;

    private RedisTemplate<String, Object> redisTemplate;

    public RedisClientDetailService(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        ClientDetails clientDetails = null;
        String value = (String) redisTemplate.boundHashOps(REDIS_KEY).get(clientId);
        if (value == null) {
            clientDetails = getAndCacheClient(clientId);
        } else {
            try {
                clientDetails = JsonUtil.fromJson(value, BaseClientDetails.class);
            } catch (IOException e) {
                logger.error("cache clientId failed : key: {} ,exception: {}", clientId, e);
            }
        }
        return clientDetails;
    }

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        super.addClientDetails(clientDetails);
        this.getAndCacheClient(clientDetails.getClientId());
    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        super.updateClientDetails(clientDetails);
        this.getAndCacheClient(clientDetails.getClientId());
    }

    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        super.updateClientSecret(clientId, secret);
        this.getAndCacheClient(clientId);
    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        super.removeClientDetails(clientId);
        this.removeClientFromCache(clientId);
    }

    private void removeClientFromCache(String clientId) {
        redisTemplate.boundHashOps(clientId).delete(clientId);
    }

    @Override
    public List<ClientDetails> listClientDetails() {
        List<ClientDetails> list = super.listClientDetails();
        list.forEach(clientDetails -> {
            this.getAndCacheClient(clientDetails.getClientId());
        });
        return list;
    }

    /**
     * 将client缓存到redis中
     *
     * @param clientId 客户端ID
     * @return 客户端信息
     */
    private ClientDetails getAndCacheClient(String clientId) {
        ClientDetails clientDetails = super.loadClientByClientId(clientId);
        String value = null;
        try {
            value = JsonUtil.toJson(clientDetails);
        } catch (JsonProcessingException e) {
            logger.error("transfer clientDetails to json format failed,clientId:{},e:{}", clientId, e);
        }
        redisTemplate.boundHashOps(REDIS_KEY).put(clientId, value);
        logger.info("缓存clientId,key: {},value: {}", clientId, clientDetails);

        return clientDetails;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}

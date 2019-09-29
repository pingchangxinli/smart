package com.lee.gateway.handler;

import ch.qos.logback.core.util.TimeUtil;
import com.google.code.kaptcha.Producer;
import com.lee.gateway.Contants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码生成逻辑
 * @author haitao.li
 */
@Configuration
@Slf4j
public class KatchaHandler implements HandlerFunction {

    @Resource
    private Producer producer;
    @Resource
    private RedisTemplate redisTemplate;
    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        String katcha = producer.createText();
        //获取随机数，根据此数字存入redis
        String random = request.queryParam(Contants.PARAM_RANDOM_KEY).get();

        if(StringUtils.isEmpty(random)) {
            return ServerResponse.status(HttpStatus.BAD_REQUEST).build();
        }
        // 将验证码放入到缓存中
        redisTemplate.opsForValue().set(Contants.KATCHA_KEY_PRE + random,random,60, TimeUnit.SECONDS);

        BufferedImage image =  producer.createImage(katcha);
        // 转换流信息写出
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", out);
        } catch (IOException e) {
            log.error("[katchaHandler] producer katcha error",e);
        }
        return ServerResponse.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG)
                .body(BodyInserters.fromResource(new ByteArrayResource(out.toByteArray())));
    }
}

package com.example.data_module.exception;

import lombok.extern.log4j.Log4j2;

import java.net.ConnectException;

@Log4j2
public class RedisException extends ConnectException {

    public RedisException(String keyword) {
        log.error("Redis에 {} 조회수를 업데이트 실패", keyword);
    }

}

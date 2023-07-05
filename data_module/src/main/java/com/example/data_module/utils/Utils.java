package com.example.data_module.utils;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.ServerSocket;

@Log4j2
public class Utils {

    public static boolean isPortAvailable(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            return true;
        } catch (IOException e) {
            log.info("{} 포트 이미 사용중", port);
            return false;
        }
    }
}

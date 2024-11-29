package com.kaolee.hotel.context;

public class BaseContext {

        public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

        public static void setCurrentId(String id) {
            threadLocal.set(id);
        }

        public static String getCurrentId() {
            return String.valueOf(threadLocal.get());
        }

        public static void removeCurrentId() {
            threadLocal.remove();
        }

    }

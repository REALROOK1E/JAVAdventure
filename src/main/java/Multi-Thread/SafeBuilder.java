package com.xiaoha;

import java.sql.*;

/**
 * @author: ZeKai
 * @date: 2025/4/17
 * @description:
 **/
public class SafeBuilder {
    // 在这里定义 ThreadLocal 变量
    ThreadLocal<StringBuilder> sb;
    
    public SafeBuilder() {
        // 初始化 ThreadLocal
        sb= ThreadLocal.withInitial(StringBuilder::new);
    }

    public void appendText(String text) {
        // 将传入的字符串追加到当前线程的 StringBuilder 中
        sb.get().append(text);
    }

    public String getText() {
        // 获取当前线程的 StringBuilder 中的字符串内容
        return sb.get().toString();
    }

    public static void main(String[] args) {
        SafeBuilder processor = new SafeBuilder();

        // 创建多个线程，每个线程添加不同的字符串
        Thread thread1 = new Thread(() -> {
            processor.appendText("Hello");
            processor.appendText(" ");
            processor.appendText("World");
            System.out.println(Thread.currentThread().getName() + ": " + processor.getText());
        });

        Thread thread2 = new Thread(() -> {
            processor.appendText("Java");
            processor.appendText(" ");
            processor.appendText("Programming");
            System.out.println(Thread.currentThread().getName() + ": " + processor.getText());
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
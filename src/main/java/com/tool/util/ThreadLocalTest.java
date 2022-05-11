package com.tool.util;

import lombok.SneakyThrows;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * @author Embrace
 * git: https://gitee.com/EmbraceQAQ
 * @version 1.0
 * @since JDK 1.8
 * Date: 2022/5/11 15:40
 * Description:
 * FileName: ThreadLocalTest
 */
public class ThreadLocalTest extends Thread{

    private List<String> messages = Lists.newArrayList();

    public static final ThreadLocal<ThreadLocalTest> holder = ThreadLocal.withInitial(ThreadLocalTest::new);

    public static void add(String message) {
        holder.get().messages.add(message);
    }

    public static List<String> clear() {
        List<String> messages = holder.get().messages;
        holder.remove();
        System.out.println("size: " + holder.get().messages.size());
        return messages;
    }

    public static void main(String[] args) {
        ThreadLocalTest threadLocalTest=new ThreadLocalTest();
        threadLocalTest.start();
        ThreadLocalTest.add("hello ThreadLocal");
        System.out.println(holder.get().messages);
        ThreadLocalTest.clear();
    }


    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(100);
        add("hello i am thread");
        System.out.println(holder.get().messages);
        clear();
    }
}

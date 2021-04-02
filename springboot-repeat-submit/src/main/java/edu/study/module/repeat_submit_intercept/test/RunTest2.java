package edu.study.module.repeat_submit_intercept.test;

import edu.study.module.repeat_submit_intercept.entity.User;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务已启动，则执行此方法进行模拟多线程测试
 *
 * @author zl
 * @create 2021-04-02 22:44
 */
@Component
public class RunTest2 implements ApplicationRunner {

    @Resource
    private RestTemplate restTemplate;


    static int count = 0;
    /**
     * 总访问量是clientNum
     */
    private int threadNum = 4;
    /**
     * 并发量是threadNum
     */
    private int clientNum = 10;

    float avgExecTime = 0;
    float sumExecTime = 0;
    long firstExecTime = Long.MAX_VALUE;
    long lastDoneTime = Long.MIN_VALUE;
    float totalExecTime = 0;

    class ThreadRecord {
        long startTime;
        long endTime;

        public ThreadRecord(long st, long et) {
            this.startTime = st;
            this.endTime = et;
        }

    }

    @Override
    public void run(ApplicationArguments args) {
        final ConcurrentHashMap<Integer, ThreadRecord> records = new ConcurrentHashMap<>();
        // 建立ExecutorService线程池，threadNum个线程可以同时访问
        ExecutorService exec = Executors.newFixedThreadPool(threadNum);
        // 模拟clientNum个客户端访问
        final CountDownLatch doneSignal = new CountDownLatch(clientNum);

        System.out.println("执行多线程测试>>>>>RunTest2.java");
        String url = "http://localhost:8080/noRepeat/submit";

        for (int i = 0; i < clientNum; i++) {
            Long userId = Long.parseLong(String.valueOf(1));
            String userName = "userName" + 1;
            User user = new User(userId, userName);
            HttpEntity request = buildRequest(user);

            Runnable run = () -> {
                int index = getIndex();
                long systemCurrentTimeMillis = System.currentTimeMillis();
                try {
                    System.out.println("Thread:" + Thread.currentThread().getName() + ", time:" + System.currentTimeMillis());
                    ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
                    System.out.println("Thread:" + Thread.currentThread().getName() + "," + response.getBody());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                records.put(index, new ThreadRecord(systemCurrentTimeMillis, System.currentTimeMillis()));
                doneSignal.countDown();// 每调用一次countDown()方法，计数器减1
            };
            exec.execute(run);
        }

        try {
            // 计数器大于0 时，await()方法会阻塞程序继续执行
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 获取每个线程的开始时间和结束时间
         */
        for (int i : records.keySet()) {
            ThreadRecord r = records.get(i);
            sumExecTime += ((double) (r.endTime - r.startTime)) / 1000;

            if (r.startTime < firstExecTime) {
                firstExecTime = r.startTime;
            }
            if (r.endTime > lastDoneTime) {
                this.lastDoneTime = r.endTime;
            }
        }

        this.avgExecTime = this.sumExecTime / records.size();
        this.totalExecTime = ((float) (this.lastDoneTime - this.firstExecTime)) / 1000;
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(4);

        System.out.println("======================================================");
        System.out.println("线程数量:\t\t" + threadNum);
        System.out.println("客户端数量:\t" + clientNum);
        System.out.println("平均执行时间:\t" + nf.format(this.avgExecTime) + "秒");
        System.out.println("总执行时间:\t" + nf.format(this.totalExecTime) + "秒");
        System.out.println("吞吐量:\t\t" + nf.format(this.clientNum / this.totalExecTime) + "次每秒");
    }

    public static int getIndex() {
        return ++count;
    }

    private HttpEntity buildRequest(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "yourToken");
        Map<String, Object> body = new HashMap<>(2);
        body.put("userId", user.getUserId());
        body.put("userName", user.getUserName());
        return new HttpEntity<>(body, headers);
    }
}

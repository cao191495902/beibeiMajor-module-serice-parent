package com.beibeiMajor.util.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class ExecutorMonitor implements Runnable{

    private ConcurrentHashMap<String,LocalExecutor> hashMap = null;

    public ExecutorMonitor(ConcurrentHashMap<String, LocalExecutor> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public void run() {
        for (ConcurrentHashMap.Entry<String,LocalExecutor> item: hashMap.entrySet()) {
            LocalExecutor executor = item.getValue();
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor.getExecutorService();
            if (threadPoolExecutor.getQueue().size() > executor.getBlockQueueWarningSize()){
                log.warn("线程池："+executor.getPoolName() + "(最大线程数为" + executor.getPoolSize() + "),队列堆积为：" + threadPoolExecutor.getQueue().size()
                        + ",超过预警值：" + executor.getBlockQueueWarningSize());
            }

        }
    }
}

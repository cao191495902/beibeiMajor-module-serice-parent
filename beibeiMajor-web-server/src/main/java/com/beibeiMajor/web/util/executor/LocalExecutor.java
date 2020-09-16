package com.beibeiMajor.web.util.executor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LocalExecutor {
    private String poolName;
    private int poolSize;
    private int blockQueueWarningSize;
    private ExecutorService executorService = null;

    public String getPoolName() {
        return poolName;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public int getBlockQueueWarningSize() {
        return blockQueueWarningSize;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public LocalExecutor(String poolName, int poolSize, int blockQueueWarningSize) {
        this.poolName = poolName;
        this.poolSize = poolSize;
        this.blockQueueWarningSize = blockQueueWarningSize;
        this.executorService = new ThreadPoolExecutor(poolSize,poolSize,0L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000));
    }
}

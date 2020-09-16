package com.beibeiMajor.web.util.executor;

import java.util.concurrent.*;

public class LocalExecutorMananger {

    public static final String MATCH_MANAGE = "matchManage";

    private static ConcurrentHashMap<String,LocalExecutor> hashMap = null;
    public LocalExecutorMananger() {
    }

    private static void init(){
        hashMap = new ConcurrentHashMap<String, LocalExecutor>();
        hashMap.put(MATCH_MANAGE,new LocalExecutor(MATCH_MANAGE,50,500));
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new ExecutorMonitor(hashMap),10,60,TimeUnit.SECONDS);
    }

    public static ExecutorService getExecutorService(String poolName){
        if (hashMap == null){
            init();
        }
        if (hashMap !=null && hashMap.containsKey(poolName)){
            return hashMap.get(poolName).getExecutorService();
        }
        return null;
    }
}

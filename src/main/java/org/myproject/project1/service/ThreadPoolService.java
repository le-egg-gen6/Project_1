package org.myproject.project1.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author nguyenle
 * @since 12:15 AM Fri 12/6/2024
 */
@Service
public class ThreadPoolService {

    private final int CORE_POOL_SIZE = 5;

    private final int MAXIMUM_POOL_SIZE = 10;

    private final int KEEP_ALIVE_TIME_IN_MINUTE = 10;

    private ExecutorService executor = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE,
            KEEP_ALIVE_TIME_IN_MINUTE,
            TimeUnit.MINUTES,
            new LinkedBlockingDeque<>()
    );

    public void submitAsynchronousTask(Runnable task) {
        executor.execute(task);
    }
}

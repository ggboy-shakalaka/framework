package com.ggboy.framework.utils.common;

import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BooleanSupplier;

public class StressTestUtil {

    public static Manager createManager(int threads) {
        return new Manager(threads);
    }

    public static class Manager {
        private final ExecutorService service;
        private final Panel panel;

        public Manager(int threads) {
            service = Executors.newFixedThreadPool(threads);
            panel = new Panel();
        }

        public void execute(BooleanSupplier supplier) {
            service.execute(() -> {
                boolean result = false;
                long time = System.currentTimeMillis();

                try {
                    result = supplier.getAsBoolean();
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }

                panel.increment(result, System.currentTimeMillis() - time);
            });
        }

        public void show() {
            long a = (System.currentTimeMillis() - panel.getBeginTime().get()) / 1000;
            long b = panel.getOkCount().get();
            long c = panel.getErrorCount().get();
            long d = panel.getMinTime().get();
            long e = panel.getMaxTime().get();
            long f = panel.getCountTime().get() / (b + c);
            long g = (b + c) / a;
            String show = String.format("时间: %s, 成功: %s, 失败: %s, 最小耗时: %s, 最大耗时: %s, 平均耗时: %s, TPS: %s",
                    a, b, c, d, e, f, g);
            System.out.println(show);
        }

        @Getter
        public class Panel {
            private AtomicLong beginTime = new AtomicLong();
            private AtomicLong countTime = new AtomicLong();
            private AtomicLong maxTime = new AtomicLong(Long.MIN_VALUE);
            private AtomicLong minTime = new AtomicLong(Long.MAX_VALUE);

            private AtomicLong okCount = new AtomicLong();
            private AtomicLong errorCount = new AtomicLong();

            public Panel() {
                beginTime.set(System.currentTimeMillis());
            }

            public void increment(boolean success, long time) {
                long ignore = success ? okCount.incrementAndGet() : errorCount.incrementAndGet();
                countTime.addAndGet(time);
                minTime.accumulateAndGet(time, Math::min);
                maxTime.accumulateAndGet(time, Math::max);
            }
        }
    }
}
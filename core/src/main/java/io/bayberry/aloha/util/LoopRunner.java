package io.bayberry.aloha.util;

import java.util.function.Consumer;

public class LoopRunner {

    public void run(Runnable runnable, Consumer<Exception> consumer) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                runnable.run();
            } catch (Exception e) {
                consumer.accept(e);
            }
        }
    }
}

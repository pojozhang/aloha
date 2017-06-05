package io.bayberry.aloha;

public abstract class LifeCycle {

    private Status status = Status.NEW;

    protected abstract void onCreate();

    protected abstract void onStart();

    protected abstract void onDestroy();

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    enum Status {
        NEW(0),
        READY(20),
        RUNNING(80),
        STOPPED(100);

        private final int progress;

        Status(int progress) {
            this.progress = progress;
        }

        public int progress() {
            return progress;
        }
    }
}

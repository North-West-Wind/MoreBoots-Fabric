package ml.northwestwind.moreboots.events;

import ml.northwestwind.moreboots.events.annotations.Cancellable;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class Event {
    private boolean cancelled;
    private Result result = Result.DEFAULT;

    @Retention(value = RUNTIME)
    @Target(value = TYPE)
    public @interface HasResult{}

    public enum Result
    {
        DENY,
        DEFAULT,
        ALLOW
    }

    public void setCancelled(boolean cancelled) {
        if (!getClass().isAnnotationPresent(Cancellable.class)) throw new UnsupportedOperationException("Attempting to call Event#setCancelled in non-cancellable events");
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setResult(Result result) {
        if (!getClass().isAnnotationPresent(HasResult.class)) throw new UnsupportedOperationException("Attempting to call Event#setResult in events without result");
        this.result = result;
    }

    public Result getResult() {
        return result;
    }
}

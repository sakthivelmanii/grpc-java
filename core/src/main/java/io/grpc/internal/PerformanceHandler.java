package io.grpc.internal;

import com.google.common.base.Stopwatch;
import java.util.concurrent.TimeUnit;

public class PerformanceHandler {

  public static PerformanceHandler BEFORE_SEND_PAYLOAD = new PerformanceHandler();
  public static PerformanceHandler BEFORE_REQUEST_DATA = new PerformanceHandler();

  private Stopwatch stopwatch;

  private PerformanceHandler() {
     stopwatch = Stopwatch.createUnstarted();
  }

  public void start() {
    stopwatch.start();
  }

  public void stop() {
    if (stopwatch.isRunning()) {
      stopwatch.stop();
    }
  }

  public void reset() {
    stopwatch.reset();
  }

  public long elapsed(TimeUnit timeUnit) {
    return stopwatch.elapsed(timeUnit);
  }
}

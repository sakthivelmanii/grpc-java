package io.grpc.internal;

import com.google.common.base.Stopwatch;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class PerformanceHandler {

  public static final Set<String> ALLOWED_METHODS = new HashSet<>();
  static {
    ALLOWED_METHODS.add("google.spanner.v1.Spanner/ExecuteStreamingSql");
    ALLOWED_METHODS.add("google.spanner.v1.Spanner/StreamingRead");
    ALLOWED_METHODS.add("google.spanner.v1.Spanner/BidiRPC");
  }

  // Request overheads
  public static PerformanceHandler OVERALL_REQUEST_OVERHEAD = new PerformanceHandler();
  public static PerformanceHandler CLIENT_REQUEST_OVERHEAD = new PerformanceHandler();
  public static PerformanceHandler AFTER_GRPC_CLIENT_OVERHEAD = new PerformanceHandler();
  public static PerformanceHandler GRPC_REQUEST_OVERHEAD = new PerformanceHandler();

  // Response overheads
  public static PerformanceHandler OVERALL_RESPONSE_OVERHEAD = new PerformanceHandler();
  public static PerformanceHandler CLIENT_RESPONSE_OVERHEAD = new PerformanceHandler();
  public static PerformanceHandler GRPC_RESPONSE_OVERHEAD = new PerformanceHandler();

  // Capture Request Interceptor
  private static long requestInterceptorLatency = 0;

  // Capture Response Interceptor
  private static long responseInterceptorLatency = 0;

  private final Stopwatch stopwatch;

  private PerformanceHandler() {
     stopwatch = Stopwatch.createUnstarted();
  }

  public void start() {
    if (!stopwatch.isRunning()) {
      stopwatch.start();
    }
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

  public static void resetAll() {
    OVERALL_REQUEST_OVERHEAD.reset();
    AFTER_GRPC_CLIENT_OVERHEAD.reset();
    CLIENT_REQUEST_OVERHEAD.reset();
    GRPC_REQUEST_OVERHEAD.reset();

    OVERALL_RESPONSE_OVERHEAD.reset();
    CLIENT_RESPONSE_OVERHEAD.reset();
    GRPC_RESPONSE_OVERHEAD.reset();

    requestInterceptorLatency = 0;
    responseInterceptorLatency = 0;
  }

  public static long getRequestInterceptorLatency() {
    return requestInterceptorLatency;
  }

  public static void recordRequestInterceptorLatency(long latency) {
    requestInterceptorLatency += latency;
  }

  public static long getResponseInterceptorLatency() {
    return responseInterceptorLatency;
  }

  public static void recordResponseInterceptorLatency(long latency) {
    responseInterceptorLatency += latency;
  }
}

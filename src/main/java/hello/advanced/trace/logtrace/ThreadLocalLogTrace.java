package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalLogTrace implements LogTrace{
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    // TraceId를 동기화하기 위함, 동시성 이슈 발생
    private final ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message){
        syncTraceId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceIdHolder.get().getId(), addSpace(START_PREFIX, traceIdHolder.get().getLevel()), message);
        return new TraceStatus(traceIdHolder.get(), startTimeMs, message);
    }

    private void syncTraceId() {
        if(traceIdHolder.get() == null) {
            traceIdHolder.set(new TraceId());
        } else {
            traceIdHolder.set(traceIdHolder.get().createNextId());
        }
    }

    @Override
    public void end(TraceStatus status){
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e){
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e){
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();

        if(e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(),
                    addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(),
                    addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }

        releaseTraceId();
    }

    private void releaseTraceId() {
        if(traceIdHolder.get().isFirstLevel()) {
            traceIdHolder.remove(); // destroy
        } else {
            traceIdHolder.set(traceIdHolder.get().createPreviousId());
        }
    }

    private static String addSpace(String prefix, int level){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1)  ? "|" + prefix : "|  ");
        }

        return sb.toString();
    }
}

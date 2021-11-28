package hello.advanced.app.v3;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
public class OrderControllerV3 {
    private final OrderServiceV3 orderServiceV3;
    private final LogTrace trace;

    @GetMapping("/v3/request")
    // 파라미터로 스트링 받으면, 자동으로 쿼리스트링으로 들어온 'itemId'를 해석해서 넣어줌
    public String request(String itemId) {
        TraceStatus status = null;

        try {
            status = trace.begin("OrderControllerV2.request()");

            orderServiceV3.orderItem(itemId);

            trace.end(status);
            return "ok";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}

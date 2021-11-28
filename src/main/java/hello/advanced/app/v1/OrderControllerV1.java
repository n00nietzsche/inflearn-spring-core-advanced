package hello.advanced.app.v1;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
public class OrderControllerV1 {
    private final OrderServiceV1 orderServiceV1;
    private final HelloTraceV1 trace;

    @GetMapping("/v1/request")
    // 파라미터로 스트링 받으면, 자동으로 쿼리스트링으로 들어온 'itemId'를 해석해서 넣어줌
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderControllerV1.request()");

            orderServiceV1.orderItem(itemId);

            trace.end(status);
            return "ok";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}

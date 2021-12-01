package hello.advanced.app.v4;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
public class OrderControllerV4 {
    private final OrderServiceV4 orderServiceV4;
    private final LogTrace trace;

    @GetMapping("/v4/request")
    // 파라미터로 스트링 받으면, 자동으로 쿼리스트링으로 들어온 'itemId'를 해석해서 넣어줌
    public String request(String itemId) {
        AbstractTemplate<String> template = new AbstractTemplate<>(trace) {
            @Override
            protected String call() {
                orderServiceV4.orderItem(itemId);
                return "ok";
            }
        };

        return template.execute("OrderControllerV4.request()");
    }
}

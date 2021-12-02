package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
public class OrderControllerV5 {
    private final OrderServiceV5 orderServiceV5;
    private final TraceTemplate traceTemplate;

    @GetMapping("/v5/request")
    // 파라미터로 스트링 받으면, 자동으로 쿼리스트링으로 들어온 'itemId'를 해석해서 넣어줌
    public String request(String itemId) {
        return traceTemplate.execute("OrderController.request()", () -> {
            orderServiceV5.orderItem(itemId);
            return "ok";
        });
    }
}

package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldService {
    private String nameStore;

    /**
     * 클래스 멤버인 nameStore 에 메서드 인자로 들어온 name 을 저장
     * 1초 sleep 후 출력
     */
    public String logic(String name) {
        log.info("저장 name={} -> nameStore={}", name, nameStore);
        nameStore = name;
        sleep(1000);
        log.info("조회 nameStore={}", nameStore);
        return nameStore;
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

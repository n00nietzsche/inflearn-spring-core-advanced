package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ThreadLocalServiceTest {
    private ThreadLocalService fieldService = new ThreadLocalService();

    @Test
    void field() {
        log.info("main start");

        // 인터페이스 구현 방식으로 스레드의 run 메소드를 구현
        Runnable userA = () -> {
            fieldService.logic("userA");
        };

        Runnable userB = () -> {
            fieldService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");

        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");
        // run과 start의 차이는 동기로 실행하는가 비동기로 실행하는가의 차이가 있다.
        // run은 Runnable 인터페이스에서 구현한 run 메소드를 그대로 실행한다.
        // start는 Thread 객체를 Runnable 한 상태로 만들어 스레드 실행 대기 큐에 Runnable 하게 올려놓는다.
        // start는 로직을 실행한다기보다 스레드를 그냥 Runnable 한 상태로 만들 뿐이다.
        threadA.start();
//        sleep(2000); // 동시성 문제가 발생하지 않는 코드
        // sleep(100); // 동시성 문제가 발생하는 코드, 조회하기 전에 저장된 값을 바꿔버린다.
        threadB.start();
//        sleep(3000); // 메인 스레드 종료 대기
        // 사실 대기할 때는 countdown latch 등 더 좋은 방법이 많다.
        log.info("main exit");
    }

    private void sleep(int millis) {
        try{
            Thread.sleep(millis);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}

package hello.advanced.trace;

import java.util.UUID;

/**
 * 로그의 아이디와 깊이를 나타내기 위한 클래스이다.
 */
public class TraceId {
    private String id;
    private int level;

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * 로그의 깊이를 표현하기 위한 메소드
     * id는 같고 레벨은 하나씩 증가한다.
     * @return  더 깊은 TraceId
     */
    public TraceId createNextId() {
        return new TraceId(id, level+1);
    }

    /**
     * 로그의 깊이를 표현하기 위한 메소드
     * id는 같고 레벨이 다시 감소한다.
     * @return  더 얕은 TraceId
     */
    public TraceId createPreviousId() {
        return new TraceId(id, level-1);
    }

    public boolean isFirstLevel() {
        return level == 0;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }
}

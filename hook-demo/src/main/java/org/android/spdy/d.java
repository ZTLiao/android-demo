package org.android.spdy;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 状态机示例，对应原始 d 类的功能优化版
 */
public class d implements d$a {

    private d$a callback;   // 原 this.a
    private final AtomicLong state;   // 原 this.b
    private d$a target;     // 原 this.c，明确为回调类型

    public d(d$a target) {
        this.state = new AtomicLong(1);
        this.target = target;
    }

    /**
     * 对应原 a() 方法：当状态为 3 时，将其增加 16 并返回 true
     */
    public boolean a() {
        while (true) {
            long cur = state.get();
            if (cur != 3) {
                return false;
            }
            long next = cur + 16;
            if (state.compareAndSet(cur, next)) {
                return true;
            }
        }
    }

    /**
     * 对应原 b() 方法：状态减16，若减完后状态为2且成功改为3则触发回调
     */
    public void b() {
        state.addAndGet(-16);
        if (state.compareAndSet(2, 3)) {
            triggerCallback();
        }
    }

    /**
     * 对应原 c() 方法：状态加1，若加完后状态为2且成功改为3则触发回调
     */
    public void c() {
        state.incrementAndGet();
        if (state.compareAndSet(2, 3)) {
            triggerCallback();
        }
    }

    private void triggerCallback() {
        // 原代码 (d)this.a，这里安全地转换（前提是 callback 必须是 StateMachine 类型）
        if (callback instanceof d) {
            d sm = (d) callback;
            sm.a(target);
        }
        target = null;
    }

    @Override
    public void a(d$a cb) {
        this.callback = cb;
    }
}
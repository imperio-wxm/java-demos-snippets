package com.wxmimperio.spring.handler;

import com.wxmimperio.spring.context.PipelineContext;

public interface ContextHandler<T extends PipelineContext> {
    /**
     * 处理输入的上下文数据
     *
     * @param context 处理时的上下文数据
     * @return 返回 true 则表示由下一个 ContextHandler 继续处理，返回 false 则表示处理结束
     */
    boolean handle(T context);
}

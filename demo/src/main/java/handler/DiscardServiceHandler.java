package handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

/**
 * discare服务Handler
 * 处理服务器通道
 *
 *
 * @author: czd
 * @create: 2018/6/21 9:38
 */
@ChannelHandler.Sharable
public class DiscardServiceHandler extends ChannelInboundHandlerAdapter {
    /**
     * 如何处理接收的数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //静默方式丢弃接收的数据
//        ((ByteBuf)msg).release();
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server receiverd: "+in.toString(CharsetUtil.US_ASCII));
        ctx.write(in);
    }

    /**
     * 通知处理器最后的 channelread() 是当前批处理中的最后一条消息时调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
        .addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 发送并捕获异常处理机制
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /* 打印异常并关闭连接*/
        cause.printStackTrace();
        ctx.close();
    }
}

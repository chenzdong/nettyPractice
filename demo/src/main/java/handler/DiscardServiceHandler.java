package handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

/**
 * discare����Handler
 * ���������ͨ��
 *
 *
 * @author: czd
 * @create: 2018/6/21 9:38
 */
@ChannelHandler.Sharable
public class DiscardServiceHandler extends ChannelInboundHandlerAdapter {
    /**
     * ��δ�����յ�����
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //��Ĭ��ʽ�������յ�����
//        ((ByteBuf)msg).release();
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server receiverd: "+in.toString(CharsetUtil.US_ASCII));
        ctx.write(in);
    }

    /**
     * ֪ͨ���������� channelread() �ǵ�ǰ�������е����һ����Ϣʱ����
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
        .addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * ���Ͳ������쳣�������
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /* ��ӡ�쳣���ر�����*/
        cause.printStackTrace();
        ctx.close();
    }
}

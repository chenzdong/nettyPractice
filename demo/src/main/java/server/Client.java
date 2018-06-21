package server;

import handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;


/**
 * 客户端
 *
 * @author: czd
 * @create: 2018/6/21 14:00
 */
public class Client {
    private final String host;
    private final int port;

    public Client(String host,int port) {
        this.host=host;
        this.port = port;
    }

    public void run() throws Exception{
        EventLoopGroup group=new NioEventLoopGroup();
        try{
            Bootstrap b=new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ClientHandler());
                        }
                    });
            ChannelFuture f=b.connect().sync();
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception{
//        if(args.length !=2 ){
//            System.out.println("error");
//            return;
//        }
//        final  String host=args[0];
//        final  int port=Integer.parseInt(args[1]);
        new Client("localhost",8080).run();
    }
}

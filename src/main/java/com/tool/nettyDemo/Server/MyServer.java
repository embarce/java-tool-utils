package com.tool.nettyDemo.Server;

import com.tool.nettyDemo.Handler.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Embrace
 * git: https://gitee.com/EmbraceQAQ
 * @version 1.0
 * @since JDK 1.8
 * Date: 2022/5/23 16:56
 * Description:
 * FileName: MyServer
 */
public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 两个线程组
         */
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();


        ServerBootstrap bootstrap=new ServerBootstrap();
        /**
         * 设置两个线程组boosGroup和workerGroup
         */
        bootstrap.group(bossGroup,workerGroup)
                /**
                 * 设置服务端通道实现类型
                 */
                .channel(NioServerSocketChannel.class)
                /**
                 * 设置线程队列得到连接个数
                 */
                .option(ChannelOption.SO_BACKLOG,128)
                /**
                 * 设置保持活动连接状态
                 */
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                /**
                 * 使用匿名内部类的形式初始化通道对象
                 */
                .childHandler(new ChannelInitializer<SocketChannel>(){

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //给pipeline管道设置处理器
                        socketChannel.pipeline().addLast(new MyServerHandler());
                    }
                });
        //给workerGroup的EventLoop对应的管道设置处理器
        System.out.println("服务端已经准备就绪...");
        //绑定端口号，启动服务端
        try {
            ChannelFuture channelFuture = bootstrap.bind(6666).sync();
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

package com.java.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.SctpChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ServerApp {

    public static void main(String[] args) {
        //Создаем 2 пула потоков
        //bossGroup отвечает за подключающихся клиентов
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //workerGroup для работы
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //Показываем какие пулы потоков использовать серверу
            //Для входящих bossGroup, для работы workerGroup
            serverBootstrap.group(bossGroup, workerGroup)
                    //для подключения клиентов используем канал NioServerSocketChannel
                    .channel(NioServerSocketChannel.class)
                    //Настройка процесса общения
                    //При подключении к серверу информация соеденения с данным клиентом лежит в SocketChannel
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //Инициализация происходит в методе initChannel
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //для каждого клиента конвеер свой
                            socketChannel.pipeline().addLast(
                                    new ServerHandler()
                            );

                        }
                    });
            //serverBootstrap.bind(8189) показывает что сервер должен стартануть на порте 8189
            //.sync() запускаем эту задачу (старт сервера)
            //ChannelFuture - информация о выполняемой задачи, нужен для того чтобы понимать, что происходит с сервером
            ChannelFuture channelFuture = serverBootstrap.bind(8189).sync();
            //.closeFuture().sync() ждем когда остановят сервер
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            //После остановки сервера закрываем пулы потоков
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

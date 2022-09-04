package com.java.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;

public class Network {

    private SocketChannel channel;


    public Network(){
        new Thread(() ->{
            EventLoopGroup workGroup = new NioEventLoopGroup();
            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(workGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    channel = socketChannel;
                            }
                        });


                ChannelFuture channelFuture = bootstrap.connect("localhost", 8189).sync();
                //Channel channel = channelFuture.channel();
                ByteBuf buffer = channel.alloc().buffer();
                buffer.writeBytes("Server hallo".getBytes(StandardCharsets.UTF_8));
                channel.writeAndFlush(buffer);
                channelFuture.channel().closeFuture().sync();

            }catch (Exception e){
                e.printStackTrace();
            } finally {
                workGroup.shutdownGracefully();
            }



        }).start();

    }

}

package com.java.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


//Inbound означает, что работаем на вход
public class ServerHandler extends ChannelInboundHandlerAdapter {


    //срабатывает при подключенмм клиента
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Клиент подключился");
    }

    //срабатываем когда клиент прислал сообщение и мы можем его обработать
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //msg является байтбуфером так как channelRead стоит первый в конвейере
        ByteBuf byteBuf = (ByteBuf)msg;
        //читаем весь буфер
        while (byteBuf.readableBytes() > 0){
            System.out.print((char)byteBuf.readByte());
        }
        //закрываем буфер так все вычитали из него
        byteBuf.release();
    }

    //exceptionCaught - Перехват исключения
    // если в процессе обработки посылки вылетает исключение, то узнаем мы об этом в этом методе
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //Распечатываем ошибку
        cause.printStackTrace();
        //закрываем соеденение с клиентом
        ctx.close();
    }
}

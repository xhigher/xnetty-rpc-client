package com.cheercent.xnetty.rpcclient.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.cheercent.xnetty.rpcclient.client.XClient.XResponseListener;
import com.cheercent.xnetty.rpcclient.message.MessageFactory;
import com.cheercent.xnetty.rpcclient.message.MessageFactory.MessageResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class XClientHandler extends SimpleChannelInboundHandler<JSONObject> {
    
	private static final Logger logger = LoggerFactory.getLogger(XClientHandler.class);

    private XResponseListener responseListener;
    
    public XClientHandler(XResponseListener listener) {
    	responseListener = listener; 
    }
    
    @Override
    public void channelRead0(ChannelHandlerContext ctx, JSONObject data) throws Exception {
    	logger.info("channelRead0: data = {}", data.toString());
    	MessageResponse response = MessageFactory.toMessageResponse(data);
    	if(response != null) {
            try {
            	if(!MessageFactory.isHeartBeatMessage(response.getRequestid())) {
            		responseListener.onResponse(response);
            	}
            } finally {
               
            }
    	}
    	
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(ctx.channel().remoteAddress().toString(), cause);
        //ctx.close();
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }



}

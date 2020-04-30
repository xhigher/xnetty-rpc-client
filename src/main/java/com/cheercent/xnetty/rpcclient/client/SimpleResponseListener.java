package com.cheercent.xnetty.rpcclient.client;

import com.alibaba.fastjson.JSONObject;
import com.cheercent.xnetty.rpcclient.client.XClient.XResponseListener;
import com.cheercent.xnetty.rpcclient.message.MessageFactory;
import com.cheercent.xnetty.rpcclient.message.MessageFactory.MessageResponse;
import com.cheercent.xnetty.rpcclient.util.CommonUtils;

/*
 * @copyright (c) xhigher 2015 
 * @author xhigher    2015-3-26 
 */
public class SimpleResponseListener implements XResponseListener {


	public void onRequest(MessageResponse request) {
    	if(request != null) {
    		String requestid = request.getRequestid();
    		if(!MessageFactory.isHeartBeatMessage(requestid)) {
    			MessageResponse response = MessageFactory.newMessageResponse(requestid);
    			JSONObject resultData = new JSONObject();
				resultData.put("name", CommonUtils.randomString(CommonUtils.randomInt(6, 16), false));
				resultData.put("age", CommonUtils.randomInt(8, 60));
				response.setData(resultData.toString());
        		return response;
        	}else {
        		return MessageFactory.newMessageResponse(requestid);
        	}
    	}
    	return null;
	}
	
}

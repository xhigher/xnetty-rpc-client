package com.cheercent.xnetty.rpcclient;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cheercent.xnetty.rpcclient.client.SimpleResponseListener;
import com.cheercent.xnetty.rpcclient.client.XClient;
import com.cheercent.xnetty.rpcclient.client.XClient.XResponseListener;
import com.cheercent.xnetty.rpcclient.message.MessageFactory;
import com.cheercent.xnetty.rpcclient.message.MessageFactory.MessageRequest;

public class XStarter {
	
	private static final Logger logger = LoggerFactory.getLogger(XStarter.class);
	
	private static String configFile = "/application.properties";

	private static final ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(1);
	
	public static void main(String[] args) {
		try{
			Properties properties = new Properties();
			InputStream is = Object.class.getResourceAsStream(configFile);
			properties.load(is);
			if (is != null) {
				is.close();
			}
			
			XResponseListener responseListener = new SimpleResponseListener();
			
			final XClient client = new XClient(properties, responseListener);
			
	        scheduledService.scheduleAtFixedRate(new Runnable() {

				public void run() {
					String requestid = String.valueOf(System.currentTimeMillis());
					logger.info("scheduleAtFixedRate send message: requestid="+requestid);
					MessageRequest request = MessageFactory.newMessageRequest(requestid);
					request.setModule("user");
					request.setAction("info");
					client.sendMessage(request);
				}
	        	
	        }, 10, 10, TimeUnit.SECONDS);
			
		}catch(Exception e){
			logger.error("Exception:", e);
		}
	}

}

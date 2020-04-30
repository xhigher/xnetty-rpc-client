# xnetty-rpc-client
基于netty构建的rpc分布式服务框架客户端

简介
---
  基于netty构建的rpc分布式服务框架客户端，通信协议采用json，心跳检测及重连机制；

服务端实现

  参看 xnetty-rpc-server
  
客户端实现

```java
      XClient client = new XClient(properties, new SimpleResponseListener());
      
      String requestid = String.valueOf(System.currentTimeMillis());
      MessageRequest request = MessageFactory.newMessageRequest(requestid);
      request.setModule("user");
      request.setAction("info");
      client.sendMessage(request);
```







package fun.fanlepian;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class SocketClient {

    public static WebSocketClient webSocketClient;
    public void LinkServer(){
        try {
            URI socketserver = new URI("ws://127.0.0.1:1020");
            webSocketClient = new WebSocketClient(socketserver) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println("FanCraft服务器正常开启，QQ机器人连接正常");
                    sendGroupMessage(891967365,"FanCraft服务器正常开启，QQ机器人连接正常");
                }

                @Override
                public void onMessage(String s) {
                    JSONObject data = JSON.parseObject(s);
                        String message = data.getString("message");
                        switch (message){
                            case "/help":
                                new API().sendhelp(webSocketClient);
                                break;
                            case "/memory":
                                new API().sendMemory(webSocketClient);
                                break;
                            case "/online":
                                new API().sendOnlinePlayer(webSocketClient);
                                break;
                            case "/status":
                                new API().sendStatus(webSocketClient);
                                break;
                            case "/version":
                                new API().sendVersion(webSocketClient);
                                break;
                            default:
                                break;

                        }
                    }


                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("FanCraft服务器关闭了，QQ机器人断开");
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                }

            };
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        webSocketClient.connect();
    }
    public static void sendGroupMessage(int groupId, String message) {
        String payload = String.format("{\"action\":\"send_group_msg\",\"params\":{\"group_id\":%d,\"message\":\"%s\"}}", groupId, message);
        webSocketClient.send(payload);

    }
}

package fun.fanlepian;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.java_websocket.client.WebSocketClient;

public class API {
    private int GroupID = 891967365;

    public void sendMemory(WebSocketClient w){
        Runtime runtime = Runtime.getRuntime();
        // 获取Java虚拟机总共分配的内存量（以字节为单位）
        long usedMemory = (runtime.totalMemory()-runtime.freeMemory())/1024/1024;
        long maxMemory = runtime.maxMemory()/1024/1024;
        String message = "服务器内存使用:"+usedMemory+"/"+maxMemory+"MB";
        SocketClient.sendGroupMessage(GroupID,message);
    }
    public void sendOnlinePlayer(WebSocketClient w){
        int onlineplayer = Bukkit.getServer().getOnlinePlayers().size();
        String message ="服务器现在有"+onlineplayer+"位玩家在线";
        SocketClient.sendGroupMessage(GroupID,message);
    }
    public void sendStatus(WebSocketClient w){
        double tps = Bukkit.getServer().getTPS()[0];
        double mspt = Bukkit.getAverageTickTime();
        String message ="服务器的TPS:"+String.format("%.2f", tps)+"\n服务器的MSPT:"+String.format("%.2f", mspt);
        for (Player p : Bukkit.getOnlinePlayers()){
            message = message + "\n" +p.getName();
        }
        SocketClient.sendGroupMessage(GroupID,message);
    }
    public void sendVersion(WebSocketClient w){
        String version= Bukkit.getVersion();
        String message ="服务器版本:"+version;
        SocketClient.sendGroupMessage(GroupID,message);
    }
    public void sendhelp(WebSocketClient w){
        String message = "欢迎使用FanCraft服务器QQ机器人\n下面是帮助菜单\n/help   获取菜单\n/online   获取在线玩家人数\n/status   获取服务器运行状态\n/memory  获取服务器使用内存\n/version 获取服务器的版本\n更多的功能正在开发，敬请期待";
        SocketClient.sendGroupMessage(GroupID,message);
    }
}

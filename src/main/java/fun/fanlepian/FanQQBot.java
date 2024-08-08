package fun.fanlepian;

import fun.fanlepian.Command.getLocation;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.java_websocket.client.WebSocketClient;

import java.io.File;

public final class FanQQBot extends JavaPlugin {
    private static FanQQBot instance;

    private WebSocketClient webSocketClient;
    public static File locationFile;
    public static YamlConfiguration location;

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginCommand("fanqqbot").setExecutor(new getLocation());
        new SocketClient().LinkServer();
        this.webSocketClient=SocketClient.webSocketClient;
        saveResource("location.yml",false);
        locationFile = new File(getDataFolder(),"location.yml");
        System.out.println(getDataFolder());
        System.out.println(locationFile);
        location = YamlConfiguration.loadConfiguration(locationFile);
    }

    @Override
    public void onDisable() {
        if (webSocketClient != null) {
            // 尝试关闭 WebSocket 连接
            try {

                SocketClient.sendGroupMessage(891967365,"FanCraft服务器关闭了，QQ机器人断开");
                webSocketClient.close();
            } catch (Exception e) {
                // 捕获关闭 WebSocket 时可能发生的异常
                e.printStackTrace();
            }
        }
    }
    public static FanQQBot getInstance(){
        return instance;
    }
}

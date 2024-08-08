package fun.fanlepian.Command;

import fun.fanlepian.FanQQBot;
import fun.fanlepian.SocketClient;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class getLocation implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;


            switch (strings[0]){
                case "add":
                    String name = strings[1];
                    boolean isnull = !FanQQBot.location.contains("location" + "." + name);
                    if (isnull){
                        FanQQBot.location.addDefault("location" + "." + name,player.getLocation());
                        player.sendMessage("§e已为您保存名为"+name+"的建筑坐标");
                        try {
                            FanQQBot.location.save(FanQQBot.locationFile);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return true;
                    }else{
                        player.sendMessage("§e您已经存在名为"+name+"的建筑，请重新命名或者删除原有的！");
                        try {
                            FanQQBot.location.save(FanQQBot.locationFile);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return true;
                    }
                case "del":
                    String delname = strings[1];
                    boolean isdelnull = !FanQQBot.location.contains("location" + "." + delname);
                    if(isdelnull){
                        player.sendMessage("§e您并没有名为"+delname+"的建筑！");
                        try {
                            FanQQBot.location.save(FanQQBot.locationFile);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return true;
                    }else {
                        FanQQBot.location.set("location" + "." + delname,null);
                        player.sendMessage("§e已为您删除名为"+delname+"的建筑坐标");
                        try {
                            FanQQBot.location.save(FanQQBot.locationFile);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return true;
                    }
                case "get":
                    String getname = strings[1];
                    boolean isgetnull = !FanQQBot.location.contains("location" + "." + getname);
                    if(isgetnull){
                        player.sendMessage("§e您并没有名为"+getname+"的建筑！");
                        try {
                            FanQQBot.location.save(FanQQBot.locationFile);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return true;
                    }else {
                        player.sendMessage("§e"+getname+"的坐标是"+FanQQBot.location.getLocation("location" + "." + getname));
                        try {
                            FanQQBot.location.save(FanQQBot.locationFile);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return true;
                    }

            }
        }

        return true;
    }
}

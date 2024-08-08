package fun.fanlepian.Command;

import fun.fanlepian.FanQQBot;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class getLocation implements CommandExecutor {
    private static void saveLocation(){
        try {
            FanQQBot.location.save(FanQQBot.locationFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            switch (strings[0]){
                case "add":
                    String name = strings[1];
                    boolean isnull = !FanQQBot.location.contains(player.getName()+"."+name);
                    if (isnull){
                        FanQQBot.location.set(player.getName()+"."+name,player.getLocation());
                        player.sendMessage("§e已为您保存名为"+name+"的建筑坐标");
                        break;
                    }else{
                        player.sendMessage("§e您已经存在名为"+name+"的建筑，请重新命名或者删除原有的！");
                        break;
                    }
                case "del":
                    String delname = strings[1];
                    boolean isdelnull = !FanQQBot.location.contains(player.getName()+"."+delname);
                    if(isdelnull){
                        player.sendMessage("§e您并没有名为"+delname+"的建筑！");
                        break;
                    }else {
                        FanQQBot.location.set(player.getName()+"."+delname,null);
                        player.sendMessage("§e已为您删除名为"+delname+"的建筑坐标");
                        break;
                    }
                case "get":
                    String getname = strings[1];
                    boolean isgetnull = !FanQQBot.location.contains(player.getName()+"."+getname);
                    if(isgetnull){
                        player.sendMessage("§e您并没有名为"+getname+"的建筑！");
                        break;
                    }else {
                        Location getLocation = FanQQBot.location.getLocation(player.getName()+"."+getname);
                        String message = "§e"+getname+"§e的坐标是世界"+getLocation.getWorld()+"的"+getLocation.getX()+" "+getLocation.getY()+" "+getLocation.getZ()+getLocation;
                        player.sendMessage(message);
                        break;
                    }

            }
            saveLocation();
            return true;
        }
        sender.sendMessage("§e非玩家并不能使用这个命令！");
        return true;
    }
}

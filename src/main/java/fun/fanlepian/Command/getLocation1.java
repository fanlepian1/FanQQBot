package fun.fanlepian.Command;

import fun.fanlepian.FanQQBot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class getLocation1 implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            switch (strings[0]){
                case "add":
                    String name = strings[1];
                    boolean isnull = !FanQQBot.getInstance().getConfig().contains("location."+player.getName() + "." + name);
                    if (isnull){
                        FanQQBot.getInstance().getConfig().addDefault("location."+player.getName() + "." + name,player.getLocation());
                        player.sendMessage("§e已为您保存名为"+name+"的建筑坐标");
                        FanQQBot.getInstance().saveConfig();
                        return true;
                    }else{
                        player.sendMessage("§e您已经存在名为"+name+"的建筑，请重新命名或者删除原有的！");
                        FanQQBot.getInstance().saveConfig();
                        return true;
                    }
                case "del":
                    String delname = strings[1];
                    boolean isdelnull = !FanQQBot.getInstance().getConfig().contains("location."+player.getName() + "." + delname);
                    if(isdelnull){
                        player.sendMessage("§e您并没有名为"+delname+"的建筑！");
                        FanQQBot.getInstance().saveConfig();
                        return true;
                    }else {
                        FanQQBot.getInstance().getConfig().set("location."+player.getName() + "." + delname,null);
                        player.sendMessage("§e已为您删除名为"+delname+"的建筑坐标");
                        FanQQBot.getInstance().saveConfig();
                        return true;
                    }
                case "get":
                    String getname = strings[1];
                    boolean isgetnull = !FanQQBot.getInstance().getConfig().contains("location."+player.getName() + "." + getname);
                    if(isgetnull){
                        player.sendMessage("§e您并没有名为"+getname+"的建筑！");
                        FanQQBot.getInstance().saveConfig();
                        return true;
                    }else {
                        player.sendMessage("§e"+getname+"的坐标是"+FanQQBot.getInstance().getConfig().getLocation("location."+player.getName() + "." + getname));
                        FanQQBot.getInstance().saveConfig();
                        return true;
                    }

            }
        }

        return true;
    }
}

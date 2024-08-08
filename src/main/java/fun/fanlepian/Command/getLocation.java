package fun.fanlepian.Command;

import fun.fanlepian.FanQQBot;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Set;

public class getLocation implements CommandExecutor {
    private static void saveLocation(){
        try {
            FanQQBot.location.save(FanQQBot.locationFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void reloadLocation(){
        try {
            FanQQBot.location.load(FanQQBot.locationFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addLocation(Location l,String n,String pn){
        FanQQBot.location.set(pn+"."+n+".World",l.getWorld().getName());
        FanQQBot.location.set(pn+"."+n+".X",l.getBlockX());
        FanQQBot.location.set(pn+"."+n+".Y",l.getBlockY());
        FanQQBot.location.set(pn+"."+n+".Z",l.getBlockZ());
    }
    public static String getLocation(String n,String pn){
        String world = FanQQBot.location.getString(pn + "." + n + ".World");
        int x = FanQQBot.location.getInt(pn+"."+n+".X");
        int y = FanQQBot.location.getInt(pn+"."+n+".Y");
        int z = FanQQBot.location.getInt(pn+"."+n+".Z");
        return "§e" + n + "§e的坐标是世界" + world + "的" + x + " " + y + " " + z;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.equals(null)){
            return true;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            switch (strings[0]){
                case "add":
                    String name = strings[1];
                    boolean isnull = !FanQQBot.location.contains(player.getName()+"."+name);
                    if (isnull){
                        addLocation(player.getLocation(),name,player.getName());
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
                    }else {
                        player.sendMessage(getLocation(getname,player.getName()));
                    }
                    return true;
                case "reload":
                        reloadLocation();
                case "cx":

                    if (!FanQQBot.location.contains("")) {
                        sender.sendMessage("§e没有玩家保存坐标");
                        return true;
                    }
                    if (strings.length == 1){
                        ConfigurationSection locationsSection = FanQQBot.location;
                        Set<String> keys = locationsSection.getKeys(false);
                        for (String key : keys) {
                            ConfigurationSection location = locationsSection.getConfigurationSection(key);
                            Set<String> keys2 = location.getKeys(false);
                            sender.sendMessage("§e玩家"+key);
                            for (String key2 : keys2){
                                String hoverText = key2+"\n世界: "+location.getConfigurationSection(key2).getString("World")+"\nX: "+location.getConfigurationSection(key2).getInt("X")+"\nY: "+location.getConfigurationSection(key2).getInt("Y")+"\nZ: "+location.getConfigurationSection(key2).getInt("Z");
                                TextComponent message = new TextComponent("- "+key2);
                                BaseComponent[] hoverComponents = new ComponentBuilder(hoverText).create();
                                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverComponents));
                                sender.sendMessage(message);
                            }
                        }
                        return true;
                    }
                    if (strings.length == 2){
                        if (!FanQQBot.location.contains(strings[1])){
                            sender.sendMessage("§e该玩家并没有保存任何坐标");
                            return true;
                        }
                        ConfigurationSection location = FanQQBot.location.getConfigurationSection(strings[1]);
                        sender.sendMessage("§e玩家"+strings[1]);
                        Set<String> keys2 = location.getKeys(false);
                        for (String key2:keys2){
                            String hoverText = key2+"\n世界: "+location.getConfigurationSection(key2).getString("World")+"\nX: "+location.getConfigurationSection(key2).getInt("X")+"\nY: "+location.getConfigurationSection(key2).getInt("Y")+"\nZ: "+location.getConfigurationSection(key2).getInt("Z");
                            TextComponent message = new TextComponent("- "+key2);
                            BaseComponent[] hoverComponents = new ComponentBuilder(hoverText).create();
                            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverComponents));
                            sender.sendMessage(message);
                        }
                        return true;
                    }

            }
            saveLocation();
            return true;
        }
        sender.sendMessage("§e非玩家并不能使用这个命令！");
        return true;
    }
}

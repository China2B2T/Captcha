package org.china2b2t.captchalib;

import org.bukkit.plugin.java.*;
import java.util.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.entity.Player;
/*
 *Merged From China2b2t's Developer lushangkan
 *Time:30/05/2020
 *Unix timestamp:1590845355
 *Author:MornSakura Rabbit0w0 Fqeke_ lushangkan
 */
public class Main extends JavaPlugin implements Listener
{
    private static Main instance;
    Random r;

    public Main() {
        this.r = new Random();
    }

    public void onEnable() {
        if (instance != null) {
            getLogger().warning("不要使用PlugMan重载这个插件，可能会产生问题");
            return;
        }

        instance = this;
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        System.out.println("[CaptchaLib] 已成功加载");
        if (!Bukkit.getMessenger().isOutgoingChannelRegistered(this, "BungeeCord")) {
            Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        }
    }

    @EventHandler
    public void onClick(final InventoryClickEvent e) {
        int i = 0;
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§a点我AwA")) {
            Player p =(Player)(e.getWhoClicked());
            this.getConfig().set(p.getName() + ".needed", (Object)null);
            this.saveConfig();
            BungeeUtils.connect((Player)e.getWhoClicked(),"2b2t");
            p.closeInventory();
        }
        else if (e.getInventory().getName().equals("§6§lChina2B2T Captcha")) {
            e.setCancelled(true);
            ++i;

            if(i>=3) {
                ((Player)e.getWhoClicked()).kickPlayer("§6您点击了错误的物品");
            }
        }
    }

    @EventHandler
    public void onClose(final InventoryCloseEvent e) {
        if (this.getConfig().getBoolean(e.getPlayer().getName() + ".needed")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, (Runnable)new Runnable() {
                @Override
                public void run() {
                    e.getPlayer().openInventory(e.getInventory());
                }
            }, 1L);
        }
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        if (this.getConfig().getBoolean(e.getPlayer().getName() + ".needed")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent e) {
        if (this.getConfig().getBoolean(e.getPlayer().getName() + ".needed")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent e) {
        if (this.getConfig().getBoolean(e.getPlayer().getName() + ".needed")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        e.getPlayer().sendMessage("§3--------------------------------------------------");
        e.getPlayer().sendMessage("§6\u6b22\u8fce\u56de\u6765 §c§lCHINA§f§l2B2T.ORG §6! \u73a9\u5bb6 §c" + e.getPlayer().getName());
        e.getPlayer().sendMessage("§6\u9996\u5148\uff0c\u6211\u4eec\u8981\u786e\u8ba4\u60a8\u662f\u4e0d\u662f\u673a\u5668\u4eba :) \u8bf7\u60a8\u5b8c\u6210\u9a8c\u8bc1\u7801\u5373\u53ef\u6e38\u73a9");
        e.getPlayer().sendMessage("§6\u8bf7\u52a0\u5165CHINA2B2T\u65f6\u4f7f\u7528\u6211\u4eec\u7684\u5b98\u65b9IP §cchina2b2t.org");
        e.getPlayer().sendMessage("§6\u6211\u4eec\u7684\u5b98\u65b9QQ\u7fa4\u804a\u4e3a §c684460070 §6\u8bf7\u8bb0\u4f4f\u4ed6");
        e.getPlayer().sendMessage("§6\u5982\u679c\u4f60\u9700\u8981\u66f4\u591a\u7684\u5e2e\u52a9\u53ef\u4ee5\u5728\u6e38\u620f\u4e2d\u8f93\u5165§c /help");
        e.getPlayer().sendMessage("§6\u8bf7\u4eab\u53d7\u4f60\u5728 §c§lCHINA§f§l2B2T.ORG §6\u5ea6\u8fc7\u7684\u65f6\u95f4\u5427");
        e.getPlayer().sendMessage("§3--------------------------------------------------");
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, (Runnable)new Runnable() {
            @Override
            public void run() {
                final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, "§6§lChina2B2T Captcha");
                final Integer i = Main.this.r.nextInt(54);
                for (int j = 0; j < 54; ++j) {
                    if (j == i) {
                        final ItemStack is = XMaterial.LIME_WOOL.parseItem();
                        final ItemMeta im = is.getItemMeta();
                        im.setDisplayName("§a点我AwA");
                        is.setItemMeta(im);
                        inv.setItem(j, is);
                    }
                    else {
                        final ItemStack iss = XMaterial.RED_WOOL.parseItem();
                        final ItemMeta imm = iss.getItemMeta();
                        imm.setDisplayName("§c不要点我QwQ");
                        iss.setItemMeta(imm);
                        inv.setItem(j, iss);
                    }
                }
                e.getPlayer().openInventory(inv);
                Main.this.getConfig().set(e.getPlayer().getName() + ".needed", (Object)true);
                Main.this.saveConfig();
            }
        }, 40L);
    }

    public static Main getInstance() {
        return instance;
    }
}

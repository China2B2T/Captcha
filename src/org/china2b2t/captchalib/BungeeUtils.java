package org.china2b2t.captchalib;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BungeeUtils{
	public static boolean connect(Player player, String server) {

		try {

			if (server.length() == 0) {
				player.sendMessage("§c目标主机为 \"\" (空字符串) 无法连接");
				return false;
			}

			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(byteArray);

			out.writeUTF("Connect");
			out.writeUTF(server); // 目标主机

			player.sendPluginMessage(Main.getInstance(), "BungeeCord", byteArray.toByteArray());

		} catch (Exception ex) {
			player.sendMessage(ChatColor.RED + "当尝试连接至目标主机时发生了错误");
			ex.printStackTrace();
			Main.getInstance().getLogger().warning("无法传送 \"" + player.getName() + "\" 至服务器 \"" + server + "\".");
			return false;
		}

		return true;
	}

}
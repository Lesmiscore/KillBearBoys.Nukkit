package com.nao20010128nao.KillBearBoys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.PluginBase;

public class KillBearBoys extends PluginBase implements Listener {
	String path;
	Map<String, logs> wands;

	public KillBearBoys() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void onEnable() {
		super.onEnable();
		new File(getDataFolder(), "plugins/KillBearBoys/data/").mkdirs();
		path = "plugins/KillBearBoys/data";
		getServer().getPluginManager().registerEvents(this, this);
		wands = new HashMap<>();
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if (wands.containsKey(event.getPlayer().getName()))
			wands.remove(event.getPlayer().getName());
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		String level = event.getPlayer().getLevel().getName();
		double x = event.getBlock().x;
		double y = event.getBlock().y;
		double z = event.getBlock().z;
		logs logs = new logs(x, y, z, level, path);
		if (!wands.containsKey(event.getPlayer().getName())) {
			Map<String, String> log = new HashMap<>();
			log.put("name", event.getPlayer().getName());
			log.put("action", "Break");
			log.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
			log.put("blockname", event.getBlock().getName());
			log.put("blockId", event.getBlock().getId() + "");
			log.put("meta", event.getBlock().getDamage() + "");
			logs.addLog(log);
		} else if (wands.get(event.getPlayer().getName()) != null
				&& wands.get(event.getPlayer().getName()) instanceof logs) {
			if (logs.equals(wands.get(event.getPlayer().getName())))
				event.getPlayer().sendMessage(wands.get(event.getPlayer().getName()).getLog());
			else {
				event.getPlayer().sendMessage(logs.getLog());
				wands.put(event.getPlayer().getName(), logs);
			}
			event.setCancelled(true);
		} else {
			event.getPlayer().sendMessage(logs.getLog());
			wands.put(event.getPlayer().getName(), logs);
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		String level = event.getPlayer().getLevel().getName();
		double x = event.getBlock().x;
		double y = event.getBlock().y;
		double z = event.getBlock().z;
		logs logs = new logs(x, y, z, level, path);
		if (!wands.containsKey(event.getPlayer().getName())) {
			Map<String, String> log = new HashMap<>();
			log.put("name", event.getPlayer().getName());
			log.put("action", "Place");
			log.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
			log.put("blockname", event.getBlock().getName());
			log.put("blockId", event.getBlock().getId() + "");
			log.put("meta", event.getBlock().getDamage() + "");
			logs.addLog(log);
		} else if (wands.get(event.getPlayer().getName()) != null
				&& wands.get(event.getPlayer().getName()) instanceof logs) {
			if (logs.equals(wands.get(event.getPlayer().getName())))
				event.getPlayer().sendMessage(wands.get(event.getPlayer().getName()).getLog());
			else {
				event.getPlayer().sendMessage(logs.getLog());
				wands.put(event.getPlayer().getName(), logs);
			}
			event.setCancelled(true);
		} else {
			event.getPlayer().sendMessage(logs.getLog());
			wands.put(event.getPlayer().getName(), logs);
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onTouth(PlayerInteractEvent event) {
		String level = event.getPlayer().getLevel().getName();
		double x = event.getBlock().x;
		double y = event.getBlock().y;
		double z = event.getBlock().z;
		logs logs = new logs(x, y, z, level, path);
		if (!wands.containsKey(event.getPlayer().getName())) {
			Map<String, String> log = new HashMap<>();
			log.put("name", event.getPlayer().getName());
			log.put("action", "Place");
			log.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
			log.put("blockname", event.getBlock().getName());
			log.put("blockId", event.getBlock().getId() + "");
			log.put("meta", event.getBlock().getDamage() + "");
			logs.addLog(log);
		} else if (wands.get(event.getPlayer().getName()) != null
				&& wands.get(event.getPlayer().getName()) instanceof logs) {
			if (logs.equals(wands.get(event.getPlayer().getName())))
				event.getPlayer().sendMessage(wands.get(event.getPlayer().getName()).getLog());
			else {
				event.getPlayer().sendMessage(logs.getLog());
				wands.put(event.getPlayer().getName(), logs);
			}
			event.setCancelled(true);
		} else {
			event.getPlayer().sendMessage(logs.getLog());
			wands.put(event.getPlayer().getName(), logs);
			event.setCancelled(true);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player)
			if (!wands.containsKey(sender.getName())) {
				wands.put(sender.getName(), null);
				sender.sendMessage("[KillBearBoys] Enable.");
			} else {
				wands.remove(sender.getName());
				sender.sendMessage("[KillBearBoys] Disable.");
			}
		else
			sender.sendMessage("[KillBearBoys] Please run this command in-game.");
		return true;
	}
}

class logs {
	public double x, y, z;
	public String level, path;
	public int now, num;

	public logs(double x, double y, double z, String level, String path) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.level = level;
		File pathFile = new File(path + "/" + level + "/" + x + "/" + y + "/" + z);
		pathFile.mkdirs();
		this.path = pathFile.getAbsolutePath();
		if (!new File(pathFile, "num.dat").exists())
			try {
				Files.write(new File(pathFile, "num.dat").toPath(), new byte[] { '0' });
			} catch (IOException e) {

			}
		getNum();
		now = 0;
	}

	public int getNum() {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(path, "num.dat"));
			return num = scanner.nextInt();
		} catch (FileNotFoundException e) {
			return -1;
		} finally {
			scanner.close();
		}
	}

	public String getLog() {
		StringBuilder sb = new StringBuilder();
		if (num < 1)
			sb.append("[Logs]There are no logs here.");
		else if (now < 1)
			now = num;
		else {
			Map<String, String> data = new HashMap<>();
			try {
				data = new Gson()
						.fromJson(new String(Files.readAllBytes(new File(path, now + ".dat").toPath())), HashMap.class);
			} catch (JsonSyntaxException | IOException e) {

			}
			sb.append("[Logs][").append(now).append('/').append(num).append("]\n");
			data.forEach((k, v) -> sb.append('[').append(k).append("][").append(v).append("]\n"));
			now--;
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof logs))
			return false;
		logs l = (logs) obj;
		return level.equals(l.level) &
				x == l.x &
				y == l.y &
				z == l.z;
	}

	public void addLog(Map<String, String> log) {
		String data = new Gson().toJson(log);
		num++;
		try {
			Files.write(new File(path, num + ".dat").toPath(), data.getBytes());
			Files.write(new File(path, "num.dat").toPath(), (num + "").getBytes());
		} catch (IOException e) {

		}
	}
}
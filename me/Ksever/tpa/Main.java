package me.Ksever.tpa;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	String err = "\247c\247lBlad!";
	String tag = "\2476\247lTPA \247e\247l≫ \247e";
	ArrayList<String> tpas = new ArrayList<String>();
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player) sender;
		if(command.getName().equals("tpa")) {
			if(args.length > 0) {
				Player p2 = Bukkit.getPlayerExact(args[0]);
				if(p2.isOnline()) {
					if(!(p2 == p)) {
						p.sendMessage(tag + "Wyslales prosbe o teleportacje do gracza \2476" + p2.getName() + "\247e.\nMa on 60 sekund na zaakceptowanie prosby o teleportacje!");
						p2.sendMessage(tag + "\2476" + p.getName() + "\247e chce sie do ciebie teleportowac!\nNapisz \2476/tpaccept " + p.getName() + "\247e, aby zaakceptowac!");
						tpas.add(p.getName() + ":" + p2.getName());
						Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
							@Override
							public void run() {
								if(tpas.contains(p.getName() + ":" + p2.getName())) {
									tpas.remove(p.getName() + ":" + p2.getName());
									p.sendMessage(tag + "Twoje zapytanie o teleportacje do gracza \2476" + p2.getName() + "\247e, wygaslo!");
								}
							}
						}, 1200L);
					} else {
						p.sendTitle(err, "\247cNie mozesz sie teleportowac do siebie!");
					}
				} else {
					p.sendTitle(err, "\247cTen gracz jest offline!");
				}
			} else {
				p.sendTitle(err, "\247cPodaj gracza!");
			}
		} if(command.getName().equals("tpaccept")) {
			if(args.length > 0) {
				Player p2 = Bukkit.getPlayerExact(args[0]);
				if(p2.isOnline()) {
					if(tpas.contains(p2.getName() + ":" + p.getName())) {
						tpas.remove(p2.getName() + ":" + p.getName());
						p2.sendMessage(tag + "\2476" + p.getName() + " \247ezaakceptowal twoja prosbe o teleportacje!");
						p.sendMessage(tag + "Zaakceptowales prosbe o teleportacje gracza \2476" + p2.getName());
						Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
							@Override
							public void run() {
								p2.teleport(p);
							}
						}, 100L);
					} else {
						p.sendTitle(err, "\247cNie masz prosby od tego gracza");
					}
				} else {
					p.sendTitle(err, "\247cTen gracz jest offline!");
				}
			} else {
				p.sendTitle(err, "\247cPodaj gracza!");
			}
		} if(command.getName().equals("tpdeny")) {
			if(args.length > 0) {
				Player p2 = Bukkit.getPlayerExact(args[0]);
				if(p2.isOnline()) {
					if(tpas.contains(p2.getName() + ":" + p.getName())) {
						tpas.remove(p2.getName() + ":" + p.getName());
						p2.sendMessage(tag + "\2476" + p.getName() + " \247eodrzucil twoja prosbe o teleportacje!");
						p.sendMessage(tag + "Odrzuciles prosbe o teleportacje gracza \2476" + p2.getName());
					}
				} else {
					p.sendTitle(err, "\247cTen gracz jest offline!");
				}
			} else {
				p.sendTitle(err, "\247cPodaj gracza!");
			}
		}
		return super.onCommand(sender, command, label, args);
	}

}

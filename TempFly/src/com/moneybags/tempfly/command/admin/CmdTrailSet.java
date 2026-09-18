package com.moneybags.tempfly.command.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.moneybags.tempfly.TempFly;
import com.moneybags.tempfly.aesthetic.particle.Particles;
import com.moneybags.tempfly.command.TempFlyCommand;
import com.moneybags.tempfly.util.Console;
import com.moneybags.tempfly.util.U;
import com.moneybags.tempfly.util.V;

public class CmdTrailSet extends TempFlyCommand {

	public CmdTrailSet(TempFly tempfly, String[] args) {
		super(tempfly, args);
	}
	
	// Invoked from command
	// /tf trail {player} {trail}
	// /tf trail {trail}
	@Override
	public void executeAs(CommandSender s) {
		String particle = args.length > 2 ? args[2].toUpperCase() :
			args.length > 1 ? args[1].toUpperCase() : null;
		if (particle == null) {
			U.m(s, "/tf trail {player} {trail}");
			return;
		}
		
		CommandSender target = args.length > 2 ? Bukkit.getPlayerExact(args[1]) : s;
		if (s.equals(target) && !(s instanceof Player)) {
			U.m(s, V.invalidSender);
			return;
		}
		if (target == null) {
			U.m(s, V.invalidPlayer.replaceAll("\\{PLAYER}", args[1]));
			return;
		}
		
		if (s.equals(target) && !s.hasPermission("tempfly.trails.set.self")) {
			U.m(target, V.invalidPermission);
			return;
		} else if (!s.equals(target) && !s.hasPermission("tempfly.trails.set.other")) {
			U.m(target, V.invalidPermission);
			return;
		}
		
		if (Particles.resolve(particle) == null) {
			U.m(s, V.invalidParticle.replaceAll("\\{PARTICLE}", particle));
			return;
		}
		Particles.setTrail(((Player)target).getUniqueId(), Particles.resolve(particle).name());
		U.m(target, V.trailSetSelf
				.replaceAll("\\{PARTICLE}", particle));
		if (s != target) {
			U.m(s, V.trailSetOther
					.replaceAll("\\{PLAYER}", target.getName())
					.replaceAll("\\{PARTICLE}", particle));
		}
		return;
	}

	@Override
	public List<String> getPotentialArguments(CommandSender s) {
		Console.debug(U.arrayToString(args, " - "));
		if (args.length <= 2) {
			if (U.hasPermission(s, "tempfly.trails.set.other")) {
				return getPlayerArguments(args.length > 1 ? args[1] : "");
			} else if (s instanceof Player && U.hasPermission(s, "tempfly.trails.set.self")) {
				return Arrays.asList(((Player)s).getName());
			}
		} else if (args.length <= 3) {
			List<String> particles = new ArrayList<>();
			for (Particle particle : Particle.values()) {
				if (Particles.isPlayable(particle)) particles.add(particle.name());
			}
			return particles;
		}
		
		return new ArrayList<>();
	}

}

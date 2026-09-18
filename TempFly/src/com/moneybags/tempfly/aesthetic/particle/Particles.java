package com.moneybags.tempfly.aesthetic.particle;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.data.BlockData;
import com.moneybags.tempfly.TempFly;
import com.moneybags.tempfly.user.FlightUser;
import com.moneybags.tempfly.util.Console;
import com.moneybags.tempfly.util.V;
import com.moneybags.tempfly.util.data.DataBridge.DataValue;
import com.moneybags.tempfly.util.data.DataPointer;

public class Particles {

	private static TempFly tempfly;

	public static void initialize(TempFly plugin) {
		tempfly = plugin;
	}

	public static Particle resolve(String name) {
		if (name == null || name.isEmpty()) return null;
		String normalized = name.toUpperCase(Locale.ROOT);
		if (normalized.equals("VILLAGER_HAPPY")) normalized = "HAPPY_VILLAGER";
		try {
			Particle particle = Particle.valueOf(normalized);
			return isPlayable(particle) ? particle : null;
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public static boolean isPlayable(Particle particle) {
		Class<?> type = particle.getDataType();
		return type == Void.class || type == DustOptions.class || type == BlockData.class
				|| type == ItemStack.class || type == Color.class || type == Float.class || type == Integer.class;
	}

	public static void play(Location loc, String s) {
		if (loc == null || loc.getWorld() == null) return;
		Particle particle = resolve(s);
		if (particle == null) particle = resolve(V.particleType);
		if (particle == null) particle = Particle.HAPPY_VILLAGER;
		Class<?> type = particle.getDataType();
		Object data = null;
		if (type == DustOptions.class) {
			ThreadLocalRandom random = ThreadLocalRandom.current();
			data = new DustOptions(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)), 2f);
		} else if (type == BlockData.class) {
			data = Material.STONE.createBlockData();
		} else if (type == ItemStack.class) {
			data = new ItemStack(Material.STONE);
		} else if (type == Color.class) {
			data = Color.WHITE;
		} else if (type == Float.class) {
			data = 0f;
		} else if (type == Integer.class) {
			data = 0;
		}
		if (data == null) loc.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0, 0.1);
		else loc.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0, 0.1, data);
	}

	public static String loadTrail(UUID u) {
		String particle = (String) tempfly.getDataBridge().getOrDefault(DataPointer.of(DataValue.PLAYER_TRAIL, u.toString()), null);
		if (V.debug) {Console.debug("", "------Loading particle trail------", "Player: " + u.toString(), "Value from data: " + String.valueOf(particle), "Default trail enabled: " + V.particleDefault, "Default trail is: " + V.particleType, "Returning trail: " +  (particle != null ? particle: (V.particleDefault ? V.particleType : "")), "------End particle trail------", "");}
		return particle != null ? particle: (V.particleDefault ? V.particleType : "");
	}

	/**
	 * Set a players particle trail.
	 * If particle is set to null or if the trail specified does not exist TempFly will attempt to use the default trail if enabled in the config.
	 * If it is set to an empty string however the particle will be disabled, IE no trail. This is what the remove trail command does.
	 * @param u the player
	 * @param particle the particle
	 */
	public static void setTrail(UUID u, String particle) {
		FlightUser user = tempfly.getFlightManager().getUser(Bukkit.getPlayer(u));
		if (user != null) {
			user.setTrail(particle);
			return;
		}
		tempfly.getDataBridge().stageChange(DataPointer.of(DataValue.PLAYER_TRAIL, u.toString()), particle);
	}

}

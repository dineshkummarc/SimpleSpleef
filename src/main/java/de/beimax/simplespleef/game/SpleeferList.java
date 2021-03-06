/**
 * This file is part of the SimpleSpleef bukkit plugin.
 * Copyright (C) 2011 Maximilian Kalus
 * See http://dev.bukkit.org/server-mods/simple-spleef/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/
package de.beimax.simplespleef.game;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Player;

import de.beimax.simplespleef.SimpleSpleef;

/**
 * @author mkalus
 *
 */
public class SpleeferList {
	/**
	 * Get a compiled list of players with commas and and at the end
	 * @param players
	 * @return
	 */
	public static String getPrintablePlayerList(LinkedList<Spleefer> players) {
		// build list of winners
		StringBuilder builder = new StringBuilder();
		int i = 0;
		String comma = SimpleSpleef.getPlugin().ll("feedback.infoComma");
		// compile list of spleefers
		for (Spleefer spleefer : players) {
			if (i > 0 && i == players.size() - 1) builder.append(SimpleSpleef.getPlugin().ll("feedback.infoAnd")); // last element with end
			else if (i > 0) builder.append(comma); // other elements with ,
			builder.append(spleefer.getPlayer().getDisplayName());
		}
		return builder.toString();
	}
	
	/**
	 *  list of spleefers currently spleefing
	 *  - Since we have to iterate the list most of the time anyway, we simply use a linked list
	 */
	private LinkedList<Spleefer> spleefers;

	/**
	 * Constructor
	 */
	public SpleeferList() {
		spleefers = new LinkedList<Spleefer>();
	}

	/**
	 * Get a spleefer from the list
	 * @param player
	 * @return spleefer or null
	 */
	public Spleefer getSpleefer(Player player) {
		for (Spleefer spleefer : spleefers) {
			if (spleefer.getPlayer() == player) return spleefer;
		}
		return null;
	}

	/**
	 * Add a spleefer to the list
	 * @param player
	 * @return boolean, true if successful
	 */
	public boolean addSpleefer(Player player) {
		if (hasSpleefer(player)) return false;
		spleefers.add(new Spleefer(player));
		return true;
	}
	
	/**
	 * Remove spleefer from list
	 * @param player
	 * @return boolean, true if successful
	 */
	public boolean removeSpleefer(Player player) {
		for (Spleefer spleefer : spleefers) {
			if (spleefer.getPlayer() == player) {
				spleefers.remove(spleefer);
				return true;
			}
		}
		return false;
	}

	/**
	 * checks if a player is in the list
	 * @param player
	 * @return
	 */
	public boolean hasSpleefer(Player player) {
		for (Spleefer spleefer : spleefers) {
			if (spleefer.getPlayer() == player) return true;
		}
		return false;
	}
	
	/**
	 * checks if a player has lost
	 * @param player
	 * @return
	 */
	public boolean hasLost(Player player) {
		for (Spleefer spleefer : spleefers) {
			if (spleefer.getPlayer() == player) return spleefer.hasLost();
		}
		return false;
	}

	/**
	 * set player to lost
	 * @param player
	 */
	public void setLost(Player player) {
		for (Spleefer spleefer : spleefers) {
			if (spleefer.getPlayer() == player) spleefer.setLost(true);
		}
	}
	
	/**
	 * counts the spleefers still in the game
	 * @return
	 */
	public int inGame() {
		int inGame = 0;
		for (Spleefer spleefer : spleefers) {
			if (!spleefer.hasLost()) inGame++;
		}
		return inGame;
	}
	
	/**
	 * counts the spleefers still in the game (team version)
	 * @param team
	 * @return
	 */
	public int inGame(int team) {
		int inGame = 0;
		for (Spleefer spleefer : spleefers) {
			if (spleefer.getTeam() == team && !spleefer.hasLost()) inGame++;
		}
		return inGame;
	}
	
	/**
	 * counts the size of the spleefers
	 * @return
	 */
	public int size() {
		return spleefers.size();
	}
	
	/**
	 * get the whole list
	 * @return
	 */
	public List<Spleefer> get() {
		return spleefers;
	}
	
	/**
	 * return a specific team list
	 * @param team
	 * @return
	 */
	public List<Spleefer> getTeam(int team) {
		LinkedList<Spleefer> compiledTeam = new LinkedList<Spleefer>();
		for (Spleefer spleefer : spleefers) {
			if (spleefer.getTeam() == team) compiledTeam.add(spleefer);
		}
		
		// empty teams return null
		return compiledTeam;
	}
	
	/**
	 * count unready spleefers
	 * @return
	 */
	public int countUnreadyPlayers() {
		int unready = 0;
		for (Spleefer spleefer : spleefers) {
			if (!spleefer.isReady()) unready++;
		}
		return unready;
	}
}

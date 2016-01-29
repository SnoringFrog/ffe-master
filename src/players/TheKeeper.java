package players;

import controller.Player;

public class TheKeeper extends Player {

	@Override
	public String getCmd() {
		return "lua-5.2 "+BOT_LOCATION+"TheKeeper.lua";
	}	
}

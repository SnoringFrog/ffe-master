package players;

import controller.Player;

public class XenoBot extends Player {

	@Override
	public String getCmd() {
		return "node "+BOT_LOCATION+"XenoBot.js";
	}	
}

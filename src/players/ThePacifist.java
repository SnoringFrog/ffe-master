package players;

import controller.Player;

public class ThePacifist extends Player {

	@Override
	public String getCmd() {
		return "node "+BOT_LOCATION+"ThePacifist.js";
	}	
}

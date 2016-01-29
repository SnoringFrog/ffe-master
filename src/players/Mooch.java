package players;

import controller.Player;

public class Mooch extends Player {

	@Override
	public String getCmd() {
		return "java "+BOT_LOCATION+"Mooch";
	}	
}

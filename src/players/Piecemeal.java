package players;

import controller.Player;

public class Piecemeal extends Player {

	@Override
	public String getCmd() {
		return "java "+BOT_LOCATION+"Piecemeal";
	}	
}

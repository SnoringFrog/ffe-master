package players;

import controller.Player;

public class Researcher extends Player {

	@Override
	public String getCmd() {
		return "java "+BOT_LOCATION+"Researcher";
	}	
}

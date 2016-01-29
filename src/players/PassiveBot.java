package players;

import controller.Player;

public class PassiveBot extends Player {

	@Override
	public String getCmd() {
		return "java "+BOT_LOCATION+"PassiveBot";
	}	
}

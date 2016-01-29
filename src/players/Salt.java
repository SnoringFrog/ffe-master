package players;

import controller.Player;

public class Salt extends Player {

	@Override
	public String getCmd() {
		return "kotlin "+BOT_LOCATION+"salt.SaltKt";
	}	
}

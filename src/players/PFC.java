package players;

import controller.Player;

public class PFC extends Player {

	@Override
	public String getCmd() {
		return "kotlin "+BOT_LOCATION+"pfc.PFCKt";
	}	
}

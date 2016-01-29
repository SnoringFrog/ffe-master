package players;

import controller.Player;

public class WICKED extends Player {

	@Override
	public String getCmd() {
		return "kotlin "+BOT_LOCATION+"wicked.WICKEDKt";
	}	
}

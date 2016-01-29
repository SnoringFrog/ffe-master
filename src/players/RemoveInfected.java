package players;

import controller.Player;

public class RemoveInfected extends Player {

	@Override
	public String getCmd() {
		return "python "+BOT_LOCATION+"RemoveInfected.py";
	}	
}

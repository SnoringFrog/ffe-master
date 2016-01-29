package players;

import controller.Player;

public class MadScienceBot extends Player {

	@Override
	public String getCmd() {
		return "python "+BOT_LOCATION+"MadScienceBot.py";
	}	
}

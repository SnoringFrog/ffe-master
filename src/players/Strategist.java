package players;

import controller.Player;

public class Strategist extends Player {

	@Override
	public String getCmd() {
		return "python3 "+BOT_LOCATION+"Strategist.py";
	}	
}

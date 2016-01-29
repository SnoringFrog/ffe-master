package players;

import controller.Player;

public class Crossroads extends Player {

	@Override
	public String getCmd() {
		return "python2 "+BOT_LOCATION+"Crossroads.py";
	}	
}

package players;

import controller.Player;

public class Smaug extends Player {

	@Override
	public String getCmd() {
		return "python2 "+BOT_LOCATION+"Smaug.py";
	}	
}

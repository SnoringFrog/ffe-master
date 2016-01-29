package players;

import controller.Player;

public class RedCross extends Player {

	@Override
	public String getCmd() {
		return "python2 "+BOT_LOCATION+"RedCross.py";
	}	
}

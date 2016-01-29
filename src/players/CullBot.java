package players;

import controller.Player;

public class CullBot extends Player {

	@Override
	public String getCmd() {
		return "python3 "+BOT_LOCATION+"CullBot.py";
	}	
}

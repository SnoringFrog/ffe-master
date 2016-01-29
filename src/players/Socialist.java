package players;

import controller.Player;

public class Socialist extends Player {

	@Override
	public String getCmd() {
		return "python3 "+BOT_LOCATION+"Socialist.py";
	}	
}

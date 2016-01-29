package players;

import controller.Player;

public class InfectedHaven extends Player {

	@Override
	public String getCmd() {
		return "python3 "+BOT_LOCATION+"InfectedHaven.py";
	}	
}

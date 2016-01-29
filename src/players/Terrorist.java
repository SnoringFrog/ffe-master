package players;

import controller.Player;

public class Terrorist extends Player {

	@Override
	public String getCmd() {
		return "kotlin "+BOT_LOCATION+"terrorist.TerroristKt";
	}	
}

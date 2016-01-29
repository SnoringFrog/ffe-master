package players;

import controller.Player;

public class Israel extends Player {

	@Override
	public String getCmd() {
		return "python "+BOT_LOCATION+"Isreal.py";
	}	
}

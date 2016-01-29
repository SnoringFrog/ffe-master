package players;

import controller.Player;

public class Medic extends Player {

	@Override
	public String getCmd() {
		return "python "+BOT_LOCATION+"Medic.py";
	}	
}

package players;

import controller.Player;

public class InfectedTown extends Player {

	@Override
	public String getCmd() {
		return "java "+BOT_LOCATION+"InfectedTown";
	}	
}

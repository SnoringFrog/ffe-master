package players;

import controller.Player;

public class UndecidedBot extends Player {

	@Override
	public String getCmd() {
		return "python3 "+BOT_LOCATION+"UndecidedBot.py";
	}	
}

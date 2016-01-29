package players;

import controller.Player;

public class FamilyValues extends Player {

	@Override
	public String getCmd() {
		return "node "+BOT_LOCATION+"FamilyValues.js";
	}	
}

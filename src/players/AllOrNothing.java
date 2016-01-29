package players;

import controller.Player;

public class AllOrNothing extends Player {

	@Override
	public String getCmd() {
		return "Rscript -e "+BOT_LOCATION+"AllOrNothing.R";
	}	
}

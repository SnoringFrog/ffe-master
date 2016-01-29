package players;

import controller.Player;

public class DisseminationBot extends Player {

	@Override
	public String getCmd() {
		return "ruby "+BOT_LOCATION+"DisseminationBot.rb";
	}	
}

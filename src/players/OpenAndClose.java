package players;

import controller.Player;

public class OpenAndClose extends Player {

	@Override
	public String getCmd() {
		return "ruby "+BOT_LOCATION+"OpenAndClose.rb";
	}	
}

package Entity.Command.Game;

import DevTSK.Entity.Entity;
import DevTSK.Entity.Commands.Command;
import DevTSK.Util.LoggerPro;

public class Game implements Command {

	@Override
	public String getHelp() {
		return "Play a game with the chars";
	}

	@Override
	public String getSyntax() {
		return "units [Your Char name] [Opposing Char name]";
	}

	@Override
	public String[] getTokens() {
		return new String[] { "Units" };
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public int run(Entity[] chars, LoggerPro p, String[] syntax) {
		if (syntax.length > 1) {
			//TODO
		} else {
			Ugame g = new Ugame(chars);
		}
		return 0;
	}

}

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
		return true;
	}

	@Override
	public int run(Entity[] chars, LoggerPro p, String[] syntax) {
		if (syntax.length == 3) {
			Entity player = null, enemy = null;
			boolean pl = false, e = false;
			p.log("Attempting to locate entity with name of " + syntax[1] + " to fight entity with name " + syntax[2]);
			for (int i = 0; i < chars.length; i++) {
				if (chars[i].getName().equalsIgnoreCase(syntax[1]) || chars[i].getAltName().equalsIgnoreCase(syntax[1])) {
					pl = true;
					player = chars[i];
				}
				if (chars[i].getName().equalsIgnoreCase(syntax[2]) || chars[i].getAltName().equalsIgnoreCase(syntax[2])) {
					e = true;
					enemy = chars[i];
				}
			}
			if (e && pl) {
				Ugame g = new Ugame(player, enemy);
				g.init();
			} else {
				p.log(2, "Could not find one or more specified entities! continuing with random entries.");
				Ugame g = new Ugame(chars);
				g.init();
			}

		}
		if (syntax.length == 1) {
			Ugame g = new Ugame(chars);
			g.init();
		}
		return 0;
	}

}

package Entity.Command.Breed;

import DevTSK.Entity.Entity;
import DevTSK.Entity.MasterControl;
import DevTSK.Util.LoggerPro;

public class Breed implements DevTSK.Entity.Commands.Command {
	private static final String[] modes = { "0", "1", "2" };

	public String getHelp() {
		return "Combines the given parents dna into one child string.";
	}

	public String getSyntax() {
		return "Breed <mode> <Father> <Mother> [times]";
	}

	public String[] getTokens() {
		return new String[] { "Breed" };
	}

	public boolean isHidden() {
		return false;
	}

	public int run(Entity[] list, LoggerPro logbook, String[] sl) {
		String say = "";
		Breeder b = new Breeder(0);
		Boolean isValidMode = Boolean.valueOf(true);
		for (int i = 0; i < modes.length; i++) {
			if (sl[1].equalsIgnoreCase(modes[i]))
				isValidMode = Boolean.valueOf(true);
		}
		if ((sl.length < 4) || (sl.length > 5)) {
			logbook.log(2, "Command " + sl[0] + " failed to complete successfully. Required args not met.");
		} else if (!isValidMode.booleanValue()) {
			say =

					"Invalid mode : " + sl[1] + "\n\nValid Modes are:\n" + "0 = Colour chosen will be from one of the parents.\n" + "1 = Colour chosen will be a mix of the RGB values of the parents.\n" + "2 = Colour will be chosen by chopping the parents colours and mixing the values.";
			logbook.log(2, "Command " + sl[0] + " failed to complete successfully. Invalid mode.");
		} else {
			int f = -1;
			int m = -1;
			for (int i = 0; i < list.length; i++) {
				if (sl[2].equalsIgnoreCase(list[i].getName())) {
					f = i;
				}
				if (sl[2].equalsIgnoreCase(list[i].getAltName())) {
					f = i;
				}
			}
			for (int i = 0; i < list.length; i++) {
				if (sl[3].equalsIgnoreCase(list[i].getName())) {
					m = i;
				}
				if (sl[3].equalsIgnoreCase(list[i].getAltName())) {
					m = i;
				}
			}
			if (sl[1].equals("0"))
				b = new Breeder(0);
			if (sl[1].equals("1"))
				b = new Breeder(1);
			if (sl[1].equals("2"))
				b = new Breeder(2);
			if ((f != -1) && (m != -1)) {
				say = b.breed(list[f], list[m]);
				if (sl.length == 5) {
					say = "";
					for (int i = 1; i < Integer.parseInt(sl[4]); i++) {
						if (sl[0].equalsIgnoreCase("breed")) {
							say = say + "\n" + b.breed(list[f], list[m]);
						}
					}
				}
			} else {
				logbook.log(2, "Command " + sl[0] + " failed to complete successfully. Could not find one or both parents.");
			}
		}
		if (say.contains("Error")) {
			logbook.log(2, "Command " + sl[0] + " failed to complete successfully. Missing DNA on one or both parents.");
		} else
			logbook.log("Command " + sl[0] + " completed successfully.");
		MasterControl.poni.printCl();
		MasterControl.poni.println(say);
		return 0;
	}
}

package Entity.Command.Game;

public class Data {

	public static String[] getDeathMessages() {
		return new String[] {
				"How can this be?",
				"Impossible...",
				"How careless of me...",
				"This cannot be!",
				"Defeated? how?",
				"I'll be back...",
				"...",
				"ha..."
		};
	}

	public static Xlass[] getDefaultClasses() {
		//**************************************************
		//*         Name   / BaseStats / stat rates        *
		//*         String / int[]     / double[]          *
		//**************************************************
		//*  HP / ATK / DEF / SKL / SPD / LUK / RES / MOV  *
		//**************************************************
		Xlass[] x = new Xlass[] {
				new Xlass("Great Knight", new int[] { 21, 10, 10, 6, 6, 3, 2, 0 }, new double[] {}),
				new Xlass("SwordMaster", new int[] { 18, 6, 5, 7, 11, 4, 5, 0 }, new double[] {}),
				new Xlass("General", new int[] { 22, 11, 12, 7, 3, 4, 3, 0 }, new double[] {}),
				new Xlass("Dark Knight", new int[] { 19, 8, 8, 6, 5, 3, 6, 0 }, new double[] {}),
				new Xlass("Falcon Knight", new int[] { 18, 5, 5, 6, 10, 5, 9, 0 }, new double[] {}),
				new Xlass("Master Ninja", new int[] { 17, 5, 4, 10, 11, 2, 8, 0 }, new double[] {}),
				new Xlass("Dread Fighter", new int[] { 19, 8, 6, 6, 8, 1, 9 }, new double[] {})

		};

		return x;
	}

	public static Weapon[] getDefaultWeapons() {
		//******************************************************************************************
		//* Name   / type / Range / Attack / crit chance / hit modifier /  magic  /  reaver *
		//* String / int  /  int  /   int  /     int     /    double    / boolean / boolean *
		//******************************************************************************************
		return new Weapon[] {
				new Weapon("Steel Lance", Weapon.SPEAR, 14, 0, .75, new int[] { -1 }, false, false),
				new Weapon("Silver Sword", Weapon.SWORD, 12, 0, 1, new int[] { -1 }, false, false),
				new Weapon("Steel Axe", Weapon.AX, 15, 0, .7, new int[] { -1 }, false, false),
				new Weapon("Ragnarok", Weapon.ANIMA, 11, 5, .85, new int[] { -1 }, true, false),
				new Weapon("Apocalypse", Weapon.DARK, 18, 5, .7, new int[] { -1 }, true, false),
				new Weapon("Aura", Weapon.LIGHT, 12, 5, .8, new int[] { -1 }, true, false),

		};
	}
}

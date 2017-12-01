package Entity.Command.Game;

public class Data {

	public static String[] getDeathMessages() {
		return new String[] {
				"How can this be?",
				"Impossible...",
				"How careless of me...",
				"This cannot be!",
				"Defeated? how?",
				"I'll be back..."
		};
	}

	public static Xlass[] getDefaultClasses() {
		//**************************************************
		//*Name   / BaseStats / Useable Weapons/ stat rates*
		//*String / int[]     / int[]          / double[]  *
		//**************************************************
		//*  HP / ATK / DEF / SKL / SPD / LUK / RES / MOV  *
		//**************************************************
		Xlass[] x = new Xlass[] {

		};

		return x;
	}

	public static Weapon[] getDefaultWeapons() {
		//******************************************************************************************
		//* Name   / type / Range / Attack / crit chance / hit modifier / eff  /  magic  /  reaver *
		//* String / int  /  int  /   int  /     int     /    double    / null / boolean / boolean *
		//******************************************************************************************
		return new Weapon[] {
		};
	}
}

package Entity.Command.Game;

public class Weapon {
	public final int atk, crit, type;
	public final double hit;
	public final int[] efns;
	public final String name;
	public static final int SWORD = 0,
			AX = 1,
			SPEAR = 2,
			BOW = 3,
			ANIMA = 4,
			DARK = 5,
			LIGHT = 6,
			ENERGY = 7,
			DIVINE = 8;

	public final boolean isMagic, isReaver;

	public Weapon(String name, int type, int atk, int crit, double hit, int[] effectiveness, boolean magic, boolean reaver) {
		this.name = name;
		this.type = type;
		this.atk = atk;
		this.crit = crit;
		this.hit = hit;
		efns = effectiveness;
		isMagic = magic;
		isReaver = reaver;
	}
}

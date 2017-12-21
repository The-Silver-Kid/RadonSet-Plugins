package Entity.Command.Game;

public class Xlass {
	private final int[] bSt;
	private final double[] sG;
	private final String name;

	public static final int HP = 0, ATTACK = 1, DEFENSE = 2, SKILL = 3, SPEED = 4, LUCK = 5, RESISTANCE = 6, MOVEMENT = 7, MAGIC = 8;

	public Xlass(String name, int[] baseStats, double[] statGains) {
		this.name = name;
		bSt = baseStats;
		sG = statGains;
	}

	public String getName() {
		return name;
	}

	public int getBaseStat(int stat) {
		return bSt[stat];
	}

	public double getGrowthRate(int stat) {
		return sG[stat];
	}

	public boolean canUseWeapon(Weapon w) {
		return true;
	}

}

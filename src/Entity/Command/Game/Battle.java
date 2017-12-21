package Entity.Command.Game;

import java.util.Random;

public class Battle {
	private Unit a, e;
	private int ac, ec;
	private boolean adead, edead;

	private static final int[][] EFFECT = new int[][] {
			new int[] { 0, 1, -1, 0, 0, 0, 0, -1, 0 }, //SWORD
			new int[] { -1, 0, 1, 0, 0, 0, 0, -1, 0 }, //AXE
			new int[] { 1, -1, 0, 0, 0, 0, 0, -1, 0 }, //SPEAR
			new int[] { 0, 0, 0, 0, 0, 0, 0, -1, 0 }, //BOW
			new int[] { 0, 0, 0, 0, 0, -1, 1, 1, 0 }, //ANIMA
			new int[] { 0, 0, 0, 0, 1, 0, -1, 1, 0 }, //DARK
			new int[] { 0, 0, 0, 0, -1, 1, 0, 1, 0 }, //LIGHT
			new int[] { 1, 1, 1, 1, -1, -1, -1, 0 }, //ENERGY
			new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 } //DIVINE
	};

	public Battle(Unit allies, Unit opponents) {
		a = allies;
		e = opponents;
		ac = a.getStat(Unit.HP);
		ec = e.getStat(Unit.HP);
	}

	public void init() {
		println("Your unit, " + a.getXlass().getName() + " " + a.getNom() + ", wields a " + a.getWeapon().name + ".");
		println("Enemy unit, " + e.getXlass().getName() + " " + e.getNom() + ", wields a " + e.getWeapon().name + ".");
		println(a.getNom() + "'s HP is " + a.getStat(Unit.HP) + " and the attack is " + (a.getStat(Unit.ATTACK) + a.getWeapon().atk));
		println(e.getNom() + "'s HP is " + e.getStat(Unit.HP) + " and the attack is " + (e.getStat(Unit.ATTACK) + e.getWeapon().atk));
	}

	public void doBattle(boolean allyAttack) {
		adead = false;
		edead = false;
		boolean ad = false, ed = false, aw = a.canUseWeapon(), ew = e.canUseWeapon(), eCrit = false, aCrit = false, oCrit = false;
		Random r = new Random();

		int ahit = 0, ehit = 0, aev = 0, eev = 0, aWep = a.getWeapon().type, eWep = e.getWeapon().type;

		int edmg = 0, admg = 0, odmg = 0;

		int as = a.getStat(Unit.SPEED), es = e.getStat(Unit.SPEED);
		if (as + 5 < es)
			ed = true;
		if (es + 5 < as)
			ad = true;
		//BONUS DAMAGE
		//ally
		if (ad)
			//can use weapon
			if (aw)
				odmg = allyDamage(a.getWeapon().isMagic);
			else
				odmg = 0;//(a.getStat(Unit.ATTACK)) - e.getStat(Unit.DEFENSE);
		//opponent
		if (ed)
			if (ew)
				odmg = enemyDamage(e.getWeapon().isMagic);
			else
				odmg = 0;//(e.getStat(Unit.ATTACK)) - a.getStat(Unit.DEFENSE);

		//Normal damage
		//enemy attack
		if (ew)
			edmg = enemyDamage(e.getWeapon().isMagic);
		else
			edmg = 0;//(e.getStat(Unit.ATTACK)) - a.getStat(Unit.DEFENSE);
		//ally attack
		if (aw)
			admg = allyDamage(a.getWeapon().isMagic);
		else
			admg = 0;//(a.getStat(Unit.ATTACK)) - e.getStat(Unit.DEFENSE);

		//weapon triangle

		admg += (5 * EFFECT[aWep][eWep]);
		edmg += (5 * EFFECT[eWep][aWep]);
		if (ad)
			odmg += (5 * EFFECT[aWep][eWep]);
		if (ed)
			odmg += (5 * EFFECT[eWep][aWep]);

		//prevent healing
		if (odmg < 0)
			odmg = 0;
		if (edmg < 0)
			edmg = 0;
		if (admg < 0)
			admg = 0;

		//debug
		if (!ad && !ed)
			println(a.getXlass().getName() + " " + a.getNom() + " will do " + admg + " Damage while receiving " + edmg + " damage from the opposing " + e.getXlass().getName() + " " + e.getNom());
		if (ad && !ed)
			println(a.getXlass().getName() + " " + a.getNom() + " will do " + (admg + odmg) + " Damage with a double hit (" + admg + ") while receiving " + edmg + " damage from the opposing " + e.getXlass().getName() + " " + e.getNom());
		if (!ad && ed)
			println(a.getXlass().getName() + " " + a.getNom() + " will do " + admg + " Damage while receiving " + (edmg + odmg) + " damage with a double hit (" + edmg + ") from the opposing " + e.getXlass().getName() + " " + e.getNom());

		//Handle Critical
		int aCritR, eCritR;
		if (aw)
			aCritR = ((a.getStat(Unit.SKILL) / 2) + a.getWeapon().crit) - e.getStat(Unit.LUCK);
		else
			aCritR = (a.getStat(Unit.SKILL) / 2) - e.getStat(Unit.LUCK);
		if (ew)
			eCritR = ((e.getStat(Unit.SKILL) / 2) + e.getWeapon().crit) - a.getStat(Unit.LUCK);
		else
			eCritR = (e.getStat(Unit.SKILL) / 2) - a.getStat(Unit.LUCK);

		if (aCritR < 0)
			aCritR = 0;
		if (eCritR < 0)
			eCritR = 0;

		if (r.nextInt(100) < aCritR)
			aCrit = true;
		if (r.nextInt(100) < eCritR)
			eCrit = true;

		if (ad)
			if (r.nextInt(100) < aCritR)
				oCrit = true;
		if (ed)
			if (r.nextInt(100) < eCritR)
				oCrit = true;

		if (oCrit)
			odmg = odmg * 3;
		if (aCrit)
			admg = admg * 3;
		if (eCrit)
			edmg = edmg * 3;

		//debug
		println(a.getXlass().getName() + " " + a.getNom() + " has a " + aCritR + "% chance of critical.");
		println(e.getXlass().getName() + " " + e.getNom() + " has a " + eCritR + "% chance of critical.");

		//hit chance
		println(odmg + ":" + edmg + ":" + admg);

		if (!a.getWeapon().isMagic)
			ahit = a.getStat(Unit.SKILL) + (int) (100 * a.getWeapon().hit);
		else
			ahit = (int) (100 * a.getWeapon().hit);

		if (!e.getWeapon().isMagic)
			ehit = e.getStat(Unit.SKILL) + (int) (100 * e.getWeapon().hit);
		else
			ehit = (int) (100 * e.getWeapon().hit);

		//evasion
		if (e.getWeapon().isMagic)
			aev = a.getStat(Unit.SPEED) + a.getStat(Unit.LUCK);
		else
			aev = a.getStat(Unit.SPEED); //TODO + Terrain bonus
		if (a.getWeapon().isMagic)
			eev = e.getStat(Unit.SPEED) + e.getStat(Unit.LUCK);
		else
			eev = e.getStat(Unit.SPEED); //TODO + Terrain bounus

		//debug
		println(a.getNom() + " has a " + (ahit - eev) + "% chance of landing an attack with " + a.getWeapon().name + ".");
		println(e.getNom() + " has a " + (ehit - aev) + "% chance of landing an attack with " + e.getWeapon().name + ".");

		println(a.getXlass().getName() + " " + a.getNom() + " has " + ac + " HP.\n" + e.getXlass().getName() + " " + e.getNom() + " has " + ec + " HP.");

		//ATTACK!
		if (allyAttack) {
			if (r.nextInt(100) < (ahit - eev)) {
				ec -= admg;
				if (!aCrit)
					println(a.getNom() + " hits!");
				else
					println(a.getNom() + " gets a CRITICAL for " + admg);
			} else
				println(a.getNom() + " Missed!");
			deathCheck();
			if (!edead)
				if (r.nextInt(100) < (ehit - aev)) {
					ac -= edmg;
					if (!eCrit)
						println(e.getNom() + " hits!");
					else
						println(e.getNom() + " gets a CRITICAL for " + edmg);
				} else
					println(e.getNom() + " Missed!");

		} else {
			if (r.nextInt(100) < (ehit - aev)) {
				ac -= edmg;
				if (!eCrit)
					println(e.getNom() + " hits!");
				else
					println(e.getNom() + " gets a CRITICAL for " + edmg);
			} else
				println(e.getNom() + " Missed!");
			deathCheck();
			if (!adead)
				if (r.nextInt(100) < (ahit - eev)) {
					ec -= admg;
					if (!aCrit)
						println(a.getNom() + " hits!");
					else
						println(a.getNom() + " gets a CRITICAL for " + admg);
				} else
					println(a.getNom() + " Missed!");

		}
		deathCheck();
		if (!edead && !adead) {
			if (ad)
				if (r.nextInt(100) < (ahit - eev)) {
					ec -= odmg;
					if (!oCrit)
						println(a.getNom() + " hits!");
					else
						println(a.getNom() + " gets a CRITICAL for " + odmg);
				} else
					println(a.getNom() + " Missed!");
			if (ed)
				if (r.nextInt(100) < (ehit - aev)) {
					ac -= odmg;
					if (!oCrit)
						println(e.getNom() + " hits!");
					else
						println(e.getNom() + " gets a CRITICAL for " + odmg);
				} else
					println(e.getNom() + " Missed!");
			deathCheck();
		}
		//doLooseCheck();
		//debug
		if (!edead && !adead)
			println(a.getXlass().getName() + " " + a.getNom() + " has " + ac + " HP left.\n" + e.getXlass().getName() + " " + e.getNom() + " has " + ec + " HP left.");
		if (edead && !adead)
			println(a.getXlass().getName() + " " + a.getNom() + " has " + ac + " HP left.\n" + e.getXlass().getName() + " " + e.getNom() + " is dead.");
		if (!edead && adead)
			println(a.getXlass().getName() + " " + a.getNom() + " is dead.\n" + e.getXlass().getName() + " " + e.getNom() + " has " + ec + " HP left.");

	}

	private int allyDamage(boolean isMagic) {
		if (!isMagic)
			return (a.getStat(Unit.ATTACK) + (a.getWeapon().atk * isEffective(false))) - e.getStat(Unit.DEFENSE);
		else
			return (a.getStat(Unit.ATTACK) + (a.getWeapon().atk * isEffective(false))) - e.getStat(Unit.RESISTANCE);
	}

	private int enemyDamage(boolean isMagic) {
		if (!isMagic)
			return (e.getStat(Unit.ATTACK) + (e.getWeapon().atk * isEffective(true))) - a.getStat(Unit.DEFENSE);
		else
			return (e.getStat(Unit.ATTACK) + (e.getWeapon().atk * isEffective(true))) - a.getStat(Unit.RESISTANCE);
	}

	private int isEffective(boolean b) {
		int level = 1;
		if (!b) {
			for (int i = 0; i < a.getWeapon().efns.length; i++)
				for (int o = 0; o < e.getType().length; o++)
					if (a.getWeapon().efns[i] == e.getType()[o])
						level++;
		} else {
			for (int i = 0; i < e.getWeapon().efns.length; i++)
				for (int o = 0; o < a.getType().length; o++)
					if (e.getWeapon().efns[i] == a.getType()[o])
						level++;
		}
		return level;
	}

	private void deathCheck() {
		if (ac < 1) {
			ac = 0;
			a.deathToggle();
			adead = true;
		}
		if (ec < 1) {
			ec = 0;
			e.deathToggle();
			edead = true;
		}
	}

	private void println(String s) {
		Ugame.textBox.setText(Ugame.textBox.getText() + s + "\n");
	}

	public boolean isOver() {
		return adead || edead;
	}
}

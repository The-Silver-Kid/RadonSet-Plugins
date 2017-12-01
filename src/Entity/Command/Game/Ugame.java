package Entity.Command.Game;

import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import DevTSK.Entity.Entity;
import DevTSK.Util.BaseConverter;

public class Ugame {

	private JFrame window = new JFrame();
	private JLabel textBox = new JLabel();
	private JButton attack = new JButton(), idle = new JButton(), flee = new JButton(), close = new JButton();

	private Unit player, enemy;

	private BaseConverter b = new BaseConverter("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz-?.!");

	Random r = new Random();

	public Ugame(Entity[] e) {
		r.setSeed(System.currentTimeMillis());
		player = genFrom(e[r.nextInt(e.length)]);
		enemy = genFrom(e[r.nextInt(e.length)]);
		construct();
		run();
	}

	public Ugame(Entity[] entities, String playername, String enemyname) {

	}

	private Unit genFrom(Entity entity) {
		r.setSeed(b.to10(entity.getName() + entity.getGender().toString() + entity.hashCode(), b.MAX_BASE));

		return new Unit(entity.getName(),
				Data.getDefaultClasses()[r.nextInt(Data.getDefaultClasses().length)],
				Data.getDefaultWeapons()[r.nextInt(Data.getDefaultWeapons().length)],
				false,
				new int[] { r.nextInt(6),
						r.nextInt(6),
						r.nextInt(6),
						r.nextInt(6),
						r.nextInt(6),
						r.nextInt(6),
						r.nextInt(6),
						r.nextInt(6),
						r.nextInt(6) },
				Data.getDeathMessages()[r.nextInt(Data.getDeathMessages().length)]);
	}
}

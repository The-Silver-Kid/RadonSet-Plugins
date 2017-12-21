package Entity.Command.Game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import DevTSK.Entity.Entity;
import DevTSK.Util.BaseConverter;

public class Ugame {

	private JFrame window;
	public static JTextArea textBox;
	private JButton attack, idle, close;
	private JScrollPane pain;

	private Action ak, idl, cls;

	private Unit player, enemy;

	private BaseConverter b = new BaseConverter("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz-?.!");
	public Battle battleda;
	public boolean enemyPhase, gameAlive, isClosing;
	private Random r = new Random();

	private Thread runner = new CommanderVideo();

	public Ugame(Entity[] e) {
		r.setSeed(System.currentTimeMillis());
		player = genFrom(e[r.nextInt(e.length)]);
		enemy = genFrom(e[r.nextInt(e.length)]);
	}

	public void init() {
		enemyPhase = false;
		gameAlive = true;
		isClosing = false;
		r = new Random();
		runner = new CommanderVideo();
		ak = new Ak();
		idl = new Idl();
		cls = new Clse();
		window = new JFrame();
		textBox = new JTextArea();
		pain = new JScrollPane();
		attack = new JButton();
		idle = new JButton();
		close = new JButton();
		construct();
		start();
	}

	public Ugame(Entity player, Entity enemy) {
		this.player = genFrom(player);
		this.enemy = genFrom(enemy);
	}

	private void start() {
		battleda = new Battle(player, enemy);
		battleda.init();
		runner.start();
	}

	private void construct() {
		window.setSize(500, 350);
		window.setVisible(true);
		window.setTitle("THE GAME");
		window.setResizable(false);
		window.getContentPane().setLayout(null);
		window.getContentPane().setBackground(new Color(0x000000));

		textBox.setBounds(0, 0, 500, 260);
		textBox.setEditable(false);
		textBox.setForeground(new Color(0x00FF00));
		textBox.setBackground(new Color(0x000000));
		window.getContentPane().add(textBox);

		pain = new JScrollPane(textBox);
		pain.setBounds(textBox.getBounds());
		pain.setAutoscrolls(true);
		window.getContentPane().add(pain);

		attack.setBounds(0, 261, 250, 45);
		attack.setText("ATTACK!");
		attack.setBackground(new Color(0xFF0000));
		attack.setAction(ak);
		window.getContentPane().add(attack);

		idle.setBounds(251, 261, 250, 45);
		idle.setText("Idle");
		idle.setBackground(new Color(0x00FF00));
		idle.setAction(idl);
		window.getContentPane().add(idle);

		close.setBounds(0, 306, 500, 18);
		close.setText("Close");
		close.setBackground(new Color(0x000000));
		close.setForeground(new Color(0xFFFFFF));
		close.setAction(cls);
		window.getContentPane().add(close);
	}

	private Unit genFrom(Entity entity) {
		r.setSeed(b.to10(getHash(entity), 16));

		return new Unit(entity.getAltName(),
				Data.getDefaultClasses()[r.nextInt(Data.getDefaultClasses().length)],
				Data.getDefaultWeapons()[r.nextInt(Data.getDefaultWeapons().length)],
				false,
				new int[] { r.nextInt(11),
						r.nextInt(11),
						r.nextInt(11),
						r.nextInt(11),
						r.nextInt(11),
						r.nextInt(11),
						r.nextInt(11),
						r.nextInt(11),
						r.nextInt(11) },
				new int[] { 0 },
				Data.getDeathMessages()[r.nextInt(Data.getDeathMessages().length)]);
	}

	private String getHash(Entity entity) {
		return b.from10(
				b.to10(entity.getName().substring(0, 3), b.MAX_BASE) +
						b.to10(entity.getGender().substring(0, 3), b.MAX_BASE) +
						entity.getBirthday().getDay() +
						entity.getBirthday().getYear(),
				16);
	}

	class Ak extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public Ak() {
			putValue(NAME, "ATTACK!");
		}

		public void actionPerformed(ActionEvent arg0) {
			if (!enemyPhase) {
				battleda.doBattle(true);
				enemyPhase = true;
			}
		}

	}

	class Clse extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public Clse() {
			putValue(NAME, "Close");
		}

		public void actionPerformed(ActionEvent arg0) {
			isClosing = true;
			textBox.setText("");
			window.dispose();
		}

	}

	class Idl extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public Idl() {
			putValue(NAME, "Idle");
		}

		public void actionPerformed(ActionEvent arg0) {
			if (!enemyPhase)
				enemyPhase = true;
		}

	}

	class CommanderVideo extends Thread {
		public void run() {
			while (gameAlive) {
				if (enemyPhase) {
					textBox.setText(textBox.getText() + "\n");
					battleda.doBattle(false);
					enemyPhase = !enemyPhase;
				}
				gameAlive = !battleda.isOver();
				if (gameAlive)
					gameAlive = !isClosing;
			}
		}
	}
}

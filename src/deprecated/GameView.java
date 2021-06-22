package views;

import controller.Game;
import controller.GameLoop;
import model.World;
import model.CommandButton;
import model.LevelUpButton;
import model.SummonButton;

import java.util.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;

public class GameView extends JFrame{
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	private final Canvas canvas;
	private final Background background;
	private final ButtonsPanel buttonsPanel;
	private final Game game;

	public GameView(Game game, String ... spriteNames) throws HeadlessException{
		this.game = game;
		this.background = new Background();
		this.canvas = new Canvas();
		this.game.setView(canvas);
		this.buttonsPanel = new ButtonsPanel(game, spriteNames);
	}

	public void launch(){
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		buttonsPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT - background.getPreferredSize().height));
		setContentPane(background);
		add(canvas);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		add(buttonsPanel);
		pack();
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setVisible(true);
	}

	private class Canvas extends JPanel implements GameLoop.View{
		private World world;

		public Canvas(){
			setOpaque(false);
			setPreferredSize(GameView.this.background.getPreferredSize());
		}

		@Override
		public void render(World world) {
			this.world = world;
			repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			world.render(g);
		}
	}

	private class Background extends JPanel{
		private final String backgroundPath = "assets/images/background.png";
		private final Image backgroundImage;

		public Background(){
			Image originalBackgroundImage;

			try{
				originalBackgroundImage = ImageIO.read(new File(backgroundPath));
			} catch(IOException e){
				throw new RuntimeException(e);
			}

			double scaleMultiplier = (double)GameView.WIDTH / (double)originalBackgroundImage.getWidth(null);
			backgroundImage = originalBackgroundImage.getScaledInstance(GameView.WIDTH, (int)((double)orginalBackgroundImage.getHeight(null) * scaleMultiplier), Image.SCALE_DEFAULT);
			setPreferredSize(new Dimension(this.backgroundImage.getWidth(null), this.backgroundImage.getHeight(null)));
		}

		@Override
		public void paintComponent(Graphics g){  
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, this);
		}

		@Override
		public Dimension getPreferredSize(){
			return new Dimension(backgroundImage.getWidth(null), backgroundImage.getHeight(null));
		}
	}

	private class ButtonsPanel extends JPanel{
		private final List<CommandButton> buttons;

		public ButtonsPanel(Game game, String ... spriteNames){
			this.buttons = new ArrayList<>();
			setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			LevelUpButton levelUpButton = new LevelUpButton(game);
			buttons.add(levelUpButton);
			this.add(levelUpButton);

			for(String name: spriteNames){
				SummonButton button = new SummonButton(game, name);
				buttons.add(button);
				this.add(button);
			}
		}

		@Override
		public void setPreferredSize(Dimension preferredSize){
			super.setPreferredSize(preferredSize);
			Dimension buttonPreferredSize = new Dimension((int)((double)preferredSize.width / (double)buttons.size()), preferredSize.height);

			for(CommandButton b: buttons)
				b.setPreferredSize(buttonPreferredSize);
		}
	}
}

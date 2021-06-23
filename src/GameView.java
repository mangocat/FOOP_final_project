

// import controller.Game;
// import controller.GameLoop;
// import model.World;
// import model.Button;
// import model.LevelUpButton;
// import model.SummonButton;

import java.util.List;
import java.util.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;

public class GameView extends JFrame{
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 750;
	private final Canvas canvas;
	private final Background background;
	private final ButtonsPanel buttonsPanel;
	private final Game game;

	public GameView(Game game, List<Button> buttons) throws HeadlessException{
		this.game = game;
		this.background = new Background();
		this.canvas = new Canvas();
		this.game.setView(canvas);
		this.buttonsPanel = new ButtonsPanel(game, buttons);
	}

	public void launch(){
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		buttonsPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT - background.getPreferredSize().height));
		setLayout(null);
		canvas.setBounds(0, 0, WIDTH, HEIGHT);
		add(canvas);
		background.setBounds(0, 0, background.getPreferredSize().width, background.getPreferredSize().height);
		add(background);
		buttonsPanel.setBounds(0, background.getPreferredSize().height, buttonsPanel.getPreferredSize().width, buttonsPanel.getPreferredSize().height);
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
		public void renderGameOver(boolean humanWin){
			JFrame gameOverFrame = new JFrame();
			gameOverFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			gameOverFrame.setLayout(new GridBagLayout());
			JLabel resultText = new JLabel(humanWin? "You win!": "You lose.");
			resultText.setFont(new Font(null, Font.PLAIN, 48));
			gameOverFrame.add(resultText);
			gameOverFrame.getContentPane().setBackground(humanWin? Color.BLUE: Color.RED);
			gameOverFrame.setSize(GameView.WIDTH + 10, GameView.HEIGHT);
			gameOverFrame.setLocationRelativeTo(GameView.this);
			gameOverFrame.setVisible(true);
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
			backgroundImage = originalBackgroundImage.getScaledInstance(GameView.WIDTH, (int)((double)originalBackgroundImage.getHeight(null) * scaleMultiplier), Image.SCALE_DEFAULT);
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
		private final List<Button> buttons;

		public ButtonsPanel(Game game, List<Button> buttons){
			this.buttons = buttons;
			setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

			for(Button b: buttons)
				this.add(b);
		}

		@Override
		public void setPreferredSize(Dimension preferredSize){
			super.setPreferredSize(preferredSize);
			Dimension buttonPreferredSize = new Dimension((int)((double)preferredSize.width / (double)buttons.size()), preferredSize.height - 30);

			for(Button b: buttons)
				b.setPreferredSize(buttonPreferredSize);
		}
	}
}

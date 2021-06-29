package view;

import controller.*;
import model.World;

import java.util.List;
import java.util.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.event.*;

public class GameView extends JFrame implements GameLoop.View {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 750;
    private final GameplayPanel gameplayPanel;
    private final GameOverPanel gameOverPanel;
    private final Game game;

    public GameView(Game game, List<Button> buttons) throws HeadlessException {
        this.game = game;
        this.gameplayPanel = new GameplayPanel(game, buttons);
        this.gameOverPanel = new GameOverPanel();
        this.game.setView(this);
    }

    public void launch() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout());
        contentPanel.add(this.gameplayPanel, "gameplay panel");
        contentPanel.add(this.gameOverPanel, "game over panel");
        this.setContentPane(contentPanel);
        this.pack();
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void render(World world) {
        this.gameplayPanel.render(world);
    }

    @Override
    public void renderGameOver(boolean humanWin) {
        this.gameOverPanel.setResultText(humanWin ? "You win!" : "You lose.");
        this.gameOverPanel.setBackground(humanWin ? Color.BLUE : Color.RED);
        ((CardLayout)(this.getContentPane().getLayout())).next(this.getContentPane());
    }

    private class GameplayPanel extends JPanel {
        private final Canvas canvas;
        private final Background background;
        private final ButtonsPanel buttonsPanel;
        private World world;

        public GameplayPanel(Game game, List<Button> buttons) {
            this.world = game.getWorld();
            this.background = new Background();
            this.canvas = new Canvas();
            this.buttonsPanel = new ButtonsPanel(game, buttons);
            this.buttonsPanel.setPreferredSize(new Dimension(GameView.WIDTH, GameView.HEIGHT - this.background.getPreferredSize().height));
            this.setLayout(null);
            this.canvas.setBounds(0, 0, GameView.WIDTH, GameView.HEIGHT);
            this.add(canvas);
            this.background.setBounds(0, 0, this.background.getPreferredSize().width, this.background.getPreferredSize().height);
            this.add(background);
            this.buttonsPanel.setBounds(0, this.background.getPreferredSize().height, this.buttonsPanel.getPreferredSize().width, this.buttonsPanel.getPreferredSize().height);
            this.add(buttonsPanel);
        }

        public void render(World world) {
            this.world = world;
            this.canvas.repaint();
        }

        private class Canvas extends JPanel {
            public Canvas() {
                setBackground(new Color(0, 0, 0, 0));
                setOpaque(false);
                setPreferredSize(GameplayPanel.this.background.getPreferredSize());
            }           

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                GameplayPanel.this.world.render(g);
            }
        }

        private class Background extends JPanel {
            private final String backgroundPath = "assets/images/background.png";
            private final Image backgroundImage;

            public Background() {
                Image originalBackgroundImage;

                try {
                    originalBackgroundImage = ImageIO.read(new File(backgroundPath));
                } catch(IOException e) {
                    throw new RuntimeException(e);
                }

                double scaleMultiplier = (double)GameView.WIDTH / (double)originalBackgroundImage.getWidth(null);
                backgroundImage = originalBackgroundImage.getScaledInstance(GameView.WIDTH, (int)((double)originalBackgroundImage.getHeight(null) * scaleMultiplier), Image.SCALE_DEFAULT);
                setPreferredSize(new Dimension(this.backgroundImage.getWidth(null), this.backgroundImage.getHeight(null)));
            }

            @Override
            public void paintComponent(Graphics g) {  
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, this);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(backgroundImage.getWidth(null), backgroundImage.getHeight(null));
            }
        }

        private class ButtonsPanel extends JPanel {
            private final List<Button> buttons;

            public ButtonsPanel(Game game, List<Button> buttons) {
                this.buttons = buttons;
                setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

                for (Button b : buttons)
                    this.add(b);
            }

            @Override
            public void setPreferredSize(Dimension preferredSize) {
                super.setPreferredSize(preferredSize);
                Dimension buttonPreferredSize = new Dimension((int)((double)preferredSize.width / (double)buttons.size()), preferredSize.height - 30);

                for (Button b : buttons)
                    b.setPreferredSize(buttonPreferredSize);
            }
        }
    }

    private class GameOverPanel extends JPanel {
        private JLabel resultText;
        private final JButton restartButton;

        private void placeComponent(GridBagConstraints cons, Component comp, JPanel panel, int x, int y, int w, int h) {
            cons.gridx = x;
            cons.gridy = y;
            cons.gridwidth = w;
            cons.gridheight = h;
            panel.add(comp, cons);
        }

        private ActionListener getRestartActionListener() {
            return new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Container gameViewContentPane = GameView.this.getContentPane();
                    ((CardLayout)(gameViewContentPane.getLayout())).next(gameViewContentPane);
                    GameView.this.game.restart();
                }
            };
        }

        public GameOverPanel() {
            this.resultText = new JLabel();
            this.restartButton = new JButton("restart");
            restartButton.addActionListener(getRestartActionListener());
            this.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.weightx = 0.5;
            c.weighty = 0.5;
            placeComponent(c, Box.createHorizontalStrut(10), this, 0, 0, 3, 1);
            placeComponent(c, this.resultText, this, 1, 1, 1, 1);
            placeComponent(c, this.restartButton, this, 1, 2, 1, 1);
        }

        public void setResultText(String s) {
            this.resultText.setText(s);
            this.resultText.setFont(new Font(null, Font.PLAIN, 48));
        }
    }
}

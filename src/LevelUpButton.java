

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LevelUpButton extends Button{
	private final Game game;

	public LevelUpButton(Game game){
		this.game = game;
		setText("Level Up!");
		addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					LevelUpButton.this.game.humanLevelUp();
				}
			}
		);
	}
}

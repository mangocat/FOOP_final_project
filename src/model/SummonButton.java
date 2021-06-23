package model;

import controller.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class SummonButton extends Button{
	private static final String loadDirectory = "assets/icons/";
	private final String spriteName;
	private final Game game;

	public SummonButton(Game game, String spriteName){
		this.spriteName = spriteName;
		this.game = game;
		Image iconImage;

		try{
			iconImage = ImageIO.read(new File(loadDirectory + spriteName + ".png"));
		} catch(IOException e){
			throw new RuntimeException(e);
		}

		setIcon(new ImageIcon(iconImage));
		setText(spriteName);
		setHorizontalTextPosition(JButton.RIGHT);

		addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					SummonButton.this.game.hummanSummon(spriteName);
				}
			}
		);
	}

	@Override
	public void setPreferredSize(Dimension preferredSize){
		super.setPreferredSize(preferredSize);
		Image i = ((ImageIcon)getIcon()).getImage();
		double scaleMultiplier = (double)preferredSize.height / (double)i.getHeight(null);
		i = i.getScaledInstance((int)((double)i.getWidth(null) * scaleMultiplier), preferredSize.height, Image.SCALE_DEFAULT);
		setIcon(new ImageIcon(i));
	}
}

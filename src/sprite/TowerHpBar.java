package sprite;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;

public class TowerHpBar {
    private final Tower tower;
    private final int maxHp;
    private int hp, height = 5;

    public TowerHpBar(Tower tower) {
        this.tower = tower;
        maxHp = hp = tower.getCurHp();
    }

    public void update() {
        hp = tower.getCurHp();
    }

    public void render(Graphics g) {
        Rectangle range = tower.getRange();
        int greenWidth = (int)(((double)hp / maxHp) * range.width);
        int y = range.y - height;
        g.setColor(Color.red);
        g.fillRect(range.x, y, range.width, height);
        g.setColor(Color.green);
        g.fillRect(range.x, y, greenWidth, height);
        g.setColor(Color.black);
    }
}

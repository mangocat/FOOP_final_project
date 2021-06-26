import java.util.Map;

public class ButtonHandler {
    private Human human;
    private Map<UnitCreator, Button> ucToButtons;
    private Map<UnitCreator, String> ucToStrings;
    private Button levelUpButton;
    
    public ButtonHandler(Human human, Map<UnitCreator, Button> ucToButtons, Map<UnitCreator, String> ucToStrings, Button levelUpButton) {
        this.human = human;
        this.ucToButtons = ucToButtons;
        this.ucToStrings = ucToStrings;
        this.levelUpButton = levelUpButton;
    }

    public void update() {        
        for(UnitCreator uc : this.ucToButtons.keySet()) {
            int CD = this.human.getCD(uc);
            int money = this.human.getMoney();
            int cost = uc.getCost();
            
            this.ucToButtons.get(uc).setEnabled((CD == 0) && (money >= cost));
            this.ucToButtons.get(uc).setText("<html>" + this.ucToStrings.get(uc) + "<br>" +
                                            "Cost: " + cost + "<br>" + 
                                            "CD: " + CD + "</html>");
        }

        this.levelUpButton.setEnabled(this.human.getMoney() >= this.human.getLevelCost());
        this.levelUpButton.setText("<html>" + "Level Up" + "<br>" + 
                                "Cost: " + this.human.getLevelCost() + "</html>");

        return;
    }

    public void setDisable(UnitCreator uc) {
        this.ucToButtons.get(uc).setEnabled(false);
        return;
    }
}



// import model.World;

public abstract class GameLoop {
	private boolean running;
	private View view;

	public void setView(View view) {
		this.view = view;
	}

	public void start() {
		new Thread(this::gameLoop).start();
	}

	private void gameLoop() {
		running = true;
		boolean humanWin = false;

		while (running) {
			World world = getWorld();
			world.update();
			view.render(world);
			delay(15);


			if(world.getHuman().isLose()){
				humanWin = false;
				running = false;
			}
			else if(world.getComputer().isLose()){
				humanWin = true;
				running = false;
			}
		}

		view.renderGameOver(humanWin);
	}

	protected abstract World getWorld();

	public void stop() {
		running = false;
	}

	private void delay(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void restart(){
		this.getWorld().reset();
		new Thread(this::gameLoop).start();
	}

	public interface View {
		void render(World world);
		void renderGameOver(boolean humanWin);
	}
}

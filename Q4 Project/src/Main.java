
public class Main implements Runnable {
	
	GameClass game = new GameClass();
	
	public static void main(String[] args) {
		(new Thread(new Main())).start();
	}

	@Override
	public void run() {
		while(true) {
			game.doIt();
			game.winCheck();
		}
	}

}
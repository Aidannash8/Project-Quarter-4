
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameClass extends JFrame {
	
	boolean victory = false;
	
	Date startDate = new Date();
	
	Date winDate;
	
	
	int xC = -100;
	int yC = -100;
	
	int fX = 500;
	int fY = 25;
	
	int smX = 365;
	int smY = 15;
	
	int ckX = 600;
	int ckY = 25;
	
	int vicX = -250;
	int vicY = 25;
	int vicX2 = -250;
	
	int time;
	int hundreds;
	int tens;
	int ones;
	
	int[][] table = new int[16][12];
	int[][] nearby = new int[16][12];
	boolean[][] revealed = new boolean[16][12];
	boolean[][] flagged = new boolean[16][12];
	
	Random rand = new Random();
	
	boolean flagger = false;
	
	boolean happy = true;
	
	public GameClass() {
		
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 12; j++) {
				table[i][j] = 0;
				nearby[i][j] = 0;
				revealed[i][j] = false;
				flagged[i][j] = false;
				if (rand.nextInt(100) < 20 && j > 1) {
					table[i][j] = 1;
				}
			}
		}
		
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 12; j++) {
				for (int m = 0; m < 16; m++) {
					for (int n = 0; n < 12; n++) {
						if (Math.abs(i-m) <= 1 && Math.abs(j-n) <= 1 && (i != m || j != n) && table[m][n] == 1) {
							nearby[i][j]++;
						}
					}
				}
			}
		}
		
		Board board = new Board();
		Move move = new Move();
		Click click = new Click();
		
		this.setSize(806,630);
		this.setTitle("Minesweeper");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		this.setContentPane(board);
		
		board.addMouseMotionListener(move);
		board.addMouseListener(click);
	}
	
	public class Board extends JPanel {
		
		public void paintComponent (Graphics g) {
			g.fillRect(0, 0, 800, 600);
			for (int i = 0; i < 16; i++) {
				for (int j = 2; j < 12; j++) {
					g.setColor(Color.GRAY);
					if (revealed[i][j] == true) {
						g.setColor(Color.WHITE);
						if (nearby[i][j] > 0) {
							g.setColor(Color.LIGHT_GRAY);
						}
						if (table[i][j] == 1) {
							g.setColor(Color.RED);
						}
						
					}
					
					
					g.fillRect(2 + 50 * i, 2 + 50 * j, 46, 46);
					
					if (flagged[i][j] == true) {
						g.setColor(Color.BLACK);
						g.fillRect(i * 50 + 24, j * 50 + 10, 3, 30);
						g.fillRect(i * 50 + 20, j * 50 + 35, 11, 5);
						g.fillRect(i * 50 + 16, j * 50 + 37, 19, 3);
						g.setColor(Color.RED);
						g.fillRect(i * 50 + 15, j * 50 + 10, 9, 9);
					}
					
					if (nearby[i][j] > 0 && table[i][j] != 1 && revealed[i][j] == true) {
						int k = nearby[i][j];
						if (k == 1) {
							g.setColor(Color.BLUE);
							g.fillRect(i * 50 + 23, j * 50 + 10, 4, 30);
							g.fillRect(i * 50 + 19, j * 50 + 36, 12, 4);
							g.fillRect(i * 50 + 19, j * 50 + 10, 8, 4);
						} else if (k == 2) {
							g.setColor(new Color(0,180,0));
							g.fillRect(i * 50 + 15, j * 50 + 10, 20, 4);
							g.fillRect(i * 50 + 15, j * 50 + 23, 20, 4);
							g.fillRect(i * 50 + 15, j * 50 + 36, 20, 4);
							g.fillRect(i * 50 + 31, j * 50 + 10, 4, 17);
							g.fillRect(i * 50 + 15, j * 50 + 23, 4, 17);
						} else if (k == 3) {
							g.setColor(Color.RED);
							g.fillRect(i * 50 + 15, j * 50 + 10, 20, 4);
							g.fillRect(i * 50 + 15, j * 50 + 23, 20, 4);
							g.fillRect(i * 50 + 15, j * 50 + 36, 20, 4);
							g.fillRect(i * 50 + 31, j * 50 + 10, 4, 30);
						} else if (k == 4) {
							g.setColor(new Color(15,15,112));
							g.fillRect(i * 50 + 30, j * 50 + 10, 4, 30);
							g.fillRect(i * 50 + 16, j * 50 + 10, 4, 14);
							g.fillRect(i * 50 + 16, j * 50 + 20, 20, 4);
						} else if (k == 5) {
							g.setColor(new Color(165,42,42));
							g.fillRect(i * 50 + 15, j * 50 + 10, 20, 4);
							g.fillRect(i * 50 + 15, j * 50 + 23, 20, 4);
							g.fillRect(i * 50 + 15, j * 50 + 36, 20, 4);
							g.fillRect(i * 50 + 15, j * 50 + 10, 4, 17);
							g.fillRect(i * 50 + 31, j * 50 + 23, 4, 17);
						} else if (k == 6) {
							g.setColor(new Color(32,178,170));
							g.fillRect(i * 50 + 15, j * 50 + 10, 20, 4);
							g.fillRect(i * 50 + 15, j * 50 + 23, 20, 4);
							g.fillRect(i * 50 + 15, j * 50 + 36, 20, 4);
							g.fillRect(i * 50 + 15, j * 50 + 10, 4, 17);
							g.fillRect(i * 50 + 31, j * 50 + 23, 4, 17);
							g.fillRect(i * 50 + 15, j * 50 + 23, 4, 17);
						} else if (k == 7) {
							g.setColor(Color.MAGENTA);
							g.fillRect(i * 50 + 15, j * 50 + 10, 20, 4);
							g.fillRect(i * 50 + 15, j * 50 + 10, 4, 5);
							g.fillRect(i * 50 + 31, j * 50 + 10, 4, 8);
							g.fillRect(i * 50 + 29, j * 50 + 18, 4, 4);
							g.fillRect(i * 50 + 27, j * 50 + 22, 4, 4);
							g.fillRect(i * 50 + 25, j * 50 + 26, 4, 4);
							g.fillRect(i * 50 + 23, j * 50 + 30, 4, 4);
							g.fillRect(i * 50 + 21, j * 50 + 34, 4, 4);
						} else if (k == 8) {
							g.setColor(new Color(139,139,131));
							g.fillRect(i * 50 + 15, j * 50 + 10, 20, 4);
							g.fillRect(i * 50 + 15, j * 50 + 23, 20, 4);
							g.fillRect(i * 50 + 15, j * 50 + 36, 20, 4);
							g.fillRect(i * 50 + 31, j * 50 + 10, 4, 30);
							g.fillRect(i * 50 + 15, j * 50 + 10, 4, 30);
						}
					}
				}
			}
			
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(fX, fY, 50, 50);
			if (flagger == true) {
				g.setColor(Color.BLACK);
				g.fillRect(fX + 24, fY + 10, 3, 30);
				g.fillRect(fX + 20, fY + 35, 11, 5);
				g.fillRect(fX + 16, fY + 37, 19, 3);
				g.setColor(Color.RED);
				g.fillRect(fX + 15, fY + 10, 9, 9);
				g.fillRect(fX, fY, 50, 4);
				g.fillRect(fX, fY + 46, 50, 4);
				g.fillRect(fX, fY, 4, 50);
				g.fillRect(fX + 46, fY, 4, 50);
			} else {
				g.setColor(Color.BLACK);
				g.fillRect(fX + 24, fY + 10, 3, 30);
				g.fillRect(fX + 20, fY + 35, 11, 5);
				g.fillRect(fX + 16, fY + 37, 19, 3);
				g.setColor(Color.RED);
				g.fillRect(fX + 15, fY + 10, 9, 9);
			}
			
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(smX, smY, 70, 70);
			g.setColor(Color.YELLOW);
			g.fillOval(smX + 10, smY + 10, 50, 50);
			g.setColor(Color.BLACK);
			g.fillOval(smX + 20, smY + 25, 10, 10);
			g.fillOval(smX + 40, smY + 25, 10, 10);
			if (happy == true) {
				g.fillRect(smX + 26, smY + 46, 19, 4);
				g.fillRect(smX + 23, smY + 42, 5, 5);
				g.fillRect(smX + 43, smY + 42, 5, 5);
			} else {
				g.fillRect(smX + 26, smY + 44, 19, 4);
				g.fillRect(smX + 23, smY + 46, 5, 5);
				g.fillRect(smX + 43, smY + 46, 5, 5);
			}
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(ckX, ckY, 200, 50);
			time = (int) (new Date().getTime() - startDate.getTime()) / 1000;
			if (happy == true && victory == false) {
				ones = (int) time % 10;
				tens = (int) (time % 100 - ones) / 10;
				hundreds = (int) (time % 1000 - time % 100) / 100;
			}
			g.setColor(Color.BLACK);
			if (happy == false) {
				g.setColor(Color.RED);
			}
			if (victory == true) {
				g.setColor(Color.GREEN);
			}
			// HUNDREDS NUMBER DISPLAY
			if (hundreds == 0) {
				g.fillRect(ckX + 15, ckY + 10, 4, 30);
				g.fillRect(ckX + 31, ckY + 10, 4, 30);
				g.fillRect(ckX + 15, ckY + 10, 20, 4);
				g.fillRect(ckX + 15, ckY + 36, 20, 4);
			}
			if (hundreds == 1) {
				g.fillRect(ckX + 23, ckY + 10, 4, 30);
				g.fillRect(ckX + 19, ckY + 36, 12, 4);
				g.fillRect(ckX + 19, ckY + 10, 8, 4);
			} else if (hundreds == 2) {
				g.fillRect(ckX + 15, ckY + 10, 20, 4);
				g.fillRect(ckX + 15, ckY + 23, 20, 4);
				g.fillRect(ckX + 15, ckY + 36, 20, 4);
				g.fillRect(ckX + 31, ckY + 10, 4, 17);
				g.fillRect(ckX + 15, ckY + 23, 4, 17);
			} else if (hundreds == 3) {
				g.fillRect(ckX + 15, ckY + 10, 20, 4);
				g.fillRect(ckX + 15, ckY + 23, 20, 4);
				g.fillRect(ckX + 15, ckY + 36, 20, 4);
				g.fillRect(ckX + 31, ckY + 10, 4, 30);
			} else if (hundreds == 4) {
				g.fillRect(ckX + 30, ckY + 10, 4, 30);
				g.fillRect(ckX + 16, ckY + 10, 4, 14);
				g.fillRect(ckX + 16, ckY + 20, 20, 4);
			} else if (hundreds == 5) {
				g.fillRect(ckX + 15, ckY + 10, 20, 4);
				g.fillRect(ckX + 15, ckY + 23, 20, 4);
				g.fillRect(ckX + 15, ckY + 36, 20, 4);
				g.fillRect(ckX + 15, ckY + 10, 4, 17);
				g.fillRect(ckX + 31, ckY + 23, 4, 17);
			} else if (hundreds == 6) {
				g.fillRect(ckX + 15, ckY + 10, 20, 4);
				g.fillRect(ckX + 15, ckY + 23, 20, 4);
				g.fillRect(ckX + 15, ckY + 36, 20, 4);
				g.fillRect(ckX + 15, ckY + 10, 4, 17);
				g.fillRect(ckX + 31, ckY + 23, 4, 17);
				g.fillRect(ckX + 15, ckY + 23, 4, 17);
			} else if (hundreds == 7) {
				g.fillRect(ckX + 15, ckY + 10, 20, 4);
				g.fillRect(ckX + 15, ckY + 10, 4, 5);
				g.fillRect(ckX + 31, ckY + 10, 4, 8);
				g.fillRect(ckX + 29, ckY + 18, 4, 4);
				g.fillRect(ckX + 27, ckY + 22, 4, 4);
				g.fillRect(ckX + 25, ckY + 26, 4, 4);
				g.fillRect(ckX + 23, ckY + 30, 4, 4);
				g.fillRect(ckX + 21, ckY + 34, 4, 4);
			} else if (hundreds == 8) {
				g.fillRect(ckX + 15, ckY + 10, 20, 4);
				g.fillRect(ckX + 15, ckY + 23, 20, 4);
				g.fillRect(ckX + 15, ckY + 36, 20, 4);
				g.fillRect(ckX + 31, ckY + 10, 4, 30);
				g.fillRect(ckX + 15, ckY + 10, 4, 30);
			} else if (hundreds == 9) {
				g.fillRect(ckX + 15, ckY + 10, 20, 4);
				g.fillRect(ckX + 15, ckY + 23, 20, 4);
				g.fillRect(ckX + 15, ckY + 36, 20, 4);
				g.fillRect(ckX + 31, ckY + 10, 4, 30);
				g.fillRect(ckX + 15, ckY + 10, 4, 17);
			}
			// TENS NUMBER DISPLAY
			if (tens == 0) {
				g.fillRect(ckX + 15 + 30, ckY + 10, 4, 30);
				g.fillRect(ckX + 31 + 30, ckY + 10, 4, 30);
				g.fillRect(ckX + 15 + 30, ckY + 10, 20, 4);
				g.fillRect(ckX + 15 + 30, ckY + 36, 20, 4);
			}
			if (tens == 1) {
				g.fillRect(ckX + 23 + 30, ckY + 10, 4, 30);
				g.fillRect(ckX + 19 + 30, ckY + 36, 12, 4);
				g.fillRect(ckX + 19 + 30, ckY + 10, 8, 4);
			} else if (tens == 2) {
				g.fillRect(ckX + 15 + 30, ckY + 10, 20, 4);
				g.fillRect(ckX + 15 + 30, ckY + 23, 20, 4);
				g.fillRect(ckX + 15 + 30, ckY + 36, 20, 4);
				g.fillRect(ckX + 31 + 30, ckY + 10, 4, 17);
				g.fillRect(ckX + 15 + 30, ckY + 23, 4, 17);
			} else if (tens == 3) {
				g.fillRect(ckX + 15 + 30, ckY + 10, 20, 4);
				g.fillRect(ckX + 15 + 30, ckY + 23, 20, 4);
				g.fillRect(ckX + 15 + 30, ckY + 36, 20, 4);
				g.fillRect(ckX + 31 + 30, ckY + 10, 4, 30);
			} else if (tens == 4) {
				g.fillRect(ckX + 30 + 30, ckY + 10, 4, 30);
				g.fillRect(ckX + 16 + 30, ckY + 10, 4, 14);
				g.fillRect(ckX + 16 + 30, ckY + 20, 20, 4);
			} else if (tens == 5) {
				g.fillRect(ckX + 15 + 30, ckY + 10, 20, 4);
				g.fillRect(ckX + 15 + 30, ckY + 23, 20, 4);
				g.fillRect(ckX + 15 + 30, ckY + 36, 20, 4);
				g.fillRect(ckX + 15 + 30, ckY + 10, 4, 17);
				g.fillRect(ckX + 31 + 30, ckY + 23, 4, 17);
			} else if (tens == 6) {
				g.fillRect(ckX + 15 + 30, ckY + 10, 20, 4);
				g.fillRect(ckX + 15 + 30, ckY + 23, 20, 4);
				g.fillRect(ckX + 15 + 30, ckY + 36, 20, 4);
				g.fillRect(ckX + 15 + 30, ckY + 10, 4, 17);
				g.fillRect(ckX + 31 + 30, ckY + 23, 4, 17);
				g.fillRect(ckX + 15 + 30, ckY + 23, 4, 17);
			} else if (tens == 7) {
				g.fillRect(ckX + 15 + 30, ckY + 10, 20, 4);
				g.fillRect(ckX + 15 + 30, ckY + 10, 4, 5);
				g.fillRect(ckX + 31 + 30, ckY + 10, 4, 8);
				g.fillRect(ckX + 29 + 30, ckY + 18, 4, 4);
				g.fillRect(ckX + 27 + 30, ckY + 22, 4, 4);
				g.fillRect(ckX + 25 + 30, ckY + 26, 4, 4);
				g.fillRect(ckX + 23 + 30, ckY + 30, 4, 4);
				g.fillRect(ckX + 21 + 30, ckY + 34, 4, 4);
			} else if (tens == 8) {
				g.fillRect(ckX + 15 + 30, ckY + 10, 20, 4);
				g.fillRect(ckX + 15 + 30, ckY + 23, 20, 4);
				g.fillRect(ckX + 15 + 30, ckY + 36, 20, 4);
				g.fillRect(ckX + 31 + 30, ckY + 10, 4, 30);
				g.fillRect(ckX + 15 + 30, ckY + 10, 4, 30);
			} else if (tens == 9) {
				g.fillRect(ckX + 15 + 30, ckY + 10, 20, 4);
				g.fillRect(ckX + 15 + 30, ckY + 23, 20, 4);
				g.fillRect(ckX + 15 + 30, ckY + 36, 20, 4);
				g.fillRect(ckX + 31 + 30, ckY + 10, 4, 30);
				g.fillRect(ckX + 15 + 30, ckY + 10, 4, 17);
			}
			// ONES NUMBER DISPLAY
			if (ones == 0) {
				g.fillRect(ckX + 15 + 60, ckY + 10, 4, 30);
				g.fillRect(ckX + 31 + 60, ckY + 10, 4, 30);
				g.fillRect(ckX + 15 + 60, ckY + 10, 20, 4);
				g.fillRect(ckX + 15 + 60, ckY + 36, 20, 4);
			}
			if (ones == 1) {
				g.fillRect(ckX + 23 + 60, ckY + 10, 4, 30);
				g.fillRect(ckX + 19 + 60, ckY + 36, 12, 4);
				g.fillRect(ckX + 19 + 60, ckY + 10, 8, 4);
			} else if (ones == 2) {
				g.fillRect(ckX + 15 + 60, ckY + 10, 20, 4);
				g.fillRect(ckX + 15 + 60, ckY + 23, 20, 4);
				g.fillRect(ckX + 15 + 60, ckY + 36, 20, 4);
				g.fillRect(ckX + 31 + 60, ckY + 10, 4, 17);
				g.fillRect(ckX + 15 + 60, ckY + 23, 4, 17);
			} else if (ones == 3) {
				g.fillRect(ckX + 15 + 60, ckY + 10, 20, 4);
				g.fillRect(ckX + 15 + 60, ckY + 23, 20, 4);
				g.fillRect(ckX + 15 + 60, ckY + 36, 20, 4);
				g.fillRect(ckX + 31 + 60, ckY + 10, 4, 30);
			} else if (ones == 4) {
				g.fillRect(ckX + 30 + 60, ckY + 10, 4, 30);
				g.fillRect(ckX + 16 + 60, ckY + 10, 4, 14);
				g.fillRect(ckX + 16 + 60, ckY + 20, 20, 4);
			} else if (ones == 5) {
				g.fillRect(ckX + 15 + 60, ckY + 10, 20, 4);
				g.fillRect(ckX + 15 + 60, ckY + 23, 20, 4);
				g.fillRect(ckX + 15 + 60, ckY + 36, 20, 4);
				g.fillRect(ckX + 15 + 60, ckY + 10, 4, 17);
				g.fillRect(ckX + 31 + 60, ckY + 23, 4, 17);
			} else if (ones == 6) {
				g.fillRect(ckX + 15 + 60, ckY + 10, 20, 4);
				g.fillRect(ckX + 15 + 60, ckY + 23, 20, 4);
				g.fillRect(ckX + 15 + 60, ckY + 36, 20, 4);
				g.fillRect(ckX + 15 + 60, ckY + 10, 4, 17);
				g.fillRect(ckX + 31 + 60, ckY + 23, 4, 17);
				g.fillRect(ckX + 15 + 60, ckY + 23, 4, 17);
			} else if (ones == 7) {
				g.fillRect(ckX + 15 + 60, ckY + 10, 20, 4);
				g.fillRect(ckX + 15 + 60, ckY + 10, 4, 5);
				g.fillRect(ckX + 31 + 60, ckY + 10, 4, 8);
				g.fillRect(ckX + 29 + 60, ckY + 18, 4, 4);
				g.fillRect(ckX + 27 + 60, ckY + 22, 4, 4);
				g.fillRect(ckX + 25 + 60, ckY + 26, 4, 4);
				g.fillRect(ckX + 23 + 60, ckY + 30, 4, 4);
				g.fillRect(ckX + 21 + 60, ckY + 34, 4, 4);
			} else if (ones == 8) {
				g.fillRect(ckX + 15 + 60, ckY + 10, 20, 4);
				g.fillRect(ckX + 15 + 60, ckY + 23, 20, 4);
				g.fillRect(ckX + 15 + 60, ckY + 36, 20, 4);
				g.fillRect(ckX + 31 + 60, ckY + 10, 4, 30);
				g.fillRect(ckX + 15 + 60, ckY + 10, 4, 30);
			} else if (ones == 9) {
				g.fillRect(ckX + 15 + 60, ckY + 10, 20, 4);
				g.fillRect(ckX + 15 + 60, ckY + 23, 20, 4);
				g.fillRect(ckX + 15 + 60, ckY + 36, 20, 4);
				g.fillRect(ckX + 31 + 60, ckY + 10, 4, 30);
				g.fillRect(ckX + 15 + 60, ckY + 10, 4, 17);
			}
			
			// U WIN DISPLAY
			
			if (victory == true) {
				int L = (int) (-winDate.getTime() + new Date().getTime()) / 10;
				if (L > 300) {
					L = 300;
				}
				vicX = L - 250;
				vicX2 = vicX + 150;
				g.setColor(Color.GREEN);
				// LETTER "Y"
				for (int m = 0; m < 11; m++) {
					g.fillRect(vicX+m+10, vicY+m+10, 7, 5);
					g.fillRect(vicX+33-m, vicY+m+10, 7, 5);
				}
				g.fillRect(vicX+21, vicY+23, 8, 17);
				// LETTER "O"
				g.fillRect(vicX+50, vicY+10, 30, 5);
				g.fillRect(vicX+50, vicY+35, 30, 5);
				g.fillRect(vicX+50, vicY+10, 5, 30);
				g.fillRect(vicX+75, vicY+10, 5, 30);
				// LETTER"U"
				g.fillRect(vicX+90, vicY+35, 30, 5);
				g.fillRect(vicX+90, vicY+10, 5, 30);
				g.fillRect(vicX+115, vicY+10, 5, 30);
				// LETTER "W"
				for (int m = 0; m < 10; m++) {
					g.fillRect(vicX2+6+m, vicY+10+m*3, 4, 4);
					g.fillRect(vicX2+40-m, vicY+10+m*3, 4, 4);
					if (m < 5) {
						g.fillRect(vicX2+22-m, vicY+22+m*3, 4, 4);
						g.fillRect(vicX2+24+m, vicY+22+m*3, 4, 4);
					}
				}
				// LETTER "I"
				g.fillRect(vicX2+60, vicY+10, 5, 30);
				// LETTER "N"
				for (int m = 0; m < 27; m++) {
					g.fillRect(vicX2+80+m, vicY+10+m, 4, 4);
					g.fillRect(vicX2+80, vicY+10, 5, 30);
					g.fillRect(vicX2+105, vicY+10, 5, 30);
				}
			}
			
		}
		
	}
	
	public boolean isInBox(int xC, int yC, int cols, int rows) {
		
		if (xC >= cols * 50 + 2 && xC < (cols + 1) * 50 - 2 && yC >= rows * 50 + 2 && yC < (rows + 1) * 50 - 2) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isIn(int xC, int yC) {
		for (int x = 0; x < 16; x++) {
			for (int y = 2; y < 12; y++) {
				if (xC >= x * 50 + 2 && xC < (x + 1) * 50 - 2 && yC >= y * 50 + 2 && yC < (y + 1) * 50 - 2) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	
	public int getXBox(int xC, int yC) {
		for (int x = 0; x < 16; x++) {
			for (int y = 2; y < 12; y++) {
				if (xC >= x * 50 + 2 && xC < (x + 1) * 50 - 2 && yC >= y * 50 + 2 && yC < (y + 1) * 50 - 2) {
					return x;
				}
			}
		}
		return 0;
	}
	
	public int getYBox(int xC, int yC) {
		for (int x = 0; x < 16; x++) {
			for (int y = 2; y < 12; y++) {
				if (xC >= x * 50 + 2 && xC < (x + 1) * 50 - 2 && yC >= y * 50 + 2 && yC < (y + 1) * 50 - 2) {
					return y;
				}
			}
		}
		return 0;
	}
	
	public void openClear(int a, int b) {
		boolean f = true;
		boolean h = true;
		ArrayList<int[]> C = new ArrayList<int[]>();
		int p[] = new int[2];
		for (int m = 0; m < 16; m++) {
			for (int n = 0; n < 12; n++) {
				if (Math.abs(m-a) <= 1 && Math.abs(n-b) <= 1 && (m != a || n != b)) {
					revealed[m][n] = true;
					flagged[m][n] = false;
					if (nearby[m][n] == 0) {
						p[0] = m;
						p[1] = n;
						C.add(p);
						p[0] = 0;
						p[1] = 0;
						f = false;
					}
				}
			}
			
		}
		}
		
	
	public void reset() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 12; j++) {
				table[i][j] = 0;
				nearby[i][j] = 0;
				revealed[i][j] = false;
				flagged[i][j] = false;
				if (rand.nextInt(10) < 2 && j > 1) {
					table[i][j] = 1;
				}
			}
			startDate = new Date();
		}
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 12; j++) {
				for (int m = 0; m < 16; m++) {
					for (int n = 0; n < 12; n++) {
						if (Math.abs(i-m) <= 1 && Math.abs(j-n) <= 1 && (i != m || j != n) && table[m][n] == 1) {
							nearby[i][j]++;
						}
					}
				}
				System.out.println(nearby[i][j]);
			}
		}
		flagger = false;
		happy = true;
		victory = false;
	}
	
	public void unhappy() {
		for (int x = 0; x < 16; x++) {
			for (int y = 2; y < 12; y++) {
				if (table[x][y] == 1) {
					revealed[x][y] = true;
				}
			}
		}
		happy = false;
	}
	
	public class Click implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			xC = e.getX();
			yC = e.getY();
			if (isIn(xC, yC) == true) {
				if (flagger == false) {
					if (flagged[getXBox(xC, yC)][getYBox(xC, yC)] == false) {
						revealed[getXBox(xC, yC)][getYBox(xC, yC)] = true;
						flagged[getXBox(xC, yC)][getYBox(xC, yC)] = false;
						if (table[getXBox(xC, yC)][getYBox(xC, yC)] == 1) {
							happy = false;
							unhappy();
						} else if (nearby[getXBox(xC, yC)][getYBox(xC, yC)] == 0) {
							openClear(getXBox(xC, yC),getYBox(xC, yC));
						}
					}
				} else {
					if (revealed[getXBox(xC, yC)][getYBox(xC, yC)] == false) {
						if(flagged[getXBox(xC, yC)][getYBox(xC, yC)] == false) {
							flagged[getXBox(xC, yC)][getYBox(xC, yC)] = true;
						} else {
							flagged[getXBox(xC, yC)][getYBox(xC, yC)] = false;
						}
					}
				}
			}
			if (xC >= fX && xC < fX + 50 && yC >= fY && yC < fY + 50) {
				if (flagger == false) {
					flagger = true;
				} else {
					flagger = false;
				}
			}
			if (xC >= smX && xC < smX + 70 && yC >= smY && yC < smY + 70) {
				reset();
			}
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			xC = e.getX();
			yC = e.getY();
			repaint();
		}
		
	}
	
	public class Move implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			xC = e.getX();
			yC = e.getY();
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			xC = e.getX();
			yC = e.getY();
			repaint();
		}
		
	}

	public void doIt() {
		repaint();
	}

	public void winCheck() {
		int mines = 0;
		int reveals = 0;
		for (int a = 0; a < 16; a++) {
			for (int b = 2; b < 12; b++) {
				if (table[a][b] == 1) {
					mines++;
				}
				if (revealed[a][b] == true) {
					reveals++;
				}
			}
		}
		if (160 - mines - reveals == 0 && happy == true && victory == false) {
			winDate = new Date();
			victory = true;
		}
	}
	
}
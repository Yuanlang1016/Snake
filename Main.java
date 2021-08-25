import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;


public class Main {
	public static String direction ="up";
	public static boolean isStart = false;
	public static boolean isDied = false;
	public static int length = 3;
	public static int headx = 20;
	public static int heady = 14;
	public static int tailx = 20;
	public static int taily = 16;
	public static int foodx = -1;
	public static int foody = -1;
	public static ArrayList<Integer> snake = new ArrayList<Integer>(3);
	
	public static void main(String[] args) {
		Random random = new Random();
		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 1067, 662);
		frame.setTitle("贪吃蛇");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(3);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JPanel drawPanel = new JPanel();
		drawPanel.setBounds(0, 0, 801, 601);
		frame.add(drawPanel);
		
		Graphics g = drawPanel.getGraphics();
		
		JLabel pslabel = new JLabel("点击开始按钮\n开始游戏");
		JButton ctrlButton = new JButton("开始游戏");
		pslabel.setBounds(850, 0, 200, 60);
		ctrlButton.setBounds(863, 60, 100, 30);
		ctrlButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(!isStart) {
					isStart = true;
					isDied = false;
					direction = "up";
					foodx = random.nextInt(39);
					foody = random.nextInt(29);
					length = 3;
					headx = 20;
					heady = 14;
					tailx = 20;
					taily = 16;
					snake.clear();
					snake.add(20);
					snake.add(14);
					snake.add(20);
					snake.add(15);
					snake.add(20);
					snake.add(16);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
		});
		
		frame.add(pslabel);
		frame.add(ctrlButton);
		
		
		snake.add(20);
		snake.add(14);
		snake.add(20);
		snake.add(15);
		snake.add(20);
		snake.add(16);
		
		
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_UP && direction != "down") {
					direction = "up";
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT && direction != "left") {
					direction = "right";
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN && direction != "up") {
					direction = "down";
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT && direction != "right") {
					direction = "left";
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyTyped(KeyEvent e) {}
			
		});
		
		while(true) {
			frame.requestFocusInWindow();
			try {
				if(length < 15) {
					Thread.sleep((int)(400 - length * 20));
				}
				else if(length < 20){
					Thread.sleep((int)(100 - length / 2));
				}
				else {
					Thread.sleep(85);
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(isStart && !isDied) {
				pslabel.setText("目前分数：" + length);
				pslabel.setLocation(873, 0);
				ctrlButton.setVisible(false);
				
				if(headx == foodx && heady == foody) {
					snake.add(tailx);
					snake.add(taily);
					foodx = random.nextInt(39);
					foody = random.nextInt(29);
					length += 1;
				}
				
				snake.remove(length * 2 - 1);
				snake.remove(length * 2 - 2);
				
				if(direction == "up") {
					snake.add(0, heady - 1);
					snake.add(0, headx);
				}
				
				if(direction == "right") {
					snake.add(0, heady);
					snake.add(0, headx + 1);
				}
				
				if(direction == "down") {
					snake.add(0, heady + 1);
					snake.add(0, headx);
				}
				
				if(direction == "left") {
					snake.add(0, heady);
					snake.add(0, headx - 1);
				}
				
				headx = snake.get(0);
				heady = snake.get(1);
				tailx = snake.get(length * 2 - 2);
				taily = snake.get(length * 2 - 1);
				
				for(int i = 1; i < length; i++) {
					if((headx == snake.get(i * 2) && heady == snake.get(i * 2 + 1)) ||
						headx < 0 || headx > 39 || heady < 0 || heady > 29) {
						isDied = true;
						isStart = false;
					}
				}
			}
			if(isDied) {
				pslabel.setText("你死了，得分：" + length);
				pslabel.setLocation(865, 0);
				ctrlButton.setText("重来一局");
				ctrlButton.setVisible(true);
				foodx = -1;
				foody = -1;
			}
			paint(drawPanel, g);
		}
	}

	
	public static void paint(JPanel panel, Graphics g) {
		panel.paint(g);
		
		for(int i = 0; i < 820; i += 20) {
			g.drawLine(i, 0, i, 600);
		}
		for(int i = 0; i < 620; i += 20) {
			g.drawLine(0, i, 800, i);
		}
		
		g.setColor(new Color(255, 255, 0));
		g.fillRect(foodx * 20 + 1, foody * 20 + 1, 19, 19);
		
		g.setColor(new Color(255, 0, 0));
		for(int i = 0; i < length; i++) {
			g.fillRect(snake.get(i * 2) * 20 + 1, snake.get(i * 2 + 1) * 20 + 1, 19, 19);
		}
	}
}
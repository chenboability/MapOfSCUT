package com.example.scut_map;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class CameraJFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	Dimension di = Toolkit.getDefaultToolkit().getScreenSize();

	public CameraJFrame() {
		setSize(di);
		getContentPane().add(new CameraJPanel());
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon(getClass()
						.getResource("/images/jiepingmouse.png")).getImage(),
				new Point(0, 0), null));
	}

	class CameraJPanel extends JPanel implements MouseListener,
			MouseMotionListener {

		private static final long serialVersionUID = 1L;
		BufferedImage bi, get;
		int startx, starty, endx, endy;
		int flag = 1;
		String filename = "";
		String fileformat = "png";
		int x1 = 0, y1 = 0, w1 = 0, h1 = 0, x2 = 0, y2 = 0, w2 = 0, h2 = 0,
				x3 = 0, y3 = 0, w3 = 0, h3 = 0, x4 = 0, y4 = 0, w4 = 0, h4 = 0;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // »ñÈ¡ÆÁÄ»µÄ³ß´ç
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		public CameraJPanel() {

			try {
				Robot ro = new Robot();
				bi = ro.createScreenCapture(new Rectangle(0, 0, di.width,
						di.height));
			} catch (Exception e) {
				e.printStackTrace();
			}
			addMouseListener(this);
			addMouseMotionListener(this);
		}

		public void paintComponent(Graphics g) {

			g.drawImage(bi, 0, 0, di.width, di.height, this);
			g.setColor(Color.YELLOW);
			g.drawRect(startx, starty, endx - startx, endy - starty);
			g.setColor(new Color(128, 128, 128, 128));
			g.fillRect(x1, y1, w1, h1);
			g.fillRect(x2, y2, w2, h2);
			g.fillRect(x3, y3, w3, h3);
			g.fillRect(x4, y4, w4, h4);

		}

		public void mouseClicked(MouseEvent e) {

		}

		private long getTime() {
			long time;
			Calendar calendar = Calendar.getInstance();
			time = calendar.getTimeInMillis();
			return time;

		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {

			startx = e.getX();
			starty = e.getY();

			w1 = screenWidth;
			h1 = starty;

			y2 = starty;
			w2 = startx;

			w3 = screenWidth;

			y4 = h1;

		}

		public void mouseReleased(MouseEvent e) {
			x1 = y1 = w1 = h1 = x2 = y2 = w2 = h2 = x3 = y3 = w3 = h3 = x4 = y4 = w4 = h4 = 0;

			try {
				Robot ro = new Robot();
				get = ro.createScreenCapture(new Rectangle(startx, starty, endx
						- startx, endy - starty));
				String name = getTime() + "." + fileformat;
				File f = new File(name);
				new fileSave(getContentPane(), get);
				// ImageIO.write(get, fileformat, f);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			startx = starty = endx = endy = 0;

			repaint();
			dispose();
		}

		public void mouseDragged(MouseEvent e) {

			endx = e.getX();
			endy = e.getY();

			h2 = endy - starty;

			y3 = endy;
			h3 = screenHeight - endy;

			x4 = endx;
			w4 = screenWidth - endx;
			h4 = screenHeight - h1 - h3;

			repaint();

		}

		public void mouseMoved(MouseEvent e) {
		}
	}
}

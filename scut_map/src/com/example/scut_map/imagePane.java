package com.example.scut_map;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

class ImagePanel extends JPanel {

	private DragStatus status = DragStatus.Ready; // 拖拽状态

	private Image image; // 要显示的图片

	ImageIcon icon;

	private Point imagePosition = new Point(0, 0), // 图片的当前位置

			imageStartposition = new Point(0, 0), // 每次拖拽开始时图片的位置（也就是上次拖拽后的位置）

			mouseStartposition; // 每次拖拽开始时鼠标的位置

	ImagePanel(ImageIcon icon) {

		this.icon = icon;

		image = icon.getImage();

		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {

			}

			// 按下鼠标时，更改状态，并且记录拖拽起始位置。
			public void mousePressed(MouseEvent e) {
				if (status == DragStatus.Ready) {
					status = DragStatus.Dragging;
					mouseStartposition = e.getPoint();
					imageStartposition.setLocation(imagePosition.getLocation());
				}
			}

			// 松开鼠标时更改状态
			public void mouseReleased(MouseEvent e) {
				if (status == DragStatus.Dragging) {
					status = DragStatus.Ready;
				}
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}
		});

		addMouseMotionListener(new MouseMotionListener() {

			// Java 有拖拽事件，在这个事件中移动图片位置
			public void mouseDragged(MouseEvent e) {
				if (map.isCounting == 0) { // 不是测量可以拉
					if (status == DragStatus.Dragging) {
						moveImage(e.getPoint());
					}
				}
			}

			public void mouseMoved(MouseEvent e) {
			}
		});
	}

	void setIcon(ImageIcon i) {
		this.icon = i;
		this.image = i.getImage();
		repaint();
	}

	private void moveImage(Point point) {
		// 图片的当前位置等于图片的起始位置加上鼠标位置的偏移量。
		imagePosition.setLocation(
				imageStartposition.getX()
						+ (point.getX() - mouseStartposition.getX()),
				imageStartposition.getY()
						+ (point.getY() - mouseStartposition.getY()));
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			g.drawImage(image, (int) imagePosition.getX(),
					(int) imagePosition.getY(), this);
		}
	}

	private enum DragStatus {
		Ready, Dragging
	}
}
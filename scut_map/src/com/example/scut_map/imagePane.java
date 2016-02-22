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

	private DragStatus status = DragStatus.Ready; // ��ק״̬

	private Image image; // Ҫ��ʾ��ͼƬ

	ImageIcon icon;

	private Point imagePosition = new Point(0, 0), // ͼƬ�ĵ�ǰλ��

			imageStartposition = new Point(0, 0), // ÿ����ק��ʼʱͼƬ��λ�ã�Ҳ�����ϴ���ק���λ�ã�

			mouseStartposition; // ÿ����ק��ʼʱ����λ��

	ImagePanel(ImageIcon icon) {

		this.icon = icon;

		image = icon.getImage();

		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {

			}

			// �������ʱ������״̬�����Ҽ�¼��ק��ʼλ�á�
			public void mousePressed(MouseEvent e) {
				if (status == DragStatus.Ready) {
					status = DragStatus.Dragging;
					mouseStartposition = e.getPoint();
					imageStartposition.setLocation(imagePosition.getLocation());
				}
			}

			// �ɿ����ʱ����״̬
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

			// Java ����ק�¼���������¼����ƶ�ͼƬλ��
			public void mouseDragged(MouseEvent e) {
				if (map.isCounting == 0) { // ���ǲ���������
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
		// ͼƬ�ĵ�ǰλ�õ���ͼƬ����ʼλ�ü������λ�õ�ƫ������
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
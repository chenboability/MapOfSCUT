package com.example.scut_map;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.plaf.LayerUI;

import com.example.scut_map.arithmetic.suanfa3;

public class topUI extends LayerUI<JComponent> {

	public static final int PLACE = 0; // type类型
	public static final int COUNT = 1;
	public static final int DRAW_X = 2;
	public static final int DRAW_DINSTANCE = 3;
	public static final int MARK = 4;
	public static final int MARK1 = 5;
	public static final int MARK2 = 6;

	public int isDrawLine = 0; // isDrawLine = 0,单击时：isDrawLine =
								// 1,双击时：isDrawLine = 2
	public int isSearching = 0;
	public int point_X_x, point_X_y;
	public int short_X_x, short_X_y;
	public int search_pointMark = -1;
	public int basePoint_x, basePoint_y;
	public int information_x, information_y;
	String information_str = "";
	int distance = 0;
	public int shortdistance = 0;
	public String alldistance = "";
	suanfa3 suanfa3;

	public int getBasePoint_x() {
		return basePoint_x;
	}

	public void setBasePoint_x(int basePoint_x) {
		this.basePoint_x = basePoint_x;
	}

	public int getBasePoint_y() {
		return basePoint_y;
	}

	public void setBasePoint_y(int basePoint_y) {
		this.basePoint_y = basePoint_y;
	}

	public int getPoint_X_x() {
		return point_X_x;
	}

	public int getPoint_X_y() {
		return point_X_y;
	}

	public ArrayList<PointInfo> points = new ArrayList<>();
	List<PointInfo> counts = new ArrayList<>();// 测量的点
	List<PointInfo> lines = new ArrayList<>();// 测量的线
	List<PointInfo> shortLines = new ArrayList<>();// 最短路径的线
	List<PointInfo> dinstances = new ArrayList<>();// 测量的距离值
	List<PointInfo> marks = new ArrayList<>();// 标志点
	PointInfo pointInfoShortX = new PointInfo();
	PointInfo pointSearch = new PointInfo();
	PointInfo pointDistence = new PointInfo();

	public topUI(suanfa3 suanfa3) {
		this.suanfa3 = suanfa3;
		this.points = suanfa3.readpoint();
	}

	public int getIsDrawLine() {
		return isDrawLine;
	}

	public void setIsDrawLine(int isDrawLine) {
		this.isDrawLine = isDrawLine;
	}

	public void showInformation(int x, int y, String mes) {
		information_str = mes;
		information_x = x;
		information_y = y;
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				1.0f));
		g2.setPaint(new GradientPaint(0, 0, Color.WHITE, 0, c.getHeight(),
				Color.WHITE));
		// g2.fillRect(0, 0, c.getWidth(), c.getHeight());
		g2.setColor(Color.RED);

		if (isSearching == 1) {

			
			pointSearch.setIcon(new ImageIcon(getClass().getResource(
					"/images/dialoglong.png")));
			Image img1 = pointSearch.getIcon().getImage();
			g2.drawImage(img1, points.get(map.searchMarkId).getPointX(), points
					.get(map.searchMarkId).getPointY(), null);
			g2.drawString("搜索结果为 : " + points.get(map.searchMarkId).getMessage(),
					points.get(map.searchMarkId).getPointX()+12, points
					.get(map.searchMarkId).getPointY()+22);
			
			
		}

		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).getType() == 1) {
				points.get(i)
						.setIcon(
								new ImageIcon(getClass().getResource(
										"/images/red.png")));
				Image img = points.get(i).getIcon().getImage();
				g2.drawImage(img, points.get(i).getPointX(), points.get(i)
						.getPointY() - 30, null);
			}
		}

		if (counts.size() > 0) {
			for (int i = 0; i < counts.size(); i++) {
				counts.get(i).setIcon(
						new ImageIcon(getClass().getResource(
								"/images/point.png")));
				Image img = counts.get(i).getIcon().getImage();
				g2.drawImage(img, counts.get(i).getPointX() - 8 + 15, counts
						.get(i).getPointY() - 8 + 30, null);

			}
		}

		if (lines.size() > 0) {
			if (isDrawLine == 1) // 单击
			{
				int i;
				for (i = 0; i < lines.size() - 1; i++) {
					g2.setColor(Color.MAGENTA);
					float lineWidth = 2.0f;
					((Graphics2D) g2).setStroke(new BasicStroke(lineWidth));
					g2.drawLine(lines.get(i).getPointX() + 15, lines.get(i)
							.getPointY() + 30,
							lines.get(i + 1).getPointX() + 15, lines.get(i + 1)
									.getPointY() + 30);
				}

				// 测量距离
				if (lines.size() > 1) {
					i--;
					int dis = distance += (Math
							.sqrt((lines.get(i).getPointX() - lines.get(i + 1)
									.getPointX())
									* (lines.get(i).getPointX() - lines.get(
											i + 1).getPointX())
									+ (lines.get(i).getPointY() - lines.get(
											i + 1).getPointY())
									* (lines.get(i).getPointY() - lines.get(
											i + 1).getPointY()))) * 0.42;

					PointInfo pointInfo = new PointInfo(lines.get(i + 1)
							.getPointX(), lines.get(i + 1).getPointY() - 30,
							dis + "", null, 0, DRAW_DINSTANCE);
					dinstances.add(pointInfo);

				}
				isDrawLine = 0;
			}

			if (isDrawLine == 2) // 双击
			{
				int i;
				for (i = 0; i < lines.size() - 1; i++) {
					g2.setColor(Color.RED);
					float lineWidth = 2.0f;
					((Graphics2D) g2).setStroke(new BasicStroke(lineWidth));
					g2.drawLine(lines.get(i).getPointX() + 15, lines.get(i)
							.getPointY() + 30,
							lines.get(i + 1).getPointX() + 15, lines.get(i + 1)
									.getPointY() + 30);

				}
				// 画x
				if (lines.size() >= 2) {

					PointInfo pointInfo = new PointInfo();
					pointInfo.setIcon(new ImageIcon(getClass().getResource(
							"/images/x.png")));
					Image img = pointInfo.getIcon().getImage();
					point_X_x = lines.get(i).getPointX() + 15 + 15;
					point_X_y = lines.get(i).getPointY() + 15 + 30;
					g2.drawImage(img, point_X_x, point_X_y, null);
					
					pointDistence.setIcon(new ImageIcon(getClass().getResource(
							"/images/dialoglong1.png")));
					Image img1 = pointDistence.getIcon().getImage();
					pointDistence.setPointX(point_X_x);
					pointDistence.setPointY(point_X_y);
					g2.drawImage(img1, point_X_x + 20, point_X_y + 20, null);
					g2.setColor(Color.DARK_GRAY);
					float lineWidth = 3.0f;
					((Graphics2D) g2).setStroke(new BasicStroke(lineWidth));
					int min = Integer.parseInt(alldistance) / 80;
					if (min == 0)
						min = 1;
					g2.drawString("总标记距离为" + alldistance + "米，步行大约需" + min + "分钟",
							point_X_x + 30, point_X_y + 42);
				}

			}
		}

		if (dinstances.size() > 0) {
			for (int i = 0; i < dinstances.size(); i++) {

				dinstances.get(i).setIcon(
						new ImageIcon(getClass().getResource(
								"/images/dialog1.png")));
				Image img = dinstances.get(i).getIcon().getImage();
				g2.drawImage(img, dinstances.get(i).getPointX() + 15,
						dinstances.get(i).getPointY() + 30, null);
				g2.setColor(Color.WHITE);
				float lineWidth = 2.0f;
				((Graphics2D) g2).setStroke(new BasicStroke(lineWidth));
				g2.drawString(dinstances.get(i).getMessage() + "m", dinstances
						.get(i).getPointX() + 9 + 15, dinstances.get(i)
						.getPointY() + 20 + 30);
				alldistance = dinstances.get(i).getMessage();

			}
		}
		
		

		// 最短路径的线
		if (map.isCaculating == 1) {
			shortLines = suanfa3.getpre();
			if (shortLines.size() > 0) {
				for (int i = 0; i < shortLines.size() - 1; i++) {
					System.out.println(shortLines.get(i).getPointX() + "!!!!!"
							+ shortLines.get(i).getPointY());
					g2.setColor(Color.RED);
					float lineWidth = 5.0f;
					((Graphics2D) g2).setStroke(new BasicStroke(lineWidth));
					g2.drawLine(shortLines.get(i).getPointX(), shortLines
							.get(i).getPointY(), shortLines.get(i + 1)
							.getPointX(), shortLines.get(i + 1).getPointY());
					shortdistance += (Math
							.sqrt((shortLines.get(i).getPointX() - shortLines
									.get(i + 1).getPointX())
									* (shortLines.get(i).getPointX() - shortLines
											.get(i + 1).getPointX())
									+ (shortLines.get(i).getPointY() - shortLines
											.get(i + 1).getPointY())
									* (shortLines.get(i).getPointY() - shortLines
											.get(i + 1).getPointY()))) * 0.42;
				}

			}

			map.isCaculating = 2;

			pointInfoShortX.setIcon(new ImageIcon(getClass().getResource(
					"/images/x.png")));
			Image img = pointInfoShortX.getIcon().getImage();
			short_X_x = shortLines.get(0).getPointX() + 15;
			short_X_y = shortLines.get(0).getPointY() + 15;
			pointInfoShortX.setPointX(short_X_x);
			pointInfoShortX.setPointY(short_X_y);
			g2.drawImage(img, short_X_x, short_X_y, null);

			pointDistence.setIcon(new ImageIcon(getClass().getResource(
					"/images/dialoglong1.png")));
			Image img1 = pointDistence.getIcon().getImage();
			pointDistence.setPointX(short_X_x);
			pointDistence.setPointY(short_X_y);
			g2.drawImage(img1, short_X_x + 20, short_X_y + 20, null);
			g2.setColor(Color.DARK_GRAY);
			float lineWidth = 3.0f;
			((Graphics2D) g2).setStroke(new BasicStroke(lineWidth));
			int min = shortdistance / 80;
			if (min == 0)
				min = 1;
			g2.drawString("路线距离为" + shortdistance + "米，步行大约需" + min + "分钟",
					short_X_x + 35, short_X_y + 42);
		} else if (map.isCaculating == 2) {
			for (int i = 0; i < shortLines.size() - 1; i++) {
				System.out.println(shortLines.get(i).getPointX() + "???"
						+ shortLines.get(i).getPointY());
				g2.setColor(Color.RED);
				float lineWidth = 5.0f;
				((Graphics2D) g2).setStroke(new BasicStroke(lineWidth));
				g2.drawLine(shortLines.get(i).getPointX(), shortLines.get(i)
						.getPointY(), shortLines.get(i + 1).getPointX(),
						shortLines.get(i + 1).getPointY());
			}
			pointInfoShortX.setIcon(new ImageIcon(getClass().getResource(
					"/images/x.png")));
			Image img = pointInfoShortX.getIcon().getImage();
			short_X_x = shortLines.get(0).getPointX() + 15;
			short_X_y = shortLines.get(0).getPointY() + 15;
			pointInfoShortX.setPointX(short_X_x);
			pointInfoShortX.setPointY(short_X_y);
			g2.drawImage(img, short_X_x, short_X_y, null);

			pointDistence.setIcon(new ImageIcon(getClass().getResource(
					"/images/dialoglong1.png")));
			Image img1 = pointDistence.getIcon().getImage();
			pointDistence.setPointX(short_X_x);
			pointDistence.setPointY(short_X_y);
			g2.drawImage(img1, short_X_x + 20, short_X_y + 20, null);
			g2.setColor(Color.DARK_GRAY);
			float lineWidth = 3.0f;
			((Graphics2D) g2).setStroke(new BasicStroke(lineWidth));
			int min = shortdistance / 80;
			if (min == 0)
				min = 1;
			g2.drawString("路线距离为" + shortdistance + "米，步行大约需" + min + "分钟",
					short_X_x + 35, short_X_y + 42);
		}
//起始点
		if (marks.size() > 0) {
			for (int i = 0; i < marks.size(); i++) {
				if (marks.get(i).getType() == MARK1) {
					marks.get(i).setIcon(
							new ImageIcon(getClass().getResource(
									"/images/begin.png")));
				} else if (marks.get(i).getType() == MARK2)
					marks.get(i).setIcon(
							new ImageIcon(getClass().getResource(
									"/images/end.png")));
				Image img = marks.get(i).getIcon().getImage();
				g2.drawImage(img, marks.get(i).getPointX() - 10, marks.get(i)
						.getPointY() - 50, null);

			}
		}
//显示具体楼栋
		if (!information_str.equals("")) {

			g2.setColor(Color.WHITE);
			float lineWidth = 4.0f;
			((Graphics2D) g2).setStroke(new BasicStroke(lineWidth));

			g2.drawImage(
					new ImageIcon(getClass().getResource("/images/dialog.png"))
							.getImage(), information_x + 10,
					information_y - 40, null);
			g2.drawString(information_str, information_x + 24,
					information_y - 22);
			information_str = "";
		}
		
		

		g2.dispose();

	}

}
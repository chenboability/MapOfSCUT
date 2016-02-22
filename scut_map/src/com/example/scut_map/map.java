package com.example.scut_map;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import com.example.scut_map.ImagePanel;
import com.example.scut_map.topUI;
import com.example.scut_map.arithmetic.Find;
import com.example.scut_map.arithmetic.suanfa3;

public class map extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point start = new Point(300, 150);
	private int width = 600;
	private int height = 400;

	double valueOfSliderd;
	int valueOfSlider;
	int hImage;
	int wImage;
	int newHImage;
	int newWImage;
	int x;
	int y;
	int countPointX;
	int countPointY;
	int isFirstPoint = 0;
	public static int isCounting = 0;
	public static int isCaculating = 0;
	int fWImage, fHImage;
	int beginX, beginY, endX, endY;
	public int mouseState;// 0 是默认 ，1 是手型，2是测量 ,3是标记
	int startPoint = 0;
	int endPoint = 0;
	int startPointMark = -1, endPointMark = -1;
	int X_drag = 0, Y_drag = 0;
	int X_drag1 = 0, Y_drag1 = 0;
	public static ArrayList searchMark = new ArrayList();
	public static int searchMarkId = -1;

	ArrayList<Integer> testsall = new ArrayList<>();
	JLabel ji = new JLabel(new ImageIcon(getClass()
			.getResource("/images/redPoint.gif")));

	JTextArea jta = new JTextArea();
	JFrame jf = new JFrame("scut_map");
	Box b1 = new Box(BoxLayout.X_AXIS);
	Box b2 = new Box(BoxLayout.Y_AXIS);
	JSlider js = new JSlider(1, -100, 100, 0);
	public ImagePanel ip;

	JTextArea search = new JTextArea("请输入要查找的地点");
	JButton searchbn = new JButton("搜索");
	JButton full = new JButton("全屏");
	JToolBar bar = new JToolBar("工具箱");
	JPanel j1 = new JPanel();
	Box j3 = new Box(BoxLayout.Y_AXIS);

	suanfa3 suanfa3 = new suanfa3();
	Find find;
	public topUI lay;
	JLayer<JComponent> layers;
	JLabel up = new JLabel(new ImageIcon(getClass().getResource(
			"/images/up.gif")));
	JLabel down = new JLabel(new ImageIcon(getClass().getResource(
			"/images/down.gif")));
	JLabel left = new JLabel(new ImageIcon(getClass().getResource(
			"/images/left.gif")));
	JLabel right = new JLabel(new ImageIcon(getClass().getResource(
			"/images/right.gif")));
	JComboBox<String> comboBox = new JComboBox<String>();

	public int getMouseState() {
		return mouseState;
	}

	public void setMouseState(int mouseState) {
		this.mouseState = mouseState;
	}

	void init() {

		search.setFont(new Font("楷书", Font.BOLD, 16));
		searchbn.setIcon(new ImageIcon(getClass().getResource(
				"/images/search.png")));

		up.setVisible(false);
		down.setVisible(false);
		left.setVisible(false);
		right.setVisible(false);

		lay = new topUI(suanfa3);
		jta.setVisible(false);

		lay.setBasePoint_x(0);
		lay.setBasePoint_y(0);
		ImageIcon icon = new ImageIcon(getClass().getResource(
				"/images/scut_map.jpg"));
		hImage = icon.getIconHeight();
		wImage = icon.getIconWidth();
		fHImage = hImage;
		fWImage = wImage;
		ip = new ImagePanel(icon);
		ip.setPreferredSize(new Dimension(wImage, hImage));
		ip.setBackground(Color.DARK_GRAY);
		ip.setLayout(null);

		search.setText("请输入要查找的地点");
		search.setColumns(20);
		search.setRows(1);

		jta.setRows(4);

		find = new Find(lay.points);
		// 工具箱事件

		Action screenshot = new AbstractAction("截图", new ImageIcon(getClass()
				.getResource("/images/screenshot.png"))) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
				  CameraJFrame cf = new CameraJFrame();
				  cf.setAlwaysOnTop(true); cf.setUndecorated(true);
				  cf.setVisible(true);
			}

		};

		Action measurement = new AbstractAction("测距", new ImageIcon(getClass()
				.getResource("/images/measurement.png"))) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				lay.lines.clear();
				lay.counts.clear();
				lay.dinstances.clear();
				lay.distance = 0;
				Image image = new ImageIcon(getClass().getResource(
						"/images/abc.png")).getImage();
				ip.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
						image, new Point(0, 0), null));
				mouseState = 2;
				isFirstPoint = 1;
				isCounting = 1;

			}
		};
		Action about = new AbstractAction("关于我们", new ImageIcon(getClass()
				.getResource("/images/about.png"))) {

			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null,
						"华南理工大学大学城校区桌面版地图！有疑问请咨询陈渤、林佳钦、刘蔚旻！");
			}
		};

		search.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				search.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
			}

		});
		searchbn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!search.getText().toString().equals("")) {
					lay.isSearching = 1;
					comboBox.removeAllItems();
					searchMark = find.getpoint(search.getText().toString());
					for (int i = 0; i < searchMark.size(); i++) {

						int markid = (int) searchMark.get(i);
						if (markid == -1) {
							JOptionPane.showMessageDialog(null, "无法搜索，请重新输入！");
							comboBox.setVisible(false);
							break;
						}
						comboBox.addItem(lay.points.get(markid).getMessage());
						comboBox.setVisible(true);
						j3.setSize(220, 50);
					}

					ip.repaint();
				}

			}
		});

		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<?> cb = (JComboBox<?>) e.getSource();
				String petName = (String) cb.getSelectedItem();
				if (petName != null) {
					for (int i = 0; i < lay.points.size(); i++)
						if (lay.points.get(i).getMessage().equals(petName)) {
							searchMarkId = lay.points.get(i).getMark();
				
							ji.setLocation(lay.points.get(map.searchMarkId)
									.getPointX()-42, lay.points.get(map.searchMarkId)
									.getPointY()-48);
							ji.setSize(100, 100);
							ip.add(ji);

							ip.repaint();
							break;
						}
				}
			}
		});

		// 全屏按钮
		full.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				GraphicsEnvironment ge = GraphicsEnvironment
						.getLocalGraphicsEnvironment();
				// 通过调用GraphicsEnvironment的getDefaultScreenDevice方法获得当前的屏幕设备了
				GraphicsDevice gd = ge.getDefaultScreenDevice();
				if (full.getText().equals("全屏")) {
					// 全屏设置
					gd.setFullScreenWindow(jf);
					full.setText("退出全屏");
				} else {
					jf.dispose();
					jf.setUndecorated(false);
					jf.setVisible(false);
					jf.getGraphicsConfiguration().getDevice()
							.setFullScreenWindow(null);
					jf.setVisible(true);
					full.setText("全屏");
				}
			}
		});

		ip.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {

				X_drag += e.getX() - beginX;
				Y_drag += e.getY() - beginY;

				if (isFirstPoint != 1 && isFirstPoint != 2) {
					ip.setCursor(Cursor
							.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					mouseState = 0;
				}
				for (int i = 0; i < lay.points.size(); i++) {
					lay.points.get(i).setFpointX(lay.points.get(i).getPointX());
					lay.points.get(i).setFpointY(lay.points.get(i).getPointY());
				}
				for (int i = 0; i < lay.marks.size(); i++) {
					lay.marks.get(i).setFpointX(lay.marks.get(i).getPointX());
					lay.marks.get(i).setFpointY(lay.marks.get(i).getPointY());
				}
				for (int i = 0; i < lay.shortLines.size(); i++) {
					lay.shortLines.get(i).setFpointX(
							lay.shortLines.get(i).getPointX());
					lay.shortLines.get(i).setFpointY(
							lay.shortLines.get(i).getPointY());
				}
				for (int i = 0; i < lay.dinstances.size(); i++) {
					lay.dinstances.get(i).setFpointX(
							lay.dinstances.get(i).getPointX());
					lay.dinstances.get(i).setFpointY(
							lay.dinstances.get(i).getPointY());
				}
				for (int i = 0; i < lay.counts.size(); i++) {
					lay.counts.get(i).setFpointX(lay.counts.get(i).getPointX());
					lay.counts.get(i).setFpointY(lay.counts.get(i).getPointY());
				}
				for (int i = 0; i < lay.lines.size(); i++) {
					lay.lines.get(i).setFpointX(lay.lines.get(i).getPointX());
					lay.lines.get(i).setFpointY(lay.lines.get(i).getPointY());
				}
			
				lay.pointInfoShortX.setFpointX(lay.pointInfoShortX.getPointX());
				lay.pointInfoShortX.setFpointY(lay.pointInfoShortX.getPointY());
				lay.pointSearch.setFpointX(lay.pointSearch.getPointX());
				lay.pointSearch.setFpointY(lay.pointSearch.getPointY());
				lay.pointDistence.setFpointX(lay.pointDistence.getPointX());
				lay.pointDistence.setFpointY(lay.pointDistence.getPointY());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				beginX = e.getX();
				beginY = e.getY();
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				int x1 = e.getX();
				int y1 = e.getY();
				System.out.println(x1 + ":" + y1);
				switch (mouseState) {
				case 0:
					if (isCounting == 2) { // 鼠标正常的点击,目的是为了点击x退出测量
						if (Math.abs(x1 - lay.getPoint_X_x()) < 13
								&& Math.abs(y1 - lay.getPoint_X_y()) < 13) {
							lay.lines.clear();
							lay.counts.clear();
							lay.dinstances.clear();
							lay.point_X_x = 0;
							lay.point_X_y = 0;
							isCounting = 0;
						}
					}
					if (isCaculating == 2) {// 鼠标正常的点击,目的是为了点击x退出最短路径

						if ((Math.abs(x1 - lay.pointInfoShortX.getPointX()) < 13)
								&& (Math.abs(y1
										- lay.pointInfoShortX.getPointY()) < 13)) {
							lay.shortLines.clear();
							lay.marks.clear();
							isCaculating = 0;
							startPoint = 0;
							endPoint = 0;
							lay.short_X_x = 0;
							lay.short_X_y = 0;
							lay.shortdistance = 0;
						}
					}
					boolean Flag1 = false;
					int whatI = -1;
					for (int i = 0; i < lay.points.size(); i++) {

						if (Math.abs(x1 - lay.points.get(i).getPointX()) < 10
								&& Math.abs(y1 - lay.points.get(i).getPointY()) < 10) {
							Flag1 = true;
							whatI = i;
							break;
						}
					}
					if (Flag1 && lay.points.get(whatI).getType() == 1) {
						ip.setCursor(Cursor
								.getPredefinedCursor(Cursor.HAND_CURSOR));
						Object[] possibleValues = { "起点", "终点" };
						Object selectedValue = null;
						if (startPoint == 0 && endPoint == 0) {
							selectedValue = JOptionPane.showInputDialog(null,
									"设置起止点", "把该点设置为：",
									JOptionPane.INFORMATION_MESSAGE, null,
									possibleValues, possibleValues[0]);
							if (selectedValue.equals("起点")) {
								startPoint = 1;
								startPointMark = lay.points.get(whatI)
										.getMark();
								lay.marks.add(new PointInfo(lay.points.get(
										whatI).getPointX(), lay.points.get(
										whatI).getPointY() - 30, null, null, 0,
										topUI.MARK1));
							} else if (selectedValue.equals("终点")) {
								endPoint = 1;
								endPointMark = lay.points.get(whatI).getMark();
								lay.marks.add(new PointInfo(lay.points.get(
										whatI).getPointX(), lay.points.get(
										whatI).getPointY() - 30, null, null, 0,
										topUI.MARK2));
							}
							if (startPoint == 1 && endPoint == 1)
								isSetStartAndEnd();
						} else if (startPoint == 0 && endPoint == 1) {
							selectedValue = JOptionPane.showInputDialog(null,
									"设置起止点", "把该点设置为：",
									JOptionPane.INFORMATION_MESSAGE, null,
									possibleValues, possibleValues[0]);
							if (selectedValue.equals("起点")) {
								startPoint = 1;
								startPointMark = lay.points.get(whatI)
										.getMark();
								lay.marks.add(new PointInfo(lay.points.get(
										whatI).getPointX(), lay.points.get(
										whatI).getPointY() - 30, null, null, 0,
										topUI.MARK1));
							} else if (selectedValue.equals("终点")) {
								JOptionPane.showMessageDialog(null, "终点已设置");
							}
							if (startPoint == 1 && endPoint == 1)
								isSetStartAndEnd();
						} else if (startPoint == 1 && endPoint == 0) {
							selectedValue = JOptionPane.showInputDialog(null,
									"设置起止点", "把该点设置为：",
									JOptionPane.INFORMATION_MESSAGE, null,
									possibleValues, possibleValues[1]);
							if (selectedValue.equals("起点")) {
								JOptionPane.showMessageDialog(null, "起点已设置");
							} else if (selectedValue.equals("终点")) {
								endPoint = 1;
								endPointMark = lay.points.get(whatI).getMark();
								lay.marks.add(new PointInfo(lay.points.get(
										whatI).getPointX(), lay.points.get(
										whatI).getPointY() - 30, null, null, 0,
										topUI.MARK2));
							}
							if (startPoint == 1 && endPoint == 1)
								isSetStartAndEnd();
						} else if (startPoint == 1 && endPoint == 1) {
							JOptionPane.showMessageDialog(null, "起始点已设置");
						}
					}
					ip.repaint();
					break;
				case 1:
					break;
				case 2:
					if (isCounting == 1) { // 测量时 isCounting = 1,(测量结束isCounting
						// = 2,没有测量isCounting = 0)
						lay.lines.add(new PointInfo(x1, y1, null, null, 0,
								topUI.COUNT));
						if (e.getClickCount() == 1) { // 单击

							if (isFirstPoint == 1) {// 第一点
								PointInfo pointInfo = new PointInfo(x1, y1,
										null, null, 0, topUI.COUNT);
								lay.counts.add(pointInfo);
								isFirstPoint = 2;
								countPointX = x1;
								countPointY = y1;

							} else if (isFirstPoint == 2) { // 以后的点
								PointInfo pointInfo = new PointInfo(x1, y1,
										null, null, 0, topUI.COUNT);
								lay.counts.add(pointInfo);
								isFirstPoint = 2;

								lay.setIsDrawLine(1);
								countPointX = x1;
								countPointY = y1;
							}
						} else if (e.getClickCount() == 2) { // 双击
							isFirstPoint = 0;
							mouseState = 0;
							isCounting = 2; // 表明测量结束，进入停滞区
							ip.setCursor(Cursor
									.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							lay.setIsDrawLine(2);
						}
						ip.repaint();
					}
					break;

				}

			}
		});

		ip.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				if (lay.counts.size() <= 0) {

					ip.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					mouseState = 1;
					endX = e.getX();
					endY = e.getY();
					lay.pointInfoShortX.setPointX(lay.pointInfoShortX
							.getFpointX() + (endX - beginX));
					lay.pointInfoShortX.setPointY(lay.pointInfoShortX
							.getFpointY() + (endY - beginY));
					lay.pointSearch.setPointX(lay.pointSearch.getFpointX()
							+ (endX - beginX));
					lay.pointSearch.setPointY(lay.pointSearch.getFpointY()
							+ (endY - beginY));
					lay.pointDistence.setPointX(lay.pointDistence.getFpointX()
							+ (endX - beginX));
					lay.pointDistence.setPointY(lay.pointDistence.getFpointY()
							+ (endY - beginY));
					for (int i = 0; i < lay.points.size(); i++) {

						lay.points.get(i).setPointX(
								lay.points.get(i).getFpointX()
										+ (endX - beginX));
						lay.points.get(i).setPointY(
								lay.points.get(i).getFpointY()
										+ (endY - beginY));
					}
					if (lay.marks.size() > 0) {
						for (int i = 0; i < lay.marks.size(); i++) {

							lay.marks.get(i).setPointX(
									lay.marks.get(i).getFpointX()
											+ (endX - beginX));
							lay.marks.get(i).setPointY(
									lay.marks.get(i).getFpointY()
											+ (endY - beginY));
						}
					}
					if (lay.shortLines.size() > 0) {
						for (int i = 0; i < lay.shortLines.size(); i++) {

							lay.shortLines.get(i).setPointX(
									lay.shortLines.get(i).getFpointX()
											+ (endX - beginX));
							lay.shortLines.get(i).setPointY(
									lay.shortLines.get(i).getFpointY()
											+ (endY - beginY));
						}
					}
					if (lay.counts.size() > 0) {
						for (int i = 0; i < lay.counts.size(); i++) {

							lay.counts.get(i).setPointX(
									lay.counts.get(i).getFpointX()
											+ (endX - beginX));
							lay.counts.get(i).setPointY(
									lay.counts.get(i).getFpointY()
											+ (endY - beginY));
						}
					}
					if (lay.lines.size() > 0) {
						for (int i = 0; i < lay.lines.size(); i++) {

							lay.lines.get(i).setPointX(
									lay.lines.get(i).getFpointX()
											+ (endX - beginX));
							lay.lines.get(i).setPointY(
									lay.lines.get(i).getFpointY()
											+ (endY - beginY));
						}
					}
					if (lay.dinstances.size() > 0) {
						for (int i = 0; i < lay.dinstances.size(); i++) {

							lay.dinstances.get(i).setPointX(
									lay.dinstances.get(i).getFpointX()
											+ (endX - beginX));
							lay.dinstances.get(i).setPointY(
									lay.dinstances.get(i).getFpointY()
											+ (endY - beginY));
						}
					}
					

					Dimension screenSize = Toolkit.getDefaultToolkit()
							.getScreenSize(); // 获取屏幕的尺寸
					int screenWidth = screenSize.width;
					int screenHeight = screenSize.height;
					
					ji.setLocation(lay.points.get(map.searchMarkId)
							.getPointX()-42, lay.points.get(map.searchMarkId)
							.getPointY()-48);
					ji.setSize(100, 100);

					// up
					if (lay.points.get(searchMarkId).getPointY() < 0)
						up.setVisible(true);
					else
						up.setVisible(false);
					// down
					if (lay.points.get(searchMarkId).getPointY() > screenHeight)
						down.setVisible(true);
					else
						down.setVisible(false);
					// left
					if (lay.points.get(searchMarkId).getPointX() < 0)
						left.setVisible(true);
					else
						left.setVisible(false);
					// right
					if (lay.points.get(searchMarkId).getPointX() > screenWidth)
						right.setVisible(true);
					else
						right.setVisible(false);
					ip.repaint();

				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				int x1 = e.getX();
				int y1 = e.getY();
				boolean Flag = false;
				int whatI = -1;
				for (int i = 0; i < lay.points.size(); i++) {

					if (Math.abs(x1 - lay.points.get(i).getPointX()) < 10
							&& Math.abs(y1 - lay.points.get(i).getPointY()) < 10) {
						Flag = true;
						whatI = i;

					}
				}
				if (Flag) {
					if (lay.points.get(whatI).getType() == 1) {
						ip.setCursor(Cursor
								.getPredefinedCursor(Cursor.HAND_CURSOR));
						jta.setVisible(true);
						jta.setBackground(Color.gray);
						jta.setForeground(Color.WHITE);
						jta.setFont(new Font("楷书", Font.BOLD, 15));
						jta.setText("地点：" + lay.points.get(whatI).getMessage()
								+ "\n描述：" + lay.points.get(whatI).getDetail());
						lay.showInformation(x1, y1, lay.points.get(whatI)
								.getMessage());
						ip.repaint();
					}
				}

				else {
					jta.setVisible(false);
					if (ip.getCursor().getType() == Cursor.HAND_CURSOR) {
						switch (getMouseState()) {
						case 0:
							ip.setCursor(Cursor
									.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							break;
						case 2:
							ip.setCursor(Toolkit.getDefaultToolkit()
									.createCustomCursor(
											new ImageIcon(getClass()
													.getResource(
															"/images/abc.png"))
													.getImage(),
											new Point(0, 0), null));
							break;
						case 3:
							ip.setCursor(Toolkit
									.getDefaultToolkit()
									.createCustomCursor(
											new ImageIcon(
													getClass()
															.getResource(
																	"/images/markbig.png"))
													.getImage(),
											new Point(0, 0), null));
							break;

						}

					}
					jta.setText("");
				}

				repaint();
			}
		});

		/*
		 * j1.add(jsearch); j1.add(search); j1.add(searchbn);
		 * 
		 * j3.add(j1); j3.add(comboBox);
		 */

		// j1.add(jsearch);
		// j1.add(search);
		// j1.add(searchbn);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		
		comboBox.setVisible(false);
		j3.setLocation(screenWidth/2-220, 20);
		j3.setSize(220, 20);
		
		searchbn.setLocation(screenWidth/2+10, 20);
		searchbn.setSize(80, 23);
		full.setLocation(screenWidth/2+100, 20);
		full.setSize(80, 23);
		bar.setLocation(screenWidth/2+190, 20);
		bar.setSize(100, 28);
		
		down.setLocation(screenWidth/2, screenHeight-190);
		down.setSize(100, 100);
		up.setLocation(screenWidth/2, 60);
		up.setSize(100, 100);
		left.setLocation(20, screenHeight/2);
		left.setSize(100, 100);
		right.setLocation(screenWidth-120, screenHeight/2);
		right.setSize(100, 100);

		j3.add(search);
		j3.add(comboBox);
		

		bar.add(measurement);
		bar.add(screenshot);
		bar.add(about);

		// ip.add(jsearch);

		ip.add(j3);
		ip.add(searchbn);
		ip.add(full);
		ip.add(bar);

		ip.add(down);
		ip.add(up);
		ip.add(left);
		ip.add(right);



		layers = new JLayer<>(ip, lay);

		b1.add(layers);

		b2.add(b1);
		b2.add(jta);

		jf.add(b2);

		jf.setSize(200, 200);
		jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
		jf.pack();
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private int getjsValue() {

		return js.getValue();
	}

	private void isSetStartAndEnd() {
		isCaculating = 1;
		suanfa3.setstart(startPointMark);
		suanfa3.calculate();
		suanfa3.setEnd(endPointMark);
		System.out.println("起点：" + startPointMark + "终点" + endPointMark);
		ip.repaint();
	}

	public static void main(String args[]) {
		new map().init();
	}

}
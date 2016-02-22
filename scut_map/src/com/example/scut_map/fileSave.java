package com.example.scut_map;

import java.awt.*; //Ϊ��ʹ�ò��ֹ�����   
import java.awt.event.*;//���������¼�   
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*; //���µ�GUI���   

import java.io.*; //��д�ļ���   
import java.util.Calendar;

public class fileSave {

	Container c;
	BufferedImage image;
	private File f;
	private JFileChooser fc;
	private int flag;

	public fileSave(Container c, BufferedImage image) {
		fc = new JFileChooser();
		this.c = c;
		this.image = image;
		fc.setFileSelectionMode(JFileChooser.SAVE_DIALOG
				| JFileChooser.DIRECTORIES_ONLY);
		readFile();
	}

	private void readFile() // �����ļ�
	{
		fc.setDialogTitle("Save File");
		// ���ｫ��ʾ�����ļ��ĶԻ���
		try {
			flag = fc.showSaveDialog(c);
		} catch (HeadlessException he) {
		}

		// �������ȷ����ť�����ø��ļ���
		if (flag == JFileChooser.APPROVE_OPTION) {
			// ���������Ҫ������ļ�
			f = fc.getSelectedFile();
			String filestr = f.getAbsolutePath() +"\\"+ getTime() + ".jpg";
			File file = new File(filestr);
			try {
				if (!file.exists()) {
					file.createNewFile();
				}
				ImageIO.write(image, "JPG", file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
		}
	}

	long getTime() {
		long time = 0;
		Calendar currCalendar = Calendar.getInstance();
		currCalendar.setTimeInMillis(System.currentTimeMillis());
		time = currCalendar.getTimeInMillis();
		return time;
	}

	
	
	public void cutScreen() {
		String name;
		Graphics g = image.getGraphics();
		c.paint(g);
		g.dispose();
		JFileChooser jfc = new JFileChooser();
/*		jfc.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.isDirectory()
						|| f.getName().toLowerCase().endsWith(".jpg");
			}

			public String getDescription() {
				return "*.jpg";
			}
		});*/
		int type = jfc.showSaveDialog(null);
		if (type == 0) {
			File file = jfc.getSelectedFile();
			name = file.getName().toLowerCase();
			if (!name.endsWith("jpg")) {
				String path = file.getAbsolutePath();
				file = new File(path + ".jpg");
				for (int i = 0; file.exists(); i++) {
					file = new File(path + "(" + i + ").jpg");
				}
			}
			try {
				if (!file.exists()) {
					file.createNewFile();
				}
				ImageIO.write(image, "JPG", file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

}

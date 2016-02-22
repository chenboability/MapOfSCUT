package com.example.scut_map.arithmetic;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

import com.example.scut_map.PointInfo;
import com.example.scut_map.map;
import com.example.scut_map.topUI;

public class Fileinfo {
	String fileName = "ins1.dat";
	RandomAccessFile file;
	// InputStream in;
	long pos;
	Scanner cin = new Scanner(System.in);

	public Fileinfo() {
		try {
			file = new RandomAccessFile(fileName, "rw");
			try {
				pos = file.getFilePointer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readin() {
		int t;
		while (true) {
			t = cin.nextInt();
			try {
				file.writeInt(t);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (t == -1)
				break;
		}
	}

	public ArrayList<Integer> output() {
		 System.out.println("over!!!");
		 ArrayList<Integer> listsTest = new ArrayList<>();
		int a, b;
		// file.write("caonima");
		try {
			file.seek(pos);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while ((a = file.readInt()) != -1) {
				b = file.readInt();
				System.out.println(a+"?"+b);
				listsTest.add(a);
				listsTest.add(b);
				/*int ax = lay.points.get(a).getPointX();
				int ay = lay.points.get(a).getPointY();
				int bx = lay.points.get(b).getPointX();
				int by = lay.points.get(b).getPointY();
				System.out.println(ax+":11"+ay+":"+bx+":"+by);
				lay.tests.add(new PointInfo(ax, ay, null, null, 0, 0));
				lay.tests.add(new PointInfo(bx, by, null, null, 0, 0));*/
			//	map.ip.repaint();
				//new map().draw(a,b);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listsTest;
	}


}

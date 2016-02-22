package com.example.scut_map.arithmetic;

import java.util.ArrayList;
import java.util.Scanner;

import com.example.scut_map.PointInfo;

public class Find {
	LCS lcs;
	String t;
	String s;
	ArrayList<PointInfo> point;
	@SuppressWarnings("rawtypes")
	ArrayList[] q;

	public Find(ArrayList<PointInfo> point) {
		lcs = new LCS();
		this.point = point;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList getpoint(String t) {
		this.t = t;
		int maxnum = -1;
		int maxs = -1;
		q = new ArrayList[11];
		for (int i = 0; i < 11; i++) {
			q[i] = new ArrayList();
		}
		for (int i = 0; i < point.size(); i++) {
			if (point.get(i).type != 1) {
				continue;
			}
			s = point.get(i).message + point.get(i).getDetail();
			lcs.set(t, s);
			int res = lcs.solve();
			// q[res]=new ArrayList();
			q[res].add(i);
			if (res > maxs && res != 0) {
				maxs = lcs.solve();
				maxnum = i;
			}
		}

		ArrayList qq = new ArrayList();
		if (maxs != -1) {
			for (int i = maxs; i > 0; i--) {
				for (int j = 0; j < q[i].size(); j++) {
					qq.add(q[i].get(j));
				}
			}
		} else
			qq.add(-1);
		return qq;
	}

}

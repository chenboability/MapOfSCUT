package com.example.scut_map.arithmetic;


import java.awt.Color;

import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import com.example.scut_map.PointInfo;

class node{
	int s;
	int d;
	node(int s,int d){
		this.s=s;
		this.d=d;
	}
	node(){
		
	}
}
public class suanfa3 {
	int n=605;     //点数     ,n在初始化时读入，为point的点数
	int d[]=new int[n];        //最短距离
    int pre[]=new int[n];      //父节点
    ArrayList<PointInfo>point;
    String fileName1="ins1.dat";
    String s_FilePath="ins2.dat";
    RandomAccessFile file1;
    InputStream is = null;
    DataInputStream dis = null;
    FileOutputStream fos = null;
    DataOutputStream dos = null;
    int inf=1000005;
    static int start;
	static int end;
    long pos1;
    int maxn=1005;
    int temp = -1;
    int first[]=new int[maxn];
    int nxt[]=new int[maxn];
    int to[]=new int[maxn];
    int cost[]=new int[maxn];
    int e;
    PriorityQueue<node>q;
    ArrayList<PointInfo> pp = new ArrayList<PointInfo>();
    public void setstart(int start) {
 		this.start = start;
 		System.out.println("start");
 	}

 	public void setEnd(int end) {
 		this.end = end;
 		temp = end;
 		System.out.println("end");
 	}

 	public int getEnd() {
 		return end;
 	}

 	public int getStart() {
 		return start;
 	}
     
    void addedge(int u,int v,int c){
    	to[e]=v;
    	cost[e]=c;
    	nxt[e]=first[u];
    	first[u]=e++;
    }
    public void calculate(){
    	for(int i=0;i<n;i++){
    		d[i]=inf;
    		pre[i]=-1;
    	}
    	d[start]=0;
		Comparator<node> compare=new Comparator<node>(){

			@Override
			public int compare(node o1, node o2) {
				// TODO Auto-generated method stub
				if(o1.d<o2.d)
				return 1;
				else return 0;
			}
    		
    	};
    	q=new PriorityQueue<node>(605,compare);
    	q.add(new node(start,d[start]));
    	while(!q.isEmpty()){
    		System.out.println(q.size());
    		node temp=q.peek();
    		q.remove();
    		int u=temp.s;
    		int dis=temp.d;
    		if(d[u]<dis)continue;
    		for(int i=first[u];i!=-1;i=nxt[i]){
    			int c=cost[i];
    			int v=to[i];
    			if(d[u]+c<d[v]){
    				d[v]=d[u]+c;
    				pre[v]=u;
    				q.add(new node(v,d[v]));
    			}
    		}
    	}
    }
    public suanfa3(){
   	 inital();
    }
    
    void inital(){
    	e=0;
    	for(int i=0;i<1005;i++){
    		first[i]=-1;
    	}
   	 try{
 			
   		file1=new RandomAccessFile(fileName1, "rw");
   		
 		}catch(Exception e){
 			e.printStackTrace();
 		}
   	 try {
   		 pos1=file1.getFilePointer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	 
   	 try{
			if(!new File(s_FilePath).exists()){
				new File(s_FilePath).createNewFile();
			}
			 
             
		}catch(Exception e){
			e.printStackTrace();
		}
   	 
   	 readpoint();   //读入点的位置
   	 
   	
   	 readconnect();// 读入点之间连通文件
   	 
    }
    
    
    void readconnect(){
   	 
   	    int u,v = 0;
    	try {
			while((u=file1.readInt())!=-1) {
				try {
					v=file1.readInt();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    double d2=(Math.pow(point.get(u).getPointX()-point.get(v).getPointX(), 2)+
			    		Math.pow(point.get(u).getPointY()-point.get(v).getPointY(), 2));
			    int cc=(int) Math.sqrt(d2);
			    addedge(u,v,cc);
			    addedge(v,u,cc);
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			file1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    public ArrayList<PointInfo> readpoint(){
   	 point=new ArrayList<PointInfo>();
   	 int x,y,mark,type;
   	 String s,s2;
   	 int i=0;
   	 n=0;
   	 try {
			is = new FileInputStream("ins2.dat");
			
			 dis = new DataInputStream(is);
			 
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
   	 try {
			while(true){
				 x=dis.readInt();
				 if(x==-1)break;
				 y=dis.readInt();
				 s=dis.readUTF();
				 s2=dis.readUTF();
				 mark=dis.readInt();
				 type=dis.readInt();
				 
				// PointInfo pointInfo = new PointInfo(x, y,mark,s,s2);
				 point.add(new PointInfo(x, y,s,s2,mark,type));
				 i++;
				 n++;
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	 

   	 try {
				if(is!=null)
				is.close();
				if(dis!=null)
			    dis.close();
			    if(fos!=null)
			    fos.close();
			    if(dos!=null)
			    dos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   	 return point;
    }
    
    
    public ArrayList<PointInfo> getpre() {
		System.out.println("end = " + end + " strat = " + start);
		while (end != -1 && start != -1) {
			pp.add(new PointInfo(point.get(end).getPointX(), point.get(end)
					.getPointY(), null, null, 0, 0));
			end = pre[end];
		}
		end = temp;
		return pp;
	}
    
    
    
   public void draw(Graphics g,int end){ 
   	//Graphics g2=(Graphics2D)g;
   	while(end!=-1&&pre[end]!=-1){
   		int x1=point.get(end).getPointX();
   	    int y1=point.get(end).getPointY();
   		int x2=point.get(pre[end]).getPointX();
   		int y2=point.get(pre[end]).getPointY();
   		g.setColor(Color.RED);
   		g.drawLine(x1, y1, x2, y2);
   		end=pre[end];
   	}
   }
   /*
   public static void main(String args[]){
   	Scanner cin=new Scanner(System.in);
   	suanfa3 dijkstra=new suanfa3();
   	while(true){
   	start=cin.nextInt();
   	end=cin.nextInt();
   	dijkstra.calculate(start);
   	System.out.println(dijkstra.d[end]);     //通过类名调用
   	}
   }
   */
}


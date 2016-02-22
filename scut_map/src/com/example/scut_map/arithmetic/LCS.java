package com.example.scut_map.arithmetic;

import java.util.Scanner;

public class LCS {
      char s[];
      char t[];
      int dp[][];
      int n,m;
      LCS(){
    	  
      }
      void set(String a,String b){
    	  s=a.toCharArray();
    	  t=b.toCharArray();
    	  n=s.length;
    	  m=t.length;
    	  dp=new int[n+1][m+1];
    	  dp[0][0]=0;
      }
      int max(int x,int y){
    	  if(x>=y)return x;
    	  else return y;
      }
      int solve(){
    	  for(int i=0;i<n;i++)
    		  for(int j=0;j<m;j++){
    			  if(s[i]==t[j]||(Character.isLetter(s[i])&&Character.isLetter(t[j])&&Character.toLowerCase(s[i])==Character.toLowerCase(t[j])))
    				  dp[i+1][j+1]=dp[i][j]+1;
    			  else dp[i+1][j+1]=max(dp[i][j+1],dp[i+1][j]);
    		  }
    	  return dp[n][m];
      }
      /*
      public static void main(String args[]){
    	  String a,b;
    	  Scanner cin=new Scanner(System.in);
    	  while(true){
    	  a=cin.nextLine();
    	  b=cin.nextLine();
    	  LCS lcs=new LCS(a,b);
    	  System.out.println(lcs.solve());
    	  System.out.println(lcs.n+" "+lcs.m);
    	  }
      }*/
}

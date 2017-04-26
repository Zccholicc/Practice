import java.util.*;
public class Calcudoku{
	private static int[][] board ;
	private static int[][] ans;
	private static HashMap<Integer,Integer> count;
	private static HashMap<Integer,Character> operat;
	private static HashMap<Integer,Integer> target;
	private static HashMap<Integer,ArrayList<Integer>> current=new HashMap<Integer,ArrayList<Integer>>();
	private static Scanner in;


	public static void printArray() {
	        for (int i = 0; i < 6; i++) {
	            for (int j = 0; j < 6; j++) {
					if(j==5){
						System.out.print(ans[i][j]);
					}
					else{
	                	System.out.print(ans[i][j] + " ");
					}
	            }
	            System.out.println();
	        }
	}
	public static void backTrace(int i,int j){
		if(i==5&&j==6){
			printArray();
			System.exit(0);
		}
		if(j==6){
			i++;
			j=0;
		}
		if(ans[i][j]==0){
			for(int k=1;k<=6;k++){
				int x=board[i][j];
				if(current.get(x)==null){
					current.put(x,new ArrayList<Integer>());
				}
				if(check(i,j,k)){
					ans[i][j]=k;
					current.get(x).add(k);
					if(check_ans(i,j)){
						backTrace(i,j+1);
					}
					ans[i][j]=0;
					current.get(x).remove(current.get(x).size()-1);
				}
			}
		}
		else{
			backTrace(i,j+1);
		}
	}
	private static boolean check_ans(int row,int line){
		int w=board[row][line];
        int val=0;
        if(current.get(w).size()==count.get(w)){
        	char o=operat.get(w);
        	for(Integer j:current.get(w)){
        		if(o=='+'){ val+=j;}
        		if(o=='*'){
        			if(val==0){
        				val=j;
        			}
        			else{
        				val*=j;
        			}
        		}
        		if(o=='-'){
        			if(val==0){
        				val=j;
        			}
        			else{
        				val-=j;
        			}
        		}
        	}
        	if(o=='/'){
        		int large=Math.max(current.get(w).get(0), current.get(w).get(1));
        		int small=Math.min(current.get(w).get(0), current.get(w).get(1));
        		return large==target.get(w)*small;
        	}
        	return target.get(w)==Math.abs(val);
        }
        return true;
	}
	 private static boolean check(int row, int line, int number) {
	        for (int i = 0; i < 6; i++) {
	            if (ans[row][i] == number || ans[i][line] == number) {
	                return false;
	            }
	        }
	        return true;
	 }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//acount for cages
		board=new int[6][6];
		ans =new int[6][6];
		count=new HashMap<>();
		operat=new HashMap<>();
		target=new HashMap<>();
		in = new Scanner(System.in);
		for(int i=0;i<6;i++){
			String line=in.nextLine();
			String[] li=line.split(" ");
			for(int j=0;j<li.length;j++){
				int thisp=Integer.parseInt(li[j]);
				board[i][j]=thisp;
				if(count.containsKey(thisp)){
					count.put(thisp,count.get(thisp)+1);
				}
				else{
					count.put(thisp,1);
				}
			}
		}
		String op=in.nextLine();
		String[] opera=op.split(" ");
		for(int i=0;i<opera.length;i++){
			if(opera[i].length()>1){
				int val=Integer.parseInt(opera[i].substring(0, opera[i].length()-1));
				if(!target.containsKey(i)){
					target.put(i, val);
				}
				char mod=opera[i].charAt(opera[i].length()-1);
				if(!operat.containsKey(i)){
					operat.put(i, mod);
				}
			}
			if(opera[i].length()==1){
				for(int n=0;n<6;n++){
					for(int m=0;m<6;m++){
						if(board[m][n]==i){
							ans[m][n]=Integer.parseInt(opera[i]);
						}
					}
				}
			}
		}
		backTrace(0,0);
		System.out.println("No solution.");
	}
}

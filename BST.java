import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.io.*;
public class BST implements WordCounter {
	private class TreeNode {
		WordFreq item;
		TreeNode left; // pointer to left subtree
		TreeNode right; // pointer to right subtree
		int N; //number of nodes in the subtree rooted at this TreeNode 
		TreeNode(){
		}
		private TreeNode(WordFreq item){
			this.item=item;
			right=null;
			left=null;
			this.N = 0;
		}
		private WordFreq  getData(){
			return item;
		}
		private void setData( WordFreq item){
			item= item;
		}
		public TreeNode getLeft() {
			return left;
		}
		public void setLeft(TreeNode left) {
			this.left = left;
		}
		public TreeNode getRight() {
			return right;
		}
		public void setRight(TreeNode right) {
			this.right = right;
		}
	}
	
	public static boolean putonroot=false;
	private TreeNode head ; 
	private Listt stopWWords  = new Listt();// list of stopwords
	public static boolean f=true;
	private int total_words;
	private int d_words;//number of total nodes 
	private boolean b;
	private PQ byfreq = new PQ();
	private int fr;
	WordFreq maxxx;
	//methods start  here
	
	//#void load(String filename)	
	public void load(String filename){
		BufferedReader reader = null;
		String fname=filename;
		// we start reading the file
		try{
			reader = new BufferedReader(new FileReader(new File(fname)));
			String line = reader.readLine();
			while (line != null) {
				char c ;
				String wordd="";
				String emptywordd="";
				int k=line.substring(0).length();
				//System.out.println(k);
				for (int i =0;i<k ;i++){
					c = line.charAt(i);
					if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')){
						wordd+=c;
						//System.out.println("word"+wordd);
						if (i==k-1){
							wordd=wordd.toUpperCase();
							//System.out.println("word "+wordd);
							if (!stopWWords.search(wordd)){
								insert(wordd);
							}
							wordd="";
						}
						continue;
					}
					
					if (wordd.compareTo(emptywordd)==0){
						continue;
					}
					else {
						wordd=wordd.toUpperCase();
						//System.out.println("word "+wordd);
						insert(wordd);
						wordd="";
					}
				}
				//read next line
				line = reader.readLine();
			}	
		}catch (IOException e) {
            //System.out.println	("Error reading line in file ...");
		}
	}
	
	//WordFreq search(String w)
	public WordFreq search(String w){
			return traverseInOrderhead(head,w);
	}
	private WordFreq traverseInOrderhead(TreeNode node,String aa) {
			if (node != null) {
				traverseInOrderhead(node.left,aa);
				if(aa.compareTo(node.getData().getWord()) == 0){
					int freqq=node.getData().getFreq();
					if (freqq> getMeanFrequency()){
						remove(aa);
						putonroot=true;
						insert (aa);
						putonroot=false;
						return new WordFreq(aa,freqq);
					}
						
				}
			}return null;
	} 
	
	//int getDistinctWords()
	public int getDistinctWords(){
		return d_words;
	}//done
	
	//int getFrequency(String w)
	public int getFrequency(String w){
			fr=0;
			return traverseInOrderfindfr(head,w.toUpperCase());
		}
	private int traverseInOrderfindfr(TreeNode node,String a) {
		if (node != null) {
			traverseInOrderfindfr(node.left,a);
			//System.out.println(a.compareTo(node.getData().getWord()) == 0);
			if(a.compareTo(node.getData().getWord()) == 0){
				fr=node.getData().getFreq();
				return fr;
			}
			traverseInOrderfindfr(node.right,a);
			
		}
		return fr;
		}
	
	//int getTotalWords()
	public int getTotalWords(){
		total_words=0;
		return traverseInOrderfortotalwords(head);
	}
	private int traverseInOrderfortotalwords(TreeNode node) {
		if (node != null) {
			traverseInOrderfortotalwords(node.left);
			total_words+=node.getData().getFreq();
			traverseInOrderfortotalwords(node.right);
		}
		return total_words;
	}//done

	//void addStopWord(String w)
	public void addStopWord(String w){
		stopWWords.insert(w.toUpperCase());
		//int f=stopWWords.size();
		//System.out.println(f);
	}//done
	
	//void REMOVEStopWord(String w)
	public void removeStopWord(String w){
		stopWWords.remove(w.toUpperCase());
		//int f=stopWWords.size();
		//System.out.println(f);
	}//done

	//void insert(String w)
	private boolean traverseInOrdersearcher(TreeNode node,String a) {
		
			if (node != null) {
				traverseInOrdersearcher(node.left,a);
				if(a.compareTo(node.getData().getWord()) == 0){
					int freqq=node.getData().getFreq();
					node.getData().setfreq(freqq+1);
					b=true;
					return true;
				}
				else{
					traverseInOrdersearcher(node.right,a);
					}
			}
			if (b!=true){
				return false;
				}
			else {
				return b;
			}
		}
	public void insert(String w){
		if (!stopWWords.search(w)){
			b=false;
			if (traverseInOrdersearcher(head,w.toUpperCase())){
				return;
				}
			else{
				d_words++;
				//System.out.println(d_words);
				head=insertR(head,new WordFreq(w.toUpperCase()));
			}
			
		}
	}
	private TreeNode insertR(TreeNode h, WordFreq item){
		
		if(h == null){
			return new TreeNode(item);
		}
		if(Math.random()*h.N < 1.0 || putonroot){
			return insertT(h,item);
		}
		if(h.item.key().compareTo(item.key()) > 0 ){
			h.left = insertR(h.left, item);
		}
		else{
			h.right = insertR(h.right, item);
		}
		h.N++;
		return h;
	}
	private TreeNode insertT(TreeNode h, WordFreq x){
		if(h == null){
			return new TreeNode(x);
		}
		if(h.item.key().compareTo(x.key()) > 0){
			h.left = insertT(h.left, x);
			h = rotR(h);
		}
		else{
			h.right = insertT(h.right, x);
			h = rotL(h);
		}
		h.N++;
		return h;
	}
	private TreeNode rotR(TreeNode h){
		TreeNode x = h.left; 
		h.left = x.right; 
		x.right = h; 
		return x;
	}
	private TreeNode rotL(TreeNode h ){
		TreeNode x = h.right; 
		h.right = x.left; 
		x.left = h; 
		return x;
	}//done
	
	//void remove(String w)
	public void remove(String w){
		TreeNode current = head;
        TreeNode parent = null;
		while (true) {
            if (current == null)
                return;
			if (current.getData().getWord().compareTo(w)==0){
				d_words--;
				break;}
            
			parent = current;
            if (current.getData().getWord().compareTo(w)<0)
                current = current.getRight();
            else
                current = current.getLeft();
        }
        TreeNode replace = null;         // node to replace with
        if (current.getLeft() == null)
            replace = current.getRight();
        else if (current.getRight() == null)
            replace = current.getLeft();
        else {
            TreeNode findCurrent = current.getRight();
            while (true) {
                if (findCurrent.getLeft() != null)
                    findCurrent = findCurrent.getLeft();
                else
                    break;
            }

            remove(findCurrent.getData().getWord());
            findCurrent.setLeft(current.getLeft());
            findCurrent.setRight(current.getRight());
            replace = findCurrent;
        }
        if (parent == null) { 
            head = replace;
        } else {
            if (parent.getLeft()==current)
                parent.setLeft(replace);

            if (parent.getRight()==current)
                parent.setRight(replace);
        }
    }
	//double getMeanFrequency()
	public double getMeanFrequency(){
		int dw=getDistinctWords();
		if (dw!=0){
			return (double) getTotalWords()/dw;
		}
		else{
			return 0.0;
		}
	}//done
	
	//printTreeAlphabetically(PrintStream stream)
	public void printTreeAlphabetically(PrintStream stream){
		traverseInOrder0(head,stream);
	}
	private void traverseInOrder0(TreeNode node,PrintStream stream) {
		if (node != null) {
			traverseInOrder0(node.left,stream);
			stream.println(node.getData().toString());
			traverseInOrder0(node.right,stream);
		}
	}//done
	//CORRECT !
	
	//void printTreeByFrequency(PrintStream stream)
	public void printTreeByFrequency(PrintStream stream){
		//byfreq = new PQ();
		traverseInOrder3(head ,byfreq);
		while (byfreq.size()>0){
			maxxx=byfreq.getmax();
			stream.println(maxxx.toString());
		}
	}	
	private void traverseInOrder3(TreeNode node , PQ byfreq) {
		if (node != null) {
			traverseInOrder3(node.left, byfreq);
				byfreq.insert(node.item);
			traverseInOrder3 (node.right, byfreq);
		}
	}
	//WordFreq getMaximumFrequency()
	public WordFreq getMaximumFrequency(){
		PQ byfreq2 = new PQ();
		traverseInOrder3(head ,byfreq2);
		return byfreq2.getmax();
	}
	
	
//MAIN METHOD IF U WANT TO CHECK THEM 
	/*
	public static void main(String [] args){
		BST bst = new BST();

		while (f){
			System.out.println("");
			Scanner number = new Scanner(System.in);
			System.out.println("Press 1 if you want to insert a word  ");//kk 
			System.out.println("Press 2 if you want to search a word  "); 
			System.out.println("Press 3 if you want to remove a word  ");//problem
			System.out.println("Press 4 if you want to load a file  ");//kk 
			System.out.println("Press 5 if you want to know the total amount of words ");//kk
			System.out.println("Press 6 if you want to know the distinct amount of words ");//kk 
			System.out.println("Press 7 if you want to know the freq of a word "); //kk
			System.out.println("Press 8 if you want to learn the most frequent word  ");//kk
			System.out.println("Press 9  if you want to get the Mean Frequency ");//kk
			System.out.println("Press 10 if you want to add StopWord  ");//kk
			System.out.println("Press 11 if you want to remove StopWord  ");//problem
			System.out.println("Press 12 if you want to printTreeAlphabetically  ");//kk
			System.out.println("Press 13 if you want to printTreeByFrequency ");//kk
			System.out.println("Press 0 to exit  ");//kk
			int select = number.nextInt();
			
			if (select==0){ //correct
				System.exit(0);
			}
			else if (select==1){ //correct 
				System.out.println("Give the word: " );
				Scanner aaa = new Scanner(System.in);
				String a = aaa.nextLine();
				bst.insert(a) ;
				continue; 
			}
			else if (select==2){
				System.out.println("Give the word: " );
				Scanner bb = new Scanner(System.in);
				String a = bb.nextLine();
				System.out.println(bst.search(a).getWord() );
				continue; 
			}
			else if (select==3){
				System.out.println("Give the word: " );
				Scanner cc = new Scanner(System.in);
				String a = cc.nextLine();
				bst.remove(a) ;
				continue; 
			}
		    else if (select==4){ //correct
				System.out.println("What is the name of the file that you want me to read (ex. name.txt): ");
				Scanner in = new Scanner(System.in);
				String filename = in.nextLine();
				bst.load(filename);
				continue;
			}
			else if (select==5){ //correct
				System.out.println(bst.getTotalWords());
				continue;
			}
			else if (select==6){//correct
				System.out.println(bst.getDistinctWords());
				continue;
			}
			else if (select==7){ //correct 
				System.out.println("Give the word: " );
				Scanner ccc = new Scanner(System.in);
				String a = ccc.nextLine();
				System.out.println(bst.getFrequency(a)) ;
				continue; 
			}
			else if (select==8){ //correct
				System.out.println(bst.getMaximumFrequency());
				continue;
			}
			else if (select==9){ //correct
				System.out.println(bst.getMeanFrequency());
				continue;
			}
			else if (select==10){ //correct
				System.out.println("Give the word: " );
				Scanner ddd = new Scanner(System.in);
				String a = ddd.nextLine();
				bst.addStopWord(a) ;
				
				continue; 
			}
			else if (select==11){
				System.out.println("Give the word: " );
				Scanner ee = new Scanner(System.in);
				String a = ee.nextLine();
				bst.removeStopWord(a) ;
				continue; 
			}
			else if (select==12){ //correct
				bst.printTreeAlphabetically(System.out);
				continue;
			}
			else if (select==13){//correct
				bst.printTreeByFrequency(System.out);
				continue;
			}	
			else { //correct
				System.out.println("Give a valid number! \n " );
				continue ;
			}
		}
	} 
	*/

}

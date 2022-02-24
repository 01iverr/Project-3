class WordFreq{
	private String word; 
	private int freq; 
	String key() {return word;} 
	//  getters, setters 
	public WordFreq(String word, int freq){
		this.word = word;
		this.freq=freq;
	}
	public WordFreq(String word){
		this.word = word;
		this.freq=1;
	}
	public WordFreq(){
	}

	public String getWord(){
		return word;
	}

	public int getFreq(){
		return freq; 
	}
	public void setword(String word){
		word = word;
	}
	public void setfreq(int f){
		freq=f;
	}
	@Override
	//change for print
	public String toString(){
		return "word: " + word + " has frequency " + freq ;
	}
	//@Override
	public int compareTo(WordFreq x){
		if(this.getFreq() > x.getFreq()){
			return 1;
		}
		else{
			return -1; 
		}
	}
}

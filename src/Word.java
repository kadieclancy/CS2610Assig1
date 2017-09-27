
public class Word implements Comparable<Word> {
  public String word;
  public float value;

	public Word(String word, float value) {
    this.word = word;
    this.value = value;
  }

  public int compareTo(Word other){
    if(other.value > this.value){
      return 1;
    }
    else if(other.value < this.value){
      return -1;
    }
    else{
      return 0;
    }
  }
}

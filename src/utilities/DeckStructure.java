package utilities;

import java.util.Iterator;
import java.util.Random;

import cards.Card;

public class DeckStructure implements Iterable<Card>{
	/*
	 * DeckStructure is a pair of arrays that are meant
	 * to provide less strain on the system than an
	 * ArrayList.
	 * 
	 * How it works:
	 * -Two arrays: contents and sortNumbers
	 * --contents are never removed
	 * --sortNumbers is the 'index' of the adjacent content, -1 means it is removed
	 * -Three sizes:
	 * --maxSize is the size of each array
	 * --currentSize is the number of elements in contents
	 * --accessibleSize is the number of defined, non-negative elements in sortNumbers
	 */
	
	private Card[] contents;
	private int[] sortNumbers;
	private int maxSize;
	private int currentSize;
	private int accessibleSize;
	
	public DeckStructure(int s){
		contents = new Card[s];
		sortNumbers = new int[s];
		currentSize = 0;
		accessibleSize = 0;
		maxSize = s;
		
		for(int i = 0; i < maxSize; i++){
			sortNumbers[i] = -1;
		}
	}
	
	public void add(Card c){
		// adds a card to the bottom of the deck
		if(currentSize < maxSize){
			contents[currentSize] = new Card(c);
			sortNumbers[currentSize] = currentSize;
			currentSize++;
			accessibleSize++;
		}
	}
	public void add(Card c, int copies){
		for(int i = 0; i < copies; i++){
			add(c);
		}
	}
	
	public void remove(int index){
		sortNumbers[getIndexOf(index)] = -1;
		filterDown();
	}
	
	public void discard(){
		sortNumbers[getIndexOf(0)] = -1;
		filterDown();
	}
	
	public Card get(int index){
		Card ret = new Card("ERROR");
		boolean found = false;
		
		if(index < 0 || index >= accessibleSize){
			throw new IndexOutOfBoundsException();
		}
		
		for(int i = 0; i < currentSize && !found; i++){
			Op.log("sortNumbers[" + i + "]: " + sortNumbers[i]);
			if(sortNumbers[i] == index){
				found = true;
				ret = contents[i];
			}
		}
		if(!found){
			Op.log("Not found index: " + index);
			throw new IndexOutOfBoundsException();
		}
		return ret;
	}
	
	public int getIndexOf(int index){
		int ret = -1;
		boolean found = false;
		for(int i = 0; i < currentSize && !found; i++){
			if(sortNumbers[i] == index){
				ret = i;
			}
		}
		if(ret == -1){
			Op.log("Not found index: " + index);
			throw new IndexOutOfBoundsException();
		}
		return ret;
	}
	
	public int size(){
		return accessibleSize;
	}
	
	public void shuffle(){
		Random rng = new Random();
		for(int i = 0; i < maxSize; i++){
			if(sortNumbers[i] != -1){
				sortNumbers[i] = rng.nextInt(maxSize);
			}
		}
		filterDown();
	}
	
	// todo: weed out dupes
	public void filterDown(){
		/* 
		 * makes it so the numbers 
		 * form a proper array
		 */
		// outer loop stores the number of cards
		// between the card and the top of the deck
		int smallerCount;
		int[] newNums = new int[maxSize];
		for(int i = 0; i < maxSize; i++){
			newNums[i] = -1;
		}
		
		for(int i = 0; i < maxSize; i++){
			smallerCount = 0; // number of elements smaller than this
			Op.log("Data before iteration:");
			Op.log(newNums);
			Op.log(sortNumbers);
			for(int j = 0; j < maxSize; j++){
				if(sortNumbers[j] < sortNumbers[i] && sortNumbers[j] != -1){
					smallerCount++;
				}
			}
			if(sortNumbers[i] != -1){
				newNums[i] = smallerCount;
			}
		}
		sortNumbers = newNums;
	}
	
	public void displayData(){
		for(int i = 0; i < currentSize; i++){
			Op.log(contents[i].getName() + ": at position " + sortNumbers[i]);
		}
	}
	
	public Iterator<Card> iterator(){
		return new DeckIterator();
	}
	
	public class DeckIterator implements Iterator<Card>{
		private int current;
		
		public DeckIterator(){
			current = 0;
		}
		public boolean hasNext(){
			return current < DeckStructure.this.accessibleSize;
		}
		public Card next(){
			Card ret;
			if(hasNext()){
				ret = get(current);
				current++;
			} else {
				throw new NullPointerException();
			}
			return ret;
		}
	}
}

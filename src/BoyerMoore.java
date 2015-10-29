import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class BoyerMoore {
//	Character[] needle;
//	String haystack;
//	Integer[] badCharacterShift;// = new Integer[256];
	
	
	//Matcher baklengs så vi starter på index needle.length-1

	/**
	 * 
	 * @param needle
	 * @return the number of hit
	 */
	public Integer[] computeBadCharShift(String needle){
		Integer[] badCharacterShift  = new Integer[256];
		Integer lastWildCardPos = needle.length();
		
		//Finding the index of the last wildcard
		for(int i = 0; i<needle.length(); i++){
			if(needle.charAt(i) == ('_')){
//				System.out.println("wildcard at po " + i);
				lastWildCardPos = needle.length()-i;
			}
		}
			
		for(int i = 0; i < badCharacterShift.length; i++){
			badCharacterShift[i] = needle.length();
		}
				
		//This loop will make the needle jump longer
		Integer last = needle.length() - 1;
		for(int i = 0; i < last; i++){
//			System.out.println("Configure pos: " + (int)needle.charAt(i) + "with value " + needle.charAt(i)+ " to " + (last-i));
			badCharacterShift[(int)needle.charAt(i)] = last-i;
		}
		
		//This loop ensure that the jump is no longer then the wildcard
		for(int i = 0; i < badCharacterShift.length; i++){
			badCharacterShift[i] = lastWildCardPos;
		}
		return badCharacterShift;
	}
	
	public int findMatch(String needle, String haystack){
		if(needle.length()> haystack.length()){
			return -1;
		}
		Integer[] badCharShift = computeBadCharShift(needle);
		int offsetPointer = 0;
		int scan = 0;
		int lastCharInNeedle = needle.length() - 1;
		int endOfHaystack = haystack.length() - needle.length();
		int shiftCount = 0;
		HashMap<Integer, String> hit = new HashMap<Integer,String>();
		while( offsetPointer <= endOfHaystack ){
			
			for( scan = lastCharInNeedle; scan > -1; scan--){			
//				System.out.println("Scan " +scan);
//				System.out.println("Needle at scan " + needle.charAt(scan));
//				System.out.println("Haystack at scan " + haystack.charAt(scan+offsetPointer));
//				System.out.println("OffsetPointer " + offsetPointer);
				if(scan == 0){
//					System.out.println("********Found match at scan = " +offsetPointer);
//					return offsetPointer;
					hit.put(offsetPointer, haystack.substring(offsetPointer, offsetPointer+needle.length()));
				}
				if(needle.charAt(scan) !='_'){
					if(needle.charAt(scan) != haystack.charAt(scan+offsetPointer) ){
//						System.out.println("Miss!");
						break;
					}
				}
//				System.out.println("HIT");
			}
			shiftCount++;
//			System.out.println("Miss shifting: " + badCharShift[haystack.charAt(scan+offsetPointer)] + " for the value " +haystack.charAt(scan+offsetPointer) );
			offsetPointer += badCharShift[haystack.charAt(offsetPointer+scan)];
		}
		System.out.println("Hit: ");
		for(Integer i : hit.keySet()){
			System.out.println("index: " + i + " string " + hit.get(i));
		}
		System.out.println("Total number of shifts: " +shiftCount);
		return hit.size();
	}
	
	public void run(File needleFile, File hayStackFile){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(needleFile));
		
			String needle = "";
			String nextLine = reader.readLine();
			while(nextLine != null){
				needle += nextLine;
				nextLine = reader.readLine();
			}
			
			reader.close();
			reader = new BufferedReader(new FileReader(hayStackFile));
			
			String haystack = "";
			nextLine = reader.readLine();
			while(nextLine != null){
				haystack += nextLine;
				nextLine = reader.readLine();
			}
			
			findMatch(needle, haystack);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not open file..");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		if(args.length != 2){
			System.out.println("Usage: java BoyerMoore <needleFile> <haystackFile>");
		}
		File needle = new File(args[0]);
		File haystack = new File(args[1]);
		BoyerMoore bom = new BoyerMoore();
		bom.run(needle, haystack);

	}
	
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class BoyerMoore {
	/**
	 * Compute the bad character shift. It will first set all the shift to the
	 * length of the needle , then it would set the shift for chacters that
	 * exists in the needle and at the end it will set the maximum jump to the
	 * last wildcard if there is a wildcard in the string.
	 * 
	 * @param needle
	 * @return the number of hit
	 */
	public Integer[] computeBadCharShift(String needle) {
		Integer[] badCharacterShift = new Integer[256];
		Integer lastWildCardPos = needle.length();

		// Finding the index of the last wildcard
		for (int i = 0; i < needle.length(); i++) {
			if (needle.charAt(i) == ('_')) {
				// System.out.println("wildcard at po " + i);
				lastWildCardPos = needle.length() - i;
			}
		}
		
		// This loop ensure that the jump is no longer then the wildcard
		for (int i = 0; i < badCharacterShift.length; i++) {
			badCharacterShift[i] = lastWildCardPos;	
		}

		// This loop will ensure that the letters to the right of the wildcard isn´t skipped, also if it is no wildcard.
		Integer last = needle.length() - 1;
		for (int i = 0; i < last; i++) {
			if(last-i<lastWildCardPos){
				badCharacterShift[(int) needle.charAt(i)] = last - i;
			}
			//System.out.println(needle.charAt(i) + " has bad char shift: " + badCharacterShift[(int) needle.charAt(i)] );
		}

	
		return badCharacterShift;
	}

	public Integer[] computeGoodCharShift(String needle){
		System.out.println("Computing good char shift");
		Integer[] goodCharShift = new Integer[needle.length()];
		int lastChar = needle.length()-1;
		for(int i =0; i<lastChar; i++){
			goodCharShift[i] = needle.length();
		}
		goodCharShift[lastChar] = 1;
		//This does not care about the wildcard right now!!
		
		for(int i = lastChar-1; i>0; i--){
			//let igjennom nåla baklengs for å finne dette suffixet
			for(int j = 1; j<i; j++){
				String comparator = needle.substring(j,j+lastChar-i);
				if(comparator.equals(needle.substring(i+1))&& (needle.charAt(j-1)!=(needle.charAt(i)))){
					goodCharShift[i] = j+1;
				}
			}
		}
		for(int i = 0; i<lastChar+1; i++){
			System.out.println(goodCharShift[i]);
		}
		return goodCharShift;
	}
	/**
	 * Using the Boyer Moore algorithm to find all the match of a needle in a
	 * haystack
	 * 
	 * @param needle
	 * @param haystack
	 * @return number of hit and -1 if the haystack is to short, or the needle
	 *         has length 0.
	 */
	public int findMatch(String needle, String haystack) {
		if (needle.length() > haystack.length() || needle.length() == 0 || haystack.length() == 0) {
			System.out.print("\n Could not find any match." + "\n Caused by the following reasons: \n"
					+ " (1) Needle is larger then haystack: " + (needle.length() > haystack.length()) + ",\n"
					+ " (2) Length of needle is 0: " + (needle.length() == 0) + ", \n"
					+ " (3) Length of Haystack is 0: " + (haystack.length() == 0) + ", \n");
			return -1;
		}
		Integer[] badCharShift = computeBadCharShift(needle);
		Integer[] goodCharShift = computeGoodCharShift(needle);
		int offsetPointer = 0;
		int scan = 0;
		int lastCharInNeedle = needle.length() - 1;
		int endOfHaystack = haystack.length() - needle.length();
		int shiftCount = 0;
		LinkedHashMap<Integer, String> hit = new LinkedHashMap<Integer, String>();
		while (offsetPointer <= endOfHaystack) {
			for (scan = lastCharInNeedle; scan > -1; scan--) {
				//check if the position is a wildcard, this is always hit. 
				if (needle.charAt(scan) != '_') {
					if (needle.charAt(scan) != haystack.charAt(scan + offsetPointer)) {
						break;
					}
				}
				if (scan == 0) {
					hit.put(offsetPointer, haystack.substring(offsetPointer, offsetPointer + needle.length()));
					break;
				}
			}
			shiftCount++;
			if(goodCharShift[scan]> badCharShift[haystack.charAt(offsetPointer + lastCharInNeedle)]){
				offsetPointer += goodCharShift[scan];
			}else{
				offsetPointer += badCharShift[haystack.charAt(offsetPointer + lastCharInNeedle)];
			}
			
		}
		System.out.println("Total number of hit: " + hit.size());
		for (Integer i : hit.keySet()) {
			System.out.println("index: " + i + " string " + hit.get(i));
		}
		System.out.println("Total number of shifts: " + shiftCount);
		return hit.size();
	}

	/**
	 * Import two files where the needleFile contains the string that we want to
	 * compare, and the haystackFile contains the one to compare the needle to
	 * 
	 * @param needleFile
	 * @param hayStackFile
	 */
	public void run(File needleFile, File hayStackFile) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(needleFile));

			String needle = "";
			String nextLine = reader.readLine();
			while (nextLine != null) {
				needle += nextLine;
				nextLine = reader.readLine();
			}
			
			reader.close();
			reader = new BufferedReader(new FileReader(hayStackFile));

			String haystack = "";
			nextLine = reader.readLine();
			while (nextLine != null) {
				haystack += nextLine;
				nextLine = reader.readLine();
			}

			System.out.println("******Shearching for needle: "+needle+ " *********");
			findMatch(needle, haystack);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not find the file..");
			System.out.println("Make sure the needle and the haystack files are in the directory you run your code..!");
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java BoyerMoore <needleFile> <haystackFile>");
			System.exit(0);
		}
		File needle = new File(args[0]);
		File haystack = new File(args[1]);
		BoyerMoore bom = new BoyerMoore();
		bom.run(needle, haystack);
	}
}

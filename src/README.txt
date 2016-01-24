1) Explanation of the algorithm:
The algorithm should follow the Boyer moores algorithm. 
a) First Compute the bad character shift.
	*  It will first set all the shift to the length of the needle ,
	*  then it would set the shift for chacters that exists in the needle 
	* and at the end it will set the maximum jump to the last wildcard in the needle if there is a wildcard in the string

b) Then it run the findMatch which is the BoyerMoore algorithm
	* Initially it check wether the needle or the haystack has length 0 or the needle is longer than the haystack and return -1 beacuse there is no solution to the problem,
	* Next it will scan through the haystack starting with index equal the length of the needle-1. 
	* If there is a miss it will move the needle according to the bad char shift of the haystack lookated at offset+needle-1

2) Compile by using javac *.java, if it is run correctly it should output something simular to bulletpoint 8.

3) The only class is BoyerMoore.java and it contains the main method
4) The needle and the haystack should be in the same directory as where the code is run from.
5) No special peculiarities
6) It should work as descirbed in the assignment description
7) Have used the lecture slides when implementing the algorithm, but tried to use my way of programming when writing the code

8) Output from run
Idas-MBP:src Zelus$ java BoyerMoore needle haystack
***************************************
TestCase 1 - wildcard at start end
Total number of hit: 5
index: 1 string his
index: 4 string  is
index: 28 string ain
index: 33 string dif
index: 43 string lin
Total number of shifts: 63
****************Test case 1 end*****************
***************************************
TestCase 2 - needle all wildcards
Total number of hit: 62
index: 0 string this
index: 1 string his 
index: 2 string is i
index: 3 string s is
index: 4 string  is 
index: 5 string is a
index: 6 string s a 
index: 7 string  a h
index: 8 string a ha
index: 9 string  hay
index: 10 string hays
index: 11 string ayst
index: 12 string ysta
index: 13 string stac
index: 14 string tack
index: 15 string ack 
index: 16 string ck t
index: 17 string k th
index: 18 string  tha
index: 19 string that
index: 20 string hat 
index: 21 string at c
index: 22 string t co
index: 23 string  con
index: 24 string cont
index: 25 string onta
index: 26 string ntai
index: 27 string tain
index: 28 string ains
index: 29 string ins 
index: 30 string ns d
index: 31 string s di
index: 32 string  dif
index: 33 string diff
index: 34 string iffe
index: 35 string ffer
index: 36 string fere
index: 37 string eren
index: 38 string rent
index: 39 string ent 
index: 40 string nt l
index: 41 string t li
index: 42 string  lin
index: 43 string line
index: 44 string ines
index: 45 string nes 
index: 46 string es t
index: 47 string s to
index: 48 string  to 
index: 49 string to m
index: 50 string o ma
index: 51 string  mak
index: 52 string make
index: 53 string ake 
index: 54 string ke s
index: 55 string e so
index: 56 string  som
index: 57 string some
index: 58 string omet
index: 59 string mete
index: 60 string etes
index: 61 string test
Total number of shifts: 62
****************Test case 2 end*****************
***************************************
TestCase 3 - needle start of haystack
Total number of hit: 1
index: 0 string thi
Total number of shifts: 21
****************Test case 3 end*****************
***************************************
TestCase 4 - needle end  of haystack
Total number of hit: 0
Total number of shifts: 16
****************Test case 4 end*****************
***************************************
TestCase 5 - needle empty

 Could not find any match.
 Caused by the following reasons: 
 (1) Needle is larger then haystack: false,
 (2) Length of needle is 0: true, 
 (3) Length of Haystack is 0: false, 
****************Test case 5 end*****************
***************************************
TestCase 6 - haystack empty

 Could not find any match.
 Caused by the following reasons: 
 (1) Needle is larger then haystack: true,
 (2) Length of needle is 0: false, 
 (3) Length of Haystack is 0: true, 
****************Test case 6 end*****************
*******USERS RUN*********
Total number of hit: 0
Total number of shifts: 13

import java.util.Random;

/*
 * Basic Games Library
 * 
 * Variables :
 * 
 * Functions :
 * 
 * int RndInt (int i) ; return rndint 
 * int RndInt (int i , int i) * overload + bound ; return rndint + bound
 * */
public class BGL {

	//basic random integer number
	public static int RndInt(int i) {
		Random nn = new Random();
		return nn.nextInt(i);
	}
	//overload + bound
	public static int RndInt(int i , int bound) {
		Random nn = new Random();
		return nn.nextInt(i)+bound;
	}
	//overload + bound
	//random without 0
	public static int RndInt(int i , int bound , int noVal){
		int val=RndInt(i,bound);
		if (val==noVal)return RndInt(i,bound,noVal);
		return val;
	}
	//______________________________________________________________________________________
	//rand pick one of two elms 
	public static int RndIntPick(int val , int val2){
		if (RndInt(2)==1)
		return val;
		return val2;
	}
	
	//______________________________________________________________________________________
	//Collision 
	// point to square
	public static boolean ptRect(int ptx , int pty  , int rectx , int recty , int size){
	if (ptx>rectx && ptx<rectx+size && pty>recty && pty<recty+size )
		return true;
		return false;
	}
	// point to rectangle
	public static boolean ptRect(int ptx , int pty  , int rectx , int recty , int sizex , int sizey){
	if (ptx>rectx && ptx<rectx+sizex && pty>recty && pty<recty+sizey )
		return true;
		return false;
	}

	
}


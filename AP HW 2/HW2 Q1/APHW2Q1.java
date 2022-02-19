import java.util.*;

class APHW2Q1{

	public static String[] takeInput(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter needle");
		String needle = sc.nextLine();
		System.out.println("Enter haystack");
		String hay = sc.nextLine();
		return new String[]{needle, hay};
	}

	public static int findNeedleInHay(String needle, String hay) {
                if(needle.length()==0||hay.length()==0) return -1;
		for(int i=0;i<=hay.length()-needle.length();i++){
			if(hay.charAt(i)==needle.charAt(0)){
				int j=0;
				for(;j<needle.length();j++)
					if(hay.charAt(i+j)!=needle.charAt(j))
						break;
				if(j==needle.length())
				return i;
			}
		}
		return -1;
	}

	public static void main(String args[]){
		String[] input = takeInput();
		System.out.println("Needle at Index: "+findNeedleInHay(input[0],input[1]));
	}
}

import java.util.*;

class APHW2Q4{

    public static String[] takeInput(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter needle");
        String needle = sc.nextLine();
        System.out.println("Enter haystack");
        String hay = sc.nextLine();
        return new String[]{needle, hay};
    }

    public static int[] KMPPrefixSumArr(String currStr){
        int left=0,right=1;
        int[] prefix_sum_array = new int[currStr.length()];
        while(right<currStr.length()){
          if(currStr.charAt(right)==currStr.charAt(left)) {
            prefix_sum_array[right++]=++left;
          }else{
            if(left!=0)
              left=prefix_sum_array[left-1];
            else
              prefix_sum_array[right++]=left;
          }
        }
        return prefix_sum_array;
      }

    public static int KMPfindNeedleInHay(String needle, String hay, int[] currlps) {
        int t=0,p=0;
        while(t<hay.length()){
          if(needle.charAt(p)==hay.charAt(t)){
            p++;t++;
            if(p==needle.length())
              return t-needle.length();
          }
          else{
            if(p!=0) p=currlps[p-1];
            else t++;
          }
        }
        return -1;
    }

    public static void main(String args[]){
        String[] input = takeInput();
        int[] lps = KMPPrefixSumArr(input[0]);
        System.out.println("Needle at Index: "+KMPfindNeedleInHay(input[0],input[1],lps));
    }
}

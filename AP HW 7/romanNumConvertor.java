import java.util.*;

public class romanNumConvertor {
    private static HashMap<String,Integer> charToDigit = new HashMap<>();

    private static void initCharDigitMap(){
        charToDigit.put("I",1);charToDigit.put("V",5);charToDigit.put("X",10);
        charToDigit.put("L",50);charToDigit.put("C",100);charToDigit.put("D",500);
        charToDigit.put("M",1000);charToDigit.put("IV",4);charToDigit.put("IX",9);
        charToDigit.put("XL",40);charToDigit.put("XC",90);charToDigit.put("CD",400);
        charToDigit.put("CM",900);
    }

    private static String appendSameChar(int v, int k, char a) {
        StringBuilder tmp = new StringBuilder();
        while(v>k){
            tmp.append(a);
            v--;
        }
        return tmp.toString();
    }

    private static String charSelectionMenu(int v, char a, char b, char c) {
        StringBuilder sb = new StringBuilder();
        if(v==9) {
            sb.append(a);
            sb.append(b);
        }
        else if(v>=5){
            sb.append(c);
            sb.append(appendSameChar(v,5,a));
        }else if(v==4) {
            sb.append(a);
            sb.append(c);
        }
        else
            sb.append(appendSameChar(v,0,a));
        return sb.toString();
    }

    private static boolean validateIntString(char[] snum){
        for(char c:snum)
            if(!Character.isDigit(c)) return false;
        return true;
    }

    private static String intToRoman(String snum) {
        if(!validateIntString(snum.toCharArray())) return "Invalid Input";
        StringBuilder sb = new StringBuilder();
        int i=0;
        if(snum.length()==4)
            sb.append(appendSameChar(snum.charAt(i++)-'0',0,'M'));
        if(snum.length()>=3)
            sb.append(charSelectionMenu(snum.charAt(i++)-'0','C','M','D'));
        if(snum.length()>=2)
            sb.append(charSelectionMenu(snum.charAt(i++)-'0','X','C','L'));
        if(snum.length()>=1)
            sb.append(charSelectionMenu(snum.charAt(i++)-'0','I','X','V'));
        return sb.toString();
    }

    private static String romanToInt(String s) {
        int num=0;
        for(int i=0;i<s.length();i++){
            if(!Character.isUpperCase(s.charAt(i))) return "Invalid Input";
            if(i<s.length()-1&&charToDigit.containsKey(s.substring(i,i+2))){
                num+=charToDigit.get(s.substring(i,i+2));i++;
            }else if(charToDigit.containsKey(s.substring(i,i+1))) num+=charToDigit.get(s.substring(i,i+1));
            else return "Invalid Input";
        }
        return String.valueOf(num);
    }

    private static void selectionMenu(Scanner sc){
        while (true){
            System.out.println("Enter input or 'quit");
            String curr = sc.next();
            if(curr.equals("quit")) break;
            if(curr.length()>0){
                if(Character.isDigit(curr.charAt(0))) System.out.println(intToRoman(curr));
                else System.out.println(romanToInt(curr));
            }
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        initCharDigitMap();
        selectionMenu(sc);
        sc.close();
    }
}

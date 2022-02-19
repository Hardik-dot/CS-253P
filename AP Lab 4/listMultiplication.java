import java.util.*;
public class listMultiplication {
    static class Node {
        int val;
        Node next;
        Node(int v){
            val = v;
        }
    }

    public static Node convertStringToInputList(String s){
        Node iter = null;
        for(int i=0;i<s.length();i++){
            Node curr = new Node((int) s.charAt(i)-'0');
            if(iter!=null) curr.next=iter;
            iter = curr;
        }
        return iter;
    }

    public static Node initializeAnswerList(int len){
        Node res = new Node(0);
        for(int i=0;i<len;i++){
            Node curr = new Node(0);
            curr.next=res; res = curr;
        }
        return res;
    }

    public static void addCarry(Node tempRes, int carry){
        while(carry>0){
            int v = tempRes.val + carry;
            tempRes.val = v%10;
            carry = v/10;
            tempRes = tempRes.next;
        }
    }

    public static void multiplyEachNumber(Node curr, Node tempRes, int two_digit){
        int carry = 0;
        while(curr!=null){
            int v = tempRes.val + two_digit * curr.val + carry;
            tempRes.val = v%10;
            carry = v/10;
            tempRes = tempRes.next;
            curr = curr.next;
        }
        addCarry(tempRes, carry);
    }

    public static Node computeMultiplication(String[] nums){
        Node numberOne = convertStringToInputList(nums[0]), numberTwo = convertStringToInputList(nums[1]);
        Node ans = initializeAnswerList(nums[0].length()+nums[1].length()), result = ans;
        while(numberTwo!=null){
            Node curr = numberOne, rescur = ans;
            multiplyEachNumber(curr, rescur, numberTwo.val);
            numberTwo = numberTwo.next;
            ans = ans.next;
        }
        return result;
    }

    public static String removeLeadingZeroes(StringBuilder sb){
        int i=0;
        while(i<sb.length()&&sb.charAt(i)=='0')
            i++;
        return sb.substring(i);
    }

    public static void printList(Node result){ //O(n+m)
        StringBuilder sb = new StringBuilder();
        while (result!=null) {
            sb.insert(0, result.val);
            result = result.next;
        }
        System.out.println("Muliplication: "+removeLeadingZeroes(sb));
    }

    public static boolean validateNumberString(String num){
        for(int i=0;i<num.length();i++)
            if(!Character.isDigit(num.charAt(i)))
                return false;
        return true;
    }

    public static String askUser(Scanner sc, int i){
        System.out.println("Please enter nummber"+(i+1));
        return sc.nextLine();
    }

    public static String[] takeInput(Scanner sc, String[] nums){
        nums[0] = askUser(sc,0); nums[1] = askUser(sc,1);
        if(!validateNumberString(nums[0])||!validateNumberString(nums[1])) {
            nums=new String[]{"",""};
            System.out.println("Invalid number entered.");
        }
        return nums;
    }

    public static void main(String[] args) {
        String[] nums = takeInput(new Scanner(System.in), new String[2]);
        Node result = computeMultiplication(nums);
        printList(result);
    }
}

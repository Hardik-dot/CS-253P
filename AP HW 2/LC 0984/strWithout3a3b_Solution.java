import java.util.*;
class strWithout3a3b_Solution {
    public static String strWithout3a3b(int a, int b) {
        StringBuilder sb = new StringBuilder();
        String one="a",two="b";
        int cto=a,ctt=b;
        if(b>a){
            one="b";two="a";
            cto=b;ctt=a;
        }

        while(cto!=ctt&&cto>1&&ctt>0){
            sb.append(one);sb.append(one);sb.append(two);
            cto-=2;ctt--;
        }

        while(true){
            if(cto==0&&ctt==0) break;
            if(cto!=0){
                sb.append(one);
                cto--;
            }
            if(ctt!=0){
                sb.append(two);
                ctt--;
            }
        }
        return sb.toString();
    }

	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a:");
		int a = sc.nextInt();
		System.out.println("Enter b:");
		int b = sc.nextInt();

		System.out.println(" String without AAA or BBB: "+strWithout3a3b(a,b));

	}
}

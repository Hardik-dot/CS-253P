import java.util.*;

public class COINSQ1 {

    static int[] denoValue = new int[]{1, 5, 10, 25};

    public static int[] coinChange(int[] deno, int ind, int amount){
        if(amount == 0)
            return deno;

        if(ind == 4)
            return null;

        if(deno[ind] != 0 && denoValue[ind] <= amount){
            int[] temp_coin_store = Arrays.copyOf(deno, 4);
            temp_coin_store[ind] -= 1;
            int[] exclude_current = coinChange(deno, ind + 1, amount);
            int[] include_current = coinChange(temp_coin_store, ind, amount - denoValue[ind]);
            if(include_current == null && exclude_current == null)
                return null;
            if(include_current == null)
                return exclude_current;
            if(exclude_current == null)
                return include_current;
            int coins_inclusive = calculateCost(deno, include_current), coins_exclusive = calculateCost(deno, exclude_current);
            return (Math.max(coins_inclusive, coins_exclusive) == coins_inclusive) ? include_current : exclude_current;
        }
        return coinChange(deno, ind + 1, amount);
    }


    public static void takeInput(Scanner sc, int[] deno){
        System.out.println("Enter initial denominations");
        for(int i = 0; i < 4; i++)
            deno[i] = sc.nextInt();
    }

    public static int calculateCost(int[] pre, int[] post){
        return  (pre[0] - post[0] + pre[1] - post[1] + pre[2] - post[2] +pre[3] - post[3]);
    }

    public static void loopForItem(Scanner sc, int[] deno){
        System.out.println("Keep entering value of each item(or -1 to quit)\n");

        while (true){
            int val = sc.nextInt();
            if(val==-1) break;
            int[] curr_iter_coins = coinChange(deno,0,val);
            for (int i = 0; i < 4; i++) {
                System.out.print(deno[i]+" ");
            }
            if(curr_iter_coins==null)
                System.out.println("\nUnable to pay!");
            else {
                System.out.println();
                for (int i = 0; i < 4; i++) {
                    System.out.print(deno[i] - curr_iter_coins[i] + " ");
                    deno[i] = curr_iter_coins[i];
                }
            }
            System.out.println();
            System.out.println("------------");
        }
    }

    public static void main(String[] args){
        int[] deno = new int[4];
        takeInput(new Scanner(System.in),deno);
        loopForItem(new Scanner(System.in),deno);
    }

}

import java.util.*;

public class AVLTree {
    static class TreeNode {
        int val, height;
        TreeNode left,right;
        TreeNode(int val){
            this.val = val;
        }
    }

    private static int getHeight(TreeNode root){
        if(root==null) return 0;
        return Math.max(getHeight(root.left),getHeight(root.right))+1;
    }

    private static int getBalFactor(TreeNode root) {
        if(root==null) return 0;
        return getHeight(root.left)-getHeight(root.right);
    }

    private static TreeNode rotateLeft(TreeNode root) {
        TreeNode y = root.right;
        TreeNode beta = y.left;
        y.left = root;
        root.right = beta;
        root.height = Math.max(getHeight(root.left),getHeight(root.right))+1;
        y.height = Math.max(getHeight(y.left),getHeight(y.right))+1;
        return y;
    }

    private static TreeNode rotateRight(TreeNode root) {
        TreeNode x = root.left;
        TreeNode beta = x.right;
        x.right = root;
        root.left = beta;
        root.height = Math.max(getHeight(root.left),getHeight(root.right))+1;
        x.height = Math.max(getHeight(x.left),getHeight(x.right))+1;
        return x;
    }

    private static TreeNode leftImbalanceInsert(int val, TreeNode root) {
        if(val<root.left.val)
            return rotateRight(root);
        else if(val>root.left.val){
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }
        return root;
    }

    private static TreeNode rightImbalanceInsert(int val, TreeNode root) {
        if(val<root.right.val){
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }else if(val>root.right.val)
            return rotateLeft(root);
        return root;
    }

    private static TreeNode balanceInsert(int val, TreeNode root){
        int leftHt = getHeight(root.left), rightHt = getHeight(root.right);
        root.height = Math.max(leftHt,rightHt) + 1;
        int balFactor = leftHt - rightHt;
        if(balFactor>1)
            return leftImbalanceInsert(val,root);
        else if(balFactor<-1)
            return rightImbalanceInsert(val, root);
        return root;
    }

    private static TreeNode insertInTree(int val, TreeNode root){
        if(root==null) {
            System.out.print(val+" (inserted)");
            return new TreeNode(val);
        }
        System.out.print(root.val+" ");
        if(val<root.val) root.left = insertInTree(val,root.left);
        else if(val>root.val) root.right = insertInTree(val,root.right);
        else return root;
        return balanceInsert(val, root);
    }

    private static void findInTree(int val, TreeNode root){
        if(root==null) {
            System.out.print(" (not found!)");
            return;
        }
        System.out.print(root.val+" ");
        if(val<root.val) findInTree(val,root.left);
        else if(val>root.val) findInTree(val,root.right);
        else System.out.print("(found)");
    }

    private static void levelOrderTraversal(TreeNode root){
        System.out.println();
        Queue<TreeNode> travel = new LinkedList<>();
        travel.add(root);
        travel.add(null);
        while(true){
            TreeNode current = travel.poll();
            if(current==null){
                if(travel.isEmpty()) break;
                System.out.println();
                travel.add(null);continue;
            }
            System.out.print(current.val+" ");
            if(current.left!=null) travel.add(current.left);
            if(current.right!=null) travel.add(current.right);
        }
        System.out.println();
    }

    private static TreeNode findInorderSuccesor(TreeNode root) {
        if(root.right==null) return root;
        return findInorderSuccesor(root.right);
    }

    private static TreeNode rearrangeDeleted(TreeNode root){
        if(root.left==null||root.right==null){
            if (root.left==null) root=root.right;
            else root = root.left;
        }else{
            TreeNode suc = findInorderSuccesor(root.left);
            root.val = suc.val;
            root.left = deleteValue(suc.val,root.left,false);
        }
        return root;
    }

    private static TreeNode leftImbalanceDelete(int val, TreeNode root) {
        if(getBalFactor(root.left)>=0)
            return rotateRight(root);
        else {
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }
    }

    private static TreeNode rightImbalanceDelete(int val, TreeNode root) {
        if(getBalFactor(root.right)<=0)
            return rotateLeft(root);
        else {
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }
    }

    private static TreeNode balanceDelete(int val, TreeNode root){
        if(root==null) return null;
        int leftHt = getHeight(root.left), rightHt = getHeight(root.right);
        root.height = Math.max(leftHt,rightHt) + 1;
        int balFactor = leftHt - rightHt;
        if(balFactor>1)
            return leftImbalanceDelete(val,root);
        else if(balFactor<-1)
            return rightImbalanceDelete(val, root);
        return root;
    }


    private static TreeNode deleteValue(int val, TreeNode root, boolean toPrint) {
        if(root==null) {
            System.out.print(" (not found!)");
            return null;
        }
        if(toPrint) System.out.print(root.val+" ");
        if(val<root.val) root.left = deleteValue(val,root.left,toPrint);
        else if(val>root.val) root.right = deleteValue(val,root.right,toPrint);
        else {
            if(toPrint) System.out.print(" (deleted)");
            root = rearrangeDeleted(root);
        }

        return balanceDelete(val, root);
    }

    private static void selectionMenu(Scanner sc, TreeNode root){
        while (true){
            System.out.println("\nEnter choice (insert/delete/find/quit):");
            String choice = sc.nextLine();
            String[] choices = choice.split(" ");
            if(choice.startsWith("i")){
                int val = Integer.parseInt(choices[1]);
                root = insertInTree(val,root);
            }
            else if(choice.startsWith("f")){
                int val = Integer.parseInt(choices[1]);
                findInTree(val,root);
            }else if(choice.startsWith("d")){
                int val = Integer.parseInt(choices[1]);
                root = deleteValue(val,root,true);
            }
            else if(choice.equals("quit")) break;
        }
    }

    public static void main(String[] args) {
        TreeNode root = null;
        selectionMenu(new Scanner(System.in),root);
    }
}

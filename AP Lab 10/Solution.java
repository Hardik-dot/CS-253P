class Solution {
    HashMap<Long, Integer> hm;
    int ans;

    public void aux2(long a, int count, int ind, int n, int[] tmp, int mHeight, int m){
        hm.put(a, count);
        int end = ind;
        while(end+1<n&&tmp[end+1]==tmp[ind]&&(end-ind+mHeight+2)<=m)
            end++;
        for (int j = end; j >= ind; j--) {
            int[] next = tmp.clone();
            for (int k = ind; k <= j; k++)
                next[k] += j - ind + 1;
            auxFn(n, m, next, count + 1);
        }
    }

    public void auxFn(int n, int m, int[] tmp, int count) {
        if (count >= ans)
            return;

        int ind = -1, mHeight = Integer.MAX_VALUE;
        long a = 0, b = 1;
        for (int i = 0; i < n; i++) {
            a =a+ tmp[i]*b;
            b =b*(m + 1);
            if(tmp[i]<mHeight)
                ind = i;
            mHeight = tmp[ind];
        }

        if (mHeight == m)
            ans = Math.min(count, ans);
        else if(hm.containsKey(a) && hm.get(a) <= count)
            return;
        else
            aux2(a,count,ind,n,tmp,mHeight,m);

    }


    public int tilingRectangle(int n, int m) {
        hm = new HashMap<>();
        ans = Integer.MAX_VALUE;
        int[] tmp = new int[n];
        if (n == m)
            return 1;
        else if (n > m)
            auxFn(m, n, tmp, 0);
        else
            auxFn(n, m, tmp, 0);

        return ans;
    }


}

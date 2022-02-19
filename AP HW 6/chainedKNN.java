import java.util.*;

public class chainedKNN {
    static class city{
        String name;
        int x, y, cid;
        city(String name, int x, int y, int cid){
            this.name = name;
            this.x = x;
            this.y = y;
            this.cid = cid;
        }
    }

    static String[] idNameMap;

    static class edge{
        int from,to;
        double dist;
        edge(int from, int to, double d){
            this.from = from;
            this.to = to;
            this.dist = d;
        }
    }

    public static city[] takeInput(Scanner sc){
        System.out.println("Enter number of cities");
        numCities = Integer.parseInt(sc.nextLine());
        idNameMap=new String[numCities];
        System.out.println("Enter city coordinates");
        int c=0;
        city[] cityLoc = new city[numCities];
        while (c<numCities){
            String line = sc.nextLine();
            String[] info = line.split(" ");
            cityLoc[c] = new city(info[0],Integer.parseInt(info[1]),Integer.parseInt(info[2]),c);
            idNameMap[c]=info[0];
            c++;
        }
        return cityLoc;
    }

    private static double getEuclideanDist(city c1, city c2){
        return Math.sqrt(Math.pow(c1.x-c2.x,2)+Math.pow(c1.y-c2.y,2));
    }

    static int[] parent;
    static int numCities;

    private static int findParent(int id){
        if(parent[id]<0) return id;
        return parent[id]=findParent(parent[id]);
    }

    private static void union(int pa,int pb){
        //parent[pa]<=parent[pb]
        if(pa<=pb){
            parent[pa]+=parent[pb];
            parent[pb]=pa;
        }else{
            parent[pb]+=parent[pa];
            parent[pa]=pb;
        }
    }

    private static List<edge> getMSTEdges(PriorityQueue<edge> allEdges){
        int edges=0;
        List<edge> mstEdges = new ArrayList<>();
        while(!allEdges.isEmpty()&&edges<numCities-1){
            edge side = allEdges.poll();
            int a=side.from,b=side.to;
            int pa=findParent(a),pb=findParent(b);
            if(pa==pb&&pa!=-1) continue;
            union(pa,pb);
            mstEdges.add(side);
            edges++;
        }
        return mstEdges;
    }

    private static List<edge> computeAllDistances(city[] cityLoc) {
        PriorityQueue<edge> allEdges = new PriorityQueue<>(Comparator.comparingDouble(x -> x.dist));
        for(int i=0;i<cityLoc.length;i++){
            for(int j=i+1;j<cityLoc.length;j++){
                double dist = getEuclideanDist(cityLoc[i],cityLoc[j]);
                allEdges.add(new edge(cityLoc[i].cid,cityLoc[j].cid,dist));
                allEdges.add(new edge(cityLoc[j].cid,cityLoc[i].cid,dist));
            }
        }
        return getMSTEdges(allEdges);
    }

    private static void printClusters(HashMap<Integer,TreeSet<Integer>> mst){
        int n=1;
        for(Map.Entry<Integer, TreeSet<Integer>> e:mst.entrySet()){
            System.out.println();
            System.out.print("Cluster "+n+": "+idNameMap[e.getKey()]);
            TreeSet<Integer> child = e.getValue();
            for(int c:child)
                System.out.print(", "+idNameMap[c]);
            n++;
        }
    }

    private static void extractClusters(){
        HashMap<Integer,TreeSet<Integer>> mst = new HashMap<>();
        for(int i=0;i<parent.length;i++) findParent(i);
        for(int ind=0;ind<parent.length;ind++){
            if(parent[ind]>=0){
                TreeSet<Integer> ts = mst.getOrDefault(parent[ind], new TreeSet<>());
                ts.add(ind);
                mst.put(parent[ind],ts);
            }else mst.put(ind,mst.getOrDefault(parent[ind], new TreeSet<>()));
        }
        printClusters(mst);
    }

    private static void deleteKm1edges(List<edge> mstEdges, int k){
        PriorityQueue<edge> allEdges = new PriorityQueue<>(Comparator.comparingDouble(x -> -x.dist));
        PriorityQueue<edge> newEdges = new PriorityQueue<>(Comparator.comparingDouble(x -> x.dist));
        allEdges.addAll(mstEdges);
        while(k>1) {
            allEdges.poll();
            k--;
        }
        while (!allEdges.isEmpty()) newEdges.add(allEdges.poll());
        Arrays.fill(parent,-1);
        getMSTEdges(newEdges);
        extractClusters();
    }

    private static void makeClusters(List<edge> mstEdges, Scanner sc){
        while(true){
            System.out.println("\n\nEnter number of cluster or 'quit'");
            String k = sc.nextLine();
            if(k.equals("quit")) break;
            deleteKm1edges(mstEdges,Integer.parseInt(k));
        }
    }

    public static void main(String[] args) {
        city[] cityLoc = takeInput(new Scanner(System.in));
        parent=new int[numCities];
        Arrays.fill(parent,-1);
        List<edge> mstEdges = computeAllDistances(cityLoc);
        makeClusters(mstEdges, new Scanner(System.in));
        return;
    }
}

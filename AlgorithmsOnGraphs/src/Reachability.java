import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Reachability {
    private static int reach(HashSet<Integer>[] adj, int x, int y) {
        //write your code here

        if (adj == null || adj.length == 0) {
            return 0;
        }


        HashSet<Integer> edges = adj[x];

        // check maybe there is direct connection
        // in HashSet.contain -> O(1)
        if (edges.contains(y)) {
            return 1;
        }


        return 0;
    }


    public static void main(String[] args) {
//        mainStarter();
        test();
    }

    private static void mainStarter() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        HashSet<Integer>[] adj = getGraph(n);
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(reach(adj, x, y));
    }

    private static HashSet<Integer>[] getGraph(int n) {
        HashSet<Integer>[] adj = (HashSet<Integer>[]) new HashSet[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new HashSet<Integer>();
        }
        return adj;
    }

    private static void test() {
        int n = 4;
        int m = 4;

        HashSet<Integer>[] adj = getGraph(n);
        int[][] edges = new int[][]{{1, 2}, {3, 2}, {4, 3}, {1, 4}};

        int x, y;
        for (int i = 0; i < m; i++) {
            x = edges[i][0];
            y = edges[i][1];
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }

        x = 1 - 1;
        y = 4 - 1;

        System.out.println(reach(adj, x, y));

        System.out.println("End");
    }
}


import java.util.*;

/**
 * Not finished, assignment 2
 */
public class StronglyConnected {
    private static int numberOfStronglyConnectedComponents(ArrayList<Integer>[] adj) {
        ArrayList<Integer>[] reverseGraph = reverseGraph(adj);
        int used[] = new int[adj.length];
        int visited[] = new int[adj.length];
        //write your code here

//        explore(reverseGraph, )

        return 0;
    }

    private static ArrayList<Integer>[] reverseGraph(ArrayList<Integer>[] adj) {
        int n = adj.length;
        ArrayList<Integer>[] reversedGraph = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            reversedGraph[i] = new ArrayList<Integer>();
        }

        for (int i = 0; i < n; i++) {
            ArrayList<Integer> edges = adj[i];
            for (int j = 0; j < edges.size(); j++) {
                reversedGraph[edges.get(j)].add(i);
            }
        }

        return reversedGraph;
    }


    private static void dfs(ArrayList<Integer>[] adj, int vertex) {
        int preIndex[] = new int[adj.length];
        int postIndex[] = new int[adj.length];
        int visited[] = new int[adj.length];
        Stack<Integer> stack = new Stack<>();
        stack.push(vertex);
        int index = 1;
        while (!stack.isEmpty()) {
            ArrayList<Integer> currentEdges = adj[stack.peek()];
            visited[stack.peek()] = 1;

            preIndex[stack.peek()] = index++;
            if (currentEdges.isEmpty()) {
                postIndex[stack.peek()] = index++;
                stack.pop();
            } else {
                for (int i = 0; i < currentEdges.size(); i++) {
                    if (visited[currentEdges.get(i)] != 1) {
                        // continue dfs
                        stack.push(currentEdges.get(i));
                    }
                }
            }
        }


    }

    private static int explore(ArrayList<Integer>[] adj, int[] visited, int vertex, int scc) {

        Stack<Integer> stack = new Stack<>();
        stack.push(vertex);

        while (!stack.isEmpty()) {
            ArrayList<Integer> currentEdges = adj[stack.peek()];
            visited[stack.peek()] = 1;

            if (currentEdges.isEmpty()) {
                stack.pop();
            } else {
                for (int i = 0; i < currentEdges.size(); i++) {
                    if (visited[currentEdges.get(i)] != 1) {
                        // continue dfs
                        stack.push(currentEdges.get(i));
                    } else {
                        // remove edge because connected vertex is deleted
                        currentEdges.remove(i);
                    }
                }
            }
        }

        return scc++;
    }

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        int m = scanner.nextInt();
//        ArrayList<Integer>[] adj = getGraph(n);
//        for (int i = 0; i < n; i++) {
//            adj[i] = new ArrayList<Integer>();
//        }
//        for (int i = 0; i < m; i++) {
//            int x, y;
//            x = scanner.nextInt();
//            y = scanner.nextInt();
//            adj[x - 1].add(y - 1);
//        }
//        System.out.println(numberOfComponents(adj));

        test();
    }

    private static ArrayList<Integer>[] getGraph(int n) {
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        return adj;
    }


    private static void test() {
        int n = 4;
        int m = 4;

        ArrayList<Integer>[] adj = getGraph(n);

        int[][] edges = new int[][]{{1, 2}, {4, 1}, {2, 3}, {3, 1}};
        int x, y;
        for (int i = 0; i < m; i++) {
            x = edges[i][0];
            y = edges[i][1];
            adj[x - 1].add(y - 1);
        }

        System.out.println(numberOfStronglyConnectedComponents(adj));

        System.out.println();
        n = 5;
        m = 7;

        adj = getGraph(n);
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        edges = new int[][]{{2, 1}, {3, 2}, {3, 1}, {4, 3}, {4, 1}, {5, 2}, {5, 3}};
        for (int i = 0; i < m; i++) {
            x = edges[i][0];
            y = edges[i][1];
            adj[x - 1].add(y - 1);
        }
        System.out.println(numberOfStronglyConnectedComponents(adj));
    }
}


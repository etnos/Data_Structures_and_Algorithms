package assignment2;

import java.util.*;

public class Acyclicity {
//    private static int acyclic(ArrayList<Integer>[] adj) {
//        //write your code here
//
//        LinkedList<Integer> stack = new LinkedList<>();
//        LinkedList<Integer> result = new LinkedList<>();
//        HashSet<Integer> visited = new HashSet<>();
//        HashSet<Integer> deleted = new HashSet<>();
//
//        for (int i = 0; i < adj.length; i++) {
//            if (!visited.contains(i)) {
//                visited.add(i);
//                stack.push(i);
//                if (!linearOrder(adj, visited, stack, result, deleted)) {
//                    return 1;
//                }
//            }
//
//        }
//        return 0;
//    }
//
//    private static boolean linearOrder(ArrayList<Integer>[] adj, HashSet<Integer> visited, LinkedList<Integer> stack, LinkedList<Integer> result, HashSet<Integer> deleted) {
//        while (!stack.isEmpty()) {
//            int current = stack.peek();
//            ArrayList<Integer> currentEdges = adj[current];
//
//            if (currentEdges.isEmpty()) {
//                result.push(current);
//                deleted.add(current);
//                stack.poll();
//            } else {
//                boolean isSink = true, isCycle = true;
//                for (Integer edge : currentEdges) {
//                    if (!deleted.contains(edge)) {
//                        isSink = false;
//
//                        if (!visited.contains(edge)) {
//                            isCycle = false;
//                            stack.push(edge);
//                            visited.add(edge);
//                        }
//                    }
//                }
//
//                if (isSink) {
//                    result.push(current);
//                    deleted.add(current);
//                    stack.poll();
//                } else if (isCycle) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

    private static int acyclic(ArrayList<Integer>[] adj) {

        HashSet<Integer> visited = new HashSet<>();
        HashSet<Integer> tempVisited = new HashSet<>();

        for (int i = 0; i < adj.length; i++) {

            if (adj[i].size() > 0) {
                if (explore(i, visited, tempVisited, adj) == 1) {
                    return 1;
                }
            }
        }


        return 0;
    }


    private static int explore(int current, HashSet<Integer> visited, HashSet<Integer> tempVisited, ArrayList<Integer>[] adj) {

        if (tempVisited.contains(current)) {
            return 1;
        } else {
            tempVisited.add(current);

            ArrayList<Integer> list = adj[current];

            for (int j = 0; j < list.size(); j++) {
                int number = list.get(j);
                if (!visited.contains(number)) {
                    if (explore(number, visited, tempVisited, adj) == 1) {
                        return 1;
                    }
                }
            }
            tempVisited.remove(current);
            visited.add(current);
            return 0;
        }

    }

    public static void main(String[] args) {
        mainStarter();
//        test1();
//        test2();
    }

    private static void mainStarter() {
        ArrayList<Integer>[] adj = getGraph();
        System.out.println(acyclic(adj));
    }

    private static ArrayList<Integer>[] getGraph() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
        }
        return adj;
    }


    private static void test1() {
        int n = 4;
        int m = 4;

        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        int[][] edges = new int[][]{{1, 2}, {4, 1}, {2, 3}, {3, 1}};
        int x, y;
        for (int i = 0; i < m; i++) {
            x = edges[i][0];
            y = edges[i][1];
            adj[x - 1].add(y - 1);
        }


        System.out.println(acyclic(adj));
    }

    private static void test2() {
        int n = 5;
        int m = 7;

        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        int[][] edges = new int[][]{{1, 2}, {2, 3}, {1, 3}, {3, 4}, {1, 4}, {2, 5}, {3, 5}};
        int x, y;
        for (int i = 0; i < m; i++) {
            x = edges[i][0];
            y = edges[i][1];
            adj[x - 1].add(y - 1);
        }


        System.out.println(acyclic(adj));

        n = 4;
        m = 4;

        adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        edges = new int[][]{{1, 2}, {2, 4}, {1, 3}, {3, 2}};
        for (int i = 0; i < m; i++) {
            x = edges[i][0];
            y = edges[i][1];
            adj[x - 1].add(y - 1);
        }


        System.out.println(acyclic(adj));
    }
}


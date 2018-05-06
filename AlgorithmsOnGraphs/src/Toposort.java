import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class Toposort {
    private static ArrayList<Integer> toposort(ArrayList<Integer>[] adj) {
        int used[] = new int[adj.length];
        ArrayList<Integer> order = new ArrayList<>();
        //write your code here
        for (int i = 0; i < adj.length; i++) {
            if (used[i] != 1) {
                dfs(adj, used, order, 0, i);
            }
        }
        return order;
    }

    private static void dfs(ArrayList<Integer>[] adj, int[] used, ArrayList<Integer> order, int s, int vertex) {
        //write your code here

        Stack<Integer> stack = new Stack<>();
        stack.add(vertex);
        while (!stack.isEmpty()) {
            if (used[stack.peek()] == 1) {
                // this element already used
                stack.pop();
            } else {
                ArrayList<Integer> currentEdges = adj[stack.peek()];
                if (currentEdges.isEmpty()) {
                    // the vertex is a sink, no edges
                    used[stack.peek()] = 1;
                    order.add(stack.pop());
                } else {
                    for (int i = 0; i < currentEdges.size(); i++) {
                        if (used[currentEdges.get(i)] != 1) {
                            // continue dfs
                            stack.push(currentEdges.get(i));
                            break;
                        } else {
                            // remove edge because connected vertex is deleted
                            currentEdges.remove(i);
                        }
                    }

                    if (currentEdges.isEmpty()) {
                        // the vertex is a sink, no not deleted edges
                        used[stack.peek()] = 1;
                        order.add(stack.pop());
                    }
                }
            }
        }

    }


    public static void main(String[] args) {
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
        ArrayList<Integer> order = toposort(adj);
        Collections.reverse(order);
        for (int x : order) {
            System.out.print((x + 1) + " ");
        }

//        test1();
    }


    private static void test1() {
        int n = 4;
        int m = 3;

        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        int[][] edges = new int[][]{{1, 2}, {4, 1}, {3, 1}};
        int x, y;
        for (int i = 0; i < m; i++) {
            x = edges[i][0];
            y = edges[i][1];
            adj[x - 1].add(y - 1);
        }

        ArrayList<Integer> toposort = toposort(adj);
        Collections.reverse(toposort);
        for (Integer i : toposort) {
            System.out.print((i + 1) + " ");
        }


        System.out.println();
        n = 4;
        m = 1;

        adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        edges = new int[][]{{1, 3}};
        for (int i = 0; i < m; i++) {
            x = edges[i][0];
            y = edges[i][1];
            adj[x - 1].add(y - 1);
        }

        toposort = toposort(adj);
        Collections.reverse(toposort);
        for (Integer i : toposort) {
            System.out.print((i + 1) + " ");
        }

        System.out.println();
        n = 5;
        m = 7;

        adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        edges = new int[][]{{2, 1}, {3, 2}, {3, 1}, {4, 3}, {4, 1}, {5, 2}, {5, 3}};
        for (int i = 0; i < m; i++) {
            x = edges[i][0];
            y = edges[i][1];
            adj[x - 1].add(y - 1);
        }

        toposort = toposort(adj);
        Collections.reverse(toposort);
        for (Integer i : toposort) {
            System.out.print((i + 1) + " ");
        }

        System.out.println();
        n = 5;
        m = 10;

        adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        edges = new int[][]{{4, 5}, {3, 1}, {2, 1}, {2, 3}, {5, 1}, {2, 5}, {4, 1}, {2, 4}, {3, 4}, {3, 5}};
        for (int i = 0; i < m; i++) {
            x = edges[i][0];
            y = edges[i][1];
            adj[x - 1].add(y - 1);
        }

        toposort = toposort(adj);
        Collections.reverse(toposort);
        for (Integer i : toposort) {
            System.out.print((i + 1) + " ");
        }

        System.out.println();
        n = 10;
        m = 20;

        adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        edges = new int[][]{{10, 2}, {10, 8}, {5, 4}, {3, 2}, {8, 4}, {10, 4}, {1, 2}, {9, 7}, {2, 8}, {3, 10}, {7, 6}, {1, 5}, {1, 8}, {1, 6}, {1, 9}, {3, 6}, {7, 10}, {9, 4}, {9, 5}, {5, 3}};
        for (int i = 0; i < m; i++) {
            x = edges[i][0];
            y = edges[i][1];
            adj[x - 1].add(y - 1);
        }

        toposort = toposort(adj);
        Collections.reverse(toposort);
        for (Integer i : toposort) {
            System.out.print((i + 1) + " ");
        }
    }

}


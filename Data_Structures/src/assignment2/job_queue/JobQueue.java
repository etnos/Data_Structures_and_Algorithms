package assignment2.job_queue;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class JobQueue {
    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;
    private PriorityQueue<Worker> minPriQueue;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
//        test();
//        autoTest();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs() {
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        minPriQueue = new PriorityQueue<>(numWorkers);
        for (int i = 0; i < numWorkers; i++) {
            minPriQueue.add(new Worker(i));
        }
        for (int i = 0; i < jobs.length; i++) {
            int duration = jobs[i];
            Worker bestWorker = minPriQueue.poll();
            assignedWorker[i] = bestWorker.index;
            startTime[i] = bestWorker.currentFinishTime;
            bestWorker.currentFinishTime += duration;
            minPriQueue.add(bestWorker);
        }
    }


    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    private static void test() {
        int numWorkers = 2;
        int[] jobs = new int[]{1, 2, 3, 4, 5};
        JobQueue jobQueue = new JobQueue();
        jobQueue.jobs = jobs;
        jobQueue.numWorkers = numWorkers;
        jobQueue.assignJobs();

        for (int i = 0; i < jobs.length; ++i) {
            System.out.println(jobQueue.assignedWorker[i] + " " + jobQueue.startTime[i]);
        }
        System.out.println();

        numWorkers = 4;
        jobs = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        jobQueue = new JobQueue();
        jobQueue.jobs = jobs;
        jobQueue.numWorkers = numWorkers;
        jobQueue.assignJobs();

        for (int i = 0; i < jobs.length; ++i) {
            System.out.println(jobQueue.assignedWorker[i] + " " + jobQueue.startTime[i]);
        }

    }

    private static void autoTest() {
        try {
            String data = new String(Files.readAllBytes(Paths.get("src/assignment2/job_queue/tests/02")));
            String result = new String(Files.readAllBytes(Paths.get("src/assignment2/job_queue/tests/02.a")));

            testCase(data, result, "02");

            data = new String(Files.readAllBytes(Paths.get("src/assignment2/job_queue/tests/08")));
            result = new String(Files.readAllBytes(Paths.get("src/assignment2/job_queue/tests/08.a")));

            testCase(data, result, "08");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testCase(String data, String result, String fileName) {
        StringTokenizer tok = new StringTokenizer(data);
        int numWorkers = Integer.parseInt(tok.nextToken());
        int n = Integer.parseInt(tok.nextToken());

        int[] jobs = new int[n];
        int[] results1 = new int[n];
        long[] results2 = new long[n];

        for (int i = 0; i < n; i++) {
            jobs[i] = Integer.parseInt(tok.nextToken());
        }

        tok = new StringTokenizer(result);
        for (int i = 0; i < n; i++) {
            results1[i] = Integer.parseInt(tok.nextToken());
            results2[i] = Long.parseLong(tok.nextToken());
        }

        JobQueue jobQueue = new JobQueue();
        jobQueue.jobs = jobs;
        jobQueue.numWorkers = numWorkers;
        jobQueue.assignJobs();

        for (int i = 0; i < jobs.length; ++i) {
            if (jobQueue.assignedWorker[i] != results1[i] || jobQueue.startTime[i] != results2[i]) {
                System.out.println("Wrong " + fileName);
                System.out.println(i + " " + jobQueue.assignedWorker[i] + " " + results1[i] + "  " + jobQueue.startTime[i] + " " + results2[i]);
                break;
            }
        }
    }

    static class Worker implements Comparable<Worker> {
        int index;
        long currentFinishTime;

        public Worker(int index) {
            this.index = index;
        }

        /**
         * Sort based on the finishTime and index
         *
         * @param w
         * @return
         */
        @Override
        public int compareTo(Worker w) {
            if (currentFinishTime < w.currentFinishTime) {
                return -1;
            } else if (currentFinishTime == w.currentFinishTime) {
                if (index < w.index) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (currentFinishTime > w.currentFinishTime) {
                return 1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return "" + index;
        }
    }
}

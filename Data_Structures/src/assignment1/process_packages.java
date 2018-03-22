package assignment1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class Request {
    public Request(int arrival_time, int process_time) {
        this.arrival_time = arrival_time;
        this.process_time = process_time;
    }

    public int arrival_time;
    public int process_time;
}

class Response {
    public Response(boolean dropped, int start_time) {
        this.dropped = dropped;
        this.start_time = start_time;
    }

    public boolean dropped;
    public int start_time;
}

class Buffer {
    public Buffer(int size) {
        this.size_ = size;
        this.finish_time = new LinkedList<Integer>();
    }

    public Response Process(Request request) {
        // write your code here

        for (int i = 0; i < finish_time.size(); i++) {
            if (request.arrival_time >= finish_time.getFirst()) {
                // remove processed requests
                finish_time.pollFirst();
            } else {
                break;
            }
        }


        if (finish_time.isEmpty()) {
            // if buffer is empty, add request
            finish_time.add(request.arrival_time + request.process_time);
            return new Response(false, request.arrival_time);
        } else if (finish_time.size() < size_) {
            if (request.arrival_time <= finish_time.getLast()) {
                int currentLastFinish = finish_time.getLast();
                finish_time.add(finish_time.getLast() + request.process_time);
                return new Response(false, currentLastFinish);
            } else {
                finish_time.add(request.arrival_time + request.process_time);
                return new Response(false, request.arrival_time);
            }
        }
        return new Response(true, -1);
    }

    //    private
    private int size_;
    private LinkedList<Integer> finish_time;
}

class process_packages {
    private static ArrayList<Request> ReadQueries(Scanner scanner) throws IOException {
        int requests_count = scanner.nextInt();
        ArrayList<Request> requests = new ArrayList<Request>();
        for (int i = 0; i < requests_count; ++i) {
            int arrival_time = scanner.nextInt();
            int process_time = scanner.nextInt();
            requests.add(new Request(arrival_time, process_time));
        }
        return requests;
    }

    private static ArrayList<Response> ProcessRequests(ArrayList<Request> requests, Buffer buffer) {
        ArrayList<Response> responses = new ArrayList<Response>();
        for (int i = 0; i < requests.size(); ++i) {
            responses.add(buffer.Process(requests.get(i)));
        }
        return responses;
    }

    private static void PrintResponses(ArrayList<Response> responses) {
        for (int i = 0; i < responses.size(); ++i) {
            Response response = responses.get(i);
            if (response.dropped) {
                System.out.println(-1);
            } else {
                System.out.println(response.start_time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int buffer_max_size = scanner.nextInt();
        Buffer buffer = new Buffer(buffer_max_size);

        ArrayList<Request> requests = ReadQueries(scanner);
        ArrayList<Response> responses = ProcessRequests(requests, buffer);
        PrintResponses(responses);
//        test();
//        generatedTest();
    }

    private static void test() {
        int buffer_max_size = 1;
        Buffer buffer = new Buffer(buffer_max_size);
        ArrayList<Request> requests = new ArrayList<Request>();
        requests.add(new Request(0, 1));
        requests.add(new Request(0, 1));

        ArrayList<Response> responses = ProcessRequests(requests, buffer);
        ArrayList<Response> responsesExpected = new ArrayList<>(2);
        responsesExpected.add(new Response(false, 0));
        responsesExpected.add(new Response(true, -1));

        testCase(responses, responsesExpected, "test_1");


        buffer_max_size = 1;
        buffer = new Buffer(buffer_max_size);
        requests = new ArrayList<Request>();
        responses = ProcessRequests(requests, buffer);
        responsesExpected = new ArrayList<>();

        testCase(responses, responsesExpected, "test_2");


        buffer_max_size = 1;
        buffer = new Buffer(buffer_max_size);
        requests = new ArrayList<Request>();
        requests.add(new Request(0, 1));
        requests.add(new Request(1, 1));
        responses = ProcessRequests(requests, buffer);
        responsesExpected = new ArrayList<>();
        responsesExpected.add(new Response(false, 0));
        responsesExpected.add(new Response(false, 1));

        testCase(responses, responsesExpected, "test_3");

        buffer_max_size = 2;
        buffer = new Buffer(buffer_max_size);
        requests = new ArrayList<Request>();
        requests.add(new Request(0, 1));
        requests.add(new Request(0, 1));
        responses = ProcessRequests(requests, buffer);
        responsesExpected = new ArrayList<>();
        responsesExpected.add(new Response(false, 0));
        responsesExpected.add(new Response(false, 1));

        testCase(responses, responsesExpected, "test_4");
    }


    private static void generatedTest() {
        try {
            String name;
            for (int i = 1; i <= 22; i++) {
                if (i < 10) {
                    name = "0" + String.valueOf(i);
                } else {
                    name = String.valueOf(i);
                }
                String data = new String(Files.readAllBytes(Paths.get("src/assignment1/network/tests/" + name)));
                String result = new String(Files.readAllBytes(Paths.get("src/assignment1/network/tests/" + name + ".a")));

                StringTokenizer tok = new StringTokenizer(data);
                int buffer_max_size = Integer.parseInt(tok.nextToken());
                int n = Integer.parseInt(tok.nextToken());

                int arrive;
                int process;
                Buffer buffer = new Buffer(buffer_max_size);
                ArrayList<Request> requests = new ArrayList<Request>(n);
                for (int j = 0; j < n; j++) {
                    arrive = Integer.parseInt(tok.nextToken());
                    process = Integer.parseInt(tok.nextToken());
                    requests.add(new Request(arrive, process));
                }

                ArrayList<Response> responses = ProcessRequests(requests, buffer);
                ArrayList<Response> responsesExpected = new ArrayList<>(n);
                tok = new StringTokenizer(result);
                for (int j = 0; j < n; j++) {
                    responsesExpected.add(new Response(false, Integer.parseInt(tok.nextToken())));
                }

                testCase(responses, responsesExpected, name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testCase(ArrayList<Response> responses, ArrayList<Response> responsesExpected) {
        testCase(responses, responsesExpected, "");
    }

    private static void testCase(ArrayList<Response> responses, ArrayList<Response> responsesExpected, String fileName) {
        for (int i = 0; i < responses.size(); i++) {
            boolean isStartTime = responses.get(i).start_time == responsesExpected.get(i).start_time;
            if (!isStartTime) {
                System.out.println("WRONG local " + responses.get(i).start_time + " expected " + responsesExpected.get(i).start_time + " file: " + fileName);
                System.exit(0);
            }
        }
    }
}

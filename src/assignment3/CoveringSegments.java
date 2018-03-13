package assignment3;

import java.util.*;

public class CoveringSegments {

    private static class Segment {
        int start, end;

        Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        Comparator<Segment> comparator = new SegmentComparator();
        ArrayList<Segment> list = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            int start, end;
            start = scanner.nextInt();
            end = scanner.nextInt();
            list.add(new Segment(start, end));
        }

        list.sort(comparator);
        ArrayList<Integer> resultList = optimalPointsFast(list);
        System.out.println(resultList.size());
        for (int point : resultList) {
            System.out.print(point + " ");
        }

//        hardcodedTest();
    }

    private static ArrayList<Integer> optimalPointsFast(ArrayList<Segment> dataList) {
        //write your code here

        ArrayList<Integer> resultList = new ArrayList<>();
        if (!dataList.isEmpty()) {
            int minEnd = dataList.get(0).end;
            Segment segment;

            for (int i = 1; i < dataList.size(); i++) {
                segment = dataList.get(i);
                if (segment.start > minEnd) {
                    resultList.add(minEnd);
                    minEnd = segment.end;
                } else if (segment.end < minEnd) {
                    minEnd = segment.end;
                }
            }
            resultList.add(minEnd);
        }

        return resultList;
    }

    static class SegmentComparator implements Comparator<Segment> {

        @Override
        public int compare(Segment o1, Segment o2) {
            int isEqual = Integer.compare(o1.start, o2.start);
            if (isEqual == 0) {
                isEqual = Integer.compare(o1.end, o2.end);
            }

            return isEqual;
        }
    }

    private static void hardcodedTest() {
        int n = 3;

        Comparator<Segment> comparator = new SegmentComparator();
        ArrayList<Segment> list = new ArrayList<>(n);

        list.add(new Segment(1, 3));
        list.add(new Segment(2, 5));
        list.add(new Segment(3, 6));

        list.sort(comparator);

        ArrayList<Integer> resultList = optimalPointsFast(list);
        System.out.println(resultList);


        list.clear();
        list.add(new Segment(2, 5));
        list.add(new Segment(4, 7));
        list.add(new Segment(3, 6));
        list.add(new Segment(1, 3));

        list.sort(comparator);

        resultList = optimalPointsFast(list);
        System.out.println(resultList);


        list.clear();
        list.add(new Segment(4, 7));
        list.add(new Segment(1, 3));
        list.add(new Segment(2, 5));
        list.add(new Segment(5, 6));

        list.sort(comparator);

        resultList = optimalPointsFast(list);
        System.out.println(resultList);


    }
}
 

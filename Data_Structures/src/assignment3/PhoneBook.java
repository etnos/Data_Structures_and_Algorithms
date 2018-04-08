package assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class PhoneBook {

    private FastScanner in = new FastScanner();
    // Keep list of all existing (i.e. not deleted yet) contacts.
    private MyHashMap hashMap = new MyHashMap();
    private List<Contact> contacts = new ArrayList<>();

    public static void main(String[] args) {
        new PhoneBook().processQueries();
//        new assignment3.PhoneBook().test();
    }

    private Query readQuery() {
        String type = in.next();
        int number = in.nextInt();
        if (type.equals("add")) {
            String name = in.next();
            return new Query(type, name, number);
        } else {
            return new Query(type, number);
        }
    }

    private void writeResponse(String response) {
        System.out.println(response);
    }

    private String processQueryNaive(Query query) {
        if (query.type.equals("add")) {
            // if we already have contact with such number,
            // we should rewrite contact's name
            boolean wasFound = false;
            for (Contact contact : contacts)
                if (contact.number == query.number) {
                    contact.name = query.name;
                    wasFound = true;
                    break;
                }
            // otherwise, just add it
            if (!wasFound)
                contacts.add(new Contact(query.name, query.number));
        } else if (query.type.equals("del")) {
            for (Iterator<Contact> it = contacts.iterator(); it.hasNext(); )
                if (it.next().number == query.number) {
                    it.remove();
                    break;
                }
        } else {
            String response = "not found";
            for (Contact contact : contacts)
                if (contact.number == query.number) {
                    response = contact.name;
                    break;
                }
//            writeResponse(response);
            return response;
        }

        return "";
    }

    private String processQuery(Query query) {
        switch (query.type) {
            case "add":
                // if we already have contact with such number,
                // we should rewrite contact's name
                hashMap.add(query);
                break;
            case "del":
                hashMap.del(query);
                break;
            case "find":
                writeResponse(hashMap.find(query));
//                return hashMap.find(query);
                break;
        }

        return "";
    }

    public void processQueries() {
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i)
            processQuery(readQuery());
    }

    static class Contact {
        String name;
        int number;

        public Contact(String name, int number) {
            this.name = name;
            this.number = number;
        }
    }

    static class Query {
        String type;
        String name;
        int number;

        public Query(String type, String name, int number) {
            this.type = type;
            this.name = name;
            this.number = number;
        }

        public Query(String type, int number) {
            this.type = type;
            this.number = number;
        }

        @Override
        public String toString() {
            return "type: " + type + ", name: " + name + ", number: " + number;
        }
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }


    class MyHashMap {
        int hashMapSize = 1000;
        private ArrayList<LinkedList<Integer>> hashMap = new ArrayList<>(hashMapSize);
        private ArrayList<Contact> contacts = new ArrayList<>();

        MyHashMap() {
            for (int i = 0; i < hashMapSize; i++) {
                hashMap.add(new LinkedList<>());
            }
        }

        public void add(Query query) {
            int index = hashPosition(hash(query.number));
            LinkedList<Integer> contactsList = hashMap.get(index);


            boolean isChanged = false;
            for (Integer position : contactsList) {
                Contact contact = contacts.get(position);
                if (contact != null && contact.number == query.number) {
                    contacts.set(position, new Contact(query.name, query.number));
                    isChanged = true;
                    break;
                }
            }

            if (!isChanged) {
                contacts.add(new Contact(query.name, query.number));
                contactsList.add(contacts.size() - 1);
            }

//            if (contacts.size() >= hashMapSize * 0.9) {
//                // resize HashMap;
//                resize(hashMapSize * 2);
//            }


        }

        public String find(Query query) {
            int index = hashPosition(hash(query.number));
            LinkedList<Integer> positionsList = hashMap.get(index);
            String response = "not found";
            for (Integer position : positionsList) {
                Contact contact = contacts.get(position);
                if (contact != null && contact.number == query.number) {
                    response = contact.name;
                    break;
                }
            }


            return response;
        }

        public void del(Query query) {
            int index = hashPosition(hash(query.number));
            LinkedList<Integer> positionsList = hashMap.get(index);
            if (positionsList != null) {
                for (Integer position : positionsList) {
                    Contact contact = contacts.get(position);
                    if (contact != null && contact.number == query.number) {
                        contacts.set(position, null);
                        positionsList.remove(position);
                        break;
                    }
                }
            }
        }

        /**
         * Something is wrong here, it does not work correctly
         *
         * @param newHashMapSize
         */
        private void resize(int newHashMapSize) {
            ArrayList<LinkedList<Integer>> newHashMap = new ArrayList<>(newHashMapSize);
            for (int i = 0; i < newHashMapSize; i++) {
                newHashMap.add(new LinkedList<>());
            }
            // go through old hash
            for (LinkedList<Integer> positionsList : hashMap) {
                if (positionsList != null) {
                    for (int i = 0; i < positionsList.size(); i++) {
                        Integer position = positionsList.get(i);

                        // find object by saved position
                        Contact contact = contacts.get(position);
                        if (contact != null) {
                            // recalculate new Hash
                            int newHashPosition = hash(contact.number) % newHashMapSize;

                            // save into new HashMap
                            LinkedList<Integer> contactsList = newHashMap.get(newHashPosition);
                            contactsList.add(position);
                        }
                    }
                }
            }

            hashMap = newHashMap;
        }

        private int hashPosition(int hash) {
            return hash % hashMapSize;
        }

        int a = 34;
        int b = 2;
        int p = 2000019;

        public int hash(int number) {
            return (a * number + b) % p;
        }
    }


    private void test() {
        int n = 12;
        hashMap = new MyHashMap();
        List<Query> list = new ArrayList<>(n);
        list.add(new Query("add", "police", 911));
        list.add(new Query("add", "Mom", 76213));
        list.add(new Query("add", "Bob", 17239));
        list.add(new Query("find", 76213));
        list.add(new Query("find", 910));
        list.add(new Query("find", 911));
        list.add(new Query("del", 910));
        list.add(new Query("del", 911));
        list.add(new Query("find", 911));
        list.add(new Query("find", 76213));
        list.add(new Query("add", "daddy", 76213));
        list.add(new Query("find", 76213));


        for (Query query : list) {
            String res1 = processQuery(query);
            String res2 = processQueryNaive(query);
            if (!res1.equals(res2)) {
                System.out.println("Wrong");
                return;
            }
        }

        System.out.println();
        n = 8;
        hashMap = new MyHashMap();
        list = new ArrayList<>(n);
        list.add(new Query("find", 3839442));
        list.add(new Query("add", "me", 123456));
        list.add(new Query("add", "granny", 0));
        list.add(new Query("find", 0));
        list.add(new Query("find", 123456));
        list.add(new Query("del", 0));
        list.add(new Query("del", 0));
        list.add(new Query("find", 0));

        for (Query query : list) {
            String res1 = processQuery(query);
            String res2 = processQueryNaive(query);
            if (!res1.equals(res2)) {
                System.out.println("Wrong");
                return;
            }
        }

        System.out.println();
        hashMap = new MyHashMap();
        list = new ArrayList<>();


        list.add(new Query("find", 1));
        list.add(new Query("add", "m1", 1));
        list.add(new Query("add", "m2", 2));
        list.add(new Query("find", 0));
        list.add(new Query("find", 1));
        list.add(new Query("del", 2));
        list.add(new Query("del", 1));
        list.add(new Query("find", 1));
        list.add(new Query("find", 0));
        list.add(new Query("add", "m1", 1));
        list.add(new Query("add", "m2", 2));
        list.add(new Query("add", "m2", 2));
        list.add(new Query("find", 1));
        list.add(new Query("find", 2));
        list.add(new Query("add", "m2", 3));
        list.add(new Query("find", 2));
        list.add(new Query("find", 3));
        list.add(new Query("add", "m3", 2));
        list.add(new Query("find", 2));
        list.add(new Query("find", 3));
        list.add(new Query("add", "m3", 2));
        list.add(new Query("add", "m3", 3));
        list.add(new Query("find", 1));
        list.add(new Query("find", 2));
        list.add(new Query("find", 3));
        list.add(new Query("del", 3));
        list.add(new Query("find", 1));
        list.add(new Query("find", 2));
        list.add(new Query("find", 3));

        for (int i = 0; i < list.size(); i++) {
            Query query = list.get(i);
            String res1 = processQuery(query);
            String res2 = processQueryNaive(query);
            if (!res1.equals(res2)) {
                System.out.println("action " + i + " Wrong \n quick: " + res1 + " \n naive: " + res2);
                System.out.println("query: " + query);
                return;
            }
        }

        System.out.println();
        hashMap = new MyHashMap();
        list = new ArrayList<>();


        list.add(new Query("add", "m1", 1));
        list.add(new Query("add", "m2", 2));
        list.add(new Query("add", "m3", 3));


        list.add(new Query("add", "m1", 1));
        list.add(new Query("add", "m2", 2));
        list.add(new Query("add", "m3", 3));

        list.add(new Query("add", "m1", 1));
        list.add(new Query("add", "m2", 2));
        list.add(new Query("add", "m3", 3));
        list.add(new Query("del", 1));
        list.add(new Query("find", 0));
        list.add(new Query("find", 1));
        list.add(new Query("find", 2));
        list.add(new Query("del", 2));
        list.add(new Query("find", 0));
        list.add(new Query("find", 1));
        list.add(new Query("find", 2));
        list.add(new Query("find", 3));
        list.add(new Query("del", 2));
        list.add(new Query("find", 0));
        list.add(new Query("find", 1));
        list.add(new Query("find", 2));
        list.add(new Query("find", 3));

        list.add(new Query("del", 3));
        list.add(new Query("find", 0));
        list.add(new Query("find", 1));
        list.add(new Query("find", 2));
        list.add(new Query("find", 3));

        list.add(new Query("add", "m1", 1));
        list.add(new Query("add", "m2", 2));
        list.add(new Query("add", "m3", 3));

        list.add(new Query("find", 0));
        list.add(new Query("find", 1));
        list.add(new Query("find", 2));
        list.add(new Query("find", 3));

//        list.add(new Query("del", 2));
//        list.add(new Query("find", 1));
//        list.add(new Query("find", 0));
//        list.add(new Query("add", "m1", 1));
//        list.add(new Query("add", "m2", 2));
//        list.add(new Query("add", "m2", 2));
//        list.add(new Query("find", 1));
//        list.add(new Query("find", 2));
//        list.add(new Query("add", "m2", 3));
//        list.add(new Query("find", 2));
//        list.add(new Query("find", 3));
//        list.add(new Query("add", "m3", 2));
//        list.add(new Query("find", 2));
//        list.add(new Query("find", 3));
//        list.add(new Query("add", "m3", 2));
//        list.add(new Query("add", "m3", 3));
//        list.add(new Query("find", 1));
//        list.add(new Query("find", 2));
//        list.add(new Query("find", 3));
//        list.add(new Query("del", 3));
        list.add(new Query("find", 1));
        list.add(new Query("find", 2));
        list.add(new Query("find", 3));

        for (int i = 0; i < list.size(); i++) {
            Query query = list.get(i);
            String res1 = processQuery(query);
            String res2 = processQueryNaive(query);
            if (!res1.equals(res2)) {
                System.out.println("action " + i + " Wrong \n quick: " + res1 + " \n naive: " + res2);
                System.out.println("query: " + query);
                return;
            }
        }
    }
}

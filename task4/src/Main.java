package ru.pl;

import java.util.*;

public class Main {

    public static String f(String s1, String s2) {

        List<HashMap<Character, Integer>> automate = new ArrayList<HashMap<Character, Integer>>();

        Set<Integer> acceptedStates = new HashSet<Integer>();

        int j = 0;
        for (int i = 0; i < s2.length(); i++) {
            HashMap<Character, Integer> map = new HashMap<Character, Integer>();
            if (s2.charAt(i) != '*') {
                map.put(s2.charAt(i), j + 1);
                automate.add(map);
            } else {
                while (i < s2.length() && s2.charAt(i) == '*') {
                    i++;
                }
                map.put('*', j);
                if (i < s2.length()) {
                    map.put(s2.charAt(i), j + 1);
                } else {
                    acceptedStates.add(j);
                }
                automate.add(map);
            }
            if (i == s2.length() - 1) {
                automate.add(new HashMap<Character, Integer>());
                acceptedStates.add(j + 1);
            }
            j++;
        }

        return isMatch(s1, automate, acceptedStates);
    }

    public static String isMatch(String s1, List<HashMap<Character, Integer>> automate, Set<Integer> acceptedStates) {
        int index = 0;

        int currentState = 0;

        int asteriskState = -1;

        while (true) {
            if (index == s1.length()) {
                if (acceptedStates.contains(currentState)) {
                    return "OK";
                } else return "KO";
            } else if (automate.get(currentState).containsKey(s1.charAt(index))) {
                currentState = automate.get(currentState).get(s1.charAt(index));
                index++;

            } else if (automate.get(currentState).containsKey('*')) {
                asteriskState = currentState;
                currentState = automate.get(currentState).get('*');
                index++;
            } else {
                if (asteriskState != -1) {
                   currentState = asteriskState;
                   continue;
                }
                return "KO";
            }
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String s1 = scanner.nextLine();
        String s2 = scanner.nextLine();

        System.out.println(f(s1, s2));
    }
}

package com.program;

import org.json.*;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.*;
import static java.time.temporal.ChronoUnit.MINUTES;

public class Main {

    /*
        Złożoność: O(n^2)
        Opis algorytmu:
        1. Utworzenie HashMapy w której kluczem są zmienne typu LocalTime, natomiast wartościami kolejne liczby naturalne od 0.
        2. Utworzenie macierzy sąsiedztwa w której wierzchołkami wartości Hashmapy, krawędziami wartości 0 lub 1 (w zależności od tego czy istnieje połączenie).
        3. Wyznaczenie wierzchołków (tablica availableArrays), które w macierzy sąsiedzctwa mają same 0. (Nie mają połączeń do innych wierzchołków).
        4. Wyznaczenie indeksów, które wskazują na to, że czasy spotkań się nakładają (uaktualnienie talicy availableArrays).
        5. Sprawdzanie w macierzy sąsiedztwa zakresu czasowego, czasu trwania spotkania, sprawdzenie czy tablica znajduje się w availableArrays.

        Autor: Michał Szmeja
     */

    private static HashMap<String, Integer> hashMap = new HashMap<>();

    private static LocalTime[] workingHoursComm = new LocalTime[2];

    private static ArrayList<Integer> indexesOfArraysToRemove = new ArrayList<>();

    static void workingHoursCommonPart(LocalTime[] timeArr) {
        LocalTime[] result = new LocalTime[2];
        if (timeArr[0].isAfter(timeArr[2]))
            workingHoursComm[0] = timeArr[0];
        else
            workingHoursComm[0] = timeArr[2];

        if (timeArr[1].isBefore(timeArr[3]))
            workingHoursComm[1] = timeArr[1];
        else
            workingHoursComm[1] = timeArr[3];
    }

    public static String getKeyByValue(HashMap<String, Integer> map, Integer value) {
        String strKey = null;

        for(HashMap.Entry entry: map.entrySet()){
            if(value.equals(entry.getValue())){
                strKey = (String) entry.getKey();
                break;
            }
        };

        return strKey;
    }

    static int[][] getMeetingsAdjMatrix(JSONArray a1, JSONArray a2) throws ParseException {
        List<LocalTime> vertices = new ArrayList<>();
        String start1, start2, end1, end2;

        int tSize = 0;

        if (a1.length() >= a2.length())
            tSize = a1.length();
        else tSize = a2.length();

        for (int i = 0; i < tSize; i++) {
            if (i < a1.length()) {
                start1 = a1.getJSONObject(i).getString("start");
                end1 = a1.getJSONObject(i).getString("end");
                vertices.add(LocalTime.parse(start1));
                vertices.add(LocalTime.parse(end1));
            }

            if (i < a2.length()) {
                start2 = a2.getJSONObject(i).getString("start");
                end2 = a2.getJSONObject(i).getString("end");
                vertices.add(LocalTime.parse(start2));
                vertices.add(LocalTime.parse(end2));
            }
        }

        // To remove duplicates
        Set<LocalTime> removedDuplicates = new LinkedHashSet<>(vertices);
        vertices.clear();
        vertices.addAll(removedDuplicates);
        if (!vertices.contains(workingHoursComm[0]))
           vertices.add(workingHoursComm[0]);
        if (!vertices.contains(workingHoursComm[1]))
            vertices.add(workingHoursComm[1]);
        java.util.Collections.sort(vertices);

        //for (int i = 0; i < vertices.size(); i++)
        //    System.out.println(vertices.get(i));

        int V = vertices.size();

        for (int i = 0; i < vertices.size(); i++)
            hashMap.put(String.valueOf(vertices.get(i)), i);

        int[][] resultMatrix = new int[V][V];

        // Edges
        for (int i = 0; i < tSize; i++) {

            if (i < a1.length()) {
                start1 = a1.getJSONObject(i).getString("start");
                end1 = a1.getJSONObject(i).getString("end");
                int from = hashMap.get(start1), to = hashMap.get(end1);
                resultMatrix[from][to] = 1;

                for (int y = from; y <= to; y++) {
                    if (y > from && y < to) {
                        //System.out.println("To remove " + y);
                        indexesOfArraysToRemove.add(y);
                    }
                }
            }

            if (i < a2.length()) {
                start2 = a2.getJSONObject(i).getString("start");
                end2 = a2.getJSONObject(i).getString("end");

                int from = hashMap.get(start2), to = hashMap.get(end2);
                resultMatrix[from][to] = 1;

                for (int y = from; y <= to; y++) {
                    if (y > from && y < to) {
                        //System.out.println("To remove " + y);
                        indexesOfArraysToRemove.add(y);
                    }
                }
            }
        }

        java.util.Collections.sort(indexesOfArraysToRemove);

        return  resultMatrix;
    }

    static LocalTime[][] findAvailableMeetingsRange(int[][] matrix, LocalTime meetingDuration) {

        LocalTime[][] result;

        int[] availableArrays = new int[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            int counter = 0;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 0)
                    counter++;
            if (counter == matrix.length) availableArrays[i] = 1;
        }}

        if (indexesOfArraysToRemove.size() > 0) {
            for (int i = 0; i < availableArrays.length; i++) {
                if (availableArrays[i] == 1) {
                    for (int y = 0; y < indexesOfArraysToRemove.size(); y++) {
                        if (i == indexesOfArraysToRemove.get(y)) {
                            availableArrays[i] = 0;
                            indexesOfArraysToRemove.remove(y);
                        }
                    }
                }
            }
        }

        result = new LocalTime[availableArrays.length][2];
        int counter = 0;

        for (int i = 0; i < matrix.length -  1; i++) {

            LocalTime t1 = LocalTime.parse(getKeyByValue(hashMap, i));
            LocalTime t2 = LocalTime.parse(getKeyByValue(hashMap, i + 1));

            if ((t1.isAfter(workingHoursComm[0]) || t1.compareTo(workingHoursComm[0]) == 0) &&
                    (t2.isBefore(workingHoursComm[1])  || t2.compareTo(workingHoursComm[1]) == 0)) {

                int timeBetweenHours = (int) MINUTES.between(t1, t2), temp;
                temp = meetingDuration.getHour() * 60 + meetingDuration.getMinute();

                if (timeBetweenHours >= temp) {
                    if (availableArrays[i] == 1) {
                        result[counter][0] = t1;
                        result[counter][1] = t2;
                        counter++;
                    }
                }
            }
        }

        // Remove nulls
        LocalTime[][] tempResult = result;
        result = new LocalTime[counter][2];
        for (int i = 0; i < counter; i++) {
            for (int j = 0; j < 2; j++)
                result[i][j] = tempResult[i][j];
        }

        return result;
    }

    public static void main(String[] args) throws ParseException {

        String Kalendarz1 = "{\n" +
                " working_hours: {\n" +
                " start: \"09:00\",\n" +
                " end: \"20:00\"\n" +
                " },\n" +
                " planned_meeting: [\n" +
                " {\n" +
                " start: \"09:00\",\n" +
                " end: \"10:30\"\n" +
                " },\n" +
                " {\n" +
                " start: \"12:00\",\n" +
                " end: \"13:00\"\n" +
                " },\n" +
                " {\n" +
                " start: \"16:00\",\n" +
                " end: \"18:00\"\n" +
                " }\n" +
                " ]\n" +
                "}\n" ;

        String Kalendarz2 = "{\n" +
                " working_hours: {\n" +
                " start: \"10:00\",\n" +
                " end: \"18:30\"\n" +
                " },\n" +
                " planned_meeting: [\n" +
                " {\n" +
                " start: \"10:00\",\n" +
                " end: \"11:30\"\n" +
                " },\n" +
                " {\n" +
                " start: \"12:30\",\n" +
                " end: \"14:30\"\n" +
                " },\n" +
                " {\n" +
                " start: \"14:30\",\n" +
                " end: \"15:00\"\n" +
                " },\n" +
                " {\n" +
                " start: \"16:00\",\n" +
                " end: \"17:00\"\n" +
                " }\n" +
                " ]\n" +
                "}\n";

        JSONObject callendar1 = new JSONObject(Kalendarz1);
        JSONObject callendar2 = new JSONObject(Kalendarz2);

        LocalTime[] workingHoursCallendars = new LocalTime[4];

        String workingHours = "";

        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                workingHours = callendar1.getJSONObject("working_hours").getString("start");
                workingHoursCallendars[0] = LocalTime.of(Integer.parseInt(workingHours.substring(0, 2)), Integer.parseInt(workingHours.substring(3)));
            }
            else if (i == 1) {
                workingHours = callendar1.getJSONObject("working_hours").getString("end");
                workingHoursCallendars[1] = LocalTime.of(Integer.parseInt(workingHours.substring(0, 2)), Integer.parseInt(workingHours.substring(3)));
            }
            else if (i == 2) {
                workingHours = callendar2.getJSONObject("working_hours").getString("start");
                workingHoursCallendars[2] = LocalTime.of(Integer.parseInt(workingHours.substring(0, 2)), Integer.parseInt(workingHours.substring(3)));
            }
            else if (i == 3) {
                workingHours = callendar2.getJSONObject("working_hours").getString("end");
                workingHoursCallendars[3] = LocalTime.of(Integer.parseInt(workingHours.substring(0, 2)), Integer.parseInt(workingHours.substring(3)));
            }
        }

        workingHoursCommonPart(workingHoursCallendars);

        JSONArray arr = callendar1.getJSONArray("planned_meeting");
        JSONArray arr2 = callendar2.getJSONArray("planned_meeting");

        // Adjacency Matrix
        int[][] meetingsAdjMatrix = getMeetingsAdjMatrix(arr, arr2);
        //System.out.println(Arrays.deepToString(meetingsAdjMatrix));

        LocalTime meetingDur = LocalTime.of(0, 30);

        LocalTime[][] result = findAvailableMeetingsRange(meetingsAdjMatrix, meetingDur);
        System.out.println(Arrays.deepToString(result));
    }
}

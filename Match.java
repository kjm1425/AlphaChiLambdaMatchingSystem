import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class Match {

        public static HashMap<String, Pref> bigPrefs = new HashMap<String, Pref>();
        public static HashMap<String, Pref> littlePrefs = new HashMap<String, Pref>();
        public static HashMap<String, Pref> availableBigs = new HashMap<String, Pref>();
        public static HashMap<String, Pref> remainingBigs = new HashMap<String, Pref>();
        public static HashMap<String, Pref> availableLittles = new HashMap<String, Pref>();
        public static ArrayList<String> bigsWithRestrictions = new ArrayList<String>();
        public static ArrayList<String> bigsTakingTwins = new ArrayList<String>();
        public static ArrayList<String> bigsWithRestrictionsTakingTwins = new ArrayList<String>();
        public static ArrayList<String> littlesToAdd = new ArrayList<String>();
        public static ArrayList<String> littlesToRemove = new ArrayList<String>();
        public static ArrayList<String> bigsToAdd = new ArrayList<String>();
        public static ArrayList<String> bigsToRemove = new ArrayList<String>();
        public static int undeclared = 0;
        public static int newUndeclared = -1;
        public static HashMap<String, ArrayList<String>> matches = new HashMap<String, ArrayList<String>>();
        public static StringBuilder sb = new StringBuilder();

        public static void main(String[] args) throws FileNotFoundException {

                boolean cont = false;
                String bigFilename = "";
                String littleFilename = "";


                if(args[0].equals("-h")){
                        System.out.println("Usage: match [options] [filename of bigs preferences] [filename of littles prefernces]");
                        System.out.println();
                        System.out.println("Options:    -h              Print a list of valid command-line arguments and descriptions.");
                } else if (args[0] != ""){
                        bigFilename = args[0];
                        littleFilename = args[1];
                        cont = true;
                }

                if (cont == true) {

                        Scanner bigScanner = new Scanner(new File(bigFilename));
                        Scanner littleScanner = new Scanner(new File(littleFilename));

                        // Get rid of extra info lines.
                        bigScanner.nextLine();
                        littleScanner.nextLine();

                        while (bigScanner.hasNextLine()) {

                                String[] arr = bigScanner.nextLine().split(",");
                                Pref newPref = new Pref(arr[2], arr[3], arr[4], arr[5]);
                                         if (arr[6].contains("Yes")) {
                                        if (arr[7].contains("Yes")) {
                                                bigsWithRestrictionsTakingTwins.add(arr[1]);
                                        } else {
                                                bigsTakingTwins.add(arr[1]);
                                        }
                                }
                                if (arr[7].contains("Yes")) {
                                        bigsWithRestrictions.add(arr[1]);
                                } else {
                                        availableBigs.put(arr[1], newPref);
                                }

                                bigPrefs.put(arr[1], newPref);
                        }

                        System.out.print("Bigs Taking Twins: ");
                        for (String s : bigsTakingTwins) {
                                System.out.print(s + " ");
                        }
                        for (String s : bigsWithRestrictionsTakingTwins) {
                                System.out.print(s + " ");
                        }
                        System.out.print("\nRestricted Bigs: ");
                        for (String s : bigsWithRestrictions) {
                                System.out.print(s + " ");
                        }

                        while (littleScanner.hasNextLine()) {
                                String[] arr = littleScanner.nextLine().split(",");
                                Pref newPref = new Pref(arr[2], arr[3], arr[4], arr[5]);
                                littlePrefs.put(arr[1], newPref);
                                availableLittles.put(arr[1], newPref);
                        }

                        matchFirstBabies();
                        matchRestricted();
                        matchTwins();
                        matchRestrictedTwins();
                        checkIfAnyNotMatched();

                        bigScanner.close();
                        littleScanner.close();
                }
        }

        public static void Match(int size) {

                undeclared = 0;
                newUndeclared = -1;

                while (undeclared != newUndeclared) {

                        undeclared = littlesToRemove.size();

                        for (Entry<String, Pref> entry : availableLittles.entrySet()) {
                                String nameOfLittle = entry.getKey();
                                Pref pref = entry.getValue();
                                String[] potentialBigs = { pref.one, pref.two, pref.three, pref.four };
              
                           for (int i = 0; i < 4; i++) {
                                        if (availableBigs.containsKey(potentialBigs[i])
                                                        && availableBigs.get(potentialBigs[i]).contains(nameOfLittle)) {
                                                ArrayList<String> littles = new ArrayList<String>();
                                                // If big already matched with a little(s) equal to size.
                                                if (matches.containsKey(potentialBigs[i]) && matches.get(potentialBigs[i]).size() == size) {
                                                        Pref bigPref = availableBigs.get(potentialBigs[i]);
                                                        String currLittle = matches.get(potentialBigs[i]).get(0);
                                                        int currLittleRank = bigPref.rank(currLittle);
                                                        int newLittleRank = bigPref.rank(nameOfLittle);
                                                        if (newLittleRank > currLittleRank) {
                                                                littlesToAdd.add(currLittle);
                                                                littlesToRemove.remove(currLittle);
                                                                littlesToRemove.add(nameOfLittle);
                                                                littles = matches.get(potentialBigs[i]);
                                                                littles.remove(currLittle);
                                                                littles.add(nameOfLittle);
                                                                matches.put(potentialBigs[i], littles);
                                                                break;
                                                        }
                                                } else {
                                                        bigsToRemove.add(potentialBigs[i]);
                                                        littlesToRemove.add(nameOfLittle);
                                                        if (matches.containsKey(potentialBigs[i])) {
                                                                littles = matches.get(potentialBigs[i]);
                                                        }
                                                        littles.add(nameOfLittle);
                                                        matches.put(potentialBigs[i], littles);
                                                        break;
                                                }

                                        }

                                }

                        }

                        newUndeclared = littlesToRemove.size();
                        ClearTakenMatches();
                }

        }

        public static void ForceMatchUnclaimedBigsWithLittlesPreferences(int size) {
                undeclared = littlesToRemove.size();

                for (Entry<String, Pref> entry : availableLittles.entrySet()) {
                        String nameOfLittle = entry.getKey();
                        Pref pref = entry.getValue();
                        String[] potentialBigs = { pref.one, pref.two, pref.three, pref.four };
                        ArrayList<String> littles = new ArrayList<String>();

                        for (int i = 0; i < 4; i++) {         
              
              
              if (availableBigs.containsKey(potentialBigs[i])) {
                                        // If big already matched with a little(s) equal to size.
                                        if (matches.containsKey(potentialBigs[i]) && matches.get(potentialBigs[i]).size() == size) {
                                                String currLittle = matches.get(potentialBigs[i]).get(0);
                                                Pref currPref = littlePrefs.get(currLittle);
                                                int currLittleRank = currPref.rank(potentialBigs[i]);
                                                int newLittleRank = pref.rank(potentialBigs[i]);
                                                if (newLittleRank > currLittleRank) {
                                                        littlesToAdd.add(currLittle);
                                                        littlesToRemove.remove(currLittle);
                                                        littlesToRemove.add(nameOfLittle);
                                                        littles = matches.get(potentialBigs[i]);
                                                        littles.remove(currLittle);
                                                        littles.add(nameOfLittle);
                                                        matches.put(potentialBigs[i], littles);
                                                        break;
                                                }
                                        } else {
                                                bigsToRemove.add(potentialBigs[i]);
                                                littlesToRemove.add(nameOfLittle);
                                                if (matches.containsKey(potentialBigs[i])) {
                                                        littles = matches.get(potentialBigs[i]);
                                                }
                                                littles.add(nameOfLittle);
                                                matches.put(potentialBigs[i], littles);
                                                break;
                                        }
                                }

                        }

                }

                for(String little : littlesToRemove){
                        sb.append("***** FORCE MATCH of " + little + " *****");
                }

                newUndeclared = littlesToRemove.size();
                ClearTakenMatches();
        }

        public static void ClearTakenMatches() {

                for (String little : littlesToRemove) {
                        availableLittles.remove(little);
                }
                for (String little : littlesToAdd) {
                        availableLittles.put(little, littlePrefs.get(little));
                }
                for (String big : bigsToRemove) {
                        availableBigs.remove(big);
                        bigsWithRestrictions.remove(big);
                }
                littlesToRemove.clear();
                littlesToAdd.clear();
                bigsToRemove.clear();
        }

      public static void PrintMatches() {

                for (Entry<String, ArrayList<String>> entry : matches.entrySet()) {
                        String big = entry.getKey();
                        ArrayList<String> littles = entry.getValue();
                        System.out.print(big + ": ");
                        for (String s : littles) {
                                System.out.print(s + " ");
                        }
                        System.out.println();
                }

                System.out.print("\nUnmatched Littles: ");
                for (Entry<String, Pref> entry : availableLittles.entrySet()) {
                        String name = entry.getKey();
                        System.out.print(name + " ");
                }

                for (String big : bigsWithRestrictions) {
                        availableBigs.put(big, bigPrefs.get(big));
                }

                System.out.print("\nUnmatched Bigs: ");
                for (Entry<String, Pref> entry : availableBigs.entrySet()) {
                        String name = entry.getKey();
                    System.out.print(name + " ");
                }

                for (String big : bigsWithRestrictions) {
                        availableBigs.remove(big);
                }

        }

        public static void matchTwins() {

                // Matches of twins of bigs taking twins.
                for (String big : bigsTakingTwins) {
                        availableBigs.put(big, bigPrefs.get(big));
                }

                Match(2);
                ForceMatchUnclaimedBigsWithLittlesPreferences(2);

                System.out.println("\n\n************************************************\n***********  First Matches of Twins  ***********\n");
                PrintMatches();

        }
        
     public static void matchRestricted() {
                // Match bigs and littles with any RESTRICTED big candidates.
                for (String big : bigsWithRestrictions) {
                        availableBigs.put(big, bigPrefs.get(big));
                }

                Match(1);
                ForceMatchUnclaimedBigsWithLittlesPreferences(1);

                System.out.println("\n\n************************************************\n***********   Matches of Restricted  ***********\n");
                PrintMatches();
        }

        public static void matchRestrictedTwins() {
                // Matches of twins of RESTRICTED bigs taking twins.
                for (String big : bigsWithRestrictionsTakingTwins) {
                        availableBigs.put(big, bigPrefs.get(big));
                }

                Match(2);

                System.out.println("\n\n************************************************\n*********   Matches of Restricted Twins  *******\n");
                PrintMatches();

        }

        public static void matchFirstBabies() {

                // Match bigs and littles with first highest match.
                Match(1);

                // Force match any remaining unclaimed bigs with little's preferences.
                ForceMatchUnclaimedBigsWithLittlesPreferences(1);

                System.out.println("\n\n************************************************\n****************  First Matches  ***************\n");
                PrintMatches();
        }

        public static void checkIfAnyNotMatched() {
                // Check if any remaining.
                if (availableBigs.size() > 0) {
                        System.out.println(
                                        "\n\n******Remaining bigs could not matched by any method - these bigs must be hand matched****");
                        for (Entry<String, Pref> entry : availableBigs.entrySet()) {
                                String name = entry.getKey();
                                if (bigsWithRestrictionsTakingTwins.contains(name) || bigsTakingTwins.contains(name)) {
                                        System.out.print("*** /MAY ONLY NEED A TWIN/ " + name + " ");
                                } else {
                                        System.out.print("*** " + name + " ");
                                }
                        }
                        System.out.println("***\n");
                        System.out.println(sb.toString());
                }
        }

}

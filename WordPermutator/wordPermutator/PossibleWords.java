package wordPermutator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This program permutes a given set of N letters of char primitive data type;
 * such letters are input from the user in an (N x M) matrix.
 * Once the program calculates all the possible combinations with those letters,
 * it reads a dictionary file, and checks whether they form (or not) a valid word.
 * 
 * Algorithm implemented: Depth First Search.
 * Complexity time: O(V) O(V) O(V).
 * 
 * NOTE: arrays detail all eight possible movements from a 
 * cell (top, right, bottom, left, and four diagonal moves).
 * 
 * @author Kevin Mora
 */
public class PossibleWords {
    public static int[] row = { -1, -1, -1, 0, 1, 0, 1, 1 };
    public static int[] col = { -1, 1, 0, -1, -1, 1, 0, 1 };
 
    /**
     * Function to check c from the current cell. The function returns false if (x, y) 
     * is not valid matrix coordinates or cell (x, y) is already processed.
     */
    public static boolean isSafe(int x, int y, boolean[][] processed) {
        return (x >= 0 && x < processed.length) && (y >= 0 && y < processed[0].length)
                && !processed[x][y];
    }
 
    /**
     * A recursive function to generate all possible words in a boggle.
     */
    public static void searchBoggle(char[][] board, Set<String> words,
                                    Set<String> result, boolean[][] processed,
                                    int i, int j, String path) {
        // Mark the current node as processed
        processed[i][j] = true;
 
        // Update the path with the current character and insert it into the set
        path += board[i][j];
 
        // Check whether the path is present in the input set
        if (words.contains(path)) {
            result.add(path);
        }
 
        // Check for all possible movements from the current cell
        for (int k = 0; k < row.length; k++) {
            // Skip if (cell is invalid) || (already processed)
            if (isSafe(i + row[k], j + col[k], processed)) {
                searchBoggle(board, words, result, processed, i + row[k],
                        j + col[k], path);
            }
        }
        // Backtrack :: mark current node as unprocessed
        processed[i][j] = false;
    }
 
    /**
     * Function to search for a given set of words in a boggle.
     */
    public static Set<String> searchBoggle(char[][] board, Set<String> words) {
        // Construct a set to store valid words constructed from the boggle
        Set<String> result = new HashSet<>();
 
        // Recursive base case
        if (board.length == 0) {
            return result;
        }
 
        // (M × N) board
        int M = board.length;
        int N = board[0].length;
 
        // Create a boolean matrix to store whether a cell is processed or not
        boolean[][] processed = new boolean[M][N];
 
        // Generate all possible words in a boggle
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                // Consider each character as a starting point :: run DFS
                searchBoggle(board, words, result, processed, i, j, "");
            }
        }
        return result;
    }
    
    /**
     * Client application.
     */
    public static void main(String[] args) throws FileNotFoundException {
        char[][] board = {
                {'b', 'n', 'a'},
                {'a', 'n', 'a'}
        };
        
        // Specify data path :: obtain words in dictionary
	String filename = "src/dictionaries/englishDictionary.txt";
	Scanner sc = new Scanner(new File(filename));
	List<String> lines = new ArrayList<String>();
	while (sc.hasNextLine()) {
		lines.add(sc.nextLine());
	}
		
	String[] arr = lines.toArray(new String[0]);
        Set<String> words = Stream.of(arr).collect(Collectors.toSet());
        
        // Compare input data with dictionary :: print
        Set<String> validWords = searchBoggle(board, words);
        System.out.println(validWords);
    }
}

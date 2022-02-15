import java.io.FileNotFoundException;
import java.util.*;
import java.awt.Point;
import java.io.*;

/**
* Map class
*/
public class Map {
    private char Map [] [];
    private boolean[][] revealed;
    public static Map instance = null;

    /**
     * Initialize the instructor to contain the map and 2D representation of the array-map
     */
    private Map() {
        // column = 5, row = 5
        Map = new char[5][5];
        revealed = new boolean[5][5];
    }

    /**
     * Check to see if the Map object has already been created. If it hasn't then instantiate one, otherwise
     * return the original instance stored as a private static member of the Map class
     * @return instance of the Map object
     */
    public static Map getInstance() {
      if (instance == null) {
        instance = new Map();
      }
      return instance;
    }

    /**
     * Load the contents of the area files in a 2D map array
     * @param mapNum area ranging between 1 - 3
     */
    public void loadMap(int mapNum) {
        try {
            String area;

            switch (mapNum) {

                case 1:
                    area = "PokemonGame/Area1.txt";
                    break;

                case 2:
                    area = "PokemonGame/Area2.txt";
                    break;

                case 3:
                    area = "PokemonGame/Area3.txt";
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + mapNum);
            }

            // First attempt: Scanner fileScanArea = new Scanner(arena);
            // BufferedReader reads text from a character input stream(from a file) and FileReader reads data from the text file

            FileReader textFile = new FileReader(area);
            Scanner fileScanArea = new Scanner(new BufferedReader(textFile));
            
            revealed = new boolean[5][5];

            while (fileScanArea.hasNextLine()) {
                for (int i = 0; i < Map.length; i++) {
                    String[] line = fileScanArea.nextLine().trim().split(" ");
                    for (int j = 0; j < line.length; j++) {
                        //Map[i][j] = (char) Integer.parseInt(line[j]);
                        Map[i][j] = line[j].charAt(0);
                    }
                }
            }
        }
        catch (FileNotFoundException B) {
            System.out.println("Something went wrong: File not found");

        }

    }

    /**
     * Return character at a specific location in our 2D map
     * @param p the (x, y) coordinate of a point on the map
     * @return character located at the specified point
     */
    public char getCharAtLoc(Point p) {
        int x = p.x;
        int y = p.y;

        // Map is confined in 5 x 5 square so check if it goes
        // out of bound
        if ( (x > 4 || x < 0) || (y > 4 || y < 0) ) {
            return 'B';
        }
        else {
            return Map[x] [y];
        }
    }

    /**
     * Display a 2D representation of the map as a type string
     * @param p point location
     * @return a neatly formatted representation of the trainer's position as a " * "
     */
    public String mapToString(Point p) {
        String mapString = "";

        int columns = 5;
        int rows = 5;

        for (int i = 0; i < columns; i++) {

          for (int j = 0; j < rows; j++) {

            if (p.getX() == i && p.getY() == j) {
              mapString += " * ";
            }

            else if (!revealed[i] [j]) {
              mapString += " x ";
            }
            
            else {
              mapString += " " + Map[i] [j] + " ";
            }

          }
          mapString += "\n";
        }

        return mapString;
    }

    /**
     * Traverse through the area and find the start of the map
     * @return (x, y) coordinate of the start character 's'
     */
    //@SuppressWarnings("null")
    public Point findStart() {

        Point startingLocation = new Point();

        for (int i = 0;  i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (Map[i] [j] == 's') {
                    startingLocation.setLocation(i, j);
                }
            }
        }
        return startingLocation;
    }

    /**
     * Determine wehthere a space on the map is shown or hidden
     * @param P point location
     */
    public void reveal(Point P) {
        // check if revealed is true
        revealed[(int) P.getX()] [(int) P.getY()] = true;
    }

    /**
     * Remove a space in the 2D map
     * @param P point location of the person, wild pokemon and item after encountering it
     */
    public void removeCharAtLoc(Point P) {
        if (Map[(int) P.getX()] [(int) P.getY()] == 'p' || Map[(int) P.getX()] [(int) P.getY()] == 'w' || Map[(int) P.getX()] [(int) P.getY()] == 'i') {
          Map[(int) P.getX()] [(int) P.getY()] = 'n';
        }
    }

}
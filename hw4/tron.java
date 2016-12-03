import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        int numPlayers, playerID;
        int playerX = -1, playerY = -1;

        int[][] occupied = new int[30][20];
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 20; j++) {
                occupied[i][j] = -1;
            }
        }

        Hashtable<Integer, String> currentMoves = new Hashtable<Integer, String>();

        // game loop
        while (true) {
            numPlayers = in.nextInt(); // total number of players (2 to 4).
            playerID = in.nextInt(); // your player number (0 to 3).
            for (int i = 0; i < numPlayers; i++) {
                int x0 = in.nextInt(); // starting X coordinate of lightcycle (or -1)
                int y0 = in.nextInt(); // starting Y coordinate of lightcycle (or -1)
                int x1 = in.nextInt(); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)
                int y1 = in.nextInt(); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)

                occupied[x0][y0] = i;
                occupied[x1][y1] = i;

                currentMoves.put(i, x1 + "," + y1);

                if (i == playerID) {
                    playerX = x1;
                    playerY = y1;
                }
            }

            // wipe out dead players from board state
            for (int i = 0; i < numPlayers; i++) {
                String currentMove = currentMoves.get(i);
                if (currentMove.equals("-1,-1")) {
                    for (int j = 0; j < 30; j++) {
                        for (int k = 0; k < 20; k++) {
                            if (occupied[j][k] == i) occupied[j][k] = -1;
                        }
                    }
                }
            }

            // actually start thinking about the neighboring squares we could move in to
            ArrayList<String> neighbors = new ArrayList<String>();
            if (playerX > 0 && occupied[playerX - 1][playerY] == -1) neighbors.add((playerX - 1) + "," + playerY); // we can move left
            if (playerX < 29 && occupied[playerX + 1][playerY] == -1) neighbors.add((playerX + 1) + "," + playerY); // we can move right
            if (playerY > 0 && occupied[playerX][playerY - 1] == -1) neighbors.add(playerX + "," + (playerY - 1)); // we can move up
            if (playerY < 19 && occupied[playerX][playerY + 1] == -1) neighbors.add(playerX + "," + (playerY + 1)); // we can move down

            System.err.println("CURRENT POSITION: " + playerX + ", " + playerY);
            System.err.println("Can move left: " + (playerX > 0 && occupied[playerX - 1][playerY] == -1));
            System.err.println("Can move right: " + (playerX < 29 && occupied[playerX + 1][playerY] == -1));
            System.err.println("Can move up: " + (playerY < 19 && occupied[playerX][playerY + 1] == -1));
            System.err.println("Can move down: " + (playerY < 19 && occupied[playerX][playerY + 1] == -1));

            int[] scores = new int[neighbors.size()];
            for (int i = 0; i < scores.length; i++) {
                scores[i] = 0;
            }

            // count spaces we're closest to for each remaining potential move
            for (int n = 0; n < scores.length; n++) {
                String s = neighbors.get(n);
                int x0 = Integer.parseInt(s.split(",")[0]);
                int y0 = Integer.parseInt(s.split(",")[1]);

                for (int i = 0; i < 30; i++) {
                    for (int j = 0; j < 20; j++) {
                        if (occupied[i][j] < 0) {
                            int playerDist = distance(x0, y0, i, j);
                            boolean playerClosest = true;
                            for (int k = 0; k < numPlayers; k++) {
                                if (k == playerID) continue;
                                String opponentMove = currentMoves.get(k);
                                if (distance(Integer.parseInt(opponentMove.split(",")[0]), Integer.parseInt(opponentMove.split(",")[1]), i, j) < playerDist) {
                                    playerClosest = false;
                                    break;
                                }
                            }
                            if (!playerClosest) continue;
                            else scores[n] += 1;
                        }
                    }
                }
            }

            int index = 0, max = -1;
            for (int i = 0; i < scores.length; i++) {
                if (scores[i] > max) {
                    max = scores[i];
                    index = i;
                }
            }

            // for (int i = 0; i < 20; i++) {
            //     for (int j = 0; j < 30; j++) {
            //         System.err.print(occupied[j][i] + " ");
            //     }
            //     System.err.println();
            // }

            System.err.println("Chosen space status: " + occupied[Integer.parseInt(neighbors.get(index).split(",")[0])][Integer.parseInt(neighbors.get(index).split(",")[1])]);
            System.out.println(direction(playerX, playerY, Integer.parseInt(neighbors.get(index).split(",")[0]), Integer.parseInt(neighbors.get(index).split(",")[1])));

        }
    }

    public static int distance(int x0, int y0, int x1, int y1) {
        return Math.abs(x0 - x1) + Math.abs(y0 - y1);
    }

    public static String direction(int x0, int y0, int x1, int y1) {
        if (x0 < x1) return "RIGHT";
        if (x1 < x0) return "LEFT";
        if (y0 < y1) return "DOWN";
        return "UP";
    }
}
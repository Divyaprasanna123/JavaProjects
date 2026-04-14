import java.util.Scanner;

public class TicTacToe {

    static char board[][] = new char[3][3];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        initializeBoard();
        printBoard();

        char currentPlayer = 'X';

        for(int turn = 0; turn < 9; turn++) {

            System.out.println("Player " + currentPlayer + " turn");

            System.out.print("Enter row (0-2): ");
            int row = sc.nextInt();

            System.out.print("Enter column (0-2): ");
            int col = sc.nextInt();

            // check if position is valid
            if(row >= 0 && row < 3 && col >= 0 && col < 3) {

                if(board[row][col] == '-') {

                    board[row][col] = currentPlayer;

                    // 🔥 check winner BEFORE switching
                    if(checkWinner(currentPlayer)) {
                        printBoard();
                        System.out.println("🎉 Player " + currentPlayer + " wins!");
                        return;
                    }

                    // switch player
                    if(currentPlayer == 'X') {
                        currentPlayer = 'O';
                    } else {
                        currentPlayer = 'X';
                    }

                } else {
                    System.out.println("Cell already filled! Try again.");
                    turn--; // repeat turn
                }

            } else {
                System.out.println("Invalid position! Try again.");
                turn--; // repeat turn
            }

            printBoard();
        }

        System.out.println("It's a DRAW!");
    }

    // ✅ Winner checking logic
    static boolean checkWinner(char player) {

        // check rows
        for(int i = 0; i < 3; i++) {
            if(board[i][0] == player &&
               board[i][1] == player &&
               board[i][2] == player) {
                return true;
            }
        }

        // check columns
        for(int j = 0; j < 3; j++) {
            if(board[0][j] == player &&
               board[1][j] == player &&
               board[2][j] == player) {
                return true;
            }
        }

        // check diagonals
        if(board[0][0] == player &&
           board[1][1] == player &&
           board[2][2] == player) {
            return true;
        }

        if(board[0][2] == player &&
           board[1][1] == player &&
           board[2][0] == player) {
            return true;
        }

        return false;
    }

    static void initializeBoard() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    static void printBoard() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
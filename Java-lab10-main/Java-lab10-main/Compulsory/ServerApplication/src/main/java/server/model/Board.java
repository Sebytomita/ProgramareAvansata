package server.model;

public class Board {
    private final int size;
    private final PlayerColor[][] cells;

    public Board(int size) {
        this.size = size;
        this.cells = new PlayerColor[size][size];
    }

    public boolean isValidMove(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size && cells[row][col] == null;
    }

    public void placeStone(int row, int col, PlayerColor color) {
        if (isValidMove(row, col)) {
            cells[row][col] = color;
        }
    }

    public boolean checkWin(PlayerColor color) {
        if (color == PlayerColor.RED) {
            for (int col = 0; col < size; col++) {
                if (cells[0][col] == PlayerColor.RED) {
                    boolean[][] visited = new boolean[size][size];
                    if (dfsRed(0, col, visited)) {
                        return true;
                    }
                }
            }
        }
        else if (color == PlayerColor.BLUE) {
            for (int row = 0; row < size; row++) {
                if (cells[row][0] == PlayerColor.BLUE) {
                    boolean[][] visited = new boolean[size][size];
                    if (dfsBlue(row, 0, visited)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean dfsRed(int row, int col, boolean[][] visited) {
        if (row < 0 || row >= size || col < 0 || col >= size || visited[row][col] || cells[row][col] != PlayerColor.RED) {
            return false;
        }

        if (row == size - 1) {
            return true;
        }

        visited[row][col] = true;

        int[][] directions = {{-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}};
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (dfsRed(newRow, newCol, visited)) {
                return true;
            }
        }

        return false;
    }

    private boolean dfsBlue(int row, int col, boolean[][] visited) {
        if (row < 0 || row >= size || col < 0 || col >= size || visited[row][col] || cells[row][col] != PlayerColor.BLUE) {
            return false;
        }

        if (col == size - 1) {
            return true;
        }

        visited[row][col] = true;

        int[][] directions = {{-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}};
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (dfsBlue(newRow, newCol, visited)) {
                return true;
            }
        }

        return false;
    }

    public PlayerColor[][] getCells() {
        return cells;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int s = 0; s < i; s++) {
                sb.append(" ");
            }

            for (int j = 0; j < size; j++) {
                if (cells[i][j] == PlayerColor.RED) {
                    sb.append("R ");
                } else if (cells[i][j] == PlayerColor.BLUE) {
                    sb.append("B ");
                } else {
                    sb.append(". ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

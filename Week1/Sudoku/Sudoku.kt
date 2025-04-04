import kotlin.math.sqrt

/**
 * Validates if the provided Sudoku board is correctly structured with no duplicates
 * in rows, columns, or sub-boxes.
 *
 * A Sudoku board is valid if it meets the following criteria:
 * - The board is non-empty and all rows have the same length.
 * - The board size is a perfect square (e.g., 4x4, 9x9, 16x16).
 * - Each cell contains either '-' (representing an empty cell) or a valid number.
 *   - Valid numbers are represented as digits or uppercase letters, where letters
 *     represent values starting from 10 (e.g., 'A' for 10).
 * - The numeric value of a filled cell must be between 1 and the board size (inclusive).
 * - No duplicate numbers exist in any row, column, or sub-box.
 *
 * @param board The 2D character array representing the Sudoku board.
 * @return true if the Sudoku board is valid, false otherwise.
 */
fun isValidSudoku(board: Array<CharArray>): Boolean {
    val size = board.size

    // Check for empty board
    if (size == 0) return false

    // Validate that all rows have the correct length
    for (row in board) {
        if (row.size != size) return false
    }

    // Calculate sub-box size and validate it's a perfect square
    val subBoxSize = sqrt(size.toDouble()).toInt()
    if (subBoxSize * subBoxSize != size) return false

    // Track occurrences of each character in rows, columns, and sub-boxes
    val rowSets = Array(size) { HashSet<Int>() }
    val colSets = Array(size) { HashSet<Int>() }
    val boxSets = Array(size) { HashSet<Int>() }

    for (rowIdx in 0 until size) {
        for (colIdx in 0 until size) {
            val cellValue = board[rowIdx][colIdx]
            if (cellValue == '-') continue // Skip empty cells

            // Validate character is a valid number for the current board size
            val numericValue = when {
                cellValue.isDigit() -> cellValue.toString().toInt()
                cellValue.isUpperCase() && cellValue in 'A'..'Z' -> 10 + (cellValue - 'A')
                else -> return false // Invalid character
            }

            if (numericValue < 1 || numericValue > size) return false

            // Calculate box index
            val boxIdx = (rowIdx / subBoxSize) * subBoxSize + (colIdx / subBoxSize)

            // Check for duplicates in the row, column, or box
            if (!rowSets[rowIdx].add(numericValue) ||
                !colSets[colIdx].add(numericValue) ||
                !boxSets[boxIdx].add(numericValue)) {
                return false
            }
        }
    }

    return true
}


/**
 * A helper function that verifies whether a test case passes.
 *
 * @param testName A descriptive name for the test case.
 * @param computed The result produced by the function under test.
 * @param expected The expected result for the test case.
 */
fun check(testName: String, computed: Boolean, expected: Boolean) {
    if (computed == expected) println("$testName: Passed")
    else println("$testName: Failed (Expected $expected, Got $computed)")
}

fun main() {
    // =========================
    // 9x9 Sudoku Tests
    // =========================

    // Test case: Empty board (should detect invalid empty input)
    val emptyBoard = arrayOf<CharArray>()
    check("Empty Board", isValidSudoku(emptyBoard), false)  // Testing empty board

    // Test case: Invalid 9x9 Sudoku board with a non-numeric character
    val invalidSudokuChar: Array<CharArray> = arrayOf(
        charArrayOf('-', '-', '-', '-', 'm', '-', '-', '-', '-'),  // 'm' is not valid
        charArrayOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        charArrayOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        charArrayOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        charArrayOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        charArrayOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        charArrayOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        charArrayOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        charArrayOf('-', '-', '-', '-', '-', '-', '-', '-', '-')
    )
    check("Invalid 9x9 Sudoku with Invalid Character", isValidSudoku(invalidSudokuChar), false)

    // Test case: Valid empty 9x9 Sudoku board (all cells are empty)
    val validSudokuEmpty: Array<CharArray> = Array(9) { CharArray(9) { '-' } }
    check("Valid 9x9 Empty Sudoku", isValidSudoku(validSudokuEmpty), true)

    // Test case: Valid 9x9 Sudoku board with some filled cells
    val validSudoku: Array<CharArray> = arrayOf(
        charArrayOf('5', '3', '-', '-', '7', '-', '-', '-', '-'),
        charArrayOf('6', '-', '-', '1', '9', '5', '-', '-', '-'),
        charArrayOf('-', '9', '8', '-', '-', '-', '-', '6', '-'),
        charArrayOf('8', '-', '-', '-', '6', '-', '-', '-', '3'),
        charArrayOf('4', '-', '-', '8', '-', '3', '-', '-', '1'),
        charArrayOf('7', '-', '-', '-', '2', '-', '-', '-', '6'),
        charArrayOf('-', '6', '-', '-', '-', '-', '2', '8', '-'),
        charArrayOf('-', '-', '-', '4', '1', '9', '-', '-', '5'),
        charArrayOf('-', '-', '-', '-', '8', '-', '-', '7', '9')
    )
    check("Valid 9x9 Sudoku with Values", isValidSudoku(validSudoku), true)

    // Test case: Invalid 9x9 Sudoku with inconsistent row length
    val invalidSizeSudoku: Array<CharArray> = arrayOf(
        charArrayOf('5', '3', '-', '-', '7', '-', '-', '-', '-'),
        charArrayOf('6', '-', '-', '1', '9', '5', '-'),  // Only 7 cells in this row
        charArrayOf('-', '9', '8', '-', '-', '-', '-', '6', '-'),
        charArrayOf('8', '-', '-', '-', '6', '-', '-', '-', '3'),
        charArrayOf('4', '-', '-', '8', '-', '3', '-', '-', '1'),
        charArrayOf('7', '-', '-', '-', '2', '-', '-', '-', '6'),
        charArrayOf('-', '6', '-', '-', '-', '-', '2', '8', '-'),
        charArrayOf('-', '-', '-', '4', '1', '9', '-', '-', '5'),
        charArrayOf('-', '-', '-', '-', '8', '-', '-', '7', '9')
    )
    check("Invalid 9x9 Sudoku with Inconsistent Row Length", isValidSudoku(invalidSizeSudoku), false)

    // Test case: Invalid 9x9 Sudoku due to duplicate in a row
    val invalidRowSudoku: Array<CharArray> = arrayOf(
        charArrayOf('5', '3', '-', '-', '7', '-', '-', '3', '-'),  // Duplicate '3' in first row
        charArrayOf('6', '-', '-', '1', '9', '5', '-', '-', '-'),
        charArrayOf('-', '9', '8', '-', '-', '-', '-', '6', '-'),
        charArrayOf('8', '-', '-', '-', '6', '-', '-', '-', '3'),
        charArrayOf('4', '-', '-', '8', '-', '3', '-', '-', '1'),
        charArrayOf('7', '-', '-', '-', '2', '-', '-', '-', '6'),
        charArrayOf('-', '6', '-', '-', '-', '-', '2', '8', '-'),
        charArrayOf('-', '-', '-', '4', '1', '9', '-', '-', '5'),
        charArrayOf('-', '-', '-', '-', '8', '-', '-', '7', '9')
    )
    check("Invalid 9x9 Sudoku with Duplicate in Row", isValidSudoku(invalidRowSudoku), false)

    // Test case: Invalid 9x9 Sudoku due to duplicate in a column
    val invalidColSudoku: Array<CharArray> = arrayOf(
        charArrayOf('5', '3', '-', '-', '7', '-', '-', '-', '-'),
        charArrayOf('6', '-', '-', '1', '9', '5', '-', '-', '-'),
        charArrayOf('5', '9', '8', '-', '-', '-', '-', '6', '-'),  // Duplicate '5' in first column
        charArrayOf('8', '-', '-', '-', '6', '-', '-', '-', '3'),
        charArrayOf('4', '-', '-', '8', '-', '3', '-', '-', '1'),
        charArrayOf('7', '-', '-', '-', '2', '-', '-', '-', '6'),
        charArrayOf('-', '6', '-', '-', '-', '-', '2', '8', '-'),
        charArrayOf('-', '-', '-', '4', '1', '9', '-', '-', '5'),
        charArrayOf('-', '-', '-', '-', '8', '-', '-', '7', '9')
    )
    check("Invalid 9x9 Sudoku with Duplicate in Column", isValidSudoku(invalidColSudoku), false)

    // Test case: Invalid 9x9 Sudoku due to duplicate in a 3x3 subgrid
    val invalidBoxSudoku: Array<CharArray> = arrayOf(
        charArrayOf('5', '3', '-', '-', '7', '-', '-', '-', '-'),
        charArrayOf('6', '5', '-', '1', '9', '5', '-', '-', '-'),  // Duplicate '5' in top-left 3x3 box
        charArrayOf('-', '9', '8', '-', '-', '-', '-', '6', '-'),
        charArrayOf('8', '-', '-', '-', '6', '-', '-', '-', '3'),
        charArrayOf('4', '-', '-', '8', '-', '3', '-', '-', '1'),
        charArrayOf('7', '-', '-', '-', '2', '-', '-', '-', '6'),
        charArrayOf('-', '6', '-', '-', '-', '-', '2', '8', '-'),
        charArrayOf('-', '-', '-', '4', '1', '9', '-', '-', '5'),
        charArrayOf('-', '-', '-', '-', '8', '-', '-', '7', '9')
    )
    check("Invalid 9x9 Sudoku with Duplicate in 3x3 Box", isValidSudoku(invalidBoxSudoku), false)

    // =========================
    // 4x4 Sudoku Tests
    // =========================

    // Test case: Valid 4x4 Sudoku board
    val valid4x4: Array<CharArray> = arrayOf(
        charArrayOf('1', '-', '-', '4'),
        charArrayOf('-', '3', '2', '-'),
        charArrayOf('-', '2', '3', '-'),
        charArrayOf('4', '-', '-', '1')
    )
    check("Valid 4x4 Sudoku", isValidSudoku(valid4x4), true)

    // =========================
    // 16x16 Sudoku Tests
    // =========================

    // Test case: Valid 16x16 Sudoku board with all cells empty (should be valid)
    val valid16x16Empty: Array<CharArray> = Array(16) { CharArray(16) { '-' } }
    check("Valid 16x16 Sudoku (Empty Board)", isValidSudoku(valid16x16Empty), true)

    // Test case: Invalid 16x16 Sudoku board with a duplicate in a row
    // Create a 16x16 board that is empty except for a duplicate in the first row.
    val invalid16x16: Array<CharArray> = Array(16) { CharArray(16) { '-' } }
    invalid16x16[0][0] = '1'
    invalid16x16[0][5] = '1'  // Duplicate '1' in the first row.
    check("Invalid 16x16 Sudoku with Duplicate in Row", isValidSudoku(invalid16x16), false)

    // =========================
    // Invalid Board Size Tests
    // =========================

    // Test case: Invalid board size (5x5 is not a perfect square)
    val invalidSize: Array<CharArray> = arrayOf(
        charArrayOf('1', '2', '3', '4', '5'),
        charArrayOf('2', '3', '4', '5', '1'),
        charArrayOf('3', '4', '5', '1', '2'),
        charArrayOf('4', '5', '1', '2', '3'),
        charArrayOf('5', '1', '2', '3', '4')
    )
    check("Invalid 5x5 Sudoku (Not Perfect Square Size)", isValidSudoku(invalidSize), false)

    // Test case: Out of range value (0 is not valid in Sudoku)
    val outOfRangeValue: Array<CharArray> = arrayOf(
        charArrayOf('5', '3', '-', '-', '7', '-', '-', '-', '-'),
        charArrayOf('6', '-', '-', '1', '9', '5', '-', '-', '-'),
        charArrayOf('-', '9', '8', '-', '-', '-', '-', '6', '-'),
        charArrayOf('8', '-', '-', '-', '6', '-', '-', '-', '3'),
        charArrayOf('4', '-', '-', '8', '-', '3', '-', '-', '1'),
        charArrayOf('7', '-', '-', '-', '2', '-', '-', '-', '6'),
        charArrayOf('-', '6', '-', '-', '-', '-', '2', '8', '-'),
        charArrayOf('-', '-', '-', '4', '1', '9', '-', '-', '5'),
        charArrayOf('-', '-', '-', '-', '8', '-', '0', '7', '9')  // '0' is not valid in Sudoku
    )
    check("Invalid 9x9 Sudoku with Out of Range Value", isValidSudoku(outOfRangeValue), false)
}
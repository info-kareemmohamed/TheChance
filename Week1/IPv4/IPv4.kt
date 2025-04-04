/**
 * Validates if the provided string is a valid IPv4 address.
 *
 * An IPv4 address is valid if it meets the following criteria:
 * - Contains exactly 4 segments separated by dots (.)
 * - Each segment is a number between 0 and 255, inclusive
 * - No leading zeros allowed (except for the digit 0 itself)
 * - No extra characters allowed
 *
 * @param ip The string to validate as an IPv4 address
 * @return true if the string is a valid IPv4 address, false otherwise
 */
fun isValidIPv4(ip: String): Boolean {
    // Split by dots and check if there are exactly 4 segments
    val segments = ip.split(".")
    if (segments.size != 4) return false // Must have exactly 4 segments

    // Validate each segment
    return segments.all { segment ->
        segment.isNotEmpty() && // Segment should not be empty
        segment.length <= 3 && // Segment should not exceed 3 characters
                segment.all { it.isDigit() } && // Segment should contain only digits
                segment.toIntOrNull() in 0..255 && // Segment value should be between 0 and 255
                !(segment.length > 1 &&
                        segment[0] == '0') // No leading zeros allowed (except "0" itself)
    }
}

/**
 * A helper function that verifies whether a test case passes.
 *
 * @param testName A descriptive name for the test case.
 * @param computed The result produced by the function under test.
 * @param expected The expected result for the test case.
 */
fun check(testName: String, computed: Boolean, expected: Boolean) {
    if (computed == expected)
        println("$testName: Passed")  // Test passed: computed result matches expected
    else
        println("$testName: Failed (Expected $expected, Got $computed)")  // Test failed: show expected vs. computed
}

fun main() {
    // Valid cases
    check("Valid IPv4 - 127.0.0.1", isValidIPv4("127.0.0.1"), true)  // Testing valid IP 127.0.0.1
    check("Valid IPv4 - 0.0.0.0", isValidIPv4("0.0.0.0"), true)        // Testing valid IP 0.0.0.0
    check("Valid IPv4 - 255.255.255.255", isValidIPv4("255.255.255.255"), true)  // Testing valid IP 255.255.255.255

    // Invalid cases
    check("Invalid IPv4 - empty", isValidIPv4(""), false)  // Testing empty string as invalid IP
    check("Invalid IPv4 - wrong char", isValidIPv4("192.m.1"), false)  // Testing IP with non-digit character
    check("Invalid IPv4 - wrong format", isValidIPv4("192.168.1"), false)  // Testing IP with missing segments
    check("Invalid IPv4 - extra segments", isValidIPv4("192.168.1.1.1"), false)  // Testing IP with extra segments
    check("Invalid IPv4 - no dots", isValidIPv4("19216811"), false)  // Testing IP without any dots
    check("Invalid IPv4 - extra characters", isValidIPv4("192.168.1.1."), false)  // Testing IP with trailing dot
    check("Invalid IPv4 - out of range", isValidIPv4("192.168.1.256"), false)  // Testing IP with segment > 255
    check("Invalid IPv4 - negative value", isValidIPv4("-1.192.168.1"), false)  // Testing IP with a negative segment
    check("Invalid IPv4 - leading zero", isValidIPv4("192.168.01.1"), false)  // Testing IP with a segment that has a leading zero
    check("Invalid IPv4 - leading zero in segment", isValidIPv4("192.168.001.1"), false)  // Testing IP with multiple leading zeros in a segment
    check("Invalid IPv4 - double dot", isValidIPv4("192.168..1"), false)  // Testing IP with consecutive dots
    check("Invalid IPv4 - spaces", isValidIPv4(" 192.168.1.1"), false)  // Testing IP with leading space
}
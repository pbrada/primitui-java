package cz.zcu.kiv.brada.primitui;

/**
 * Utility class for writing simple formatted output to the console.
 *
 * <p>This class provides small helper methods used by the library to print
 * lines, headers, horizontal rules, newlines and formatted strings. All
 * methods are static convenience wrappers around {@link System#out}.
 */
public class Show {

    private static final int HEADER_FRAME_LENGTH = 8;

    /**
     * Print the given string to the console without a trailing newline.
     *
     * @param line the text to print (may be an empty string)
     */
    public static void text(Object text) {
        System.out.print(text.toString());
    }

    /**
     * Print the given string to the console followed by a newline.
     *
     * @param text the text to print (may be an empty string)
     */
    public static void textln(Object text) {
        System.out.println(text.toString());
    }

    /**
     * Print a formatted string to the console using {@link System#out#printf}.
     *
     * @param text  the format string (see {@link java.util.Formatter})
     * @param args  format arguments referenced by the format specifiers in {@code text}
     */
    public static void textf(String fmt, Object... args) {
        System.out.printf(fmt, args);
    }

    /**
     * Print a section header framed with equals signs.
     *
     * @param header the header text to print
     */
    public static void header(String header) {
        System.out.println("=== " + header + " ===");
    }

    /**
     * Print a section header framed with equals signs.
     *
     * @param header the header text to print
     */
    public static void header(String header, char frameChar) {
        for (int i = 0; i < HEADER_FRAME_LENGTH; i++) {
            System.out.print(frameChar);
        }
        System.out.print(" " + header + " ");
        for (int i = 0; i < HEADER_FRAME_LENGTH; i++) {
            System.out.print(frameChar);
        }
        System.out.println();
    }

    /**
     * Print a horizontal rule (separator line).
     */
    public static void hr() {
        System.out.println("--------------------------------------------------");
    }

    /**
     * Print a single newline to the console.
     */
    public static void nl() {
        System.out.println();
    }

    /**
     * Print the string representation of the parameter to stderr.
    */
    public static void err(Object msg) {
        System.err.print(msg.toString());
    }
    
    /**
     * Print the string representation of the parameter to stderr, adding a newline.
    */
    public static void errln(Object msg) {
        System.err.println(msg.toString());
    }
    
    /**
     * Print a formatted error message to stderr.
     *
     * @param format  the format string (see {@link java.util.Formatter})
     * @param args  format arguments referenced by the format specifiers in {@code text}
     */
    public static void errf(String format, Object... args) {
        System.err.printf(format, args);
    }

    /**
     * Attempt to clear the console by emitting ANSI escape sequences and
     * flushing the output. This works in most ANSI-capable terminals but is
     * not guaranteed on all platforms or IDE consoles.
     */
    public static void clear() {
        // Clear the console (works in most terminals)
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

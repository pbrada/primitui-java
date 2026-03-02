package cz.zcu.kiv.brada.primitui;

/**
 * Static utility class for console input operations.
 */
public class Read {
    
    // ===== TEXT INPUT =====
    
    /**
     * Prompt for text input.
     * Accepts any text, incl. empty string. Uses default prompt.
     * 
     * @return the entered text
     */
    public static String text() {
        return text("");
    }

    /**
     * Prompt for text input, using the provided prompt text.
     * Accepts any text, incl. empty string.
     * 
     * @param prompt the prompt text
     * @return the entered text
     */
    public static String text(String prompt) {
        return text(prompt, 0, -1); // Default: min length 1, no max length
    }
    
    /**
     * Prompt for text input with length constraints, using the provided prompt text.
     * 
     * Validates length and re-prompts on error with appropriate message.
     * 
     * @param prompt the prompt text
     * @param minLength minimum allowed length (0 for no minimum)
     * @param maxLength maximum allowed length (-1 for no maximum)
     * @return the entered text (not trimmed or manipulated)
     */
    public static String text(String prompt, int minLength, int maxLength) {
        if (minLength < 0) minLength = 0;
        if (prompt != null && !prompt.isEmpty()) {
            System.out.print(prompt);
        }
        String input = System.console().readLine();
        if (input == null) {
            return text(prompt, minLength, maxLength); // Something weird, recursively re-prompt
        }
        int length = input.length();
        if (length < minLength || (maxLength >= 0 && length > maxLength)) {
            System.err.println("Input must be between " + minLength + " and " + maxLength + " characters.");
            return text(prompt, minLength, maxLength); // Recursively re-prompt 
        }
        return input;

    }
    

    // ===== PASSWORD-LIKE INPUT =====
    

    /**
     * Prompt for password-like input, using a default prompt. 
     */
    public static String hidden() {
        return hidden("");
    }  


    /**
     * Prompt for password-like input, using the provided text prompt. 
     * 
     * If System.console() is available, 
     * uses readPassword() for masking. Otherwise, falls back to text() without masking 
     * and shows a warning.
     * 
     * @param prompt the prompt text
     * @return the entered password (trimmed)
     */
    public static String hidden(String prompt) {

        java.io.Console console = System.console();
        
        if (console == null) { // Fallback: Just use text() without masking
            System.err.println("[Warning: Password input could not be masked]");
            return text(prompt);
        } else {
            // Preferred method: Uses built-in password masking
            char[] chars = console.readPassword(prompt, null);
            String result = new String(chars);
            java.util.Arrays.fill(chars, ' ');  // Clear for security
            return result;
        }
        
    }
    

    // ===== NUMBER INPUT =====
    

    /**
     * Prompt for integer input, using the default prompt.
     * 
     * @return the entered integer
     */    
    public static int num() {
        return num("", Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Prompt for integer input, using the provided text prompt.
     * 
     * @param prompt the prompt text
     * @return the entered integer
     */
    public static int num(String prompt) {
        // Displays: "prompt _"
        // Accepts valid integers only
        // Re-prompts on invalid input
        return num(prompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    /**
     * Prompt for integer with range validation.  Re-prompts on invalid input 
     * or out-of-range value.
     * 
     * @param prompt the prompt text
     * @param min minimum allowed value (inclusive)
     * @param max maximum allowed value (inclusive)
     * @return the entered integer
     */
    public static int num(String prompt, int min, int max) {
        String input = text(prompt);
        int result;
        try {
            result = Integer.parseInt(input);
            if (result < min || result > max) {
                System.err.println("Please enter a number between " + min + " and " + max + ".");
                result = num(prompt, min, max); // Recursively re-prompt   
            }
            return result;
        } catch (NumberFormatException e) {
            System.err.println("Please enter a valid integer.");
            result = num(prompt, min, max); // Recursively re-prompt   
        }
        return result;
    }
    

    /**
     * Prompt for double input, using a default prompt.
     * 
     * @return the entered double
     */
    public static double real() {
        return real("", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    /**
     * Prompt for double input, using the provided text prompt.
     * 
     * @param prompt the prompt text
     * @return the entered double
     */
    public static double real(String prompt) {
        // Displays: "prompt _"
        // Accepts valid doubles
        return real(prompt, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
    
    /**
     * Prompt for double with range validation. Re-prompts on invalid input 
     * or out-of-range value.
     * 
     * @param prompt the prompt text
     * @param min minimum allowed value (inclusive)
     * @param max maximum allowed value (inclusive)
     * @return the entered double
     */
    public static double real(String prompt, double min, double max) {
        String input = text(prompt);
        double result;
        try {
            result = Double.parseDouble(input);
            if (result < min || result > max) {
                System.err.println("Please enter a number between " + min + " and " + max + ".");
                result = real(prompt, min, max); // Recursively re-prompt   
            }
            return result;
        } catch (NumberFormatException e) {
            System.err.println("Please enter a valid floating-point number.");
            result = real(prompt, min, max); // Recursively re-prompt   
        }
        return result;
    }
    
    // ===== BOOLEAN INPUT =====
    
    /**
     * Prompt for yes/no input, using a default prompt.
     * Accepts: yes/y/true/1/on or no/n/false/0/off (case-insensitive).  
     * Re-prompts on invalid input.
     * 
     * @param prompt the prompt text
     * @return true if user entered yes/y/true/1/on, false otherwise
     */
    public static boolean confirm() {
        return confirm("");
    }

    /**
     * Prompt for yes/no input, using the provided text prompt.
     * Accepts: yes/y/true/1/on or no/n/false/0/off (case-insensitive).  
     * Re-prompts on invalid input.
     * 
     * @param prompt the prompt text
     * @return true if user entered yes/y/true/1/on, false otherwise
     */
    public static boolean confirm(String prompt) {
        String input = text(prompt + " (y/n) ");
        switch (input.toLowerCase()) {
            case "y":
            case "yes":
            case "true":
            case "1":
            case "on":
                return true;
            case "n":
            case "no":
            case "false":
            case "0":
            case "off":
                return false;
            default:
                System.err.println("Please enter 'y' for yes or 'n' for no.");
                return confirm(prompt); // Recursively re-prompt
        }
    }
    
}

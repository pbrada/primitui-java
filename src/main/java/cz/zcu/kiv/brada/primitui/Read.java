package cz.zcu.kiv.brada.primitui;

/**
 * Static utility class for input operations.
 */
public class Read {
    
    // ===== TEXT INPUT =====
    
    /**
     * Prompt for text input.
     * Accepts any non-empty text, re-prompts on empty input.
     * 
     * @param prompt the prompt text
     * @return the entered text (trimmed)
     */
    public static String text() {
        return text("");
    }

    /**
     * Prompt for text input.
     * Accepts any non-empty text, re-prompts on empty input.
     * 
     * @param prompt the prompt text
     * @return the entered text (trimmed)
     */
    public static String text(String prompt) {
        return text(prompt, 0, -1); // Default: min length 1, no max length
    }
    
    /**
     * Prompt for text input with length constraints.
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
    
    // /**
    //  * Prompt for text input with custom validation.
    //  * 
    //  * @param prompt the prompt text
    //  * @param validator a lambda/function that returns null if valid, 
    //  *                  or an error message if invalid
    //  * @return the entered text (trimmed)
    //  */
    // public static String text(String prompt, java.util.function.Function<String, String> validator) {
    //     // Calls validator(input). If null/empty, accept.
    //     // Otherwise display the error message and re-prompt.
    // }
    
    // /**
    //  * Prompt for text input with length AND custom validation.
    //  * 
    //  * @param prompt the prompt text
    //  * @param minLength minimum allowed length
    //  * @param maxLength maximum allowed length
    //  * @param validator custom validator
    //  * @return the entered text (trimmed)
    //  */
    // public static String text(String prompt, int minLength, int maxLength, 
    //                           java.util.function.Function<String, String> validator) {
    //     // Validates length first, then custom validator
    // }
    

        // ===== PASSWORD INPUT =====
    
    /**
     * Prompt for password-like input.
     * Characters are masked with UIConfig.PASSWORD_MASK_CHAR (default: "*")
     * Error on empty input: UIConfig.ERROR_EMPTY_INPUT.get()
     * 
     * @param prompt the prompt text
     * @return the entered password (trimmed)
     */
    public static String hidden(String prompt) {
        // Displays: "prompt _"
        // Shows masked characters as user types (e.g., "****")
        // Accepts any non-empty text
        // Re-prompts on empty input with error message
        return hidden(prompt, "*");
    }
    
    // /**
    //  * Prompt for password input with length constraints.
    //  * Characters are masked with UIConfig.PASSWORD_MASK_CHAR (default: "*")
    //  * Error messages from UIConfig: ERROR_LENGTH_VIOLATION, ERROR_MIN_LENGTH, ERROR_MAX_LENGTH
    //  * 
    //  * @param prompt the prompt text
    //  * @param minLength minimum allowed length (0 for no minimum)
    //  * @param maxLength maximum allowed length (-1 for no maximum)
    //  * @return the entered password (trimmed)
    //  */
    // public static String password(String prompt, int minLength, int maxLength) {
    //     // Validates length and re-prompts on error with appropriate message
    //     // Shows masked characters as user types
    // }
    
    // /**
    //  * Prompt for password input with custom validation.
    //  * Characters are masked with UIConfig.PASSWORD_MASK_CHAR (default: "*")
    //  * The validator should return an error message if invalid, or null/empty string if valid.
    //  * 
    //  * @param prompt the prompt text
    //  * @param validator a lambda/function that returns null if valid, 
    //  *                  or an error message if invalid
    //  * @return the entered password (trimmed)
    //  */
    // public static String password(String prompt, java.util.function.Function<String, String> validator) {
    //     // Calls validator(input). If null/empty, accept.
    //     // Otherwise display the error message and re-prompt.
    //     // Shows masked characters as user types
    // }
    
    // /**
    //  * Prompt for password input with length AND custom validation.
    //  * Characters are masked with UIConfig.PASSWORD_MASK_CHAR (default: "*")
    //  * Validates length first, then custom validator.
    //  * 
    //  * @param prompt the prompt text
    //  * @param minLength minimum allowed length
    //  * @param maxLength maximum allowed length
    //  * @param validator custom validator
    //  * @return the entered password (trimmed)
    //  */
    // public static String password(String prompt, int minLength, int maxLength, 
    //                               java.util.function.Function<String, String> validator) {
        
    //     String input = readPassword("*");

    //     // TODO: Validates length first, then custom validator
        
    //     return input;
    // }
    
    // /**
    //  * Prompt for password input with custom mask character.
    //  * 
    //  * @param prompt the prompt text
    //  * @param maskChar the character to display instead of actual input (e.g., "*", "●", "•")
    //  * @return the entered password (trimmed)
    //  */
    // public static String password(String prompt, String maskChar) {
    //     // Displays: "prompt _"
    //     // Shows maskChar for each typed character
    //     // Accepts any non-empty text
    //     // Re-prompts on empty input with error message
    // }
    
 
    /**
     * Read password with visual masking.
     * Tries System.console().readPassword() first (best option).
     * Falls back to manual character-by-character reading.
     */
    public static String hidden(String prompt, String maskChar) {

        // TODO maskChar is ignored 

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
     * Prompt for integer input.
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
     * Prompt for integer with range validation.
     * 
     * @param prompt the prompt text
     * @param min minimum allowed value (inclusive)
     * @param max maximum allowed value (inclusive)
     * @return the entered integer
     */
    public static int num(String prompt, int min, int max) {
        // Validates range and re-prompts on error
        // Error message: "Please enter a number between X and Y."
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
     * Prompt for double input.
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
     * Prompt for double with range validation.
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
     * Prompt for yes/no input.
     * Accepts: yes/y/true/1/on or no/n/false/0/off (case-insensitive)
     * 
     * @param prompt the prompt text
     * @return true if user entered yes/y/true/1/on, false otherwise
     */
    public static boolean confirm(String prompt) {
        // Displays: "prompt (y/n) _"
        // Re-prompts on invalid input
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

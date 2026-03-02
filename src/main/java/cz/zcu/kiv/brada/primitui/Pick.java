
package cz.zcu.kiv.brada.primitui;

import java.util.Map;
import java.util.List;
import java.util.Arrays;

/**
 * Static utility class for menu operations.
 */
public class Pick {
    
    // ===== BASIC MENU =====

    private static final String PROMPT_RADIO = "> select item: ";
    private static final String PROMPT_CHECK = "> select items (space-separated): ";

    /**
     * Display a menu composed of provided items' string representations and 
     * return the user's choice, using configurable prompt.
     * 
     * @param items the menu items to display
     * @return the 0-based index of the selected item
     */
    public static int menu(String prompt, Object... items) {
        return menu(Arrays.asList(items), Map.of("prompt", prompt));
    }
    
    
    /**
     * Display a menu from a list and get the user's choice, using a default prompt text.
     * 
     * @param items a List of menu items to display
     * @return the 0-based index of the selected item
     */
    public static int menu(Iterable<?> items) {
        return menu(items, Map.of("prompt", "> "));
    }
    
    /**
     * Display a menu from an array and get the user's choice, using a default prompt text.
     * 
     * @param items an array of menu items to display
     * @return the 0-based index of the selected item
     */
    public static int menu(Object[] items) {
        return menu(Arrays.asList(items), Map.of("prompt", "> "));
    }
    
    /**
     * Display a menu from a list, with custom prompt.
     * 
     * @param prompt the prompt text
     * @param items a List of menu items
     * @return the 0-based index of the selected item
     */
    public static int menu(Iterable<?> items, String prompt) {
        Map<String,Object> config = Map.of("prompt", prompt);
        return menu(items, config);
    }
    
    /**
     * Display a menu from an array, with custom prompt.
     * 
     * @param prompt the prompt text
     * @param items an array of menu items
     * @return the 0-based index of the selected item
     */
    public static int menu(Object[] items, String prompt) {
        Map<String,Object> config = Map.of("prompt", prompt);
        return menu(Arrays.asList(items), config);
    }
    
    /**
     * Display a menu from a list, with user-defined options.
     * 
     * @param items a List of menu items
     * @param options a Map of user-defined options
     * 
     * @return the 0-based index of the selected item
     * 
     * Displays menu with options from the `items` list, waits for user input, and 
     * returns the index of the item corresponding to the entered choice character. 
     * 
     * Configuration options:
     * "prompt" -- custom prompt text (default: "> ").
     * "startsWith" -- single character to define the starting character for menu choice characters 
     * (default '1').  If set to a letter, menu will use letters instead of numbers.
     * "header" -- string displayed above the menu; optional.
     * "footer" -- string displayed below the menu; optional.
     */
    private static int menu(Iterable<?> items, Map<String,Object> config) {
        if (config == null) {  // Avoid null pointer if config is not provided
            config = Map.of();
        }
        if (config.containsKey("header")) {
            System.out.println(config.get("header"));
        }
        // Get list enum character
        char enumChar = config.containsKey("start") ? (char) config.get("start") : '1';
        // Display menu items
        int i = 0;
        for (Object item : items) {
            System.out.print((char)(enumChar + i));
            System.out.println(". " + item);
            i++;
        }
        int numItems = i ;
        if (config.containsKey("footer")) {
            System.out.println(config.get("footer"));
        }
        String prompt = config.containsKey("prompt") ? (String) config.get("prompt") : "> select: ";
        // Get user choice
        String choiceChar = Read.text(prompt);
        int choice = choiceChar.charAt(0) - enumChar;
        if (choice < 0 || choice >= numItems) {
            // Invalid choice, re-prompt
            System.out.println("Invalid choice. Please try again.");
            return menu(items, config);
        }
        return choice;
    }

    
    // ===== RADIO BUTTON (SINGLE SELECTION) =====
    
    /**
     * Display a radio button list (single selection) with varargs.
     * 
     * @param prompt the prompt text
     * @param options the available options
     * @return the 0-based index of the selected option
     */
    public static int radio(String prompt, Object... options) {
        return radio(Arrays.asList(options), -1, Map.of("prompt", prompt));    
    }
    
    /**
     * Display a radio button list (single selection) from a list.
     * 
     * @param prompt the prompt text
     * @param options a List of available options
     * @return the 0-based index of the selected option
     */
    public static int radio(Iterable<?> options) {
        return radio(options, -1, Map.of("prompt", "> "));
    }
    
    /**
     * Display a radio button list (single selection) from an array.
     * 
     * @param prompt the prompt text
     * @param options an array of available options
     * @return the 0-based index of the selected option
     */
    public static int radio(Object[] options) {
        return radio(Arrays.asList(options), -1, Map.of("prompt", "> "));
    }

    /**
     * Display a radio button list with initial selection from a list.
     * 
     * @param prompt the prompt text
     * @param initialSelection 0-based index of initially selected option
     * @param options a List of available options
     * @return the 0-based index of the selected option
     */
    public static int radio(Iterable<?> options, int initialSelection) {
        return radio(options, initialSelection, Map.of());
    }
    
    /**
     * Display a radio button list with initial selection from an array.
     * 
     * @param prompt the prompt text
     * @param initialSelection 0-based index of initially selected option
     * @param options an array of available options
     * @return the 0-based index of the selected option
     */
    public static int radio(Object[] options, int initialSelection) {
        return radio(Arrays.asList(options), initialSelection, Map.of());
    }

    /**
     * Display a radio button list with initial selection from a list.
     * 
     * @param prompt the prompt text
     * @param initialSelection 0-based index of initially selected option
     * @param options a List of available options
     * @return the 0-based index of the selected option
     */
    public static int radio(Iterable<?> options, int initialSelection, String prompt) {
        return radio(options, initialSelection, Map.of("prompt", prompt));
    }
    
    /**
     * Display a radio button list with initial selection from an array.
     * 
     * @param prompt the prompt text
     * @param initialSelection 0-based index of initially selected option
     * @param options an array of available options
     * @return the 0-based index of the selected option
     */
    public static int radio(Object[] options, int initialSelection, String prompt) {
        return radio(Arrays.asList(options), initialSelection, Map.of("prompt", prompt));
    }
    
    /**
     * Display a radio button list with initial selection.  Entering the choice character 
     * selects the corresponding option and returns its index.  Entering '0' signals no 
     * selection and returns -1.
     * 
     * @param options an array of available options
     * @param initialSelection 0-based index of the initially selected option
     * @param config the configuration map containing prompt and other settings
     * @return the 0-based index of the selected option, or -1 if no selection is made
     */
    private static int radio(Iterable<?> options, int initialSelection, Map<String,Object> config) {
        if (config == null) {  // Avoid null pointer if config is not provided
            config = Map.of();
        }
        // Display menu items
        int i = 0;
        for (Object option : options) {
            System.out.print((1 + i) + ". (");
            System.out.print((i == initialSelection ? "*" : " ") + ") ");
            System.out.println(option);
            i++;
        }
        int numItems = i;
        String prompt = config.containsKey("prompt") ? (String) config.get("prompt") : PROMPT_RADIO;
        // Get user choice
        int choice = Read.num(prompt, 0, numItems);
        if (choice < 0 || choice > numItems) {
            // Invalid choice, re-prompt
            System.out.println("Invalid choice. Please try again.");
            return radio(options, initialSelection, config);
        }
        return choice - 1;        
    }


    // ===== CHECKBOX (MULTIPLE SELECTION) =====
    
    /**
     * Display a checkbox list (multiple selection) using varargs.
     * 
     * @param prompt the prompt text
     * @param options the available options
     * @return a list of 0-based indices of selected options (in order entered)
     * 
     * Displays checklist with no option pre-selected.  
     */
    public static List<Integer> check(String prompt, Object... options) {
        return check(Arrays.asList(options), List.of(), prompt);
    }
    
    /**
     * Display a checkbox list (multiple selection) from an array.
     * 
     * @param prompt the prompt text
     * @param options an array of available options
     * @return a list of 0-based indices of selected options
     */
    public static List<Integer> check(Object[] options, int[] initialSelection, String prompt) {
        return check(Arrays.asList(options), Arrays.stream(initialSelection).boxed().toList(), prompt);
    }
    
    /**
     * Display a checkbox list (multiple selection) from a list.
     * 
     * @param prompt the prompt text
     * @param options a List of available options
     * @return a list of 0-based indices of selected options
     */
    public static List<Integer> check(Iterable<?> options, List<Integer> initialSelection, String prompt) {
        return check(options, initialSelection, Map.of("prompt", prompt));
    }

    /**
     * Display a checkbox list with an initial selection.  Entering the space-separated choice 
     * characters (e.g. "1 3 4") selects the corresponding options and returns their indices. 
     * Entering '0' signals no selection and returns -1.
     * 
     * @param options an array of available options
     * @param initialSelection 0-based index of the initially selected option
     * @param config the configuration map containing prompt and other settings
     * @return the 0-based indices of the selected options, or -1 if no selection is made
     * 
     * @param prompt the prompt text
     * @param options a List of available options
     * @return a list of 0-based indices of selected options
     */
    private static List<Integer> check(Iterable<?> options, List<Integer> initialSelection, Map<String,Object> config) {
        if (config == null) {  // Avoid null pointer if config is not provided
            config = Map.of();
        }
        // Display menu items
        int i = 0;
        for (Object option : options) {
            System.out.print((1 + i) + ". [");
            System.out.print((initialSelection.contains(i) ? "X" : " ") + "] ");
            System.out.println(option);
            i++;
        }
        int numItems = i;
        String prompt = config.containsKey("prompt") ? (String) config.get("prompt") : PROMPT_CHECK;
        // Get user choice
        String choices = Read.text(prompt);
        // Parse space-separated choices into list of integers (0-based indices)
        String[] choiceArray = choices.trim().split("\\s+");
        List<Integer> finalSelection = new java.util.ArrayList<>();
        int numErrors = 0;
        for (String choiceStr : choiceArray) {
            try {
                int choice = Integer.parseInt(choiceStr);
                if (choice >= 1 && choice <= numItems) {
                    finalSelection.add(choice - 1);
                } 
                else if (choice == 0) {
                    return List.of(-1);  // Return -1 to indicate no selection
                } else {
                    numErrors++;
                    System.err.println("Choice out of range: " + choice);
                }
            } catch (NumberFormatException e) {
                numErrors++;
                System.err.println("Invalid input: " + choiceStr);
            }
        }
        if (numErrors > 0) {
            System.out.println("Some choices were invalid. Please try again.");
            return check(options, initialSelection, config);
        }

        return finalSelection;        
        
    }
    
}

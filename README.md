
PrimiTUI -- a Simple Java TUI Library
======================================

A minimal, pragmatic Java library for interactive command-line text user interfaces (TUIs). Provides utility library classes with methods for the frequently used operations: writing things on console (`Show`), reading basic values like numbers, text and Y/N (`Read`) and picking (selecting) from several options like menus, radio and checkbox lists (`Pick`).


Basic documentation
--------------------

**Picking from a list**

For methods that accept options/items, all of the following are supported:

*    Varargs: `method(String prompt, Object...)` -- with no customization
*    Iterables: `method(Iterable<?> choices, [initialSelection], String prompt, ...)` -- with customization
*    Array: `method(Object[] choices, String prompt, ...)` -- array variant of the previous

All three variants produce identical behavior; choose based on how your data is structured.

**Reading from input**

An assortment of methods is provided, including:

* Text and "password" input
* Numerical input, separate methods for integer and real numbers
* Confirmation, accepting common alteratives (y/Y/true/T/1 for _yes_, likewise for _no_)

Length/value constraints can be provided for text and numerical input.

**Writing things on console**

Shortcut methods to cater for simple common cases:

* Printing any object as text, via `toString()` to _stdout_
* Formatted text: `textf(String fmt, Object args...)`
* Simple headings, dividers and "clean console"
* Error message through `toString()` to _stderr_

**General behaviour**

Prompts can be specified for the `Pick` and `Read` operations. On error, methods print to _stderr_ and retry, if possible/sensible.  Default prompts and error messages are in English only.  


Approach and motivation
-----------------------

The _PrimiTUI_ comes from _Primit_-ive _TUI_ (not the most clever name but then again, maybe it'll click :-).  

The initial motivation was, that I needed a simple TUI library which students could use in their semester projects, focusing on application functionality and design (rather than getting trapped in overblown libraries and frameworks).  Searching the internet for a couple of days didn't reveal anything useful, to my surprise...  So I gave myself one day of GenAI-assisted coding and `primitui` was born.

The result is actually quite unique in providing an ultra-simple static API -- most existing TUI libraries like [Text-IO](https://github.com/beryx/text-io) are rather heavy for the simple use cases, use builder patterns or require configuration objects.  The closest similar effort is [java-tui](https://github.com/olivertwistor/java-tui) by Johan Nilsson, but it's too simple and verbose ;-) 

This KISS approach lead to a few design decisions:

* No frills and kitchen sinks. Just the bare minimum that I thought is necessary.
* Class and method names were chosen to be terse, saving keystrokes ;-) 
* No configuration and I10N is provided (at the moment), apart from the defined method parameters. 


Usage examples
--------------

```java

    private static java.util.List<Person> people = java.util.Arrays.asList(
        new Person("Alice", 30, 50000),
        new Person("Bob", 25, 60000),
        new Person("Charlie", 35, 55000),
        // ...
        new Person("Mike", 34, 68000)
    );

    public static void main(String[] args) {

        java.util.List<Object> options;

        Show.clear();
        Show.header("Welcome to the TUI library demo!");

        java.util.List<Object> menu = java.util.Arrays.asList("Radio", 
            "Checkboxes", "Input reading", "Menu from objects", 
            "Silly you", "END");
        int choice = -1;

        while (choice != menu.size() - 1) {  // Loop until "END" is selected

            Show.nl();
            Show.header("menu",'-');
            choice = Pick.menu(menu);
            System.out.println("You selected option " + (choice + 1));
            Show.hr();

            switch (choice) {
                case 0:
                    Show.header("radio",'-');
                    options = java.util.Arrays.asList("Option A", "Option B", "Option C");
                    int radioChoice = Pick.radio(options, 2);
                    Show.text("You switched to option " + (char)('A' + radioChoice));              
                    break;
                
                case 1:
                    Show.header("checkboxes",'-');
                    String[] checkArray = {"Value A", "Value B", "Value C", "Value D", "Value E"};
                    int[] defaultValues = {0, 2}; // Default selected values
                    java.util.List<Integer> checkboxes = Pick.check(checkArray, defaultValues, "Select some: ");
                    Show.text("You selected options with indexes " + checkboxes);
                    break;

                case 2:
                    Show.header("input reading",'-');
                    String name = Read.text("Enter your name: ");
                    int age = Read.num("Enter your age: ", 0, 120);
                    double salary = Read.real("Enter your salary: ", 0, Double.MAX_VALUE);
                    String password = Read.hidden("Enter your password: ");
                    String country = Read.text("Enter your 2-letter country code: ", 2, 2); 
                    
                    Show.textln("Name: " + name);
                    Show.textf("Age: %d\n", age);
                    Show.text("Salary: "); Show.text(salary); Show.nl();
                    Show.textln("Password: " + "*".repeat(password.length()));
                    Show.textln("Country: " + country);
                    break;

                case 3:
                    Show.header("Menu from list of objects",'-');
                    int personChoice = Pick.menu(people);
                    Show.textln("You selected: " + people.get(personChoice));
                    break;

                case 5:
                    break;

                default:
                    Show.errf("Invalid choice: %d\n", choice);
                    break;
            }
        }
        
        Show.header("Goodbye!");
        
    }    

```


Contributing
------------

If you use the library, give credit to the author :-) .  If you have a need to improve, extend or modify, fork and maybe send PRs.  Some ideas for possible future work are in [the issues here](https://github.com/pbrada/primitui-java/issues); but as time is scarce and simplicity is desired, well designed contributions are welcome.

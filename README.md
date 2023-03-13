# Multi-tape Turing machine
This project is a Java implementation of a Turing machine simulator,
equipped with sleazy Swing GUI (work in progress) and 
unintuitive input file format (also work in progress).

What differs between this simulation and many others available on the web
is that the concept of Turing machine is extended to operate over arbitrary numbers of tapes.

## Input file
The input file is a text containing the configuration of the machine. 
It specifies (each point in new line):
1. Number of tapes `N`
2. List of internal states (separated by space)
3. Initial state
4. String interpreted as blank symbol
5. Initial content of tapes -- each tape in new line, symbols separated by spaces
The head starts at the leftmost symbol of every tape.
6. Instructions for the machine -- each in new line, symbols separated by a space
   1. first symbol -- input state
   2. following `N` symbols -- input contents of the tapes
   3. next symbol -- result state
   4. following `N` symbols -- contents to write to the tapes
   5. following `N` symbols -- movements of the head in regard to each tape
      - R -- head moves right
      - N -- head does not move
      - L -- head moves left


if there's an error in the input file, the program will let you know where to find it ;) 

There are several demo input files available in the `input_files` directory. 
## Usage
Build the app and run the `main` method from `pl.polsl.tomasz.michalik.controller.Controller`.
Enter the relative path to the input file and click `load` -- that should fill the Swing viewport with your configuration.



## Web version
There's also an alternative version of the software being implemented as a webapp, with even worse HTML -- if 
you're keen on 2010 webpage layout feel free to poke around in the branches .)

## Contact
As i like Turing and his machine, there's a huge chance the project will be further developed 
in future and all those "works in progress" would be replaced by more user-friendly solutions. 
Therefore if you wish to leave some insightful comments or notify me of a bug you've come across 
feel free to open an issue!
# BoyerMoore-Demonstration

   This is the simple demostration of boyer moore string search algorithm using a animation for showing how the comaparisions are being done between the search string and text in which string is to be searched.
   
   ## BoyerMooreDemo.java file
   This file consists of the code for demonstaring the boyer-moore string search algorithm for pattern matching using animation and java swing.
   
   **Methods and their description** 
   
   1. *BoyerMooreDemo()* : It is the constructor which initializes the components which are to be added to the JFrame
   2. *constructFrame()* : This is the method which adds various elements to the JFrame and also add the functionlity to those elements
   3. *badCharHeuristic( char str[], int size,int badchar[])* :this method gives bad character rule heuristic calculation for searching string
        - str - character array with chars in search string
        - size - len of str
        - badchar[] - int array in which heuristic is stored
   4. *search( char txt[],  char pat[], boolean animate)* : this method comprises of the algorithm for boyer-moore algo and also shows the step by step execution using animation.
        - txt - text in which pattern is to be searched
        - pat - pattern which is to be searched
        - animate - method executes the animation logic if animate is true else executes normally
   5. *animate()* : starts the thread which runs the algorithm for showing the step by step demostration of algo
   6. *findAll()* : Highlights all the occurences of pattern in the text
 
 ## Wire Frame Diagram
 *detailed wire-frame diagram can be found [here](https://wireframe.cc/pro/pp/0fd4d1aff231057)*
 *OR you can find it in BMDemo-wireframe.pdf*

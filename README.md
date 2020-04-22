# Text-to-object

This is a program for parsing a set of legal documents for the purpose of displaying their table of contents as well
as searching them for individual sections or ranges of sections. The main objective of this project was to learn how 
to transform structuralized text into an object representation and perfom certain operations on it.

Example usages might include:

```Java
-f <path to file> -t                  //prints the table of contents

-f <path to file> -a 4                //prints Article 4
-f <path to file> -c I                //prints Chapter I
-f <path to file> -a 54 -p 1
-f <path to file> -a 108 -p 1 -pp1    //prints Article 108, Paragraph 1, Section 1

-f <path to file> -c I -c III         //prints Chapters I - III
-f <path to file> -c I -c II -a 50    //prints the contents from Chapter I up to Chapter II, Article 50 inclusive
```

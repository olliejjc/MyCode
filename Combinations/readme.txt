-------------------
Program Notes
-------------------

Consider a list A of strings B. Each string B starts with a token name T, followed by the symbol ‘=' and a comma-separated list of values {Vx}: 

B: “T=V1,V2,V3,..." 

The B string can be interpreted as: “the token T can take the value V1 or V2 or V3, etc.”. 

The goal is to generate a list C containing all unique combinations of A. 

In other words C is a list of all the unique strings D, where each D is a concatenation of multiple
"T=V" strings, such that:

• Each token T is used exactly once within each D,
• Each token-value combination is valid, i.e. defined by the list A.
• Additionally, pairs of token-value should be concatenated within each C using
the character '&'

An example input would be:

["a=a1,a2","b=b1,b2,b3","c=c1,c2"]

An example output would be:

[
"a=a1&b=b1&c=c1",
"a=a1&b=b1&c=c2",
"a=a1&b=b2&c=c1",
"a=a1&b=b2&c=c2",
"a=a1&b=b3&c=c1",
"a=a1&b=b3&c=c2",
"a=a2&b=b1&c=c1",
"a=a2&b=b1&c=c2",
"a=a2&b=b2&c=c1",
"a=a2&b=b2&c=c2",
"a=a2&b=b3&c=c1",
"a=a2&b=b3&c=c2"
]

-------------------
To Run The Program
-------------------

To run the program enter php combinations.php within the Combinations folder

When the program is running it will ask you to enter a list of strings in the format ["T=V1,V2,V3,...", "U=W1,W2,W3,..."]

An example input would be:

["a=a1,a2,a3","b=b1,b2,b3","c=c1,c2,c3"]
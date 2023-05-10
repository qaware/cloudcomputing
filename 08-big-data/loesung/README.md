# Übung 1: MapReduce

Schritt 1: Normalize

```
"o tannenbaum o tannenbaum"
"wie grün sind deine blätter"
"du grünst nicht nur zur sommerszeit"
"nein auch im winter wenn es schneit"
"o tannenbaum o tannenbaum"
"wie grün sind deine blätter"
```

Schritt 2: Map

```
1: [("o", 1), ("tannenbaum", 1), ("o", 1), ("tannenbaum", 1)]
2: [("wie", 1), ("grün", 1), ("sind", 1), ("deine", 1), ("blätter", 1)]
3: [("du", 1), ("grünst", 1), ("nicht", 1), ("nur", 1), ("zur", 1), ("sommerszeit", 1)]
4: [("nein", 1), ("auch", 1), ("im", 1), ("winter", 1), ("wenn", 1), ("es", 1), ("schneit", 1)]
5: [("o", 1), ("tannenbaum", 1), ("o", 1), ("tannenbaum", 1)]
6: [("wie", 1), ("grün", 1), ("sind", 1), ("deine", 1), ("blätter", 1)]
```

Schritt 3: Reduce

```
("o", 4)
("tannenbaum", 4)
("wie", 2)
("grün", 2)
("sind", 2)
("deine", 2)
("blätter", 2)
("du", 1)
("grünst", 1)
("nicht", 1)
("nur", 1)
("zur", 1)
("sommerszeit", 1)
("nein", 1)
("auch", 1)
("im", 1)
("winter", 1)
("wenn", 1)
("es", 1)
("schneit", 1)
```

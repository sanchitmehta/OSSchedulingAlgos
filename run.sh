javac *.java
for var in 1 2 3 4 5 6 7
do
    java Scheduler data/input-$var.txt fcfs 0
    echo "\n\n\n\n------------\n\n\n"
    java Scheduler data/input-$var.txt sjf 0
    echo "\n\n\n\n------------\n\n\n"
    java Scheduler data/input-$var.txt rr 0
    echo "\n\n\n\n------------\n\n\n"
    java Scheduler data/input-$var.txt uni 0
    echo "\n\n\n\n------------\n\n\n"
done

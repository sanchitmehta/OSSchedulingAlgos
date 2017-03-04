javac Scheduler.java
for var in 1 2 3 4 5 6 7
do
	java Scheduler data/input-$var.txt
	echo "\n\n\n\n------------\n\n\n"
done


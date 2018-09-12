javac Main.java
echo --Main class compiled
javac Server.java
echo --Server class compiled
jar cfe Server.jar Main Main.class Server.class
echo --Jar archive made, running...
java -jar Server.jar
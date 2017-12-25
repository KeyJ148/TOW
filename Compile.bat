rmdir /s/q bin
mkdir bin
javac -classpath ./lib/lwjgl.jar;./lib/slick-util.jar -sourcepath ./src -d ./bin -encoding UTF-8 ./src/engine/Loader.java ./src/engine/net/server/GameServer.java ./src/game/client/person/equipment/armor/* ./src/game/client/person/equipment/gun/* ./src/game/client/person/equipment/bullet/*
pause
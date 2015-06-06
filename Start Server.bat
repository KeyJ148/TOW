xcopy bin\main main\ /s /e /h /y /q
java main/GameServer
rmdir main\ /s /q
pause
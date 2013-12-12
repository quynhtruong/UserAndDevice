call cd "D:\clone-qsoft\Dropbox\qsoft\tech_stack\jwebsocket\source\UserAndDevice\myjwebsocket"
call mvn clean install -DskipTests=true
echo finished build sm bundles
REM  /Y overwrite exists files, replace by your sm deploy folder
call xcopy "D:\clone-qsoft\Dropbox\qsoft\tech_stack\jwebsocket\source\UserAndDevice\myjwebsocket\target\myjwebsocket-1.0.jar" "D:\qsoft\jwebsocket\jWebSocketServer-1.0-b30518\jWebSocket-1.0\libs" /Y

echo all bundlers has been copied
REM start SM
echo starting SM
call "D:\qsoft\jwebsocket\jWebSocketServer-1.0-b30518\jWebSocket-1.0\bin\myJWebSocketServer.bat"
call cd..
pause

call cd "H:\clone-qsoft\Dropbox\qsoft (1)\tech_stack\jwebsocket\source\UserAndDevice\myjwebsocket"
call mvn clean install -DskipTests=true
echo finished build sm bundles
REM  /Y overwrite exists files, replace by your sm deploy folder
call xcopy "H:\clone-qsoft\Dropbox\qsoft (1)\tech_stack\jwebsocket\source\UserAndDevice\myjwebsocket\target\myjwebsocket-1.0.jar" "H:\qsoft\tech stack\jwebsocket\jWebSocketServer-1.0-b30518-thinh\jWebSocketServer-1.0-b30518\jWebSocket-1.0\libs" /Y

echo all bundlers has been copied
REM start SM
echo starting SM
call "H:\qsoft\tech stack\jwebsocket\jWebSocketServer-1.0-b30518-thinh\jWebSocketServer-1.0-b30518\jWebSocket-1.0\bin\myJWebSocketServer.bat"
pause

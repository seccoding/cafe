<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
    .true {
        background-color: yellow;
    }
    
    .false {
        background-color: green;
    }
</style>
<script type="text/javascript" src="/js/lib/jquery-3.7.1.js"></script>
<script type="text/javascript" src="/js/lib/sockjs.min.js"></script>
<script type="text/javascript" src="/js/socket.js"></script>
<script type="text/javascript">
    $().ready(function() {
        
        var chatSend = undefined;
        
        $("#sendBtn").click(function() {
            var message = $("#chatMessage").val();
            $("#chatMessage").val("");
            
            chatSend({
                roomName: "test",
                sendType: "all",
                message: message
            })
        });
        
        chatSend = connectSocket("${sessionScope._LOGIN_USER_.name}"
                               , "${sessionScope._LOGIN_USER_.email}"
                               , function(content) {
            var chatBox = $(".chat-box");
            
            var message = "<div>" + content.message + "</div>";
            message = message.replaceAll("↵", "<br/>").replaceAll("\n", "<br/>");
            
            var isMe = content.sendToMe == true;
            if ( !isMe ) {
                message = '<div style="width:50px; margin-right: 3px;">' + content.userName + '</div>' + message;
            }
            
            chatBox.append('<div class=' + isMe + '>' + message + '</div>');
            chatBox.scrollTop(99999999999999999999999);
        });
        
    });
</script>
</head>
<body>
    <div>
       <input type="text" id="chatMessage" />
       <button id="sendBtn">전송</button>
    </div>
    <div class="chat-box"></div>
</body>
</html>
/**
 * 채팅 관련 JS 라이브러리
 */
var sock = undefined

function connectSocket(userName, userEmail, receiveCallback) {
    // 채팅서버 연결
    sock = new SockJS("/cafe-chat");
    
    // 소켓서버 연결 완료 콜백
    sock.onopen = function() {
        
        // send : connection으로 message를 전달
        // connection이 맺어진 후 가입(JOIN) 메시지를 전달
        send({
                roomName: "test",
                sendType: "enter",
                userName: userName,
                userEmail: userEmail,
                message: userName + "님이 입장했습니다."
        })
        
        // onmessage : message를 받았을 때의 callback
        sock.onmessage = function(e) {
            var content = JSON.parse(e.data);

            if (content.sendType == "all" || content.sendType == "enter") {
                receiveCallback(content);
            }
        }
    }
    
    // 일반 메시지 전송
    var send = function(message) {
        var msg = {
            roomName : message.roomName,
            sendType : message.sendType,
            userName : userName,
            userEmail: userEmail,
            message : message.message
        };
        
        sock.send(JSON.stringify(msg));
    }
    
    
    return send;
    
}

/**
 * This software is protected by copyright laws and international copyright
 * treaties. The ownership and intellectual property rights of this software
 * belong to the developer. Unauthorized reproduction, distribution,
 * modification, sale, or commercial use of this software is strictly prohibited
 * and may result in legal consequences. This software is licensed to the user
 * and must be used in accordance with the terms of the license. Under no
 * circumstances should the source code or design of this software be disclosed
 * or leaked. The developer shall not be liable for any loss or damages. Please
 * read the license and usage permissions carefully before using.
 */

var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");
var showButton = true;
var websocket;

$(document).ready(function() {
	debugger;
	
	// getToken 함수를 호출하여 JWT 토큰을 얻음
	getToken('autoCoding1', 'jw0713!@')
	    .then((jwtToken) => {
	        // JWT 토큰을 사용하여 웹소켓 연결 설정
	        websocket = new WebSocket("ws://192.168.219.106:8080/chat?token="+jwtToken);
	        
	        // 웹소켓 연결이 열렸을 때 실행
	        websocket.onopen = () => {
	            console.log("WebSocket connection opened.");       
	            
	            // 메시지 전송
	            function sendMessage() {
	                var messageInput = $('#message');
	                var message = messageInput.val();
	                if (message) {
	                    var chatMessage = {
	                    	message: message,
	                        sender: 'autoCoding'
	                    };
	                    
	                    messageInput.val('');
	                    
	                    //웹소켓으로도 보내주기
	                    websocket.send(JSON.stringify(chatMessage));
	                }
	            }
	            
	            // 전송 버튼 클릭 이벤트에 메시지 전송 함수 연결
	            $('#send-button').click(sendMessage);

	        };
	    	
	    })
	    .catch((error) => {
	        // 토큰 얻기 실패
	        console.error('Token retrieval error:', error);
	    });
    
    // 웹소켓 메시지를 수신했을 때 실행
    websocket.onmessage = (event) => {
        const message = JSON.parse(event.data);
        var chatMessages = $('#chat-messages');
        var messageElement = $('<li>').text(message.sender + ": " + message.message);
        chatMessages.append(messageElement);
    };

    // 웹소켓 연결이 닫혔을 때 실행
    websocket.onclose = (event) => {
        if (event.wasClean) {
            console.log(`WebSocket connection closed cleanly, code=${event.code}, reason=${event.reason}`);
        } else {
            // 연결이 비정상적으로 닫힌 경우
            console.error('WebSocket connection closed unexpectedly');
        }
        
        // 사용자에게 알림을 띄우거나 다른 처리를 수행할 수 있습니다.
        alert('WebSocket connection closed. Please refresh the page.');
    };
});

//로그인 요청을 보내서 JWT 토큰을 얻는 함수
async function getToken(userId, pass) {
    try {
    	
        const userInfo = {
                username: userId,
                password: pass
            };
    	
        const response = await fetch('/api/getToken', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userInfo),
        });

        if (!response.ok) {
            throw new Error('Authentication failed');
        }

        const token = await response.text();
        return token;
    } catch (error) {
        console.error('Authentication error:', error);
        throw error;
    }
}
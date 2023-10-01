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
var showButton = true; // 이 부분을 원하는 조건에 따라 true 또는 false로 변경하세요.
var token;
$(document).ready(function() {
	debugger;
	
	// getToken 함수를 호출하여 JWT 토큰을 얻음
	getToken('autoCoding1', 'jw0713!@')
	    .then((jwtToken) => {
	    	token = jwtToken;
	    	
	        // WebSocket 연결 설정
	        var socket = new SockJS('/ws-service');
	        var stompClient = Stomp.over(socket);
	        
	     // STOMP 클라이언트에 연결할 때 사용할 헤더 설정
	        var headers = {
	            'Authorization': 'Bearer ' + jwtToken, // 예시: JWT 토큰을 헤더에 추가
	        };
	        
	        // WebSocket 연결이 완료된 후에 메시지 전송 및 구독 설정
	        stompClient.connect({ Authorization: 'Bearer ' + jwtToken }, function(frame) {
	            console.log('WebSocket 연결 성공');

	            // 메시지 전송
	            function sendMessage() {
	                var messageInput = $('#message');
	                var message = messageInput.val();
	                if (message) {
	                    var chatMessage = {
	                    	roomId: 'ROOM_001',	
	                    	sender: 'Admin',
	                    	message: message,
	                    	timestamp: getCurrentTimestamp()
	                    };
	                    stompClient.send("/app/chat/send", {}, JSON.stringify(chatMessage));
	                    messageInput.val('');
	                    
	                }
	            }

	            // 전송 버튼 클릭 이벤트에 메시지 전송 함수 연결
	            $('#send-button').click(sendMessage);

	            // 메시지 수신 처리
	            stompClient.subscribe('/topic/chat', function(response) {
	                var message = JSON.parse(response.body);
	                var chatMessages = $('#chat-messages');
	                var messageElement = $('<li>').text(message.sender+"("+message.timestamp + ") : " + message.message);
	                chatMessages.append(messageElement);
	            });
	            
	            // 기존 메세지 조회
	            getPreviousMessages();
	        }, function(error) {
	            // 연결이 실패한 경우 실행되는 콜백 함수
	            console.error('Error in STOMP connection', error);
	        });
	    	
	    })
	    .catch((error) => {
	        // 토큰 얻기 실패
	        console.error('Token retrieval error:', error);
	    });
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

function getCurrentTimestamp() {
    const now = new Date();
    const hours = now.getHours();
    const minutes = now.getMinutes();
    const period = hours >= 12 ? '오후' : '오전';
    const formattedHours = hours % 12 || 12; // 0시를 12시로 표시

    const formattedTimestamp = `${period} ${formattedHours}:${minutes}`;

    return formattedTimestamp;
}

function getPreviousMessages() {

	var params = {
		"roomId": "ROOM_001",	
		"page": 1,
		"size": 100
	};
	
	callAjax("/api/chat/list", "POST", params, getPreviousMessagesCallback, token);
}

function getPreviousMessagesCallback(data) {

	if (data.messages != undefined && data.messages != "") {
		displayPreviousMessages(data.messages);
	}
}

//이전 메시지 조회 및 표시 (역순으로 표시)
function displayPreviousMessages(data) {
    var chatMessages = $('#chat-messages');
    var currentUpdateDt = ''; // 현재 날짜를 저장할 변수

    data.reverse().forEach(function (message) { // 데이터를 역순으로 반복
        var updateDt = message.updateDt.split(' ')[0]; // 날짜 부분만 추출

        // updateDt가 변경되었을 때, 해당 날짜를 표시하는 엘리먼트 추가
        if (updateDt !== currentUpdateDt) {
            currentUpdateDt = updateDt;
            var dateElement = $('<li>').text('[' + updateDt + ']');
            chatMessages.append(dateElement); // 날짜 엘리먼트를 하단에 추가
        }

        // 메시지 표시
        var messageElement = $('<li>').text(message.sender + " (" + message.timestamp + ") : " + message.message);
        chatMessages.append(messageElement); // 메시지 엘리먼트를 하단에 추가
    });
}



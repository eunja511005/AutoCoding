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

$(document).ready(function() {
	debugger;
    // WebSocket 연결 설정
    var socket = new SockJS('/ws-service');
    var stompClient = Stomp.over(socket);
    
    // WebSocket 연결이 완료된 후에 메시지 전송 및 구독 설정
    stompClient.connect({}, function(frame) {
        console.log('WebSocket 연결 성공');

        // 메시지 전송
        function sendMessage() {
            var messageInput = $('#message');
            var message = messageInput.val();
            if (message) {
                var chatMessage = {
                	message: message,
                    sender: 'Admin'
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
            var messageElement = $('<li>').text(message.sender + ": " + message.message);
            chatMessages.append(messageElement);
        });
    });
});
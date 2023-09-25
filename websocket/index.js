const express = require("express");
const app = express();
const redis = require("redis");
const http = require("http");
const server = http.createServer(app);
const Subscriber = redis.createClient({url: "redis://redis-server:6379"});
const io = require('socket.io')(server);

// Redis 연결 성공 시
Subscriber.on('connect', () => {
    console.info('Redis Sub connected!');
});

// Redis 연결 실패 시
Subscriber.on('error', (err) => {
    console.error('Redis Sub Client Error', err);
});

const channel = "topic3";

// Redis 연결(connect 이벤트 호출)
Subscriber.connect().then(); 

// Subscriber: 특정 채널을 구독하고 메시지를 처리
function subscribeToChannel(channel) {
    Subscriber.subscribe(channel, (message) => {
        console.log(`message : ${message}`);
        console.log(typeof message);
        const a = {"sender" : message["sender"], "context" : message["context"]}
        console.log(`message : ${a}`);
        console.log(typeof a);
        io.emit("message", message);
    });
}

// 소켓 연결 처리(connection은 연결에 대한 기본 설정)
io.on("connection", (socket) => {
    console.log(`Socket Connected`); 
    
    // Sub로 데이터 받고 바로 Agent로 데이터 전송
    subscribeToChannel(channel);

    // 소켓 연결 해제
    socket.on("disconnect", () => {
       console.log("Socket Disconnected");
    });
});

// 포트번호 3000으로 서버 실행
server.listen(3000, () => {
  console.log("Server Start");
});
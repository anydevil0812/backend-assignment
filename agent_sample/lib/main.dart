import 'dart:async';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:socket_io_client/socket_io_client.dart' as IO;
import 'package:agent_sample/message.dart';

void main() {
  runApp(
      const MyApp(),
  );
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Agent Sample',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.cyanAccent),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Agent Sample Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {

  const MyHomePage({super.key, required this.title});

  final String title;

  get socket => IO.io('http://localhost:3000',
                      IO.OptionBuilder().setTransports(['websocket']).build());

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final messageController = StreamController<Message>(); // ??
  final List<Message> messageList = []; // Message 객체의 리스트로 변경

  @override
  void initState() {
    super.initState();

    WidgetsBinding.instance.addPostFrameCallback((_) =>
        widget.socket.on("message", (data) {
          try {
            // JSON 문자열을 Map<String, dynamic>으로 파싱
            final Map<String, dynamic> messageData = json.decode(data);

            // Map<String, dynamic> => Message 변환
            Message message = Message.fromJson(messageData);

            // ??
            messageController.sink.add(message);
            setState(() => messageList.add(message));
          } catch (e) {
            // 파싱에 실패한 경우 예외 처리
            print("Error parsing message data: $e");
          }
        })
    );
  }

  @override
  void dispose() {
    super.dispose();
    messageController.close();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme
            .of(context)
            .colorScheme
            .inversePrimary,
        title: const Text('Agent-Sample'),
      ),
      body: ListView.builder(
        itemBuilder: (BuildContext context, int index) {
          return Text(
            "Success: ${messageList[index]}",
          );
        },
        itemCount: messageList.length, // 리스트의 길이로 itemCount 설정
      ),
    );
  }
}

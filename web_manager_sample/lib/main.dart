import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Web Manager',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.blueAccent),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Web Manager Test'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;
  String result = '';

  // 컨트롤러를 이용하여 입력받은 텍스트 데이터 추출
  final senderTextEditController = TextEditingController();
  final contextTextEditController = TextEditingController();

  @override
  void dispose() {
    senderTextEditController.dispose();
    contextTextEditController.dispose();
    super.dispose();
  }

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  onPressPost() async {
    var url = 'http://localhost:8080/api/chat';
    try {
      Map data = {"sender" : senderTextEditController.text, "context" : contextTextEditController.text};
      var body = json.encode(data);
      http.Response response = await http.post(
          Uri.parse(url),
          headers: {"Content-Type": "application/json"},
          body: body);
      print('statusCode : ${response.statusCode}');
      if (response.statusCode == 200 || response.statusCode == 201) {
        setState(() {
          result = response.body;
        });
      } else {
        print('error......');
      }
    } catch (e) {
      print('error ... $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'sender & context 입력',
              style: TextStyle(
                fontWeight: FontWeight.bold,
                color: Colors.purple,
                fontSize: 20,
              ),
            ),
            TextField(
              controller: senderTextEditController,
              decoration: InputDecoration(
                labelText: "sender",
                prefixIcon: Icon(Icons.input),
                border: OutlineInputBorder(),
                hintText: "발신자명",
                helperText: "발신자명을 입력하세요",
              ),
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            TextField(
              controller: contextTextEditController,
              decoration: InputDecoration(
                labelText: "context",
                prefixIcon: Icon(Icons.input),
                border: OutlineInputBorder(),
                hintText: "내용",
                helperText: "보낼 내용을 입력하세요",
              ),
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            ElevatedButton(onPressed: onPressPost, child: Text("POST"))
          ],
        ),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}

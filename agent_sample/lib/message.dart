class Message {
  String sender;
  String context;

  Message({
    required this.sender,
    required this.context
  });

  // Dart에서는 json 데이터를 다루기 위해서 Map<String, dynamic> 자료형을 사용
  factory Message.fromJson(Map<String, dynamic> message) {
    if (message == null) {
      // data가 null인 경우 예외 처리
      throw ArgumentError("message cannot be null");
    }
    final sender = message['sender'];
    final context = message['context'];

    if (sender is String && context is String) {
      return Message(
        sender: sender,
        context: context,
      );
    } else {
      // 데이터가 필요한 형식이 아닌 경우 예외 처리
      throw ArgumentError("Invalid message format => sender : ${sender.runtimeType}, context : ${context.runtimeType}");
    }
  }

  @override
  String toString() {
    return 'Message(sender: $sender, context: $context)';
  }
}
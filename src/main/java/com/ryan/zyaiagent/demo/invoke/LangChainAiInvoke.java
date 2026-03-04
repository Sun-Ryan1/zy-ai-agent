package com.ryan.zyaiagent.demo.invoke;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;

public class LangChainAiInvoke {
    public static void main(String[] args) {
        ChatLanguageModel qwenChatModel = QwenChatModel.builder()
                .apiKey(TestApiKey.API_KEY) // Replace with  API key
                .modelName("qwen-max")
                .build();
        String answer = qwenChatModel.chat("我是程序员Ryan，这是原创的 AI 超级智能体项目");
        System.out.println(answer);
    }
}

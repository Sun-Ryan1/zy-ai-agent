<template>
  <div class="chat-container">
    <div class="chat-header">
      <button class="back-btn" @click="goBack">
        <span class="back-icon">←</span>
        <span class="back-text">返回</span>
      </button>
      <div class="header-center">
        <img src="../assets/love-ai-avatar.svg" alt="AI" class="header-avatar" />
        <h2>AI 恋爱分析师</h2>
      </div>
      <span class="chat-id">会话ID: {{ chatId }}</span>
    </div>
    
    <div class="chat-messages" ref="messagesContainer">
      <div 
        v-for="(msg, index) in messages" 
        :key="index" 
        :class="['message', msg.role === 'user' ? 'user-message' : 'ai-message']"
      >
        <div class="message-avatar" v-if="msg.role === 'ai'">
          <img src="../assets/love-ai-avatar.svg" alt="AI" />
        </div>
        <div class="message-content">
          <div class="message-text">{{ msg.content }}</div>
        </div>
        <div class="message-avatar user-avatar" v-if="msg.role === 'user'">
          <img src="../assets/user-avatar.svg" alt="User" />
        </div>
      </div>
      <div v-if="isLoading" class="message ai-message">
        <div class="message-avatar">
          <img src="../assets/love-ai-avatar.svg" alt="AI" />
        </div>
        <div class="message-content">
          <div class="message-text loading">
            <span class="dot"></span>
            <span class="dot"></span>
            <span class="dot"></span>
          </div>
        </div>
      </div>
    </div>
    
    <div class="chat-input">
      <textarea 
        v-model="inputMessage" 
        @keydown.enter.exact.prevent="sendMessage"
        placeholder="输入你的情感问题..."
        rows="1"
        ref="inputRef"
      ></textarea>
      <button 
        class="send-btn" 
        @click="sendMessage" 
        :disabled="!inputMessage.trim() || isLoading"
      >
        <span class="heart-icon">♡</span>
        发送
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { chatWithLoveApp, generateChatId } from '../utils/api'

const router = useRouter()

const chatId = ref('')
const messages = ref([])
const inputMessage = ref('')
const isLoading = ref(false)
const messagesContainer = ref(null)
const inputRef = ref(null)
let currentEventSource = null

onMounted(() => {
  chatId.value = generateChatId()
  inputRef.value?.focus()
})

const scrollToBottom = () => {
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
    }
  })
}

const sendMessage = () => {
  const message = inputMessage.value.trim()
  if (!message || isLoading.value) return
  
  messages.value.push({
    role: 'user',
    content: message
  })
  
  inputMessage.value = ''
  isLoading.value = true
  scrollToBottom()
  
  let aiResponse = ''
  
  messages.value.push({
    role: 'ai',
    content: ''
  })
  
  currentEventSource = chatWithLoveApp(
    message,
    chatId.value,
    (data) => {
      aiResponse += data
      messages.value[messages.value.length - 1].content = aiResponse
      scrollToBottom()
    },
    (error) => {
      console.error('SSE Error:', error)
      isLoading.value = false
    },
    () => {
      isLoading.value = false
    }
  )
}

const goBack = () => {
  if (currentEventSource) {
    currentEventSource.close()
  }
  window.__isReturningFromChild = true
  router.push('/')
}
</script>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  height: 100dvh;
  background: #FFFFFF;
}

.chat-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 20px;
  background: linear-gradient(135deg, #FF85A2 0%, #FFB6C1 50%, #FFC0CB 100%);
  color: #fff;
  box-shadow: 0 4px 20px rgba(255, 133, 162, 0.3);
  gap: 12px;
  position: relative;
  overflow: hidden;
}

.chat-header::before {
  content: '';
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 70%);
  animation: shimmer 3s ease-in-out infinite;
}

@keyframes shimmer {
  0%, 100% { transform: translateX(-30%) translateY(-30%); }
  50% { transform: translateX(30%) translateY(30%); }
}

.header-center {
  display: flex;
  align-items: center;
  gap: 10px;
  flex: 1;
  justify-content: center;
  position: relative;
  z-index: 1;
}

.header-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  box-shadow: 0 2px 12px rgba(255, 77, 109, 0.4);
  border: 2px solid rgba(255, 255, 255, 0.8);
}

.chat-header h2 {
  font-size: 18px;
  font-weight: 600;
  white-space: nowrap;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

.back-btn {
  display: flex;
  align-items: center;
  gap: 4px;
  background: rgba(255, 255, 255, 0.25);
  border: none;
  color: #fff;
  padding: 8px 12px;
  border-radius: 20px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
  flex-shrink: 0;
  position: relative;
  z-index: 1;
  backdrop-filter: blur(10px);
}

.back-btn:hover {
  background: rgba(255, 255, 255, 0.4);
  transform: translateX(-2px);
}

.back-icon {
  font-size: 16px;
}

.chat-id {
  font-size: 11px;
  opacity: 0.9;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 120px;
  flex-shrink: 0;
  position: relative;
  z-index: 1;
  background: rgba(255, 255, 255, 0.2);
  padding: 4px 10px;
  border-radius: 12px;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 16px;
  background-image: 
    radial-gradient(circle at 10% 20%, rgba(255, 182, 193, 0.3) 0%, transparent 20%),
    radial-gradient(circle at 90% 80%, rgba(255, 133, 162, 0.2) 0%, transparent 20%),
    radial-gradient(circle at 50% 50%, rgba(255, 192, 203, 0.1) 0%, transparent 30%);
}

.message {
  display: flex;
  gap: 10px;
  max-width: 85%;
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px) scale(0.95);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.user-message {
  align-self: flex-end;
  flex-direction: row;
}

.ai-message {
  align-self: flex-start;
  flex-direction: row;
}

.message-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  overflow: hidden;
}

.message-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.user-avatar {
  background: linear-gradient(135deg, #FFCCD5 0%, #FFE4EC 100%);
  box-shadow: 0 2px 12px rgba(255, 133, 162, 0.3);
  border: 2px solid rgba(255, 255, 255, 0.9);
}

.user-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.message-content {
  display: flex;
  flex-direction: column;
  flex: 1;
  min-width: 0;
}

.message-text {
  padding: 12px 16px;
  border-radius: 20px;
  line-height: 1.6;
  word-break: break-word;
  white-space: pre-wrap;
  text-align: left;
  font-size: 15px;
}

.user-message .message-text {
  background: linear-gradient(135deg, #FF85A2 0%, #FFB6C1 100%);
  color: #fff;
  border-bottom-right-radius: 6px;
  border-bottom-left-radius: 20px;
  box-shadow: 0 4px 15px rgba(255, 133, 162, 0.3);
}

.ai-message .message-text {
  background: #fff;
  color: #FF6B8A;
  border-bottom-left-radius: 6px;
  border-bottom-right-radius: 20px;
  box-shadow: 0 4px 15px rgba(255, 182, 193, 0.2);
  border: 1px solid rgba(255, 182, 193, 0.3);
  font-weight: 500;
}

.loading {
  display: flex;
  gap: 4px;
  padding: 16px 20px;
}

.dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: #FF85A2;
  animation: bounce 1.4s ease-in-out infinite;
}

.dot:nth-child(1) { animation-delay: 0s; }
.dot:nth-child(2) { animation-delay: 0.2s; }
.dot:nth-child(3) { animation-delay: 0.4s; }

@keyframes bounce {
  0%, 80%, 100% { transform: scale(0.8); opacity: 0.5; }
  40% { transform: scale(1); opacity: 1; }
}

.chat-input {
  display: flex;
  gap: 12px;
  padding: 12px 16px;
  background: linear-gradient(135deg, #FFE4EC 0%, #FFF0F5 100%);
  border-top: 1px solid rgba(255, 182, 193, 0.3);
  box-shadow: 0 -4px 20px rgba(255, 182, 193, 0.15);
  backdrop-filter: blur(10px);
}

.chat-input textarea {
  flex: 1;
  padding: 12px 16px;
  border: 2px solid rgba(255, 182, 193, 0.5);
  border-radius: 24px;
  font-size: 15px;
  resize: none;
  outline: none;
  transition: all 0.3s ease;
  font-family: inherit;
  max-height: 120px;
  line-height: 1.5;
  min-height: 48px;
  background: #fff;
}

.chat-input textarea:focus {
  border-color: #FF85A2;
  box-shadow: 0 0 0 3px rgba(255, 133, 162, 0.15);
}

.chat-input textarea::placeholder {
  color: #FFB6C1;
}

.send-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 12px 24px;
  background: linear-gradient(135deg, #FF85A2 0%, #FFB6C1 100%);
  color: #fff;
  border: none;
  border-radius: 24px;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  flex-shrink: 0;
  box-shadow: 0 4px 15px rgba(255, 133, 162, 0.3);
}

.send-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(255, 133, 162, 0.4);
}

.send-btn:active:not(:disabled) {
  transform: translateY(0);
}

.send-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.heart-icon {
  font-size: 16px;
}

@media (max-width: 768px) {
  .chat-header {
    padding: 10px 16px;
  }

  .chat-header h2 {
    font-size: 16px;
  }

  .header-avatar {
    width: 32px;
    height: 32px;
  }

  .chat-messages {
    padding: 16px;
    gap: 12px;
  }

  .message {
    max-width: 90%;
  }

  .message-avatar {
    width: 32px;
    height: 32px;
  }

  .message-text {
    padding: 10px 14px;
    font-size: 14px;
  }

  .chat-input {
    padding: 10px 12px;
    gap: 10px;
  }

  .send-btn {
    padding: 10px 20px;
    font-size: 14px;
  }
}

@media (max-width: 480px) {
  .back-text {
    display: none;
  }

  .back-btn {
    padding: 8px;
  }

  .chat-id {
    font-size: 10px;
    max-width: 80px;
  }

  .chat-messages {
    padding: 12px;
    gap: 10px;
  }

  .message {
    gap: 8px;
    max-width: 92%;
  }

  .message-avatar {
    width: 28px;
    height: 28px;
  }

  .message-text {
    padding: 8px 12px;
    font-size: 13px;
    border-radius: 16px;
  }

  .chat-input {
    padding: 8px 10px;
  }

  .chat-input textarea {
    padding: 10px 12px;
    font-size: 14px;
    min-height: 44px;
  }

  .send-btn {
    padding: 10px 16px;
    font-size: 13px;
  }

  .heart-icon {
    display: none;
  }
}
</style>

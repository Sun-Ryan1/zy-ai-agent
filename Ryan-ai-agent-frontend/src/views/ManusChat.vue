<template>
  <div class="chat-container">
    <div class="chat-header">
      <button class="back-btn" @click="goBack">
        <span class="back-icon">←</span>
        <span class="back-text">返回</span>
      </button>
      <div class="header-center">
        <img src="../assets/manus-ai-avatar.svg" alt="AI" class="header-avatar" />
        <h2>AI 超级智能体</h2>
      </div>
      <span class="status">
        <span class="status-dot"></span>
        在线
      </span>
    </div>
    
    <div class="chat-messages" ref="messagesContainer">
      <div 
        v-for="(msg, index) in messages" 
        :key="index" 
        :class="['message', msg.role === 'user' ? 'user-message' : 'ai-message']"
      >
        <div class="message-avatar" v-if="msg.role === 'ai'">
          <img src="../assets/manus-ai-avatar.svg" alt="AI" />
        </div>
        <div class="message-content">
          <div class="step-badge" v-if="msg.step">
            <span class="step-icon">◈</span>
            步骤 {{ msg.step }}
          </div>
          <div class="message-text">{{ msg.content }}</div>
          <div class="message-time" v-if="msg.time">{{ msg.time }}</div>
        </div>
        <div class="message-avatar user-avatar" v-if="msg.role === 'user'">
          <img src="../assets/manus-user-avatar.svg" alt="User" />
        </div>
      </div>
      <div v-if="isLoading" class="message ai-message">
        <div class="message-avatar">
          <img src="../assets/manus-ai-avatar.svg" alt="AI" />
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
        placeholder="输入你的问题..."
        rows="1"
        ref="inputRef"
      ></textarea>
      <button 
        v-if="!isLoading"
        class="send-btn" 
        @click="sendMessage" 
        :disabled="!inputMessage.trim()"
      >
        <span class="btn-icon">▶</span>
        发送
      </button>
      <button 
        v-else
        class="stop-btn" 
        @click="stopGeneration"
      >
        <span class="stop-icon">■</span>
        停止
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { chatWithManus } from '../utils/api'

const router = useRouter()

const messages = ref([])
const inputMessage = ref('')
const isLoading = ref(false)
const messagesContainer = ref(null)
const inputRef = ref(null)
let currentEventSource = null
let currentStepCount = 0

onMounted(() => {
  inputRef.value?.focus()
})

const scrollToBottom = () => {
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
    }
  })
}

const formatTime = () => {
  const now = new Date()
  const hours = now.getHours().toString().padStart(2, '0')
  const minutes = now.getMinutes().toString().padStart(2, '0')
  const seconds = now.getSeconds().toString().padStart(2, '0')
  return `${hours}:${minutes}:${seconds}`
}

const sendMessage = () => {
  const message = inputMessage.value.trim()
  if (!message || isLoading.value) return
  
  messages.value.push({
    role: 'user',
    content: message,
    time: formatTime()
  })
  
  inputMessage.value = ''
  isLoading.value = true
  currentStepCount = 0
  scrollToBottom()
  
  currentEventSource = chatWithManus(
    message,
    (data) => {
      currentStepCount++
      messages.value.push({
        role: 'ai',
        content: data,
        step: currentStepCount,
        time: formatTime()
      })
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

const stopGeneration = () => {
  if (currentEventSource) {
    currentEventSource.close()
    currentEventSource = null
  }
  isLoading.value = false
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
  background: linear-gradient(180deg, #0D0D0D 0%, #1A1A1A 50%, #0D0D0D 100%);
}

.chat-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 14px 24px;
  background: linear-gradient(135deg, #1A1A1A 0%, #2D2D2D 100%);
  color: #fff;
  box-shadow: 0 4px 30px rgba(0, 0, 0, 0.5);
  gap: 12px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
  position: relative;
  overflow: hidden;
}

.chat-header::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 1px;
  background: linear-gradient(90deg, transparent, rgba(0, 200, 255, 0.5), transparent);
}

.header-center {
  display: flex;
  align-items: center;
  gap: 12px;
  flex: 1;
  justify-content: center;
}

.header-avatar {
  width: 38px;
  height: 38px;
  border-radius: 50%;
  box-shadow: 0 0 20px rgba(0, 200, 255, 0.3);
  border: 2px solid rgba(0, 200, 255, 0.3);
}

.chat-header h2 {
  font-size: 18px;
  font-weight: 500;
  white-space: nowrap;
  letter-spacing: 1px;
}

.back-btn {
  display: flex;
  align-items: center;
  gap: 4px;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  color: #fff;
  padding: 8px 14px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
  flex-shrink: 0;
}

.back-btn:hover {
  background: rgba(255, 255, 255, 0.1);
  border-color: rgba(0, 200, 255, 0.3);
}

.back-icon {
  font-size: 16px;
}

.status {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 12px;
  padding: 6px 14px;
  background: rgba(0, 200, 255, 0.1);
  border: 1px solid rgba(0, 200, 255, 0.2);
  border-radius: 20px;
  flex-shrink: 0;
  color: #00C8FF;
}

.status-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: #00FF88;
  box-shadow: 0 0 10px #00FF88;
  animation: pulse 2s ease-in-out infinite;
}

@keyframes pulse {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.5; }
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 24px;
  display: flex;
  flex-direction: column;
  gap: 20px;
  background-image: 
    radial-gradient(circle at 20% 30%, rgba(0, 200, 255, 0.03) 0%, transparent 40%),
    radial-gradient(circle at 80% 70%, rgba(0, 255, 136, 0.02) 0%, transparent 40%);
}

.message {
  display: flex;
  gap: 12px;
  max-width: 85%;
  animation: fadeIn 0.4s ease;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(15px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
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
  width: 40px;
  height: 40px;
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
  background: linear-gradient(135deg, #1A1A2E 0%, #16213E 100%);
  box-shadow: 0 0 20px rgba(0, 200, 255, 0.4);
  border: 2px solid rgba(0, 200, 255, 0.3);
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

.step-badge {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 11px;
  color: #00C8FF;
  background: rgba(0, 200, 255, 0.1);
  border: 1px solid rgba(0, 200, 255, 0.2);
  padding: 4px 10px;
  border-radius: 12px;
  margin-bottom: 8px;
  align-self: flex-start;
  font-weight: 500;
  letter-spacing: 0.5px;
}

.step-icon {
  font-size: 10px;
}

.message-text {
  padding: 14px 18px;
  border-radius: 20px;
  line-height: 1.7;
  word-break: break-word;
  white-space: pre-wrap;
  text-align: left;
  font-size: 15px;
}

.user-message .message-text {
  background: linear-gradient(135deg, #00C8FF 0%, #0088CC 100%);
  color: #fff;
  border-bottom-right-radius: 6px;
  border-bottom-left-radius: 20px;
  box-shadow: 0 4px 20px rgba(0, 200, 255, 0.3);
}

.ai-message .message-text {
  background: linear-gradient(135deg, #3A3A3A 0%, #2A2A2A 100%);
  color: #FFFFFF;
  border-bottom-left-radius: 6px;
  border-bottom-right-radius: 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(255, 255, 255, 0.15);
  font-weight: 400;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
}

.message-time {
  font-size: 11px;
  color: rgba(255, 255, 255, 0.7);
  margin-top: 6px;
  align-self: flex-end;
}

.user-message .message-time {
  color: rgba(255, 255, 255, 0.85);
  align-self: flex-start;
}

.loading {
  display: flex;
  gap: 6px;
  padding: 18px 24px;
}

.dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: #00C8FF;
  animation: bounce 1.4s ease-in-out infinite;
  box-shadow: 0 0 10px rgba(0, 200, 255, 0.5);
}

.dot:nth-child(1) { animation-delay: 0s; }
.dot:nth-child(2) { animation-delay: 0.2s; }
.dot:nth-child(3) { animation-delay: 0.4s; }

@keyframes bounce {
  0%, 80%, 100% { transform: scale(0.8); opacity: 0.4; }
  40% { transform: scale(1.2); opacity: 1; }
}

.chat-input {
  display: flex;
  gap: 14px;
  padding: 16px 24px;
  background: linear-gradient(135deg, #1A1A1A 0%, #2D2D2D 100%);
  border-top: 1px solid rgba(255, 255, 255, 0.05);
  box-shadow: 0 -4px 30px rgba(0, 0, 0, 0.3);
}

.chat-input textarea {
  flex: 1;
  padding: 14px 18px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 16px;
  font-size: 15px;
  resize: none;
  outline: none;
  transition: all 0.3s ease;
  font-family: inherit;
  max-height: 120px;
  line-height: 1.5;
  min-height: 52px;
  background: rgba(255, 255, 255, 0.05);
  color: #fff;
}

.chat-input textarea:focus {
  border-color: rgba(0, 200, 255, 0.5);
  box-shadow: 0 0 0 3px rgba(0, 200, 255, 0.1);
  background: rgba(255, 255, 255, 0.08);
}

.chat-input textarea::placeholder {
  color: rgba(255, 255, 255, 0.3);
}

.send-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 14px 28px;
  background: linear-gradient(135deg, #00C8FF 0%, #0088CC 100%);
  color: #fff;
  border: none;
  border-radius: 16px;
  font-size: 15px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  flex-shrink: 0;
  box-shadow: 0 4px 20px rgba(0, 200, 255, 0.3);
}

.send-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 6px 30px rgba(0, 200, 255, 0.4);
}

.send-btn:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}

.btn-icon {
  font-size: 12px;
}

.stop-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 14px 28px;
  background: linear-gradient(135deg, #FF4757 0%, #C0392B 100%);
  color: #fff;
  border: none;
  border-radius: 16px;
  font-size: 15px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  flex-shrink: 0;
  box-shadow: 0 4px 20px rgba(255, 71, 87, 0.3);
}

.stop-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 30px rgba(255, 71, 87, 0.4);
}

.stop-icon {
  font-size: 12px;
}

@media (max-width: 768px) {
  .chat-header {
    padding: 12px 16px;
  }

  .chat-header h2 {
    font-size: 16px;
  }

  .header-avatar {
    width: 34px;
    height: 34px;
  }

  .chat-messages {
    padding: 16px;
    gap: 16px;
  }

  .message {
    max-width: 90%;
  }

  .message-avatar {
    width: 36px;
    height: 36px;
  }

  .message-text {
    padding: 12px 16px;
    font-size: 14px;
  }

  .chat-input {
    padding: 12px 16px;
    gap: 12px;
  }

  .send-btn, .stop-btn {
    padding: 12px 20px;
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

  .status {
    font-size: 10px;
    padding: 4px 10px;
  }

  .chat-messages {
    padding: 12px;
    gap: 12px;
  }

  .message {
    gap: 10px;
    max-width: 92%;
  }

  .message-avatar {
    width: 32px;
    height: 32px;
  }

  .message-text {
    padding: 10px 14px;
    font-size: 13px;
    border-radius: 16px;
  }

  .message-time {
    font-size: 10px;
  }

  .chat-input {
    padding: 10px 12px;
  }

  .chat-input textarea {
    padding: 12px 14px;
    font-size: 14px;
    min-height: 46px;
  }

  .send-btn, .stop-btn {
    padding: 12px 18px;
    font-size: 13px;
  }

  .btn-icon {
    display: none;
  }
}
</style>

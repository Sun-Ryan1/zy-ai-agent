const API_BASE_URL = '/api'

export function chatWithLoveApp(message, chatId, onMessage, onError, onComplete) {
  const url = `${API_BASE_URL}/ai/love_app/chat/sse?message=${encodeURIComponent(message)}&chatId=${encodeURIComponent(chatId)}`
  
  const eventSource = new EventSource(url)
  
  eventSource.onmessage = (event) => {
    if (event.data) {
      onMessage(event.data)
    }
  }
  
  eventSource.onerror = (error) => {
    eventSource.close()
    if (onError) {
      onError(error)
    }
    if (onComplete) {
      onComplete()
    }
  }
  
  return eventSource
}

export function chatWithManus(message, onMessage, onError, onComplete) {
  const url = `${API_BASE_URL}/ai/manus/chat?message=${encodeURIComponent(message)}`
  
  const eventSource = new EventSource(url)
  
  eventSource.onmessage = (event) => {
    if (event.data) {
      onMessage(event.data)
    }
  }
  
  eventSource.onerror = (error) => {
    eventSource.close()
    if (onError) {
      onError(error)
    }
    if (onComplete) {
      onComplete()
    }
  }
  
  return eventSource
}

export function generateChatId() {
  return 'chat_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9)
}

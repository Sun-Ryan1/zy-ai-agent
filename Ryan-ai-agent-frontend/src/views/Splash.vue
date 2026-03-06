<template>
  <div class="splash-container" v-if="showSplash" @selectstart.prevent @copy.prevent @contextmenu.prevent>
    <div class="stars"></div>
    <div class="content">
      <h1 class="title no-select">
        <span class="char">A</span><span class="char">I</span>
        <span class="space"></span>
        <span class="char">智</span><span class="char">能</span><span class="char">应</span><span class="char">用</span><span class="char">平</span><span class="char">台</span>
      </h1>
      <p class="subtitle no-select">汇聚全球 AI 之力，为人民服务！ — 您的专属 AI 应用平台@美好未来</p>
      
      <div class="terminal-window">
        <div class="terminal-header">
          <div class="terminal-buttons">
            <span class="btn-red"></span>
            <span class="btn-yellow"></span>
            <span class="btn-green"></span>
          </div>
          <div class="terminal-title">Ryan-terminal</div>
        </div>
        <div class="terminal-content">
          <div class="terminal-line">
            <span class="prompt">root@Ryan:~$</span>
            <span class="command">./launch-ai-platform.sh</span>
          </div>
          <div class="terminal-line loading">
            <span class="text">正在初始化 AI 引擎<span class="dots">...</span></span>
          </div>
          <div class="terminal-line loading delay-1">
            <span class="text">加载智能助手模型<span class="dots">...</span></span>
          </div>
          <div class="terminal-line loading delay-2">
            <span class="text">连接服务器<span class="dots">...</span></span>
          </div>
          <div class="terminal-line success delay-3">
            <span class="text">AI 平台启动成功！选择应用开始体验</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, onUnmounted } from 'vue'

const showSplash = ref(true)

if (!window.__splashInitialized) {
  window.__splashInitialized = true
  window.__isReturningFromChild = false
}

onMounted(() => {
  if (window.__isReturningFromChild) {
    showSplash.value = false
    window.__isReturningFromChild = false
  } else {
    setTimeout(() => {
      const container = document.querySelector('.splash-container')
      if (container) {
        container.classList.add('fade-out')
        setTimeout(() => {
          showSplash.value = false
        }, 800)
      }
    }, 4500)
  }
})
</script>

<style scoped>
.splash-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #0a0a0a 0%, #1a1a2e 50%, #16213e 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
  overflow: hidden;
  transition: opacity 0.8s ease, transform 0.8s ease;
}

.splash-container.fade-out {
  opacity: 0;
  transform: scale(1.1);
  pointer-events: none;
}

.stars {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: 
    radial-gradient(2px 2px at 20px 30px, #fff, transparent),
    radial-gradient(2px 2px at 40px 70px, rgba(255,255,255,0.8), transparent),
    radial-gradient(1px 1px at 90px 40px, #fff, transparent),
    radial-gradient(2px 2px at 160px 120px, rgba(255,255,255,0.9), transparent),
    radial-gradient(1px 1px at 230px 80px, #fff, transparent),
    radial-gradient(2px 2px at 300px 150px, rgba(255,255,255,0.7), transparent),
    radial-gradient(1px 1px at 370px 50px, #fff, transparent),
    radial-gradient(2px 2px at 450px 100px, rgba(255,255,255,0.8), transparent);
  background-size: 500px 200px;
  animation: twinkle 5s ease-in-out infinite;
  opacity: 0.6;
}

@keyframes twinkle {
  0%, 100% { opacity: 0.6; }
  50% { opacity: 0.3; }
}

.content {
  text-align: center;
  z-index: 1;
  padding: 20px;
}

.no-select {
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  -webkit-touch-callout: none;
}

.title {
  font-size: 48px;
  font-weight: 700;
  color: #fff;
  margin-bottom: 16px;
  letter-spacing: 2px;
  text-shadow: 0 0 30px rgba(102, 126, 234, 0.5);
}

.title .char {
  display: inline-block;
  opacity: 0;
  animation: fadeInDown 0.5s ease forwards;
}

.title .char:nth-child(1) { animation-delay: 0.1s; }
.title .char:nth-child(2) { animation-delay: 0.15s; }
.title .char:nth-child(3) { animation-delay: 0.3s; }
.title .char:nth-child(4) { animation-delay: 0.35s; }
.title .char:nth-child(5) { animation-delay: 0.4s; }
.title .char:nth-child(6) { animation-delay: 0.45s; }
.title .char:nth-child(7) { animation-delay: 0.5s; }
.title .char:nth-child(8) { animation-delay: 0.55s; }
.title .char:nth-child(9) { animation-delay: 0.6s; }

.title .space {
  display: inline-block;
  width: 20px;
}

@keyframes fadeInDown {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.subtitle {
  font-size: 18px;
  color: rgba(255, 255, 255, 0.7);
  margin-bottom: 40px;
  opacity: 0;
  animation: fadeIn 1s ease 0.8s forwards;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

.terminal-window {
  width: 100%;
  max-width: 700px;
  background: rgba(26, 26, 46, 0.95);
  border-radius: 12px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.5);
  overflow: hidden;
  border: 1px solid rgba(102, 126, 234, 0.2);
  opacity: 0;
  transform: translateY(30px);
  animation: slideUp 0.8s ease 1s forwards;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.terminal-header {
  display: flex;
  align-items: center;
  padding: 12px 16px;
  background: rgba(0, 0, 0, 0.3);
  border-bottom: 1px solid rgba(102, 126, 234, 0.1);
}

.terminal-buttons {
  display: flex;
  gap: 8px;
}

.btn-red, .btn-yellow, .btn-green {
  width: 12px;
  height: 12px;
  border-radius: 50%;
}

.btn-red { background: #ff5f56; }
.btn-yellow { background: #ffbd2e; }
.btn-green { background: #27c93f; }

.terminal-title {
  margin-left: 12px;
  color: rgba(255, 255, 255, 0.5);
  font-size: 13px;
  font-family: 'Courier New', monospace;
  text-align: left;
}

.terminal-content {
  padding: 20px;
  font-family: 'Courier New', monospace;
  font-size: 14px;
  line-height: 2;
  text-align: left;
}

.terminal-line {
  opacity: 0;
  animation: typeIn 0.5s ease forwards;
}

.terminal-line.loading {
  color: rgba(255, 255, 255, 0.6);
}

.terminal-line.success {
  color: #27c93f;
  font-weight: 600;
}

.terminal-line.delay-1 { animation-delay: 1.5s; }
.terminal-line.delay-2 { animation-delay: 2.5s; }
.terminal-line.delay-3 { animation-delay: 3.5s; }

@keyframes typeIn {
  from {
    opacity: 0;
    transform: translateX(-10px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

.prompt {
  color: #667eea;
  margin-right: 10px;
}

.command {
  color: rgba(255, 255, 255, 0.8);
}

.dots {
  display: inline-block;
  animation: dotsAnim 1.5s steps(4, end) infinite;
}

@keyframes dotsAnim {
  0%, 20% { content: ''; }
  40% { content: '.'; }
  60% { content: '..'; }
  80%, 100% { content: '...'; }
}

.dots::after {
  content: '';
  animation: dotsAnim 1.5s steps(4, end) infinite;
}

@media (max-width: 768px) {
  .title {
    font-size: 32px;
  }

  .subtitle {
    font-size: 14px;
  }

  .terminal-window {
    margin: 0 10px;
  }

  .terminal-content {
    padding: 16px;
    font-size: 12px;
  }
}
</style>

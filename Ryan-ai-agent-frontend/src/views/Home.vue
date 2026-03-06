<template>
  <div class="home-container">
    <Splash />
    
    <div class="galaxy-layer">
      <div class="galaxy galaxy-1"></div>
      <div class="galaxy galaxy-2"></div>
      <div class="galaxy galaxy-3"></div>
      <div class="galaxy galaxy-4"></div>
    </div>
    
    <div class="home-content">
      <div class="home-header" @selectstart.prevent @copy.prevent>
        <h1 class="title no-select" @selectstart.prevent @copy.prevent>AI Agent 应用中心</h1>
        <p class="subtitle no-select" @selectstart.prevent @copy.prevent>// 探索 AI 的无限可能 //</p>
      </div>
      <div class="app-cards">
        <div class="app-card love-card" @click="goToLoveApp">
          <div class="app-icon">
            <img src="../assets/love-ai-avatar.svg" alt="AI 恋爱大师" class="avatar-img" />
          </div>
          <h2 class="no-select">AI 恋爱大师</h2>
          <p class="no-select">智能恋爱顾问，为你解答情感困惑</p>
          <button class="enter-btn">进入应用</button>
        </div>
        <div class="app-card manus-card" @click="goToManus">
          <div class="app-icon">
            <img src="../assets/manus-ai-avatar.svg" alt="AI 超级智能体" class="avatar-img" />
          </div>
          <h2 class="no-select">AI 超级智能体</h2>
          <p class="no-select">强大的 AI 助手，解决各类问题</p>
          <button class="enter-btn">进入应用</button>
        </div>
      </div>
    </div>
    <footer class="footer" @selectstart.prevent @copy.prevent>
      <div class="footer-content">
        <div class="footer-links">
          <a href="#" class="footer-link">关于我们</a>
          <span class="footer-divider">|</span>
          <a href="#" class="footer-link">用户协议</a>
          <span class="footer-divider">|</span>
          <a href="#" class="footer-link">隐私政策</a>
          <span class="footer-divider">|</span>
          <a href="#" class="footer-link">联系我们</a>
        </div>
        <div class="footer-copyright no-select">
          <p>© 2024 Ryan AI Agent. All Rights Reserved.</p>
          <p class="footer-icp">京 ICP 备 XXXXXXXX 号 -1</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { onMounted, onUnmounted } from 'vue'
import Splash from './Splash.vue'

const router = useRouter()

const preventCopy = (e) => {
  const target = e.target
  if (target.classList.contains('no-select') || 
      target.closest('.home-header') ||
      target.closest('.app-card') ||
      target.closest('.footer')) {
    e.preventDefault()
    return false
  }
}

const preventContextMenu = (e) => {
  const target = e.target
  if (target.classList.contains('no-select') || 
      target.closest('.home-header') ||
      target.closest('.app-card') ||
      target.closest('.footer')) {
    e.preventDefault()
    return false
  }
}

onMounted(() => {
  document.addEventListener('selectstart', preventCopy)
  document.addEventListener('copy', preventCopy)
  document.addEventListener('contextmenu', preventContextMenu)
})

onUnmounted(() => {
  document.removeEventListener('selectstart', preventCopy)
  document.removeEventListener('copy', preventCopy)
  document.removeEventListener('contextmenu', preventContextMenu)
})

const goToLoveApp = () => {
  router.push('/love-app')
}

const goToManus = () => {
  router.push('/manus')
}
</script>

<style scoped>
.home-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: radial-gradient(ellipse at bottom, #1B2735 0%, #090A0F 100%);
  position: relative;
  overflow: hidden;
}

.home-container::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: 
    radial-gradient(1px 1px at 10% 10%, rgba(255,255,255,0.9), transparent),
    radial-gradient(1px 1px at 20% 25%, rgba(255,255,255,0.8), transparent),
    radial-gradient(2px 2px at 30% 15%, rgba(255,255,255,0.7), transparent),
    radial-gradient(1px 1px at 40% 35%, rgba(255,255,255,0.9), transparent),
    radial-gradient(1px 1px at 50% 20%, rgba(255,255,255,0.6), transparent),
    radial-gradient(2px 2px at 60% 40%, rgba(255,255,255,0.8), transparent),
    radial-gradient(1px 1px at 70% 30%, rgba(255,255,255,0.7), transparent),
    radial-gradient(1px 1px at 80% 50%, rgba(255,255,255,0.9), transparent),
    radial-gradient(2px 2px at 90% 45%, rgba(255,255,255,0.6), transparent),
    radial-gradient(1px 1px at 15% 55%, rgba(255,255,255,0.8), transparent),
    radial-gradient(1px 1px at 25% 70%, rgba(255,255,255,0.7), transparent),
    radial-gradient(2px 2px at 35% 60%, rgba(255,255,255,0.9), transparent),
    radial-gradient(1px 1px at 45% 80%, rgba(255,255,255,0.6), transparent),
    radial-gradient(1px 1px at 55% 65%, rgba(255,255,255,0.8), transparent),
    radial-gradient(2px 2px at 65% 75%, rgba(255,255,255,0.7), transparent),
    radial-gradient(1px 1px at 75% 85%, rgba(255,255,255,0.9), transparent),
    radial-gradient(1px 1px at 85% 70%, rgba(255,255,255,0.6), transparent),
    radial-gradient(2px 2px at 95% 90%, rgba(255,255,255,0.8), transparent),
    radial-gradient(1px 1px at 5% 95%, rgba(255,255,255,0.7), transparent),
    radial-gradient(1px 1px at 12% 40%, rgba(255,255,255,0.9), transparent);
  background-size: 200px 200px;
  animation: twinkle 4s ease-in-out infinite;
  opacity: 0.8;
  pointer-events: none;
}

.galaxy-layer {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  overflow: hidden;
}

.galaxy {
  position: absolute;
  border-radius: 50%;
  animation: galaxyRotate 30s linear infinite;
}

.galaxy::before,
.galaxy::after {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  border-radius: 50%;
  transform: translate(-50%, -50%);
}

.galaxy-1 {
  width: 300px;
  height: 300px;
  top: 5%;
  left: 10%;
  background: 
    radial-gradient(ellipse at 30% 30%, rgba(147, 112, 219, 0.6) 0%, transparent 50%),
    radial-gradient(ellipse at 70% 70%, rgba(138, 43, 226, 0.4) 0%, transparent 50%),
    radial-gradient(ellipse at center, rgba(75, 0, 130, 0.3) 0%, transparent 70%);
  box-shadow: 
    inset 0 0 60px rgba(147, 112, 219, 0.4),
    0 0 80px rgba(138, 43, 226, 0.3);
  animation-delay: 0s;
}

.galaxy-1::before {
  width: 80%;
  height: 40%;
  background: linear-gradient(90deg, 
    transparent 0%, 
    rgba(147, 112, 219, 0.3) 20%,
    transparent 40%,
    rgba(138, 43, 226, 0.2) 60%,
    transparent 80%);
  animation: galaxyArm 15s linear infinite reverse;
}

.galaxy-1::after {
  width: 60%;
  height: 30%;
  background: linear-gradient(90deg, 
    transparent 0%, 
    rgba(200, 162, 255, 0.2) 30%,
    transparent 50%,
    rgba(180, 100, 255, 0.15) 70%,
    transparent 100%);
  animation: galaxyArm 20s linear infinite;
}

.galaxy-2 {
  width: 250px;
  height: 250px;
  top: 60%;
  right: 5%;
  background: 
    radial-gradient(ellipse at 40% 40%, rgba(100, 149, 237, 0.6) 0%, transparent 50%),
    radial-gradient(ellipse at 60% 60%, rgba(65, 105, 225, 0.4) 0%, transparent 50%),
    radial-gradient(ellipse at center, rgba(30, 60, 150, 0.3) 0%, transparent 70%);
  box-shadow: 
    inset 0 0 50px rgba(100, 149, 237, 0.4),
    0 0 60px rgba(65, 105, 225, 0.3);
  animation-delay: -10s;
}

.galaxy-2::before {
  width: 75%;
  height: 35%;
  background: linear-gradient(90deg, 
    transparent 0%, 
    rgba(100, 149, 237, 0.3) 25%,
    transparent 45%,
    rgba(65, 105, 225, 0.2) 65%,
    transparent 85%);
  animation: galaxyArm 18s linear infinite reverse;
}

.galaxy-2::after {
  width: 55%;
  height: 25%;
  background: linear-gradient(90deg, 
    transparent 0%, 
    rgba(135, 206, 250, 0.2) 35%,
    transparent 55%,
    rgba(100, 149, 237, 0.15) 75%,
    transparent 100%);
  animation: galaxyArm 22s linear infinite;
}

.galaxy-3 {
  width: 200px;
  height: 200px;
  bottom: 20%;
  left: 20%;
  background: 
    radial-gradient(ellipse at 35% 35%, rgba(255, 182, 193, 0.6) 0%, transparent 50%),
    radial-gradient(ellipse at 65% 65%, rgba(255, 105, 180, 0.4) 0%, transparent 50%),
    radial-gradient(ellipse at center, rgba(199, 21, 133, 0.3) 0%, transparent 70%);
  box-shadow: 
    inset 0 0 40px rgba(255, 182, 193, 0.4),
    0 0 50px rgba(255, 105, 180, 0.3);
  animation-delay: -20s;
}

.galaxy-3::before {
  width: 70%;
  height: 30%;
  background: linear-gradient(90deg, 
    transparent 0%, 
    rgba(255, 182, 193, 0.3) 20%,
    transparent 40%,
    rgba(255, 105, 180, 0.2) 60%,
    transparent 80%);
  animation: galaxyArm 16s linear infinite reverse;
}

.galaxy-3::after {
  width: 50%;
  height: 20%;
  background: linear-gradient(90deg, 
    transparent 0%, 
    rgba(255, 200, 220, 0.2) 30%,
    transparent 50%,
    rgba(255, 150, 180, 0.15) 70%,
    transparent 100%);
  animation: galaxyArm 24s linear infinite;
}

.galaxy-4 {
  width: 180px;
  height: 180px;
  top: 30%;
  right: 25%;
  background: 
    radial-gradient(ellipse at 45% 45%, rgba(0, 255, 255, 0.5) 0%, transparent 50%),
    radial-gradient(ellipse at 55% 55%, rgba(0, 200, 200, 0.3) 0%, transparent 50%),
    radial-gradient(ellipse at center, rgba(0, 150, 150, 0.2) 0%, transparent 70%);
  box-shadow: 
    inset 0 0 40px rgba(0, 255, 255, 0.3),
    0 0 50px rgba(0, 200, 200, 0.2);
  animation-delay: -5s;
}

.galaxy-4::before {
  width: 65%;
  height: 28%;
  background: linear-gradient(90deg, 
    transparent 0%, 
    rgba(0, 255, 255, 0.25) 25%,
    transparent 45%,
    rgba(0, 200, 200, 0.2) 65%,
    transparent 85%);
  animation: galaxyArm 20s linear infinite reverse;
}

.galaxy-4::after {
  width: 45%;
  height: 18%;
  background: linear-gradient(90deg, 
    transparent 0%, 
    rgba(100, 255, 255, 0.15) 35%,
    transparent 55%,
    rgba(0, 220, 220, 0.1) 75%,
    transparent 100%);
  animation: galaxyArm 26s linear infinite;
}

@keyframes galaxyRotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

@keyframes galaxyArm {
  from { transform: translate(-50%, -50%) rotate(0deg); }
  to { transform: translate(-50%, -50%) rotate(360deg); }
}

@keyframes twinkle {
  0%, 100% { opacity: 0.8; }
  50% { opacity: 0.4; }
}

.home-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px 20px;
}

.home-header {
  text-align: center;
  margin-bottom: 60px;
}

.no-select {
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  -webkit-touch-callout: none;
}

.title {
  font-size: clamp(28px, 5vw, 48px);
  color: #fff;
  margin-bottom: 16px;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
  font-weight: 700;
}

.subtitle {
  font-size: clamp(14px, 2vw, 18px);
  color: rgba(255, 255, 255, 0.9);
}

.app-cards {
  display: flex;
  gap: 40px;
  flex-wrap: wrap;
  justify-content: center;
  width: 100%;
  max-width: 800px;
}

.app-card {
  background: var(--card-bg);
  border-radius: var(--border-radius);
  padding: 40px 30px;
  width: 100%;
  max-width: 320px;
  text-align: center;
  cursor: pointer;
  transition: var(--transition);
  box-shadow: var(--shadow-medium);
}

.app-card:hover {
  transform: translateY(-10px);
  box-shadow: var(--shadow-heavy);
}

.app-card:active {
  transform: translateY(-5px);
}

.app-icon {
  width: 80px;
  height: 80px;
  margin: 0 auto 20px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.avatar-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.love-card .app-icon {
  background: linear-gradient(135deg, #FFB6C1 0%, #FFC0CB 100%);
  box-shadow: 0 4px 20px rgba(255, 133, 162, 0.4);
  border: 3px solid rgba(255, 255, 255, 0.8);
}

.love-card {
  background: linear-gradient(180deg, #FFFFFF 0%, #FFF5F7 100%);
  border: 1px solid rgba(255, 182, 193, 0.3);
}

.love-card:hover {
  box-shadow: 0 20px 60px rgba(255, 133, 162, 0.25);
}

.love-card h2 {
  color: #FF6B8A !important;
}

.love-card p {
  color: #FF6B8A !important;
}

.love-card .enter-btn {
  background: linear-gradient(135deg, #FF85A2 0%, #FFB6C1 100%);
  box-shadow: 0 4px 15px rgba(255, 133, 162, 0.3);
}

.love-card .enter-btn:hover {
  box-shadow: 0 6px 25px rgba(255, 133, 162, 0.5);
  transform: scale(1.05);
}

.manus-card .app-icon {
  background: linear-gradient(135deg, #1A237E 0%, #0D47A1 100%);
  box-shadow: 0 4px 25px rgba(0, 200, 255, 0.3);
  border: 3px solid rgba(0, 200, 255, 0.3);
}

.manus-card {
  background: linear-gradient(180deg, #1A1A1A 0%, #2D2D2D 100%);
  border: 1px solid rgba(0, 200, 255, 0.2);
}

.manus-card:hover {
  box-shadow: 0 20px 60px rgba(0, 200, 255, 0.2);
}

.manus-card h2 {
  color: #FFFFFF !important;
  text-shadow: 0 0 20px rgba(0, 229, 255, 0.5);
  font-weight: 600;
}

.manus-card p {
  color: rgba(255, 255, 255, 0.9) !important;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
}

.manus-card .enter-btn {
  background: linear-gradient(135deg, #00C8FF 0%, #0088CC 100%);
  box-shadow: 0 4px 20px rgba(0, 200, 255, 0.3);
}

.manus-card .enter-btn:hover {
  box-shadow: 0 6px 30px rgba(0, 200, 255, 0.5);
  transform: scale(1.05);
}

.app-card h2 {
  font-size: clamp(18px, 3vw, 24px);
  color: var(--text-primary);
  margin-bottom: 12px;
  font-weight: 600;
}

.app-card p {
  font-size: clamp(12px, 2vw, 14px);
  color: var(--text-secondary);
  margin-bottom: 24px;
  line-height: 1.6;
}

.enter-btn {
  background: var(--primary-gradient);
  color: #fff;
  border: none;
  padding: 12px 32px;
  border-radius: var(--border-radius-lg);
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: var(--transition);
}

.love-card .enter-btn {
  background: var(--love-gradient);
}

.enter-btn:hover {
  transform: scale(1.05);
  box-shadow: 0 5px 20px rgba(102, 126, 234, 0.4);
}

.love-card .enter-btn:hover {
  box-shadow: 0 5px 20px rgba(255, 107, 107, 0.4);
}

@media (max-width: 768px) {
  .home-container {
    padding: 30px 16px;
  }

  .home-header {
    margin-bottom: 40px;
  }

  .app-cards {
    gap: 24px;
  }

  .app-card {
    padding: 30px 24px;
  }
}

@media (max-width: 480px) {
  .home-content {
    padding: 24px 12px;
    justify-content: flex-start;
    padding-top: 60px;
  }

  .home-header {
    margin-bottom: 30px;
  }

  .app-card {
    padding: 24px 20px;
  }

  .app-icon {
    width: 64px;
    height: 64px;
  }

  .enter-btn {
    padding: 10px 24px;
    font-size: 14px;
  }
}

.footer {
  background: rgba(0, 0, 0, 0.15);
  backdrop-filter: blur(10px);
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  padding: 20px 24px;
}

.footer-content {
  max-width: 800px;
  margin: 0 auto;
  text-align: center;
}

.footer-links {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 12px;
}

a.footer-link {
  color: rgba(255, 255, 255, 0.7);
  text-decoration: none;
  font-size: 13px;
  transition: color 0.3s ease;
}

a.footer-link:hover {
  color: #fff;
}

.footer-divider {
  color: rgba(255, 255, 255, 0.3);
  font-size: 12px;
}

.footer-copyright {
  color: rgba(255, 255, 255, 0.5);
  font-size: 12px;
  line-height: 1.8;
}

.footer-icp {
  font-size: 11px;
  opacity: 0.7;
}

@media (max-width: 480px) {
  .footer {
    padding: 16px 12px;
  }

  .footer-link {
    font-size: 12px;
  }

  .footer-copyright {
    font-size: 11px;
  }

  .footer-divider {
    display: none;
  }

  .footer-links {
    gap: 12px;
  }
}
</style>

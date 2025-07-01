<template>
  <div class="container">
    <!-- Lado esquerdo: Imagem -->
    <div class="image-side">
      <img src="assets/css/images/login-image.png" alt="Psicoterapia" />
    </div>

    <!-- Lado direito: Card de login -->
    <div class="login-side">
      <div class="login-card">
        <div class="logo-container">
          <img src="assets/css/images/logo.png" alt="Mentale Logo" class="logo-image" />
        </div>
        <p class="welcome">Bem-vindo(a) à Mentale<br />Sua jornada de cuidado emocional começa aqui</p>

        <button class="google-button">
          <img src="https://www.gstatic.com/firebasejs/ui/2.0.0/images/auth/google.svg" alt="Google Icon" />
          Entrar com Google
        </button>

        <div class="divider">
          <span>Ou entre com</span>
        </div>

        <form @submit.prevent="login">
          <input v-model="username" type="text" placeholder="Usuário" required />
          <input v-model="password" type="password" placeholder="Senha" required />
          <button type="submit">ENTRAR</button>
        </form>

        <p class="register-link">
          Não tem uma conta?
          <a href="/register">Cadastre-se aqui</a>
        </p>

        <p v-if="error" class="error">{{ error }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter() 
const username = ref('')
const password = ref('')
const error = ref('')

const login = async () => {
  error.value = ''
  try {
    const response = await axios.post('http://localhost:8080/auth/login', {
      username: username.value,
      password: password.value
    }, {
      withCredentials: true 
    })

    router.push('/home')
  } catch (err) {
    error.value = err.response?.data?.error || 'Erro ao autenticar'
  }
}
</script>



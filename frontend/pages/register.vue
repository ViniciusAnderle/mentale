<template>
  <div class="container">
    <!-- Lado esquerdo: Imagem -->
    <div class="image-side">
      <img src="assets/css/images/login-image.png" alt="Psicoterapia" />
    </div>

    <!-- Lado direito: Card de cadastro -->
    <div class="login-side">
      <div class="login-card">
        <div class="logo-container">
          <img src="assets/css/images/logo.png" alt="Mentale Logo" class="logo-image" />
        </div>
        <p class="welcome">Cadastre-se na Mentale<br />e inicie sua jornada de cuidado emocional</p>

        <button class="google-button">
          <img src="https://www.gstatic.com/firebasejs/ui/2.0.0/images/auth/google.svg" alt="Google Icon" />
          Entrar com Google
        </button>

        <div class="divider">
          <span>Ou cadastre com</span>
        </div>

        <form @submit.prevent="register">
          <input v-model="email" type="text" placeholder="Usuário" required />
          <input v-model="password" type="password" placeholder="Senha" required />
          <button type="submit">CADASTRAR</button>
        </form>

        <p class="register-link">
          Já possui conta?
          <a href="/login">Acesse aqui</a>
        </p>

        <p v-if="message" class="token">{{ message }}</p>
        <p v-if="error" class="error">{{ error }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import '/assets/css/login.css'

const email = ref('')
const password = ref('')
const message = ref('')
const error = ref('')

const register = async () => {
  error.value = ''
  message.value = ''
  try {
    const response = await axios.post('http://localhost:8080/auth/register', {
      email: email.value,
      password: password.value
    })
    if (typeof response.data === 'string') {
      message.value = response.data
    } else {
      message.value = response.data.message || 'Cadastro realizado com sucesso'
    }
  } catch (err) {
    const data = err.response?.data
    if (typeof data === 'string') {
      error.value = data
    } else if (data?.error) {
      error.value = data.error
    } else if (typeof data === 'object') {
      error.value = Object.values(data).join(', ')
    } else {
      error.value = 'Erro desconhecido ao cadastrar'
    }
  }
}
</script>

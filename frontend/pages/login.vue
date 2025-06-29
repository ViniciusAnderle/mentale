<template>
  <div class="form">
    <h2>Login</h2>
    <form @submit.prevent="login">
      <input v-model="username" placeholder="Usuário" required />
      <input v-model="password" type="password" placeholder="Senha" required />
      <button type="submit">Entrar</button>
    </form>
    <p v-if="token">Token: {{ token }}</p>
    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const username = ref('')
const password = ref('')
const token = ref('')
const error = ref('')

const login = async () => {
  error.value = ''
  try {
    const response = await axios.post('http://localhost:8080/auth/login', {
      username: username.value,
      password: password.value
    })
    token.value = response.data.token
    localStorage.setItem('token', token.value)
  } catch (err) {
    error.value = err.response?.data || 'Erro ao autenticar'
  }
}
</script>

<style scoped>
.form {
  max-width: 400px;
  margin: 30px auto;
  display: flex;
  flex-direction: column;
}
input {
  margin-bottom: 10px;
  padding: 8px;
}
button {
  padding: 10px;
}
.error {
  color: red;
  margin-top: 10px;
}
</style>

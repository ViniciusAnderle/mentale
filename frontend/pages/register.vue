<template>
  <div class="form">
    <h2>Cadastro</h2>
    <form @submit.prevent="register">
      <input v-model="username" placeholder="Usuário" required />
      <input v-model="password" type="password" placeholder="Senha" required />
      <button type="submit">Cadastrar</button>
    </form>
    <p v-if="message">{{ message }}</p>
    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const username = ref('')
const password = ref('')
const message = ref('')
const error = ref('')

const register = async () => {
  error.value = ''
  message.value = ''
  try {
    const response = await axios.post('http://localhost:8080/auth/register', {
      username: username.value,
      password: password.value
    })
    // Verifica se a resposta é string ou objeto
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

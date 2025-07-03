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
          <img
            src="assets/css/images/logo.png"
            alt="Mentale Logo"
            class="logo-image"
          />
        </div>

        <p class="welcome">
          Cadastre-se na Mentale<br />e inicie sua jornada de cuidado emocional
        </p>
        <client-only>
          <GoogleLogin :callback="handleGoogleLogin" />
        </client-only>

        <div class="divider">
          <span>Ou cadastre com</span>
        </div>

        <!-- Formulário de cadastro manual -->
        <form @submit.prevent="register">
          <input v-model="email" type="text" placeholder="Usuário" required />
          <input
            v-model="password"
            type="password"
            placeholder="Senha"
            required
          />
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
import { ref } from "vue";
import axios from "axios";
import "/assets/css/login.css";
import { GoogleLogin } from "vue3-google-login";

const email = ref("");
const password = ref("");
const message = ref("");
const error = ref("");

const register = async () => {
  error.value = "";
  message.value = "";
  try {
    const response = await axios.post("http://localhost:8080/auth/register", {
      email: email.value,
      password: password.value,
    });
    message.value = response.data.message || "Cadastro realizado com sucesso";
  } catch (err) {
    const data = err.response?.data;
    if (typeof data === "string") {
      error.value = data;
    } else if (data?.error) {
      error.value = data.error;
    } else if (typeof data === "object") {
      error.value = Object.values(data).join(", ");
    } else {
      error.value = "Erro desconhecido ao cadastrar";
    }
  }
};

const handleGoogleLogin = async (response) => {
  console.log("Google response:", response);

  const idToken = response?.credential;
  if (!idToken) {
    error.value = "Erro: Token Google ausente";
    return;
  }

  try {
    const res = await axios.post(
      "http://localhost:8080/auth/oauth/google",
      {
        idToken,
      },
      { withCredentials: true }
    );

    message.value =
      res.data.message || "Login com Google realizado com sucesso";
  } catch (err) {
    const data = err.response?.data;
    error.value = data?.error || "Erro ao logar com Google";
  }
};
</script>

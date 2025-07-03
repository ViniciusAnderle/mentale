<template>
  <div class="container">
    <div class="image-side">
      <img src="assets/css/images/login-image.png" alt="Psicoterapia" />
    </div>

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
          Bem-vindo(a) à Mentale<br />Sua jornada de cuidado emocional começa aqui
        </p>

        <!-- Google Login -->
        <client-only>
          <GoogleLogin :callback="handleGoogleLogin" />
        </client-only>

        <div class="divider">
          <span>Ou entre com</span>
        </div>

        <form @submit.prevent="login">
          <input v-model="email" type="text" placeholder="Usuário" required />
          <input
            v-model="password"
            type="password"
            placeholder="Senha"
            required
          />
          <button type="submit">ENTRAR</button>
        </form>

        <p class="register-link">
          Não tem uma conta?
          <a href="/register">Cadastre-se aqui</a>
        </p>

        <p v-if="message" class="token">{{ message }}</p>
        <p v-if="error" class="error">{{ error }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import { GoogleLogin } from "vue3-google-login";
import "/assets/css/login.css";

const router = useRouter();
const email = ref("");
const password = ref("");
const message = ref("");
const error = ref("");

const login = async () => {
  error.value = "";
  try {
    await axios.post(
      "http://localhost:8080/auth/login",
      {
        email: email.value,
        password: password.value,
      },
      { withCredentials: true }
    );
    router.push("/home");
  } catch (err) {
    error.value = err.response?.data?.error || "Erro ao autenticar";
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
      { idToken },
      { withCredentials: true }
    );

    message.value =
      res.data.message || "Login com Google realizado com sucesso";

    router.push("/home");
  } catch (err) {
    const data = err.response?.data;
    error.value = data?.error || "Erro ao logar com Google";
  }
};
</script>

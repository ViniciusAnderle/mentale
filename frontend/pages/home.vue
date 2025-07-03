<template>
  Home Sweet Home
  <button @click="logout">Sair</button>
</template>

<script setup>
import axios from "axios";
import { useRouter } from "vue-router";

const router = useRouter();

const logout = async () => {
  try {
    await axios.post(
      "http://localhost:8080/auth/logout",
      {},
      { withCredentials: true }
    );
    window.location.href = "/login"; // ou use location.reload()
  } catch (err) {
    console.error("Erro ao sair:", err);
  }
};
</script>

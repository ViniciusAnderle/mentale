<template>
  <div>
    <nav>
      <NuxtLink to="/login">Login</NuxtLink> |
      <NuxtLink to="/register">Cadastro</NuxtLink>
    </nav>
    <NuxtPage />
  </div>
</template>

<style scoped>
nav {
  display: flex;
  justify-content: center;
  gap: 16px;
  margin: 20px 0;
}
a {
  text-decoration: none;
  color: #42b983;
  font-weight: bold;
}
</style>

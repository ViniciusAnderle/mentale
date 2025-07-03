export default defineNuxtRouteMiddleware(async (to) => {
  const publicRoutes = ['/login', '/register']

  if (publicRoutes.includes(to.path)) return

  try {
    await $fetch('http://localhost:8080/auth/check', {
      method: 'GET',
      credentials: 'include'
    })
  } catch (err) {
    return navigateTo('/login')
  }
})

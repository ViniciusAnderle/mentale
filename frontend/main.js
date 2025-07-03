import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { GoogleLoginPlugin } from 'vue3-google-login'

const app = createApp(App)

app.use(router)

// 🔐 Substitua com seu CLIENT_ID real
app.use(GoogleLoginPlugin, {
  clientId: '1059352211050-2qvdmvg53lid9jvmdj11a1mbke9d15iq.apps.googleusercontent.com'
})

app.mount('#app')

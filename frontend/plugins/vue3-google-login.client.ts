import { defineNuxtPlugin } from '#app'
import GoogleSignInPlugin from 'vue3-google-login'

export default defineNuxtPlugin((nuxtApp) => {
  nuxtApp.vueApp.use(GoogleSignInPlugin, {
    clientId: '1059352211050-2qvdmvg53lid9jvmdj11a1mbke9d15iq.apps.googleusercontent.com'
  })
})

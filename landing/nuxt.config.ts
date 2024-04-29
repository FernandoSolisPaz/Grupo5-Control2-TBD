// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: { enabled: true },
  modules: [
    '@nuxt/ui',
    '@pinia/nuxt',
    '@pinia-plugin-persistedstate/nuxt',
    "@samk-dev/nuxt-vcalendar"
  ],
  ui: {
    global: true,
    icons: ['mdi', 'simple-icons']
  }
})
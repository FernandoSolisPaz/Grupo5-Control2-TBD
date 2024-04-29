<script setup>
const user = userStore()
</script>

<template>
  <div class="h-screen">
    <appBar />
    <NuxtPage />
    <UNotifications />
  </div>
</template>

<script setup>
const user = userStore()
const colorMode = useColorMode();
const toast = useToast();
const isDark = computed({
  get () {
    return colorMode.value === 'dark'
  },
  set () {
    colorMode.preference = colorMode.value === 'dark' ? 'light' : 'dark'
  }
})

const tokenCookie = useCookie('token');
const humansCookie = useCookie('humans');

function logout() {
    tokenCookie.value = undefined;
    user.$reset();
    const router = useRouter();
    router.push('/login');
}

const homeCounter = ref(0);
function home() {
   
    if (humansCookie.value === 1){
        const router = useRouter();
        router.push('/');
    };
    if (tokenCookie.value) {
        console.log(homeCounter.value);
        homeCounter.value++;
        if (homeCounter.value === 1) {
            toast.add({ title:'Ya estás en el home ¿Qué buscas?', color:'cyan', icon:'i-heroicons-bell-alert-16-solid'});
        } else if (homeCounter.value === 2) {
            toast.add({ title:'No hay nada aquí... ¿o sí?', color:'cyan', icon:'i-heroicons-bell-alert-16-solid'});
        } else if (homeCounter.value === 3) {
            const router = useRouter();
            router.push('/humans');
            console.log("hola")
        } else if (homeCounter.value === 4) {
            homeCounter.value = 0;
        }
    } else {
        toast.add({ title:'Debes loguearte para hacer eso', color:'red', icon:'i-heroicons-bell-alert-16-solid'});
    }
}

</script>

<template>
    <div class="py-3.5 px-6 shadow md:flex justify-between items-center ">
        <div class="flex items-center justify-between">
            <UButton
                icon="i-heroicons-home-solid"
                color="primary"
                variant="ghost"
                @click="home">
            </UButton>
            <ClientOnly>
                <UButton
                class="ml-2"
                :icon="isDark ? 'i-heroicons-moon-20-solid' : 'i-heroicons-sun-20-solid'"
                color="gray"
                variant="ghost"
                aria-label="Theme"
                @click="isDark = !isDark"
                />
                <template #fallback>
                    <div class="w-8 h-8" />
                </template>
            </ClientOnly>
            <h1 class="text-xl font-bold pl-5">TBD To Do</h1>
        </div>
        <div class="flex items-center justify-between">
            <UButton v-if="tokenCookie !== undefined" icon="i-heroicons-arrow-left-on-rectangle" class="rounded-full" size="xl" variant="ghost" label="Log out" trailing @click="logout"/>
        </div>
    </div>
</template>
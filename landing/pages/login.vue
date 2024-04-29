<script setup>
import { z } from 'zod';
const tokenCookie = useCookie('token');
const router = useRouter();
const toast = useToast();

if (tokenCookie.value) {
    router.push('/');
}

const config = useRuntimeConfig();

const schema = z.object({
    password: z.string().min(8, 'Contraseña debe tener al menos 8 carácteres')
});

const state = reactive({
    name: '',
    password: '',
});


async function handleSubmit (event) {
    const result = schema.safeParse(state);
    if (!result.success) {
        toast.add({ title: 'Error en los datos ingresados', color: 'red' });
        return;
    }
    try {
        await useApi().login(state.name, state.password);
        toast.add({ title: 'Inicio de sesión exitoso', color: 'green' });
        router.push('/');
    } catch (error) {
        if (error.response){
            if (error.response.status === 400) {
                const toast = useToast();
                toast.add({ title: 'Error en los datos ingresados', color: 'red' });
            } else if (error.response.status === 401) {
                const toast = useToast();
                toast.add({ title: 'Credenciales incorrectas', color: 'red' });
            } else {
                const toast = useToast();
                toast.add({ title: 'Error en el servidor', color: 'red' });
            }
        }
    }
    router.push('/');
}
</script>

<template>
    <div class="flex justify-center items-center content-below-appbar">
        <UCard class="w-3/5 max-w-2xl">
            <h1 class="text-primary font-bold text-center text-inherit text-3xl py-5">Inicio de Sesión</h1>
            <UForm :schema="schema" :state="state" class="space-y-4 text-center" @submit="handleSubmit">
                <UFormGroup label="Nombre" name="name">
                    <UInput v-model="state.name" />
                </UFormGroup>
                
                <UFormGroup label="Contraseña" name="password">
                    <UInput v-model="state.password" type="password" />
                </UFormGroup>
                <UButton class="w-1/3 max-w-52 justify-center" type="submit">
                    Submit
                </UButton>
                <UFormGroup>
                    <ULink
                    to="/register"
                    class="text-primary text-end underline text-sm"
                    >
                        No tengo una cuenta
                    </ULink>
                </UFormGroup>
            </UForm>
        </UCard>
    </div>
</template>

<style>
.content-below-appbar {
    height: calc(100vh - 72px)
}
</style>